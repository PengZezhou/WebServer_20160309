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
				LOGGER.info("exiting server ...");
				Server.getInstance().stop();
				break;
			} else if (Constant.HELP.equals(str)) {
				System.out.println(Constant.PROMT);
			} else {
				System.out.println(Constant.TIPS);
				continue;
			}
		}
		Method.closeStream(scanner);
		LOGGER.info("command monitor exited");
	}
}