package com.example.entity;

import com.example.constant.ApprovalStatus;
import com.example.constant.FinancingType;
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
    private LocalDateTime applyTime;
    private String companyId;
    private BigDecimal account;
    private String applyComment;

    private FinancingType financingType;
    private String financialInstitutionId;
    private ApprovalStatus approvalStatus;
    private LocalDateTime approvalTime;
    private String approvalId;
    private String approvalComment;
    private LocalDateTime loanTime;
    // 日利率
    private BigDecimal interestRate;
    private Boolean isRepay;
    private LocalDateTime repayTime;
    private BigDecimal repayAccount;
}
