package com.succez.server.analysis;

import java.io.File;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.analysis.directory.DirectoryInfo;
import com.succez.server.analysis.downloader.FileDownload;
import com.succez.server.analysis.reader.ReadFile;
import com.succez.server.util.Constant;
import com.succez.server.util.Method;

public class Analysis {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Analysis.class);

	/**
	 * 构造函数
	 * 
	 * @param socket
	 * @param url
	 */
	public Analysis(Socket socket, String url) {
		this.url = url;
		this.socket = socket;
		this.urlType();
		this.processUrl();
	}

	private Socket socket;
	private String url;
	private int type;

	/**
	 * 分析url类型
	 * 
	 * @param url
	 * @return
	 */
	private void urlType() {
		File file = new File(url);
		LOGGER.info("url " + url);
		if (file.isDirectory()) {
			type = 1;
		} else if (file.isFile() && file.canRead() && !Method.fileType(file)
				&& file.length() <= (Integer.MAX_VALUE)) {
			type = 2;
		} else if (!file.exists()) {
			type = 3;
		} else if (file.isFile()
				&& file.canRead()
				&& (file.length() > (Integer.MAX_VALUE) || Method
						.fileType(file))) {
			type = 4;
		} else {
			type = 5;
		}
	}

	/**
	 * 处理url
	 * 
	 * @param url
	 */
	private void processUrl() {
		LOGGER.info("应答标志位 " + type);
		File file = new File(url);
		switch (type) {
		case 1:
			new DirectoryInfo(socket, file);
			break;
		case 2:
			new ReadFile(socket, file);
			break;
		case 3:
			new ReadFile(socket, new File(Constant.ERROR_404));
			break;
		case 4:
			new FileDownload(socket, file);
			break;
		default:
			new ReadFile(socket, new File(Constant.ERROR_500));
			break;
		}
	}
}
