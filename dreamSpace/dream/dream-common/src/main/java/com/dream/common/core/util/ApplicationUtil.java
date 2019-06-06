package com.dream.common.core.util;

import org.springframework.context.ApplicationContext;

/**
 * <p>
 *  Spring 上下文工具类
 * </p>
 *
 * @author lw
 * @since 2018-10-24
 */
public class ApplicationUtil {

	private ApplicationContext context;

	private static ApplicationUtil applicationUtil;

	public ApplicationUtil(ApplicationContext context) {
		System.out.println("=============初始化Spring上下文=============");
		this.context = context;
		applicationUtil=this;
	}

	public static  <T> T getBean(Class<T> clazz){
		return applicationUtil.context.getBean(clazz);
	}

	public static Object getBean(String beanName){
		return applicationUtil.context.getBean(beanName);
	}

}
