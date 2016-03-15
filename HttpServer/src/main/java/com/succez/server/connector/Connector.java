package com.succez.server.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.util.Constant;

public class Connector {
	private static final Logger LOGGER = Logger.getLogger(Connector.class);
	public void requestMonitor(){
		LOGGER.info("");
		ServerSocket sv = Constant.SERVER_SOCKET;
		while(!Constant.SHUTDOWN){
			Socket socket = null;
			try {
				socket = sv.accept();

		        socket.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void connectToEnd(){
		// 多线程处理
	}
	public void resourceCleaner(){
		
	}
}
