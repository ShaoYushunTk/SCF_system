package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author Yushun Shao
 * @date 2024/2/24 10:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CoreEnterprise extends Company {
    // 企业所属行业
    private String enterpriseIndustry;

    // 注册资本
    private BigDecimal registeredCapital;

    // 信用评级
    private String creditRating;

    // 银行卡号
    private String bankNumber;

    // 流动资产
    private BigDecimal liquidAssets;

    // 固定资产
    private BigDecimal fixedAssets;

    // 流动负债
    private BigDecimal liquidLiabilities;

    // 固定负债
    private BigDecimal fixedLiabilities;
}

