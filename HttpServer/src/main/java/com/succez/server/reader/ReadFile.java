package com.succez.server.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
		String str = null;
		LOGGER.info("读取文件");
		byte[] bytes = file2buf(file);
		str = new String(bytes);
		return str;
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
