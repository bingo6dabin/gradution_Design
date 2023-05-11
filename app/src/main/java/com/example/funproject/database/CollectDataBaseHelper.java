package com.example.funproject.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.funproject.entity.Collects;
import com.example.funproject.entity.Favorites;
import com.example.funproject.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CollectDataBaseHelper extends  funProDataBaseHelper {
    private  static  final  String DB_NAME = "funPro.db";
    private  static  final  int DB_Version =1;
    private  static  CollectDataBaseHelper mDataBaseHelper = null;
    private SQLiteDatabase mRDB = null;
    private  SQLiteDatabase mWDB = null;
    public CollectDataBaseHelper(@Nullable Context context) {
        super(context);
    }
    //单例模式获取helper对象
    public static  CollectDataBaseHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new CollectDataBaseHelper(context);
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
    public  long insertToCollect(Collects collects){
        ContentValues values = new ContentValues();
        values.put("_uid",collects.getUid());
        values.put("_vid",collects.getVid());
        return mWDB.insert("collect",null,values);
    }
    @SuppressLint("Range")
    public List<Collects> queryByUid(User  user){
//        Cursor cursor = mRDB.query("user",null,null,null,null,null,null);
        List<Collects> mCollect = new ArrayList<>();
        Cursor cursor = mWDB.query("collect",null,"_uid=?",new String[]{String.valueOf(user.getUid())},null,null,null);
        while(cursor.moveToNext()){
            if(cursor.getInt(1)==user.getUid()) {
                Collects tempCol = new Collects();
                tempCol.setId(cursor.getInt(0));
                tempCol.setUid(cursor.getInt(1));
                tempCol.setVid(cursor.getInt(2));
                mCollect.add(tempCol);
            }
        }
        return mCollect;
    }
    public Boolean queryByVid(Collects  collects){
//        Cursor cursor = mRDB.query("user",null,null,null,null,null,null);
        Cursor cursor = mWDB.query("collect",null,"_vid=?",new String[]{String.valueOf(collects.getVid())},null,null,null);
        while(cursor.moveToNext()){
            if(cursor.getInt(1)==collects.getUid()) {
                return true;
            }
        }
        return false;
    }

    public  long deleteByCollect(Collects  collects){
        return mWDB.delete("collect","_uid=? And _vid=?",new String[]{String.valueOf(collects.getUid()),String.valueOf(collects
                .getVid())});
    }
}
