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
import com.example.funproject.database.VideoDataBaseHelper;
import com.example.funproject.entity.Video;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private List<Video> mVideoes;
    private  int preViewAccount;
    String mVideoImageUrl;
    private OnLikeClickListener mListener;
    private  OnCollectClickListener mCollectListener;
     //获取activity的video数据
    public VideoListAdapter(List<Video> mVideoes,int preViewAccount){
        this.mVideoes = mVideoes;
        this.preViewAccount =preViewAccount;
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
        Video mvideo = mVideoes.get(position);
           Video mVideo =mVideoes.get(position);
           //设置imageView视频的封面图片方法一
//        mVideoImageUrl=mVideo.getVideoimageUrl();
//        loadImage(holder.imageView);
//        方法二使用现有的Glide加载图片速度快
//        Glide.with(holder.videoView.getContext()).load(Uri.parse("https://www.jazzradio.fr/media/radio/blues.png"))
//                .into(holder.imageView);
           Glide.with(holder.videoView.getContext()).load(Uri.parse(mVideo.getVideoimageUrl()))
                   .into(holder.imageView);
           holder.favorates.setText(String.valueOf(mVideo.getFavoritesCount()));
           holder.comments.setText(String.valueOf(mVideo.getCommentCount()));
           holder.collects.setText(String.valueOf(mVideo.getCollectCount()));
        // 设置点赞按钮的点击事件监听器
        holder.favoratesIg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前碎片View的对象Video在mVideos的位置
                int position = holder.getAdapterPosition();
                // 获取当前 item 对应的数据
                Video itemVideo = mVideoes.get(position);
                notifyItemChanged(position);
                // 通过接口回调通知 Fragment 更新数据
                if (mListener != null) {
                    mListener.onLikeClick(position);
                }
            }
        });
        //设置收藏按钮的点击事件监听
        holder.collectsIg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前碎片View的对象Video在mVideos的位置
                int position = holder.getAdapterPosition();
                // 获取当前 item 对应的数据
                Video itemVideo = mVideoes.get(position);
                notifyItemChanged(position);
                // 通过接口回调通知 Fragment 更新数据
                if (mCollectListener != null) {
                    mCollectListener.onCollectClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoes.size();
    }

    //定义viewHold
    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView favorates;
        TextView collects;
        TextView comments;
        ImageView favoratesIg;
        ImageView collectsIg;
        ImageView commentsIg;
        ImageView imageView;
        View videoView;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.video_iv);
            favorates = itemView.findViewById(R.id.favorates);
            collects = itemView.findViewById(R.id.collect);
            comments = itemView.findViewById(R.id.commentVideo);
            favoratesIg = itemView.findViewById(R.id.img_like);
            collectsIg = itemView.findViewById(R.id.img_collectVideo);
            commentsIg = itemView.findViewById(R.id.img_comment);
            videoView = itemView;
        }
    }
    public interface OnLikeClickListener {
        void onLikeClick(int position);
    }

    public void setOnLikeClickListener(OnLikeClickListener listener) {
        mListener = listener;
    }

    public interface OnCollectClickListener {
        void onCollectClick(int position);
    }

    public void setOnCollectClickListener(OnCollectClickListener listener) {
        mCollectListener = listener;
    }

}
