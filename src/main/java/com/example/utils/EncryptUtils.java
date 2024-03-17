package com.example.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Yushun Shao
 * @date 2024/2/22 9:47
 */
@Component
public class EncryptUtils {
    public static String encrypt(String password) {
        String hashedPassword = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }

    public static void main(String[] args) {
        String a = "admin";
        System.out.println(EncryptUtils.encrypt(a));
    }
}
