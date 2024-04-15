package com.example.entity;

import com.example.constant.TransportType;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:46
 */
@Data
public class LogisticsProvider extends Company{
    private String enterpriseIndustry;
    private TransportType transportType;
}
