package com.example.entity;

import com.example.constant.ApprovalStatus;
import com.example.constant.FinancingType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yushun Shao
 * @date 2024/3/6 14:27
 * 融资信息
 */
@Data
public class FinancingInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime applyTime;
    private String companyId;
    private BigDecimal account;
    private String applyComment;

    private FinancingType financingType;
    private String financialInstitutionId;
    private ApprovalStatus approvalStatus;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime approvalTime;
    private String approvalId;
    private String approvalComment;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime loanTime;
    // 年利率
    private BigDecimal interestRate;
    private Boolean isRepay;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime repayTime;
    private BigDecimal repayAccount;
}
