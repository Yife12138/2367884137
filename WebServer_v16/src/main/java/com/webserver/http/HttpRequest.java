package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.webserver.core.EmptyRequestException;

/**
 * �������
 * ÿ��ʵ����ʾ�ͻ��˷��͹�����һ����������
 * @author ta
 *
 */
public class HttpRequest {
	/*
	 * �����������Ϣ����
	 */
	//����ʽ
	private String method;
	//��Դ·��
	private String url;
	//Э��汾
	private String protocol;
	
	//url�е����󲿷�
	private String requestURI;
	//url�еĲ�������
	private String queryString;
	//ÿ������
	private Map<String,String> parameters = new HashMap<String,String>();
	
	
	/*
	 * ��Ϣͷ�����Ϣ����
	 */
	private Map<String,String> headers = new HashMap<String,String>();
		
	
	
	/*
	 * ��Ϣ���������Ϣ����
	 */
	
	
	//�ͻ������������Ϣ
	private Socket socket;
	private InputStream in;//inputStream��һ���൱��ɾһ��,�����ظ���ȡ
	
	/**
	 * ��ʼ������
	 * @throws EmptyRequestException 
	 */
	public HttpRequest(Socket socket) throws EmptyRequestException {
		try {
			this.socket = socket;
			this.in = socket.getInputStream();
			/*
			 * ��������
			 * 1:����������
			 * 2:������Ϣͷ
			 * 3:������Ϣ����
			 */
			parseRequestLine();
			parseHeaders();
			parseContent();
			
		} catch(EmptyRequestException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����������
	 * @throws EmptyRequestException 
	 */
	private void parseRequestLine() throws EmptyRequestException {
		System.out.println("��ʼ����������...");	
		try {
			String line = readLine();
			System.out.println("������:"+line);
			/*
			 * �������н��в�֣���ÿ��������
			 * ��Ӧ�����õ������ϡ�
			 */
			String[] data = line.split("\\s");
			if(data.length!=3) {
				//������
				throw new EmptyRequestException();
			}
			method = data[0];
			url = data[1];
			//��һ������URL
			parseURL();
			protocol = data[2];
			
			System.out.println("method:"+method);
			System.out.println("url:"+url);
			System.out.println("protocol:"+protocol);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("�����н������!");
	}
	/**
	 * ��һ������URL
	 * url�п��ܻ������ָ�ʽ:�������Ͳ�������
	 * 1,����������:
	 * /myweb/index.html
	 * 
	 * 2,��������:
	 * /myweb/reg?username=zhangsan&password=123456&nickname=asan&age=22
	 */
	private void parseURL() {
		/*
		 * �����жϵ�ǰurl�Ƿ��в���,�жϵ�
		 * �����ǿ�url�Ƿ���"?",��������Ϊ
		 * ���url�ǰ��������ģ�����ֱ�ӽ�url
		 * ��ֵ��requestURI���ɡ�
		 * 
		 * 
		 * ���в���:
		 * 1:��url����"?"���Ϊ�����֣���һ����
		 *   Ϊ���󲿷֣���ֵ��requestURI
		 *   �ڶ�����Ϊ�������֣���ֵ��queryString
		 *   
		 * 2:�ٶ�queryString��һ����֣��Ȱ���"&"
		 *   ��ֳ�ÿ���������ٽ�ÿ����������"="
		 *   ���Ϊ�����������ֵ��������parameters
		 *   ���Map�С�  
		 * 
		 * ����������Ҫע��url�ļ����ر����:
		 * 1:url���ܺ���"?"����û�в�������
		 * ��:
		 * /myweb/reg?
		 * 
		 * 2:���������п���ֻ�в�����û�в���ֵ
		 * ��:
		 * /myweb/reg?username=&password=123&age=16...
		 */
		if(url.indexOf("?")!=-1) {
			//����"?"���
			String[] data = url.split("\\?");
			requestURI = data[0];
			//�ж�?�����Ƿ��в���
			if(data.length>1) {
				queryString = data[1];
				//��һ��������������
				parseParameter(queryString);
				
			}
		}else {
			//������?
			requestURI = url;
		}
		
		
		
		
		System.out.println("requestURI:"+requestURI);
		System.out.println("queryString:"+queryString);
		System.out.println("parameters:"+parameters);
	}
	
	
	/**
	 * ������Ϣͷ
	 */
	private void parseHeaders() {
		System.out.println("��ʼ������Ϣͷ...");
		try {
			/*
			 * ������Ϣͷ������:
			 * ѭ������readLine��������ȡÿһ����Ϣͷ
			 * ��readLine��������ֵΪ���ַ���ʱֹͣ
			 * ѭ��(��Ϊ���ؿ��ַ���˵��������ȡ��CRLF
			 * ��������Ϊ��Ϣͷ�����ı�־)
			 * �ڶ�ȡ��ÿ����Ϣͷ�󣬸���": "(ð�ſո�)
			 * ���в�֣�������Ϣͷ��������Ϊkey����Ϣ
			 * ͷ��Ӧ��ֵ��Ϊvalue���浽����headers���
			 * Map����ɽ�������
			 */
			while(true) {
				String line = readLine();
				if("".equals(line)) {
					break;
				}
				String[] data = line.split(":\\s");
				headers.put(data[0], data[1]);
			}
			System.out.println("headers:"+headers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("��Ϣͷ�������!");
	}
	/**
	 * ������Ϣ����
	 */
	private void parseContent() {
		System.out.println("��ʼ������Ϣ����...");
		/*
		 * ������Ϣͷ�Ƿ���Content-Length�����������Ƿ�����Ϣ����
		 */
		try {
			if(headers.containsKey("Content-Length")) {
				//������Ϣ����
				int length = Integer.parseInt(headers.get("Content-Length"));
				
				//��ȡ��Ϣ��������
				byte[] data = new byte[length];
				in.read(data);//inputStream��һ���൱��ɾһ��,�����ظ���ȡ
				
				
				/*
				 * ������ϢͷContent-Type�жϸ���Ϣ���ĵ���������
				 */
				String contenType = headers.get("Content-Type");
				//�ж��Ƿ�Ϊform���ύ����
				if("application/x-www-form-urlencoded".equals(contenType)) {
					/*
					 * �����������൱��ԭGET�����ַ����url�С������Ҳ�����
					 */
					String line = new String(data,"ISO8859-1");
					System.out.println("�������ݣ� "+line);
				    parseParameter(line);
				}
				/*
				 * ������ϢͷContent-Type�жϸ���Ϣ�����Ƿ�Ϊform��
				 */
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��Ϣ���Ľ������!");
	}
	/**
	 * ��������
	 * ��ʽ��name = value&name = value
	 */
	private void parseParameter(String line) {
		/*
		 * �Ƚ������е�"%xx"�����ݰ����ַ���(�����ͨ����UTF-8)��ԭΪ��Ӧ����
		 */
		try {
			/*
			 * URLDecoder��decode�������Խ������ӷ����е�"%xx"����תΪ��Ӧ2����
			 * �ֽ�Ȼ���ո������ַ�������Щ�ֽڻ�ԭΪ��Ӧ�ַ������滻��Щ"%xx"���֣�
			 * Ȼ�󽫻��õ��ַ�������
			 * username=%E4%B8%81%E9%B9%8F&password=777777
			 * ת���
			 * username=����&password=777777
			 */
			System.out.println("ת��ǰ�� "+line);
			line = URLDecoder.decode(line,"UTF-8");
			System.out.println("ת��� "+line);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//����&��ֳ�ÿһ������
		String[] paraArr = line.split("&");
		//����ÿ���������в��
		for(String para : paraArr) {
			//�ٰ���"="���ÿ������
			String[] paras = para.split("=");
			if(paras.length>1) {
				//�ò�����ֵ
				parameters.put(paras[0], paras[1]);
			}else {
				//û��ֵ
				parameters.put(paras[0], null);
			}
		}
		
	}
	/**
	 * ��ȡһ���ַ�������������ȡCR,LFʱֹͣ
	 * ����֮ǰ��������һ���ַ�����ʽ���ء�
	 * @return
	 * @throws IOException
	 */
	private String readLine() throws IOException {
		StringBuilder builder = new StringBuilder();
		//���ζ�ȡ���ֽ�
		int d = -1;
		//c1��ʾ�ϴζ�ȡ���ַ���c2��ʾ���ζ�ȡ���ַ�
		char c1='a',c2='a';
		while((d = in.read())!=-1) {
			c2 = (char)d;
			if(c1==HttpContext.CR&&c2==HttpContext.LF) {
				break;
			}
			builder.append(c2);
			c1 = c2;
		}
		return builder.toString().trim();
		
	}
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	/**
	 * ���ݸ�������Ϣͷ�����ֻ�ȡ��Ӧ��Ϣͷ��
	 * ֵ
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return headers.get(name);
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getQueryString() {
		return queryString;
	}
	/**
	 * ���ݸ����Ĳ�������ȡ��Ӧ�Ĳ���ֵ
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
}









