package com.succez.server.analysis.reader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.analysis.FileConfig;
import com.succez.server.http.Response;
import com.succez.server.responser.Handler;
import com.succez.server.util.Method;

/**
 * 文件预览模块
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
	 *            客户连接socket
	 * @param file
	 *            请求的预览文件
	 */
	public ReadFile(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
		new Handler(this.socket, this.convertFromFile());
	}

	private Socket socket = null;
	private File file = null;

	/**
	 * 文件转换为字符串
	 * 
	 * @param file
	 *            请求的预览文件
	 * @return 字符串
	 */
	private String convertFromFile() {
		StringBuilder sb = new StringBuilder();
		// 响应头设置
		Response r = new Response();
		if (fileHtmlRead(file)) {
			r.setContent_Type("text/html");
		}
		sb.append(r.toString());

		String str = null;
		LOGGER.info("预览文件转换开始");
		byte[] bytes = Method.file2buf(file);
		try {
			str = new String(bytes, Method.getFileEncode(file));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("字符串转换指定编码设置错误");
		}
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 读取html文件
	 * 
	 * @param file
	 * @return
	 */
	private Boolean fileHtmlRead(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = FileConfig.getInstance().getHtml_extern().split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("解析html文件");
				return true;
			}
		}
		return false;
	}
}
