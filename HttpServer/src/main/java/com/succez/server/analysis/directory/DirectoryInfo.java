package com.succez.server.analysis.directory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.responser.Handler;
import com.succez.server.util.Method;

/**
 * Ŀ¼�б�ģ��
 * 
 * @author Peng.Zezhou
 *
 */
public class DirectoryInfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DirectoryInfo.class);

	/**
	 * ���캯��
	 * 
	 * @param socket
	 *            ����socket
	 * @param file
	 *            �ļ�Ŀ¼
	 */
	public DirectoryInfo(Socket socket, File file) {
		this.file = file;
		new Handler(socket, this.listFromDirectory());
	}

	private File file = null;

	/**
	 * �г���ǰ�ļ����µ��б���Ϣ
	 * 
	 * @param file
	 *            ��ǰ�ļ���
	 * @return ��ǰ�ļ����µ��б���Ϣ
	 */
	private String listFromDirectory() {
		String str = null;
		try {
			str = new String(Method.directoryToHtml(file).getBytes(), "GBK");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("����ת������֧�ֵı���ת��");
		}
		return str;
	}
}
