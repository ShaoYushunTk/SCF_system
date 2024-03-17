package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/3/1 16:36
 * @Description 交易类型
 */
public enum TradingType {
    /**
     *
     */
    PURCHASE_PAYMENT("采购付款"),
    SALE_RECEIPT("销售收款"),
    FINANCING_LOAN("融资放款"),
    FINANCING_REPAYMENT("融资还款");
    private final String description;
    TradingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
