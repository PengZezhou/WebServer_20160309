package com.succez.server.analysis.reader;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.analysis.FileConfig;
import com.succez.server.http.Response;
import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * 文件预览模块
 * 
 * @author Peng.Zezhou
 *
 */
public class ReadFile {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadFile.class);

	/**
	 * 构造函数
	 * 
	 * @param pstream
	 *            写客户端流
	 * @param file
	 *            请求的预览文件
	 */
	public ReadFile(PrintStream pstream, File file) {
		this.pstream = pstream;
		this.file = file;
		convertFromFile();
		Method.closeStream(this.pstream);
	}

	private PrintStream pstream = null;
	private File file = null;

	/**
	 * 文件转换为字符串
	 * 
	 * @param file
	 *            请求的预览文件
	 * @return 字符串
	 */
	private void convertFromFile() {
		// 响应头设置
		Response r = new Response();
		String encode = Method.getFileEncode(file);
		if (encode.toLowerCase().equals(Constant.GBK_ENCODE)) {
			r.setContent_Type(Constant.PLAIN_FORMAT + encode);
		}
		if (fileHtmlRead(file)) {
			r.setContent_Type(Constant.HTML_FORMAT + encode);
		}
		r.toStream(this.pstream);;

		LOGGER.info("预览文件转换开始");
		try {
			this.pstream.write(Method.file2buf(file));
		} catch (IOException e) {
			LOGGER.info("文件写入流出错，IO异常");
		}
	}

	/**
	 * 读取html文件
	 * 
	 * @param file
	 * @return
	 */
	private Boolean fileHtmlRead(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = FileConfig.getInstance().getHtml_extern().split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("解析html文件");
				return true;
			}
		}
		return false;
	}
}
