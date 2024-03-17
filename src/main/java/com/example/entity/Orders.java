package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.constant.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/3/10 21:20
 */
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private LocalDateTime orderTime;
    private String payer;
    private String receiver;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Goods> goodsList;
    private BigDecimal amount;
    private OrderStatus orderStatus;
    private String logisticsProviderId;
    private String remark;
}
