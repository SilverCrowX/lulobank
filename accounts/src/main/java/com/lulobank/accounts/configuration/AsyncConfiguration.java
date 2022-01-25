package com.lulobank.accounts.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync

public class AsyncConfiguration implements AsyncConfigurer {

	public final static String ASYNC_NAME = "lulobank";

	@Bean(name = ASYNC_NAME)
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(25);
		executor.setQueueCapacity(200);
		executor.setThreadNamePrefix("AsynchThread-" + ASYNC_NAME);
		executor.initialize();
		return executor;
	}

}
