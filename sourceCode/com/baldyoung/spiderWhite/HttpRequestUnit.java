package com.baldyoung.spiderWhite;

import java.util.LinkedList;
import java.util.List;

/**
 * HTTP请求报文单元
 */
public class HttpRequestUnit {
    // host地址
    public String host = "";
    // 端口地址
    public int port = 80;
    // 请求方式
    public String requestMethod = "";
    // 目标路径
    public String targetUrl = "";
    // 常用头部字段
    public String userAgent = "";
    // 其它头部字段集合
    public List<String> headerList = new LinkedList();
    /*// cookie集合
    public List<String> cookieList = new LinkedList();
    // 数据体
    public byte[] content = null;*/

    public String packageToHttpRequestData() throws Exception {
        targetUrl = targetUrl.toLowerCase();
        // 协议端口判断
        if (0 == targetUrl.indexOf("https")) {
            port = 443;
        } else if (0 == targetUrl.indexOf("http")) {
            port = 80;
        } else {
            throw new Exception("请求协议不支持");
        }
        // 从URL中获取HOST
        int start = targetUrl.indexOf("//")+2;
        if (-1 == start) {
            throw new Exception("URL格式错误");
        }
        String temp = targetUrl.substring(start);
        int end = temp.indexOf("/");
        host = (-1 == end ? temp : temp.substring(0, end));

        // ----------------- 拼接Http报文头
        String result = requestMethod + " " + targetUrl + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "User-Agent: " + userAgent + "\r\n";
        for (String header : headerList) {
            result += header + "\r\n";
        }

        result += "\r\n";
        return result;
    }
}
