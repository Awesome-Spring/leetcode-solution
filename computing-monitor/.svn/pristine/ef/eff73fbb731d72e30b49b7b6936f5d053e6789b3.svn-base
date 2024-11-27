package com.shunwang.computing.monitor.server.monitorexception.bm.model;

import com.shunwang.computing.monitor.server.db.entity.BMExceptionReportRecord;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionRuler;
import com.shunwang.computing.monitor.server.enums.BMExceptionFrequencyEnum;
import com.shunwang.computing.monitor.server.enums.BMExceptionHandleEnum;
import com.shunwang.computing.monitor.server.monitorexception.bm.compoent.BMExceptionRecorder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * Created on 2024/11/11 09:43
 *
 * @author jiang tao
 */
@AllArgsConstructor
@Slf4j
public class BMExceptionRulerDTO {

    private BMExceptionRuler bmExceptionRuler;


    public static BMExceptionRulerDTO create(BMExceptionRuler BMExceptionRuler) {
        return new BMExceptionRulerDTO(BMExceptionRuler);
    }

    public Integer getRulerId() {
        return bmExceptionRuler.getId();
    }

    public String getRulerName() {
        return bmExceptionRuler.getName();
    }


    public boolean match(BMExceptionReportRecord bmExceptionReportRecord) {
        Long bmScheduledRecordId = bmExceptionReportRecord.getBmScheduledRecordId();
        Integer countWindow = bmExceptionRuler.getCountWindow();
        Integer occurrenceCount = bmExceptionRuler.getOccurrenceCount();
        List<Integer> exceptionRecordList = BMExceptionRecorder.getBMRecordList(bmExceptionReportRecord.getBmId(), bmExceptionReportRecord.getExceptionCode());
        log.info("[{}] =>执行异常规则判定 => countWindow:{},occurrenceCount:{},异常情况:{}", bmScheduledRecordId, countWindow, occurrenceCount, exceptionRecordList);
        if (exceptionRecordList.size() < countWindow) {
            return false;
        }
        int exceptionCount = (int) exceptionRecordList.stream().filter(i -> Objects.equals(BMExceptionRecorder.EXCEPTION, i)).count();

        return exceptionCount >= occurrenceCount;
    }


    public int getDeductScore() {
        return bmExceptionRuler.getDeductScore();
    }

    public Integer getHandleType() {
        return bmExceptionRuler.getHandleType();
    }

    public String getRulerDetail() {
        return String.format("名称:%s,判断条件:%s,处理方式:%s,扣分:%d", getRulerName(),
                BMExceptionFrequencyEnum.getNameByCode(bmExceptionRuler.getCountWindow(), bmExceptionRuler.getOccurrenceCount()),
                BMExceptionHandleEnum.getNameByCode(getHandleType()),
                getDeductScore()
        );
    }
}
