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
"Connection: close\r\nUpgrade-Insecure-Requests: 1\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\nAccept-Encoding: \r\nAccept-Language: zh-CN,zh;q=0.9\r\ncookie: sid=sem_pz_bdpc_dasou_title; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1599178642; __g=sem_pz_bdpc_dasou_title; lastCity=100010000; __c=1599178642; __l=l=%2Fwww.zhipin.com%2Fc100010000-p100101%2F%3Fpage%3D3%26ka%3Dpage-3&r=https%3A%2F%2Fwww.baidu.com%2Fother.php%3Fsc.Ks0000jc75tvqzsmu0erMTTKsKFnEi5DLnocyBk_rrEGAIF6gZ89l54Mt2tA6wkbvWZFB20TwlNHAplKLFAWQVcTvYt85Q4kD9ozCjP609q8M8TaHxUJ2Dve6nORwNjRng_wUKPMdPDgYj1Gu6scoLuJHkuWt8_Xjs8czyll07l5zZBberk78L5YDdUh8XTO0d2c7PF0HEHws02w0gpkfRC4nmgh.7D_NR2Ar5Od663rj6t8AGSPticrtXFBPrM-kt5QxIW94UhmLmry6S9wiGyAp7BEIu80.TLFWgv-b5HDkrfK1ThPGujYknHb0THY0IAYqmhq1Tqpkko60IgP-T-qYXgK-5H00mywxIZ-suHY10ZIEThfqmhq1Tqpkko60ThPv5HD0IgF_gv-b5HDdnHbYnjnYnHn0UgNxpyfqnHcLPWTLnj00UNqGujYknjm3P10vPfKVIZK_gv-b5HDkPHnY0ZKvgv-b5H00pywW5R9rf6KWThnqP1bkn0%26ck%3D4194.2.111.364.163.414.401.667%26dt%3D1599178639%26wd%3Dboss%25E7%259B%25B4%25E8%2581%2598%26tpl%3Dtpl_11534_22836_18980%26l%3D1519403413%26us%3DlinkName%253D%2525E6%2525A0%252587%2525E5%252587%252586%2525E5%2525A4%2525B4%2525E9%252583%2525A8-%2525E4%2525B8%2525BB%2525E6%2525A0%252587%2525E9%2525A2%252598%2526linkText%253DBOSS%2525E7%25259B%2525B4%2525E8%252581%252598%2525E2%252580%252594%2525E2%252580%252594%2525E6%252589%2525BE%2525E5%2525B7%2525A5%2525E4%2525BD%25259C%2525EF%2525BC%25258C%2525E6%252588%252591%2525E8%2525A6%252581%2525E8%2525B7%25259F%2525E8%252580%252581%2525E6%25259D%2525BF%2525E8%2525B0%252588%2525EF%2525BC%252581%2526linkType%253D&g=%2Fwww.zhipin.com%2F%3Fsid%3Dsem_pz_bdpc_dasou_title&friend_source=0&friend_source=0; __a=33074650.1599178642..1599178642.14.1.14.14; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1599189231; __zp_stoken__=8c81bYSVwSy13bVl%2Fcj1bVUBrQHA1OhIzZSdAVRJqfzoWe3U6YTcOTDl%2Bd3YjXhgsPGQDeSQ7RCRdazodbGJ%2FABkxF2dmYUYkJ0NzYT8yIyx5JhFBTkFfFgAKGkg%2FFi8ZOjV%2BH2AbRFtQQHh%2BfA%3D%3D; ___gtid=1061709971; __fid=f74e4ee90f7a300282cf36e753fe58ee\r\n\r\n";


	
		
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