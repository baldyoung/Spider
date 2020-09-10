package com.baldyoung.spiderJob;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * 字符串集中匹配器
 */
@Deprecated
public class StringMatcher {
    /**
     * 模板处理类
     */
    public static abstract class TargetTemplate {
        private String name;
        private String template;
        private Pattern pattern;
        public TargetTemplate(String name, String template) {
            this.name = name;
            this.template = template;
            this.pattern = Pattern.compile(template);
        }
        public TargetTemplate(String template) {
            this(null, template);
        }
        public TargetTemplate() {

        }
        // 修改正则表达式的匹配模板
        public void updateTemplate(String template) {
            if (null == this.template) {
                this.template = template;
            }
            this.pattern = Pattern.compile(template);
        }
        public void setName(String name) {
            this.name = name;
        }

        /**
         * 获取给定字符串中的特定数据
         * @param data
         * @throws IOException
         */
        public String matchData(String data) throws IOException {
            // this.data = data;
            Matcher m = pattern.matcher(data);
            String result=null;
            int i = 0;
            while (m.find()) {
                String string = m.group(i);
                result = process(string);
                // 将string写入指定流中
                // outputData(string);
                break;
                // i++;
            }
            if (null != this.name && null != result) {
                result = this.name+":"+result;
            }
            return result;
        }

        /**
         * 自定义数据的处理过程，并返回需要的数据
         * @param string
         * @return
         */
        public abstract String process(String string);

    }
    private List<TargetTemplate> templateList;
    private OutputStream outputStream;
    public StringMatcher(OutputStream outputStream, List<TargetTemplate> templateList) {
        this.outputStream = outputStream;
        this.templateList = templateList;
    }
    public StringMatcher() {
        this(out, new LinkedList());
    }
    public StringMatcher(OutputStream outputStream) {
        this(outputStream, new LinkedList<>());
    }

    public void addTemplate(String name, String regx, TargetTemplate targetTemplate) {
        targetTemplate.setName(name);
        targetTemplate.updateTemplate(regx);
        this.templateList.add(targetTemplate);
    }

    public void execute(String data) throws IOException {
        int i=0;
        String result = null;
        while(i<this.templateList.size()) {
            TargetTemplate targetTemplate = this.templateList.get(i);
            i++;
            String temp = targetTemplate.matchData(data);
            if (null == temp) {
                continue;
            }
            if (null == result) {
                result = temp;
            } else {
                result += ", " + temp;
            }
        }

        if (null != result) {
            this.outputStream.write(result.getBytes());
        }
    }
}

