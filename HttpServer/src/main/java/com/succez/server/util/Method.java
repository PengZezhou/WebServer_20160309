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
 * �������߼��������ǰ�����߼�
 * 
 * @author Peng.Zezhou
 *
 */
public class Method {
	private static final Logger LOGGER = Logger.getLogger(Method.class);
	private static final Properties pro = getProperties();

	/**
	 * ʵ����һ��ServerSocket
	 * 
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
			Method.closeStream(stream);
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
			LOGGER.warn("�˿ں�ת��Ϊ�����쳣��Ϊ����ϵͳ�˳�������Ĭ��ֵ��80��");
		}
		if (result < 1 || result > 65535) {
			LOGGER.warn("�˿ںŲ�����ֵ�䣬ϵͳʹ��Ĭ��ֵ");
			result = 80;
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
			LOGGER.warn("���������ת��Ϊ�����쳣��Ϊ����ϵͳ�˳�������Ĭ��ֵ��50��");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("��������������������ֵ�䣬ϵͳʹ��Ĭ��ֵ");
			result = 5;
		}
		return result;
	}

	/**
	 * ��ȡ�����ļ��̳߳�ά���̵߳���������
	 * 
	 * @return �̳߳�ά���̵߳���������
	 */
	public static final int getCorePoolSize() {
		int result = 5;
		try {
			result = Integer.parseInt(pro.getProperty("corePoolSize", "5"));
		} catch (NumberFormatException e) {
			LOGGER.warn("��С�߳���ת��Ϊ�����쳣��Ϊ����ϵͳ�˳�������Ĭ��ֵ��5��");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("�̳߳����þ��棬CorePoolSize������ֵ�䣬ϵͳʹ��Ĭ��ֵ");
			result = 5;
		}
		return result;
	}

	/**
	 * ��ȡ�����ļ��̳߳�ά���̵߳��������
	 * 
	 * @return �̳߳�ά���̵߳��������
	 */
	public static final int getMaxNumPoolSize() {
		int result = 20;
		try {
			result = Integer.parseInt(pro.getProperty("maximumPoolSize", "20"));
		} catch (NumberFormatException e) {
			LOGGER.warn("����߳���ת��Ϊ�����쳣��Ϊ����ϵͳ�˳�������Ĭ��ֵ��20��");
		}
		if (result < 1 || result > 10000) {
			LOGGER.warn("�̳߳����þ��棬CorePoolSize������ֵ�䣬ϵͳʹ��Ĭ��ֵ");
			result = 20;
		}
		return result;
	}

	/**
	 * ��ȡ�����ļ��̳߳�ά���߳�������Ŀ���ʱ�� (��)
	 * 
	 * @return �̳߳�ά���߳�������Ŀ���ʱ��
	 */
	public static final int getKeepAliveTime() {
		int result = 30;
		try {
			result = Integer.parseInt(pro.getProperty("keepAliveTime", "60"));
		} catch (NumberFormatException e) {
			LOGGER.warn("�߳��������ʱ��ת��Ϊ�����쳣��Ϊ����ϵͳ�˳�������Ĭ��ֵ��30��");
		}
		if (result < 20 || result > 6000) {
			LOGGER.warn("�̳߳����þ��棬CorePoolSize������ֵ�䣬ϵͳʹ��Ĭ��ֵ");
			result = 30;
		}
		return result;
	}

	/**
	 * ��ȡһ���̳߳�ʵ��
	 * 
	 * @return �̳߳�ʵ��
	 */
	public static final ThreadPoolExecutor getThreadPool() {
		LOGGER.info("��ʼ���̳߳�...");
		ThreadPoolExecutor thread_pool = new ThreadPoolExecutor(
				Constant.CORE_POOL_SIZE, Constant.MAX_NUM_POOL_SIZE,
				Constant.KEEP_ALIVE_TIME, Constant.TIME_UNIT,
				Constant.BLOCK_QUEUE, Constant.HANDLER);
		LOGGER.info("�̳߳س�ʼ������");
		return thread_pool;
	}

	/**
	 * �ر������߼���װ
	 * 
	 * @param closeavle
	 *            ������
	 */
	public static final void closeStream(Closeable closeavle) {
		if (closeavle != null) {
			try {
				closeavle.close();
			} catch (IOException e) {
				LOGGER.error(closeavle.toString() + " ���رճ����쳣");
			}
		}
	}
}