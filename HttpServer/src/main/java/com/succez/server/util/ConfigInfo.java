package com.succez.server.util;

import java.util.Properties;

/**
 * 配置文件类
 * 
 * @author Peng.Zezhou
 *
 */
public class ConfigInfo {
	private Properties pro;

	/**
	 * 构造函数
	 * 
	 * @param path
	 */
	public ConfigInfo(String path) {
		pro = Method.getProperties(path);
	}

	/**
	 * 获取整数值
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return Integer.parseInt(pro.getProperty(key));
	}

	/**
	 * 获取字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return pro.getProperty(key);
	}
}
