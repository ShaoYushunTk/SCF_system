package com.example.dto;

import com.example.constant.CompanyType;
import com.example.entity.User;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/3/25 10:27
 */
@Data
public class UserRegisterDto extends User {
    private CompanyType companyType;
    private String companyName;
    private String code;
}
