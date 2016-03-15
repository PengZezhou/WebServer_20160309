package com.succez.server.connector;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.succez.server.resolver.Resolver;

public class ThreadPoolTask implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ThreadPoolTask.class);
	private Socket socket;
	ThreadPoolTask(Socket socket){
		this.socket = socket;
	}
	public void run() {
		// TODO Auto-generated method stub
		LOGGER.info("线程执行中...");
		Resolver res = new Resolver(socket);
		res.urlResolve();
	}

}
