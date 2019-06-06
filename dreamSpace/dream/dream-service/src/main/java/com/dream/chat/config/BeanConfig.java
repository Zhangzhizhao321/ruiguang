package com.dream.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *
 * </p>
 * @author lw
 * @since 2018-10-24
 */
@Configuration
public class BeanConfig {

	/**
	 * 短信发送线程池
	 * @return
	 */
	@Bean("smsExecutorService")
	public ExecutorService smsExecutorService(){
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		return executorService;
	}

	/**
	 * 邮件发送线程池
	 * @return
	 */
	@Bean("emailExecutorService")
	public ExecutorService emailExecutorService(){
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		return executorService;
	}


}
