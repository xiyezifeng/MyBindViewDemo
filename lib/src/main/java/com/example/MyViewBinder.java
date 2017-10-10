package com.example;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xigua on 2017/9/26.
 */

public class MyViewBinder {
    private static final ActivityViewFinder activityFinder = new ActivityViewFinder();//默认声明一个Activity View查找器
    private static final Map<String, ViewBinder> binderMap = new LinkedHashMap<>();//管理保持管理者Map集合
    public static void bind(Object  activity){
        bind(activity, activity, activityFinder);
    }
    public static void bind(Object host, Object object, ViewFinder finder){

    }
}
