package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.common.Result;
import com.example.constant.CompanyType;
import com.example.constant.OrderStatus;
import com.example.constant.TradingType;
import com.example.dto.OrderDto;
import com.example.entity.*;
import com.example.exception.CommonException;
import com.example.mapper.OrderMapper;
import com.example.service.*;
import com.example.utils.CommonUtils;
import com.example.utils.UUIDUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

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

    @Autowired
    private FundFlowService fundFlowService;

    @Override
    public Result saveOrder(
            Orders order
    ) {
        order.setId(UUIDUtils.generate());
        order.setOrderTime(LocalDateTime.now());
        String companyIdByCurrentUser = commonUtils.getCompanyIdByCurrentUser();
        if (!Objects.equals(companyIdByCurrentUser, order.getPayer()) && !Objects.equals(companyIdByCurrentUser, order.getReceiver())) {
            throw new CommonException("支付方和收款方必须包含当前用户所属公司");
        }
        if (order.getGoodsList() == null || order.getGoodsList().isEmpty()) {
            throw new CommonException("订单商品不能为空");
        }
        if (Objects.equals(order.getPayer(), order.getReceiver())) {
            throw new CommonException("支付方和收款方不能相同");
        }
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
    @Transactional
    public Result payOrder(
            String id
    ) throws InterruptedException, TimeoutException {
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
        if (payerAssets == null) {
            payerAssets = new ArrayList<>();
        }
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
        if (receiverAssets == null) {
            receiverAssets = new ArrayList<>();
        }
        receiverAssets.add(order);
        receiverAsset.setOrderAssets(receiverAssets);
        companyAssetService.saveOrUpdate(receiverAsset);

        // 更新订单状态
        order.setOrderStatus(OrderStatus.PAID);
        this.saveOrUpdate(order);

        // 更新资金流水表
        FundFlow fundFlow = new FundFlow();
        fundFlow.setPayer(order.getPayer());
        fundFlow.setReceiver(order.getReceiver());
        fundFlow.setAmount(order.getAmount());
        fundFlow.setTradingType(TradingType.PURCHASE_PAYMENT);
        fundFlowService.createFundFlow(fundFlow);

        FundFlow fundFlow2 = new FundFlow();
        fundFlow2.setPayer(order.getReceiver());
        fundFlow2.setReceiver(order.getPayer());
        fundFlow2.setAmount(order.getAmount());
        fundFlow2.setTradingType(TradingType.SALE_RECEIPT);
        fundFlowService.createFundFlow(fundFlow2);

        return Result.success("支付成功");
    }

    @Override
    public Result updateStatus(
            String id,
            String orderStatus
    ) {
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId, id);
        Orders order = this.getOne(queryWrapper);
        if (order == null) {
            throw new CommonException("订单不存在");
        }

        // 更新订单状态
        order.setOrderStatus(OrderStatus.valueOf(orderStatus));
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
        Company byId = companyService.getById(companyId);
        if (byId.getCompanyType() == CompanyType.LOGISTICS_COMPANY || byId.getCompanyType() == CompanyType.FINANCIAL_INSTITUTION) {
            return Result.success("");
        }
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getPayer, companyId)
                .or()
                .eq(Orders::getReceiver, companyId);
        return Result.success(this.remove(lambdaQueryWrapper));
    }

    @Override
    public Result deleteById(String id) {
        Orders byId = this.getById(id);
        if (byId == null) {
            throw new CommonException("订单不存在");
        }
        if (byId.getOrderStatus() != OrderStatus.COMPLETED) {
            throw new CommonException("订单未完成，无法删除");
        }
        return Result.success(this.removeById(id));
    }
}
