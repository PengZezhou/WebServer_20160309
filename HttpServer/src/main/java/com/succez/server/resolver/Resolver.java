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
	 * 根据类型，调用相应处理模块
	 * 
	 * @param flag
	 */
	private void assignment(int flag, File file) {
		LOGGER.info("应答标志位 " + flag);
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
	 * 返回file是否是给定限制类的文件类型，是返回true，否则返回false
	 * 
	 * @param file
	 *            文件
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
				LOGGER.info("文件将被下载处理");
				return true;
			}
		}
		return false;
	}
}
