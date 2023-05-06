package com.example.funproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funproject.MainActivity;
import com.example.funproject.R;
import com.example.funproject.database.funProDataBaseHelper;
import com.example.funproject.entity.User;
import com.example.funproject.util.StringUtils;

public class LoginActivity extends BaseActivity {
private Button loginButton;
private EditText etAccount;
private  EditText etPwd;
    private funProDataBaseHelper mHelper;
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
        User user = new User("null",account,pwd,"null","null");
            if(StringUtils.isEmpty(account)){
                showToastSync("请输入账户");
            }
            if(StringUtils.isEmpty(pwd)) {
                showToastSync("请输入密码");
            }
           else if(!StringUtils.isEmpty(account)&&!StringUtils.isEmpty(pwd)&&mHelper.queryByUser(user)){
                showToastSync("登陆成功");
                Intent inToHome  = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(inToHome);
            }
            else{
               showToastSync("账号或密码错误");
            }

    }
    @Override
    protected void onStart() {
        super.onStart();
        //获得数据库帮助器实例
        mHelper = funProDataBaseHelper.getInstance(this);
        //打开数据库读写连接
        mHelper.openReadLink();
        mHelper.openWriteLink();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}