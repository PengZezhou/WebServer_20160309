package com.succez.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;

@SuppressWarnings("deprecation")
public class testMethod {

	@Test
	public void testGetIPValue() {
		String ip = Method.getIPValue();
		Assert.assertEquals(ip, "192.168.7.185");
	}

	@Test
	public void testGetPortValue() {
		int port = Method.getPortValue();
		Assert.assertEquals(8050, port);
	}

	@Test
	public void testGetMaxConValue() {
		int con = Method.getMaxConValue();
		Assert.assertEquals(100, con);
	}

	@Test
	public void testGetCorePoolSize() {
		int size = Method.getCorePoolSize();
		Assert.assertEquals(5, size);
	}

	@Test
	public void testGetMaxNumPoolSize() {
		int size = Method.getMaxNumPoolSize();
		Assert.assertEquals(20, size);
	}

	@Test
	public void testGetKeepAliveTime() {
		int time = Method.getKeepAliveTime();
		Assert.assertEquals(60, time);
	}

	@Test
	public void testCloseStream() {
		try {
			FileInputStream in = new FileInputStream("D:\\不同编码的TXT\\ANSI编码.txt");
			Method.closeStream(in);

			in = null;
			Method.closeStream(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
	public void testFileDownload() {
		String path = "D:\\不同编码的TXT\\ANSI编码.txt";
		Assert.assertEquals(false, Method.fileDownload(new File(path))
				.booleanValue());

		path = "D:\\设计书\\概要设计说明书模板.docx";
		Assert.assertEquals(true, Method.fileDownload(new File(path))
				.booleanValue());
	}

	@Test
	public void testFileHtmlRead() {
		String path = "D:\\不同编码的TXT\\ANSI编码.txt";
		Assert.assertEquals(false, Method.fileHtmlRead(new File(path))
				.booleanValue());

		path = "D:\\clonegithub\\WebServer_20160309\\HttpServer\\target\\classes\\error\\404.html";
		Assert.assertEquals(true, Method.fileHtmlRead(new File(path))
				.booleanValue());
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

		file = new File("D:\\电子书\\java多线程设计模式.pdf");
		Assert.assertNotNull(Method.file2buf(file));

		file = null;
		bytes = Method.file2buf(file);
		Assert.assertNull(bytes);
	}

	@Test
	public void testDirectoryToHtml() {
		String path = "D:\\不同编码的TXT";
		File file = new File(path);
		String str = Method.directoryToHtml(file);
		Assert.assertNotNull(str);
	}
}
