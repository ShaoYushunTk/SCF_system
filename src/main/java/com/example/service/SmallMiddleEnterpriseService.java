package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.SmallMiddleEnterprise;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:44
 */
public interface SmallMiddleEnterpriseService extends IService<SmallMiddleEnterprise> {
    SmallMiddleEnterprise getById(String id);
    SmallMiddleEnterprise getOne(LambdaQueryWrapper<SmallMiddleEnterprise> lambdaQueryWrapper);

    List<SmallMiddleEnterprise> list(LambdaQueryWrapper<SmallMiddleEnterprise> lambdaQueryWrapper);
    Result updateByDto(SmallMiddleEnterprise smallMiddleEnterprise, String id);

    boolean removeByCompanyId(String id);
}
