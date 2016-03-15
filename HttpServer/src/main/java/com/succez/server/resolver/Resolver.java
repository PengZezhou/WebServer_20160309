package com.succez.server.resolver;

import java.io.IOException;
import java.net.Socket;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resolver {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Resolver.class);
	private Socket socket;
	public Resolver(Socket socket){
		this.socket = socket;
	}
	
	public void urlResolve(){
		LOGGER.info("¿ªÊ¼½âÎöurl");
		DefaultBHttpServerConnection con = new DefaultBHttpServerConnection(1024);
		try {
			con.bind(socket);
			
			 HttpRequest req = con.receiveRequestHeader();
			 String url = req.getRequestLine().getUri();
			 System.out.println(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(con!=null){
				try {
					con.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		assignment();
	}
	
	private void assignment(){
		
	}
}
