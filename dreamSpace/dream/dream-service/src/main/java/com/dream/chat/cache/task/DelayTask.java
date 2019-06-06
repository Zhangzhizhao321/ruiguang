package com.dream.chat.cache.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 廷迟任务
 * @author Administrator
 *
 */
public abstract class DelayTask implements Delayed{
	protected long expire;  //过期时间
	public abstract void execute();
	
	public DelayTask() {

	}
	public DelayTask(String key, Long expire, Object interfaceObj) {

	}
	
	@Override
	public int compareTo(Delayed o) {
		long t0=this.getDelay(TimeUnit.MILLISECONDS);
		long t1=o.getDelay(TimeUnit.MILLISECONDS);
		long t=t0-t1;
		return t>0?1:t==0?0:-1;
	}

	@Override
	public long getDelay(TimeUnit unit) {		
		return unit.convert(this.expire-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public long getExpire() {
		return expire;
	}
	public abstract String getKey();
}
