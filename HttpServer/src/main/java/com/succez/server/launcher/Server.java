package com.succez.server.launcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.connector.ServerThread;
import com.succez.server.util.Method;

public class Server {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	/**
	 * ��ȡ������ ��������
	 * 
	 * @return ������ ��������
	 */
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	// �̳߳�
	public ThreadPool pool = null;
	// serversocket
	public ServerSocket server_socket = null;

	private int port = 8080;
	private int max_connection = 100;
	private String ip = "127.0.0.1";
	private static Server instance = null;

	/**
	 * ���캯��
	 */
	private Server() {
		initProperties();
		if (server_socket == null || pool == null) {
			LOGGER.error("��������ʼ��ʧ��");
			return;
		}
		pool.thread_pool.execute(new ServerThread());
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initProperties() {
		this.port = Method.getPortValue();
		this.max_connection = Method.getMaxConValue();
		this.ip = Method.getIPValue();
		createServerSocket();
		this.pool = ThreadPool.getInstance();
	}

	/**
	 * ����serversocketʵ��
	 */
	private void createServerSocket() {
		try {
			server_socket = new ServerSocket(this.port, this.max_connection,
					InetAddress.getByName(this.ip));
			LOGGER.error("ServerSocket�����ɹ�");
			return;
		} catch (UnknownHostException e) {
			LOGGER.error("ServerSocket����ʧ�ܣ����ɿ���ip��ַ");
		} catch (IOException e) {
			LOGGER.error("ServerSocket����ʧ�ܣ�IO�쳣");
		}
		LOGGER.info("ServerSocket����ʧ�ܣ�����null");
	}
}
