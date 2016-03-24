package com.succez.server.analysis;

import java.io.File;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.analysis.directory.DirectoryInfo;
import com.succez.server.analysis.downloader.FileDownload;
import com.succez.server.analysis.reader.ReadFile;

public class Analysis {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Analysis.class);

	/**
	 * 请求分类处理
	 * 
	 * @param socket
	 * @param url
	 */
	public Analysis(PrintStream pstream, String url) {
		this.url = url;
		this.pstream = pstream;
		this.urlType();
		this.processUrl();
	}

	private PrintStream pstream;
	private String url;
	private int type;

	/**
	 * 获取请求类型
	 * 
	 * @param url
	 * @return
	 */
	private void urlType() {
		File file = new File(url);
		LOGGER.info("url " + url);
		if (file.isDirectory()) {
			type = 1;
		} else if (file.isFile() && file.canRead() && !fileDownload(file)
				&& file.length() <= (Integer.MAX_VALUE)) {
			type = 2;
		} else if (!file.exists()) {
			type = 3;
		} else if (file.isFile() && file.canRead()
				&& (file.length() > (Integer.MAX_VALUE) || fileDownload(file))) {
			type = 4;
		} else {
			type = 5;
		}
	}

	/**
	 * 处理url请求
	 * 
	 * @param url
	 */
	private void processUrl() {
		LOGGER.info("请求类型  " + type);
		File file = new File(url);
		switch (type) {
		case 1:
			new DirectoryInfo(pstream, file);
			break;
		case 2:
			new ReadFile(pstream, file);
			break;
		case 3:
			new ReadFile(pstream, new File(FileConfig.getInstance()
					.getError_404()));
			break;
		case 4:
			new FileDownload(pstream, file);
			break;
		default:
			new ReadFile(pstream, new File(FileConfig.getInstance()
					.getError_500()));
			break;
		}
	}

	/**
	 * 判断文件是否是可下载类型
	 * 
	 * @param file
	 *            待判断文件
	 * @return
	 */
	private Boolean fileDownload(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = FileConfig.getInstance().getDownload_extern()
				.split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("文件将被下载处理");
				return true;
			}
		}
		return false;
	}
}
