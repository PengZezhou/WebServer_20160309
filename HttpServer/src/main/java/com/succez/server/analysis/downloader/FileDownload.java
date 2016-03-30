package com.succez.server.analysis.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.http.Response;
import com.succez.server.util.Constant;
import com.succez.server.util.Method;

/**
 * 文件下载模块
 * 
 * @author Peng.Zezhou
 *
 */
public class FileDownload {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileDownload.class);

	/**
	 * 构造函数
	 * 
	 * @param pstream
	 */
	public FileDownload(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
		this.downloadFile();
	}

	private Socket socket;
	private File file = null;

	/**
	 * 下载文件
	 * 
	 * @param file
	 */
	private void downloadFile() {
		LOGGER.info("开始下载文件");
		PrintStream pstream = null;
		FileInputStream fis = null;
		FileChannel fileChannel = null;
		long beginPosition = 0;
		try {
			Response r = new Response();
			pstream = new PrintStream(this.socket.getOutputStream(), true);
			r.setContent_Type("application/octet-stream");
			r.setContent_Disposition(String.format("attachment"));
			pstream.println(r.toString());

			fis = new FileInputStream(file);
			fileChannel = fis.getChannel();
			ByteBuffer bf = ByteBuffer.allocate(Constant.BYTE_BUFFER_COPACITY);
			byte[] bytes = new byte[Constant.BUFFER_SIZE];
			int nRead, nGet;

			beginPosition = DownLoadingFile
					.isExist(this.socket, this.file);
			LOGGER.info("文件下载起始位置 " + beginPosition);
			while ((nRead = fileChannel.read(bf, beginPosition)) != -1) {
				if (nRead == 0) {
					continue;
				}
				bf.flip();
				while (bf.hasRemaining()) {
					nGet = Math.min(bf.remaining(), Constant.BUFFER_SIZE);
					bf.get(bytes, 0, nGet);
					pstream.write(bytes);
					beginPosition += nGet;
				}
				bf.clear();
			}

			if (beginPosition < this.file.length()) {
				DownLoadingFile.add(this.socket, this.file, beginPosition);
				LOGGER.info("添加中断记录，文件下载中断位置 " + beginPosition);
			} else {
				DownLoadingFile.remove(this.socket, this.file);
				LOGGER.info("文件下载完成，记录列表中移除 ");
			}
		} catch (IOException e) {
			LOGGER.error("文件下载出现异常");
		} finally {
			Method.closeStream(fis);
			Method.closeStream(pstream);
			Method.closeStream(fileChannel);
		}
		LOGGER.info("文件下载结束");
	}
}
