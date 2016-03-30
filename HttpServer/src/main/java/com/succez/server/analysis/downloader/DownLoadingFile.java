package com.succez.server.analysis.downloader;

import java.io.File;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * 保存下载中断的文件信息，提供相关信息使用接口
 * 
 * @author Peng.Zezhou
 *
 */
public class DownLoadingFile {
	public static List<DownLoadingFile> fileList = new LinkedList<DownLoadingFile>();

	/**
	 * 检测待下载文件是否是在中断记录中
	 * 
	 * @param socket
	 *            客户端连接
	 * @param file
	 *            待下载文件
	 * @return 如果在中断记录，则返回已传输长度，否则返回{@code 0}
	 */
	public static boolean isExist(Socket socket, File file, long index) {
		DownLoadingFile df = new DownLoadingFile(socket.getInetAddress()
				.getHostAddress(), file.getPath(), 0);
		for (DownLoadingFile tmp : fileList) {
			if (tmp.host.equals(df.host) && tmp.path.equals(df.path)
					&& tmp.length == index) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从中断记录中删除该条记录
	 * 
	 * @param socket
	 *            客户端连接
	 * @param file
	 *            待下载文件
	 */
	public static void remove(Socket socket, File file, long index) {
		DownLoadingFile df = new DownLoadingFile(socket.getInetAddress()
				.getHostAddress(), file.getPath(), index);
		for (DownLoadingFile tmp : fileList) {
			if (tmp.host.equals(df.host) && tmp.path.equals(df.path)
					&& tmp.length == df.length) {
				fileList.remove(tmp);
				break;
			}
		}
	}

	/**
	 * 将当前未下载完成文件状态添加到中断记录
	 * 
	 * @param socket
	 *            客户端连接
	 * @param file
	 *            待下载文件
	 * @param length
	 *            已下载长度
	 */
	public static void add(Socket socket, File file, long length) {
		DownLoadingFile df = new DownLoadingFile(socket.getInetAddress()
				.getHostAddress(), file.getPath(), length);
		fileList.add(df);
	}

	/**
	 * 构造函数
	 * 
	 * @param host
	 *            客户端连接主机ip
	 * @param port
	 *            客户端连接主机端口号
	 * @param path
	 *            文件路径
	 * @param length
	 *            文件已下载长度
	 */
	private DownLoadingFile(String host, String path, long length) {
		this.host = host;
		this.path = path;
		this.length = length;

	}

	private String host;
	private String path;
	private long length;
}
