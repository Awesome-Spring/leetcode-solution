package com.shunwang.computing.monitor.api.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created on 2024/11/12 13:49
 *
 * @author jiang tao
 */
@Data
public class BMExceptionRecordResponse {

    /** 异常记录ID */
    private Integer id;

    /** 异常id */
    private Integer exceptionId;
    /**
     * 异常code
     */
    private Integer exceptionCode;
    /**
     * 异常名称
     */
    private String exceptionName;



    /**
     * 上报时间
     */
    private LocalDateTime reportTime;

    /** region ID */
    private Integer regionId;

    /** 裸金属ID */
    private Integer bmId;

    /** 裸金属MAC地址 */
    private String bmMac;

    /** 实例IP */
    private String ip;
    /** 调度记录ID */
    private Long bmScheduledRecordId;

    /**
     * 处理记录id
     */
    private Integer handleRecordId;

    /**
     * 异常处理方式 1禁用 2扣分
     */
    private Integer handleType;
    /**
     * 扣除分数
     */
    private Integer deductScore;




}
