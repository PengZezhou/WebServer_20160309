package com.succez.server.launcher;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * ������������
 * 
 * @author Peng.Zezhou
 *
 */
public class Launcher {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Launcher.class);

	/**
	 * ��������������ں���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.info("initing server ...");
		Server.getInstance();
		LOGGER.info("start command monitor ...");
		commandMonitor();
		LOGGER.info("exiting server ...");
		exitServer();
	}

	/**
	 * ��������������ܲ���������еķ�������������
	 * <p>
	 * {@code exit}�˳���������{@code help}��ʾ����������
	 */
	private static final void commandMonitor() {
		Scanner scanner = new Scanner(System.in);
		String str = null;
		LOGGER.info("command monitor started");
		System.out.println(Constant.TIPS);
		while (true) {
			str = scanner.nextLine();
			if (Constant.EXIT.equals(str)) {
				break;
			} else if (Constant.HELP.equals(str)) {
				System.out.println(Constant.PROMT);
			} else {
				System.out.println(Constant.TIPS);
				continue;
			}
		}
		scanner.close();
		LOGGER.info("command monitor exited");
	}

	/**
	 * ������������ݣ��˳�������
	 * 
	 */
	private static final void exitServer() {
		LOGGER.info("server cleanning...");
		LOGGER.info("�ر��̳߳�...");
		ThreadPool.getInstance().thread_pool.shutdown();
		LOGGER.info("�̳߳��ѹر�");
		LOGGER.info("�ر�serverSocket...");
		Method.closeStream(Server.getInstance().server_socket);
		LOGGER.info("server exited");
	}
}