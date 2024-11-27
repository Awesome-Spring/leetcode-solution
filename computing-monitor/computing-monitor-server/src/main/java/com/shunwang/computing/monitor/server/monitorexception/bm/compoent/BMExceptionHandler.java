package com.shunwang.computing.monitor.server.monitorexception.bm.compoent;


import com.shunwang.cloud.kafka.message.common.base.KafkaMessage;
import com.shunwang.cloud.kafka.message.common.business.computing.BMExceptionHandleMsg;
import com.shunwang.cloud.kafka.message.tool.producer.MessageProducer;
import com.shunwang.computing.monitor.server.enums.BMExceptionHandleEnum;
import com.shunwang.computing.monitor.server.monitorexception.bm.model.BMExceptionHandleDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created on 2024/11/10 16:05
 *
 * @author jiang tao
 */
@Component
public class BMExceptionHandler {

    private static final DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    private MessageProducer<KafkaMessage> messageProducer;

    public void exceptionHandle(BMExceptionHandleDTO bmExceptionHandleDTO) {
        //  同一个 bm 到一个partition 使用bmId 作为key  ==>  kafka 默认路由模式。消息没指定Key就以轮询的方式将消息发送到各个分区。如果指定了Key，会对Key进行Hash并根据结果将消息分发到对应分区；
        BMExceptionHandleMsg message = new BMExceptionHandleMsg();
        message.setBmId(bmExceptionHandleDTO.getBmId());
        message.setBmMac(bmExceptionHandleDTO.getBmMac());
        message.setExecuteDisable(BMExceptionHandleEnum.DISABLE.getCode().equals(bmExceptionHandleDTO.getHandleType()));
        message.setDeductScore(bmExceptionHandleDTO.getDeductScore());
        message.setEventTime(bmExceptionHandleDTO.getEventTime());
        message.setSourceId(String.valueOf(bmExceptionHandleDTO.getHandleRecordId()));
        String remark = String.format("%s-异常注销 异常原因:%s", LocalDateTime.now().format(datetimeformatter), bmExceptionHandleDTO.getExceptionName());
        message.setRemark(remark);
        messageProducer.produce(message);

    }
}
