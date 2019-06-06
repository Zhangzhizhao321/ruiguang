package com.dream.chat.common.utils;

import com.dream.chat.cache.Wechat.Message;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class MessgaeUtils {
    public static final String MESSAGE_TEXT = "text";

    public static final String MESSAGE_IMAGE = "image";

    public static final String MESSAGE_VOICE = "voice";

    public static final String MESSAGE_VIDEO = "video";

    public static final String MESSAGE_SHORTVIDEO = "shortvideo";

    public static final String MESSAGE_LINK = "link";

    public static final String MESSAGE_LOCATION = "location";

    public static final String MESSAGE_EVENT = "event";

    public static final String MESSAGE_SUBSCRIBE = "subscribe";

    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";

    public static final String MESSAGE_CLICK = "CLICK";

    public static final String MESSAGE_VIEW = "VIEW";

    public static final String MESSAGE_SCAN = "SCAN";


    //关注初始化消息内容
    public static String menuText(){
        StringBuffer b=new StringBuffer();
        b.append("欢迎关注么么乐!\n\n");
        b.append("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bb0cb889bcfebf5&redirect_uri=http%3a%2f%2fanmoyi.momole.com.cn%2fmomole%2fresources%2fhtml%2fsystem%2factivityCoupon.html&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect'>领取优惠券</a>"/*+",请选择\n\n"*/);
	         /* b.append("1、我很帅。\n\n");
	          b.append("2、我贼鸡儿帅。\n\n");
	          b.append("回复、主菜单。\n\n");*/
        return b.toString();
    }

    public static String monetText(String accessToken,String mpId){
        StringBuffer b=new StringBuffer();
       // b.append("欢迎关注么么乐!\n\n");
        b.append("<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5bb0cb889bcfebf5&redirect_uri=http%3a%2f%2fanmoyi.momole.com.cn%2fmomole%2fresources%2fhtml%2fsystem%2faccredit.html?accessToken="+accessToken+"&mpId="+mpId+"&response_type=code&scope=snsapi_userinfo&state="+accessToken+"#wechat_redirect'>点击送余额</a>"/*+",请选择\n\n"*/);
	         /* b.append("1、我很帅。\n\n");
	          b.append("2、我贼鸡儿帅。\n\n");
	          b.append("回复、主菜单。\n\n");*/
        return b.toString();
    }

    public static String initText(String toUserName,String fromUserName,String content){
        Message message=new Message();
        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setMsgType(MESSAGE_TEXT);
        message.setContent(content);
        message.setCreateTime(new Date().getTime());
        return  objectToXml(message);
    }
    /*将xml格式转化为map*/
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception{
        Map<String,String> map=new HashMap<>();

        SAXReader reader=new SAXReader();
        InputStream inputStream=request.getInputStream();
        Document doc=reader.read(inputStream);
        Element root=doc.getRootElement();//得到根节点
        List<Element> list=root.elements();//根节点下的所有的节点
        for(Element e:list){
            map.put(e.getName(),e.getText());
        }

        inputStream.close();
        return  map;
    }

    /*将我们的消息内容转变为xml*/

    public static String objectToXml(Message message){
        XStream xStream=new XStream();
        //xml根节点替换成<xml> 默认是Message的包名
        xStream.alias("xml", message.getClass());
        return xStream.toXML(message);
    }


    public static Map<String, String> xmlToMap(String xml, String charset) throws java.io.UnsupportedEncodingException, org.dom4j.DocumentException{

        Map<String, String> respMap = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes(charset)));
        Element root = doc.getRootElement();
        xmlToMap(root, respMap);
        return respMap;
    }

    public static Map<String, String> xmlToMap(Element tmpElement, Map<String, String> respMap){

        if (tmpElement.isTextOnly()) {
            respMap.put(tmpElement.getName(), tmpElement.getText());
            return respMap;
        }

        @SuppressWarnings("unchecked")
        Iterator<Element> eItor = tmpElement.elementIterator();
        while (eItor.hasNext()) {
            Element element = eItor.next();
            xmlToMap(element, respMap);
        }
        return respMap;
    }
}
