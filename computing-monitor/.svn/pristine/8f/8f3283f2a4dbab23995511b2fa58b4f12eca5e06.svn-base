package com.shunwang.computing.monitor.server.kafka.consumer;

import cn.hutool.core.bean.BeanUtil;
import com.shunwang.cloud.kafka.message.common.business.computing.BareMetalEventMsg;
import com.shunwang.computing.common.utils.BeanCopyUtils;
import com.shunwang.computing.monitor.server.monitorexception.bm.compoent.BMExceptionRecorder;
import com.shunwang.computing.monitor.enums.BMEventEnum;
import com.shunwang.computing.monitor.model.event.BMEventReport;
import com.shunwang.computing.monitor.server.db.entity.BMException;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionReportRecord;
import com.shunwang.computing.monitor.server.monitorexception.bm.compoent.BMExceptionProcessor;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionReportRecordService;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 2024/11/10 15:51
 *
 * @author jiang tao
 */
@Component
@Slf4j
public class BMEventConsumer {

    @Resource
    private BMExceptionReportRecordService bmExceptionReportRecordService;

    @Resource
    private BMExceptionService bmExceptionService;

    @Resource
    private BMExceptionProcessor bmExceptionProcessor;


    @KafkaListener(topics = {"bare_metal_event"}, containerFactory = "bmEventBatchFactory")
    public void consumeBatch(List<ConsumerRecord<Long, BareMetalEventMsg>> records) {
        for (ConsumerRecord<Long, BareMetalEventMsg> record : records) {
            try {
                BareMetalEventMsg bmExceptionMsg = record.value();
                Long bmScheduledRecordId = bmExceptionMsg.getBmScheduledRecordId();
                log.info(":[{}] => kafka message 开始消费,message:{}", bmScheduledRecordId, bmExceptionMsg);
                Integer eventCode = bmExceptionMsg.getEventCode();
                BMEventEnum bmEvent = BMEventEnum.getByEventCode(eventCode);
                if (bmEvent == null) {
                    return;
                }
                BMEventReport bmEventReport = BeanCopyUtils.copy(bmExceptionMsg.getEventDetail(), BMEventReport.class);
                Integer exceptionCode = bmEvent.getBmException().getCode();
                if (bmExceptionMsg.isNormal()) {
                    // 记录正常
                    BMExceptionRecorder.recordNormal(bmEventReport.getBmId(), exceptionCode);
                    return;
                }
                BMException bmException = bmExceptionService.getByCode(exceptionCode);
                BMExceptionReportRecord reportRecord = new BMExceptionReportRecord();
                BeanUtil.copyProperties(bmEventReport, reportRecord);
                reportRecord.setReportTime(bmExceptionMsg.getEventTime());
                reportRecord.setCreatedTime(LocalDateTime.now());
                reportRecord.setExceptionId(bmException.getId());
                reportRecord.setExceptionCode(bmException.getCode());
                try {
                    //同一个异常一个调度记录只记录一次
                    bmExceptionReportRecordService.save(reportRecord);
                } catch (DuplicateKeyException ignore) {
                    log.warn(":[{}] => 对应异常记录已存在,reportRecord:{}", bmScheduledRecordId, reportRecord);
                    return;
                }finally {
                    //记录异常
                    BMExceptionRecorder.recordException(bmEventReport.getBmId(), exceptionCode);
                }
                // todo 抽取handler
                bmExceptionProcessor.bmExceptionAsyncProcess(reportRecord);

            } catch (Exception e) {
                log.error("kafka message 消费失败,message:{}", record.value(), e);
            }
        }

    }
}
