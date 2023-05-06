package com.example.funproject.entity;

import java.io.Serializable;

//视频类应当包括视频名称，视频id以及视频url,视频图片url
public class Video implements Serializable {
    private  String VideoName;
    private  int VideoId;
    private  String VideoimageUrl;
    private  String VideoUrl;

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public void setVideoId(int videoId) {
        VideoId = videoId;
    }

    public void setVideoimageUrl(String videoimageUrl) {
        VideoimageUrl = videoimageUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getVideoName(){
        return  VideoName;
    }
    public int getVideoId(){
        return  VideoId;
    }
    public String getVideoUrl(){
        return  VideoUrl;
    }
    public String getVideoimageUrl(){return VideoimageUrl;}

    public Video(String name, int  id, String videoimageUrl, String videoUrl){
        this.VideoId = id;
        this.VideoName = name;
        this.VideoUrl = videoUrl;
        this.VideoimageUrl =videoimageUrl;

    }
}
