package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.entity.CoreEnterprise;
import com.example.entity.SmallMiddleEnterprise;
import com.example.exception.CommonException;
import com.example.mapper.SmallMiddleEnterpriseMapper;
import com.example.service.FinancialInstitutionService;
import com.example.service.FinancingInfoService;
import com.example.service.OrdersService;
import com.example.service.SmallMiddleEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:44
 */
@Service
public class SmallMiddleEnterpriseServiceImpl extends ServiceImpl<SmallMiddleEnterpriseMapper, SmallMiddleEnterprise> implements SmallMiddleEnterpriseService {
    @Autowired
    private SmallMiddleEnterpriseMapper smallMiddleEnterpriseMapper;

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
}
