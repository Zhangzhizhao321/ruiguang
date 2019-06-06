package com.dream.chat.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-24
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "发送短信", description = "发送短信")
public class SendSmsReqVo extends SupperReqVo {

	private static final long serialVersionUID = 1L;

	//@Pattern(regexp = "^(1)[0-9]\\d{10}",message="手机号码格式不正确")
	@NotBlank
	@ApiModelProperty("手机号码")
	private String phoneNumber;

	/*@NotBlank(message="短信内容不能为空")*/
	@ApiModelProperty("短信内容")
	private String content;

	@NotBlank(message="模板类型不能为空")
	@ApiModelProperty("模板类型（VCODE：验证码）")
	private String templateType;

}
