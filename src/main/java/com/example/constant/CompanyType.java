package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/2/20 17:26
 */
public enum CompanyType {
    /**
     *
     */
    SYSTEM_ADMIN("系统管理员"),
    CORE_ENTERPRISE("核心企业"),
    SMEs("中小企业"),
    FINANCIAL_INSTITUTION("金融机构"),
    LOGISTICS_COMPANY("物流公司");

    private final String description;

    CompanyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
