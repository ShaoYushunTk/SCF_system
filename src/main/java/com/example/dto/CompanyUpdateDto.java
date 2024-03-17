package com.example.dto;

import com.example.entity.CoreEnterprise;
import com.example.entity.FinancialInstitution;
import com.example.entity.LogisticsProvider;
import com.example.entity.SmallMiddleEnterprise;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/14 14:10
 */
@Data
public class CompanyUpdateDto {
    private CoreEnterprise coreEnterprise;
    private FinancialInstitution financialInstitution;
    private LogisticsProvider logisticsProvider;
    private SmallMiddleEnterprise smallMiddleEnterprise;
}
