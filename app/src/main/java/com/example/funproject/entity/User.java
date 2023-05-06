package com.example.funproject.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String   uid;
    private String     username;
    private String  password;
    private String      headImage;
    private String  createTime;
    public User(){
    }
    public  User(String uid,String username,String password,String headImage,String createTime){
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.headImage = headImage;
        this.createTime = createTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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
