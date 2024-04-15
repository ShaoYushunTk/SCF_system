package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yushun Shao
 * @date 2024/2/20 17:00
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String phone;

    private String companyId;

    private String bankNumber;

    private String name;

    private int isValid;

    private String contactEmail;

    private String contactAddress;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
