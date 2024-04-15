package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.CoreEnterprise;
import com.example.entity.FinancialInstitution;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:42
 */
public interface FinancialInstitutionService extends IService<FinancialInstitution> {
    public FinancialInstitution getById(String id);

    Result updateByDto(FinancialInstitution financialInstitution, String id);

    public List<FinancialInstitution> customList();

    Result getFinancialInstitutionList();

    Boolean removeByCompanyId(String id);

    Result page(int page, int pageSize, String name, String financialInstitutionType);
}
