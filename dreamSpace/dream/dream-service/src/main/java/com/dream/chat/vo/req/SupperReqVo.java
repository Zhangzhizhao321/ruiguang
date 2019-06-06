package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  基础请求vo
 * </p>
 *
 * @author lw
 * @since 2018-10-31
 */
@Data
public class SupperReqVo implements Serializable {

    private static final long serialVersionUID = -4093443085911159L;

    @ApiModelProperty("sessionId")
    protected String sessionId;

    @ApiModelProperty("用户id")
    protected String userId;

    @ApiModelProperty("用户名称")
    protected String userName;

    @ApiModelProperty("来源：IOS,Android,H5")
    protected String source;

    @ApiModelProperty("客户端ip")
    protected String clientIP;

}
