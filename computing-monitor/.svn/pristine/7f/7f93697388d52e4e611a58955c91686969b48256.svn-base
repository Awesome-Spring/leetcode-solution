package com.shunwang.computing.monitor.api.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created on 2024/11/12 13:49
 *
 * @author jiang tao
 */
@Data
public class BMExceptionRulerResponse {

    /**
     * 异常id
     */
    private Integer id;


    /** 规则名称 */
    private String name;

    /**
     *  数量窗口
     */
    private Integer countWindow;

    /**
     *  在数量窗口中出现的次数
     */
    private Integer occurrenceCount;
    /**
     *  规则详情 描述
     */
    private String detailDesc;

    /**
     * 异常处理方式
     */
    private Integer handleType;
    /**
     *  异常处理方式 描述
     */
    private String handleTypeDesc;

    /** 扣除分数 */
    private Integer deductScore;

    /**
     * 是否启用(生效)  1 生效  0 未生效
     */
    private Boolean enabled;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 规则对应异常
     */
    private Integer exceptionId;
    /**
     * 规则对应异常code
     */
    private Integer exceptionCode;
    /**
     *  规则对应异常名称
     */
    private String exceptionName;
}
