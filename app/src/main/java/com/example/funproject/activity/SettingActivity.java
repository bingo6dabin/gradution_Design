package com.example.funproject.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.funproject.R;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.entity.User;
import com.example.funproject.fragment.MyFragment;
import com.example.funproject.util.PermissionUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class SettingActivity extends BaseActivity {
private ImageView header;
private EditText account;
private  EditText pwd;
private  EditText configPwd;
private  EditText introduce;
private Button configChange;
private SharedPreferences preferences;
private UserDataBaseHelper mUserHelper;
private   ActivityResultLauncher<Intent> mResultLauncher;
private     Uri picUri;
private   User user;
private  String picturePath;
    private static final String[] PERMISSIONS_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE_STORAGE = 1;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //获得数据库帮助器实例
        mUserHelper = UserDataBaseHelper.getInstance(this);
        //打开数据库读写连接
        mUserHelper.openReadLink();
        mUserHelper.openWriteLink();
//        手动扫描入库
//        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},
//                null, null);

        initView();
        try {
            initData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK){
                    Intent intent = result.getData();
                     picUri = intent.getData();
                    if(picUri!=null){
                        Glide.with(SettingActivity.this).load(picUri).into(header);
                    }
                }
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到系统相册选择图片并返回
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                //打开系统相册，并等待图片选择结果
                mResultLauncher.launch(intent);
            }
        });

        configChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUsername(account.getText().toString());
                user.setPassword(pwd.getText().toString());
                user.setIntroduce(introduce.getText().toString());
//                    将此pic存到user中
                user.setHeadImage(picUri.toString());
//                showToastSync(user.getUid().toString());
                mUserHelper.updataUser(user);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    private void initData() throws FileNotFoundException {
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户的所有信息加载到“我的界面”
        if(preferences.contains("account")){
               String tempName = preferences.getString("account",null);
//            header.setImageURI(Uri.parse(user.getHeadImage()))
            user= mUserHelper.querryByUsername(tempName);
             setHeader();
            account.setText(user.getUsername());
            pwd.setText(user.getPassword());
            configPwd.setText(user.getPassword());
            introduce.setText(user.getIntroduce());
        }
        
    }

    private  void setHeader() throws FileNotFoundException {
        ContentResolver resolver = getContentResolver();
        //获取访问权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 已经被授予该权限，可以访问外部存储器中的文件
            Glide.with(this).load(Uri.parse(user.getHeadImage())).into(header);
        } else {
            // 未被授予该权限，需要申请权限
            PermissionUtil.checkPermission(this,PERMISSIONS_EXTERNAL_STORAGE,REQUEST_CODE_STORAGE);
            // 已经被授予该权限，可以访问外部存储器中的文件
//            InputStream inputStream = resolver.openInputStream(Uri.parse(user.getHeadImage()));
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Glide.with(this).load(Uri.parse(user.getHeadImage())).into(header);
        }

    }

    private void initView() {
        header = findViewById(R.id.iv_UpdataHeader);
        account = findViewById(R.id.et_UpdataAccount);
        pwd = findViewById(R.id.et_updataPwd);
        configPwd =findViewById(R.id.et_config_PWD);
        introduce = findViewById(R.id.et_intro);
        configChange = findViewById(R.id.bt_config_change);
    }
}