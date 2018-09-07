package socket;
/**
 * 聊天室客户端
 * @author soft01
 *
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	/**
	 * javaj.net.Socket
	 * Socket封装了TCP协议的通讯细节，是该过程抽象
	 * 为通过两个流的读写完成与远端计算机的数据交换
	 * Socket的本地翻译为：套接字
	 */
	private Socket socket;
	/**
	 * 构造方法，初始化客户端
	 */
	public Client(){
		
		try {
			/**
			 * 实例化Socket的同时需要传入两个参数：
			 * 1、服务端的IP地址
			 * 2、服务端所使用的端口号
			 * 
			 * 通过IP地址可以找到服务器端的计算机
			 * 通过端口地址可以连接到运行程序
			 * 而我们客户端自身的IP和端口无需指定，系统会分配一个端口
			 * 并且连接后会发送给服务器端。
			 * 
			 * 实例化Socket的过程是发起连接的过程，若服务器没有相应则这里
			 * 会直接抛出异常
			 * 
			 * 127.0.0.1或localhost都是用于表示本机的IP地址
			 */
			System.out.println("正在连接服务端.......");
			socket = new Socket("176.53.9.9",8088);
			System.out.println("连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 启动程序
	 */
	public void start() {
		try {
			//启动一个线程用来读取服务端消息
			ServerHander hander = new ServerHander();
			Thread t = new Thread(hander);
			t.start();
			
			
			/*
			 * Scoket提供的方法：
			 * OutpututStream getOutputStream()
			 * 返回一个字节输出流，通过该输出流写出的数据最终会发送给服务端
			 */
			Scanner sca = new Scanner(System.in);
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);   
            PrintWriter pw = new PrintWriter(bw,true);  //第二个参数为true时调用flush()将缓冲区字节调出或者添加close()
           
            
            String line =null;
            while(true) {
            	System.out.println("输入：");
            	line = sca.nextLine();
            pw.println("丁鹏："+line);
           
            }
            
			
		} catch (Exception e) {
            e.printStackTrace();
            
		}
	}
	/**
	 * 程序入口
	 */
	public static void main(String[] args) {
		
		Client client = new Client();
		client.start();
	}
	/**
	 * 该线程负责循环结接收服务器端发送过来的消息并输出到客户端的控制台
	 * @author soft01
	 *
	 */
private class ServerHander implements Runnable{
	public void run() {
		try {

            //通过Socket获取输入流，读取服务端发送过来的信息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            
            String line = null;
            while((line = br.readLine())!=null) {
                System.out.println(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
 }
}




























