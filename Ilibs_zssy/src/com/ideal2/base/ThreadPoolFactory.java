package com.ideal2.base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
	
	private ThreadPoolExecutor threadPoolExecutor;
	private static ThreadPoolFactory threadPoolFactory;
//	private ExecutorService pool; 
//	private int cpuNums = Runtime.getRuntime().availableProcessors(); 

	private ThreadPoolFactory(){
		threadPoolExecutor = new ThreadPoolExecutor(8, 12, 2, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(100));
	}
	
	static{
		threadPoolFactory = new ThreadPoolFactory(); 
	}
	
	public static ThreadPoolFactory tpf(){
		return threadPoolFactory;
	}
	
	public void exe(Thread thread){
		threadPoolExecutor.execute(thread);
	}
	
	public void close(){
//		threadPoolFactory = null;
//		System.gc();
//		BaseDao.isDoRequest = true;
	}
	
}
