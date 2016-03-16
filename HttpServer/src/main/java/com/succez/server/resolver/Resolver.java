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
import com.succez.server.downloader.FileDownload;
import com.succez.server.reader.ReadFile;
import com.succez.server.responser.Handler;
import com.succez.server.util.Constant;

public class Resolver {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Resolver.class);
	private Socket socket;
	private DefaultBHttpServerConnection con = null;

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
		LOGGER.info("url " + url);
		if (file.isDirectory()) {
			tmp = 1;
		} else if (file.isFile() && file.canRead() && !fileType(file)
				&& file.length() <= (Integer.MAX_VALUE)) {
			tmp = 2;
		} else if (!file.exists()) {
			tmp = 3;
		} else if (file.isFile() && file.canRead()
				&& (file.length() > (Integer.MAX_VALUE) || fileType(file))) {
			tmp = 4;
		} else {
			tmp = 5;
		}
		assignment(tmp, file);
	}

	/**
	 * �������ͣ�������Ӧ����ģ��
	 * 
	 * @param flag
	 */
	private void assignment(int flag, File file) {
		LOGGER.info("Ӧ���־λ " + flag);
		String str = "error";
		Handler handler = new Handler(this.socket);
		switch (flag) {
		case 1:
			DirectoryInfo d = new DirectoryInfo();
			str = d.listFromDirectory(file);
			handler.responseHandler(str);
			break;
		case 2:
			ReadFile r1 = new ReadFile();
			str = r1.convertFromFile(file);
			handler.responseHandler(str);
			break;
		case 3:
			ReadFile r2 = new ReadFile();
			str = r2.convertFromFile(new File(Constant.ERROR_404));
			handler.responseHandler(str);
			break;
		case 4:
			FileDownload f = new FileDownload(socket);
			f.downloadFile(file);
			break;
		default:
			ReadFile r3 = new ReadFile();
			str = r3.convertFromFile(new File(Constant.ERROR_500));
			handler.responseHandler(str);
			break;
		}
	}

	/**
	 * ����file�Ƿ��Ǹ�����������ļ����ͣ��Ƿ���true�����򷵻�false
	 * 
	 * @param file
	 *            �ļ�
	 * @return
	 */
	private Boolean fileType(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = Constant.EXTEN_NAME.split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("�ļ��������ش���");
				return true;
			}
		}
		return false;
	}
}
