package com.example.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.BaseContext;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yushun Shao
 * @date 2024/3/17 12:14
 */
@Component
public class CommonUtils {
    @Autowired
    private UserService userService;

    public String getCompanyIdByCurrentUser() {
        User user = userService.getById(BaseContext.getCurrentId());
        return user.getCompanyId();
    }
}
