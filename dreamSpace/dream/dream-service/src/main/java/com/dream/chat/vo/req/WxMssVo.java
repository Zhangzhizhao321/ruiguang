package com.dream.chat.vo.req;

import com.dream.chat.cache.Wechat.TemplateData;
import lombok.Data;

import java.util.List;

@Data
public class WxMssVo {

    private String access_token;

    private String touser;

    private String template_id;

    private String form_id;

    private String page;

    private String request_url;

    private List<TemplateData> params;
}
