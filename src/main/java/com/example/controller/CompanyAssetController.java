package com.example.controller;

import com.example.common.Result;
import com.example.exception.CommonException;
import com.example.service.CompanyAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
            String name,
            String companyType
    ) {
        return companyAssetService.page(page, pageSize, name, companyType);
    }

    @GetMapping("/{id}")
    public Result getById(
            @PathVariable
            String id
    ) {
        return companyAssetService.getCompanyAssetById(id);
    }

    @PostMapping("/{id}/addCash")
    public Result addCash(
            @PathVariable
            String id,
            @RequestParam
            BigDecimal cash
    ) {
        return companyAssetService.addCash(id, cash);
    }
}
