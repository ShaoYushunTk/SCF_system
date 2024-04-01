package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.constant.TradingType;
import com.example.dto.FundFlowDto;
import com.example.exception.CommonException;
import com.example.service.CompanyService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Autowired
    private CompanyService companyService;

    @Override
    @Transactional
    public Result createFundFlow(FundFlow fundFlow) throws InterruptedException, TimeoutException {
        fundFlow.setId(UUIDUtils.generate());
        fundFlow.setTradingTime(LocalDateTime.now());
        this.save(fundFlow);
        DateTimeFormatter customFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String formattedDateTime = customFormatter.format(fundFlow.getTradingTime().atOffset(ZoneOffset.UTC));
        try {
            contract.submitTransaction("CreateFund",
                    fundFlow.getId(), formattedDateTime,
                    String.valueOf(fundFlow.getTradingType()), fundFlow.getPayer(),
                    fundFlow.getReceiver(), String.valueOf(fundFlow.getAccount()));
        } catch (ContractException e) {
            throw new CommonException(e.getMessage());
        }

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
            throw new CommonException(e.getMessage());
        }
        this.remove(queryWrapper);
        return Result.success("删除成功");
    }

    @Override
    public Result page(
            int page,
            int pageSize,
            String tradingType,
            String name
    ) {
        Page<FundFlow> fundFlowPage = new Page<>(page, pageSize);
        Page<FundFlowDto> dtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<FundFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(tradingType), FundFlow::getTradingType, tradingType)
                .orderByDesc(FundFlow::getTradingTime);
        this.page(fundFlowPage, queryWrapper);

        BeanUtils.copyProperties(fundFlowPage, dtoPage, "records");

        List<FundFlowDto> records = fundFlowPage.getRecords().stream().map((it) -> {
            FundFlowDto dto = new FundFlowDto();
            BeanUtils.copyProperties(it, dto);

            dto.setPayerName(companyService.getById(it.getPayer()).getName());
            dto.setReceiverName(companyService.getById(it.getReceiver()).getName());

            return dto;
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getPayerName().matches(regex) || dto.getReceiverName().matches(regex)).collect(Collectors.toList());
        }
        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }
}
