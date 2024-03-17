package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.common.Result;
import com.example.constant.ApprovalStatus;
import com.example.constant.CompanyType;
import com.example.constant.TradingType;
import com.example.entity.*;
import com.example.mapper.FinancingInfoMapper;
import com.example.service.*;
import com.example.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/3/6 14:58
 */
@Service
public class FinancingInfoServiceImpl extends ServiceImpl<FinancingInfoMapper, FinancingInfo> implements FinancingInfoService {
    @Autowired
    private UserService userService;

    @Autowired
    private FundFlowService fundFlowService;

    @Autowired
    private CompanyAssetService companyAssetService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FinancialInstitutionService financialInstitutionService;

    @Autowired
    private CoreEnterpriseService coreEnterpriseService;

    @Autowired
    private SmallMiddleEnterpriseService smallMiddleEnterpriseService;

    @Override
    public Result create(
            FinancingInfo financingInfo
    ) {
        financingInfo.setId(UUIDUtils.generate());
        financingInfo.setApplyTime(LocalDateTime.now());
        financingInfo.setApprovalStatus(ApprovalStatus.PENDING);

        // 根据企业信用等级设置利率
        String companyId = financingInfo.getCompanyId();
        Company company = companyService.getById(companyId);
        FinancialInstitution financialInstitution = financialInstitutionService.getById(financingInfo.getFinancialInstitutionId());

        if (company.getCompanyType() == CompanyType.CORE_ENTERPRISE) {
            CoreEnterprise coreEnterprise = coreEnterpriseService.getById(companyId);
            financingInfo.setInterestRate(financialInstitution.getLoanInterestRate().getOrDefault(coreEnterprise.getCreditRating(), BigDecimal.ZERO));
        } else if (company.getCompanyType() == CompanyType.SMEs) {
            SmallMiddleEnterprise smallMiddleEnterprise = smallMiddleEnterpriseService.getById(companyId);
            CoreEnterprise coreEnterprise = coreEnterpriseService.getById(smallMiddleEnterprise.getCoreEnterpriseId());
            financingInfo.setInterestRate(financialInstitution.getLoanInterestRate().getOrDefault(coreEnterprise.getCreditRating(), BigDecimal.ZERO));
        } else {
            return Result.error("当前企业不支持融资");
        }

        this.save(financingInfo);
        return Result.success("融资信息新建成功");
    }

