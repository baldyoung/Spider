package test;
import static java.lang.System.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    //

    public static void main(String... args) {
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
