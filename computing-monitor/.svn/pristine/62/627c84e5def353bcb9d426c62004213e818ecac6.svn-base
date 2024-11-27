package com.shunwang.computing.monitor.server.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 裸金属异常处理记录实体类
 */
@Data
@Accessors(chain = true)
@TableName("bm_exception_handle_record")
public class BMExceptionHandleRecord {

    /** 异常记录ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 裸金属异常上报记录id
     */
    private Integer bmExceptionReportRecordId;

    /** 异常处理 1禁用 2扣分 */
    private Integer handleType;
    /**
     * 扣除分数
     */
    private Integer deductScore;

    /** 创建时间 */
    private LocalDateTime createdTime;


}

