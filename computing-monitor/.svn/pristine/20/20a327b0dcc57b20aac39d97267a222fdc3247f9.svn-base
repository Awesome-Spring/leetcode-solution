package com.shunwang.computing.monitor.server.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 裸金属异常与异常规则关系实体类
 */
@Data
@TableName("bm_exception_ruler_rel")
public class BMExceptionRulerRel {

    /** ID */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 异常id */
    private Integer exceptionId;

    /** 规则id */
    private Integer rulerId;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 更新时间 */
    private LocalDateTime updatedTime;
}

