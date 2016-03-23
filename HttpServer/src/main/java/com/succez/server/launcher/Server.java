package com.succez.server.launcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.connector.ServerThread;
import com.succez.server.connector.ThreadPoolTask;
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
		pool.thread_pool.shutdown();
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
	public boolean isStop() {
		return this.stop;
	}

	/**
	 * 执行线程任务
	 * 
	 * @param socket
	 */
	public void excuteTask(Socket socket) {
		pool.thread_pool.execute(new ThreadPoolTask(socket));
	}

	/**
	 * 获取server_socket
	 * 
	 * @return
	 */
	public ServerSocket getServerSocket() {
		return server_socket;
	}

	// 服务器线程池
	private ThreadPool pool = null;
	// serversocket
	private ServerSocket server_socket = null;
	private boolean stop = false;
	private int port = 8080;
	private int max_connection = 100;
	private String ip = "127.0.0.1";
	private static Server instance = null;
	private Config config = new Config();

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
		this.port = config.getPortValue();
		this.max_connection = config.getMaxConValue();
		this.ip = config.getIPValue();
		createServerSocket();
		this.pool = new ThreadPool();
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

	/**
	 * 配置文件内部类
	 * 
	 * @author Peng.Zezhou
	 *
	 */
	class Config {
		private Properties pro = Method
				.getProperties(Constant.SERVER_CONFIG_INFO);

		/**
		 * 读取配置文件ip地址ַ
		 * 
		 * @return ip地址默认返回，本机地址"127.0.0.1"
		 */
		public String getIPValue() {
			return pro.getProperty("ip", "127.0.0.1");
		}

		/**
		 * 读取配置文件端口
		 * 
		 * @return 默认端口地址为80
		 */
		public int getPortValue() {
			int result = 80;
			try {
				result = Integer.parseInt(pro.getProperty("port", "80"));
			} catch (NumberFormatException e) {
				LOGGER.warn("配置文件端口地址异常");
			}
			if (result < 1 || result > 65535) {
				LOGGER.warn("配置文件端口地址不在范围内");
				result = 80;
			}
			return result;
		}

		/**
		 * 读取允许最大连接数
		 * 
		 * @return 默认返回最大连接数50
		 */
		public int getMaxConValue() {
			int result = 50;
			try {
				result = Integer.parseInt(pro.getProperty("max-connection",
						"50"));
			} catch (NumberFormatException e) {
				LOGGER.warn("配置文件，最大连接数异常");
			}
			if (result < 1 || result > 1000) {
				LOGGER.warn("最大连接数不在范围内");
				result = 5;
			}
			return result;
		}

		/**
		 * 配置文件读取最小线程池大小
		 * 
		 * @return 默认最小为5
		 */
		public int getCorePoolSize() {
			int result = 5;
			try {
				result = Integer.parseInt(pro.getProperty("corePoolSize", "5"));
			} catch (NumberFormatException e) {
				LOGGER.warn("配置文件最小线程池大小异常");
			}
			if (result < 1 || result > 1000) {
				LOGGER.warn("最小线程池大小不在范围内");
				result = 5;
			}
			return result;
		}

		/**
		 * 读取配置文件最大线程数
		 * 
		 * @return 线程数
		 */
		public int getMaxNumPoolSize() {
			int result = 20;
			try {
				result = Integer.parseInt(pro.getProperty("maximumPoolSize",
						"20"));
			} catch (NumberFormatException e) {
				LOGGER.warn("最大线程数量错误");
			}
			if (result < 1 || result > 10000) {
				LOGGER.warn("线程数设置不在范围内");
				result = 20;
			}
			return result;
		}

		/**
		 * 读取配置文件 KeepAliveTime
		 * 
		 * @return KeepAliveTime值
		 */
		public int getKeepAliveTime() {
			int result = 30;
			try {
				result = Integer.parseInt(pro
						.getProperty("keepAliveTime", "60"));
			} catch (NumberFormatException e) {
				LOGGER.warn("保活时间转换错误");
			}
			if (result < 20 || result > 6000) {
				LOGGER.warn("保活时间不在阈值内");
				result = 30;
			}
			return result;
		}
	}

	/**
	 * 线程池内部类
	 * 
	 * @author Peng.Zezhou
	 *
	 */
	class ThreadPool {
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
		private ThreadPool() {
			initProperties();
			createThreadPool();
		}

		/**
		 * 读取配置文件
		 */
		private void initProperties() {
			this.corePoolSize = config.getCorePoolSize();
			this.maxNumPoolSize = config.getMaxNumPoolSize();
			this.keepAliveTime = config.getKeepAliveTime();
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
}
