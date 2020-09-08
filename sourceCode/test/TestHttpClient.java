package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestHttpClient {
    Socket socket = null;
	String host = "www.zhipin.com";
	Integer port = 443;
    
    public void createSocket() {
        try {
            // socket = new Socket(host, port);
			socket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(host, port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void communcate() {
        // 注意这里必须制定请求方式 地址 注意空格
        StringBuffer sb = new StringBuffer("GET http://www.javathinker.org/ HTTP/1.1\r\n");
        // 以下为请求头 
        sb.append("Host: www.javathinker.org\r\n");
        sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0\r\n");
        sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
        sb.append("Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        // 注意这里不要使用压缩 否则返回乱码
        sb.append("Accept-Encoding: \r\n");
        sb.append("Connection: keep-alive\r\n");
        sb.append("Upgrade-Insecure-Requests: 1\r\n");
        // 注意这里要换行结束请求头
        sb.append("\r\n");
		
		// ======================================================
		String content = sb.toString();
		content = 
"GET https://www.zhipin.com/c101240100-p100101/?page=2&ka=page-2 HTTP/1.1\r\n" +
"Host: www.zhipin.com\r\n" +
"Connection: close\r\nUpgrade-Insecure-Requests: 1\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\nAccept-Encoding: \r\nAccept-Language: zh-CN,zh;q=0.9\r\ncookie: __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1599178642,1599525428; __fid=f74e4ee90f7a300282cf36e753fe58ee; lastCity=100010000; __c=1599525428; __l=l=%2Fwww.zhipin.com%2Fc100010000-p100101%2F%3Fpage%3D1%26ka%3Dpage-1&r=&g=&friend_source=0&friend_source=0; __a=33074650.1599178642.1599178642.1599525428.33.2.10.33; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1599530031; __zp_stoken__=67f6bCxY%2FKylnGx5QWmp5FgoPL38cOS5FREgDMW1IOB8wdWluV18uexFQH35zVTZuWjs6EwgwJlF3FzkaOhMnJ2pnJyhHAXJjfTR3DmlLDGIHGD0rdRV3HylvOUFRFT1WXGpHdUxOdVt2PAl5JQ%3D%3D\r\n\r\n";


	
		
        System.out.println(content);
		System.out.println("#######################################################");
        System.out.println("service response=>\n");
		try {
            OutputStream os = socket.getOutputStream();
            os.write(content.getBytes());

            InputStream is = socket.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            System.out.println(new String(baos.toByteArray(), "utf-8"));
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
/*		String t = "https://baidu.com/index.html";
		System.out.println(t.indexOf("//"));
		int a = t.indexOf("//");
		String k = t.substring(t.indexOf("//")+2);
		System.out.println(t.substring(t.indexOf("//")+2));
		System.out.println(k.substring(0, k.indexOf("/")));
	*/	
		
        TestHttpClient client = new TestHttpClient();
        client.createSocket();
        client.communcate();
    }
}