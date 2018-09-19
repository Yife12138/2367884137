package com.webserver.core;
/**
 * ��������������Ϣ
 * @author soft01
 *
 */

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerContext {
	/*
	 * Servletӳ���ϵ
	 * key������·��
	 * value��Servlet������
	 */
	private static Map<String, String> servletMapping = new HashMap<String, String>();
	static {
		initServletMapping();
	}
	/**
	 * ��ʼ��Servletӳ��
	 */
	private static void initServletMapping() {
//		servletMapping.put("/myweb/reg", "com.webserver.servlets.RegServlet");
//		servletMapping.put("/myweb/login", "com.webserver.servlets.LoginServlet");
//		servletMapping.put("/myweb/update", "com.webserver.servlets.UpdateServlet");
		/*
		 * ����conf/servlets.xml�ļ���ʼ��
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("conf/servlets.xml"));
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			for (Element servletEle : list) {
				String url = servletEle.attributeValue("url");
				String className = servletEle.attributeValue("className");
				servletMapping.put(url, className);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String getServletName(String url) {
		return servletMapping.get(url);
	}
	public static void main(String[] args) {
		System.out.println(getServletName("/myweb/reg"));
	}

}
