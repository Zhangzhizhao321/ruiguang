package com.dream.chat.ScheduleTask;

import com.dream.chat.cache.task.DelayTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 廷迟任务监听器
 *
 * @author Administrator
 */
public class DelayQueueListener implements ServletContextListener {

	private Thread thread;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*thread = new Thread(new Runnable() {
			@Override
			public void run() {

				while (true) {
					try {
						DelayTask delayTask = DelayTaskManager.getInstance().take();
						if (delayTask != null) {
							delayTask.execute();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
		});
		thread.start();*/
		/*
				ExecutorService service = Executors.newSingleThreadExecutor();
				service.execute(new Runnable(){

					@Override
					public void run() {
						while (true) {
							try {
									DelayTask delayTask = DelayTaskManager.getInstance().take();
									delayTask.execute();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
						
					}});
*/
	}


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		//thread.stop();
	}

	/*public static void main(String[] args) {
		DelayTaskManager.add(new BidProductTask("0a844df5ba36486f8d71ca464bfff510",10));
	}*/

}
