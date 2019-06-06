package com.dream.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * 通用异常码    <br>
 * 异常码由 <1000 的数字表示 <br>
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-09-03
 */
@Getter
@ToString
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	/**
	 * 远程调度失败
	 */
	RPC_ERROR(-2, "远程调度失败"),
	/**
	 * 失败
	 */
	FAILED(-1, "失败"),

	SYSTEM_BUSY(1000,"系统繁忙"),

	/**
	 * 成功
	 */
	SUCCESS(0, "成功"),
	/**
	 * token获取失败
	 */
	TOKEN_GET_FAIL(1001, "token获取失败"),
	/**
	 * 请求token不匹配
	 */
	TOKEN_NOT_MATCH(1002, "请求token不匹配"),

	BID_FAIL(1003,"出价失败"),

	BID_FINISH(1004,"出价失败,拍卖结束"),

	NO_SIGN_UP(1005,"未报名");

	/**
	 * 错误编码
	 */
	private final long code;

	/**
	 * 错误描述
	 */
	private final String msg;

	/**
	 * 根据code查询异常码
	 *
	 * @param code 异常码
	 */
	public static CommonErrorCode findByCode(long code) {
		CommonErrorCode[] ecs = CommonErrorCode.values();
		for (CommonErrorCode ec : ecs) {
			if (ec.getCode() == code) {
				return ec;
			}
		}
		return SUCCESS;
	}
}
