package com.succez.server.launcher;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Method;

public class ThreadPool {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ThreadPool.class);
	// �̳߳�
	public ThreadPoolExecutor thread_pool = null;

	/**
	 * 线程池类
	 * 
	 * @return 获取线程池单例
	 */
	public static ThreadPool getInstance() {
		if (instance == null) {
			instance = new ThreadPool();
		}
		return instance;

	}

	private int corePoolSize = 5;
	private int maxNumPoolSize = 20;
	private int keepAliveTime = 60;
	private TimeUnit time_unit = TimeUnit.SECONDS;
	private BlockingQueue<Runnable> block_queue;
	private RejectedExecutionHandler handler;
	private static ThreadPool instance = null;

	/**
	 * 初始化线程池
	 */
	private ThreadPool() {
		initProperties();
		createThreadPool();
	}

	/**
	 * 读取配置文件
	 */
	private void initProperties() {
		this.corePoolSize = Method.getCorePoolSize();
		this.maxNumPoolSize = Method.getMaxNumPoolSize();
		this.keepAliveTime = Method.getKeepAliveTime();
		this.block_queue = new ArrayBlockingQueue<Runnable>(5);
		this.handler = new ThreadPoolExecutor.DiscardOldestPolicy();
	}

	/**
	 * 创建线程池
	 */
	private void createThreadPool() {
		LOGGER.info("开始创建线程池...");
		this.thread_pool = new ThreadPoolExecutor(this.corePoolSize,
				this.maxNumPoolSize, this.keepAliveTime, this.time_unit,
				this.block_queue, this.handler);
		LOGGER.info("线程池创建结束");
	}
}
