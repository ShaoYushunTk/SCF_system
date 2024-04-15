package com.example.controller;

import com.example.common.Result;
import com.example.constant.ApprovalStatus;
import com.example.entity.FinancingInfo;
import com.example.service.FinancingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/3/7 10:09
 */
@RestController
@RequestMapping("/financingInfo")
@Slf4j
public class FinancingInfoController {
    @Autowired
    private FinancingInfoService financingInfoService;

    @PostMapping("/save")
    public Result createFinancingInfo(
            @RequestBody
            FinancingInfo financingInfo
    ) {
        return financingInfoService.create(financingInfo);
    }

    @PostMapping("/{id}/approval")
    public Result approveFinancingInfo(
            @PathVariable
            String id,
            @RequestParam
            String approvalStatus,
            @RequestParam
            String approvalComment
    ) {
        return financingInfoService.approve(id, approvalStatus, approvalComment);
    }

    @PostMapping("/{id}/loan")
    public Result loanFinancingInfo(
            @PathVariable
            String id
    ) throws InterruptedException, TimeoutException {
        return financingInfoService.loan(id);
    }

    @PostMapping("/{id}/repay")
    public Result repayFinancingInfo(
            @PathVariable
            String id
    ) throws InterruptedException, TimeoutException {
        return financingInfoService.repay(id);
    }

    @PostMapping("/{companyId}/delete")
    public Result deleteByCompanyId(
            @PathVariable
            String companyId
    ) throws InterruptedException, TimeoutException {
        return financingInfoService.deleteByCompanyId(companyId);
    }

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String companyName,
            String approvalStatus,
            String companyType
    ) {
        return financingInfoService.page(page, pageSize, companyName, approvalStatus, companyType);
    }

    @GetMapping("/{id}")
    public Result getFinancingInfoById(
            @PathVariable
            String id
    ) {
        return financingInfoService.getFinancingInfoById(id);
    }

    @PostMapping("/{id}/deleteById")
    public Result deleteFinancingInfoById(
            @PathVariable
            String id
    ) {
        return financingInfoService.deleteFinancingInfoById(id);
    }

    @GetMapping("/company/{id}")
    public Result getFinancingInfoByCompanyId(
            @PathVariable
            String id,
            int page,
            int pageSize
    ) {
        return financingInfoService.getFinancingInfoByCompanyId(id, page, pageSize);
    }

    @GetMapping("/financialInstitution/{id}")
    public Result getFinancingInfoByFinancialInstitutionId(
            @PathVariable
            String id,
            int page,
            int pageSize
    ) {
        return financingInfoService.getFinancingInfoByFinancialInstitutionId(id, page, pageSize);
    }
}
