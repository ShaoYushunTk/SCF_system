package com.example.controller;

import com.example.common.Result;
import com.example.service.CoreEnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yushun Shao
 * @date 2024/3/14 14:55
 */
@RestController
@RequestMapping("/coreEnterprise")
@Slf4j
public class CoreEnterpriseController {
    @Autowired
    private CoreEnterpriseService coreEnterpriseService;

    @PostMapping("/{id}/saveCreditRating")
    public Result saveCreditRating(
            @PathVariable
            String id,
            @RequestParam
            String creditRating
    ) {
        return coreEnterpriseService.saveCreditRating(id, creditRating);
    }

    @GetMapping("/creditRatingList")
    public Result getCreditRatingList(
            @RequestParam
            String creditRating
    ) {
        return coreEnterpriseService.getCreditRatingList(creditRating);
    }
}