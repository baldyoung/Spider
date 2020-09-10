package com.baldyoung.spiderWhite;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class LineStringMatcher implements MatchAble {
    private List<LineStringTemplate> templateList;
    private String charSet;
    public LineStringMatcher(String charSet) {
        // 注意这里的list对象是非线程安全的！
        this.templateList = new LinkedList<>();
        this.charSet = charSet;
    }

    /**
     * 新增数据比对模块
     * 注意模板顺序要和时间数据流格式一一对应！
     * （可优化的！这里可以将regx归并到process的过程中，只需要对每次过程调用进行patter对象生成判断就可以！）
     * @param regx
     * @param process
     */
    public void addTempalte(String regx, ProcessAble process) {
        this.templateList.add(new LineStringTemplate(regx, process));
    }

    /**
     * 获取当前数据比对器下符合标准的数据
     * @param data
     * @return
     */
    public String match(String data) {
        int i=0;
        String result = null;
        LineStringTemplate lineStringTemplate;
        while(i<this.templateList.size()) {
            lineStringTemplate = this.templateList.get(i);
            i++;
            String temp = lineStringTemplate.dispose(data);
            if (null == temp) {
                continue;
            }
            temp = (null == lineStringTemplate.name) ? temp : (lineStringTemplate.name+":"+temp);
            if (null == result) {
                result = "{"+temp;
            } else {
                result += temp;
                result += (i == this.templateList.size()) ? "}" : ", ";
            }
        }
        return result;
    }


    @Override
    public void match(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, this.charSet);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        String temp;
        String result = "";
        int i=0;
        while(null != (line = bufferedReader.readLine())) {
            // System.out.println(line);
            temp = null;
            for (LineStringTemplate template : this.templateList) {
                temp = template.dispose(line);
                if (null != temp) {
                    break;
                }
            }
            if (null == temp) {
                continue;
            }
            i ++;
            result += (1 == i) ? "{" : ",";
            result += temp;
            if (this.templateList.size() == i) {
                result += "}";
                outputStream.write(result.getBytes());
                i = 0;
                result = "";
            }
        }
    }
}
