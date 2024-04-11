package com.example.constant;

/**
 * @author Yushun Shao
 * @date 2024/3/10 21:32
 */
public enum OrderStatus {
    /**
     *
     */
    UNPAID("待支付"),
    PAID("已支付"),
    UNDELIVERED("待发货"),
    DELIVERED("已发货"),
    COMPLETED("已完成"),
    SHIPPING("运输中"),
    TO_BE_SIGNED("待签收");
    private final String description;
    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}