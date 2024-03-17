package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.constant.ApprovalStatus;
import com.example.entity.FinancingInfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/3/6 14:58
 */
public interface FinancingInfoService extends IService<FinancingInfo> {
    Result create(FinancingInfo financingInfo);
    Result approve(String id, ApprovalStatus approvalStatus, String approvalComment, BigDecimal interestRate);
    Result loan(String id) throws InterruptedException, TimeoutException;

    Result repay(String id) throws InterruptedException, TimeoutException;

    List<FinancingInfo> listByCompanyId(List<String> idList);

    Result deleteByCompanyId(String companyId) throws InterruptedException, TimeoutException;
}