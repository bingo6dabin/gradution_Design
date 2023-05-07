package com.example.funproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.facebody20191230.Client;
import com.aliyun.facebody20191230.models.RecognizeFaceAdvanceRequest;
import com.aliyun.facebody20191230.models.RecognizeFaceRequest;
import com.aliyun.facebody20191230.models.RecognizeFaceResponse;


import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.funproject.R;
import com.example.funproject.adapter.VideoListAdapter;
import com.example.funproject.entity.Video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AgeAnalaysActivity extends BaseActivity {
    private  String TAG = "AgeAnalays";
    private File currentImageFile = null;
private  int age;
    /*
     这个client是为了请求服务端接口，这里只是为了端上演示，所以将代码写在了Android端
     真正上线不建议将ACCESS_KEY_ID和ACCESS_KEY_SECRET写在端上，会有泄漏风险，建议将请求服务端接口代码写到您的服务端
    */
    private Client client = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_analays);
        try {
            // 2、"YOUR_ACCESS_KEY_ID", "YOUR_ACCESS_KEY_SECRET" 的生成请参考https://help.aliyun.com/document_detail/175144.html
            // 如果您是用的子账号AccessKey，还需要为子账号授予权限AliyunVIAPIFullAccess，请参考https://help.aliyun.com/document_detail/145025.html
            Config config = new Config()
                    // 您的 AccessKey ID
                    .setAccessKeyId("LTAI5t6BPwCS7LwX8SJvJ9ut")
                    // 您的 AccessKey Secret
                    .setAccessKeySecret("NsFbIZGf9KN8UIGCpuNMmYPdK19jlj");
            // 3、访问的域名。注意：这个地方需要求改为相应类目的域名
            config.endpoint = "facebody.cn-shanghai.aliyuncs.com";
            client = new Client(config);
        } catch (Exception e) {
            Log.e(TAG, String.format("onCreate: %s", e.getMessage()));
            showToastSync("初始化失败");
        }
    }


    public void callApiLocal(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // filePath请改成您的真实文件路径
                     String filePath = "resource/test_images/myPhoto.jpg";
//                    String filePath = String.valueOf(currentImageFile.toURL());

                    Log.d(TAG, String.format("begin callApi: %s %s", "RecognizeBankCard", filePath));
                    // 使用文件，文件通过inputStream传入接口。这里只是演示了assets下的文件如何转为stream，如果文件来自其他地方，如sdcard或者摄像头，请自行查看android开发文档或教程将文件转为stream之后传入。
                    InputStream inputStream = AgeAnalaysActivity.this.getAssets().open(filePath);

                    RecognizeFaceAdvanceRequest recognizeFaceAdvanceRequest = new RecognizeFaceAdvanceRequest()
                            .setAge(true)
                            .setImageURLObject(inputStream);
                    RuntimeOptions runtime = new RuntimeOptions();

                    // 复制代码运行请自行打印 API 的返回值
                    RecognizeFaceResponse recognizeFaceResponse = client.recognizeFaceAdvance(recognizeFaceAdvanceRequest, runtime);
                    // 获取整体结果
                    System.out.println("结果如下");
                    System.out.println(Common.toJSONString(TeaModel.buildMap(recognizeFaceResponse)));

                    String response =Common.toJSONString(TeaModel.buildMap(recognizeFaceResponse));
                    JSONObject responseObj = JSONObject.parseObject(response);

                    String body = responseObj.getString("body");
                    JSONObject bodyObj = JSONObject.parseObject(body);

                    String data = bodyObj.getString("Data");
                    JSONObject dataObj = JSONObject.parseObject(data);

                    JSONArray ageListObj = dataObj.getJSONArray("AgeList");
                         age = ageListObj.getInteger(0);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("age",age);
                    Message mMessage= new Message();
                    mMessage.setData(bundle);
                    getInformation.sendMessage(mMessage);

                } catch (TeaException teaException) {
                    Log.d(TAG, "teaException.getCode(): " + teaException.getCode());
                    // 请处理Exception
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        }).start();
    }
    //向handler中传递消息，由handler响应并返回信息
    @SuppressLint("HandlerLeak")
    private final Handler getInformation = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
             age = (int)data.getSerializable("age");
            if(age>=18) {
                showToastSync("人脸认证成功");
                Intent intent = new Intent(AgeAnalaysActivity.this, HomeActivity.class);
                startActivity(intent);
            }else{
                showToastSync("人脸认证失败");
                Intent intent = new Intent(AgeAnalaysActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };
    //销毁
    public void onDestroy() {
        super.onDestroy();
    }
}