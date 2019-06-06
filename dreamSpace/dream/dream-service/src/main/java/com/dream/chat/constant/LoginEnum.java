package com.dream.chat.constant;

public enum LoginEnum {

	PROGRAM(0,"小程序"),H5(1,"h5"),APP(2,"APP");

	public Integer code;
	public String alias;

	LoginEnum(Integer code, String alias){
		this.code=code;
		this.alias=alias;
	}
}
