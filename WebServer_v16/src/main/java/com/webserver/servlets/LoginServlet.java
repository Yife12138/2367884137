package com.webserver.servlets;

import java.io.File;
import java.io.RandomAccessFile;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * ��¼ҵ��
 * @author ta
 *
 */
public class LoginServlet extends HttpServlet {
	public void service(HttpRequest request,HttpResponse response) {
		//1 ��ȡ�û���¼��Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2��user.dat�ļ����ȶ��û���Ϣ
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","r");
		){
			//���أ�Ĭ�ϵ�¼ʧ��
			boolean check = false;
			for(int i=0;i<raf.length()/100;i++) {
				//�ƶ�ָ�뵽��ǰ��¼�Ŀ�ʼλ��
				raf.seek(i*100);
				//���û���
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				if(name.equals(username)) {
					//������
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					if(pwd.equals(password)) {
						//��¼�ɹ�
						check = true;
					}
					break;
				}
			}
			
			if(check) {
				//��¼�ɹ�
			
				forward("/myweb/login_success.html", request, response);
			}else {
				//��¼ʧ��
				forward("/myweb/login_fail.html", request, response);
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
}





