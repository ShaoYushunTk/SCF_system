package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.constant.CompanyType;
import com.example.dto.UserDto;
import com.example.entity.Company;
import com.example.entity.User;
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
                return Result.error("当前用户未注册");
            }
            if (user.getIsValid() == 0) {
                return Result.error("账号已禁用");
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
            User user
    ) {
        log.info(user.toString());

        // 判断企业id是否存在
        Company company = companyService.getById(user.getCompanyId());
        if (company == null) {
            return Result.error("企业id不存在");
        }

        // 判断手机号是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,user.getPhone())
                .eq(User::getCompanyId,user.getCompanyId());
        User u = userService.getOne(lambdaQueryWrapper);

        if (u != null) {
            return Result.error("当前企业下手机号已存在");
        }

        // 注册
        user.setId(UUIDUtils.generate());
        user.setIsValid(1);
        userService.save(user);

        CompanyType companyType = companyService.getById(user.getCompanyId()).getCompanyType();

        Map <String, String> m = new HashMap<>();
        m.put("id", user.getId());
        m.put("companyType", companyType.toString());
        return Result.success(m);
    }

    @GetMapping("/page")
    public Result<Page> page(
            int page,
            int pageSize,
            String name
    ) {
        Page<User> pageInfo = new Page(page,pageSize);
        Page<UserDto> userDtoPage = new Page<>(page,pageSize);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), User::getName, name);
        queryWrapper.orderByDesc(User::getUpdatedTime);

        userService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, userDtoPage, "records");
        List<UserDto> userDtoList = pageInfo.getRecords().stream().map((it) -> {
            UserDto userDto = new UserDto();
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
            @RequestParam
            int status
    ) {
        User user = userService.getById(id);
        user.setIsValid(status);
        userService.save(user);
        return Result.success("更新成功");
    }

}
