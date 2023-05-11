package com.example.funproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.funproject.entity.User;
import com.example.funproject.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoDataBaseHelper extends  funProDataBaseHelper{
    private  static  VideoDataBaseHelper videoDataBaseHelper = null;
    private  SQLiteDatabase mRDB = null;
    private  SQLiteDatabase mWDB = null;

    public VideoDataBaseHelper(@Nullable Context context) {
        super(context);
    }
    //单例模式获取helper对象
    public static  VideoDataBaseHelper getInstance(Context context){
        if(videoDataBaseHelper == null){
            videoDataBaseHelper = new VideoDataBaseHelper(context);
        }
        return videoDataBaseHelper;
    }
    //打开数据库读连接
    public SQLiteDatabase openReadLink(){
        if(mRDB==null||!mRDB.isOpen()){
            mRDB = videoDataBaseHelper.getReadableDatabase();
        }
        return mRDB;
    }
    //打开数据库写连接
    public SQLiteDatabase openWriteLink(){
        if(mWDB==null||!mWDB.isOpen()){
            mWDB = videoDataBaseHelper.getWritableDatabase();
        }
        return mWDB;
    }
    //关闭数据库连接
    public  void close(){
        if(mRDB!=null&&mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
        if(mWDB!=null&&mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        super.onCreate(sqLiteDatabase);

    }
    public  long insertToVideo(Video video){
        ContentValues values = new ContentValues();
        values.put("_uid",video.getUid());
        values.put("authorName",video.getAuthorName());
        values.put("VideoName",video.getVideoName());
        values.put("typename",video.getTypename());
        values.put("VideoImageUrl",video.getVideoimageUrl());
        values.put("VideoUrl",video.getVideoUrl());
        values.put("description",video.getDescription());
        values.put("_favoritesCount",video.getFavoritesCount());
        values.put("_CollectCount",video.getCollectCount());
        values.put("_play",video.getPlay());
        values.put("createTime",video.getCreatTime());
        return mWDB.insert("video",null,values);
    }
    public  long updataToVideo(Video video){
        ContentValues values = new ContentValues();
        values.put("_uid",video.getUid());
        values.put("authorName",video.getAuthorName());
        values.put("VideoName",video.getVideoName());
        values.put("typename",video.getTypename());
        values.put("VideoImageUrl",video.getVideoimageUrl());
        values.put("VideoUrl",video.getVideoUrl());
        values.put("description",video.getDescription());
        values.put("_favoritesCount",video.getFavoritesCount());
        values.put("_CollectCount",video.getCollectCount());
        values.put("_play",video.getPlay());
        values.put("createTime",video.getCreatTime());
        return  mWDB.update("video",values,"_id=?",new String[]{video.getVid().toString()});
    }
    public Video queryById(Integer vid){
        Cursor cursor = mRDB.query("video",null,"_id=?",new String[]{String.valueOf(vid)},null,null,null);
        Video tempVideo = new Video();
       while(cursor.moveToNext()){
           tempVideo.setVid(cursor.getInt(0));
           tempVideo.setUid(cursor.getInt(1));
           tempVideo.setAuthorName(cursor.getString(2));
           tempVideo.setVideoimageUrl(cursor.getString(5));
           tempVideo.setTypename(cursor.getString(4));
           tempVideo.setVideoUrl(cursor.getString(6));
           tempVideo.setVideoName(cursor.getString(3));
           tempVideo.setDescription(cursor.getString(7));
           tempVideo.setFavoritesCount(cursor.getInt(8));
           tempVideo.setCollectCount(cursor.getInt(9));
           tempVideo.setPlay(cursor.getInt(10));
           tempVideo.setCreatTime(cursor.getString(11));
           tempVideo.setCommentCount(0);
       }
       return tempVideo;
    }
    public Video queryByVideoImageUrl(String imageUrl){
        Cursor cursor = mRDB.query("video",null,"VideoImageUrl=?",new String[]{imageUrl},null,null,null);
        Video tempVideo = new Video();
        while(cursor.moveToNext()){
            tempVideo.setVid(cursor.getInt(0));
            tempVideo.setUid(cursor.getInt(1));
            tempVideo.setAuthorName(cursor.getString(2));
            tempVideo.setVideoimageUrl(cursor.getString(5));
            tempVideo.setTypename(cursor.getString(4));
            tempVideo.setVideoUrl(cursor.getString(6));
            tempVideo.setVideoName(cursor.getString(3));
            tempVideo.setDescription(cursor.getString(7));
            tempVideo.setFavoritesCount(cursor.getInt(8));
            tempVideo.setCollectCount(cursor.getInt(9));
            tempVideo.setPlay(cursor.getInt(10));
            tempVideo.setCreatTime(cursor.getString(11));
            tempVideo.setCommentCount(0);
        }
        return tempVideo;
    }
    public Video queryByVId(int vid){
        Cursor cursor = mRDB.query("video",null,"_id=?",new String[]{String.valueOf(vid)},null,null,null);
        Video tempVideo = new Video();
        while(cursor.moveToNext()){
            tempVideo.setVid(cursor.getInt(0));
            tempVideo.setUid(cursor.getInt(1));
            tempVideo.setAuthorName(cursor.getString(2));
            tempVideo.setVideoimageUrl(cursor.getString(5));
            tempVideo.setTypename(cursor.getString(4));
            tempVideo.setVideoUrl(cursor.getString(6));
            tempVideo.setVideoName(cursor.getString(3));
            tempVideo.setDescription(cursor.getString(7));
            tempVideo.setFavoritesCount(cursor.getInt(8));
            tempVideo.setCollectCount(cursor.getInt(9));
            tempVideo.setPlay(cursor.getInt(10));
            tempVideo.setCreatTime(cursor.getString(11));
            tempVideo.setCommentCount(0);
        }
        return tempVideo;
    }

    public  Boolean ExitByVideoImageUrl(String imageUrl){
        Cursor cursor = mRDB.query("video",null,"VideoImageUrl=?",new String[]{imageUrl},null,null,null);
        while(cursor.moveToNext()){
            if(cursor.getString(5).equals(imageUrl))
                return true;
        }
        return false;
    }
}
