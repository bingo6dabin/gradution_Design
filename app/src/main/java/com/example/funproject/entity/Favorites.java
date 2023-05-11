package com.example.funproject.entity;

import java.io.Serializable;

public class Favorites implements Serializable {
    private  Integer id;
    private  Integer uid;
    private  Integer vid;
    public  Favorites(){

    }
    public Favorites(Integer id, Integer uid, Integer vid) {
        this.id = id;
        this.uid = uid;
        this.vid = vid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

}
