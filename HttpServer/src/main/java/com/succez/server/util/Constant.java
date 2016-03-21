package com.succez.server.util;

/**
 * 常量定义类
 * 
 * @author Peng.Zezhou
 *
 */
public class Constant {
	/**
	 * 配置文件路径
	 */
	public static final String SERVER_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\server.properties";
	public static final String LOG4J_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\log4j.properties";

	/**
	 * 启动器常量,控制台提示信息
	 */
	public static final String TIPS = "接受命令列表：'exit' | 'help'";
	public static final String EXIT = "exit";
	public static final String HELP = "help";
	public static final String PROMT = "'exit' 退出服务器";

	public static final Boolean SHUTDOWN = false;

	/**
	 * 服务器常量
	 */
	public static final String SERVER_ENCODE = "utf-8";
	public static final int SERVER_CHACHE = 1024 * 2;

	/**
	 * 文件常量
	 */
	public static final long FILE_SIZE = 1024 * 1;
	public static final String ERROR_404 = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\error\\404.html";
	public static final String ERROR_500 = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\error\\500.html";
	
	/**
	 * 文件后缀
	 */
	public static final String DOWNLOAD_EXTEN_NAME = ".pdf|.docx|.xlsx|.iso|.ico|.jpg";
	public static final String PLAIN_EXTEN_NAME = ".txt";
	public static final String HTML_EXTEN_NAME = ".html|.htm";
	public static final String HTML_HEAD = "<head><title>服务器目录D:\\</title><link rel='shortcut icon' href='/favicon.ico'/></head>";

	/**
	 * 文件下载常量
	 */
	public static final int BYTE_BUFFER_COPACITY = 1024*1024;
	public static final int BUFFER_SIZE = 128*1024;
}
