package com.example.common;

/**
 * @author Yushun Shao
 * @date 2024/2/20 17:17
 */
public class BaseContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 保存登录用户id
     * @param id
     */
    public static void setCurrentId(String id){
        threadLocal.set(id);
    }

    /**
     * 获取登录用户id
     * @return
     */
    public static String getCurrentId(){
        return threadLocal.get();
    }
}