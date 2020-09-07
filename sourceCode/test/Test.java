package test;
import com.baldyoung.spiderJob.StringMatcher;

import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String... args) throws IOException {
        // test1();
    }

    public static void test2() {

    }


    //
    public static void test1() throws IOException {
        FileInputStream inputStream = new FileInputStream("test/test-data.txt");
        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        out.println("### test1 start");
        StringMatcher stringMatcher = new StringMatcher();
        stringMatcher.addTemplate("{jobName", "job-name(.{1,10})>(.{10,}?)>(.{2,}?)<", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[2];
                result = result.substring(0, result.length()-1);
                return "'"+result+"', ";
            }
        });
        stringMatcher.addTemplate("jobArea", "job-area(.)>(.{2,}?)<", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[1];
                result = result.substring(0, result.length()-1);
                return "'"+result+"', ";
            }
        });
        stringMatcher.addTemplate("jobSalary", "red(.)>(.{2,}?)<", new StringMatcher.TargetTemplate() {
            @Override
            public String process(String string) {
                String[] stringArray = string.split(">");
                String result = stringArray[1];
                result = result.substring(0, result.length()-1);
                return "'"+result+"'}";
            }
        });
        String line = null;
        while(null != (line = bufferedReader.readLine())) {
            stringMatcher.execute(line);
        }
        // stringMatcher.execute(string);
        out.println("\n### test1 end");
    }


    public static void main2(String... args) {
        String data = "<span class=\"job-name\"><a href=\"/job_detail/5e6ea7cce33f7cd81XF_3ty9F1E~.html\" title=\"JAVA开发工程师\" target=\"_blank\" ka=\"search_list_jname_31\" data-jid=\"5e6ea7cce33f7cd81XF_3ty9F1E~\" data-itemid=\"31\" data-lid=\"nlp-1XL5VHSO2uz.search.31\" data-jobid=\"25551073\" data-index=\"0\">JAVA开发工程师</a></span>";
        String data2 = "<span class=\"job-name\"><a href=\"/job_detail/ed8e91430a53f2c433V-29y1E1Q~.html\" title=\"Java开发工程师\" target=\"_blank\" ka=\"search_list_jname_32\" data-jid=\"ed8e91430a53f2c433V-29y1E1Q~\" data-itemid=\"32\" data-lid=\"nlp-1XL5VHSO2uz.search.32\" data-jobid=\"81401836\" data-index=\"1\">Java开发工程师</a></span>\n";

        String regx = "job-name(.{1,10})>(.{10,}?)>(.{2,}?)<";
        //regx = "^job-name";
        Pattern pattern = Pattern.compile(regx);
        Matcher m = pattern.matcher(data);
        int i = 0;
        while (m.find()) {
            String string = m.group(i);
            // out.println(string);
            String[] stringArray = string.split(">");
            string = stringArray[2];
            string = string.substring(0, string.length()-1);
            out.println(string);
            i++;
        }
        m = pattern.matcher(data2);
        i = 0;
        while (m.find()) {
            String string = m.group(i);
            // out.println(string);
            String[] stringArray = string.split(">");
            string = stringArray[2];
            string = string.substring(0, string.length()-1);
            out.println(string);
            i++;
        }
    }


}
