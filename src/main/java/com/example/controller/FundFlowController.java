package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.constant.TradingType;
import com.example.dto.FundFlowDto;
import com.example.entity.FundFlow;
import com.example.entity.User;
import com.example.service.FundFlowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/3/5 9:36
 */
@RestController
@RequestMapping("/fundFlow")
@Slf4j
public class FundFlowController {
    @Autowired
    private FundFlowService fundFlowService;

    @PostMapping("/save")
    public Result createFundFlow(
            @RequestBody
            FundFlow fundFlow
    ) throws InterruptedException, TimeoutException {
        return fundFlowService.createFundFlow(fundFlow);
    }

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String tradingType,
            String name
    ) {
        return fundFlowService.page(page, pageSize, tradingType, name);
    }

    @PostMapping("/delete/{companyId}")
    public Result deleteByCompanyId(
            @PathVariable
            String companyId
    ) throws InterruptedException, TimeoutException {
        return fundFlowService.deleteByCompanyId(companyId);
    }

    @GetMapping("/company/{id}")
    public Result getFundFlowByCompanyId(
            @PathVariable
            String id,
            String tradingType,
            int page,
            int pageSize
    ) {
        return fundFlowService.getFundFlowByCompanyId(id, tradingType, page, pageSize);
    }
}
