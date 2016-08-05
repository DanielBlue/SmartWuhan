package com.example.zhwh.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 工具类
 */
public class SpUtils {
    private static SharedPreferences sp = null;

    /**
     * 得到sp中name节点的boolean值
     * @param context   上下文
     * @param name  节点名
     * @return  该节点存储的boolean值，默认为true
     */
    public static boolean getBoolean(Context context,String name){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(name,true);
    }

    /**
     * 将boolean值存入sp中
     * @param context   上下文
     * @param name  节点名
     * @param value 要存入的boolean值
     */
    public static void setBoolean(Context context,String name,Boolean value) {
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(name,value).commit();
    }

    /**
     * 得到sp中name节点的string值
     * @param context   上下文
     * @param name  节点名
     * @return  该节点存储的string值，默认为空
     */
    public static String getString(Context context,String name){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(name,"");
    }

    /**
     * 将string值存入sp中
     * @param context   上下文
     * @param name  节点名
     * @param value 要存入的string值
     */
    public static void setString(Context context,String name,String value) {
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        sp.edit().putString(name,value).commit();
    }

}
