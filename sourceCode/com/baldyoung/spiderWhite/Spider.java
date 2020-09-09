package com.baldyoung.spiderWhite;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * 一个小蜘蛛
 * 对应一个http请求，负责发送给定http报文，并处理响应的报文；
 * 其有自己的数据输出源，无需提供对外输出数据的调用；
 * @param
 */
public class Spider {
    public MatchAble matchAble;
    private Socket socket;
    public Spider(MatchAble matchAble) {
        this.matchAble = matchAble;
    }

    /**
     * 小蜘蛛的启动调用
     * @param httpRequestUnit
     * @param outputStream
     * @throws Exception
     */
    public void execute(HttpRequestUnit httpRequestUnit, OutputStream outputStream) throws Exception {
        if (80 == httpRequestUnit.port) {
            socket = new Socket(httpRequestUnit.host, httpRequestUnit.port);
        } else if (443 == httpRequestUnit.port) {
            socket = SSLSocketFactory.getDefault().createSocket(httpRequestUnit.host, httpRequestUnit.port);
        }
        OutputStream os = this.socket.getOutputStream();
        os.write(httpRequestUnit.packageToHttpRequestData().getBytes());
        InputStream is = this.socket.getInputStream();
        // 通过数据处理器，处理数据流
        matchAble.match(is, outputStream);
        socket.close();
    }
}
