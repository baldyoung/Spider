package com.baldyoung.spiderJob;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.lang.System.*;
@Deprecated
public class SpiderStarter {
    public static void main(String... args) throws Exception {

        // user-agent配置
        List<String> userAgentList = DataSourceUnit.getInstance(new FileInputStream("d:/workStation/project/Spider/resource/userAgents.data"), "utf-8").getData();
        // userAgentList.forEach(cell -> out.println(cell));
        // 其他HTTP报文参数
        FileInputStream fileInputStream = new FileInputStream("d:/workStation/project/Spider/resource/in.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        String header = properties.getProperty("header");
        header = header.replaceAll(";", "\r\n");
        out.println(header);
        header = header + "cookie: " + properties.get("cookie") + "\r\n";
        String content = properties.getProperty("content");
        // 数据输出源的配置
        FileOutputStream fileOutputStream = new FileOutputStream("d:/workStation/project/Spider/resource/out.txt");
        // 数据处理器
        StringMatcher stringMatcher = getMatcher(fileOutputStream);

        // 批量发起HTTP请求
        int i;
        for (i=0; i<93; i++) {
            String tempHeader = header + "User-Agent: " + userAgentList.get(0) + "\r\n";
            SpiderJob.getInstance(
                    "GET",
                    getXiaoShuoUrl(),
                    tempHeader,
                    content,
                    stringMatcher
            ).getData();
            out.println("item "+i);
        }
        for (String userAgent : userAgentList) {
            if (true) return;
            // out.println("request data ....");
            String tempHeader = header + "User-Agent: " + userAgent + "\r\n";
            SpiderJob.getInstance(
                    "GET",
                    getXiaoShuoUrl(),
                    tempHeader,
                    content,
                    stringMatcher
            ).getData();
            out.println("item "+i++);
            //Thread.sleep(3000);
            /*SpiderJob.getInstance(
                    "GET",
                    "https://static.zhipin.com/zhipin-geek/v308/web/geek/css/main.css",
                    tempHeader,
                    content,
                    null
            ).getData();
            Thread.sleep(5000);*/
            // out.println("option completed!");
            out.println("item "+i++);
            // break;
        }
    }
    public static int pageIndex = 0;
    public static String getNextUrl() {
        pageIndex++;
        return String.format("https://www.zhipin.com/c100010000-p100101/?page=%d&ka=page-%d", pageIndex, pageIndex);
    }
    public static String getLaGouUrl() {
        pageIndex++;
        return String.format("https://www.lagou.com/zhaopin/Java/2/?filterOption=%d&sid=64ceb710a6b049b9a222fbf41ad61564", pageIndex);
    }
    public static String getNewsUrl() {
        pageIndex++;
        return String.format("http://top.sogou.com/hot/shishi_%d.html", pageIndex);
    }

    public static String getXiaoShuoUrl() {
        pageIndex++;
        return String.format("http://www.jjwxc.net/onebook.php?novelid=622739&chapterid=%d", +pageIndex);
    }
    public static StringMatcher getMatcher(OutputStream outputStream) {
        StringMatcher stringMatcher = new StringMatcher(outputStream);
        stringMatcher.addTemplate(null, "<h1>(.{1,})</h1>", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[1];
                result = result.substring(0, result.length()-4);
                return "\t\t"+result+"\n";
            }
        });
        stringMatcher.addTemplate(null, "<p>(.{1,})</p>", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[1];
                result = result.substring(0, result.length()-4);
                return "\t"+result+"\n";
            }
        });
        if (true) return stringMatcher;
        stringMatcher.addTemplate("{title", "gv-title(.)>(.{20,100})>(.{2,}?)<", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[2];
                result = result.substring(0, result.length()-1);
                return "'"+result+"', ";
            }
        });
        stringMatcher.addTemplate("date", "gv-time(.)>(.{2,}?)<", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[1];
                result = result.substring(0, result.length()-1);
                return "'"+result+"', ";
            }
        });
        stringMatcher.addTemplate("desc", "gv-desc(.)>(.{2,}?)<", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[1];
                result = result.substring(0, result.length()-1);
                return "'"+result+"'}";
            }
        });
        return stringMatcher;
    }
}
