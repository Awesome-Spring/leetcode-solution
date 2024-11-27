package com.shunwang.computing.monitor.api.model.request;

import com.shunwang.computing.common.model.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 2024/11/12 13:50 
 *
 * @author jiang tao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BMExceptionRecordReportPageRequest extends PageRequest {
    private static final long serialVersionUID = -1996906617973537535L;

    /**
     * 裸金属ids
     */
    private List<Integer> bmIdList;
    /**
     * regionId
     */
    private Integer regionId;


    /** 异常id */
    private Integer exceptionId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 创建时间之前
     */
    private LocalDateTime beforeCreateTime;


}
