package com.example.funproject.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.funproject.R;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.entity.User;
import com.example.funproject.util.StringUtils;
import com.example.funproject.util.ViewUtils;

public class LoginActivity extends BaseActivity {
private Button loginButton;
private EditText etAccount;
private  EditText etPwd;
private  Boolean isRemenber =false;
private SharedPreferences sharedPreferences ;
    SharedPreferences preferences;
    private UserDataBaseHelper mUserHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        loginButton = findViewById(R.id.btn_login1);
        etAccount = findViewById(R.id.et_accountLogin);
        etPwd = findViewById(R.id.et_pwdLogin);
        etAccount.addTextChangedListener(new HideTextWatcher(etAccount,11));
        etPwd.addTextChangedListener(new HideTextWatcher(etPwd,6));
        preferences =  getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account,pwd);
            }
        });
    }
    private  void login(String account,String pwd){
        User user = new User(0,account,pwd,"null", 0, 0, 0, 0, "null", "null");
            if(StringUtils.isEmpty(account)){
                showToastSync("请输入账户");
            }
            if(StringUtils.isEmpty(pwd)) {
                showToastSync("请输入密码");
            }
           else if(!StringUtils.isEmpty(account)&&!StringUtils.isEmpty(pwd)&&mUserHelper.queryByUser(user)){
                showToastSync("登陆成功");
                SharedPreferences.Editor editor =preferences.edit();
                editor.putBoolean("isRemenber",true);
                editor.putString("account",etAccount.getText().toString());
                editor.putString("password",etPwd.getText().toString());
                editor.commit();

                Intent inToHome  = new Intent(LoginActivity.this, AgeAnalaysActivity.class);
                startActivity(inToHome);
            }
            else{
               showToastSync("账号或密码错误");
            }

    }

    private void reload() {
    if(preferences.contains("isRemenber")){
        String tempAccount =  preferences.getString("account","");
        etAccount.setText(tempAccount);
        String tempPassword = preferences.getString("password","");
        etPwd.setText(tempPassword);
    }
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

    private class HideTextWatcher implements TextWatcher {
        private  EditText mView;
        private  int mMaxLength;
        public HideTextWatcher(EditText et, int maxLength) {
            this.mView = et;
            this.mMaxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() ==mMaxLength){
                ViewUtils.hideOneInputMethod(LoginActivity.this,mView);
            }
        }
    }
}