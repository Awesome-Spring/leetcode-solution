package com.shunwang.computing.monitor.client.config;

import com.shunwang.computing.monitor.client.service.EventReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2024/11/19 15:12
 *
 * @author jiang tao
 */
@Configuration
public class MonitorClientAutoConfig {

    @Value("${bm.event.report.url:/open/domain/monitor/api/event/report}")
    private String reportUrl;

    @Value("${domain.credentials.host}")
    private String host;
    @Value("${domain.credentials.accessKey}")
    private String accessKey;
    @Value("${domain.credentials.secretKey}")
    private String secretKey;


    @Bean(initMethod = "init", destroyMethod = "destroy")
    public EventReportService eventReportService() {
        return new EventReportService(reportUrl, host, accessKey, secretKey);
    }
}
