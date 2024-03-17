package com.example.controller;

import com.example.common.Result;
import com.example.service.CompanyAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yushun Shao
 * @date 2024/3/13 16:32
 */
@RestController
@RequestMapping("/companyAsset")
@Slf4j
public class CompanyAssetController {
    @Autowired
    private CompanyAssetService companyAssetService;

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String companyId
    ) {
        return companyAssetService.page(page, pageSize, companyId);
    }
}
