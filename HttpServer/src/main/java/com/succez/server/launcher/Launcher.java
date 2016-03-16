package com.succez.server.launcher;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.connector.Connector;
import com.succez.server.util.Constant;

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
		initServer();
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
	 * ��ʼ�����������ݣ�����������
	 */
	private static final void initServer() {
		if (Constant.SERVER_SOCKET == null) {
			LOGGER.warn("server init failed");
			return;
		}
		LOGGER.info("SocketServer created");
		if (Constant.THREAD_POOL == null) {
			LOGGER.warn("thread pool init failed");
			return;
		}
		LOGGER.info("thread pool inited");
		Constant.THREAD_POOL.execute(new ServerThread());
	}

	/**
	 * ������������ݣ��˳�������
	 * 
	 */
	private static final void exitServer() {
		LOGGER.info("server cleanning...");
		Connector connector = new Connector();
		connector.resourceCleaner();
		LOGGER.info("server exited");
	}
}