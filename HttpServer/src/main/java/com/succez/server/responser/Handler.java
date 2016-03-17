package com.succez.server.responser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Method;

/**
 * Ӧ����
 * 
 * @author Peng.Zezhou
 *
 */
public class Handler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);
	private Socket socket;

	/**
	 * ���캯��
	 * 
	 * @param socket
	 */
	public Handler(Socket socket) {
		this.socket = socket;
	}

	/**
	 * ��Ӧ�����
	 * 
	 * @param str
	 *            ����ַ���
	 */
	public void responseHandler(String str) {
		byte[] bytes = str.getBytes();
		OutputStream out = null;
		try {
			out = socket.getOutputStream();
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			LOGGER.error("��Ӧ�쳣��IO����");
		} finally {
			Method.closeStream(out);
			Method.closeStream(socket);
		}
	}
}
