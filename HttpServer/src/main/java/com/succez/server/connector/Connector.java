package com.succez.server.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.launcher.Server;
import com.succez.server.launcher.ThreadPool;
import com.succez.server.util.Constant;

/**
 * 连接器
 * 
 * @author Peng.Zezhou
 *
 */
public class Connector {
	private static final Logger LOGGER = Logger.getLogger(Connector.class);

	/**
	 * 连接请求管理器
	 * 
	 * @return
	 */
	public int requestMonitor() {
		LOGGER.info("开始接受请求线程任务");
		int flag = 0;
		ServerSocket sv = Server.getInstance().server_socket;
		while (!Constant.SHUTDOWN) {
			if (sv.isClosed()
					|| ThreadPool.getInstance().thread_pool.isShutdown()) {
				break;
			}
			try {
				Socket socket = sv.accept();
				// 执行线程池任务
				ThreadPool.getInstance().thread_pool
						.execute(new ThreadPoolTask(socket));
			} catch (IOException e) {
				LOGGER.error("创建线程任务出错");
				flag = -1;
			}
		}
		return flag;
	}
}
