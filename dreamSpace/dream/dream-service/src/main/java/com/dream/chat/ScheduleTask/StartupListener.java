package com.dream.chat.ScheduleTask;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 启动监听器
 *
 * @author Storezhang
 */
//@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
        if (evt.getApplicationContext().getParent() == null) {
            /* System.out.println("查看退款任务");
           /* CashOrderService cashOrderService = SpringContextHolder.getBean("cashOrderService");
            cashOrderService.getTaskRefund();*/
        }
        /*try {
            new SimpleChatServer(9090).run();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
