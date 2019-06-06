package com.dream.chat.constant;

public enum ProjectCategoryEnum {

	CONSUMPTION(1,"消费"),MEDICAL(2,"医疗"),TRAVEL(3,"旅游"),STUDY(4,"学习"),IDEA(5,"创意");

	public Integer code;
	public String alias;

	ProjectCategoryEnum(Integer code, String alias){
		this.code=code;
		this.alias=alias;
	}

	public static ProjectCategoryEnum of(int code) {
		for (ProjectCategoryEnum type : values()) {
			if (code == type.code) {
				return type;
			}
		}
		return CONSUMPTION;
	}
}
