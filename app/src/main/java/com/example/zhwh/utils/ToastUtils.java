package com.example.zhwh.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 毛琦 on 2016/7/30.
 */
public class ToastUtils {
    public static void show(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
