package com.shunwang.computing.monitor.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created on 2024/11/11 10:26
 *
 * @author jiang tao
 */
@Configuration
public class BMExceptionEnabledConfig {

    @Value("${monitor.exception.disabled.id:}")
    private List<Integer> exceptionDisabledIds;

    @Value("${monitor.exceptionRuler.disabled.id:}")
    private List<Integer> exceptionRulerDisabledIds;


    public boolean getBMExceptionEnabledById(Integer exceptionId) {
        return !exceptionDisabledIds.contains(exceptionId);
    }

    public boolean getBMExceptionRulerEnabledById(Integer rulerId) {
        return !exceptionRulerDisabledIds.contains(rulerId);
    }
}
