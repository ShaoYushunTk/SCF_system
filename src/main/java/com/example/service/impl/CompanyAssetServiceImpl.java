package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.dto.CompanyAssetDto;
import com.example.entity.Company;
import com.example.entity.CompanyAsset;
import com.example.entity.FinancingInfo;
import com.example.entity.Orders;
import com.example.mapper.CompanyAssetMapper;
import com.example.service.CompanyAssetService;
import com.example.service.CompanyService;
import com.example.service.FinancingInfoService;
import com.example.service.OrdersService;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yushun Shao
 * @date 2024/3/12 15:32
 */
@Service
public class CompanyAssetServiceImpl extends ServiceImpl<CompanyAssetMapper, CompanyAsset> implements CompanyAssetService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Result page(
            int page,
            int pageSize,
            String companyId
    ) {

        Page<CompanyAsset> companyAssetPage = new Page<>(page, pageSize);
        Page<CompanyAssetDto> companyAssetDtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<CompanyAsset> companyAssetLambdaQueryWrapper = new LambdaQueryWrapper<>();
        companyAssetLambdaQueryWrapper.eq(StringUtils.isNotEmpty(companyId), CompanyAsset::getCompanyId, companyId);
        this.page(companyAssetPage, companyAssetLambdaQueryWrapper);

        BeanUtils.copyProperties(companyAssetPage, companyAssetDtoPage, "records");
        List<String> idList = companyAssetPage.getRecords().stream().map(CompanyAsset::getCompanyId).toList();

        List<Company> companyList = companyService.listByIds(idList);
        Map<String, Company> companyMap = new HashMap<>();
        for (Company entity : companyList) {
            companyMap.put(String.valueOf(entity.getId()), entity);
        }

        companyAssetDtoPage.setRecords(companyAssetPage.getRecords().stream().map((it) -> {
            CompanyAssetDto companyAssetDto = new CompanyAssetDto();
            BeanUtils.copyProperties(it, companyAssetDto);
            companyAssetDto.setCompanyName(companyMap.get(it.getCompanyId()).getName());
            return companyAssetDto;
        }).toList());

        return Result.success(companyAssetDtoPage);
    }

    @Override
    public void removeByCompanyId(String id) {
        LambdaQueryWrapper<CompanyAsset> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CompanyAsset::getCompanyId, id);
        this.remove(lambdaQueryWrapper);
    }
}
