package com.succez.server.resolver;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.directory.DirectoryInfo;
import com.succez.server.responser.Handler;
import com.succez.server.util.Constant;

public class Resolver {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Resolver.class);
	private Socket socket;

	/**
	 * ���캯������ʼ��socket
	 * 
	 * @param socket
	 */
	public Resolver(Socket socket) {
		this.socket = socket;
	}

	/**
	 * url�������������ͷַ����²㴦��
	 */
	public void urlResolve() {
		LOGGER.info("��ʼ����url");
		DefaultBHttpServerConnection con = null;
		try {
			con = new DefaultBHttpServerConnection(Constant.SERVER_CHACHE);
			con.bind(socket);
			HttpRequest req = con.receiveRequestHeader();
			String uri = URLDecoder.decode(req.getRequestLine().getUri(),
					Constant.SERVER_ENCODE);
			urlType(uri);
		} catch (IOException e) {
			LOGGER.info("����url������IO�쳣");
		} catch (HttpException e) {
			LOGGER.info("����url������http�쳣");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (IOException e) {
					LOGGER.info("����url��http�ر������쳣");
				}
			}
		}
	}

	/**
	 * ����url��ַ���ж��������ͣ��ļ��С��ļ���������
	 * 
	 * @param url
	 * @return
	 */
	private void urlType(String url) {
		int tmp = 0;
		File file = null;
		file = new File(url);
		if (file.isDirectory()) {
			tmp = 1;
		} else if (file.isFile()) {
			tmp = 2;
		} else if (!file.exists()) {
			tmp = 3;
		} else {
			tmp = 4;
		}
		assignment(tmp, file);
	}

	/**
	 * �������ͣ�������Ӧ����ģ��
	 * 
	 * @param flag
	 */
	private void assignment(int flag, File file) {
		String str = "test";
		Handler handler = new Handler(this.socket);
		switch (flag) {
		case 1:
			DirectoryInfo d = new DirectoryInfo();
			str = d.listFromDirectory(file);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;
		}
		handler.responseHandler(str);
	}
}
