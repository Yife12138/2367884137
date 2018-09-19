package com.webserver.servlets;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * �޸�����ҵ��
 * @author ta
 *
 */
public class UpdateServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response) {
		/*
		 * 1:��ȡ�û���Ϣ
		 */
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		
		/*
		 * 2:�޸�
		 */
		try(
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");
		){
			//ƥ���û�
			boolean check = false;
			for(int i=0;i<raf.length()/100;i++) {
				raf.seek(i*100);
				//��ȡ�û���
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				if(name.equals(username)) {
					check = true;
					//�ҵ����û���ƥ������
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					System.out.println("pwd: "+pwd);
					if(pwd.equals(password)) {
						//ƥ���Ϻ��޸�����
						//1�Ƚ�ָ���ƶ�������λ��
						raf.seek(i*100+32);
						//2������������д��
						data = newPassword.getBytes("UTF-8");
						data = Arrays.copyOf(data, 32);
						raf.write(data);
						//3��Ӧ�޸����ҳ��
						forward("/myweb/update_success.html", request, response);
					}else {
						//ԭ������������
						forward("/myweb/update_fail.html", request, response);
						
					}
					break;
				}
			}
			if(!check) {
				//û�д���
				forward("/myweb/no_user.html", request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}



