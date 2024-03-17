package com.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.entity.FundFlow;
import com.example.mapper.FundFlowMapper;
import com.example.service.FundFlowService;
import com.example.utils.UUIDUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/3/5 9:38
 */
@Service
public class FundFlowServiceImpl extends ServiceImpl<FundFlowMapper, FundFlow> implements FundFlowService {
    @Autowired
    private Gateway gateway;

    @Autowired
    private Contract contract;

    @Override
    public Result createFundFlow(FundFlow fundFlow) throws InterruptedException, TimeoutException {
        fundFlow.setId(UUIDUtils.generate());
        fundFlow.setTradingTime(LocalDateTime.now());

        try {
            contract.submitTransaction("CreateFund",
                    fundFlow.getId(), String.valueOf(fundFlow.getTradingTime()),
                    String.valueOf(fundFlow.getTradingType()), fundFlow.getPayer(),
                    fundFlow.getReceiver(), String.valueOf(fundFlow.getAccount()));
        } catch (ContractException e) {
            return Result.error(e.getMessage());
        }
        this.save(fundFlow);
        return Result.success(fundFlow);
    }

    @Override
    public Result deleteByCompanyId(String companyId) throws InterruptedException, TimeoutException {
        LambdaQueryWrapper<FundFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(companyId), FundFlow::getPayer, companyId)
                .or()
                .eq(StringUtils.isNotEmpty(companyId), FundFlow::getReceiver, companyId);
        List<FundFlow> fundFlowList = this.list(queryWrapper);

        try {
            for (FundFlow fundFlow : fundFlowList) {
                contract.submitTransaction("DeleteFund", fundFlow.getId());
            }

        } catch (ContractException e) {
            return Result.error(e.getMessage());
        }
        this.remove(queryWrapper);
        return Result.success("删除成功");
    }
}
