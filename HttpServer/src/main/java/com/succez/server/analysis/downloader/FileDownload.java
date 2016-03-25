package com.succez.server.analysis.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
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
	public FileDownload(PrintStream pstream, File file) {
		this.pstream = pstream;
		this.file = file;
		this.downloadFile();
	}

	private PrintStream pstream;
	private File file = null;

	/**
	 * 下载文件
	 * 
	 * @param file
	 */
	private void downloadFile() {
		LOGGER.info("开始下载文件");
		FileInputStream fis = null;
		FileChannel fileChannel = null;
		try {
			Response r = new Response();
			r.setContent_Type("application/octet-stream");
			r.setContent_Disposition(String.format("attachment"));
			this.pstream.println(r.toString());

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
					this.pstream.write(bytes);
				}
				bf.clear();
			}
		} catch (IOException e) {
			LOGGER.error("文件下载出现异常");
		} finally {
			Method.closeStream(fis);
			Method.closeStream(this.pstream);
			Method.closeStream(fileChannel);
		}
		LOGGER.info("文件下载结束");
	}
}
