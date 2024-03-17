package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.constant.OrderStatus;
import com.example.dto.OrderDto;
import com.example.entity.Orders;
import com.example.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            int pageSize
    ) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
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
}
