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

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取详细信息
     * @param id
     * @param companyType
     * @return
     */
    @GetMapping("/{id}")
    public Result<? extends Company> findById(
            @PathVariable
            String id,
            CompanyType companyType
    ) {
        Company company = companyService.getById(id);
        if (company == null) {
            return Result.error("Company not found");
        }
        return switch (companyType) {
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
            default -> Result.error("Unsupported company type");
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
            return Result.error("当前类型下已存在同名企业");
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
            default -> Result.error("Unsupported company type");
        };
        return Result.success("企业新建成功");
    }

    @PostMapping("/{id}/update")
    public Result update(
            @PathVariable
            String id,
            @RequestBody
            CompanyUpdateDto companyUpdateDto
    ) {
        Company company = companyService.getById(id);
        if (company == null) {
            return Result.error("Company not found");
        }
        return switch (company.getCompanyType()) {
            case SMEs -> smallMiddleEnterpriseService.updateByDto(companyUpdateDto.getSmallMiddleEnterprise(), id);
            case CORE_ENTERPRISE -> coreEnterpriseService.updateByDto(companyUpdateDto.getCoreEnterprise(), id);
            case LOGISTICS_COMPANY -> logisticsProviderService.updateByDto(companyUpdateDto.getLogisticsProvider(), id);
            case FINANCIAL_INSTITUTION -> financialInstitutionService.updateByDto(companyUpdateDto.getFinancialInstitution(), id);
            default -> Result.error("Unsupported company type");
        };
    }

    @PostMapping("/{id}/delete")
    public Result delete(
            @PathVariable
            String id
    ) {
        Company company = companyService.getById(id);
        if (company == null) {
            return Result.error("Company not found");
        }
        // 查看当前企业是否存在用户，若存在不可删除
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getCompanyId, id);
        User user = userService.getOne(lambdaQueryWrapper);
        if (user != null) {
            return Result.error("Company has users, cannot be deleted");
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
            default -> throw new CommonException("Unsupported company type");
        };

        // 删除资产表
        companyAssetService.removeByCompanyId(id);
        companyService.removeById(id);
        return Result.success("deleted successfully");
    }


    @GetMapping("/page")
    public Result<Page<Company>> page(
            int page,
            int pageSize,
            CompanyType companyType
    ) {
        Page<Company> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(companyType.toString()), Company::getCompanyType, companyType);

        companyService.page(pageInfo, lambdaQueryWrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/list")
    public Result list(
            CompanyType companyType
    ) {
        String redisKey = "Company" + "_" + companyType.toString() + "_" + "List";
        List<Company> list = (List<Company>) redisTemplate.opsForValue().get(redisKey);
        if (list != null) {
            return Result.success(list);
        }

        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(companyType.toString()), Company::getCompanyType, companyType);
        List<Company> companyList = companyService.list(lambdaQueryWrapper);
        redisTemplate.opsForValue().set(redisKey, companyList, 60, TimeUnit.MINUTES);
        return Result.success(companyList);
    }
}
