package com.succez.server.launcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.connector.Connector;
import com.succez.server.resolver.Resolver;
import com.succez.server.util.ConfigInfo;
import com.succez.server.util.Constant;
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

	/**
	 * 清理服务器资源
	 */
	public void stop() {
		LOGGER.info("server cleanning...");
		stop = true;
		LOGGER.info("关闭线程池...");
		pool.shutdown();;
		LOGGER.info("线程池关闭");
		LOGGER.info("关闭serversocket...");
		Method.closeStream(server_socket);
		LOGGER.info("server exited");
	}

	/**
	 * 判断服务器是否停止
	 * 
	 * @return
	 */
	public static boolean isStop() {
		return stop;
	}

	/**
	 * 执行线程任务
	 * 
	 * @param socket
	 */
	public void excuteTask(Socket socket) {
		pool.execute(new ThreadPoolTask(socket));
	}


	public ServerSocket getServerSocket() {
		return this.server_socket;
	}

	// 服务器线程池
	private ThreadPoolExecutor pool = null;
	// serversocket
	private ServerSocket server_socket = null;
	private static boolean stop = false;
	private int port;
	private int max_connection;
	private String ip;
	private static Server instance = null;
	private ConfigInfo config = new ConfigInfo(Constant.SERVER_CONFIG_INFO);

	/**
	 * 构造函数
	 */
	private Server() {
		initProperties();
		if (server_socket == null || pool == null) {
			LOGGER.error("服务器初始化失败");
			return;
		}
		pool.execute(new ServerThread());
		LOGGER.info("服务器初始化完成");
	}

	/**
	 * 读取配置文件
	 */
	private void initProperties() {
		this.ip = config.getString("ip");
		this.max_connection = config.getInt("max-connection");
		this.port = config.getInt("port");
		this.pool = new Pools().getExecutor();
		this.createServerSocket();
		stop = false;
	}

	/**
	 * 创建serversocket
	 */
	private void createServerSocket() {
		try {
			this.server_socket = new ServerSocket(this.port, this.max_connection,
					InetAddress.getByName(this.ip));
			LOGGER.info("ServerSocket创建成功");
			return;
		} catch (UnknownHostException e) {
			LOGGER.error("ServerSocket创建失败，位置的主机地址");
		} catch (IOException e) {
			LOGGER.error("ServerSocket创建失败，IO异常");
		}
	}

	/**
	 * 线程池内部类
	 * 
	 * @author Peng.Zezhou
	 *
	 */
	class Pools {
		public ThreadPoolExecutor getExecutor() {
			return this.thread_pool;
		}

		private ThreadPoolExecutor thread_pool = null;

		private int corePoolSize = 5;
		private int maxNumPoolSize = 20;
		private int keepAliveTime = 60;
		private TimeUnit time_unit = TimeUnit.SECONDS;
		private BlockingQueue<Runnable> block_queue;
		private RejectedExecutionHandler handler;

		/**
		 * 初始化线程池
		 */
		private Pools() {
			initProperties();
			createThreadPool();
		}

		/**
		 * 读取配置文件
		 */
		private void initProperties() {
			this.corePoolSize = config.getInt("corePoolSize");
			this.maxNumPoolSize = config.getInt("maximumPoolSize");
			this.keepAliveTime = config.getInt("keepAliveTime");
			this.block_queue = new ArrayBlockingQueue<Runnable>(5);
			this.handler = new ThreadPoolExecutor.DiscardOldestPolicy();
		}

		/**
		 * 创建线程池
		 */
		private void createThreadPool() {
			LOGGER.info("开始创建线程池...");
			this.thread_pool = new ThreadPoolExecutor(this.corePoolSize,
					this.maxNumPoolSize, this.keepAliveTime, this.time_unit,
					this.block_queue, this.handler);
			LOGGER.info("线程池创建结束");
		}
	}

	/**
	 * 服务器线程内部类
	 * 
	 * @author Peng.Zezhou
	 *
	 */
	class ServerThread implements Runnable {
		/**
		 * 主要功能函数
		 */
		public void run() {
			if (Server.isStop()) {
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
	/**
	 * 线程池任务
	 * 
	 * @author Peng.Zezhou
	 *
	 */
	
	class ThreadPoolTask implements Runnable {
		private Socket socket;

		/**
		 * socket连接
		 * 
		 * @param socket
		 */
		public ThreadPoolTask(Socket socket) {
			this.socket = socket;
		}

		/**
		 * 主要任务
		 */
		public void run() {
			LOGGER.info("开始处理外部请求");
			new Resolver(socket);
		}
	}
}
