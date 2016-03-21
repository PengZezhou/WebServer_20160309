package com.succez.server.analysis.directory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.responser.Handler;
import com.succez.server.util.Constant;

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
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 200 OK\r\n");
		sb.append("Connection: Keep-Alive\r\n");
		sb.append("Content-type:text/html\r\n\r\n");
		sb.append("<html>");
		sb.append(Constant.HTML_HEAD);
		sb.append("<body>");
		sb.append("<a href='/'>��Ŀ¼</a><br/>");
		sb.append("<a href='javascript:history.go(-1)'>�����ϼ�</a><br/>");
		File[] arr = file.listFiles();
		for (File f : arr) {
			String s = null;
			char c = f.getName().charAt(0);
			if (c == '.' || c == '~' || c == '$') {
				continue;
			} else if (f.isHidden()) {
				continue;
			} else if (f.isFile()) {
				s = String.format("<a href='%s' target='_blank'>%s</a><br/>", f
						.getPath().replace('\\', '/'), f.getName());
			} else {
				s = String.format("<a href='%s'>%s</a><br/>", f.getPath()
						.replace('\\', '/'), f.getName());
			}
			sb.append(s);
		}
		sb.append("</body>");
		sb.append("</html>");
		String str = null;
		try {
			str = new String(sb.toString().getBytes(), "GBK");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("����ת������֧�ֵı���ת��");
		}
		return str;
	}
}
