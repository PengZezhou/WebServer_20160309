package com.succez.server.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.succez.server.http.Response;

/**
 * 通用逻辑
 * 
 * @author Peng.Zezhou
 *
 */
public class Method {
	private static final Logger LOGGER = Logger.getLogger(Method.class);
	private static final Properties pro = getProperties();

	/**
	 * 获取配置文件类
	 * 
	 * @return Properties
	 */
	private static final Properties getProperties() {
		Properties pro = new Properties();
		File file = new File(Constant.SERVER_CONFIG_INFO);
		InputStream stream = null;
		if (!file.exists()) {
			LOGGER.error("配置文件已被移动");
			return null;
		}
		try {
			stream = new FileInputStream(file);
			pro.load(stream);
		} catch (FileNotFoundException e) {
			LOGGER.error("配置文件读取异常，文件不存在");
		} catch (IOException e) {
			LOGGER.error("配置文件读取异常，IO异常");
		} finally {
			Method.closeStream(stream);
		}
		return pro;
	}

	/**
	 * 读取配置文件ip地址ַ
	 * 
	 * @return ip地址默认返回，本机地址"127.0.0.1"
	 */
	public static final String getIPValue() {
		return pro.getProperty("ip", "127.0.0.1");
	}

	/**
	 * 读取配置文件端口
	 * 
	 * @return 默认端口地址为80
	 */
	public static final int getPortValue() {
		int result = 80;
		try {
			result = Integer.parseInt(pro.getProperty("port", "80"));
		} catch (NumberFormatException e) {
			LOGGER.warn("配置文件端口地址异常");
		}
		if (result < 1 || result > 65535) {
			LOGGER.warn("配置文件端口地址不在范围内");
			result = 80;
		}
		return result;
	}

	/**
	 * 读取允许最大连接数
	 * 
	 * @return 默认返回最大连接数50
	 */
	public static final int getMaxConValue() {
		int result = 50;
		try {
			result = Integer.parseInt(pro.getProperty("max-connection", "50"));
		} catch (NumberFormatException e) {
			LOGGER.warn("配置文件，最大连接数异常");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("最大连接数不在范围内");
			result = 5;
		}
		return result;
	}

	/**
	 * 配置文件读取最小线程池大小
	 * 
	 * @return 默认最小为5
	 */
	public static final int getCorePoolSize() {
		int result = 5;
		try {
			result = Integer.parseInt(pro.getProperty("corePoolSize", "5"));
		} catch (NumberFormatException e) {
			LOGGER.warn("配置文件最小线程池大小异常");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("最小线程池大小不在范围内");
			result = 5;
		}
		return result;
	}

	/**
	 * 读取配置文件最大线程数
	 * 
	 * @return 线程数
	 */
	public static final int getMaxNumPoolSize() {
		int result = 20;
		try {
			result = Integer.parseInt(pro.getProperty("maximumPoolSize", "20"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最大线程数量错误");
		}
		if (result < 1 || result > 10000) {
			LOGGER.warn("线程数设置不在范围内");
			result = 20;
		}
		return result;
	}

	/**
	 * 读取配置文件 KeepAliveTime
	 * 
	 * @return KeepAliveTime值
	 */
	public static final int getKeepAliveTime() {
		int result = 30;
		try {
			result = Integer.parseInt(pro.getProperty("keepAliveTime", "60"));
		} catch (NumberFormatException e) {
			LOGGER.warn("保活时间转换错误");
		}
		if (result < 20 || result > 6000) {
			LOGGER.warn("保活时间不在阈值内");
			result = 30;
		}
		return result;
	}

	/**
	 * 流关闭封装
	 * 
	 * @param closeavle
	 *            可关闭对象
	 */
	public static final void closeStream(Closeable closeavle) {
		if (closeavle != null) {
			try {
				closeavle.close();
			} catch (IOException e) {
				LOGGER.error(closeavle.toString() + "关闭出现异常");
			}
		}
	}

	/**
	 * 获取文件编码格式
	 * 
	 * @param fileName
	 *            待获取的文件
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public static String getFileEncode(File file) {
		InputStream in = null;
		byte[] byte3 = new byte[3];
		String code = "GBK";
		try {
			in = new FileInputStream(file);
			int n = in.read(byte3);
			if (n == 0 || n == -1) {
				return code;
			}
			if (byte3[0] == -17 && byte3[1] == -69 && byte3[2] == -65) {
				code = "UTF-8";
			} else {
				code = "GBK";
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("获取文件编码失败，文件未找到");
		} catch (IOException e) {
			LOGGER.error("获取文件编码失败，IO异常");
		} finally {
			Method.closeStream(in);
		}

		return code;
	}

	/**
	 * 判断文件是否是可下载类型
	 * 
	 * @param file
	 *            待判断文件
	 * @return
	 */
	public static Boolean fileDownload(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = Constant.DOWNLOAD_EXTEN_NAME.split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("文件将被下载处理");
				return true;
			}
		}
		return false;
	}

	/**
	 * 读取html文件
	 * 
	 * @param file
	 * @return
	 */
	public static Boolean fileHtmlRead(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = Constant.HTML_EXTEN_NAME.split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("解析html文件");
				return true;
			}
		}
		return false;
	}

	/**
	 * 将文件转化为字节数组
	 * 
	 * <pre>
	 * byte[] b = file2buf(new File(&quot;D:\tmp.txt&quot;));
	 * </pre>
	 * 
	 * @param fobj
	 *            文件
	 * @return 字节数组
	 * @throws IOException
	 *             IO异常
	 * 
	 */
	public static byte[] file2buf(File fobj) {
		if (fobj == null || fobj.isDirectory() || !fobj.exists()
				|| fobj.length() > (1024 * 1024 * 1024 * 2 - 1)) {
			LOGGER.error("文件为null，文件是文件夹，文件不存在，或者文件大小超过2G");
			return null;
		}
		LOGGER.info("开始字节转换...");
		FileInputStream fis = null;
		byte[] b = null;
		try {
			fis = new FileInputStream(fobj);
			int length = (int) fobj.length();
			b = new byte[length];
			int n = 0;
			int off = 0;
			int len = length < 4096 ? length : 4096;
			while ((n = fis.read(b, off, len)) != -1) {
				if (0 == n) {
					break;
				}
				off += n;
				len = len > length - off ? length - off : len;
			}
			LOGGER.info("字节转换结束");
		} catch (IOException e) {
			LOGGER.error("字节转换出现异常");
		} finally {
			Method.closeStream(fis);
		}
		return b;
	}

	/**
	 * 将目录信息转化为html格式
	 * 
	 * @param file
	 *            目录
	 * @return
	 */
	public static String directoryToHtml(File file) {
		StringBuilder sb = new StringBuilder();
		Response r = new Response();
		r.setContent_Type("text/html");
		sb.append(r.toString());
		sb.append(Constant.HTML_HEAD);
		sb.append("<a href='/'>.</a><br/>");
		sb.append("<a href='javascript:history.go(-1)'>..</a><br/>");
		File[] arr = file.listFiles();
		for (File f : arr) {
			String s = null;
			char c = f.getName().charAt(0);
			if (c == '.' || c == '~' || c == '$') {
				continue;
			} else if (f.isHidden()) {
				continue;
			} else if (f.isFile()) {
				s = String.format("<a href='%s' target='_blank'>%s</a><br/>", f
						.getPath().replace('\\', '/'), f.getName());
			} else {
				s = String.format("<a href='%s'>%s</a><br/>", f.getPath()
						.replace('\\', '/'), f.getName());
			}
			sb.append(s);
		}
		return sb.toString();
	}
}