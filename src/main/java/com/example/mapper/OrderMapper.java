package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yushun Shao
 * @date 2024/3/10 22:04
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
