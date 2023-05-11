package com.example.funproject.entity;

import java.io.Serializable;

//视频类应当包括视频名称，视频id以及视频url,视频图片url
public class Video implements Serializable {
    private Integer Vid;
    private  Integer  uid;
    private  String authorName;
    private  String VideoName;
    private  String typename;
    private  String VideoimageUrl;
    private  String VideoUrl;
   private String description;
   private  String creatTime;
    private Integer favoritesCount;
    private Integer   CollectCount;
    private  Integer commentCount;
    private Integer play;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Video() {
    }

    public Video(Integer vid, Integer uid, String authorName, String videoName, String typename,
                 String videoimageUrl, String videoUrl, String description,
                 Integer favoritesCount, Integer collectCount, Integer play, String creatTime, Integer commentCount) {
        this.Vid = vid;
        this.uid = uid;
        this.authorName = authorName;
        VideoName = videoName;
        this.typename = typename;
        VideoimageUrl = videoimageUrl;
        VideoUrl = videoUrl;
        this.description = description;
        this.creatTime = creatTime;
        this.favoritesCount = favoritesCount;
        CollectCount = collectCount;
       this.play = play;
        this.commentCount = commentCount;
    }

    public Integer getPlay() {
        return play;
    }

    public void setPlay(Integer play) {
        this.play = play;
    }

    public Integer getVid() {
        return Vid;
    }

    public void setVid(Integer vid) {
        this.Vid = vid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getVideoimageUrl() {
        return VideoimageUrl;
    }

    public void setVideoimageUrl(String videoimageUrl) {
        VideoimageUrl = videoimageUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Integer getCollectCount() {
        return CollectCount;
    }

    public void setCollectCount(Integer collectCount) {
        CollectCount = collectCount;
    }


}
