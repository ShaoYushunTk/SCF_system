package com.example.dto;

import com.example.entity.CompanyAsset;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/13 16:42
 */
@Data
public class CompanyAssetDto extends CompanyAsset {
    private String companyName;
}
