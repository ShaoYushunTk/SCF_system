package com.example.dto;

import com.example.entity.FinancingInfo;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/25 10:06
 */
@Data
public class FinancingInfoDto extends FinancingInfo {
    private String companyName;
    private String financialInstitutionName;
    private String approvalName;
}
