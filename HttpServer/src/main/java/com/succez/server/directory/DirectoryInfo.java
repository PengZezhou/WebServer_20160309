package com.succez.server.directory;

import java.io.File;

import com.succez.server.util.Constant;

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
		sb.append(Constant.HTML_HEAD);
		sb.append("<a href='/'>根目录</a><br/>");
		sb.append("<a href='javascript:history.go(-1)'>返回上级</a><br/>");
		File[] arr = file.listFiles();
		for (File f : arr) {
			String s = null;
			char c = f.getName().charAt(0);
			if (c == '.' || c == '~' || c == '$') {
				continue;
			} else if (f.isHidden()) {
				continue;
			} else if (f.isFile()) {
				s = String.format(
						"<a href='%s' target='_blank'>%s</a><br/>",
						f.getPath(), f.getName());
			} else {
				s = String.format("<a href='%s'>%s</a><br/>",
						f.getPath(), f.getName());
			}
			sb.append(s);
		}
		return sb.toString();
	}
}
