package com.succez.server.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.launcher.Server;
import com.succez.server.launcher.ThreadPool;

/**
 * 服务器主线程
 * 
 * @author Peng.Zezhou
 *
 */
public class ServerThread implements Runnable {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerThread.class);

	/**
	 * 线程逻辑
	 */
	public void run() {
		if (Server.getInstance().server_socket.isClosed()
				|| ThreadPool.getInstance().thread_pool.isShutdown()) {
			return;
		}
		Connector connector = new Connector();
		if (-1 == connector.requestMonitor()) {
			LOGGER.info("server fail inited");
		} else {
			LOGGER.info("server inited");
		}
	}
}
