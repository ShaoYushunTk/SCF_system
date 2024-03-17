package com.example.dto;

import com.example.entity.CoreEnterprise;
import com.example.entity.SmallMiddleEnterprise;
import lombok.Data;

import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/3/14 15:42
 */
@Data
public class CoreEnterpriseCreditRatingDto extends CoreEnterprise {
    private List<SmallMiddleEnterprise> smallMiddleEnterpriseList;
}
