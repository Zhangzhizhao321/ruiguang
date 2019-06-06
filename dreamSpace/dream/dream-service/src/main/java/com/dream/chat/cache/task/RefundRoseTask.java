package com.dream.chat.cache.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.chat.component.SpringContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * 退款任务
 * @author Administrator
 *
 */
@Component
public class RefundRoseTask /*extends DelayTask*/{
	/*//CustomLogger customLogger = chat CustomLogger(RefundTask.class);

	private String orderId;

	//private String userChatId;

	private String userId;

	public RefundRoseTask(){}

	public RefundRoseTask(String orderId,String userChatId,String userId, long expireSecond) {
		super();
		this.orderId = orderId;
		//this.userChatId = userChatId;
		this.userId = userId;
		this.expire = System.currentTimeMillis()+expireSecond*1000;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(simpleDateFormat.format(new Date(expire))+"====任务倒计时");
	}


	*//**
	 * 未接收玫瑰
	 *//*
    public void execute(){
    	//清除内存中的operationId
    	//OpIdMapManager.removeOpId(this.operationId);
        try {
			//System.out.println(userOrderService+"==="+bidId);
			//TODO 退款
			*//*CashOrderService cashOrderService = SpringContextHolder.getBean("cashOrderService");
			cashOrderService.refundOrderWX(orderId);*//*
			RoseInoutService roseInoutService = SpringContextHolder.getBean("roseInoutService");
			UserChatService userChatService = SpringContextHolder.getBean("userChatService");
			UserChat userChat = userChatService.getOne(new QueryWrapper<UserChat>().eq(UserChat.ORDER_ID,orderId));
			roseInoutService.receiveRose(userChat.getId(), GiftEnum.RETURN.getCode(),userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

	public String getOrderId() {
		return orderId;
	}
	@Override
	public String getKey() {
		
		//return this.getOperationId();
		return orderId;
	}
*/

    
}
