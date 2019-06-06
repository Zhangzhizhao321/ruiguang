package com.dream.chat.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.SmsDTO;
import com.dream.chat.client.AliSmsClient;
import com.dream.chat.client.SmsSendThread;
import com.dream.chat.constant.RedisConstant;
import com.dream.chat.constant.SendSmsResponseConstant;
import com.dream.chat.constant.SendStateEnum;
import com.dream.chat.entity.Sms;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.SmsMapper;
import com.dream.chat.service.SmsService;
import com.dream.chat.vo.req.SendSmsReqVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 * 短信通知 服务实现类
 * </p>
 *
 * @author lw
 * @since 2018-10-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmsServiceImpl extends SuperServiceImpl<SmsMapper, Sms> implements SmsService {

	private final ExecutorService smsExecutorService;
	//private final SmsTemplateConfService smsTemplateConfService;
	private final AliSmsClient aliSmsClient;

	private final RedisService redisService;
	@Override
	public void sendSms() {
		Sms sms = new Sms();
		sms.setSendState(SendStateEnum.unSend.getCode());
		Wrapper smsWrapper = new QueryWrapper(sms);
		int currPage=1,size=10;
		while(true){
			Page<Sms> page = new Page(currPage++, size);
			IPage resultPage = super.page(page, smsWrapper);
			List<Sms> smsList = resultPage.getRecords();
			if(smsList.size()==0){
				break;
			}
			SmsSendThread smsSendThread =new SmsSendThread(smsList);
			smsExecutorService.execute(smsSendThread);
		}
	}

	@Override
	public void sendSms(SendSmsReqVo sendSmsReqVo, HttpServletRequest request) {
		/*SmsTemplateConf smsTemplateConf = smsTemplateConfService.getSmsTemplateConfByType(sendSmsReqVo.getTemplateType());
		if(smsTemplateConf==null){
			throw new BizException("模板类型不正确");
		}*/

		SmsDTO smsDto = new SmsDTO();
		smsDto.setPhone(sendSmsReqVo.getPhoneNumber());
		/*smsDto.setSingName(smsTemplateConf.getSignName());
		smsDto.setTemplateCode(smsTemplateConf.getTemplateCode());*/
		smsDto.setTemplateParam(sendSmsReqVo.getContent());
		smsDto.setOutId(String.valueOf(System.currentTimeMillis()));
		//发送短信
		SendSmsResponse response = aliSmsClient.send(smsDto);

		Sms sms=new Sms();
		if(response ==null || !SendSmsResponseConstant.OK.equals(response.getCode())){
			//发送失败
			sms.setSendState(SendStateEnum.failSend.getCode());
		}else{
			//发送成功
            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("code", sendSmsReqVo.getContent());
            codeMap.put("createTime", System.currentTimeMillis());
            request.getSession().setAttribute(sendSmsReqVo.getPhoneNumber(), codeMap);
			sms.setSendState(SendStateEnum.hasSend.getCode());
		}
		sms.setPhoneNumber(sendSmsReqVo.getPhoneNumber());
		sms.setTemplateType(sendSmsReqVo.getTemplateType());
		sms.setSmsContent(sendSmsReqVo.getContent());
		sms.setSendTime(new Date());
		sms.setOutId(smsDto.getOutId());

		if(response!=null){
			sms.setMessage(response.getMessage());
			sms.setBizId(response.getBizId());
			sms.setRequestId(response.getRequestId());
			sms.setCode(response.getCode());
		}

		super.save(sms);

	}

	@Override
	public boolean verifyCode(String mobile, String code) {
		SmsDTO smsDTO = redisService.get(RedisConstant.SMS+":"+mobile,SmsDTO.class);
		if(StringUtils.isNotBlank(code) && code.equals(smsDTO.getCode())){
			redisService.delObj(RedisConstant.SMS+":"+mobile);
			return true;
		}
		return false;
	}
}
