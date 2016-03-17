package com.succez.server.connector;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.resolver.Resolver;

/**
 * 单个请求任务线程
 * 
 * @author Peng.Zezhou
 *
 */
public class ThreadPoolTask implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ThreadPoolTask.class);
	private Socket socket;

	/**
	 * 构造函数
	 * 
	 * @param socket
	 */
	ThreadPoolTask(Socket socket) {
		this.socket = socket;
	}

	/**
	 * 线程主逻辑
	 */
	public void run() {
		LOGGER.info("线程执行中...");
		Resolver res = new Resolver(socket);
		res.urlResolve();
	}
}
