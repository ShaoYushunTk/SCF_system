package com.example.dto;

import com.example.entity.SupplyChain;
import lombok.Data;

import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/3/21 0:30
 */
@Data
public class SupplyChainDto extends SupplyChain {
    private String coreEnterpriseName;
    private List<String> smesNameList;
    private List<String> financialInstitutionNameList;
    private List<String> logisticsCompanyNameList;
}
