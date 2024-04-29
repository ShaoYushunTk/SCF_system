package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.BaseContext;
import com.example.common.Result;
import com.example.constant.ApprovalStatus;
import com.example.constant.CompanyType;
import com.example.constant.TradingType;
import com.example.dto.FinancingInfoDto;
import com.example.entity.*;
import com.example.exception.CommonException;
import com.example.mapper.FinancingInfoMapper;
import com.example.service.*;
import com.example.utils.CommonUtils;
import com.example.utils.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/3/6 14:58
 */
@Service
public class FinancingInfoServiceImpl extends ServiceImpl<FinancingInfoMapper, FinancingInfo> implements FinancingInfoService {
    @Autowired
    private CommonUtils commonUtils;

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
        financingInfo.setCompanyId(commonUtils.getCompanyIdByCurrentUser());
        financingInfo.setApplyTime(LocalDateTime.now());
        financingInfo.setApprovalStatus(ApprovalStatus.PENDING);

        // 根据企业信用等级设置利率
        String companyId = financingInfo.getCompanyId();
        Company company = companyService.getById(companyId);
        FinancialInstitution financialInstitution = financialInstitutionService.getById(financingInfo.getFinancialInstitutionId());

        if (company.getCompanyType() == CompanyType.CORE_ENTERPRISE) {
            CoreEnterprise coreEnterprise = coreEnterpriseService.getById(companyId);
            financingInfo.setInterestRate(BigDecimal.valueOf(financialInstitution.getLoanInterestRate().getOrDefault(coreEnterprise.getCreditRating(), 0.0)));
        } else if (company.getCompanyType() == CompanyType.SMEs) {
            SmallMiddleEnterprise smallMiddleEnterprise = smallMiddleEnterpriseService.getById(companyId);
            CoreEnterprise coreEnterprise = coreEnterpriseService.getById(smallMiddleEnterprise.getCoreEnterpriseId());
            if (coreEnterprise == null) {
                throw new CommonException("当前企业未关联核心企业，无法申请融资");
            }
            financingInfo.setInterestRate(BigDecimal.valueOf(financialInstitution.getLoanInterestRate().getOrDefault(coreEnterprise.getCreditRating(), 0.0)));
        } else {
            throw new CommonException("当前企业不支持融资");
        }
        if (financingInfo.getInterestRate().equals(BigDecimal.ZERO)) {
            throw new CommonException("当前选择的金融机构不支持此信用等级的融资");
        }

