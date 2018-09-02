package raf;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 显示所有注册用户信息
 * @author soft01
 *
 */

public class DuQuYongHu {

	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("user.txt", "r");
		/*
		 * 循环读取若干100字节
		 */
		for(int i = 0;i<raf.length()/100;i++) {
			//读用户名
			//先读取32字节
			byte[] data = new byte[32];
			raf.read(data);
			//将字节数组转换为字符串
			String username = new String(data,"UTF-8").trim();
			
			//读取密码
			raf.read(data);
			String password = new String(data,"UTF-8").trim();
			
			//读昵称
			raf.read(data);
			String nickname = new String(data,"UTF-8").trim();
			
			//读年龄
			int age = raf.readInt();
			System.out.println(username+","+password+","+nickname+","+age);
		}
	}
}
