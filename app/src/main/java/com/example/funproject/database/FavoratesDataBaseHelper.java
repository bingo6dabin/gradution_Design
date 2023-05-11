package com.example.funproject.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.funproject.entity.Favorites;
import com.example.funproject.entity.User;

import java.util.ArrayList;
import java.util.List;

public class FavoratesDataBaseHelper extends  funProDataBaseHelper {
    private  static  final  String DB_NAME = "funPro.db";
    private  static  final  int DB_Version =1;
    private  static  FavoratesDataBaseHelper mDataBaseHelper = null;
    private SQLiteDatabase mRDB = null;
    private  SQLiteDatabase mWDB = null;
    public FavoratesDataBaseHelper(@Nullable Context context) {
        super(context);
    }
    //单例模式获取helper对象
    public static  FavoratesDataBaseHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new FavoratesDataBaseHelper(context);
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
        super.onCreate(sqLiteDatabase);

    }
    public  long insertToFavorates(Favorites favorites){
        ContentValues values = new ContentValues();
        values.put("_uid",favorites.getUid());
        values.put("_vid",favorites.getVid());
        return mWDB.insert("favorite",null,values);
    }
    @SuppressLint("Range")
    public List<Favorites> queryByUid(User  user){
//        Cursor cursor = mRDB.query("user",null,null,null,null,null,null);
        List<Favorites> mFavorates = new ArrayList<>();
        Cursor cursor = mWDB.query("favorite",null,"_uid=?",new String[]{String.valueOf(user.getUid())},null,null,null);
        while(cursor.moveToNext()){
            if(cursor.getInt(1)==user.getUid()) {
                Favorites tempFav = new Favorites();
                tempFav.setId(cursor.getInt(0));
                tempFav.setUid(cursor.getInt(1));
                tempFav.setVid(cursor.getInt(2));
                mFavorates.add(tempFav);
            }
        }
        return mFavorates;
    }
    public Boolean queryByVid(Favorites  favorites){
//        Cursor cursor = mRDB.query("user",null,null,null,null,null,null);
        Cursor cursor = mWDB.query("favorite",null,"_vid=?",new String[]{String.valueOf(favorites.getVid())},null,null,null);
        while(cursor.moveToNext()){
            if(cursor.getInt(1)==favorites.getUid()) {
              return true;
            }
        }
        return false;
    }
    public  long deleteByFavorites(Favorites favorites ){
        return mWDB.delete("favorite","_uid=? And _vid=?",new String[]{String.valueOf(favorites.getUid()),String.valueOf(favorites.getVid())});
    }
}
