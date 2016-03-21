package com.succez.server.analysis.reader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.responser.Handler;
import com.succez.server.util.Method;

/**
 * 文件读取模块
 * 
 * @author Peng.Zezhou
 *
 */
public class ReadFile {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadFile.class);

	/**
	 * 构造函数
	 * 
	 * @param socket
	 *            连接socket
	 * @param file
	 *            待读文件
	 */
	public ReadFile(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
		new Handler(this.socket, this.convertFromFile());
	}

	private Socket socket = null;
	private File file = null;

	/**
	 * 将文件内容转化为字符串
	 * 
	 * @param file
	 *            文件路径
	 * @return 内容字符串
	 */
	private String convertFromFile() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 200 OK\r\n");
		sb.append("Connection: Keep-Alive\r\n");
		int index = file.getName().lastIndexOf(".");
		if (index != -1) {
			String prefix = file.getName().substring(index);
			if (prefix.equals(".html") || prefix.equals(".htm")) {
				sb.append("Content-type:text/html\r\n\r\n");
			} else {
				sb.append("Content-type:text/plain\r\n\r\n");
			}
		} else {
			sb.append("Content-type:text/plain\r\n\r\n");
		}
		String str = null;
		LOGGER.info("读取文件");
		byte[] bytes = Method.file2buf(file);
		try {
			str = new String(bytes, Method.getFileEncode(file));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("编码转换，不支持的编码格式");
		}
		sb.append(str);
		return sb.toString();
	}
}
