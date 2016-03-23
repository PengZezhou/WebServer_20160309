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
	 * 服务器类
	 * 
	 * @return 服务器实例
	 */
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	// 服务器线程池
	public ThreadPool pool = null;
	// serversocket
	public ServerSocket server_socket = null;

	private int port = 8080;
	private int max_connection = 100;
	private String ip = "127.0.0.1";
	private static Server instance = null;

	/**
	 * 构造函数
	 */
	private Server() {
		initProperties();
		if (server_socket == null || pool == null) {
			LOGGER.error("服务器初始化失败");
			return;
		}
		pool.thread_pool.execute(new ServerThread());
	}

	/**
	 * 读取配置文件
	 */
	private void initProperties() {
		this.port = Method.getPortValue();
		this.max_connection = Method.getMaxConValue();
		this.ip = Method.getIPValue();
		createServerSocket();
		this.pool = ThreadPool.getInstance();
	}

	/**
	 * 创建serversocket
	 */
	private void createServerSocket() {
		try {
			server_socket = new ServerSocket(this.port, this.max_connection,
					InetAddress.getByName(this.ip));
			LOGGER.error("ServerSocket创建成功");
			return;
		} catch (UnknownHostException e) {
			LOGGER.error("ServerSocket创建失败，位置的主机地址");
		} catch (IOException e) {
			LOGGER.error("ServerSocket创建失败，IO异常");
		}
	}
}
