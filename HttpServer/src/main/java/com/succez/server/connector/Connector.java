package com.succez.server.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.launcher.Server;
import com.succez.server.launcher.ThreadPool;
import com.succez.server.util.Constant;

/**
 * ������
 * 
 * @author Peng.Zezhou
 *
 */
public class Connector {
	private static final Logger LOGGER = Logger.getLogger(Connector.class);

	/**
	 * ���������
	 * 
	 * @return
	 */
	public int requestMonitor() {
		LOGGER.info("��ʼ��������");
		int flag = 0;
		ServerSocket sv = Server.getInstance().server_socket;
		while (!Constant.SHUTDOWN) {
			if (sv.isClosed()
					|| ThreadPool.getInstance().thread_pool.isShutdown()) {
				break;
			}
			try {
				Socket socket = sv.accept();
				// ���̴߳���
				ThreadPool.getInstance().thread_pool
						.execute(new ThreadPoolTask(socket));
			} catch (IOException e) {
				LOGGER.error("�����쳣");
				flag = -1;
			}
		}
		return flag;
	}
}
