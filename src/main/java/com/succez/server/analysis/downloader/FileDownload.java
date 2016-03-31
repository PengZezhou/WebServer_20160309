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

			r.setContent_Type(Constant.HTML_CONTENT_TYPE1);
			r.setContent_Disposition(String.format(Constant.HTML_CONTENT_DIS));
			r.setContent_Range(this.responseRange());
			r.setContent_Length(this.file.length() - this.getBeginPosition());
			if (this.getBeginPosition() != 0) {
				r.setHttpVersion(Constant.HTML_206);
			}
			LOGGER.info("response报文" + r.toString());
			r.toStream(pstream);

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
		beginPosition = getBeginPosition();

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
			String[] strs = this.range.split(Constant.EQUAL);
			sb.append(strs[0]);
			sb.append(' ');
			String[] strs2 = strs[1].split(Constant.BREAK);
			if (strs2.length > 1) {
				sb.append(strs[1]);
			} else {
				sb.append(strs2[0]);
				sb.append(Constant.BREAK);
				sb.append(this.file.length() - 1);
			}
			sb.append(Constant.SLASH);
			sb.append(this.file.length());
			return sb.toString();
		}
	}

	/**
	 * 获取请求下载起始地址
	 * 
	 * @return
	 */
	private long getBeginPosition() {
		if (this.range == null) {
			return 0;
		} else {
			String[] strs2 = this.range.split(Constant.EQUAL)[1]
					.split(Constant.BREAK);
			return Long.parseLong(strs2[0]);
		}
	}
}
