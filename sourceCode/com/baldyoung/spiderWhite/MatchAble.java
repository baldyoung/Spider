package com.baldyoung.spiderWhite;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 可匹配的操作
 * 用于对服务器返回的数据流进行处理，并将匹配的数据写入相应的输出流；
 * @param
 */
public interface MatchAble {
    void match(InputStream inputStream, OutputStream outputStream) throws IOException;
}
