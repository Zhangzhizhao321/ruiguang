package com.dream.chat.client;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dream.common.core.util.MmlJsonUtil;
import com.dream.chat.cache.dto.SmsBatchDTO;
import com.dream.chat.cache.dto.SmsDTO;
import com.dream.chat.properties.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 阿里短信服务客户端
 * </p>
 * @author lw
 * @since 2018-10-24
 */
@Component
public class AliSmsClient {

	private final SmsProperties smsProperties;

	private final String region = "cn-hangzhou";
	private final String endpointName = "cn-hangzhou";

	private IAcsClient acsClient;

	@Autowired
	public AliSmsClient(SmsProperties smsProperties){
		this.smsProperties = smsProperties;
		init();
	}

	private void init(){

		String defaultConnectTimeout= smsProperties.getDefaultConnectTimeout();
		String defaultReadTimeout= smsProperties.getDefaultReadTimeout();
		String product= smsProperties.getProduct();//短信API产品名称（短信产品名固定，无需修改）
		String domain= smsProperties.getDomain();//短信API产品域名（接口地址固定，无需修改）

		System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
		System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);
		/*final IClientProfile clientProfile = DefaultProfile.getProfile(
				this.region, smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());*/
		final IClientProfile clientProfile = DefaultProfile.getProfile(
				this.region, "LTAIFjERhXk5sThi","fwdIEgb9ZSiMlHUGwCTOSG13AfhoeR");
		try {
			DefaultProfile.addEndpoint(region, endpointName, product, domain);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		acsClient = new DefaultAcsClient(clientProfile);
	}

	public SendSmsResponse send(SmsDTO smsDto){

		String phoneNumber=smsDto.getPhone();
		//String signName=smsDto.getSingName();
		String signName=smsProperties.getSignName();
		String templatecode=smsDto.getTemplateCode();
		String templateParam=smsDto.getTemplateParam();
		String outId=smsDto.getOutId();
		SendSmsResponse response=null;
		try {
			//组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			//使用post提交
			request.setMethod(MethodType.POST);
			//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
			request.setPhoneNumbers(phoneNumber);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
			//request.setTemplateCode(templatecode);
			request.setTemplateCode("SMS_81680012");
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			//request.setTemplateParam(templateParam);
			String userName = "么么乐用户";
			request.setTemplateParam("{\"name\":\"" + userName + "\", \"code\":\"" + templateParam + "\"}");
			//可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			//request.setSmsUpExtendCode("90997");
			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			//request.setOutId(outId);
			request.setOutId("83515032");
			//请求失败这里会抛ClientException异常

			response = acsClient.getAcsResponse(request);

			/*SendBatchSmsRequest batchSmsRequest = new SendBatchSmsRequest();

			SendBatchSmsResponse sendSmsResponse = acsClient.getAcsResponse(batchSmsRequest);*/
			return response;
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	/**
	 * 批量发送短信
	 * @param smsDto
	 * @return
	 */
	public SendBatchSmsResponse sendBatchSms(SmsBatchDTO smsDto){
		String phoneNumber= MmlJsonUtil.objToJson(smsDto.getPhones());
		String signName=MmlJsonUtil.objToJson(smsDto.getSingNames());
		String templatecode=smsDto.getTemplateCode();
		String templateParam=MmlJsonUtil.objToJson(smsDto.getTemplateParams());
		SendBatchSmsResponse response=null;
		try {
			//组装请求对象
			SendBatchSmsRequest  request = new SendBatchSmsRequest ();
			//使用post提交
			request.setMethod(MethodType.POST);
			request.setPhoneNumberJson(phoneNumber);
			request.setSignNameJson(signName);
			request.setTemplateCode(templatecode);
			request.setTemplateParamJson(templateParam);
			response = acsClient.getAcsResponse(request);
			return response;
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
