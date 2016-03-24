package com.succez.server.resolver;

import java.io.IOException;
import java.io.PrintStream;
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
 * 解析器
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
	 * 解析客户连接
	 * 
	 * @param socket
	 */
	public Resolver(Socket socket) {
		this.socket = socket;
		urlResolve();
	}

	/**
	 * 解析出url请求信息
	 */
	private void urlResolve() {
		LOGGER.info("开始解析url");
		try {
			con = new DefaultBHttpServerConnection(Constant.SERVER_CHACHE);
			con.bind(socket);
			HttpRequest req = con.receiveRequestHeader();
			String uri = URLDecoder.decode(req.getRequestLine().getUri(),
					Constant.SERVER_ENCODE);
			new Analysis(new PrintStream(this.socket.getOutputStream(),true), uri);
		} catch (IOException e) {
			LOGGER.info("url解析异常，IO异常");
		} catch (HttpException e) {
			LOGGER.info("url解析异常，IO异常");
		} finally {
			Method.closeStream(con);
		}
	}
}
