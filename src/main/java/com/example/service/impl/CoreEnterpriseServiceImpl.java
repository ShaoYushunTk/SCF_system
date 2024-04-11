package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.dto.CoreEnterpriseCreditRatingDto;
import com.example.entity.Company;
import com.example.entity.CoreEnterprise;
import com.example.entity.SmallMiddleEnterprise;
import com.example.exception.CommonException;
import com.example.mapper.CoreEnterpriseMapper;
import com.example.service.CompanyService;
import com.example.service.CoreEnterpriseService;
import com.example.service.SmallMiddleEnterpriseService;
import com.example.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private CompanyService companyService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommonUtils commonUtils;

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
    public Result getCreditRatingPage(
            int page,
            int pageSize,
            String name,
            String creditRating
    ) {
        Page<CoreEnterprise> coreEnterprisePage = new Page<>(page, pageSize);
        Page<CoreEnterpriseCreditRatingDto> dtoPage = new Page<>();
        commonUtils.customPage(coreEnterprisePage, new LambdaQueryWrapper<CoreEnterprise>(), this.entityClass, this.getClass());

        BeanUtils.copyProperties(coreEnterprisePage, dtoPage,  "records");
        List<CoreEnterpriseCreditRatingDto> records = coreEnterprisePage.getRecords().stream().map((it) -> {
            Company byId = companyService.getById(it.getId());
            BeanUtils.copyProperties(byId, it);
            CoreEnterpriseCreditRatingDto dto = new CoreEnterpriseCreditRatingDto();
            BeanUtils.copyProperties(it, dto);

            LambdaQueryWrapper<SmallMiddleEnterprise> smallMiddleEnterpriseLambdaQueryWrapper = new LambdaQueryWrapper<>();
            smallMiddleEnterpriseLambdaQueryWrapper.eq(SmallMiddleEnterprise::getCoreEnterpriseId, it.getId());
            dto.setSmallMiddleEnterpriseNameList(smallMiddleEnterpriseService.list(smallMiddleEnterpriseLambdaQueryWrapper).stream().map((sme) -> {
                Company company = companyService.getById(sme.getId());
                BeanUtils.copyProperties(company, sme);
                return sme.getName();
            }).toList());

            return dto;
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(creditRating)) {
            records = records.stream().filter(dto -> dto.getCreditRating().equals(creditRating)).collect(Collectors.toList());
        }
        dtoPage.setTotal(records.size());
        dtoPage.setRecords(records);
        return Result.success(dtoPage);
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

    @Override
    public Result page(
            int page,
            int pageSize,
            String name,
            String creditRating
    ) {
        Page<CoreEnterprise> coreEnterprisePage = new Page<>(page, pageSize);
        commonUtils.customPage(coreEnterprisePage, new LambdaQueryWrapper<CoreEnterprise>(), this.entityClass, this.getClass());

        List<CoreEnterprise> records = coreEnterprisePage.getRecords().stream().peek((it) -> {
            Company company = companyService.getById(it.getId());
            BeanUtils.copyProperties(company, it);
        }).toList();

        if (StringUtils.isNotEmpty(name)) {
            String regex = ".*" + Pattern.quote(name) + ".*";
            records = records.stream().filter(dto -> dto.getName().matches(regex)).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(creditRating)) {
            records = records.stream().filter(dto -> dto.getCreditRating().equals(creditRating)).collect(Collectors.toList());
        }
        coreEnterprisePage.setRecords(records);
        coreEnterprisePage.setTotal(records.size());
        return Result.success(coreEnterprisePage);
    }

    @Override
    public Result getCompanyCreditRatingById(String id) {
        Company company = companyService.getById(id);
        if (company == null) {
            throw new CommonException("企业不存在");
        }

        String creditRating;
        switch (company.getCompanyType()) {
            case CORE_ENTERPRISE:
                creditRating = getById(id).getCreditRating();
                break;
            case SMEs:
                String coreEnterpriseId = smallMiddleEnterpriseService.getById(id).getCoreEnterpriseId();
                CoreEnterprise coreEnterprise = getById(coreEnterpriseId);
                if (coreEnterprise == null) {
                    throw new CommonException("当前企业未关联核心企业，无法申请融资");
                }
                creditRating = coreEnterprise.getCreditRating();
                break;
            default:
                throw new CommonException("不支持的企业类型");
        }

        return Result.success(creditRating);
    }
}
