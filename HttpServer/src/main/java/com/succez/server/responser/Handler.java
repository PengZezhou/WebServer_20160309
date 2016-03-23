package com.succez.server.responser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Method;

/**
 * 响应器
 * 
 * @author Peng.Zezhou
 *
 */
public class Handler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);
	private Socket socket;

	/**
	 * 构造函数
	 * 
	 * @param socket
	 */
	public Handler(Socket socket,String str) {
		this.socket = socket;
		responseHandler(str);
	}

	/**
	 * 响应客户端
	 * 
	 * @param str
	 *            待传输字符
	 */
	private void responseHandler(String str) {
		byte[] bytes = str.getBytes();
		OutputStream out = null;
		try {
			out = socket.getOutputStream();
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			LOGGER.error("响应异常，IO异常");
		} finally {
			Method.closeStream(out);
			Method.closeStream(socket);
		}
	}
}
