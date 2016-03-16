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
			if (f.isHidden()) {
				continue;
			}
			String s = String.format("<a href='%s'>%s</a><br/>", f.getPath(),
					f.getName());
			sb.append(s);
		}
		if (sb.length() == 0) {
			sb.append("��Ŀ¼Ϊ��");
		}
		return sb.toString();
	}
}
