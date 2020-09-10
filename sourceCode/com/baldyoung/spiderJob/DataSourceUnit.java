package com.baldyoung.spiderJob;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
@Deprecated
public class DataSourceUnit {
    private BufferedReader bufferedReader;
    private DataSourceUnit(InputStreamReader inputStreamReader) {
        this.bufferedReader = new BufferedReader(inputStreamReader);
    }
    public static DataSourceUnit getInstance(InputStream inputStream, String charSet) throws UnsupportedEncodingException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charSet);
        return new DataSourceUnit(inputStreamReader);
    }
    public List<String> getData() throws IOException {
        List<String> data = new LinkedList();
        String line;
        while(null != (line = this.bufferedReader.readLine())) {
            data.add(line);
        }
        this.bufferedReader.close();
        return data;
    }

}
