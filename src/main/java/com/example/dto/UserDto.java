package com.example.dto;

import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/2/28 21:20
 */
@Data
public class UserDto {
    private String name;
    private String phone;
    private String companyId;
    private Integer isValid;
}
