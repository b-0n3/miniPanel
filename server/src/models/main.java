package models;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.istack.internal.logging.Logger;
import com.sun.net.httpserver.HttpServer;

public class main {
	static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
	public static void main(String[] args) {
		try {
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
		server.createContext("/test", new  MyHttpHandler());
		
		server.setExecutor(threadPoolExecutor);
		
		server.start();
		
		Logger.info(" Server started on port 8001");
		}catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		}

}
