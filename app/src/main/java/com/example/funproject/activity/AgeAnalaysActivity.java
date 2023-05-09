package com.example.funproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.internal.IoConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
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
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgeAnalaysActivity extends BaseActivity {
    private  String TAG = "AgeAnalays";
    private File currentImageFile = null;
    private    File photoFile;
    //camera
    private ImageCapture imageCapture;
    private File outputDirectory;
    private ExecutorService cameraExecutor;

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
        // 请求相机权限
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, Configuration.REQUIRED_PERMISSIONS,
                    Configuration.REQUEST_CODE_PERMISSIONS);
        }
    }
    private void takePhoto() {
        // 确保imageCapture 已经被实例化, 否则程序将可能崩溃
        if (imageCapture != null) {
            // 创建带时间戳的输出文件以保存图片，带时间戳是为了保证文件名唯一
             photoFile = new File(outputDirectory,
                    new SimpleDateFormat(Configuration.FILENAME_FORMAT,
                            Locale.SIMPLIFIED_CHINESE).format(System.currentTimeMillis())
                            + ".jpg");

            // 创建 output option 对象，用以指定照片的输出方式
            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions
                    .Builder(photoFile)
                    .build();

            // 执行takePicture（拍照）方法
            imageCapture.takePicture(outputFileOptions,
                    ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageSavedCallback() {// 保存照片时的回调
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            Uri savedUri = Uri.fromFile(photoFile);
                            String msg = "照片捕获成功! " + savedUri;
//                            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                            Log.d(Configuration.TAG, msg);
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            Log.e(Configuration.TAG, "Photo capture failed: " + exception.getMessage());
                        }
                    });
        }
    }

    private void startCamera() {
        // 将Camera的生命周期和Activity绑定在一起（设定生命周期所有者），这样就不用手动控制相机的启动和关闭。
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {

                // 将你的相机和当前生命周期的所有者绑定所需的对象
                ProcessCameraProvider processCameraProvider = cameraProviderFuture.get();


                // 创建一个Preview 实例，并设置该实例的 surface 提供者（provider）。
                PreviewView viewFinder = (PreviewView)findViewById(R.id.viewFinder);
                Preview preview = new Preview.Builder()
                        .build();

                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                // 配置照片捕获用例
                 imageCapture = new ImageCapture.Builder()
                        .setTargetResolution(new Size(192, 108)) // 设置照片分辨率
                        .build();

                // 选择前置摄像头作为默认摄像头
                CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

                // 重新绑定用例前先解绑
                processCameraProvider.unbindAll();

                // 绑定用例至相机
                processCameraProvider.bindToLifecycle(AgeAnalaysActivity.this, cameraSelector,
                        preview,
                        imageCapture);

            } catch (Exception e) {
                Log.e(Configuration.TAG, "用例绑定失败！" + e);
            }
        }, ContextCompat.getMainExecutor(this));

    }


    private boolean allPermissionsGranted() {
        for (String permission : Configuration.REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    static class Configuration {
        public static final String TAG = "CameraxBasic";
        public static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
        public static final int REQUEST_CODE_PERMISSIONS = 10;
        public static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Configuration.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {// 申请权限通过
                startCamera();
            } else {// 申请权限失败
                Toast.makeText(this, "用户拒绝授予权限！", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    private File getOutputDirectory() {
        File mediaDir = new File(getExternalMediaDirs()[0], getString(R.string.app_name));
        boolean isExist = mediaDir.exists() || mediaDir.mkdir();
        return isExist ? mediaDir : null;
    }
    public void callApiLocal(View view) {
        // 设置照片等保存的位置

        outputDirectory = getOutputDirectory();
        // 设置拍照按钮监听
        takePhoto();
        cameraExecutor = Executors.newSingleThreadExecutor();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

//                    String filePath =  photoFile.getPath();
                    System.out.println(photoFile.getPath());
                    Uri savedUri = Uri.fromFile(photoFile);
                    // filePath请改成您的真实文件路径
                     String filePath = "resource/test_images/myPhoto.jpg";
//                    String filePath = String.valueOf(currentImageFile.toURL());

                    Log.d(TAG, String.format("begin callApi: %s %s", "RecognizeBankCard", filePath));
                    // 使用文件，文件通过inputStream传入接口。这里只是演示了assets下的文件如何转为stream，如果文件来自其他地方，如sdcard或者摄像头，请自行查看android开发文档或教程将文件转为stream之后传入。
                   InputStream inputStream = AgeAnalaysActivity.this.getAssets().open(filePath);
//                    InputStream inputStream = new FileInputStream(photoFile);
//                 InputStream inputStream = new FileInputStream(ageAnalaysPhotePath);

//                   InputStream inputStream1 = getContentResolver().openInputStream(savedUri);


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
                navigateToWithFlag(HomeActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            }else{
                showToastSync("人脸认证失败");
                Intent intent = new Intent(AgeAnalaysActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };

}