package com.dream.chat.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;

//@Component
public class HelloReceiver {

    private final static Logger logger = LoggerFactory.getLogger(HelloReceiver.class);

    private Integer i = 0;



    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "mytest.queue")
    @SendTo("out.queue")
    public String receiveQueue(String text) {
        System.out.println("Consumer收到的报文为:" + text);
        return "返回给队列的消息" + text;
    }


}
