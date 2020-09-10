package com.baldyoung.spiderWhite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串数据匹配模板
 */
public class LineStringTemplate {
    public String name;
    private String template;
    private Pattern pattern;
    private ProcessAble processObject;

    public LineStringTemplate(String name, String template, ProcessAble processObject) {
        this.name = name;
        this.template = template;
        this.pattern = Pattern.compile(template);
        this.processObject = processObject;
    }

    public LineStringTemplate(String template, ProcessAble processObject) {
        this(null, template, processObject);
    }

    /**
     * 当前模板对象的数据处理调用
     * @param data
     * @return {
     *     null : 没有匹配到数据,
     *     String : 与模板对应的数据
     * }
     */
    public String dispose(String data) {
        Matcher m = pattern.matcher(data);
        String result=null;
        String temp;
        while (m.find()) {
            // 这里只取了第一个，有所不妥！
            temp = m.group(0);
            result = (null == processObject) ? temp : processObject.process(temp);
            break;
        }
        return result;
    }


}
