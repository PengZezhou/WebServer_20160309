package com.succez.server.util;

import java.net.ServerSocket;

public class Constant {
	/**
	 * ����������
	 */
	public static final String TIPS = "���������б�'exit' | 'help'";
	public static final String EXIT = "exit";
	public static final String HELP = "help";
	public static final String PROMT = "'exit' �˳�������";
	
	/**
	 * ����������,�����ļ��ж�ȡ
	 */
	public static final String IP = Method.getIPValue();
	public static final int PORT = Method.getPortValue();
	public static final int MAX_CONNECTION = Method.getMaxConValue();
	public static final ServerSocket SERVER_SOCKET = Method.getOneServer();
	public static final Boolean SHUTDOWN = true;
	
	/**
	 * �����ļ�·��
	 */
	public static final String SERVER_CONFIG_INFO = System.getProperty("user.dir")+"\\src\\main\\resources\\server.properties";
	public static final String LOG4J_CONFIG_INFO = System.getProperty("user.dir")+"\\src\\main\\resources\\log4j.properties";
}
