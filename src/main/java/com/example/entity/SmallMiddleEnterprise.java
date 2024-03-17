package com.example.entity;

import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/2/24 14:20
 */
@Data
public class SmallMiddleEnterprise extends Company {
    // 企业所属行业
    private String enterpriseIndustry;
    // 银行卡号
    private String bankNumber;

    private String coreEnterpriseId;
}
