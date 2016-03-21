package com.succez.server.http;

/**
 * http响应协议头
 * @author Peng.Zezhou
 *
 */
public class Response {

	// http版本
	private String HttpVersion = "HTTP/1.1 200 OK";
	// 应答日期
	private String Date = null;
	// 连接信息
	private String Connection = "Keep-Alive";
	// 内容长度
	private String Content_Length = null;
	// 内容类型
	private String Content_Type = "text/plain";
	// 缓存控制
	private String Cache_control = null;

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

	public String getContent_Length() {
		return Content_Length;
	}

	public void setContent_Length(String content_Length) {
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(HttpVersion + "\r\n");
		if (Date != null) {
			sb.append("Date: " + Date + "\r\n");
		}
		if (Connection != null) {
			sb.append("Connection: " + Connection + "\r\n");
		}
		if (Content_Length != null) {
			sb.append("Content-Length: " + Content_Length + "\r\n");
		}
		if (Content_Type != null) {
			sb.append("Content-Type: " + Content_Type + "\r\n");
		}
		if (Cache_control != null) {
			sb.append("Cache-control: " + Cache_control + "\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}
}
