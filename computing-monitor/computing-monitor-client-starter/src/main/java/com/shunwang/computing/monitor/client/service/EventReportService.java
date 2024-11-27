package com.shunwang.computing.monitor.client.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shunwang.cloud.signature.utils.HttpUtils;
import com.shunwang.computing.monitor.model.request.EventReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2024/11/8 15:45
 *
 * @author jiang tao
 */
public class EventReportService {

    private static final Logger log = LoggerFactory.getLogger(EventReportService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ExecutorService executorService;

    private final String reportUrl;

    private final String host;

    private final String accessKey;

    private final String secretKey;

    public EventReportService(String reportUrl, String host, String accessKey, String secretKey) {
        this.reportUrl = reportUrl;
        this.host = host;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public void init() {
        executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                run -> {
                    Thread thread = new Thread(run);
                    thread.setName("bm-exception-reporter-" + thread.getId());
                    return thread;
                },
                (r, executor) -> {
                    log.error("task {} rejected from {}", r.toString(), executor.toString());
                }
        );

    }


    public void reportToIDC(EventReport eventReport) {
        executorService.execute(() -> {
            try {
                Map params = objectMapper.convertValue(eventReport, Map.class);
                String responseBody = HttpUtils.doPostMd5(String.format("%s%s", host, reportUrl), params, HttpUtils.APPLICATION_JSON, accessKey, secretKey, 5000);
                log.info("report  {}  to MonitorServer result:{}", params, responseBody);
            } catch (Exception e) {
                log.error("report  to MonitorServer error:{}", e.getMessage());
            }
        });

    }

    public void destroy() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

}
