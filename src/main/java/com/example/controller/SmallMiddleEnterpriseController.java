package com.example.controller;

import com.example.common.Result;
import com.example.service.SmallMiddleEnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yushun Shao
 * @date 2024/3/24 9:35
 */
@RestController
@RequestMapping("/smallMiddleEnterprise")
@Slf4j
public class SmallMiddleEnterpriseController {
    @Autowired
    private SmallMiddleEnterpriseService smallMiddleEnterpriseService;

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String name,
            String coreEnterpriseName
    ) {
        return smallMiddleEnterpriseService.page(page, pageSize, name, coreEnterpriseName);
    }
}
