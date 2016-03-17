package com.succez.server.connector;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.resolver.Resolver;

/**
 * �������������߳�
 * 
 * @author Peng.Zezhou
 *
 */
public class ThreadPoolTask implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ThreadPoolTask.class);
	private Socket socket;

	/**
	 * ���캯��
	 * 
	 * @param socket
	 */
	ThreadPoolTask(Socket socket) {
		this.socket = socket;
	}

	/**
	 * �߳����߼�
	 */
	public void run() {
		LOGGER.info("�߳�ִ����...");
		Resolver res = new Resolver(socket);
		res.urlResolve();
	}
}
