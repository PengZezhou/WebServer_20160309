package com.succez.server.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Method;

/**
 * �ļ���ȡģ��
 * 
 * @author Peng.Zezhou
 *
 */
public class ReadFile {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadFile.class);

	/**
	 * ���ļ�����ת��Ϊ�ַ���
	 * 
	 * @param file
	 *            �ļ�·��
	 * @return �����ַ���
	 */
	public String convertFromFile(File file) {
		String str = null;
		LOGGER.info("��ȡ�ļ�");
		byte[] bytes = file2buf(file);
		str = new String(bytes);
		return str;
	}

	/**
	 * ���ļ�����ת����byte���鷵��,����ļ������ڡ���������ļ���С����2G�򷵻�null
	 * 
	 * <pre>
	 * byte[] b = file2buf(new File(&quot;D:\tmp.txt&quot;));
	 * </pre>
	 * 
	 * @param fobj
	 *            �ļ����� File(!null)
	 * @return byte����
	 * @throws IOException
	 *             �ļ�����������쳣
	 * 
	 */
	private byte[] file2buf(File fobj) {
		LOGGER.info("�ļ���ʼת��Ϊ�ֽ�����...");
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
			LOGGER.info("�ļ���ʼת��Ϊ�ֽ��������");
		} catch (IOException e) {
			LOGGER.error(" �ļ�ת�������쳣");
		} finally {
			Method.closeStream(fis);
		}
		return b;
	}
}
