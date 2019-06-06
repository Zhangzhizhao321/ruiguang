package com.dream.chat.constant;

public enum GenderEnum {

	WOMAN(2, "WOMAN", "女"), MAN(1, "MAN", "男");

	public final int code;
	public final String name;
	public final String alias;

	private GenderEnum(int code, String name, String alias) {
		this.code = code;
		this.name = name;
		this.alias = alias;
	}

	public static GenderEnum of(int code) {
		for (GenderEnum type : values()) {
			if (code == type.code) {
				return type;
			}
		}
		return MAN;
	}

}
