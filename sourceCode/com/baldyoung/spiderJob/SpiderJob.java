package com.baldyoung.spiderJob;

import static java.lang.System.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.*;
/**
URL路径分隔符采用/

**/
public class SpiderJob implements Runnable{
	private String method;
	private String targetURL;
	private String host;
	private Integer port;
	private String header;
	private String content;
	private Socket socket;
	private PrintStream outStream = out;
	private OutputStream outputStream;
	private int statusFlag = 0; // {0 : 未执行, 1 : 运行中, desc : 用来控制输出流在运行时的不可变更}
	private synchronized void setOutStream(PrintStream printStream) {
		if (0 == statusFlag) {
			this.outStream = printStream;
		}
	}
	private SpiderJob(String method, String host, String targetURL, Integer port, String header, String content, Socket socket) {
		this.method = method;
		this.host = host;
		this.targetURL = targetURL;
		this.port = port;
		this.header = header;
		this.content = content;
		this.socket = socket;
	}
	public List<String> getData() {
		synchronized(this) {
			statusFlag = 1;
		}
		// 构建HTTP请求报文
		String requestData = this.method+" "+this.targetURL+" HTTP/1.1\r\n" +
		"Host: "+this.host+"\r\n" +
		this.header + 
		"\r\n" + 
		this.content;
		// out.println("+++++++++++++++++++++++++");
		try {
            OutputStream os = this.socket.getOutputStream();
            os.write(requestData.getBytes());
            InputStream is = this.socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(is, "UTF-8");
			char[] chars = new char[1024];
            int len = -1;
			// 匹配算法
            while ((len = reader.read(chars, 0, 1024)) != -1) {
				outStream.println(String.valueOf(chars, 0, len));
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		synchronized(this) {
			statusFlag = 0;
		}
		return null;
	}
	public void run() {
		getData();
	}
	public static void main(String... args) throws Exception {
		if (args.length < 0) {
			return;
		}
		for (int i=0; i<20; i++) {
		// 加载配置文件
		FileInputStream fileInputStream = new FileInputStream(args[0]);
		Properties properties = new Properties();
		properties.load(fileInputStream);
		String header = properties.getProperty("header");
		header = header.replaceAll(";", "\r\n");
		out.println(header);
		header = header + "cookie: " + properties.get("cookie") + "\r\n";
		
		SpiderJob sj = getInstance(
		properties.getProperty("method"), 
		properties.getProperty("url"),
		header,
		properties.getProperty("content")
		);
		if (args.length > 1) {
			sj.setOutStream(new PrintStream(new FileOutputStream(args[1])));
		}
		sj.getData();
		}
	}
	public static void exception(String desc) throws Exception {
		throw new Exception(desc);
	}
	public static void exception() throws Exception {
		exception("");
	}
	public static SpiderJob getInstance(String method, String URL, String header, String content) throws Exception {
		URL = URL.toLowerCase();
		// 协议端口判断
		Integer port = null;
		if (0 == URL.indexOf("https")) {
			port = 443;
		} else if (0 == URL.indexOf("http")) {
			port = 80;
		} else {
			exception("不支持的协议类型");
		}
		// 从URL中获取HOST
		int start = URL.indexOf("//")+2;
		if (-1 == start) {
			exception("URL格式错误");
		}
		String temp = URL.substring(start);
		int end = temp.indexOf("/");
		String host = (-1 == end ? temp : temp.substring(0, end));
		// 创建TCP链接
		Socket socket = null;
		if (80 == port) {
			socket = new Socket(host, port);
		} else if(443 == port) {
			socket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(host, port);
		}	
		return new SpiderJob(method, host, URL, port, header, content, socket);
	}
	
}

// html字符串匹配器
class HTMLStringMatcher {
	private char[] charArrayA, charArrayB; // 临时存储用的字符数组，二者长度一致，相互配合使用
	private int currentCharArrayFlag; // 用于标记当前使用的字符串数组
	private List<char[]> templateList; // 模板集合
	private int maxTemplateSize; // 模板中最长的数组大小
	
}
	
	


