package com.example.entity;

import com.example.constant.TradingType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yushun Shao
 * @date 2024/3/1 16:34
 * 资金流
 */
@Data
public class FundFlow implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private LocalDateTime tradingTime;
    private TradingType tradingType;
    private String payer;
    private String receiver;
    private BigDecimal account;
}
