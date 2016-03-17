package com.succez.server.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.connector.Connector;
import com.succez.server.util.Constant;

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
		if (Constant.SERVER_SOCKET.isClosed()
				|| Constant.THREAD_POOL.isShutdown()) {
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
