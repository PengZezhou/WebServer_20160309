package com.succez.server.directory;

import java.io.File;

public class DirectoryInfo {

	/**
	 * �г���ǰ�ļ����µ��б���Ϣ
	 * 
	 * @param file
	 *            ��ǰ�ļ���
	 * @return ��ǰ�ļ����µ��б���Ϣ
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
	 * Ϊ�ַ�����װhtml��ǩ
	 * 
	 * @param str
	 *            �ַ���
	 * @return ��html��ǩ���ַ���
	 */
	private String convertToHtml(String str) {
		String s = String.format("<a href='%s'>%s</a><br/>", str, str);
		return s;
	}
}
