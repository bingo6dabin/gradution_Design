package com.example.funproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funproject.activity.LoginActivity;
import com.example.funproject.activity.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    private Button loginButton ;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inToLogin  = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(inToLogin);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intoRegister = new Intent(MainActivity.this, RegisterActivity.class);

                startActivity(intoRegister);
            }
        });
    }
}