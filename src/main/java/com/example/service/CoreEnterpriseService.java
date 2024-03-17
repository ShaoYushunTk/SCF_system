package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.CoreEnterprise;
import com.example.mapper.CoreEnterpriseMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Yushun Shao
 * @date 2024/2/29 10:39
 */
public interface CoreEnterpriseService extends IService<CoreEnterprise> {
    CoreEnterprise getById(String id);

    Result updateByDto(CoreEnterprise coreEnterprise, String id);

    Result saveCreditRating(String id, String creditRating);

    Result getCreditRatingList(String creditRating);

    Boolean removeByCompanyId(String id);
}
