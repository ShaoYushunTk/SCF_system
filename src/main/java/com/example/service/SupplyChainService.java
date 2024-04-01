package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.SupplyChain;

/**
 * @author Yushun Shao
 * @date 2024/3/20 23:56
 */
public interface SupplyChainService extends IService<SupplyChain> {
    Result page(int page, int pageSize, String name);

    Result init();
}
