package com.dream.chat.component;


import com.dream.chat.ScheduleTask.RedisListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

@Configuration
@AutoConfigureAfter(value = RedisListenerConfiguration.class)
public class RedisListenerConfiguration {

    @Resource
    private RedisListener redisListener;

    @Resource
    private JedisConnectionFactory connectionFactory;

    @Value("${spring.redis.database}")
    private Integer paymentDataBase;

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 监听paymentDataBase 库的过期事件
        String subscribeChannel = "__keyevent@" + paymentDataBase + "__:expired";
        container.addMessageListener(listenerAdapter, new PatternTopic(subscribeChannel));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(redisListener);
    }

}
