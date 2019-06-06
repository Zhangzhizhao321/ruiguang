package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-10-31
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "用户登录", description = "用户登录")
public class LoginReqVo extends SupperReqVo{

    @ApiModelProperty("nickName")
    private String nickName;

    @ApiModelProperty("avatarUrl")
    private String avatarUrl;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("推荐人Id")
    private String inviteId;

    //后面填上
    private String openId;

    private String h5OpenId;

    private String appOpenId;

    private String unionId;

    private String mobile;

    private Integer flag;

}
