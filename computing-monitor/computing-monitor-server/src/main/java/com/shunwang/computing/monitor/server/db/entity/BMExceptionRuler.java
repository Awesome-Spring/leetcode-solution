package com.shunwang.computing.monitor.server.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * 裸金属异常规则实体类
 */
@Data
@TableName("bm_exception_ruler")
public class BMExceptionRuler {

    /** ID */
    @TableId(type = IdType.AUTO)
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

    /** 异常处理方式 */
    private Integer handleType;

    /** 扣除分数 */
    private Integer deductScore;

    ///**
    // * 是否启用  1 是 0 否
    // */
    //private Integer enabled;

    /** 创建时间 */
    private LocalDateTime createdTime;

    /** 更新时间 */
    private LocalDateTime updatedTime;
}

