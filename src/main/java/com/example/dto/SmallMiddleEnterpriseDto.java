package com.example.dto;

import com.example.entity.SmallMiddleEnterprise;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/24 9:40
 */
@Data
public class SmallMiddleEnterpriseDto extends SmallMiddleEnterprise {
    private String coreEnterpriseName;
}
