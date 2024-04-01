package com.example.common.typeHandler;

import com.alibaba.fastjson.TypeReference;
import com.example.common.typeHandler.ListTypeHandler;

import java.util.List;

/**
 * @author Yushun Shao
 * @date 2024/3/21 23:40
 * 将ListTypeHandler<T>（T为任意对象），具体为特定的对象String
 */
public class StringTypeHandler extends ListTypeHandler<String> {
    @Override
    protected TypeReference<List<String>> specificType() {
        return new TypeReference<List<String>>() {
        };
    }
}

