package com.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yushun Shao
 * @date 2024/2/24 10:12
 */
@Data
public class ContactInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phone;
    private String address;
    private String email;
}
