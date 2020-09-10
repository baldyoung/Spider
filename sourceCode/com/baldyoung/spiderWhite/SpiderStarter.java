package com.baldyoung.spiderWhite;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 定点爬虫启动器
 * （定点爬虫概念：正对某类具有特殊规则的URL，如：某字段有序递增的URL、明显有序可寻的URL）
 *
 */
public interface SpiderStarter {


    // 过程调用
    void execute() throws Exception;
    // http请求报文设计
    String getRequestHttpData(int i);
    // 数据处理模块
    MatchAble getMatchAbleObject();

}
