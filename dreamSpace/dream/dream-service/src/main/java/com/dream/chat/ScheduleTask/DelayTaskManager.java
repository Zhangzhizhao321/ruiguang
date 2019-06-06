package com.dream.chat.ScheduleTask;

import com.dream.chat.cache.task.DelayTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * 廷迟任务的管理器
 * @author Administrator
 *
 */
public class DelayTaskManager {
       private static DelayQueue<DelayTask> delayQueue=new DelayQueue<>();
       private static Map<String,DelayTask> delayMap= new ConcurrentHashMap<>();
       private static DelayTaskManager taskManager=new DelayTaskManager();
       private DelayTaskManager(){}
       public static DelayTaskManager getInstance(){
    	     if(taskManager==null){
				 taskManager=new DelayTaskManager();
    	     }
    	     return taskManager;
       }
       /**
        * 添加任务进队列
        * @param dt
        */
       public static void add(DelayTask dt){    	   
    	    delayQueue.add(dt);
		   //System.out.println(dt.getKey());
    	    delayMap.put(dt.getKey(), dt);
		   	//System.out.println(delayMap);
       }

       public static void remove(String key){
    	   DelayTask dt=delayMap.get(key);
    	   if(dt!=null){
    	     delayQueue.remove(dt);
    	     delayMap.remove(key);
    	   }
       }
       public DelayTask take() throws Exception{
		   if(delayQueue.size()>0){
			   DelayTask dt=delayQueue.take();
			   delayMap.remove(dt.getKey());
			   return  dt;
		   }
		   return null;

       }
       public static DelayTask get(String key){    	   
         return  delayMap.get(key);
      }
}
