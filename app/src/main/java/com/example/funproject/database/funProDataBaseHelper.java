package com.example.funproject.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.funproject.entity.User;

import java.util.ArrayList;
import java.util.List;

public class funProDataBaseHelper extends SQLiteOpenHelper {
    private  static  final  String DB_NAME = "funPro.db";
    private  static  final  int DB_Version =1;
    private  static  funProDataBaseHelper mDataBaseHelper = null;
    private  SQLiteDatabase mRDB = null;
    private  SQLiteDatabase mWDB = null;
    public funProDataBaseHelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_Version);
    }
//单例模式获取helper对象
    public static  funProDataBaseHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new funProDataBaseHelper(context);
        }
        return mDataBaseHelper;
    }
    //打开数据库读连接
    public SQLiteDatabase openReadLink(){
        if(mRDB==null||!mRDB.isOpen()){
            mRDB = mDataBaseHelper.getReadableDatabase();
        }
        return mRDB;
    }
    //打开数据库写连接
    public SQLiteDatabase openWriteLink(){
        if(mWDB==null||!mWDB.isOpen()){
            mWDB = mDataBaseHelper.getWritableDatabase();
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
//用户表
        String createUser = "CREATE TABLE IF NOT EXISTS user (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "username VARCHAR NOT NULL,"+
                "introduce VARCHAR NOT NULL,"+
                "password VARCHAR NOT NULL,"+
                "headImage VARCHAR NOT NULL,"+
                "_favoratesNum Integer NOT NULL,"+
                "_collectesNum Integer NOT NULL,"+
                "_commentNum Integer NOT NULL,"+
                "_shareNum Integer NOT NULL,"+
                "createTime VARCHAR NOT NULL);";
        sqLiteDatabase.execSQL(createUser);
//视频表
        String createVideo = "CREATE TABLE IF NOT EXISTS video (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_uid INTEGER  NOT NULL,"+
                "authorName VARCHAR NOT NULL,"+
                "VideoName VARCHAR NOT NULL,"+
                "typename VARCHAR NOT NULL,"+
                "VideoImageUrl VARCHAR NOT NULL,"+
                "VideoUrl VARCHAR NOT NULL,"+
                "description VARCHAR NOT NULL,"+
                "_favoritesCount Integer NOT NULL,"+
                "_CollectCount Integer NOT NULL,"+
                "_play Integer NOT NULL,"+
                "createTime VARCHAR NOT NULL);";
        sqLiteDatabase.execSQL(createVideo);
//        点赞表
        String createFavorate = "CREATE TABLE IF NOT EXISTS favorite (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_uid INTEGER  NOT NULL,"+
                "_vid INTEGER  NOT NULL);";
        sqLiteDatabase.execSQL(createFavorate);
//        收藏表
        String createCollect = "CREATE TABLE IF NOT EXISTS collect (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_uid INTEGER  NOT NULL,"+
                "_vid INTEGER  NOT NULL);";
        sqLiteDatabase.execSQL(createCollect);
//        分享表
        String createShare = "CREATE TABLE IF NOT EXISTS share (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_uid INTEGER  NOT NULL,"+
                "_sharedId INTEGER  NOT NULL);";
        sqLiteDatabase.execSQL(createShare);
//        评论表
        String createComment = "CREATE TABLE IF NOT EXISTS comment (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_uid INTEGER  NOT NULL,"+
                "_vid INTEGER  NOT NULL,"+
                "commentInfo VARCHAR  NOT NULL);";
        sqLiteDatabase.execSQL(createComment);
//        关注表
        String createFollow = "CREATE TABLE IF NOT EXISTS follow (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_uid INTEGER  NOT NULL,"+
                "_followId INTEGER  NOT NULL);";
        sqLiteDatabase.execSQL(createFollow);
//        作品表
        String createWork = "CREATE TABLE IF NOT EXISTS work (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "_vid INTEGER  NOT NULL);";
        sqLiteDatabase.execSQL(createWork);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
