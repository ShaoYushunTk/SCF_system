package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yushun Shao
 * @date 2024/2/20 17:06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
