package com.baldyoung.spiderWhite;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 定点爬虫启动器
 * （定点爬虫概念：正对某类具有特殊规则的URL，如：某字段有序递增的URL、明显有序可寻的URL）
 *
 */
public class SpiderStarter {
    // 启动类的路径
    String starterPath = "";
    HttpRequestUnit httpRequestUnit;
    MatchAble matchAbleObject;
    Spider spider;

    public static void main(String ... args) throws Exception {
        new SpiderStarter().execute();
    }

    // 主流程函数
    public void execute() throws Exception {
        int i=0;
        for (i=0; i<99; i++) {
            spider.execute(getRequestHttpData(i), System.out);
        }
    }
    // 初始化调用
    public void init() throws IOException {
        httpRequestUnit = new HttpRequestUnit();
        // 读取header配置文件
        FileInputStream fileInputStream = new FileInputStream("d:/workStation/project/Spider/resource/in.properties");
        List<String> headerList = new FileLineReader(fileInputStream).getAllLine();
        // 读取user-agent配置文件
        httpRequestUnit.requestMethod = "GET";
        httpRequestUnit.userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36";
        httpRequestUnit.headerList = headerList;
        // 配置小蜘蛛
        spider = new Spider(getMatchAbleObject());
    }
    // http请求报文设计
    public HttpRequestUnit getRequestHttpData(int i) throws Exception {
        String url = String.format("");
        httpRequestUnit.targetUrl = url;
        return httpRequestUnit;
    }
    // 数据处理模块
    public MatchAble getMatchAbleObject() {
        LineStringMatcher matcher = new LineStringMatcher("GBK");
        matcher.addTempalte("", (string) -> null);
        return matcher;
    }

}
