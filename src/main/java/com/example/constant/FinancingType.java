package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/3/6 14:53
 */
public enum FinancingType {
    /**
     * 融资类型
     */
    ACCOUNTS_RECEIVABLE("应收账款融资"),
    PURCHASE_ORDER("采购订单融资"),
    SUPPLY_CHAIN("供应链融资");
    private final String description;
    FinancingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
