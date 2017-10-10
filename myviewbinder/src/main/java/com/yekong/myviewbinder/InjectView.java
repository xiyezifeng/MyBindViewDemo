package com.yekong.myviewbinder;

import android.app.Activity;

import com.example.Binder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xigua on 2017/9/26.
 */

public class InjectView {
    private static final String BINDING_CLASS_SUFFIX = "$$ViewBinder";//生成类的后缀 以后会用反射去取
    private static Map<Class<?>, Binder> map = new HashMap<>();


    public static void bind(Activity activity) {
        String bindClassName = activity.getClass().getName() + BINDING_CLASS_SUFFIX;
        try {
            Class bindClass = Class.forName(bindClassName);
            Binder viewBind = map.get(activity);
            if (null == viewBind) {
                viewBind = (Binder) bindClass.newInstance();
                map.put(bindClass, viewBind);
            }
            viewBind.inject(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static void unBind(Activity activity){
        String bindClassName = activity.getClass().getName() + BINDING_CLASS_SUFFIX;
        try {
            Class bindClass = Class.forName(bindClassName);
            Binder viewBind = map.get(bindClass);
            if (null == viewBind) {
                throw new NullPointerException("");
            }
            viewBind.unBind(activity);
            map.remove(bindClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
