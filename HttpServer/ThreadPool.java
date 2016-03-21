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
	 * ��ȡ�̳߳ض�����
	 * 
	 * @return �̳߳ض�����
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
	 * ˽�й��캯���������̳߳ض���
	 */
	private ThreadPool() {
		initProperties();
		createThreadPool();
	}

	/**
	 * ��ȡ�����ļ���Ϣ
	 */
	private void initProperties() {
		this.corePoolSize = Method.getCorePoolSize();
		this.maxNumPoolSize = Method.getMaxNumPoolSize();
		this.keepAliveTime = Method.getKeepAliveTime();
		this.block_queue = new ArrayBlockingQueue<Runnable>(5);
		this.handler = new ThreadPoolExecutor.DiscardOldestPolicy();
	}

	/**
	 * �����̳߳�ʵ��
	 */
	private void createThreadPool() {
		LOGGER.info("��ʼ���̳߳�...");
		this.thread_pool = new ThreadPoolExecutor(this.corePoolSize,
				this.maxNumPoolSize, this.keepAliveTime, this.time_unit,
				this.block_queue, this.handler);
		LOGGER.info("�̳߳س�ʼ������");
	}
}
