package com.succez.server.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.util.Constant;

public class Connector {
	private static final Logger LOGGER = Logger.getLogger(Connector.class);
	public int requestMonitor(){
		LOGGER.info("��ʼ��������");
		int flag = 0;
		ServerSocket sv = Constant.SERVER_SOCKET;
		while (!Constant.SHUTDOWN) {
			if (sv.isClosed() || Constant.THREAD_POOL.isShutdown()) {
				break;
			}
			Socket socket = null;
			try {
				socket = sv.accept();
				connectToEnd(socket);
			} catch (IOException e) {
				LOGGER.error("�����쳣");
				flag = -1;
			}
		}
		return flag;
	}

	/**
	 * ����������ַ����̳߳ش���
	 * 
	 * @param socket
	 */
	private void connectToEnd(Socket socket) {
		// ���̴߳���
		Constant.THREAD_POOL.execute(new ThreadPoolTask(socket));
	}

	/**
	 * �����������Դ
	 */
	public void resourceCleaner() {
		LOGGER.info("�ر��̳߳�...");
		Constant.THREAD_POOL.shutdown();
		LOGGER.info("�̳߳��ѹر�");
		LOGGER.info("�ر�serverSocket...");
		try {
			if (Constant.SERVER_SOCKET != null) {
				Constant.SERVER_SOCKET.close();
				LOGGER.info("serverSocket�ѹر�");
			}
		} catch (IOException e) {
			LOGGER.error("�ر�serverSocket�����쳣");
		}
	}
}
