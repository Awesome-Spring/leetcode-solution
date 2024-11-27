package com.shunwang.computing.monitor.receive.controller;

import com.alibaba.fastjson.JSON;
import com.shunwang.cloud.kafka.message.common.base.KafkaMessage;
import com.shunwang.cloud.kafka.message.common.business.computing.BareMetalEventMsg;
import com.shunwang.cloud.kafka.message.tool.producer.MessageProducer;
import com.shunwang.computing.common.exception.ParamException;
import com.shunwang.computing.common.logback.context.LogContext;
import com.shunwang.computing.common.model.Result;
import com.shunwang.computing.common.utils.BeanCopyUtils;
import com.shunwang.computing.monitor.enums.EventTypeEnum;
import com.shunwang.computing.monitor.model.request.EventReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created on 2024/11/12 13:56
 *
 * @author jiang tao
 */
@RestController
@Slf4j
public class EventReportController {

    @Resource
    private MessageProducer<KafkaMessage> messageProducer;


    @PostMapping(value = "/event/report")
    @LogContext(value = "eventReport")
    public Result<Void> eventReport(@RequestBody EventReport request) {
        checkParam(request);
        if (Objects.equals(request.getEventType(), EventTypeEnum.BARE_METAL.getType())) {
            // 直接丢事件到kafka
            BareMetalEventMsg message = new BareMetalEventMsg();
            BeanCopyUtils.copy(request, message);
            String detailJsonStr = JSON.toJSONString(request.getEventDetail());
            message.setEventDetail(JSON.parseObject(detailJsonStr,BareMetalEventMsg.BMEvent.class));
            messageProducer.produce(message);
        }

        return Result.success();

    }

    private void checkParam(EventReport request) {
        if (!StringUtils.hasText(request.getEventType())) {
            throw new ParamException("eventType为空");
        }
        if (Objects.isNull(request.getEventCode())) {
            throw new ParamException("eventCode为空");
        }
        if (Objects.isNull(request.getEventDetail())) {
            throw new ParamException("eventDetail为空");
        }
    }


}
