package com.dream.chat.cache.Wechat;

import lombok.Data;

import java.util.Map;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-02-22
 */
@Data
public class WechatTemplate {
    private String touser;

    private String template_id;

    private String url;

    private Map<String, TemplateData> data;

}
