package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.constant.OrderStatus;
import com.example.dto.OrderDto;
import com.example.entity.Orders;
import com.example.service.CompanyService;
import com.example.service.LogisticsProviderService;
import com.example.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/3/10 22:06
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private CompanyService companyService;


    @PostMapping("/save")
    public Result save(
            @RequestBody
            Orders order
    ) {
        return ordersService.saveOrder(order);
    }

    @GetMapping("/page")
    public Result<Page> page(
            int page,
            int pageSize,
            String companyName,
            String orderStatus
    ) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrderDto> dtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<OrderDto> records = pageInfo.getRecords().stream().map((it) -> {
            OrderDto dto = new OrderDto();
            BeanUtils.copyProperties(it, dto);

            dto.setPayerName(companyService.getById(it.getPayer()).getName());
            dto.setReceiverName(companyService.getById(it.getReceiver()).getName());
            dto.setReceiverPhone(companyService.getById(it.getReceiver()).getContactInfo().getPhone());
            dto.setReceiverAddress(companyService.getById(it.getReceiver()).getContactInfo().getAddress());
            dto.setLogisticProviderName(companyService.getById(it.getLogisticsProviderId()).getName());
            return dto;
        }).toList();

        if (StringUtils.isNotEmpty(companyName)) {
            String regex = ".*" + Pattern.quote(companyName) + ".*";
            records = records.stream().filter(dto -> dto.getPayerName().matches(regex) || dto.getReceiverName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(orderStatus)) {
            records = records.stream().filter(dto -> dto.getOrderStatus().toString().equals(orderStatus)).collect(Collectors.toList());
        }
        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }

    // todo 获取详情，付款收款，快递公司修改订单状态
    @GetMapping("/{id}")
    public Result<OrderDto> getById(
            @PathVariable
            String id
    ) {
        return ordersService.getOrderDtoById(id);
    }

    @PostMapping("/{id}/pay")
    public Result pay(
            @PathVariable
            String id
    ) {
        return ordersService.payOrder(id);
    }

    @PostMapping("/{id}/updateStatus")
    public Result updateStatus(
            @PathVariable
            String id,
            @RequestBody
            OrderStatus orderStatus
    ) {
        return ordersService.updateStatus(id, orderStatus);
    }

    @PostMapping("/{companyId}/delete")
    public Result deleteByCompanyId(
            @PathVariable
            String companyId
    ) {
        return ordersService.deleteByCompanyId(companyId);
    }

    @PostMapping("/{id}/delete")
    public Result deleteById(
            @PathVariable
            String id
    ) {
        return ordersService.deleteById(id);
    }

    @GetMapping("/company/{id}")
    public Result getOrdersByCompanyId(
            @PathVariable
            String id,
            int page,
            int pageSize
    ) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrderDto> dtoPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        queryWrapper.eq(Orders::getPayer, id)
                .or()
                .eq(Orders::getReceiver, id);
        ordersService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<OrderDto> records = pageInfo.getRecords().stream().map((it) -> {
            OrderDto dto = new OrderDto();
            BeanUtils.copyProperties(it, dto);

            dto.setPayerName(companyService.getById(it.getPayer()).getName());
            dto.setReceiverName(companyService.getById(it.getReceiver()).getName());
            dto.setReceiverPhone(companyService.getById(it.getReceiver()).getContactInfo().getPhone());
            dto.setReceiverAddress(companyService.getById(it.getReceiver()).getContactInfo().getAddress());
            dto.setLogisticProviderName(companyService.getById(it.getLogisticsProviderId()).getName());
            return dto;
        }).toList();

        dtoPage.setRecords(records);
        dtoPage.setTotal(records.size());
        return Result.success(dtoPage);
    }
}
