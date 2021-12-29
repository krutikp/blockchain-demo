package org.my.block.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor(){

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("CORES-->"+ cores);
        int maxPoolSize = cores *(1 + 100/10);

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("Async-Threads--");

        return taskExecutor;
    }
}

