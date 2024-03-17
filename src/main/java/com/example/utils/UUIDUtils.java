package com.example.utils;

/**
 * @author Yushun Shao
 * @date 2024/2/20 21:33
 */
public class UUIDUtils {
    public static String generate() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
