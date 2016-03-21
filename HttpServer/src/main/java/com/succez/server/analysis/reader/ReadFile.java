package com.succez.server.analysis.reader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.responser.Handler;
import com.succez.server.util.Method;

/**
 * �ļ���ȡģ��
 * 
 * @author Peng.Zezhou
 *
 */
public class ReadFile {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadFile.class);

	/**
	 * ���캯��
	 * 
	 * @param socket
	 *            ����socket
	 * @param file
	 *            �����ļ�
	 */
	public ReadFile(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
		new Handler(this.socket, this.convertFromFile());
	}

	private Socket socket = null;
	private File file = null;

	/**
	 * ���ļ�����ת��Ϊ�ַ���
	 * 
	 * @param file
	 *            �ļ�·��
	 * @return �����ַ���
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
		LOGGER.info("��ȡ�ļ�");
		byte[] bytes = Method.file2buf(file);
		try {
			str = new String(bytes, Method.getFileEncode(file));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("����ת������֧�ֵı����ʽ");
		}
		sb.append(str);
		return sb.toString();
	}
}
