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
	 * ʵ����һ��ServerSocket
	 * @return ����ServerSocketʵ����ʧ�ܷ���null
	 */
	public static final ServerSocket getOneServer() {
		try {
			return new ServerSocket(Constant.PORT, Constant.MAX_CONNECTION,
					InetAddress.getByName(Constant.IP));
		} catch (UnknownHostException e) {
			LOGGER.error("ServerSocket����ʧ�ܣ����ɿ���ip��ַ");
		} catch (IOException e) {
			LOGGER.error("ServerSocket����ʧ�ܣ�IO�쳣");
		}
		LOGGER.info("ServerSocket����ʧ�ܣ�����null");
		return null;
	}

	/**
	 * ��ȡ�����ļ���ȡ����
	 * 
	 * @return Properties
	 */
	private static final Properties getProperties() {
		Properties pro = new Properties();
		File file = new File(Constant.SERVER_CONFIG_INFO);
		InputStream stream = null;
		if (!file.exists()) {
			LOGGER.error("�����ļ��ѱ��ƶ�");
			return null;
		}
		try {
			stream = new FileInputStream(file);
			pro.load(stream);
		} catch (FileNotFoundException e) {
			LOGGER.error("�����ļ��޷����ƶ�λ���ҵ�");
		} catch (IOException e) {
			LOGGER.error("�����ļ���ȡ���������쳣");
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					LOGGER.error("�����ļ���ȡ�����ر��쳣");
				}
			}
		}
		return pro;
	}

	/**
	 * ��ȡ�����ļ�ip��ַ
	 * 
	 * @return ip��ַ����ȡʧ��Ĭ�Ϸ��ء�127.0.0.1��
	 */
	public static final String getIPValue() {
		return pro.getProperty("ip", "127.0.0.1");
	}

	/**
	 * ��ȡ�����ļ��˿�
	 * 
	 * @return �˿ںţ���ȡʧ��Ĭ�Ϸ��ء�80��
	 */
	public static final int getPortValue() {
		int result = 80;
		try {
			result = Integer.parseInt(pro.getProperty("port", "80"));
		} catch (NumberFormatException e) {
			LOGGER.warn("�˿ں�ת��Ϊ�����쳣��Ϊ����ϵͳ�Ƴ�������Ĭ��ֵ��80��");
		}
		return result;
	}

	/**
	 * ��ȡ�����ļ���������������
	 * 
	 * @return ���������ֵ����ȡʧ��Ĭ�Ϸ��ء�50��
	 */
	public static final int getMaxConValue() {
		int result = 50;
		try {
			result = Integer.parseInt(pro.getProperty("max-connection", "50"));
		} catch (NumberFormatException e) {
			LOGGER.warn("���������ת��Ϊ�����쳣��Ϊ����ϵͳ�Ƴ�������Ĭ��ֵ��50��");
		}
		return result;
	}
}