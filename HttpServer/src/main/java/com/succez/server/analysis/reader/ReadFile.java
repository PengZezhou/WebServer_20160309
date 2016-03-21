package com.succez.server.analysis.reader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.http.Response;
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
		// ����Э��ͷ
		Response r = new Response();
		if(Method.fileHtmlRead(file)){
			r.setContent_Type("text/html");
		}
		sb.append(r.toString());
		
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
