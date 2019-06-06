package com.dream.chat.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-11-09    ${env}
 */

@Configuration
@PropertySource("classpath:/momole.properties")
public class MomolePropectiesConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

