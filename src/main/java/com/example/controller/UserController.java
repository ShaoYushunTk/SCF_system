package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.constant.CompanyType;
import com.example.dto.UserDto;
import com.example.dto.UserRegisterDto;
import com.example.entity.Company;
import com.example.entity.User;
import com.example.exception.CommonException;
import com.example.properties.SmsProperties;
import com.example.service.CompanyService;
import com.example.service.UserService;
import com.example.utils.SMSUtils;
import com.example.utils.UUIDUtils;
import com.example.utils.ValidateCodeUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Yushun Shao
 * @date 2024/2/20 17:09
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;
    private final RedisTemplate<String, String> redisTemplate;
    private final SmsProperties smsProperties;
    private final SMSUtils smsUtils;

    public UserController(
            UserService userService,
            CompanyService companyService,
            RedisTemplate<String, String> redisTemplate,
            SmsProperties smsProperties,
            SMSUtils smsUtils
    ) {
        this.userService = userService;
        this.companyService = companyService;
        this.redisTemplate = redisTemplate;
        this.smsProperties = smsProperties;
        this.smsUtils = smsUtils;
    }

    @PostMapping("/sendMsg")
    public Result<String> sendMsg(
            @RequestBody
            User user
    ) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("code = {}",code);

            // smsUtils.sendMessage(smsProperties.signName, smsProperties.templateCode, phone,code);

            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Result.success("发送成功");
        }
        return Result.error("发送失败");
    }

    @PostMapping("/login")
    public Result<Map> login(
            @RequestBody
            Map map,
            HttpSession httpSession
    ) {
        log.info(map.toString());

        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        String codeInRedis = redisTemplate.opsForValue().get(phone);

        if (code != null && code.equals(codeInRedis)) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(lambdaQueryWrapper);

            if (user == null) {
                throw new CommonException("当前用户未注册");
            }
            if (user.getIsValid() == 0) {
                throw new CommonException("账号已禁用");
            }

            httpSession.setAttribute("user",user.getId());

            // 删除redis
            redisTemplate.delete(phone);

            // 根据企业id拿到类型
            CompanyType companyType = companyService.getById(user.getCompanyId()).getCompanyType();

            Map <String, String> m = new HashMap<>();
            m.put("phone", phone);
            m.put("code", code);
            m.put("companyType", companyType.toString());
            return Result.success(m);
        }
        return Result.error("登录失败");
    }

    @PostMapping("/register")
    public Result<Map> register(
            @RequestBody
            UserRegisterDto userRegisterDto
    ) {
        log.info(userRegisterDto.toString());

        // 判断企业id是否存在
        LambdaQueryWrapper<Company> companyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        companyLambdaQueryWrapper.eq(Company::getCompanyType, userRegisterDto.getCompanyType())
                .eq(Company::getName, userRegisterDto.getCompanyName());
        Company company = companyService.getOne(companyLambdaQueryWrapper);
        if (company == null) {
            throw new CommonException("企业不存在");
        }

        // 判断手机号是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,userRegisterDto.getPhone())
                .eq(User::getCompanyId,userRegisterDto.getCompanyId());
        User u = userService.getOne(lambdaQueryWrapper);

        if (u != null) {
            throw new CommonException("当前企业下手机号已存在");
        }

        // 注册
        userRegisterDto.setId(UUIDUtils.generate());
        userRegisterDto.setIsValid(1);
        userRegisterDto.setCompanyId(company.getId());
        User user = new User();
        BeanUtils.copyProperties(userRegisterDto, user);
        userService.save(user);

        Map <String, Object> m = new HashMap<>();
        m.put("userInfo", user);
        m.put("companyType", userRegisterDto.getCompanyType().toString());
        return Result.success(m);
    }

    @GetMapping("/page")
    public Result<Page> page(
            int page,
            int pageSize,
            String name
    ) {
        Page<User> pageInfo = new Page<>(page,pageSize);
        Page<UserDto> userDtoPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), User::getName, name);
        queryWrapper.orderByDesc(User::getUpdatedTime);

        userService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, userDtoPage, "records");
        List<UserDto> userDtoList = pageInfo.getRecords().stream().map((it) -> {
            UserDto userDto = new UserDto();
            Company byId = companyService.getById(it.getCompanyId());
            userDto.setCompanyName(byId.getName());
            userDto.setCompanyType(byId.getCompanyType());
            BeanUtils.copyProperties(it, userDto);
            return userDto;
        }).collect(Collectors.toList());

        userDtoPage.setRecords(userDtoList);

        return Result.success(userDtoPage);
    }

    @PostMapping("/{id}/updateStatus")
    public Result updateStatus(
            @PathVariable
            String id,
            @RequestParam("status")
            int status
    ) {
        User user = userService.getById(id);
        user.setIsValid(status);
        userService.updateById(user);
        return Result.success("更新成功");
    }

    @PostMapping("/save")
    public Result save(
            @RequestBody
            User user
    ) {
        // 同名校验
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, user.getName());
        User one = userService.getOne(lambdaQueryWrapper);
        if (one != null) {
            throw new CommonException("当前用户名称已存在");
        }
        // 企业id校验
        Company byId = companyService.getById(user.getCompanyId());
        if (byId == null) {
            throw new CommonException("企业不存在");
        }
        user.setId(UUIDUtils.generate());
        user.setIsValid(1);
        userService.save(user);
        return Result.success("保存成功");
    }

    @PostMapping("/{id}/update")
    public Result update(
            @PathVariable
            String id,
            @RequestBody
            User user
    ) {
        User byId = userService.getById(id);
        if (byId == null) {
            throw new CommonException("用户不存在");
        }
        user.setId(id);
        userService.updateById(user);
        return Result.success("更新成功");
    }

}
