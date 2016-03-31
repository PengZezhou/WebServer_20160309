package com.succez.server.http;

import java.io.PrintStream;

import com.succez.server.util.Constant;

/**
 * http响应类
 * 
 * @author Peng.Zezhou
 *
 */
public class Response {

	// http协议头
	private String httpVersion = Constant.HTML_200;
	// 日期
	private String date = null;
	// 保活时间
	private String connection = Constant.HTML_ALIVE;
	// 内容长度
	private long content_Length = 0;
	// 内容类型
	private String content_Type = Constant.PLAIN_FORMAT
			+ Constant.SERVER_ENCODE;
	// 缓存控制
	private String cache_control = null;
	// 强制下载
	private String content_Disposition = null;
	// 文件下载范围
	private String content_Range = null;

	public String getContent_Range() {
		return content_Range;
	}

	public void setContent_Range(String content_Range) {
		this.content_Range = content_Range;
	}

	public String getContent_Disposition() {
		return content_Disposition;
	}

	public void setContent_Disposition(String content_Disposition) {
		this.content_Disposition = content_Disposition;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getConnection() {
		return this.connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public long getContent_Length() {
		return this.content_Length;
	}

	public void setContent_Length(long content_Length) {
		this.content_Length = content_Length;
	}

	public String getContent_Type() {
		return this.content_Type;
	}

	public void setContent_Type(String content_Type) {
		this.content_Type = content_Type;
	}

	public String getCache_control() {
		return this.cache_control;
	}

	public void setCache_control(String cache_control) {
		this.cache_control = cache_control;
	}

	/**
	 * 将响应类按http可理解格式输出
	 */
	public void toStream(PrintStream ps) {
		ps.println(httpVersion);
		if (date != null) {
			ps.println("Date: " + date);
		}
		if (connection != null) {
			ps.println("Connection: " + connection);
		}
		if (content_Length != 0) {
			ps.println("Content-Length: " + content_Length);
		}
		if (content_Type != null) {
			ps.println("Content-Type: " + content_Type);
		}
		if (cache_control != null) {
			ps.println("Cache-control: " + cache_control);
		}
		if (content_Disposition != null) {
			ps.println("Content-Disposition: " + content_Disposition);
		}
		if (content_Range != null) {
			ps.println("Content-Range: " + content_Range);
		}
		ps.println();
	}
}
