package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/4/15 13:29
 */
public enum FinancialInstitutionType {
    /**
     *
     */
    COMMERCIAL_BANK("商业银行"),
    FACTORING_COMPANIES("保理公司"),
    FINTECH_PLATFORMS("金融科技平台");
    private final String description;
    FinancialInstitutionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
