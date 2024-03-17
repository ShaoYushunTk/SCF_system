package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.CompanyAsset;

/**
 * @author Yushun Shao
 * @date 2024/3/12 15:31
 */
public interface CompanyAssetService extends IService<CompanyAsset> {
    Result page(int page, int pageSize, String companyId);

    void removeByCompanyId(String id);
}
