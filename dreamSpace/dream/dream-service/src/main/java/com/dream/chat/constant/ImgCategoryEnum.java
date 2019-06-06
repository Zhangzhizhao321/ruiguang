package com.dream.chat.constant;

public enum ImgCategoryEnum {

	DETAIL(1, "DETAIL", "详情描述"), KNOW(2, "KNOW", "报名须知"),INSTITUTION(3,"INSTITUTION","组织机构");

	public final int code;
	public final String name;
	public final String alias;

	private ImgCategoryEnum(int code, String name, String alias) {
		this.code = code;
		this.name = name;
		this.alias = alias;
	}

	public static ImgCategoryEnum of(int code) {
		for (ImgCategoryEnum type : values()) {
			if (code == type.code) {
				return type;
			}
		}
		return DETAIL;
	}

}
