package com.succez.server.connector;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.resolver.Resolver;

/**
 * 线程池任务
 * 
 * @author Peng.Zezhou
 *
 */
public class ThreadPoolTask implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ThreadPoolTask.class);
	private Socket socket;

	/**
	 * socket连接
	 * 
	 * @param socket
	 */
	ThreadPoolTask(Socket socket) {
		this.socket = socket;
	}

	/**
	 * 主要任务
	 */
	public void run() {
		LOGGER.info("开始处理外部请求");
		new Resolver(socket);
	}
}
