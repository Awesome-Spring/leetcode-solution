package com.shunwang.computing.monitor.server.monitorexception.bm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.shunwang.computing.monitor.server.db.entity.BMException;
import com.shunwang.computing.monitor.server.db.mapper.BMExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2024/11/10 16:40
 *
 * @author jiang tao
 */
@Service
@Slf4j
public class BMExceptionService extends MPJBaseServiceImpl<BMExceptionMapper, BMException> {

    // 创建 Guava 缓存
    private final Cache<Integer, BMException> cache;

    public BMExceptionService() {
        // 设置缓存的最大容量和过期时间
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(100) // 设置最大缓存大小
                .expireAfterWrite(10, TimeUnit.MINUTES) // 设置缓存过期时间
                .build();
    }

    public BMException getByCode(int code) {
        // 首先从缓存中获取
        BMException cachedException = cache.getIfPresent(code);
        if (cachedException != null) {
            return cachedException;
        }
        // 如果缓存中没有，则从数据库中查询
        BMException exception = getOne(new LambdaQueryWrapper<BMException>()
                .eq(BMException::getCode, code)
                .last("limit 1"));

        // 将查询结果放入缓存
        if (exception != null) {
            cache.put(code, exception);
        }
        return exception;
    }


    public List<BMException> getAll(int code) {
        return list(new LambdaQueryWrapper<BMException>().last("limit 1000"));
    }


}
