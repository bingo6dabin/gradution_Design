package com.example.funproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funproject.MainActivity;
import com.example.funproject.R;

public class LoginActivity extends AppCompatActivity {
private Button loginButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login1);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inToHome  = new Intent(LoginActivity.this, HomeActivity.class);

                startActivity(inToHome);
            }
        });
    }
}