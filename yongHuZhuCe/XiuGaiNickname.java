package raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 修改昵称
 * 程序启动后，要求用户输入要修改的昵称的用户名以及新的昵称
 * 然后将该用户昵称进行修改操作，若输入的用户名不存在，则提示
 * “无此用户”
 * @author soft01
 *
 */
public class XiuGaiNickname {

	public static void main(String[] args) throws IOException {
		Scanner sca = new Scanner(System.in);
		System.out.println("输入向查找的用户名：");
		String username = sca.nextLine();
		System.out.println("输入新的昵称：");
		String nickname = sca.nextLine();
		/*
		 * 获取用户输入的用户名及新昵称
		 * 使用RAF打开user.dat文件
		 * 循环读取每条记录
		 *    将指针移动到该条记录用户名的位置（i*100）
		 *    读取32字节，将用户名读取出来
		 *    判断该用户是否为用户输入的用户
		 *    若是此人，则移动指针到该条记录昵称位置，将新昵称以32字节写入该位置，覆盖原昵称完成修改并停止循环
		 *    若不是此人则进入下次循环
		 */
		RandomAccessFile raf = new RandomAccessFile("user.txt", "rw");
		boolean update = false;
		for(int i = 0;i<raf.length()/100;i++) {
			raf.seek(i*100);
			byte[] data = new byte[32];
			raf.read(data);
			String name = new String(data,"UTF-8").trim();//读取用户
			if(name.equals(username)) {
				raf.seek(i*100+64);
				data = nickname.getBytes("UTF-8");
				data = Arrays.copyOf(data, 32);
				raf.write(data);
				update = true;
				System.out.println("over!");
				break;
			}
	
		}if(!update) {
			System.out.println("查无此人！");
		}raf.close();
	}
}
