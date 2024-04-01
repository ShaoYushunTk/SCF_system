package com.example.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.BaseContext;
import com.example.entity.SmallMiddleEnterprise;
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

    public <T> void customPage(Page<T> page, LambdaQueryWrapper<T> queryWrapper, Class<T> entityClass, Class<?> entityServiceClass) {
        // 在外部对 queryWrapper 进行定制化处理
        queryWrapper.select(entityClass, info ->
                !"name".equals(info.getColumn()) &&
                        !"company_type".equals(info.getColumn()) &&
                        !"contact_info".equals(info.getColumn())
        );

        // 调用 MyBatis Plus 提供的 page 方法执行查询并返回结果
        // 注意：这里的 this.page 需要根据你的具体情况来调整，确保调用的是正确的方法
        this.page(page, queryWrapper, entityServiceClass);
    }

    private <T> void page(Page<T> page, LambdaQueryWrapper<T> queryWrapper, Class<?> entityServiceClass) {
        // 根据传入的 entityServiceClass 来获取对应的 Service 实例
        Object serviceInstance = SpringContextUtil.getBean(entityServiceClass);

        // 确保获取的 Service 实例不为空，并且是一个有效的 MyBatis Plus Service
        if (serviceInstance instanceof IService) {
            IService<T> mybatisPlusService = (IService<T>) serviceInstance;

            // 调用 MyBatis Plus Service 的分页查询方法
            mybatisPlusService.page(page, queryWrapper);
        } else {
            throw new IllegalArgumentException("Invalid entityServiceClass");
        }
    }
}
