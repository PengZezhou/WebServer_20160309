package com.succez.server.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 程序公有逻辑与类加载前处理逻辑
 * 
 * @author Peng.Zezhou
 *
 */
public class Method {
	private static final Logger LOGGER = Logger.getLogger(Method.class);
	private static final Properties pro = getProperties();

	/**
	 * 获取配置文件读取对象
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
			LOGGER.error("配置文件无法在制定位置找到");
		} catch (IOException e) {
			LOGGER.error("配置文件读取，流加载异常");
		} finally {
			Method.closeStream(stream);
		}
		return pro;
	}

	/**
	 * 获取配置文件ip地址
	 * 
	 * @return ip地址，读取失败默认返回‘127.0.0.1’
	 */
	public static final String getIPValue() {
		return pro.getProperty("ip", "127.0.0.1");
	}

	/**
	 * 获取配置文件端口
	 * 
	 * @return 端口号，读取失败默认返回‘80’
	 */
	public static final int getPortValue() {
		int result = 80;
		try {
			result = Integer.parseInt(pro.getProperty("port", "80"));
		} catch (NumberFormatException e) {
			LOGGER.warn("端口号转换为整数异常，为避免系统退出，返回默认值‘80’");
		}
		if (result < 1 || result > 65535) {
			LOGGER.warn("端口号不在阈值间，系统使用默认值");
			result = 80;
		}
		return result;
	}

	/**
	 * 获取配置文件最大浏览器连接数
	 * 
	 * @return 连接数最大值，读取失败默认返回‘50’
	 */
	public static final int getMaxConValue() {
		int result = 50;
		try {
			result = Integer.parseInt(pro.getProperty("max-connection", "50"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最大连接数转换为整数异常，为避免系统退出，返回默认值‘50’");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("浏览器最大连接数不在阈值间，系统使用默认值");
			result = 5;
		}
		return result;
	}

	/**
	 * 获取配置文件线程池维护线程的最少数量
	 * 
	 * @return 线程池维护线程的最少数量
	 */
	public static final int getCorePoolSize() {
		int result = 5;
		try {
			result = Integer.parseInt(pro.getProperty("corePoolSize", "5"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最小线程数转换为整数异常，为避免系统退出，返回默认值‘5’");
		}
		if (result < 1 || result > 1000) {
			LOGGER.warn("线程池配置警告，CorePoolSize不在阈值间，系统使用默认值");
			result = 5;
		}
		return result;
	}

	/**
	 * 获取配置文件线程池维护线程的最大数量
	 * 
	 * @return 线程池维护线程的最大数量
	 */
	public static final int getMaxNumPoolSize() {
		int result = 20;
		try {
			result = Integer.parseInt(pro.getProperty("maximumPoolSize", "20"));
		} catch (NumberFormatException e) {
			LOGGER.warn("最大线程数转换为整数异常，为避免系统退出，返回默认值‘20’");
		}
		if (result < 1 || result > 10000) {
			LOGGER.warn("线程池配置警告，CorePoolSize不在阈值间，系统使用默认值");
			result = 20;
		}
		return result;
	}

	/**
	 * 获取配置文件线程池维护线程所允许的空闲时间 (秒)
	 * 
	 * @return 线程池维护线程所允许的空闲时间
	 */
	public static final int getKeepAliveTime() {
		int result = 30;
		try {
			result = Integer.parseInt(pro.getProperty("keepAliveTime", "60"));
		} catch (NumberFormatException e) {
			LOGGER.warn("线程允许空闲时间转换为整数异常，为避免系统退出，返回默认值‘30’");
		}
		if (result < 20 || result > 6000) {
			LOGGER.warn("线程池配置警告，CorePoolSize不在阈值间，系统使用默认值");
			result = 30;
		}
		return result;
	}

	/**
	 * 关闭流的逻辑封装
	 * 
	 * @param closeavle
	 *            流对象
	 */
	public static final void closeStream(Closeable closeavle) {
		if (closeavle != null) {
			try {
				closeavle.close();
			} catch (IOException e) {
				LOGGER.error(closeavle.toString() + " 流关闭出现异常");
			}
		}
	}

	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 *            :file
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
			LOGGER.error("判断文件编码，文件未找到");
		} catch (IOException e) {
			LOGGER.error("判断文件编码，文件读取异常");
		} finally {
			Method.closeStream(in);
		}

		return code;
	}

	/**
	 * 返回file是否是给定限制类的文件类型，是返回true，否则返回false
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public static Boolean fileType(File file) {
		String name = file.getName();
		int dot = name.lastIndexOf('.');
		if (dot == -1) {
			return false;
		}
		String ext = name.substring(dot, name.length()).toLowerCase();
		String[] arr = Constant.EXTEN_NAME.split("\\|");
		for (String str : arr) {
			if (ext.equals(str)) {
				LOGGER.info("文件将被下载处理");
				return true;
			}
		}
		return false;
	}

	/**
	 * 将文件内容转换成byte数组返回,如果文件不存在、读入错误、文件大小超过2G则返回null
	 * 
	 * <pre>
	 * byte[] b = file2buf(new File(&quot;D:\tmp.txt&quot;));
	 * </pre>
	 * 
	 * @param fobj
	 *            文件对象 File(!null)
	 * @return byte数组
	 * @throws IOException
	 *             文件输入输出流异常
	 * 
	 */
	public static byte[] file2buf(File fobj) {
		LOGGER.info("文件开始转换为字节数组...");
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
			LOGGER.info("文件开始转换为字节数组结束");
		} catch (IOException e) {
			LOGGER.error(" 文件转化出现异常");
		} finally {
			Method.closeStream(fis);
		}
		return b;
	}
}