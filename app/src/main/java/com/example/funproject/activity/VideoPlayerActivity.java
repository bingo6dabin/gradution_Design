package com.example.funproject.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funproject.R;

public class VideoPlayerActivity extends AppCompatActivity {
    private VideoView mVideoView;

    //自带视频播放控制器
    MediaController mMediaController;
    private  String myVideoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置界面无顶部标识栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_player);

        initView();
        initData();
    }
    public void initView() {

        mVideoView = findViewById(R.id.myVideo);
        mVideoView = new VideoView(this);
        mVideoView = (VideoView) findViewById(R.id.myVideo);
        mMediaController = new MediaController(this);
    }

    public void initData() {
        myVideoUrl = getIntent().getStringExtra("VideoUrl");
//        String uri = "android.resource://" + getPackageName() + "/" + R.raw.basketball;  //本地
       String uri = myVideoUrl ; //网络
        //设置播放资源
        mVideoView.setVideoURI(Uri.parse(uri));
        //videoVIew和视频播放控制器绑定
        mMediaController.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(mMediaController);
        mVideoView.start();
    }
}