package com.shunwang.computing.monitor.api.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created on 2024/11/12 13:49
 *
 * @author jiang tao
 */
@Data
public class BMExceptionResponse {
    /**
     * 异常id
     */
    private Integer id;

    /** 异常名称 */
    private String name;

    /** 异常code */
    private Integer code;

    /** 异常数据来源 */
    private String source;
    /**
     * 异常详情
     */
    private String detail;


    ///** 规则名称 */
    //private String rulerName;
    //
    ///** 异常判定标准 */
    //private Integer rulerDetail;
    //
    ///** 异常处理方式 */
    //private Integer handleType;
    //
    ///** 扣除分数 */
    //private Integer deductScore;

    /**
     * 是否启用(生效)  1 生效  0 未生效
     */
    private Boolean enabled;


    private LocalDateTime effectiveTime;

}
