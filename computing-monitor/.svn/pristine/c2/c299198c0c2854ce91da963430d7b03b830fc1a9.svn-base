package com.shunwang.computing.monitor.server.monitorexception.bm.compoent;

import com.shunwang.computing.monitor.server.config.BMExceptionEnabledConfig;
import com.shunwang.computing.monitor.server.db.entity.BMException;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionHandleRecord;
import com.shunwang.computing.monitor.server.db.entity.BMExceptionReportRecord;
import com.shunwang.computing.monitor.server.monitorexception.bm.model.BMExceptionHandleDTO;
import com.shunwang.computing.monitor.server.monitorexception.bm.model.BMExceptionRulerDTO;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionHandleRecordService;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionRulerService;
import com.shunwang.computing.monitor.server.monitorexception.bm.service.BMExceptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2024/11/10 17:02
 *
 * @author jiang tao
 */
@Component
public class BMExceptionProcessor {

    private static final Logger log = LoggerFactory.getLogger(BMExceptionProcessor.class);

    @Resource
    private BMExceptionService BMExceptionService;

    @Resource
    private BMExceptionRulerService BMExceptionRulerService;

    @Resource
    private BMExceptionHandler bmExceptionHandler;

    @Resource
    private BMExceptionEnabledConfig BMExceptionEnabledConfig;

    @Resource
    private BMExceptionHandleRecordService bmExceptionHandleRecordService;

    @Async("monitorAsyncExecutor") // todo 抽取
    public void bmExceptionAsyncProcess(BMExceptionReportRecord bmExceptionReportRecord) {
        Long bmScheduledRecordId = bmExceptionReportRecord.getBmScheduledRecordId();
        log.info(":[{}] =>进行异常监控处理", bmScheduledRecordId);
        //获取对应异常
        Integer exceptionCode = bmExceptionReportRecord.getExceptionCode();
        BMException bmException = BMExceptionService.getByCode(exceptionCode);
        if (bmException == null) {
            return;
        }
        boolean enabled = BMExceptionEnabledConfig.getBMExceptionEnabledById(bmException.getId());
        log.info(":[{}] =>异常监控 => 异常名称:{},启用情况:[{}]", bmScheduledRecordId, bmException.getName(), enabled ? "启用" : "禁用");
        //1.异常监控是否启用
        if (!enabled) {
            log.info("[{}] =>异常监控未启用,异常code:{}", bmScheduledRecordId, exceptionCode);
            return;
        }
        //2.获取异常对应规则
        List<BMExceptionRulerDTO> exceptionRulers = BMExceptionRulerService.getExceptionRulers(bmException.getId()).stream().map(BMExceptionRulerDTO::create).collect(Collectors.toList());
        for (BMExceptionRulerDTO exceptionRuler : exceptionRulers) {
            boolean rulerEnabled = BMExceptionEnabledConfig.getBMExceptionRulerEnabledById(exceptionRuler.getRulerId());
            log.info("[{}] =>异常处理规则 启用情况:[{}],规则详情:{}", bmScheduledRecordId, rulerEnabled ? "启用" : "禁用", exceptionRuler.getRulerDetail());
            if (!rulerEnabled) {
                return;
            }
            //3.异常规则匹配
            if (!exceptionRuler.match(bmExceptionReportRecord)) {
                log.info("[{}] =>执行异常处理未成功,不符合规则", bmScheduledRecordId);
                return;
            }
            log.info("[{}] =>符合规则,即将执行异常处理", bmScheduledRecordId);
            Integer handleType = exceptionRuler.getHandleType();
            //4.保存异常处理记录
            BMExceptionHandleRecord handleRecord = saveExceptionHandleRecord(bmExceptionReportRecord.getId(), handleType, exceptionRuler.getDeductScore());
            //5.执行规则异常处理
            bmExceptionHandler.exceptionHandle(new BMExceptionHandleDTO(bmExceptionReportRecord, handleRecord));
            log.info("[{}] =>执行异常处理成功,handleRecord:{}", bmScheduledRecordId, handleRecord);
            //6.清空计数器
            BMExceptionRecorder.reset(bmExceptionReportRecord.getBmId(), exceptionCode);
            log.info("[{}] =>异常记录reset,异常:{},bmId:{}", bmScheduledRecordId, bmException.getName(), bmExceptionReportRecord.getBmId());
        }

    }

    private BMExceptionHandleRecord saveExceptionHandleRecord(Integer bmExceptionRecordId, Integer handleType, int deductScore) {
        BMExceptionHandleRecord bmExceptionHandleRecord = new BMExceptionHandleRecord()
                .setBmExceptionReportRecordId(bmExceptionRecordId)
                .setHandleType(handleType)
                .setDeductScore(deductScore)
                .setCreatedTime(LocalDateTime.now());
        bmExceptionHandleRecordService.save(bmExceptionHandleRecord);

        return bmExceptionHandleRecord;

    }
}
