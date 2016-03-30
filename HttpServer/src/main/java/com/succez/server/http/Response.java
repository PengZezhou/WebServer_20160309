package com.succez.server.http;

/**
 * http响应类
 * 
 * @author Peng.Zezhou
 *
 */
public class Response {

	// http协议头
	private String HttpVersion = "HTTP/1.1 200 OK";
	// 日期
	private String Date = null;
	// 保活时间
	private String Connection = "Keep-Alive";
	// 内容长度
	private long Content_Length = 0;
	// 内容类型
	private String Content_Type = "text/plain;charset=utf-8";
	// 缓存控制
	private String Cache_control = null;
	// 强制下载
	private String Content_Disposition = null;
	// 文件下载范围
	private String Content_Range = null;

	public String getContent_Range() {
		return Content_Range;
	}

	public void setContent_Range(String content_Range) {
		Content_Range = content_Range;
	}

	public String getContent_Disposition() {
		return Content_Disposition;
	}

	public void setContent_Disposition(String content_Disposition) {
		Content_Disposition = content_Disposition;
	}

	public String getHttpVersion() {
		return HttpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.HttpVersion = httpVersion;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getConnection() {
		return Connection;
	}

	public void setConnection(String connection) {
		Connection = connection;
	}

	public long getContent_Length() {
		return Content_Length;
	}

	public void setContent_Length(long content_Length) {
		Content_Length = content_Length;
	}

	public String getContent_Type() {
		return Content_Type;
	}

	public void setContent_Type(String content_Type) {
		Content_Type = content_Type;
	}

	public String getCache_control() {
		return Cache_control;
	}

	public void setCache_control(String cache_control) {
		Cache_control = cache_control;
	}

	/**
	 * 将响应类按http可理解格式输出
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(HttpVersion + "\r\n");
		if (Date != null) {
			sb.append("Date: " + Date + "\r\n");
		}
		if (Connection != null) {
			sb.append("Connection: " + Connection + "\r\n");
		}
		if (Content_Length != 0) {
			sb.append("Content-Length: " + Content_Length + "\r\n");
		}
		if (Content_Type != null) {
			sb.append("Content-Type: " + Content_Type + "\r\n");
		}
		if (Cache_control != null) {
			sb.append("Cache-control: " + Cache_control + "\r\n");
		}
		if (Content_Disposition != null) {
			sb.append("Content-Disposition: " + Content_Disposition + "\r\n");
		}
		if (Content_Range != null) {
			sb.append("Content-Range: " + Content_Range + "\r\n");
		}
		return sb.toString();
	}
}
