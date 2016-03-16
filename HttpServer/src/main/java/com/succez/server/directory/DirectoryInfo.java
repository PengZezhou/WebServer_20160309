package com.succez.server.directory;

import java.io.File;

public class DirectoryInfo {

	/**
	 * 列出当前文件夹下的列表信息
	 * 
	 * @param file
	 *            当前文件夹
	 * @return 当前文件夹下的列表信息
	 */
	public String listFromDirectory(File file) {
		StringBuilder sb = new StringBuilder();
		File[] arr = file.listFiles();
		for (File f : arr) {
			char c = f.getName().charAt(0);
			if (c == '.' || c == '~' || c == '$') {
				continue;
			}
			sb.append(convertToHtml(f.getPath()));
		}
		return sb.toString();
	}

	/**
	 * 为字符串包装html标签
	 * 
	 * @param str
	 *            字符串
	 * @return 带html标签的字符串
	 */
	private String convertToHtml(String str) {
		String s = String.format("<a href='%s'>%s</a><br/>", str, str);
		return s;
	}
}
