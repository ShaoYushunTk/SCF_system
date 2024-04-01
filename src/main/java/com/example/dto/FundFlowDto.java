package com.example.dto;

import com.example.entity.FundFlow;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/21 23:51
 */
@Data
public class FundFlowDto extends FundFlow {
    private String payerName;
    private String receiverName;
}
