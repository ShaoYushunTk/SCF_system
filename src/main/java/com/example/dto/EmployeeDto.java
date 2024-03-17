package com.example.dto;

import com.example.constant.CompanyType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yushun Shao
 * @date 2024/2/22 17:49
 */
@Data
public class EmployeeDto {
    private String id;
    private String username;

    private CompanyType companyType;

    private String createdBy;

    private LocalDateTime createdTime;

    private String updatedBy;

    private LocalDateTime updatedTime;
}
