package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.constant.TradingType;
import com.example.entity.FundFlow;

import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/3/5 9:37
 */
public interface FundFlowService extends IService<FundFlow> {
    Result createFundFlow(FundFlow fundFlow) throws InterruptedException, TimeoutException;

    Result deleteByCompanyId(String companyId) throws InterruptedException, TimeoutException;

    Result page(int page, int pageSize, String tradingType, String name);

    Result getFundFlowByCompanyId(String id, String tradingType, int page, int pageSize);
}
