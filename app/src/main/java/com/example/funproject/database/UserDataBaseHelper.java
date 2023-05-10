package com.example.funproject.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.funproject.entity.User;

public class UserDataBaseHelper extends  funProDataBaseHelper{
    private  static  final  String DB_NAME = "funPro.db";
    private  static  final  int DB_Version =1;
    private  static  UserDataBaseHelper mDataBaseHelper = null;
    private  SQLiteDatabase mRDB = null;
    private  SQLiteDatabase mWDB = null;

    public UserDataBaseHelper(@Nullable Context context) {
        super(context);
    }
    //单例模式获取helper对象
    public static  UserDataBaseHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new UserDataBaseHelper(context);
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

    public  long insertToUser(User user){
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("introduce",user.getIntroduce());
        values.put("password",user.getPassword());
        values.put("headImage",user.getHeadImage());
        values.put("_favoratesNum",user.getFavoratesNum());
        values.put("_collectesNum",user.getCollectesNum());
        values.put("_commentNum",user.getCommentNum());
        values.put("_shareNum",user.getShareNum());
        values.put("createTime",user.getCreateTime());
        return mWDB.insert("user",null,values);
    }
    public long updataUser(User user){
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("introduce",user.getIntroduce());
        values.put("password",user.getPassword());
        values.put("headImage",user.getHeadImage());
        values.put("_favoratesNum",user.getFavoratesNum());
        values.put("_collectesNum",user.getCollectesNum());
        values.put("_commentNum",user.getCommentNum());
        values.put("_shareNum",user.getShareNum());
        values.put("createTime",user.getCreateTime());
       return  mWDB.update("user",values,"_id=?",new String[]{user.getUid().toString()});
    }
    @SuppressLint("Range")
    public boolean queryByUser(User  user){
//        Cursor cursor = mRDB.query("user",null,null,null,null,null,null);
        Cursor cursor = mWDB.query("user",null,"username=?",new String[]{user.getUsername()},null,null,null);
        while(cursor.moveToNext()){
            if(user.getPassword().equals(cursor.getString(cursor.getColumnIndex("password"))) ) {
                return true;
            }
        }
        return false;
    }

    public   User querryByUsername(String name){
        User user = new User();
        Cursor cursor = mRDB.query("user",null,"username=?",new String[]{name},null,null,null);
        while(cursor.moveToNext()){
            if(name.equals(cursor.getString(1))){
               user.setUid(cursor.getInt(0));
               user.setUsername(cursor.getString(1));
               user.setIntroduce(cursor.getString(2));
               user.setPassword(cursor.getString(3));
               user.setHeadImage(cursor.getString(4));
               user.setFavoratesNum(cursor.getInt(5));
               user.setCollectesNum(cursor.getInt(6));
               user.setCommentNum(cursor.getInt(7));
               user.setShareNum(cursor.getInt(8));
               user.setCreateTime(cursor.getString(9));
               break;
            }
        }
       return  user;
    }
}
