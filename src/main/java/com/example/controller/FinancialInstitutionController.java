package com.example.controller;

import com.example.common.Result;
import com.example.service.FinancialInstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/3/14 16:13
 */
@RestController
@RequestMapping("/financialInstitution")
@Slf4j
public class FinancialInstitutionController {
    @Autowired
    private FinancialInstitutionService financialInstitutionService;

    @GetMapping("/list")
    public Result list() {
        return financialInstitutionService.getFinancialInstitutionList();
    }

    @GetMapping("/page")
    public Result page(
            int page,
            int pageSize,
            String name,
            String financialInstitutionType
    ) {
        return financialInstitutionService.page(page, pageSize, name, financialInstitutionType);
    }

}
