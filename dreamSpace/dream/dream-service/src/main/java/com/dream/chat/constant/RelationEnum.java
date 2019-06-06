package com.dream.chat.constant;

public enum RelationEnum {

	INSTITUTION(0,"机构"),ONESELF(1,"本人"),FATHER(2,"父亲"),
	MOM(3,"母亲"),SON(4,"儿子"),DAUGHTER(5,"女儿"),
	HUSBAND(6,"丈夫"),WIFE(7,"妻子"),OTHER(8,"其他");
	public Integer code;
	public String alias;

	RelationEnum(Integer code, String alias){
		this.code=code;
		this.alias=alias;
	}

	public static RelationEnum of(int code) {
		for (RelationEnum type : values()) {
			if (code == type.code) {
				return type;
			}
		}
		return ONESELF;
	}
}
