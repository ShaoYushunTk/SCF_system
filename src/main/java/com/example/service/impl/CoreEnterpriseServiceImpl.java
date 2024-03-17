package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.dto.CoreEnterpriseCreditRatingDto;
import com.example.entity.CoreEnterprise;
import com.example.entity.SmallMiddleEnterprise;
import com.example.exception.CommonException;
import com.example.mapper.CoreEnterpriseMapper;
import com.example.service.CoreEnterpriseService;
import com.example.service.SmallMiddleEnterpriseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:40
 */
@Service
public class CoreEnterpriseServiceImpl extends ServiceImpl<CoreEnterpriseMapper, CoreEnterprise> implements CoreEnterpriseService {
    @Autowired
    private CoreEnterpriseMapper coreEnterpriseMapper;

    @Autowired
    private SmallMiddleEnterpriseService smallMiddleEnterpriseService;

    @Autowired
    private RedisTemplate redisTemplate;

    public CoreEnterprise getById(
            String id
    ) {
        QueryWrapper<CoreEnterprise> queryWrapper = new QueryWrapper<>();

        // 排除父类的列
        queryWrapper.select(CoreEnterprise.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()))
                .eq("id", id);

        return coreEnterpriseMapper.selectOne(queryWrapper);
    }

    public boolean saveOrUpdate(CoreEnterprise coreEnterprise) {
        QueryWrapper<CoreEnterprise> queryWrapper = new QueryWrapper<>();

        queryWrapper.select(CoreEnterprise.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()))
                .eq("id", coreEnterprise.getId());

        return coreEnterpriseMapper.update(coreEnterprise, queryWrapper) > 0;
    }

    public List<CoreEnterprise> list(LambdaQueryWrapper<CoreEnterprise> lambdaQueryWrapper) {
        lambdaQueryWrapper.select(CoreEnterprise.class, info -> !"name".equals(info.getColumn())
                        && !"company_type".equals(info.getColumn()) && !"contact_info".equals(info.getColumn()));

        return coreEnterpriseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public Result updateByDto(CoreEnterprise coreEnterprise, String id) {
        LambdaQueryWrapper<CoreEnterprise> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoreEnterprise::getId, id);
        coreEnterpriseMapper.update(coreEnterprise, lambdaQueryWrapper);
        return Result.success("核心企业更新成功");
    }

    @Override
    public Result saveCreditRating(
            String id,
            String creditRating
    ) {
        CoreEnterprise coreEnterprise = this.getById(id);
        if (coreEnterprise == null) {
            return Result.error("核心企业不存在");
        }
        coreEnterprise.setCreditRating(creditRating);
        this.saveOrUpdate(coreEnterprise);
        return Result.success("核心企业信用评级保存成功");
    }

    @Override
    public Result getCreditRatingList(
            String creditRating
    ) {
        LambdaQueryWrapper<CoreEnterprise> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(creditRating), CoreEnterprise::getCreditRating, creditRating);

        List<CoreEnterpriseCreditRatingDto> dtoList = this.list(lambdaQueryWrapper).stream().map((it) -> {
            CoreEnterpriseCreditRatingDto dto = new CoreEnterpriseCreditRatingDto();
            BeanUtils.copyProperties(it, dto);

            LambdaQueryWrapper<SmallMiddleEnterprise> smallMiddleEnterpriseLambdaQueryWrapper = new LambdaQueryWrapper<>();
            smallMiddleEnterpriseLambdaQueryWrapper.eq(SmallMiddleEnterprise::getCoreEnterpriseId, it.getId());
            List<SmallMiddleEnterprise> smallMiddleEnterpriseList = smallMiddleEnterpriseService.list(smallMiddleEnterpriseLambdaQueryWrapper);
            dto.setSmallMiddleEnterpriseList(smallMiddleEnterpriseList);

            return dto;
        }).toList();

        return Result.success(dtoList);
    }

    @Override
    public Boolean removeByCompanyId(String id) {
        // 删除时检查当前核心企业是否关联中小企业
        LambdaQueryWrapper<SmallMiddleEnterprise> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SmallMiddleEnterprise::getCoreEnterpriseId, id);
        SmallMiddleEnterprise one = smallMiddleEnterpriseService.getOne(lambdaQueryWrapper);
        if (one != null) {
            throw new CommonException("当前核心企业已关联中小企业，无法删除");
        }
        return this.removeById(id);
    }
}
