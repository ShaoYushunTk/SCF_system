package com.example.dto;

import com.example.entity.Orders;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/10 21:48
 */
@Data
public class OrderDto extends Orders {
    private String payerName;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String logisticProviderName;
}
