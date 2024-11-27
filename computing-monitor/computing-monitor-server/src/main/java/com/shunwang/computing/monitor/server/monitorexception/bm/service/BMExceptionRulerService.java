package com.shunwang.computing.monitor.server.monitorexception.bm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.google.common.collect.Lists;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionRuler;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionRulerRel;
import com.shunwang.computing.monitor.server.db.mapper.BMExceptionRulerMapper;
import com.shunwang.computing.monitor.server.db.mapper.BMExceptionRulerRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2024/11/10 16:40
 *
 * @author jiang tao
 */
@Service
@Slf4j
public class BMExceptionRulerService extends MPJBaseServiceImpl<BMExceptionRulerMapper, BMExceptionRuler> {


    @Resource
    private BMExceptionRulerRelMapper bmExceptionRulerRelMapper;


    public List<BMExceptionRuler> getExceptionRulers(Integer exceptionId) {
        List<Integer> exceptionRulerIds = bmExceptionRulerRelMapper
                .selectList(new LambdaQueryWrapper<BMExceptionRulerRel>()
                        //.select(MonitorExceptionRulerRel::getRulerId)
                        .eq(BMExceptionRulerRel::getExceptionId, exceptionId))
                .stream()
                .map(BMExceptionRulerRel::getRulerId)
                .collect(Collectors.toList());

        return exceptionRulerIds.isEmpty() ? Lists.newArrayList() : listByIds(exceptionRulerIds);
    }
}
