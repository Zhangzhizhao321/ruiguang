package com.dream.chat.service;

import com.dream.chat.entity.Sms;
import com.dream.chat.vo.req.SendSmsReqVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 短信通知 服务类
 * </p>
 *
 * @author lw
 * @since 2018-10-23
 */
public interface SmsService extends SuperService<Sms> {


	void sendSms();

	/**
	 * 发送短信
	 * @param sendSmsReqVo
	 */
	void sendSms(SendSmsReqVo sendSmsReqVo, HttpServletRequest request);

	/**
	 * 验证手机号
	 */
	boolean verifyCode(String mobile,String code);


}
