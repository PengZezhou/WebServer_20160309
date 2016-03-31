package com.succez.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;

@SuppressWarnings("deprecation")
public class testMethod {



	@Test
	public void testCloseStream() {
		try {
			FileInputStream in = new FileInputStream("D:\\不同编码的TXT\\ANSI编码.txt");
			Method.closeStream(in);

			in = null;
			Method.closeStream(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFileEncode() {
		String path = "D:\\不同编码的TXT\\ANSI编码.txt";
		String code = Method.getFileEncode(new File(path));
		Assert.assertEquals("GBK", code);

		path = "D:\\不同编码的TXT\\utf8编码.txt";
		code = Method.getFileEncode(new File(path));
		Assert.assertEquals("UTF-8", code);

		path = "D:\\不同编码的TXT\\unicode编码.txt";
		code = Method.getFileEncode(new File(path));
		Assert.assertEquals("GBK", code);
	}

	@Test 
	public void testFile2buf() {
		String path = "D:\\不同编码的TXT\\ANSI编码.txt";
		File file = new File(path);
		byte[] bytes = Method.file2buf(file);
		Assert.assertNotNull(bytes);

		path = "D:\\MicrosoftVisualStudio2008Professional.ISO";
		file = new File(path);
		bytes = Method.file2buf(file);
		Assert.assertNull(bytes);

		file = new File("D:\\电子书\\java程序性能优化 .pdf");
		Assert.assertNotNull(Method.file2buf(file));

		file = null;
		bytes = Method.file2buf(file);
		Assert.assertNull(bytes);
	}
}
