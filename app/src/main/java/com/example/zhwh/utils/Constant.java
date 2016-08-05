package com.example.zhwh.utils;

/**
 * Created by 毛琦 on 2016/7/27.
 */
public class Constant {
    /**
     * 储存是否第一次进入程序的boolean值的key
     */
    public static final String IS_FIRST_ENTER = "is_first_enter";
    /**
     * 服务器的前置URL
     */
    public static final String BASE_URL = "http://10.0.2.2:8080/zhbj";

    /**
     *  网站分类json的Url
     */
    public static final String CATEGORY_URL= BASE_URL+"/categories.json";
    /**
     * 储存服务器接收的json字符串的key
     */
    public static final String JSON_FROM_SERVICER = "json_from_servicer";

    /**
     * 储存服务器接收的json字符串的key
     */
    public static final String TOP_NEWS = "top_news";
}
