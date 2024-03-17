package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.entity.CoreEnterprise;
import com.example.entity.FinancialInstitution;
import com.example.exception.CommonException;
import com.example.mapper.CoreEnterpriseMapper;
import com.example.mapper.FinancialInstitutionMapper;
import com.example.service.FinancialInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:42
 */
@Service
public class FinancialInstitutionServiceImpl extends ServiceImpl<FinancialInstitutionMapper, FinancialInstitution> implements FinancialInstitutionService {
    @Autowired
    private FinancialInstitutionMapper financialInstitutionMapper;
    @Override
    public FinancialInstitution getById(String id) {
        QueryWrapper<FinancialInstitution> queryWrapper = new QueryWrapper<>();

        // 排除父类的列
        queryWrapper.select(FinancialInstitution.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn())
                        && !"created_by".equals(info.getColumn()) && !"created_time".equals(info.getColumn())
                        && !"updated_by".equals(info.getColumn()) && !"updated_time".equals(info.getColumn()))
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
    public Result getFinancialInstitutionList() {
        return Result.success(this.list());
    }

    @Override
    public Boolean removeByCompanyId(String id) {
        FinancialInstitution byId = this.getById(id);
        if (byId == null) {
            throw new CommonException("金融机构不存在");
        }
        return this.removeById(id);
    }
}
