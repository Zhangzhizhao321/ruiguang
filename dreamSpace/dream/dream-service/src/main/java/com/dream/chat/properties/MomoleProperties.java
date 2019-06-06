package com.dream.chat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-29
 */
@Data
@Component
@ConfigurationProperties(prefix = "dream.chat")
public class MomoleProperties {

    private String isTest;

}
