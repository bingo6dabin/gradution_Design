package com.example.funproject.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtils {
    public  static void hideOneInputMethod(Activity act, View v){
        //获取输入法管理器
        InputMethodManager imm =(InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        //关闭屏幕上的输入框
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }
}
