package com.dream.chat.mq;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.*;

//@Service("HelloSender")
public class HelloSender {
 
   @Autowired
    private JmsMessagingTemplate jmsTemplate;

    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(Destination destination, final Object obj){
        jmsTemplate.convertAndSend(destination, obj);
    }

    @JmsListener(destination="out.queue")
    public void consumerMessage(String text){
        System.out.println(text);
    }

    /**
     * @desc 延时发送
     */
    public void delaySend(String text, String queueName, Long time) {
        //获取连接工厂
        ConnectionFactory connectionFactory = this.jmsTemplate.getConnectionFactory();
        try {
            //获取连接
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //获取session
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage(text);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            //发送
            producer.send(message);
            session.commit();
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

 
   /* public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.jmsTemplate.convertAndSend("hello", context);

    }*/
 
}
