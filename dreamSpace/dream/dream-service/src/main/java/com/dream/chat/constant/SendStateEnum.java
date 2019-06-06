package com.dream.chat.constant;

import lombok.Getter;

/**
 * @author lw
 * @since 2018-10-23
 */
@Getter
public enum SendStateEnum {
	unSend(0,"未发送"), hasSend(1," 已发送"), failSend(2,"发送失败");

	private int code;
	private String desc;

	SendStateEnum(int code, String desc){
		this.code=code;
		this.desc=desc;
	}
}
