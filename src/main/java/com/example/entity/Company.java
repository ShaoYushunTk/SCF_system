package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.constant.CompanyType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yushun Shao
 * @date 2024/2/24 10:04
 */
@Data
@TableName(autoResultMap = true)
@EqualsAndHashCode(callSuper = false)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    private String id;
    private String name;
    private CompanyType companyType;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ContactInfo contactInfo;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
