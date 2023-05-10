package com.example.funproject.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer   uid;
    private String     username;
    private String  password;
    private String      headImage;
    private Integer   favoratesNum;
    private  Integer collectesNum;
    private  Integer commentNum;
    private  Integer shareNum;
    private  String introduce;
    private String  createTime;


    public User(){
    }
    public  User(Integer uid, String username, String password, String headImage, Integer favoratesNum, Integer collectesNum, Integer commentNum, Integer shareNum, String createTime, String introduce){
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.headImage = headImage;
        this.favoratesNum = favoratesNum;
        this.collectesNum = collectesNum;
        this.commentNum = commentNum;
        this.shareNum = shareNum;
        this.createTime = createTime;
        this.introduce = introduce;
    }

    public Integer getFavoratesNum() {
        return favoratesNum;
    }

    public void setFavoratesNum(Integer favoratesNum) {
        this.favoratesNum = favoratesNum;
    }

    public Integer getCollectesNum() {
        return collectesNum;
    }

    public void setCollectesNum(Integer collectesNum) {
        this.collectesNum = collectesNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}
