package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.constant.OrderStatus;
import com.example.dto.OrderDto;
import com.example.entity.Orders;

import java.util.List;
import java.util.Map;

/**
 * @author Yushun Shao
 * @date 2024/3/10 22:05
 */
public interface OrdersService extends IService<Orders> {
    Result saveOrder(Orders order);

    Result<OrderDto> getOrderDtoById(String id);

    Result payOrder(String id);

    Result updateStatus(String id, OrderStatus orderStatus);

    List<Orders> listByCompanyId(List<String> idList);

    Result deleteByCompanyId(String companyId);
}
