package com.shunwang.computing.monitor.client.factory;

import com.shunwang.computing.monitor.enums.BMEventEnum;
import com.shunwang.computing.monitor.enums.EventTypeEnum;
import com.shunwang.computing.monitor.model.event.BMEventReport;
import com.shunwang.computing.monitor.model.request.EventReport;

/**
 * Created on 2024/11/8 13:54
 *
 * @author jiang tao
 */

public class ReportEventRequestFactory {


    public static EventReport createAbnormalBMEvent(BMEventEnum bmEventEnum, BMEventReport event) {
        return EventReport.createEvent(EventTypeEnum.BARE_METAL.getType(), bmEventEnum.getEventCode(), false, event);
    }


    public static EventReport createNormalBMEvent(BMEventEnum bmEventEnum, BMEventReport event) {
        return EventReport.createEvent(EventTypeEnum.BARE_METAL.getType(), bmEventEnum.getEventCode(), true, event);
    }


}
