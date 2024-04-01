package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.entity.Company;
import com.example.entity.CoreEnterprise;
import com.example.entity.LogisticsProvider;
import com.example.exception.CommonException;
import com.example.mapper.LogisticsProviderMapper;
import com.example.service.CompanyService;
import com.example.service.LogisticsProviderService;
import com.example.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:48
 */
@Service
public class LogisticsProviderServiceImpl extends ServiceImpl<LogisticsProviderMapper, LogisticsProvider> implements LogisticsProviderService {
    @Autowired
    private LogisticsProviderMapper logisticsProviderMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CommonUtils commonUtils;
    @Override
    public LogisticsProvider getById(String id) {
        QueryWrapper<LogisticsProvider> queryWrapper = new QueryWrapper<>();

        // 排除父类的列
        queryWrapper.select(LogisticsProvider.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn())
                        && !"created_by".equals(info.getColumn()) && !"created_time".equals(info.getColumn())
                        && !"updated_by".equals(info.getColumn()) && !"updated_time".equals(info.getColumn()))
                .eq("id", id);

        return logisticsProviderMapper.selectOne(queryWrapper);
    }

    @Override
    public Result updateByDto(
            LogisticsProvider logisticsProvider,
            String id
    ) {
        LambdaQueryWrapper<LogisticsProvider> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LogisticsProvider::getId, id);
        logisticsProviderMapper.update(logisticsProvider, lambdaQueryWrapper);
        return Result.success("物流公司更新成功");
    }

    @Override
    public Boolean removeByCompanyId(String id) {
        LogisticsProvider byId = this.getById(id);
        if (byId == null) {
            throw new CommonException("物流公司不存在");
        }
        return this.removeById(id);
    }

    @Override
    public Result page(
            int page,
            int pageSize,
            String name
    ) {
        Page<LogisticsProvider> logisticsProviderPage = new Page<>(page, pageSize);
        commonUtils.customPage(logisticsProviderPage, new LambdaQueryWrapper<>(), this.entityClass, this.getClass());

        List<LogisticsProvider> records = logisticsProviderPage.getRecords().stream().peek((it) -> {
            Company byId = companyService.getById(it.getId());
            BeanUtils.copyProperties(byId, it);
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getName().matches(regex)).collect(Collectors.toList());
        }
        logisticsProviderPage.setRecords(records);
        logisticsProviderPage.setTotal(records.size());
        return Result.success(logisticsProviderPage);
    }

    public void customPage(Page<LogisticsProvider> page, LambdaQueryWrapper<LogisticsProvider> queryWrapper) {
        // 在外部对 queryWrapper 进行定制化处理
        queryWrapper.select(LogisticsProvider.class, info ->
                !"name".equals(info.getColumn()) &&
                        !"company_type".equals(info.getColumn()) &&
                        !"contact_info".equals(info.getColumn())
        );

        // 调用 MyBatis Plus 提供的 page 方法执行查询并返回结果
        this.page(page, queryWrapper);
    }
}
