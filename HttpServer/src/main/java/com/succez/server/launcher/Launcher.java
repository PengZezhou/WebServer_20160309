package com.succez.server.launcher;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * 启动器模块
 * 
 * @author Peng.Zezhou
 *
 */
public class Launcher {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Launcher.class);

	/**
	 * 程序入口
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
	 * 命令管理器，接受用户命令配置服务器
	 * <p>
	 * {@code exit}退出服务器{@code help}显示提示信息
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
	 * 退出服务器前的资源
	 * 
	 */
	private static final void exitServer() {
		LOGGER.info("server cleanning...");
		LOGGER.info("关闭线程池...");
		ThreadPool.getInstance().thread_pool.shutdown();
		LOGGER.info("线程池关闭");
		LOGGER.info("关闭serversocket...");
		Method.closeStream(Server.getInstance().server_socket);
		LOGGER.info("server exited");
	}
}