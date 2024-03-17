package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Yushun Shao
 * @date 2024/3/11 16:35
 */
@Data
public class CompanyAsset implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String companyId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<FinancingInfo> financingInfoList;
    private BigDecimal cash;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Orders> orderAssets;
}
