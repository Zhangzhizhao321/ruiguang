package com.dream.chat.web.controller;

import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.SmsDTO;
import com.dream.chat.cache.dto.UserSessionDTO;
import com.dream.chat.constant.RedisConstant;
import com.dream.chat.constant.SmsEnum;
import com.dream.chat.service.SmsService;
import com.dream.chat.vo.req.SendSmsReqVo;
import com.dream.common.core.api.R;
import com.dream.common.core.exception.SDKRuntimeException;
import com.dream.common.core.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.httpclient.HttpsURL;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 短信通知 前端控制器
 * </p>
 *
 * @author lw
 * @since 2018-10-23
 */
@RestController
@RequestMapping("/sms")
@Api(tags = "短信服务对外接口")
@AllArgsConstructor
public class SmsController extends SuperController {
    //private final SmsService smsService;
    private  static final String token = "c260pr1p1ty2qi09xilp8daa981dca81dd8cb050bc7c861b2b9448ox34du2xl5kja";
    private static final String url = "http://47.104.68.57:8090/send/message";

    private final RedisService redisService;

    private final SmsService smsService;

    @PostMapping("/v1/directSendSms")
    @ApiOperation(value = "直接发送短信", notes = "不经定时任务，直接发送短信")
    public R directSendSms(String mobile,Integer vcode) throws SDKRuntimeException {
        /*SmsDTO smsDTO = redisService.get(RedisConstant.SMS+":"+mobile,SmsDTO.class);
        if(smsDTO != null){
            return R.failed("发送失败！请稍后再试！");
        }*/
        if(vcode == SmsEnum.VCODE.code){
            //发送验证码
            String random = MathUtil.getSixNumber();
            String content = random;
            HashMap<String,String> map = new HashMap<>();
            map.put("key",token);
            map.put("mobile",mobile);
            map.put("content",content);
            String param = ApiUtil.genParam(map);
            String result = UrlUtil.postURL(url,param);
            System.out.println(result);
            if(!"true".equals(result)){
                return R.failed("发送失败！");
            }
            /*smsDTO = new SmsDTO(mobile,mobile,random);
            redisService.set(smsDTO,5,TimeUnit.MINUTES);*/
            return R.ok(random);
        }
        return R.failed("发送失败");
    }

    public static void main(String[] args) throws SDKRuntimeException {
        String random = MathUtil.getSixNumber();
        String content = random;
        Map<String,String> map = new HashMap<>();
        map.put("key",token);
        map.put("mobile","19802095556");
        map.put("content",content);
        String param = ApiUtil.genParam(map);
        String result = UrlUtil.postURL(url,param);
        System.out.println(result);
    }

    /*@PostMapping("/v1/directSendSms")
    @ApiOperation(value = "直接发送短信", notes = "不经定时任务，直接发送短信")
    public R directSendSms(@Valid SendSmsReqVo sendSmsVo, HttpServletRequest request) {
        sendSmsVo.setContent(MathUtil.getSixNumber());
        //smsService.sendSms(sendSmsVo, request);

        Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute(sendSmsVo.getPhoneNumber());
        SmsDTO smsDTO = redisService.get(RedisConstant.SMS + ":" + sendSmsVo.getPhoneNumber(), SmsDTO.class);
        if (smsDTO != null) {
            redisService.delObj(RedisConstant.SMS + ":" + sendSmsVo.getPhoneNumber());
        }
        smsDTO = new SmsDTO(sendSmsVo.getPhoneNumber(), sendSmsVo.getPhoneNumber(), map.get("code").toString());
        redisService.set(smsDTO, 3, TimeUnit.MINUTES);
        map.put("codeKey", sendSmsVo.getPhoneNumber());
        return R.ok(map);
    }*/


}


