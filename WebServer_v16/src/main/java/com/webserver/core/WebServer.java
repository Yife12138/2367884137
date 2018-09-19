package com.webserver.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer����
 * @author ta
 *
 */
public class WebServer {
	private ServerSocket server;
    private	ExecutorService threadPool;
	/**
	 * ���췽����������ʼ�������
	 */
	public WebServer() {
		try {
			System.out.println("�������������...");
			server = new ServerSocket(8088);
			threadPool = Executors.newFixedThreadPool(5);
			System.out.println("������������!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����˿�ʼ�����ķ���
	 */
	public void start() {
		try {
			while(true) {
				System.out.println("�ȴ��ͻ�������...");
				Socket socket = server.accept();
				System.out.println("һ���ͻ��������ˣ�");
				//����һ���̴߳���ÿͻ�������
				ClientHandler handler 
					= new ClientHandler(socket);
				threadPool.execute(handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
	}
}










