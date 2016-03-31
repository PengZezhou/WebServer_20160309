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
 * 通用逻辑
 * 
 * @author Peng.Zezhou
 *
 */
public class Method {
	private static final Logger LOGGER = Logger.getLogger(Method.class);

	/**
	 * 获取配置文件类
	 * 
	 * @return Properties
	 */
	public static final Properties getProperties(String path) {
		Properties pro = new Properties();
		File file = new File(path);
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
			} else if (byte3[0] == 35 && byte3[1] == 32 && byte3[2] == 112) {
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
}