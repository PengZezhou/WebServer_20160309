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
	public static final String USER_DIR = "user.dir";
	public static final String SERVER_CONFIG_INFO = System
			.getProperty(USER_DIR)
			+ "\\src\\main\\resources\\server.properties";
	public static final String LOG4J_CONFIG_INFO = System.getProperty(USER_DIR)
			+ "\\src\\main\\resources\\log4j.properties";
	public static final String FILE_CONFIG_INFO = System.getProperty(USER_DIR)
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
	public static final String GBK_ENCODE = "gbk";
	public static final int SERVER_CHACHE = 1024 * 2;

	/**
	 * 同步传输文件最大容量
	 */
	public static final long FILE_SIZE = 1024 * 1014;

	/**
	 * 文件下载模块常量
	 */
	public static final int BYTE_BUFFER_COPACITY = 1024 * 1024;
	public static final int BUFFER_SIZE = 128 * 1024;

	/**
	 * 配置文件常量
	 */
	public static final String IP = "ip";
	public static final String PORT = "port";
	public static final String MAX_CON = "max-connection";

	public static final String CORE_SIZE = "corePoolSize";
	public static final String MAX_SIZE = "maximumPoolSize";
	public static final String KEEP_ALIVE_TIME = "keepAliveTime";

	public static final String ERROR_404 = "error-404";
	public static final String ERROR_500 = "error-500";
	public static final String NOT_SUPPORT = "not-support";
	public static final String EXTERN_DOWNLOAD = "download-extern-name";
	public static final String EXTERN_PLAIN = "palin-extern-name";
	public static final String EXTERN_HTML = "html-extern-name";

	/**
	 * 字符常量
	 */
	public static final String HTML_FORMAT = "text/html;charset=";
	public static final String PLAIN_FORMAT = "text/plain;charset=";
	public static final String HTML_200 = "HTTP/1.1 200 OK";
	public static final String HTML_206 = "HTTP/1.1 206 Partial Content";
	public static final String HTML_CONTENT_TYPE1 = "application/octet-stream";
	public static final String HTML_CONTENT_DIS = "attachment";
	public static final String HTML_ALIVE = "Keep-Alive";

	/**
	 * 符号常量
	 */
	public static final String SPACE = " ";
	public static final String COLON = ":";
	public static final String EQUAL = "=";
	public static final String BREAK = "-";
	public static final String SLASH = "/";
	public static final char DOT = '.';
	public static final String VERTICAL = "\\|";
}
