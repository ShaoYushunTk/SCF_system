package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.EmployeeDto;
import com.example.entity.Employee;
import com.example.mapper.EmployeeMapper;
import com.example.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author Yushun Shao
 * @date 2024/2/22 9:37
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setUsername(employee.getUsername());
        employeeDto.setCompanyType(employee.getCompanyType());
        employeeDto.setId(employee.getId());
        employeeDto.setUpdatedBy(employee.getUpdatedBy());
        employeeDto.setCreatedBy(employee.getCreatedBy());
        employeeDto.setCreatedTime(employee.getCreatedTime());
        employeeDto.setUpdatedTime(employee.getUpdatedTime());
        return employeeDto;
    }
}
