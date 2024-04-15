package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.entity.Company;
import com.example.entity.CoreEnterprise;
import com.example.entity.FinancialInstitution;
import com.example.exception.CommonException;
import com.example.mapper.CoreEnterpriseMapper;
import com.example.mapper.FinancialInstitutionMapper;
import com.example.service.CompanyService;
import com.example.service.FinancialInstitutionService;
import com.example.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:42
 */
@Service
public class FinancialInstitutionServiceImpl extends ServiceImpl<FinancialInstitutionMapper, FinancialInstitution> implements FinancialInstitutionService {
    @Autowired
    private FinancialInstitutionMapper financialInstitutionMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CommonUtils commonUtils;

    @Override
    public FinancialInstitution getById(String id) {
        QueryWrapper<FinancialInstitution> queryWrapper = new QueryWrapper<>();

        // 排除父类的列
        queryWrapper.select(FinancialInstitution.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()))
                .eq("id", id);

        return financialInstitutionMapper.selectOne(queryWrapper);
    }

    @Override
    public Result updateByDto(
            FinancialInstitution financialInstitution,
            String id
    ) {
        LambdaQueryWrapper<FinancialInstitution> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FinancialInstitution::getId, id);
        financialInstitutionMapper.update(financialInstitution, lambdaQueryWrapper);
        return Result.success("金融机构更新成功");
    }

    @Override
    public List<FinancialInstitution> customList() {
        QueryWrapper<FinancialInstitution> queryWrapper = new QueryWrapper<>();

        // 排除父类的列
        queryWrapper.select(FinancialInstitution.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()));

        return this.list(queryWrapper);
    }

    @Override
    public Result getFinancialInstitutionList() {
        List<FinancialInstitution> financialInstitutions = this.customList();
        financialInstitutions.forEach((it) -> {
            Company byId = companyService.getById(it.getId());
            BeanUtils.copyProperties(byId, it);
        });
        return Result.success(financialInstitutions);
    }

    @Override
    public Boolean removeByCompanyId(String id) {
        FinancialInstitution byId = this.getById(id);
        if (byId == null) {
            throw new CommonException("金融机构不存在");
        }
        return this.removeById(id);
    }

    @Override
    public Result page(
            int page,
            int pageSize,
            String name,
            String financialInstitutionType
    ) {
        Page<FinancialInstitution> financialInstitutionPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<FinancialInstitution> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(FinancialInstitution::getUpdatedTime);
        commonUtils.customPage(financialInstitutionPage, lambdaQueryWrapper, this.entityClass, this.getClass());

        List<FinancialInstitution> records = financialInstitutionPage.getRecords().stream().peek((it) -> {
            Company company = companyService.getById(it.getId());
            BeanUtils.copyProperties(company, it);
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(financialInstitutionType)) {
            records = records.stream().filter(dto -> dto.getFinancialInstitutionType().toString().equals(financialInstitutionType)).collect(Collectors.toList());
        }
        financialInstitutionPage.setRecords(records);
        financialInstitutionPage.setTotal(records.size());
        return Result.success(financialInstitutionPage);
    }
}
