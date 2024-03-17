package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.constant.CompanyType;
import com.example.dto.EmployeeDto;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import com.example.utils.EncryptUtils;
import com.example.utils.UUIDUtils;
import jakarta.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/2/22 9:38
 */
@RestController
@RequestMapping(value = "/employee")
@Slf4j
public class EmployeeController {

    public final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 登录
     * @param employee
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public Result<Employee> login(
            @RequestBody
            Employee employee,
            HttpServletRequest request
    ) {
        // 加密密码
        String encrypt = EncryptUtils.encrypt(employee.getPassword());

        // 查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername())
                .eq(Employee::getCompanyType, employee.getCompanyType());
        Employee emp = employeeService.getOne(queryWrapper);

        // 判断
        // TODO 优化：自定义异常类封装
        if (emp == null) {
            return Result.error("账号不存在");
        }

        if (!emp.getPassword().equals(encrypt)) {
            return Result.error("用户名或密码有误");
        }

        request.getSession().setAttribute("employee",emp.getId());
        return Result.success(emp);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        //清理session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增
     * @param employee
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(
            @RequestBody
            Employee employee
    ) {
        employee.setId(UUIDUtils.generate());
        employee.setPassword(EncryptUtils.encrypt("admin"));

        employeeService.save(employee);
        return Result.success("新增成功");
    }

    @GetMapping("/page")
    public Result<Page<EmployeeDto>> page(int page, int pageSize, String name, CompanyType companyType) {
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getUsername, name);
        queryWrapper.eq(companyType != null, Employee::getCompanyType, companyType);
        queryWrapper.orderByDesc(Employee::getUpdatedTime);
        employeeService.page(pageInfo, queryWrapper);

        List<EmployeeDto> employeeDtos = pageInfo.getRecords().stream()
                .map(employeeService::toDto).collect(Collectors.toList());


        Page<EmployeeDto> employeeDtoPageInfo = new Page<>();
        employeeDtoPageInfo.setRecords(employeeDtos);
        employeeDtoPageInfo.setTotal(pageInfo.getTotal());

        return Result.success(employeeDtoPageInfo);
    }


}
