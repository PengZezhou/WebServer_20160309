package com.succez.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class Method {
	private static final Logger LOGGER = Logger.getLogger(Method.class);
	private static final Properties pro = getProperties();
	/**
	 * 实例化一个ServerSocket
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
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					LOGGER.error("配置文件读取，流关闭异常");
				}
			}
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
			LOGGER.warn("端口号转换为整数异常，为避免系统推出，返回默认值‘80’");
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
			LOGGER.warn("最大连接数转换为整数异常，为避免系统推出，返回默认值‘50’");
		}
		return result;
	}
}