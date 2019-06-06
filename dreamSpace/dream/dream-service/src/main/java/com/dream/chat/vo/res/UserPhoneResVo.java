package com.dream.chat.vo.res;

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
@ApiModel(value = "用户手机获取", description = "用户手机获取")
public class UserPhoneResVo {

    @ApiModelProperty("phoneNumber")
    private String phoneNumber;

    @ApiModelProperty("purePhoneNumber")
    private String purePhoneNumber;

    @ApiModelProperty("countryCode")
    private String countryCode;

    @ApiModelProperty("watermark")
    private String watermark;

}
