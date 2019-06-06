package com.dream.common.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**同步锁锁工具类*/
public class SyncLock {
	private static Map<String,Lock> lockMap = new HashMap<String,Lock>();
	/**
	 * 获取操作的锁，锁未释放则等待
	 * @param id 业务id 根据id获取唯一的锁
	 */
	public static void lock(String id){
		Lock lock = lockMap.get(id);
		if(lock==null){
			lock = new ReentrantLock();
		}
		lock.lock();
		lockMap.put(id, lock);
	}
	/**释放锁*/
	public static void unSyncLock(String id){
		Lock lock = null;
		try {
			lock = lockMap.get(id);
			if(lock!=null){
				lockMap.remove(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(lock!=null){
				lock.unlock();
			}
		}
		
	}
}
