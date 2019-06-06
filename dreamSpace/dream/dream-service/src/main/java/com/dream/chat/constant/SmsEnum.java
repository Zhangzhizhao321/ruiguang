package com.dream.chat.constant;

public enum SmsEnum {

	VCODE (1, "VCODE", "验证码");

	public final int code;
	public final String name;
	public final String alias;

	private SmsEnum(int code, String name, String alias) {
		this.code = code;
		this.name = name;
		this.alias = alias;
	}

	public static SmsEnum of(int code) {
		for (SmsEnum type : values()) {
			if (code == type.code) {
				return type;
			}
		}
		return VCODE;
	}

}
