package com.succez.server.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.launcher.Server;

/**
 * 服务器线程
 * 
 * @author Peng.Zezhou
 *
 */
public class ServerThread implements Runnable {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerThread.class);

	/**
	 * 主要功能函数
	 */
	public void run() {
		if (Server.getInstance().isStop()) {
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
