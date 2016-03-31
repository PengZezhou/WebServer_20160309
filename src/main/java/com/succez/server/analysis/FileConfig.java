package com.succez.server.analysis;

import com.succez.server.util.ConfigInfo;
import com.succez.server.util.Constant;

public class FileConfig {
	private static FileConfig instance = null;

	public static FileConfig getInstance() {
		if (instance == null) {
			return new FileConfig();
		}
		return instance;
	}

	private FileConfig() {
		ConfigInfo config = new ConfigInfo(Constant.FILE_CONFIG_INFO);
		this.error_404 = config.getString("error-404");
		this.error_500 = config.getString("error-500");
		this.not_support = config.getString("not-support");
		this.download_extern = config.getString("download-extern-name");
		this.plain_extern = config.getString("palin-extern-name");
		this.html_extern = config.getString("html-extern-name");
	}

	private String not_support;
	private String error_404;
	private String error_500;
	private String download_extern;
	private String plain_extern;
	private String html_extern;

	public String getError_404() {
		return error_404.substring(1, this.error_404.length() - 1);
	}

	public String getError_500() {
		return error_500.substring(1, this.error_500.length() - 1);
	}

	public String getDownload_extern() {
		return download_extern.substring(1, this.download_extern.length() - 1);
	}

	public String getPlain_extern() {
		return plain_extern.substring(1, this.plain_extern.length() - 1);
	}

	public String getHtml_extern() {
		return html_extern.substring(1, this.html_extern.length() - 1);
	}

	public String getNot_support() {
		return not_support.substring(1, this.not_support.length() - 1);
	}
}
