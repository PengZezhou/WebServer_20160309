package com.succez.server.util;

import java.net.ServerSocket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * ����������
 * 
 * @author Peng.Zezhou
 *
 */
public class Constant {
	/**
	 * �����ļ�·��
	 */
	public static final String SERVER_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\server.properties";
	public static final String LOG4J_CONFIG_INFO = System
			.getProperty("user.dir")
			+ "\\src\\main\\resources\\log4j.properties";

	/**
	 * ����������,����̨��ʾ��Ϣ
	 */
	public static final String TIPS = "���������б�'exit' | 'help'";
	public static final String EXIT = "exit";
	public static final String HELP = "help";
	public static final String PROMT = "'exit' �˳�������";

	/**
	 * serverSocket����,�����ļ��ж�ȡ
	 */
	public static final String IP = Method.getIPValue();
	public static final int PORT = Method.getPortValue();
	public static final int MAX_CONNECTION = Method.getMaxConValue();
	public static final ServerSocket SERVER_SOCKET = Method.getOneServer();
	public static final Boolean SHUTDOWN = false;

	/**
	 * �̳߳�������ã������ļ���ȡ
	 */
	public static final int CORE_POOL_SIZE = Method.getCorePoolSize();
	public static final int MAX_NUM_POOL_SIZE = Method.getMaxNumPoolSize();
	public static final int KEEP_ALIVE_TIME = Method.getKeepAliveTime();
	public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	public static final BlockingQueue<Runnable> BLOCK_QUEUE = new ArrayBlockingQueue<Runnable>(
			5);
	public static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.DiscardOldestPolicy();
	public static final ThreadPoolExecutor THREAD_POOL = Method.getThreadPool();

	/**
	 * ����������
	 */
	public static final String SERVER_ENCODE = "utf-8";
	public static final int SERVER_CHACHE = 1024 * 2;

	/**
	 * �ļ�����
	 */
	public static final long FILE_SIZE = 1024 * 1;
	public static final String ERROR_404 = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\error\\404.html";
	public static final String ERROR_500 = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\error\\500.html";
	public static final String EXTEN_NAME = ".pdf|.docx|.xlsx|.iso|.ico|.jpg";
	public static final String HTML_HEAD = "<head><title>������Ŀ¼D:\\</title><link rel='shortcut icon' href='/favicon.ico'/></head>";

	/**
	 * �ļ����س���
	 */
	public static final int BYTE_BUFFER_COPACITY = 786432;
	public static final int BUFFER_SIZE = 131072;
}
