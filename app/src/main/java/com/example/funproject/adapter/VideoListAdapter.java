package com.example.funproject.adapter;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.funproject.R;
import com.example.funproject.activity.HomeActivity;
import com.example.funproject.activity.VideoPlayerActivity;
import com.example.funproject.entity.Video;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private List<Video> mVideoes;
    String mVideoImageUrl;
    //获取activity的video数据
    public VideoListAdapter(List<Video> mVideoes){
        this.mVideoes = mVideoes;
    }
    public  VideoListAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        final ViewHolder holder =new ViewHolder(v);
        //点击视频图片跳转至视频播放界面
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前碎片View的对象Video在mVideos的位置
                int position = holder.getAdapterPosition();
                Intent intent =new Intent(v.getContext(), VideoPlayerActivity.class);
                intent.putExtra("VideoUrl",mVideoes.get(position).getVideoUrl());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdapter.ViewHolder holder, int position) {
        Video mVideo =mVideoes.get(position);
        //设置imageView视频的封面图片方法一
//        mVideoImageUrl=mVideo.getVideoimageUrl();
//        loadImage(holder.imageView);
//        方法二使用现有的Glide加载图片速度快
        System.out.println("图片资源地址");
        System.out.println(mVideo.getVideoimageUrl());
//        Glide.with(holder.videoView.getContext()).load(Uri.parse("https://www.jazzradio.fr/media/radio/blues.png"))
//                .into(holder.imageView);
        Glide.with(holder.videoView.getContext()).load(Uri.parse(mVideo.getVideoimageUrl()))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mVideoes.size();
    }

    //定义viewHold
    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        View videoView;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.video_iv);
            videoView = itemView;
        }
    }
}
