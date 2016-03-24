package com.succez.server.analysis.directory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.responser.Handler;
import com.succez.server.util.Method;

/**
 * 文件目录列表模块
 * 
 * @author Peng.Zezhou
 *
 */
public class DirectoryInfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DirectoryInfo.class);

	/**
	 * 目录下文件列表
	 * 
	 * @param socket
	 *            客户连接socket
	 * @param file
	 *            用户请求的文件夹
	 */
	public DirectoryInfo(Socket socket, File file) {
		this.file = file;
		new Handler(socket, this.listFromDirectory());
	}

	private File file = null;

	/**
	 * 列出目录文件信息
	 * 
	 * @param file
	 *            用户请求的文件夹
	 * @return 文件列表信息
	 */
	private String listFromDirectory() {
		String str = null;
		try {
			str = new String(Method.directoryToHtml(file).getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("文件列表信息转换出错");
		}
		return str;
	}
}
