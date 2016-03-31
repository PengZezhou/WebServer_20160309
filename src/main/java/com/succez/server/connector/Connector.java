package com.succez.server.connector;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.launcher.Server;
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
		Server sv = Server.getInstance();
		Socket socket;
		while (!Constant.SHUTDOWN) {
			if (Server.isStop()) {
				break;
			}
			try {
				LOGGER.info("服务器地址"+sv.getServerSocket().toString());
				socket = sv.getServerSocket().accept();
				LOGGER.info("客户端连接"+socket.getInetAddress().getHostAddress()+":"+socket.getPort());
				// 执行线程池任务
				sv.excuteTask(socket);
			} catch (IOException e) {
				LOGGER.error("创建线程任务出错");
				flag = -1;
			}
		}
		LOGGER.info("退出监听任务");
		return flag;
	}
}
