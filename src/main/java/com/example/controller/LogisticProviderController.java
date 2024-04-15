package com.example.controller;

import com.example.common.Result;
import com.example.service.LogisticsProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yushun Shao
 * @date 2024/3/24 9:45
 */
@RestController
@RequestMapping("/logisticProvider")
@Slf4j
public class LogisticProviderController {
    @Autowired
    private LogisticsProviderService logisticsProviderService;

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String name,
            String transportType
    ) {
        return logisticsProviderService.page(page, pageSize, name, transportType);
    }
}
