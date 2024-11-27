package com.shunwang.computing.monitor.server.monitorexception.bm.model;

import com.shunwang.computing.monitor.enums.BMExceptionEnum;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionHandleRecord;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionReportRecord;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created on 2024/11/20 15:42
 *
 * @author jiang tao
 */
@AllArgsConstructor
public class BMExceptionHandleDTO {

    private BMExceptionReportRecord bmExceptionReportRecord;


    private BMExceptionHandleRecord bmExceptionHandleRecord;

    public Integer getBmId() {
        return bmExceptionReportRecord.getBmId();
    }

    public String getBmMac() {
        return bmExceptionReportRecord.getBmMac();
    }

    public Integer getHandleType() {
        return bmExceptionHandleRecord.getHandleType();
    }

    public int getDeductScore() {
        return bmExceptionHandleRecord.getDeductScore();
    }

    public Integer getHandleRecordId() {
        return bmExceptionHandleRecord.getId();
    }

    public String getExceptionName() {
        return BMExceptionEnum.getNameByCode(bmExceptionReportRecord.getExceptionCode());
    }

    public LocalDateTime getEventTime() {
        return bmExceptionReportRecord.getReportTime();
    }
}