        this.save(financingInfo);
        return Result.success("融资信息新建成功");
    }

    @Override
    public Result approve(
            String id,
            String approvalStatus,
            String approvalComment
    ) {
        FinancingInfo financingInfo = this.getById(id);
        if (financingInfo == null) {
            return Result.error("融资信息不存在");
        }
        // 更新融资信息的状态和审批意见
        financingInfo.setApprovalId(BaseContext.getCurrentId());
        financingInfo.setApprovalComment(approvalComment);
        financingInfo.setApprovalStatus(ApprovalStatus.valueOf(approvalStatus));
        financingInfo.setApprovalTime(LocalDateTime.now());
        this.saveOrUpdate(financingInfo);
        return Result.success("融资信息审批成功");
    }

    @Override
    @Transactional
    public Result loan(
            String id
    ) throws InterruptedException, TimeoutException {
        FinancingInfo financingInfo = this.getById(id);
        if (financingInfo == null) {
            throw new CommonException("融资信息不存在");
        }
        // 更新融资信息的状态为已放款
        financingInfo.setLoanTime(LocalDateTime.now());
        financingInfo.setIsRepay(false);
        this.saveOrUpdate(financingInfo);
        // 更新资金流水表
        String companyId = financingInfo.getCompanyId();
        FundFlow fundFlow = new FundFlow();
        fundFlow.setAmount(financingInfo.getAmount());
        fundFlow.setPayer(financingInfo.getFinancialInstitutionId());
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
            companyAsset.setCash(financingInfo.getAmount());
            companyAsset.setFinancingInfoList(new ArrayList<>(Collections.singletonList(financingInfo)));
            companyAsset.setOrderAssets(new ArrayList<>());
            companyAssetService.saveOrUpdate(companyAsset);
        } else {
            // 更新企业资金信息
            BigDecimal cash = companyAsset.getCash();
            cash = cash.add(financingInfo.getAmount());
            companyAsset.setCash(cash);
            List<FinancingInfo> financingInfoList = companyAsset.getFinancingInfoList();
            if (financingInfoList == null) {
                financingInfoList = new ArrayList<>();
            }
            financingInfoList.add(financingInfo);
            companyAsset.setFinancingInfoList(financingInfoList);
            companyAssetService.saveOrUpdate(companyAsset);
        }

        return Result.success("融资信息放款成功");
    }

    @Override
    @Transactional
    public Result repay(
            String id
    ) throws InterruptedException, TimeoutException {
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancingInfo::getId, id);
        FinancingInfo financingInfo = this.getOne(queryWrapper);
        if (financingInfo == null) {
            return Result.error("融资信息不存在");
        }
        // 验证企业资金是否能还款
        String companyId = commonUtils.getCompanyIdByCurrentUser();
        LambdaQueryWrapper<CompanyAsset> companyAssetQueryWrapper = new LambdaQueryWrapper<>();
        companyAssetQueryWrapper.eq(CompanyAsset::getCompanyId, companyId);
        CompanyAsset companyAsset = companyAssetService.getOne(companyAssetQueryWrapper);

        // 根据当前日期计算本金+利息
        int days = (int) ChronoUnit.DAYS.between(financingInfo.getLoanTime().toLocalDate(), LocalDateTime.now().toLocalDate());
        // 将年利率转换为日利率
        BigDecimal dailyInterestRate = financingInfo.getInterestRate().divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);
        BigDecimal amount = financingInfo.getAmount().multiply(BigDecimal.ONE.add(dailyInterestRate.multiply(BigDecimal.valueOf(days))));
        if (companyAsset.getCash().compareTo(amount) < 0) {
            return Result.error("企业资金不足，无法还款");
        }
        // 更新企业资金
        BigDecimal cash = companyAsset.getCash();
        cash = cash.subtract(amount);
        companyAsset.setCash(cash);
        companyAssetService.saveOrUpdate(companyAsset);

        // 更新融资信息的状态为已还款
        financingInfo.setIsRepay(true);
        financingInfo.setRepayTime(LocalDateTime.now());
        financingInfo.setRepayAmount(amount);
        this.saveOrUpdate(financingInfo);

        // 更新资金流水表
        FundFlow fundFlow = new FundFlow();
        fundFlow.setAmount(amount);
        fundFlow.setPayer(companyId);
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
    public Result deleteByCompanyId(
            String companyId
    ) throws InterruptedException, TimeoutException {
        Company byId = companyService.getById(companyId);
        if (byId.getCompanyType() == CompanyType.LOGISTICS_COMPANY || byId.getCompanyType() == CompanyType.FINANCIAL_INSTITUTION) {
            return Result.success("");
        }
        LambdaQueryWrapper<FinancingInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancingInfo::getCompanyId, companyId);
        this.remove(queryWrapper);
        // 删除资金流
        fundFlowService.deleteByCompanyId(companyId);
        return Result.success("删除成功");
    }

    @Override
    public Result page(
            int page,
            int pageSize,
            String companyName,
            String approvalStatus,
            String companyType
    ) {
        Page<FinancingInfo> pageInfo = new Page<>(page, pageSize);
        Page<FinancingInfoDto> dtoPage = new Page<>(page, pageSize);
        this.page(pageInfo, new LambdaQueryWrapper<FinancingInfo>());

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<FinancingInfoDto> records = pageInfo.getRecords().stream().map((it) -> {
            FinancingInfoDto dto = new FinancingInfoDto();
            BeanUtils.copyProperties(it, dto);
            dto.setCompanyName(companyService.getById(it.getCompanyId()).getName());
            dto.setFinancialInstitutionName(companyService.getById(it.getFinancialInstitutionId()).getName());
            if (it.getApprovalStatus() == ApprovalStatus.PENDING) {
                dto.setApprovalName("待审批");
            } else {
                dto.setApprovalName(commonUtils.getUserNameById(it.getApprovalId()));
            }
            return dto;
        }).toList();

        if (StringUtils.isNotEmpty(companyName)) {
            String regex = ".*" + Pattern.quote(companyName) + ".*";
            records = records.stream().filter(dto -> dto.getCompanyName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(approvalStatus)) {
            records = records.stream().filter(dto -> dto.getApprovalStatus().toString().equals(approvalStatus)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(companyType)) {
            records = records.stream().filter(dto -> companyService.getById(dto.getCompanyId()).getCompanyType().toString().equals(companyType)).collect(Collectors.toList());
        }
        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }

    @Override
    public Result getFinancingInfoById(String id) {
        FinancingInfo financingInfo = this.getById(id);
        if (financingInfo == null) {
            throw new CommonException("融资信息不存在");
        }
        FinancingInfoDto financingInfoDto = new FinancingInfoDto();
        BeanUtils.copyProperties(financingInfo, financingInfoDto);
        financingInfoDto.setCompanyName(companyService.getById(financingInfo.getCompanyId()).getName());
        financingInfoDto.setFinancialInstitutionName(companyService.getById(financingInfo.getFinancialInstitutionId()).getName());
        return Result.success(financingInfoDto);
    }

    @Override
    public Result deleteFinancingInfoById(String id) {
        if (this.getById(id) == null) {
            throw new CommonException("融资信息不存在");
        }
        this.removeById(id);
        return Result.success("融资信息删除成功");
    }

    @Override
    public Result getFinancingInfoByCompanyId(
            String id,
            int page,
            int pageSize
    ) {
        Page<FinancingInfo> pageInfo = new Page<>(page, pageSize);
        Page<FinancingInfoDto> dtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinancingInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FinancingInfo::getCompanyId, id);
        lambdaQueryWrapper.orderByDesc(FinancingInfo::getApplyTime);
        this.page(pageInfo, lambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<FinancingInfoDto> records = pageInfo.getRecords().stream().map((it) -> {
            FinancingInfoDto dto = new FinancingInfoDto();
            BeanUtils.copyProperties(it, dto);
            dto.setCompanyName(companyService.getById(it.getCompanyId()).getName());
            dto.setFinancialInstitutionName(companyService.getById(it.getFinancialInstitutionId()).getName());
            if (it.getApprovalStatus() == ApprovalStatus.PENDING) {
                dto.setApprovalName("待审批");
            } else {
                dto.setApprovalName(commonUtils.getUserNameById(it.getApprovalId()));
            }
            return dto;
        }).toList();

        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }

    @Override
    public Result getFinancingInfoByFinancialInstitutionId(String id, int page, int pageSize) {
        Page<FinancingInfo> pageInfo = new Page<>(page, pageSize);
        Page<FinancingInfoDto> dtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinancingInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FinancingInfo::getFinancialInstitutionId, id);
        lambdaQueryWrapper.orderByDesc(FinancingInfo::getApplyTime);
        this.page(pageInfo, lambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<FinancingInfoDto> records = pageInfo.getRecords().stream().map((it) -> {
            FinancingInfoDto dto = new FinancingInfoDto();
            BeanUtils.copyProperties(it, dto);
            dto.setCompanyName(companyService.getById(it.getCompanyId()).getName());
            dto.setFinancialInstitutionName(companyService.getById(it.getFinancialInstitutionId()).getName());
            if (it.getApprovalStatus() == ApprovalStatus.PENDING) {
                dto.setApprovalName("待审批");
            } else {
                dto.setApprovalName(commonUtils.getUserNameById(it.getApprovalId()));
            }
            return dto;
        }).toList();

        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }
}
