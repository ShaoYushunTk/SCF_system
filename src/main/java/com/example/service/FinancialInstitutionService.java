package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.CoreEnterprise;
import com.example.entity.FinancialInstitution;

import javax.swing.plaf.basic.BasicOptionPaneUI;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:42
 */
public interface FinancialInstitutionService extends IService<FinancialInstitution> {
    public FinancialInstitution getById(String id);

    Result updateByDto(FinancialInstitution financialInstitution, String id);

    Result getFinancialInstitutionList();

    Boolean removeByCompanyId(String id);
}