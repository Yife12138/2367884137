package com.webserver.servlets;

import java.io.File;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

public abstract class HttpServlet {
	public abstract void service(HttpRequest request,HttpResponse response);
	/**
	 * ��ת��ָ��·��
	 * TOMCAT��ʵ�ʸ÷�������ת����������ͨ��request��ȡ
	 * @param path
	 * @param request
	 * @param response
	 */
	public void forward(String path,HttpRequest request,HttpResponse response) {
		response.setEntity(new File("webapps"+path));
		
	}

}
