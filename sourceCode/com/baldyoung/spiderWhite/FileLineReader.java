package com.baldyoung.spiderWhite;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileLineReader {
    private BufferedReader bufferedReader;
    public FileLineReader(InputStream inputStream, String charSet) throws UnsupportedEncodingException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charSet));
    }
    public FileLineReader(InputStream inputStream) throws UnsupportedEncodingException {
        this(inputStream, "utf-8");
    }
    public String getNextLine() throws IOException {
        String result = this.bufferedReader.readLine();
        if (null == result) {
            this.bufferedReader.close();
        }
        return result;
    }
    public List<String> getAllLine() throws IOException {
        List<String> result = new LinkedList();
        String temp;
        while(null != (temp = this.bufferedReader.readLine())) {
            result.add(temp);
        }
        this.bufferedReader.close();
        return result;
    }
}
