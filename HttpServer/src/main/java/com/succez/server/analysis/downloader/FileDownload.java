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
	public FileDownload(Socket socket, File file, String range) {
		this.socket = socket;
		this.file = file;
		this.range = range;
		this.downloadFile();
	}

	private Socket socket;
	private File file = null;
	private String range;

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
		try {
			pstream = new PrintStream(this.socket.getOutputStream(), true);
			fis = new FileInputStream(file);
			fileChannel = fis.getChannel();
			ByteBuffer bf = ByteBuffer.allocate(Constant.BYTE_BUFFER_COPACITY);
			byte[] bytes = new byte[Constant.BUFFER_SIZE];
			Response r = new Response();

			r.setContent_Type("application/octet-stream");
			r.setContent_Disposition(String.format("attachment"));
			r.setContent_Range(this.responseRange());
			LOGGER.info("response报文" + r.toString());
			pstream.println(r.toString());

			this.download(pstream, fileChannel, bf, bytes);
		} catch (IOException e) {
			LOGGER.error("文件下载出现异常");
		} finally {
			Method.closeStream(fis);
			Method.closeStream(pstream);
			Method.closeStream(fileChannel);
		}
		LOGGER.info("文件下载结束");
	}

	/**
	 * 下载文件内容
	 * 
	 * @param pstream
	 * @param fileChannel
	 * @param bf
	 * @param bytes
	 * @throws IOException
	 */
	private void download(PrintStream pstream, FileChannel fileChannel,
			ByteBuffer bf, byte[] bytes) throws IOException {
		long beginPosition;
		int nRead;
		int nGet;
		beginPosition = DownLoadingFile.isExist(this.socket, this.file);
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
	}

	/**
	 * 解析request的Range部分，返回response的content-range部分
	 * 
	 * @return
	 */
	private String responseRange() {
		if (this.range == null) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			String[] strs = this.range.split("=");
			sb.append(strs[0]);
			sb.append(' ');
			String[] strs2 = strs[1].split("-");
			if (strs2.length > 1) {
				sb.append(strs[1]);
			} else {
				sb.append(strs2[0]);
				sb.append('-');
				sb.append(this.file.length() - 1);
			}
			sb.append('/');
			sb.append(this.file.length());
			return sb.toString();
		}
	}
}
