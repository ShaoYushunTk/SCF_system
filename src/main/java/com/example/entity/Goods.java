package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Yushun Shao
 * @date 2024/3/10 21:30
 * 商品
 */
@Data
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String orderId;
    private String name;
    private Integer number;
    private BigDecimal price;
}
