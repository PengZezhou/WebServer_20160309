package com.succez.server.resolver;

import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.analysis.Analysis;
import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * ��������������
 * 
 * @author Peng.Zezhou
 *
 */
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
		urlResolve();
	}

	/**
	 * url�������������ͷַ����²㴦��
	 */
	private void urlResolve() {
		LOGGER.info("��ʼ����url");
		try {
			con = new DefaultBHttpServerConnection(Constant.SERVER_CHACHE);
			con.bind(socket);
			HttpRequest req = con.receiveRequestHeader();
			String uri = URLDecoder.decode(req.getRequestLine().getUri(),
					Constant.SERVER_ENCODE);
			new Analysis(this.socket,uri);
		} catch (IOException e) {
			LOGGER.info("����url������IO�쳣");
		} catch (HttpException e) {
			LOGGER.info("����url������http�쳣");
		} finally {
			Method.closeStream(con);
		}
	}
}
