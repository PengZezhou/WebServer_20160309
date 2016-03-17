package com.succez.server.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * 程序公有逻辑与类加载前处理逻辑
 * 
 * @author Peng.Zezhou
 *
 */
public class Method {
	private static final Logger LOGGER = Logger.getLogger(Method.class);
	private static final Properties pro = getProperties();

	/**
	 * 实例化一个ServerSocket
	 * 
	 * @return 返回ServerSocket实例，失败返回null
	 */
	public static final ServerSocket getOneServer() {
		try {
			return new ServerSocket(Constant.PORT, Constant.MAX_CONNECTION,
					InetAddress.getByName(Constant.IP));
		} catch (UnknownHostException e) {
			LOGGER.error("ServerSocket创建失败，不可靠的ip地址");
		} catch (IOException e) {
			LOGGER.error("ServerSocket创建失败，IO异常");
		}
		LOGGER.info("ServerSocket创建失败，返回null");
		return null;
	}

	/**
	 * 获取配置文件读取对象
	 * 
	 * @return Properties
	 */
	private static final Properties getProperties() {
		Properties pro = new Properties();
		File file = new File(Constant.SERVER_CONFIG_INFO);
		InputStream stream = null;
		if (!file.exists()) {
			LOGGER.error("配置文件已被移动");
			return null;
		}
		try {
			stream = new FileInputStream(file);
			pro.load(stream);
		} catch (FileNotFoundException e) {
			LOGGER.error("配置文件无法在制定位置找到");
		} catch (IOException e) {
			LOGGER.error("配置文件读取，流加载异常");
		} finally {
			Method.closeStream(stream);
		}
		return pro;
	}

	/**
	 * 获取配置文件ip地址
	 * 
	 * @return ip地址，读取失败默认返回‘127.0.0.1’
	 */
	public static final String getIPValue() {
		return pro.getProperty("ip", "127.0.0.1");
	}

	/**
	 * 获取配置文件端口
	 * 
	 * @return 端口号，读取失败默认返回‘80’
	 */
	public static final int getPortValue() {
		int result = 80;
		try {
			result = Integer.parseInt(pro.getProperty("port", "80"));
		} catch (NumberFormatException e) {
			LOGGER.warn("端口号转换为整数异常，为避免系统退出，返回默认值‘80’");
		}
		if (result < 1 || result > 65535) {
			LOGGER.warn("端口号不在阈值间，系统使用默认值");
			result = 80;
		}
		return result;
	}

	/**
	 * 获取配置文件最大浏览器连接数
	 * 
	 * @return 连接数最大值，读取失败默认返回‘50’
	 */
	public static final int getMaxConValue() {
		int result = 50;
		try {
			result = Integer.parseInt(pro.getProperty("max-connection", "50"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最大连接数转换为整数异常，为避免系统退出，返回默认值‘50’");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("浏览器最大连接数不在阈值间，系统使用默认值");
			result = 5;
		}
		return result;
	}

	/**
	 * 获取配置文件线程池维护线程的最少数量
	 * 
	 * @return 线程池维护线程的最少数量
	 */
	public static final int getCorePoolSize() {
		int result = 5;
		try {
			result = Integer.parseInt(pro.getProperty("corePoolSize", "5"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最小线程数转换为整数异常，为避免系统退出，返回默认值‘5’");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("线程池配置警告，CorePoolSize不在阈值间，系统使用默认值");
			result = 5;
		}
		return result;
	}

	/**
	 * 获取配置文件线程池维护线程的最大数量
	 * 
	 * @return 线程池维护线程的最大数量
	 */
	public static final int getMaxNumPoolSize() {
		int result = 20;
		try {
			result = Integer.parseInt(pro.getProperty("maximumPoolSize", "20"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最大线程数转换为整数异常，为避免系统退出，返回默认值‘20’");
		}
		if (result < 1 || result > 10000) {
			LOGGER.warn("线程池配置警告，CorePoolSize不在阈值间，系统使用默认值");
			result = 20;
		}
		return result;
	}

	/**
	 * 获取配置文件线程池维护线程所允许的空闲时间 (秒)
	 * 
	 * @return 线程池维护线程所允许的空闲时间
	 */
	public static final int getKeepAliveTime() {
		int result = 30;
		try {
			result = Integer.parseInt(pro.getProperty("keepAliveTime", "60"));
		} catch (NumberFormatException e) {
			LOGGER.warn("线程允许空闲时间转换为整数异常，为避免系统退出，返回默认值‘30’");
		}
		if (result < 20 || result > 6000) {
			LOGGER.warn("线程池配置警告，CorePoolSize不在阈值间，系统使用默认值");
			result = 30;
		}
		return result;
	}

	/**
	 * 获取一个线程池实例
	 * 
	 * @return 线程池实例
	 */
	public static final ThreadPoolExecutor getThreadPool() {
		LOGGER.info("初始化线程池...");
		ThreadPoolExecutor thread_pool = new ThreadPoolExecutor(
				Constant.CORE_POOL_SIZE, Constant.MAX_NUM_POOL_SIZE,
				Constant.KEEP_ALIVE_TIME, Constant.TIME_UNIT,
				Constant.BLOCK_QUEUE, Constant.HANDLER);
		LOGGER.info("线程池初始化结束");
		return thread_pool;
	}

	/**
	 * 关闭流的逻辑封装
	 * 
	 * @param closeavle
	 *            流对象
	 */
	public static final void closeStream(Closeable closeavle) {
		if (closeavle != null) {
			try {
				closeavle.close();
			} catch (IOException e) {
				LOGGER.error(closeavle.toString() + " 流关闭出现异常");
			}
		}
	}
}