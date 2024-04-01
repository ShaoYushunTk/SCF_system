package com.example.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.constant.CompanyType;
import com.example.dto.SupplyChainDto;
import com.example.entity.*;
import com.example.mapper.SupplyChainMapper;
import com.example.service.*;
import com.example.utils.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/3/20 23:56
 */
@Service
public class SupplyChainServiceImpl extends ServiceImpl<SupplyChainMapper, SupplyChain> implements SupplyChainService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SmallMiddleEnterpriseService smallMiddleEnterpriseService;

    @Autowired
    private FinancingInfoService financingInfoService;

    @Autowired
    private OrdersService ordersService;
    @Override
    public Result page(
            int page,
            int pageSize,
            String name
    ) {
        Page<SupplyChain> pageInfo = new Page<>(page, pageSize);
        Page<SupplyChainDto> dtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Company> companyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        companyLambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Company::getName, name);
        companyLambdaQueryWrapper.eq(Company::getCompanyType, CompanyType.CORE_ENTERPRISE);
        List<Company> companyList = companyService.list(companyLambdaQueryWrapper);

        LambdaQueryWrapper<SupplyChain> supplyChainLambdaQueryWrapper = new LambdaQueryWrapper<>();
        supplyChainLambdaQueryWrapper.in(SupplyChain::getCoreEnterpriseId, companyList.stream().map(Company::getId).distinct().toList());
        this.page(pageInfo, supplyChainLambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<SupplyChainDto> supplyChainDtos = this.list(supplyChainLambdaQueryWrapper).stream().map((it) -> {
            SupplyChainDto supplyChainDto = new SupplyChainDto();
            BeanUtils.copyProperties(it, supplyChainDto);

            // 设置企业名称
            Company company = companyService.getById(it.getCoreEnterpriseId());
            supplyChainDto.setCoreEnterpriseName(company.getName());

            List<String> smesNameList = new ArrayList<>();
            if (it.getSmesList() != null && !it.getSmesList().isEmpty()) {
                smesNameList = it.getSmesList().stream()
                        .map((smesId) -> companyService.getById(smesId))
                        .map(Company::getName)
                        .toList();
            }
            supplyChainDto.setSmesNameList(smesNameList);

            List<String> financialInstitutionNameList = new ArrayList<>();
            if (it.getFinancialInstitutionList() != null && !it.getFinancialInstitutionList().isEmpty()) {
                financialInstitutionNameList = it.getFinancialInstitutionList().stream()
                        .map((fiId) -> companyService.getById(fiId))
                        .map(Company::getName)
                        .toList();
            }
            supplyChainDto.setFinancialInstitutionNameList(financialInstitutionNameList);

            List<String> logisticsCompanyNameList = new ArrayList<>();
            if (it.getLogisticsCompanyList() != null && !it.getLogisticsCompanyList().isEmpty()) {
                logisticsCompanyNameList = it.getLogisticsCompanyList().stream()
                        .map((lcId) -> companyService.getById(lcId))
                        .map(Company::getName)
                        .toList();
            }
            supplyChainDto.setLogisticsCompanyNameList(logisticsCompanyNameList);

            return supplyChainDto;
        }).toList();

        dtoPage.setRecords(supplyChainDtos);
        return Result.success(dtoPage);
    }

    @Override
    public Result init() {
        LambdaQueryWrapper<Company> companyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        companyLambdaQueryWrapper.eq(Company::getCompanyType, CompanyType.CORE_ENTERPRISE);
        List<Company> coreEnterpriseList = companyService.list(companyLambdaQueryWrapper);
        for (Company coreEnterprise : coreEnterpriseList) {
             SupplyChain supplyChain = new SupplyChain();
             supplyChain.setId(UUIDUtils.generate());
             supplyChain.setCoreEnterpriseId(coreEnterprise.getId());

             LambdaQueryWrapper<SmallMiddleEnterprise> smallMiddleEnterpriseLambdaQueryWrapper = new LambdaQueryWrapper<>();
             smallMiddleEnterpriseLambdaQueryWrapper.eq(SmallMiddleEnterprise::getCoreEnterpriseId, coreEnterprise.getId());
             List<SmallMiddleEnterprise> smallMiddleEnterprises = smallMiddleEnterpriseService.list(smallMiddleEnterpriseLambdaQueryWrapper);
             if (smallMiddleEnterprises.isEmpty()) {
                 continue;
             }
             supplyChain.setSmesList(smallMiddleEnterprises.stream().map(Company::getId).distinct().toList());

            List<String> companyList = smallMiddleEnterprises.stream()
                    .map(Company::getId)
                    .distinct()
                    .collect(Collectors.toList());

            companyList.add(coreEnterprise.getId());

             LambdaQueryWrapper<FinancingInfo> financingInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
             financingInfoLambdaQueryWrapper.in(FinancingInfo::getCompanyId, companyList);
             List<FinancingInfo> financingInfos = financingInfoService.list(financingInfoLambdaQueryWrapper);
             supplyChain.setFinancialInstitutionList(financingInfos.stream().map(FinancingInfo::getFinancialInstitutionId).distinct().toList());

             LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
             ordersLambdaQueryWrapper.in(Orders::getPayer, companyList)
                     .or()
                     .in(Orders::getReceiver, companyList);
            List<Orders> orders = ordersService.list(ordersLambdaQueryWrapper);
            supplyChain.setLogisticsCompanyList(orders.stream().map(Orders::getLogisticsProviderId).distinct().toList());

            this.saveOrUpdate(supplyChain);
        }
        return Result.success("初始化成功");
    }


}
