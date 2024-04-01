package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.typeHandler.StringTypeHandler;
import lombok.Data;

import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/3/20 23:52
 */
@Data
@TableName(autoResultMap = true)
public class SupplyChain {
    private String id;
    private String coreEnterpriseId;
    @TableField(typeHandler = StringTypeHandler.class)
    private List<String> smesList;
    @TableField(typeHandler = StringTypeHandler.class)
    private List<String> financialInstitutionList;
    @TableField(typeHandler = StringTypeHandler.class)
    private List<String> logisticsCompanyList;

}
