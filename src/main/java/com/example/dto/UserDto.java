package com.example.dto;

import com.example.constant.CompanyType;
import com.example.entity.User;
import lombok.Data;

/**
 * @author Yushun Shao
 * @date 2024/2/28 21:20
 */
@Data
public class UserDto extends User {
    private String companyName;
    private CompanyType companyType;
}
