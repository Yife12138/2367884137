package raf;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 完成用户注册功能
 * 
 * 程序开始后要求输出：用户名，密码，昵称，年龄
 * 将该记录写道user.dat文件中，其中用户名，
 * 密码，昵称为字符串.年龄为int值
 * 
 * 每条记录占用100字节，其中：用户名，密码，昵称为字符串，各占32字节，
 * 年龄为int占用4字节
 * 
 * @author soft01
 * 
 *
 */
public class YongHuZhuCeDemo {
	public static void main(String[] args)  {
		
		
		System.out.println("开始注册");
		Scanner sca = new Scanner(System.in);
		
		System.out.println("输入用户名：");
		String username = sca.nextLine();
		
		System.out.println("输入密码：");
		String password = sca.nextLine();
		
		System.out.println("输入昵称：");
		String nickname = sca.nextLine();
		
		System.out.println("输入年龄：");
		int age = Integer.parseInt(sca.nextLine());
		
		System.out.println(username+","+password+","+nickname+","+age);
		
		RandomAccessFile raf = new RandomAccesisFile("user.txt", "rw");
		//将指针移动到文件末尾
		raf.seek(raf.length());//从上一个文件的最后一位开始，从哪里开始是int类型
		
		//用户名
		//1先将用户名转成对应的一组字节
		byte[] data = username.getBytes("UTF-8");
		//2将该数组扩容为32字节
		data = Arrays.copyOf(data, 32);
		//3将该字节数组一次性写入文件
	    raf.write(data);
		
	    //密码
	    data = password.getBytes("UTF-8");
	    data = Arrays.copyOf(data, 32);
	    raf.write(data);
	    
	    //昵称
	    data = nickname.getBytes("UTF-8");
	    data = Arrays.copyOf(data, 32);
	    raf.write(data);
	    
	    //年龄
	    raf.writeInt(age);
	    
	    System.out.println("注册完毕");
	    raf.close();
		
		
		
	}

}





















