package com.example.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Yushun Shao
 * @description: Meta Object Handler 设置公共字段填充
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("insert...");
//        log.info(metaObject.toString());
        metaObject.setValue("createdTime", LocalDateTime.now());
        metaObject.setValue("updatedTime", LocalDateTime.now());
        metaObject.setValue("createdBy", BaseContext.getCurrentId());
        metaObject.setValue("updatedBy", BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("update...");
//        log.info(metaObject.toString());
        metaObject.setValue("updatedTime", LocalDateTime.now());
        metaObject.setValue("updatedBy", BaseContext.getCurrentId());
    }
}
