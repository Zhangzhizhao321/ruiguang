package com.dream.common.core.exception;

import lombok.Getter;

/**
 * <p>
 * 业务异常类
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-08-30
 */
@Getter
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private ErrorCode errorCode;

	/**
	 * <p>
	 * 仅包含message, 没有cause, 也不记录栈异常, 性能最高
	 * </p>
	 *
	 * @param errorCode
	 */
	public BizException(ErrorCode errorCode) {
		super(errorCode.getMsg(), null, false, false);
		this.errorCode = errorCode;
	}

	/**
	 * <p>
	 * 仅包含message, 没有cause, 也不记录栈异常, 性能最高
	 * </p>
	 *
	 * @param message
	 */
	public BizException(String message) {
		super(message, null, false, false);
	}

	/**
	 * <p>
	 * 包含message和cause, 会记录栈异常
	 * </p>
	 *
	 * @param errorCode
	 * @param cause
	 */
	public BizException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMsg(), cause, false, true);
		this.errorCode = errorCode;
	}

	/**
	 * <p>
	 * 包含message和cause, 会记录栈异常
	 * </p>
	 *
	 * @param message
	 * @param cause
	 */
	public BizException(String message, Throwable cause) {
		super(message, cause, false, true);
	}
}
