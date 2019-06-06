package com.dream.chat.component;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * common模块，包扫描配置
 * 让共公组件在服务模块中勿需配置
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-09-03
 */
@Configuration
@ComponentScan(basePackages = ConfigConstant.BASE_PACKAGES)
public class BasePackagesConfiguration {

}