    @Override
    public Result approve(
            String id,
            ApprovalStatus approvalStatus,
            String approvalComment,
            BigDecimal interestRate
    ) {
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancingInfo::getId, id);
        FinancingInfo financingInfo = this.getOne(queryWrapper);
        if (financingInfo == null) {
            return Result.error("融资信息不存在");
        }
        // 更新融资信息的状态和审批意见
        financingInfo.setApprovalId(BaseContext.getCurrentId());
        financingInfo.setApprovalComment(approvalComment);
        financingInfo.setApprovalStatus(approvalStatus);
        financingInfo.setApprovalTime(LocalDateTime.now());
        this.save(financingInfo);
        return Result.success("融资信息审批成功");
    }

    @Override
    public Result loan(String id) throws InterruptedException, TimeoutException {
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancingInfo::getId, id);
        FinancingInfo financingInfo = this.getOne(queryWrapper);
        if (financingInfo == null) {
            return Result.error("融资信息不存在");
        }
        // 更新融资信息的状态为已放款
        financingInfo.setLoanTime(LocalDateTime.now());
        financingInfo.setIsRepay(false);
        this.save(financingInfo);
        // 更新资金流水表
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getId, BaseContext.getCurrentId());
        User user = userService.getOne(userQueryWrapper);
        String companyId = user.getCompanyId();
        FundFlow fundFlow = new FundFlow();
        fundFlow.setAccount(financingInfo.getAccount());
        fundFlow.setPayer(financingInfo.getApprovalId());
        fundFlow.setReceiver(companyId);
        fundFlow.setTradingType(TradingType.FINANCING_LOAN);
        fundFlowService.createFundFlow(fundFlow);
        // 更新企业资金信息表

        LambdaQueryWrapper<CompanyAsset> companyAssetQueryWrapper = new LambdaQueryWrapper<>();
        companyAssetQueryWrapper.eq(CompanyAsset::getCompanyId, companyId);
        CompanyAsset companyAsset = companyAssetService.getOne(companyAssetQueryWrapper);
        if (companyAsset == null) {
            // 初始化
            companyAsset = new CompanyAsset();
            companyAsset.setId(UUIDUtils.generate());
            companyAsset.setCompanyId(companyId);
            companyAsset.setCash(financingInfo.getAccount());
            companyAsset.setFinancingInfoList(new ArrayList<>(Collections.singletonList(financingInfo)));
            companyAssetService.save(companyAsset);
        } else {
            // 更新企业资金信息
            BigDecimal cash = companyAsset.getCash();
            cash = cash.add(financingInfo.getAccount());
            companyAsset.setCash(cash);
            List<FinancingInfo> financingInfoList = companyAsset.getFinancingInfoList();
            financingInfoList.add(financingInfo);
            companyAsset.setFinancingInfoList(financingInfoList);
            companyAssetService.saveOrUpdate(companyAsset);
        }

        return Result.success("融资信息放款成功");
    }

    @Override
    public Result repay(String id) throws InterruptedException, TimeoutException {
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancingInfo::getId, id);
        FinancingInfo financingInfo = this.getOne(queryWrapper);
        if (financingInfo == null) {
            return Result.error("融资信息不存在");
        }
        // 验证企业资金是否能还款
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getId, BaseContext.getCurrentId());
        User user = userService.getOne(userQueryWrapper);

        String companyId = user.getCompanyId();
        LambdaQueryWrapper<CompanyAsset> companyAssetQueryWrapper = new LambdaQueryWrapper<>();
        companyAssetQueryWrapper.eq(CompanyAsset::getCompanyId, companyId);
        CompanyAsset companyAsset = companyAssetService.getOne(companyAssetQueryWrapper);

        // 根据当前日期计算本金+利息
        int days = (int) ChronoUnit.DAYS.between(financingInfo.getLoanTime().toLocalDate(), LocalDateTime.now().toLocalDate());
        BigDecimal account = financingInfo.getAccount().multiply(BigDecimal.ONE.add(financingInfo.getInterestRate().multiply(BigDecimal.valueOf(days))));
        if (companyAsset.getCash().compareTo(account) < 0) {
            return Result.error("企业资金不足，无法还款");
        }
        // 更新企业资金
        BigDecimal cash = companyAsset.getCash();
        cash = cash.subtract(account);
        companyAsset.setCash(cash);
        companyAssetService.saveOrUpdate(companyAsset);

        // 更新融资信息的状态为已还款
        financingInfo.setIsRepay(true);
        financingInfo.setRepayTime(LocalDateTime.now());
        financingInfo.setRepayAccount(account);

        // 更新资金流水表
        FundFlow fundFlow = new FundFlow();
        fundFlow.setAccount(account);
        fundFlow.setPayer(user.getCompanyId());
        fundFlow.setReceiver(financingInfo.getApprovalId());
        fundFlow.setTradingType(TradingType.FINANCING_REPAYMENT);
        fundFlowService.createFundFlow(fundFlow);

        return Result.success("融资信息还款成功");
    }

    @Override
    public List<FinancingInfo> listByCompanyId(List<String> idList) {
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(FinancingInfo::getCompanyId, idList);
        return this.list(queryWrapper);
    }

    @Override
    public Result deleteByCompanyId(String companyId) throws InterruptedException, TimeoutException {
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancingInfo::getCompanyId, companyId);
        this.remove(queryWrapper);
        // 删除资金流
        fundFlowService.deleteByCompanyId(companyId);
        return Result.success("删除成功");
    }
}
