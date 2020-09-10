package com.baldyoung.spiderWhite.demo;

import com.baldyoung.spiderWhite.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 定向爬虫的使用demo
 * 这里是一个使用的demo，其实验对象是腾讯网中的新闻数据。
 * 小蜘蛛的输出结果可以在项目resource目录下的out.txt中看到！
 */
public class WhiteSpiderDemo implements SpiderStarter {
    public static void main(String... args) throws Exception {
        new WhiteSpiderDemo().execute();
    }

    // 过程调用
    public void execute() throws Exception {
        HttpRequestUnit httpRequestUnit = new HttpRequestUnit();
        // 读取header配置文件
        FileInputStream fileInputStream = new FileInputStream("d:/workStation/project/Spider/resource/header.txt");
        List<String> headerList = new FileLineReader(fileInputStream).getAllLine();
        // 创建输出流
        FileOutputStream outputStream = new FileOutputStream("d:/workStation/project/Spider/resource/out.txt");
        // 读取user-agent配置文件
        httpRequestUnit.requestMethod = "GET";
        httpRequestUnit.userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36";
        httpRequestUnit.headerList = headerList;
        // 配置小蜘蛛
        Spider spider = new Spider(getMatchAbleObject());
        // 执行过程
        for (int i=0; i<99; i++) {
            httpRequestUnit.targetUrl = getRequestHttpData(i);
            spider.execute(httpRequestUnit, outputStream);
            // break;
        }
    }
    // http请求报文设计
    public String getRequestHttpData(int i) {
        return "https://i.news.qq.com/trpc.qqnews_web.kv_srv.kv_srv_http_proxy/list?sub_srv_id=health&srv_id=pc&offset="+(20 * i)+"&limit=20&strategy=1&ext={%22pool%22:[%22top%22],%22is_filter%22:10,%22check_type%22:true}";
    }
    // 数据处理模块
    public MatchAble getMatchAbleObject() {
        LineStringMatcher matcher = new LineStringMatcher("utf-8");
        matcher.addTempalte("\\[(.{5,})\\}\\]", (string) -> string);
        return matcher;
    }

}
