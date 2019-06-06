package com.dream.chat.constant;

public enum PayTypeEnum {

	WALLET(0, "WALLET", "钱包"), WEIXIN(1, "WEIXIN", "微信支付"), ALIPAY(2, "ALIPAY", "支付宝"), UNIONPAY(3, "UNIONPAY", "银联支付");

	public final int code;
	public final String name;
	public final String alias;

	private PayTypeEnum(int code, String name, String alias) {
		this.code = code;
		this.name = name;
		this.alias = alias;
	}

	public static PayTypeEnum of(int code) {
		for (PayTypeEnum type : values()) {
			if (code == type.code) {
				return type;
			}
		}
		return WALLET;
	}

}
