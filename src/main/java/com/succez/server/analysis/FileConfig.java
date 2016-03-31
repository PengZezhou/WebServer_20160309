package com.succez.server.analysis;

import com.succez.server.util.ConfigInfo;
import com.succez.server.util.Constant;

public class FileConfig {
	private static FileConfig instance = null;

	/**
	 * 获取配置文件单例
	 * @return
	 */
	public static FileConfig getInstance() {
		if (instance == null) {
			return new FileConfig();
		}
		return instance;
	}

	private FileConfig() {
		ConfigInfo config = new ConfigInfo(Constant.FILE_CONFIG_INFO);
		this.error_404 = config.getString(Constant.ERROR_404);
		this.error_500 = config.getString(Constant.ERROR_500);
		this.not_support = config.getString(Constant.NOT_SUPPORT);
		this.download_extern = config.getString(Constant.EXTERN_DOWNLOAD);
		this.plain_extern = config.getString(Constant.EXTERN_PLAIN);
		this.html_extern = config.getString(Constant.EXTERN_HTML);
	}

	private String not_support;
	private String error_404;
	private String error_500;
	private String download_extern;
	private String plain_extern;
	private String html_extern;

	/**
	 * 获取404错误文件
	 * @return
	 */
	public String getError_404() {
		return error_404.substring(1, this.error_404.length() - 1);
	}

	/**
	 * 获取500错误文件
	 * @return
	 */
	public String getError_500() {
		return error_500.substring(1, this.error_500.length() - 1);
	}

	/**
	 * 获取被下载的指定文件扩展名
	 * @return
	 */
	public String getDownload_extern() {
		return download_extern.substring(1, this.download_extern.length() - 1);
	}

	/**
	 * 获取被当作文本展示的文件扩展名
	 * @return
	 */
	public String getPlain_extern() {
		return plain_extern.substring(1, this.plain_extern.length() - 1);
	}

	/**
	 * 获取当作html解析的文件扩展名
	 * @return
	 */
	public String getHtml_extern() {
		return html_extern.substring(1, this.html_extern.length() - 1);
	}

	/**
	 * 获取服务器不支持功能的提示页面地址
	 * @return
	 */
	public String getNot_support() {
		return not_support.substring(1, this.not_support.length() - 1);
	}
}
