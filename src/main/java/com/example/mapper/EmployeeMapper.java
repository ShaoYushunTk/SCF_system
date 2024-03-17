package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.EmployeeDto;
import com.example.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yushun Shao
 * @date 2024/2/22 9:36
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}


