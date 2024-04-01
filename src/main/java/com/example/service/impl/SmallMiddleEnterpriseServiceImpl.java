package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.dto.SmallMiddleEnterpriseDto;
import com.example.entity.Company;
import com.example.entity.CoreEnterprise;
import com.example.entity.SmallMiddleEnterprise;
import com.example.exception.CommonException;
import com.example.mapper.SmallMiddleEnterpriseMapper;
import com.example.service.*;
import com.example.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:44
 */
@Service
public class SmallMiddleEnterpriseServiceImpl extends ServiceImpl<SmallMiddleEnterpriseMapper, SmallMiddleEnterprise> implements SmallMiddleEnterpriseService {
    @Autowired
    private SmallMiddleEnterpriseMapper smallMiddleEnterpriseMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CommonUtils commonUtils;

    @Override
    public SmallMiddleEnterprise getById(String id) {
        QueryWrapper<SmallMiddleEnterprise> queryWrapper = new QueryWrapper<>();

        // 排除父类的列
        queryWrapper.select(SmallMiddleEnterprise.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()))
                .eq("id", id);

        return smallMiddleEnterpriseMapper.selectOne(queryWrapper);
    }

    @Override
    public SmallMiddleEnterprise getOne(LambdaQueryWrapper<SmallMiddleEnterprise> lambdaQueryWrapper) {
        lambdaQueryWrapper.select(SmallMiddleEnterprise.class, info -> !"name".equals(info.getColumn())
                && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()));
        return smallMiddleEnterpriseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public List<SmallMiddleEnterprise> list(LambdaQueryWrapper<SmallMiddleEnterprise> lambdaQueryWrapper) {
        // 排除父类的列
        lambdaQueryWrapper.select(SmallMiddleEnterprise.class, info -> !"name".equals(info.getColumn())
                && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()));

        return smallMiddleEnterpriseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Result updateByDto(
            SmallMiddleEnterprise smallMiddleEnterprise,
            String id
    ) {
        LambdaQueryWrapper<SmallMiddleEnterprise> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SmallMiddleEnterprise::getId, id);
        smallMiddleEnterpriseMapper.update(smallMiddleEnterprise, lambdaQueryWrapper);
        return Result.success("中小企业更新成功");
    }

    @Override
    public boolean removeByCompanyId(String id) {
        SmallMiddleEnterprise byId = this.getById(id);
        if (byId == null) {
            throw new CommonException("中小企业不存在");
        }
        return this.removeById(id);
    }

    @Override
    public Result page(
            int page,
            int pageSize,
            String name,
            String coreEnterpriseName
    ) {
        Page<SmallMiddleEnterprise> smallMiddleEnterprisePage = new Page<>(page, pageSize);
        Page<SmallMiddleEnterpriseDto> dtoPage = new Page<>(page, pageSize);
        commonUtils.customPage(smallMiddleEnterprisePage, new LambdaQueryWrapper<SmallMiddleEnterprise>(), this.entityClass, this.getClass());

        BeanUtils.copyProperties(smallMiddleEnterprisePage, dtoPage, "records");

        List<SmallMiddleEnterpriseDto> records = smallMiddleEnterprisePage.getRecords().stream().map((it) -> {
            Company company = companyService.getById(it.getId());
            BeanUtils.copyProperties(company, it);
            SmallMiddleEnterpriseDto dto = new SmallMiddleEnterpriseDto();
            BeanUtils.copyProperties(it, dto);
            dto.setCoreEnterpriseName(companyService.getById(it.getCoreEnterpriseId()).getName());
            return dto;
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(coreEnterpriseName)) {
            String regex = ".*" + Pattern.quote(coreEnterpriseName) + ".*";
            records = records.stream().filter(dto -> dto.getCoreEnterpriseName().matches(regex)).collect(Collectors.toList());
        }
        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }
}
