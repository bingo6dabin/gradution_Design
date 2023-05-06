package com.example.funproject.util;

import android.widget.Toast;

public class StringUtils {

    public static boolean isEmpty(String str){
        if(str == null||str.length()<=0){
            return true;
        }else return false;
    }

}
