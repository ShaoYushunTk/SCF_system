package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.constant.CompanyType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.CompanyUpdateDto;
import com.example.entity.*;
import com.example.exception.CommonException;
import com.example.service.*;
import com.example.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/2/26 23:33
 */
@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private SmallMiddleEnterpriseService smallMiddleEnterpriseService;

    @Autowired
    private CoreEnterpriseService coreEnterpriseService;

    @Autowired
    private FinancialInstitutionService financialInstitutionService;

    @Autowired
    private LogisticsProviderService logisticsProviderService;

    @Autowired
    private CompanyAssetService companyAssetService;

    @Autowired
    private UserService userService;

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<? extends Company> findById(
            @PathVariable
            String id
    ) {
        Company company = companyService.getById(id);
        if (company == null) {
            throw new CommonException("企业不存在");
        }
        return switch (company.getCompanyType()) {
            case SMEs -> {
                SmallMiddleEnterprise byId = smallMiddleEnterpriseService.getById(id);
                BeanUtils.copyProperties(company, byId);
                yield Result.success(byId);
            }
            case CORE_ENTERPRISE -> {
                CoreEnterprise byId = coreEnterpriseService.getById(id);
                BeanUtils.copyProperties(company, byId);
                yield Result.success(byId);
            }
            case LOGISTICS_COMPANY -> {
                LogisticsProvider byId = logisticsProviderService.getById(id);
                BeanUtils.copyProperties(company, byId);
                yield Result.success(byId);
            }
            case FINANCIAL_INSTITUTION -> {
                FinancialInstitution byId = financialInstitutionService.getById(id);
                BeanUtils.copyProperties(company, byId);
                yield Result.success(byId);
            }
            default -> Result.error("不支持的企业类型");
        };
    }

    @PostMapping("/save")
    public Result save(
            @RequestBody
            Company company
    ) {
        // 校验同类型下是否同名
        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Company::getCompanyType, company.getCompanyType());
        lambdaQueryWrapper.eq(Company::getName, company.getName());
        Company one = companyService.getOne(lambdaQueryWrapper);
        if (one != null) {
            throw new CommonException("当前类型下已存在同名企业");
        }

        company.setId(UUIDUtils.generate());
        companyService.save(company);
        switch (company.getCompanyType()) {
            case SMEs -> {
                SmallMiddleEnterprise smallMiddleEnterprise = new SmallMiddleEnterprise();
                smallMiddleEnterprise.setId(company.getId());
                smallMiddleEnterpriseService.save(smallMiddleEnterprise);

                CompanyAsset companyAsset = new CompanyAsset();
                companyAsset.setId(UUIDUtils.generate());
                companyAsset.setCompanyId(company.getId());
                companyAsset.setCash(BigDecimal.ZERO);
                companyAssetService.save(companyAsset);
            }
            case CORE_ENTERPRISE -> {
                CoreEnterprise coreEnterprise = new CoreEnterprise();
                coreEnterprise.setId(company.getId());
                coreEnterpriseService.save(coreEnterprise);
                
                CompanyAsset companyAsset = new CompanyAsset();
                companyAsset.setId(UUIDUtils.generate());
                companyAsset.setCompanyId(company.getId());
                companyAsset.setCash(BigDecimal.ZERO);
                companyAssetService.save(companyAsset);
            }
            case LOGISTICS_COMPANY -> {
                LogisticsProvider logisticsProvider = new LogisticsProvider();
                logisticsProvider.setId(company.getId());
                logisticsProviderService.save(logisticsProvider);
            }
            case FINANCIAL_INSTITUTION -> {
                FinancialInstitution financialInstitution = new FinancialInstitution();
                financialInstitution.setId(company.getId());
                financialInstitutionService.save(financialInstitution);
            }
            default -> Result.error("不支持的企业类型");
        };
        return Result.success("企业新建成功");
    }

    @PostMapping("/{id}/updateDetail")
    public Result updateDetail(
            @PathVariable
            String id,
            @RequestBody
            CompanyUpdateDto companyUpdateDto
    ) {
        Company company = companyService.getById(id);
        if (company == null) {
            throw new CommonException("企业不存在");
        }
        return switch (company.getCompanyType()) {
            case SMEs -> smallMiddleEnterpriseService.updateByDto(companyUpdateDto.getSmallMiddleEnterprise(), id);
            case CORE_ENTERPRISE -> coreEnterpriseService.updateByDto(companyUpdateDto.getCoreEnterprise(), id);
            case LOGISTICS_COMPANY -> logisticsProviderService.updateByDto(companyUpdateDto.getLogisticsProvider(), id);
            case FINANCIAL_INSTITUTION -> financialInstitutionService.updateByDto(companyUpdateDto.getFinancialInstitution(), id);
            default -> Result.error("不支持的企业类型");
        };
    }

    @PostMapping("/{id}/updateBasic")
    public Result updateBasic(
            @PathVariable
            String id,
            @RequestBody
            Company company
    ) {
        Company byId = companyService.getById(id);
        if (byId == null) {
            throw new CommonException("企业不存在");
        }
        company.setId(id);
        companyService.updateById(company);
        return Result.success("企业基本信息更新成功");
    }

    @PostMapping("/{id}/delete")
    public Result delete(
            @PathVariable
            String id
    ) {
        Company company = companyService.getById(id);
        if (company == null) {
            throw new CommonException("企业不存在");
        }
        // 查看当前企业是否存在用户，若存在不可删除
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getCompanyId, id);
        User user = userService.getOne(lambdaQueryWrapper);
        if (user != null) {
            throw new CommonException("当前企业存在用户，不可删除");
        }
        // 根据企业类型删除对应表
        // 删除资金流水表和订单表（前端发请求）
        CompanyType companyType = company.getCompanyType();
        switch (companyType) {
            case SMEs -> {
                smallMiddleEnterpriseService.removeByCompanyId(id);
            }
            case FINANCIAL_INSTITUTION -> {
                financialInstitutionService.removeByCompanyId(id);
            }
            case LOGISTICS_COMPANY -> {
                logisticsProviderService.removeByCompanyId(id);
            }
            case CORE_ENTERPRISE -> {
                coreEnterpriseService.removeByCompanyId(id);
            }
            default -> throw new CommonException("不支持的企业类型");
        };

        // 删除资产表
        companyAssetService.removeByCompanyId(id);
        companyService.removeById(id);
        return Result.success("删除成功");
    }


    @GetMapping("/page")
    public Result<Page<Company>> page(
            int page,
            int pageSize,
            String name,
            String companyType
    ) {
        Page<Company> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Company::getName, name);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(companyType), Company::getCompanyType, companyType);

        companyService.page(pageInfo, lambdaQueryWrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/list")
    public Result list(
            String companyType
    ) {
        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(companyType), Company::getCompanyType, companyType);
        List<Company> companyList = companyService.list(lambdaQueryWrapper);
        return Result.success(companyList);
    }
}
