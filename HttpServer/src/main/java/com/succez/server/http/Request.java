package com.succez.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {
	private static final Logger LOGGER = LoggerFactory.getLogger(Request.class);

	private String type;
	private String version;
	private Map<String, String> head = new HashMap<String, String>();
	private String url;
	private String body;

	public String getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	public Map<String, String> getHead() {
		return head;
	}

	public String getUrl() {
		return url;
	}

	public String getBody() {
		return body;
	}

	/**
	 * 请求绑定socket
	 * 
	 * @param socket
	 */
	public void bind(Socket socket) {
		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String str = b.readLine();
			String[] strs = str.split(" ");
			this.type = strs[0];
			this.url = strs[1];
			this.version = strs[2];
			while ((str = b.readLine()) != null) {
				if (str.isEmpty()) {
					break;
				} else {
					if (str.indexOf(':') != -1) {
						strs = str.split(":");
						this.head.put(strs[0], strs[1]);
					} else {
						this.body = str;
					}
				}
			}
			LOGGER.info("协议解析完成");
		} catch (IOException e) {
			LOGGER.info("协议解析错误，IO异常");
		} catch (IndexOutOfBoundsException e) {
			LOGGER.info("协议解析错误，数组越界");
		} catch (NullPointerException e) {
			LOGGER.info("协议解析错误，空指针异常");
		}
	}
}
