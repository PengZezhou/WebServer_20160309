package com.succez.server.analysis.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.http.Response;
import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * �ļ�����ģ��
 * 
 * @author Peng.Zezhou
 *
 */
public class FileDownload {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileDownload.class);

	/**
	 * ���캯��
	 * 
	 * @param socket
	 */
	public FileDownload(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
		this.downloadFile();
	}

	private Socket socket;
	private File file = null;

	/**
	 * ����ָ��·�����ļ�
	 * 
	 * @param file
	 */
	public void downloadFile() {
		LOGGER.info("��ʼִ������");
		FileInputStream fis = null;
		OutputStream out = null;
		FileChannel fileChannel = null;
		try {
			out = socket.getOutputStream();
			Response r = new Response();
			r.setContent_Disposition(String.format("attachment"));
			out.write(r.toString().getBytes());
			fis = new FileInputStream(file);
			fileChannel = fis.getChannel();
			ByteBuffer bf = ByteBuffer.allocate(Constant.BYTE_BUFFER_COPACITY);
			byte[] bytes = new byte[Constant.BUFFER_SIZE];
			int nRead, nGet;
			while ((nRead = fileChannel.read(bf)) != -1) {
				if (nRead == 0) {
					continue;
				}
				bf.flip();
				while (bf.hasRemaining()) {
					nGet = Math.min(bf.remaining(), Constant.BUFFER_SIZE);
					bf.get(bytes, 0, nGet);
					out.write(bytes);
				}
				bf.clear();
			}
		} catch (IOException e) {
			LOGGER.error(" �ļ����أ�NIO�����쳣");
		} finally {
			Method.closeStream(fis);
			Method.closeStream(out);
			Method.closeStream(fileChannel);
			Method.closeStream(socket);
		}
		LOGGER.info("��ʼ���ؽ���");
	}
}