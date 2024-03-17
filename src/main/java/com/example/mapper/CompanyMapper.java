package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Company;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Yushun Shao
 * @date 2024/2/24 21:55
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
}
