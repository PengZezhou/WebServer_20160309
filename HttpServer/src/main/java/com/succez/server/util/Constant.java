package com.succez.server.util;

/**
 * 常量类
 * 
 * @author Peng.Zezhou
 *
 */
public class Constant {
	/**
	 * 配置文件路径，启动器模块常量
	 */
	public static final String SERVER_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\server.properties";
	public static final String LOG4J_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\log4j.properties";
	public static final String FILE_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\file.properties";

	/**
	 * 命令控制器相关信息，启动器模块常量
	 */
	public static final String TIPS = "可接受命令 'exit' | 'help'";
	public static final String EXIT = "exit";
	public static final String HELP = "help";
	public static final String PROMT = "'exit' 退出服务器";

	public static final Boolean SHUTDOWN = false;

	/**
	 * 服务器端常量，连接器模块常量
	 */
	public static final String SERVER_ENCODE = "utf-8";
	public static final int SERVER_CHACHE = 1024 * 2;

	/**
	 * 异常响应文件路径，分析处理模块常量
	 */
	public static final long FILE_SIZE = 1024 * 1;

	/**
	 * 文件下载模块常量
	 */
	public static final int BYTE_BUFFER_COPACITY = 1024 * 1024;
	public static final int BUFFER_SIZE = 128 * 1024;
}
