package com.succez.server.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.util.Constant;
import com.succez.server.util.Method;

public class FileDownload {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileDownload.class);

	private Socket socket;

	/**
	 * 构造函数
	 * 
	 * @param socket
	 */
	public FileDownload(Socket socket) {
		this.socket = socket;
	}

	/**
	 * 下载指定路径的文件
	 * 
	 * @param file
	 */
	public void downloadFile(File file) {
		LOGGER.info("开始执行下载");
		FileInputStream fis = null;
		OutputStream out = null;
		FileChannel fileChannel = null;
		try {
			out = socket.getOutputStream();
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
			LOGGER.error(" 文件下载，NIO出现异常");
		} finally {
			Method.closeStream(fis);
			Method.closeStream(out);
			Method.closeStream(fileChannel);
			Method.closeStream(socket);
		}
		LOGGER.info("开始下载结束");
	}
}
