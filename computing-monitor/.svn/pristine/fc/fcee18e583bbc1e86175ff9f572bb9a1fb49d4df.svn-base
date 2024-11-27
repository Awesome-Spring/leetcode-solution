package com.shunwang.computing.monitor.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jr.fan
 */
@Slf4j
@Profile("!junit")
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "monitorAsyncExecutor", destroyMethod = "destroy")
    public ThreadPoolTaskExecutor bizAsyncExecutorAsyncExecutor() {
        int core = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(core);
        threadPoolTaskExecutor.setMaxPoolSize(core * 2);
        threadPoolTaskExecutor.setKeepAliveSeconds(30);
        threadPoolTaskExecutor.setQueueCapacity(10000);
        // 超过线程池，caller run
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setThreadNamePrefix("monitor-AsyncExecutor-");
        threadPoolTaskExecutor.setDaemon(true);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
