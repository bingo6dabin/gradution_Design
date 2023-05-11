package com.example.funproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.funproject.R;
import com.example.funproject.adapter.VideoListAdapter;
import com.example.funproject.dataSourse.Analysis;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.entity.Video;
import com.example.funproject.fragment.MyFragmentPagerAdapter;
import com.example.funproject.util.PermissionUtil;
import com.google.android.material.badge.BadgeDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;

public class HomeActivity extends AppCompatActivity {
    private RadioGroup rg_tab_bar;
    private RadioButton rb_shouye;
    private RadioButton rb_message;
//    private RadioButton rb_share;
    private RadioButton rb_my;
//    private RadioButton rb_setting;
    private ViewPager vpager;
    private volatile List<Video> mVideoes = new ArrayList<>();
    private VideoListAdapter mVideoListAdapter;
    private String categoryId="4";
    private MyFragmentPagerAdapter mAdapter;
    private  UserDataBaseHelper mUserHelper;
    private static final String[] PERMISSIONS_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE_STORAGE = 1;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 已经被授予该权限，可以访问外部存储器中的文件
        } else {
            // 未被授予该权限，需要申请权限
            PermissionUtil.checkPermission(this,PERMISSIONS_EXTERNAL_STORAGE,REQUEST_CODE_STORAGE);
        }
        rb_shouye.setChecked(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fg_my);
//        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fg_my);
//        if (fragment != null) {
//            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            Log.d("Jun", "存储权限获取成功");
//        }else{
//            Log.d("Jun", "存储权限获取失败");
//        }
    }

    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void bindViews() {
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_shouye = (RadioButton) findViewById(R.id.rb_home);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
//        rb_share = (RadioButton) findViewById(R.id.rb_share);
        rb_my = (RadioButton) findViewById(R.id.rb_my);
//        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rg_tab_bar.setOnCheckedChangeListener(this::onCheckedChanged);

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
       vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {
               //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
               if (state == 2) {
                   switch (vpager.getCurrentItem()) {
                       case PAGE_ONE:
                           rb_shouye.setChecked(true);
                           break;
                       case PAGE_TWO:
                           rb_message.setChecked(true);
                           break;
                       case PAGE_THREE:
                           rb_my.setChecked(true);
                           break;
//                       case PAGE_FOUR:
//                           rb_setting.setChecked(true);
//                           break;
                   }
               }
           }
       });
    }
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_message:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_my:
                vpager.setCurrentItem(PAGE_THREE);
                break;
//            case R.id.rb_setting:
//                vpager.setCurrentItem(PAGE_FOUR);
//                break;
        }

    }
}