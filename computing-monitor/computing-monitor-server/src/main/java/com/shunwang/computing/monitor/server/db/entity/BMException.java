package com.shunwang.computing.monitor.server.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 裸金属异常实体类
 */
@Data
@TableName("bm_exception")
public class BMException {

    /** 异常ID */
    @TableId(type = IdType.AUTO)
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
    ///**
    // * 是否启用  1 是 0 否
    // */
    //private Integer enabled;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 更新时间 */
    private LocalDateTime updatedTime;
}

