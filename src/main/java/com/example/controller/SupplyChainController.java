package com.example.controller;

import com.example.common.Result;
import com.example.service.SupplyChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yushun Shao
 * @date 2024/3/20 23:57
 */
@RestController
@RequestMapping("/supplyChain")
@Slf4j
public class SupplyChainController {
    @Autowired
    private SupplyChainService supplyChainService;

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String name
    ) {
        return supplyChainService.page(page, pageSize, name);
    }

    @PostMapping("/init")
    public Result init() {
        return supplyChainService.init();
    }
}
