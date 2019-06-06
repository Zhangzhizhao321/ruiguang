package com.dream.chat.cache.Wechat;

import lombok.Data;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-02-22
 */
@Data
public class TemplateData {
    private String value;
    private String color;

    public TemplateData(String value, String color) {
        this.value = value;
        this.color = color;
    }
    public TemplateData() {

    }

}
