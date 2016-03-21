package com.succez.server.analysis.directory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.responser.Handler;
import com.succez.server.util.Method;

/**
 * 目录列表模块
 * 
 * @author Peng.Zezhou
 *
 */
public class DirectoryInfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DirectoryInfo.class);

	/**
	 * 构造函数
	 * 
	 * @param socket
	 *            连接socket
	 * @param file
	 *            文件目录
	 */
	public DirectoryInfo(Socket socket, File file) {
		this.file = file;
		new Handler(socket, this.listFromDirectory());
	}

	private File file = null;

	/**
	 * 列出当前文件夹下的列表信息
	 * 
	 * @param file
	 *            当前文件夹
	 * @return 当前文件夹下的列表信息
	 */
	private String listFromDirectory() {
		String str = null;
		try {
			str = new String(Method.directoryToHtml(file).getBytes(), "GBK");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("编码转换，不支持的编码转换");
		}
		return str;
	}
}
