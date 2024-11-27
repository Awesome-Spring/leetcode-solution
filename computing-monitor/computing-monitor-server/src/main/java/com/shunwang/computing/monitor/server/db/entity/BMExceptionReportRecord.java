package com.shunwang.computing.monitor.server.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 裸金属异常上报记录实体类
 */
@Data
@TableName("bm_exception_report_record")
public class BMExceptionReportRecord {

    /** 异常记录ID */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 异常id */
    private Integer exceptionId;
    /**
     * 异常code
     */
    private Integer exceptionCode;

    /** 调度记录ID */

    private Long bmScheduledRecordId;

    /** region ID */
    private Integer regionId;

    /** 裸金属ID */
    private Integer bmId;

    /** 裸金属MAC地址 */
    private String bmMac;
    /**
     * 时间上报时间
     */
    private LocalDateTime reportTime;

    /** 创建时间 */
    private LocalDateTime createdTime;


}
