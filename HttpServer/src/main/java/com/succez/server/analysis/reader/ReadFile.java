package com.succez.server.analysis.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Method;

/**
 * 文件读取模块
 * 
 * @author Peng.Zezhou
 *
 */
public class ReadFile {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadFile.class);

	/**
	 * 将文件内容转化为字符串
	 * 
	 * @param file
	 *            文件路径
	 * @return 内容字符串
	 */
	public String convertFromFile(File file) {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 200 OK\r\n");
		sb.append("Connection: Keep-Alive\r\n");
		int index = file.getName().lastIndexOf(".");
		if (index != -1) {
			String prefix = file.getName().substring(index);
			if (prefix.equals(".html") || prefix.equals(".htm")) {
				sb.append("Content-type:text/html\r\n\r\n");
			} else {
				sb.append("Content-type:text/plain\r\n\r\n");
			}
		} else {
			sb.append("Content-type:text/plain\r\n\r\n");
		}
		String str = null;
		LOGGER.info("读取文件");
		byte[] bytes = file2buf(file);
		try {
			str = new String(bytes, Method.getFileEncode(file));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("编码转换，不支持的编码格式");
		}
		sb.append(str);
		return sb.toString();
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
	private byte[] file2buf(File fobj) {
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
