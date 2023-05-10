package com.example.funproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.funproject.R;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.entity.User;
import com.example.funproject.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RegisterActivity extends BaseActivity {
    private UserDataBaseHelper mUserHelper;
    private EditText et_username;
    private EditText et_pwd;
private Button bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void register() {
        String account = et_username.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
        String createDate = formatter.format(curDate);   //格式转换

        User user = new User(0,account,pwd,"暂无", 0, 0, 0, 0, createDate, "暂无");
        if(StringUtils.isEmpty(account)){
            showToastSync("请输入账户");
        }
        if(StringUtils.isEmpty(pwd)) {
            showToastSync("请输入密码");
        }
        mUserHelper.insertToUser(user);
            showToastSync("注册成功");
            navigateTo(LoginActivity.class);
    }

    private void initView() {
        bt_register = findViewById(R.id.btn_register);
        et_username = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        //获得数据库帮助器实例
        mUserHelper = UserDataBaseHelper.getInstance(this);
        //打开数据库读写连接
        mUserHelper.openReadLink();
        mUserHelper.openWriteLink();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}