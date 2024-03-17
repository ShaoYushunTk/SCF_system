package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.LogisticsProvider;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:48
 */
public interface LogisticsProviderService extends IService<LogisticsProvider> {
    LogisticsProvider getById(String id);

    Result updateByDto(LogisticsProvider logisticsProvider, String id);

    Boolean removeByCompanyId(String id);
}
