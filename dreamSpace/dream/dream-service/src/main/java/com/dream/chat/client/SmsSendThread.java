package com.dream.chat.client;

import com.dream.chat.entity.Sms;
import com.dream.chat.service.impl.SmsServiceImpl;
import com.dream.chat.service.SmsService;
import com.dream.common.core.util.ApplicationUtil;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author lw
 * @since 2018-10-24
 */
public class SmsSendThread implements Runnable {

	private AliSmsClient aliSmsClient;
	private SmsService smsService;
	//private SmsTemplateConfService smsTemplateConfService;
	private List<Sms> smsList;

	public SmsSendThread(List<Sms> smsList){
		this.smsList =smsList;
		this.smsService= ApplicationUtil.getBean(SmsServiceImpl.class);
		this.aliSmsClient = ApplicationUtil.getBean(AliSmsClient.class);
		//this.smsTemplateConfService= ApplicationUtil.getBean(SmsTemplateConfServiceImpl.class);
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {

	}
}
