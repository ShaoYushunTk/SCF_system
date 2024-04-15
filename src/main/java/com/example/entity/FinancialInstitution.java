package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.constant.FinancialInstitutionType;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:21
 */
@Data
@TableName(autoResultMap = true)
public class FinancialInstitution extends Company {
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Double> loanInterestRate;
    private String enterpriseIndustry;
    private FinancialInstitutionType financialInstitutionType;
}
