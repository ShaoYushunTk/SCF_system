package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.EmployeeDto;
import com.example.entity.Employee;

/**
 * @author Yushun Shao
 * @date 2024/2/22 9:36
 */
public interface EmployeeService extends IService<Employee> {
    public EmployeeDto toDto(Employee employee);
}
