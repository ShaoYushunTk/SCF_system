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
import com.example.exception.CommonException;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            String name,
            String companyType
    ) {
        Page<CompanyAsset> companyAssetPage = new Page<>(page, pageSize);
        Page<CompanyAssetDto> companyAssetDtoPage = new Page<>(page, pageSize);
        this.page(companyAssetPage, new LambdaQueryWrapper<CompanyAsset>());

        BeanUtils.copyProperties(companyAssetPage, companyAssetDtoPage, "records");
        List<CompanyAssetDto> records = companyAssetPage.getRecords().stream().map((it) -> {
            CompanyAssetDto companyAssetDto = new CompanyAssetDto();
            BeanUtils.copyProperties(it, companyAssetDto);
            Company byId = companyService.getById(it.getCompanyId());
            companyAssetDto.setCompanyName(byId.getName());
            companyAssetDto.setCompanyType(byId.getCompanyType());
            return companyAssetDto;
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getCompanyName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(companyType)) {
            records = records.stream().filter(dto -> dto.getCompanyType().toString().equals(companyType)).collect(Collectors.toList());
        }
        companyAssetDtoPage.setRecords(records);
        companyAssetDtoPage.setTotal(records.size());
        return Result.success(companyAssetDtoPage);
    }

    @Override
    public void removeByCompanyId(String id) {
        LambdaQueryWrapper<CompanyAsset> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CompanyAsset::getCompanyId, id);
        this.remove(lambdaQueryWrapper);
    }

    @Override
    public Result addCash(
            String id,
            BigDecimal addCash
    ) {
        CompanyAsset companyAsset = this.getById(id);
        if (companyAsset == null) {
            throw new CommonException("企业资产不存在");
        }
        BigDecimal cash = companyAsset.getCash();
        cash = cash.add(addCash);
        companyAsset.setCash(cash);
        this.updateById(companyAsset);
        return Result.success("企业资产增加成功");
    }

    @Override
    public Result getCompanyAssetById(String id) {
        CompanyAsset companyAsset = this.getById(id);
        if (companyAsset == null) {
            throw new CommonException("企业资产不存在");
        }
        return Result.success(companyAsset);
    }
}
