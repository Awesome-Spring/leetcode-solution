package com.shunwang.computing.monitor.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created on 2024/11/8 13:54
 *
 * @author jiang tao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventReport {
    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件code
     */
    private Integer eventCode;
    /**
     * 事件发生时间
     */
    private String eventTime;

    /**
     * 是否正常
     */
    private boolean normal;
    /**
     * 事件详情
     */
    private Object eventDetail;


    public static EventReport createEvent(String eventType, Integer eventCode, boolean normal, Object eventDetail) {
        return new EventReport(eventType, eventCode, LocalDateTime.now().toString(), normal, eventDetail);
    }


}
