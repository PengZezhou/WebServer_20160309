package com.succez.server.analysis.directory;

import java.io.File;
import java.io.PrintStream;

import com.succez.server.http.Response;
import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * 文件目录列表模块
 * 
 * @author Peng.Zezhou
 *
 */
public class DirectoryInfo {

	private PrintStream pstream;

	/**
	 * 目录下文件列表
	 * 
	 * @param pstream
	 *            写客户端流
	 * @param file
	 *            用户请求的文件夹
	 */
	public DirectoryInfo(PrintStream pstream, File file) {
		this.pstream = pstream;
		directoryToHtml(file);
	}

	/**
	 * 将目录信息转化为html格式
	 * 
	 * @param file
	 *            目录
	 * @return
	 */
	private void directoryToHtml(File file) {
		Response r = new Response();
		r.setContent_Type(Constant.HTML_FORMAT + Constant.SERVER_ENCODE);
		r.toStream(this.pstream);
		this.pstream
				.println("<head><title>disk-D:\\</title><link rel='shortcut icon' href='/favicon.ico'/></head>");
		this.pstream.println("<a href='/'>.</a><br/>");
		this.pstream.println("<a href='javascript:history.go(-1)'>..</a><br/>");
		File[] arr = file.listFiles();
		for (File f : arr) {
			char c = f.getName().charAt(0);
			if (c == '.' || c == '~' || c == '$') {
				continue;
			} else if (f.isHidden()) {
				continue;
			} else if (f.isFile()) {
				this.pstream.println("<a href='");
				this.pstream.println(f.getPath().replace('\\', '/'));
				this.pstream.println("' target='_blank'>");
				this.pstream.println(f.getName());
				this.pstream.println("</a><br/>");
			} else {
				this.pstream.println("<a href='");
				this.pstream.println(f.getPath().replace('\\', '/'));
				this.pstream.println("'>");
				this.pstream.println(f.getName());
				this.pstream.println("</a><br/>");
			}
		}
		Method.closeStream(this.pstream);
	}
}
