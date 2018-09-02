package socket;
/**
 * 聊天室服务端
 * @author soft01
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	/*
	 * 运行在服务器端ServerSocket
	 */
	private ServerSocket server;
	
	
	public Server() {
		/*
		 *运行在服务器端的ServerSocket有两个作用
		 *
		 *1、向系统申请服务端口，客户端就是通过这个
		 *端口与服务端程序建立连接 
		 *
		 *2、监听该端口，当客户端通过该端口与服务器端建立
		 *连接时会自动创建一个Socket。通过这个Socket与客户端进行数据交换
		 */
		try {
			/*
			 * 实例化ServerSocket的同时向系统申请服务器端口，该端口
			 * 不能与系统运行的其他应用程序相同，否则会抛出地址被占用
			 * 的异常
			 */
			System.out.println("正在启动服务器......");
			server = new ServerSocket(8088);
			System.out.println("启动成功！");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void start() {
		try {
			/*
			 * ServerSocket有一个重要的方法：
			 * Socket accept()
			 * 该方法是一个阻塞方法，调用后程序就在这里“卡住了”,这时开始监听服务器端口
			 * 等待客户端的连接。
			 * 那么当客户端通过端口尝试连接时，accept会返回一个Socket
			 * 通过该Socket就可以与刚建立连接的客户端进行交互
			 * 
			 */
			System.out.println("等待客户端连接......");
			Socket socket = server.accept();
			System.out.println("一个客户端已连接！");
			
			/*
			 * 通过Scoket获取输入流，读取客户端发送过来的数据
			 */
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine())!=null) {
			System.out.println("13132132312");
			System.out.println("客户端："+line);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}












