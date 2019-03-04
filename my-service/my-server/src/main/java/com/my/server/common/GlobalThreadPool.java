package com.my.server.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;
/**
 * server共用线程池
 * 
 * @project sinafenqi-server
 * @author duannp
 * @date 2016年7月27日
 * www.sinafenqi.com
 */
@Component
public class GlobalThreadPool {


	private ExecutorService threadPool = Executors.newFixedThreadPool(10);

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	@PreDestroy
	public void destroy() {
		threadPool.shutdown();
	}
	
	
	private void example(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("-----需要做的业务-----");
			}
		};
		//提交到线程池异步处理
		getThreadPool().submit(runnable);
	}
}
