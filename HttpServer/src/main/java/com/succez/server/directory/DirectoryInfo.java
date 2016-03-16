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
		sb.append("<a href='/'>.</a><br/>");
		sb.append("<a href='javascript:history.go(-1)'>..</a><br/>");
		File[] arr = file.listFiles();
		for (File f : arr) {
			char c = f.getName().charAt(0);
			if (c == '.' || c == '~' || c == '$') {
				continue;
			}
			if (f.isHidden()) {
				continue;
			}
			String s = String.format("<a href='%s'>%s</a><br/>", f.getPath(),
					f.getName());
			sb.append(s);
		}
		return sb.toString();
	}
}
