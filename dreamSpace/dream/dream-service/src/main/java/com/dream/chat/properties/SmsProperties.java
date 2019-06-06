package com.dream.chat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lw
 * @since 2018-10-23
 */
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
@Component
public class SmsProperties {

	private String accessKeyId;

	private String accessKeySecret;

	private String product;

	private String domain;

	private String defaultConnectTimeout;

	private String defaultReadTimeout;

	private String signName;

}
