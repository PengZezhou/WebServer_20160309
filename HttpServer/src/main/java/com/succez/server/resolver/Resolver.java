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
	 * 构造函数，初始化socket
	 * 
	 * @param socket
	 */
	public Resolver(Socket socket) {
		this.socket = socket;
	}

	/**
	 * url解析处理，按类型分发到下层处理
	 */
	public void urlResolve() {
		LOGGER.info("开始解析url");
		DefaultBHttpServerConnection con = null;
		try {
			con = new DefaultBHttpServerConnection(Constant.SERVER_CHACHE);
			con.bind(socket);
			HttpRequest req = con.receiveRequestHeader();
			String uri = URLDecoder.decode(req.getRequestLine().getUri(),
					Constant.SERVER_ENCODE);
			urlType(uri);
		} catch (IOException e) {
			LOGGER.info("解析url，出现IO异常");
		} catch (HttpException e) {
			LOGGER.info("解析url，出现http异常");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (IOException e) {
					LOGGER.info("解析url，http关闭连接异常");
				}
			}
		}
	}

	/**
	 * 解析url地址，判断请求类型（文件夹、文件、其他）
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
	 * 根据类型，调用相应处理模块
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
