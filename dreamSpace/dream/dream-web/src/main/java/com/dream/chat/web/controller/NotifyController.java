package com.dream.chat.web.controller;

import com.dream.chat.service.OrderService;
import com.dream.common.core.util.CommonUtil;
import com.dream.common.core.util.XMLUtil;
import com.dream.common.core.util.wechat.AzuraWechatUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2019-01-08
 */
@RestController
@RequestMapping("/notify/v1")
@Api(tags = "回调")
public class NotifyController {

    @Autowired
    private OrderService orderService;

    /**
     * 支付结果通知
     */
    @RequestMapping(value = "/payOrderWX", method = RequestMethod.POST)
    public String payOrderWX(HttpServletRequest request) {
        HashMap<String, String> returnMap = new HashMap<>();
        try {
            // 获取HTTP请求的输入流
            // 已HTTP请求输入流建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            String buffer = null;
            // 存放请求内容
            StringBuffer xml = new StringBuffer();
            while ((buffer = br.readLine()) != null) {
                // 在页面中显示读取到的请求参数
                xml.append(buffer);
            }
            // 解析xml为map
            Map<String, String> resMap = XMLUtil.doXMLParse(xml.toString());
            System.out.println(resMap);
            // 检查参数
            String checkResult = AzuraWechatUtil.check(resMap);
            if (!"SUCCESS".equals(checkResult)) {
                System.out.println(checkResult);
                returnMap.clear();
                returnMap.put("return_code", "FAIL");
                returnMap.put("return_msg", checkResult);
                return CommonUtil.ArrayToXml(returnMap);
            }
            // 业务处理
            if (!orderService.notifyOrder(resMap)) {
                returnMap.clear();
                returnMap.put("return_code", "FAIL");
                returnMap.put("return_msg", "订单处理失败");
                return CommonUtil.ArrayToXml(returnMap);
            }
            // 返回成功参数
            returnMap.clear();
            returnMap.put("return_code", "SUCCESS");
            returnMap.put("return_msg", "OK");
            return CommonUtil.ArrayToXml(returnMap);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        returnMap.clear();
        returnMap.put("return_code", "FAIL");
        returnMap.put("return_msg", "系统错误");
        return CommonUtil.ArrayToXml(returnMap);
    }

    @RequestMapping(value="/fuiouWdNotify",method = RequestMethod.POST)
    @ResponseBody
    public String fuiouWdNotify(Map<String,Object> map){
        System.out.println(map);
        return  "200";
    }

}
