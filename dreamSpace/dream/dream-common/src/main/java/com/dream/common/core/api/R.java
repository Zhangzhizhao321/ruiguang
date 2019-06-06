package com.dream.common.core.api;

import java.io.Serializable;
import java.util.Optional;

import com.dream.common.core.exception.BizException;
import com.dream.common.core.exception.CommonErrorCode;
import com.dream.common.core.exception.ErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 接口响应信息
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-09-03
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "响应信息")
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	@ApiModelProperty(value = "错误码", required = true)
	private long code;

	/**
	 * 错误码描述
	 */
	@ApiModelProperty(value = "错误码描述", required = true)
	private String msg;

	/**
	 * 结果集
	 */
	@ApiModelProperty(value = "结果集", required = true)
	private T data;

	public R() {
		// to do nothing
	}

	public R(ErrorCode errorCode) {
		errorCode = Optional.ofNullable(errorCode).orElse(CommonErrorCode.FAILED);
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, CommonErrorCode.SUCCESS);
	}

	public static <T> R<T> failed(String msg) {
		return restResult(null, CommonErrorCode.FAILED.getCode(), msg);
	}

	public static <T> R<T> failed(ErrorCode errorCode) {
		return restResult(null, errorCode);
	}

	public static <T> R<T> restResult(T data, ErrorCode errorCode) {
		return restResult(data, errorCode.getCode(), errorCode.getMsg());
	}

	private static <T> R<T> restResult(T data, long code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

	public boolean ok() {
		return CommonErrorCode.SUCCESS.getCode() == code;
	}

	/**
	 * 服务间调用非业务正常，异常直接释放
	 */
	public T serviceData() {
		if (!ok()) {
			throw new BizException(this.msg);
		}
		return data;
	}
}
