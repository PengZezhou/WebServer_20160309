package com.succez.server.analysis;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.analysis.directory.DirectoryInfo;
import com.succez.server.analysis.downloader.FileDownload;
import com.succez.server.analysis.reader.ReadFile;
import com.succez.server.util.Constant;

public class Analysis {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Analysis.class);

	/**
	 * 请求分类处理
	 * 
	 * @param socket
	 * @param url
	 * @throws IOException
	 */
	public Analysis(Socket socket, String url, String range) throws IOException {
		this.url = url;
		this.range = range;
		this.socket = socket;
		this.pstream = new PrintStream(this.socket.getOutputStream(), true);
		this.urlType();
	}

	private String range;
	private Socket socket;
	private PrintStream pstream;
	private String url;

	/**
	 * 获取请求类型并分发处理
	 * <p>目录：包装目录信息为html格式
	 * <p>文件：小文件可读，用文本信息处理展示
	 * <p>文件：文件不存在，返回404错误信息
	 * <p>文件：大文件可读，用NIO读取，支持断点续传
	 * <p>File ：未知错误，返回500服务器错误，未支持此功能
	 * @param url
	 * @return
	 */
	private void urlType() {
		File file = new File(url);
		if (file.isDirectory()) {
			new DirectoryInfo(pstream, file);
		} else if (file.isFile() && file.canRead() && !fileDownload(file)
				&& file.length() <= (Integer.MAX_VALUE)) {
			new ReadFile(pstream, file);
		} else if (!file.exists()) {
			new ReadFile(pstream, new File(
					System.getProperty(Constant.USER_DIR)
							+ FileConfig.getInstance().getError_404()));
		} else if (file.isFile() && file.canRead()
				&& (file.length() > (Integer.MAX_VALUE) || fileDownload(file))) {
			new FileDownload(this.socket, file, this.range);
		} else {
			new ReadFile(pstream, new File(
					System.getProperty(Constant.USER_DIR)
							+ FileConfig.getInstance().getError_500()));
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
		int dot = name.lastIndexOf(Constant.DOT);
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = FileConfig.getInstance().getDownload_extern()
				.split(Constant.VERTICAL);
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("文件将被下载处理");
				return true;
			}
		}
		return false;
	}
}
