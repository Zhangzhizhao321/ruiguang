package com.dream.chat.cache.Wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	 private String ToUserName;

	 private String FromUserName;

	 private Long CreateTime;

	 private String MsgType;

	 private String Content;

	 private String MsgId;

}
