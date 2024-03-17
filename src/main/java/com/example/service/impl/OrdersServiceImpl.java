package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.common.Result;
import com.example.constant.OrderStatus;
import com.example.dto.OrderDto;
import com.example.entity.*;
import com.example.exception.CommonException;
import com.example.mapper.OrderMapper;
import com.example.service.CompanyAssetService;
import com.example.service.CompanyService;
import com.example.service.OrdersService;
import com.example.service.UserService;
import com.example.utils.CommonUtils;
import com.example.utils.UUIDUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/3/10 22:06
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrdersService {

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyAssetService companyAssetService;

    @Override
    public Result saveOrder(
            Orders order
    ) {
        order.setId(UUIDUtils.generate());
        order.setOrderTime(LocalDateTime.now());
        // TODO 使用工具类抽取根据userId获取公司信息的方法
        order.setPayer(commonUtils.getCompanyIdByCurrentUser());
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Goods goods : order.getGoodsList()) {
            goods.setOrderId(order.getId());
            goods.setId(UUIDUtils.generate());

            BigDecimal goodsAmount = goods.getPrice().multiply(BigDecimal.valueOf(goods.getNumber()));
            totalAmount = totalAmount.add(goodsAmount);
        }

        order.setAmount(totalAmount);
        order.setOrderStatus(OrderStatus.UNPAID);
        this.save(order);
        return Result.success(order);
    }

    @Override
    public Result<OrderDto> getOrderDtoById(
            String id
    ) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId, id);
        Orders order = this.getOne(queryWrapper);
        if (order == null) {
            throw new CommonException("订单不存在");
        }

        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);

        Company payer = companyService.getById(order.getPayer());
        orderDto.setPayerName(payer.getName());
        Company receiver = companyService.getById(order.getReceiver());
        orderDto.setReceiverName(receiver.getName());
        orderDto.setReceiverPhone(receiver.getContactInfo().getPhone());
        orderDto.setReceiverAddress(receiver.getContactInfo().getAddress());
        Company logisticsProvider = companyService.getById(order.getLogisticsProviderId());
        orderDto.setLogisticProviderName(logisticsProvider.getName());

        return Result.success(orderDto);
    }

    @Override
    public Result payOrder(
            String id
    ) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId, id);
        Orders order = this.getOne(queryWrapper);
        if (order == null) {
            throw new CommonException("订单不存在");
        }

        LambdaQueryWrapper<CompanyAsset> companyAssetLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        companyAssetLambdaQueryWrapper1.eq(CompanyAsset::getCompanyId, order.getPayer());
        CompanyAsset payerAsset = companyAssetService.getOne(companyAssetLambdaQueryWrapper1);

        // 验证企业资金是否能支付
        if (payerAsset.getCash().compareTo(order.getAmount()) < 0) {
            throw new CommonException("企业资金不足，无法支付");
        }

        // 更新支付方企业资金表
        BigDecimal payerCash = payerAsset.getCash();
        payerCash = payerCash.subtract(order.getAmount());
        payerAsset.setCash(payerCash);
        List<Orders> payerAssets = payerAsset.getOrderAssets();
        payerAssets.add(order);
        payerAsset.setOrderAssets(payerAssets);
        companyAssetService.saveOrUpdate(payerAsset);

        // 更新收款方企业资金表
        LambdaQueryWrapper<CompanyAsset> companyAssetLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        companyAssetLambdaQueryWrapper2.eq(CompanyAsset::getCompanyId, order.getReceiver());
        CompanyAsset receiverAsset = companyAssetService.getOne(companyAssetLambdaQueryWrapper2);
        BigDecimal receiverCash = receiverAsset.getCash();
        receiverCash = receiverCash.add(order.getAmount());
        receiverAsset.setCash(receiverCash);
        List<Orders> receiverAssets = receiverAsset.getOrderAssets();
        receiverAssets.add(order);
        receiverAsset.setOrderAssets(receiverAssets);
        companyAssetService.saveOrUpdate(receiverAsset);

        // 更新订单状态
        order.setOrderStatus(OrderStatus.PAID);
        this.saveOrUpdate(order);

        return Result.success("支付成功");
    }

    @Override
    public Result updateStatus(
            String id,
            OrderStatus orderStatus
    ) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId, id);
        Orders order = this.getOne(queryWrapper);
        if (order == null) {
            return Result.error("订单不存在");
        }

        // 更新订单状态
        order.setOrderStatus(orderStatus);
        this.saveOrUpdate(order);

        return Result.success("更新状态成功");
    }

    @Override
    public List<Orders> listByCompanyId(List<String> idList) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Orders::getPayer, idList)
                .or()
                .in(Orders::getReceiver, idList)
                .orderByDesc(Orders::getOrderTime);
        return this.list(queryWrapper);
    }

    @Override
    public Result deleteByCompanyId(String companyId) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getPayer, companyId)
                .or()
                .eq(Orders::getReceiver, companyId);
        return Result.success(this.remove(lambdaQueryWrapper));
    }
}
