package com.example.funproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.funproject.R;
import com.example.funproject.adapter.VideoListAdapter;
import com.example.funproject.database.CollectDataBaseHelper;
import com.example.funproject.database.FavoratesDataBaseHelper;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.database.VideoDataBaseHelper;
import com.example.funproject.dedigned_class.ModifyClass.GridSpacingItemDecoration;
import com.example.funproject.entity.Collects;
import com.example.funproject.entity.Favorites;
import com.example.funproject.entity.User;
import com.example.funproject.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoDataBaseHelper mVideoHelper;
    private CollectDataBaseHelper mCollectHelper;
    private UserDataBaseHelper mUserHelper;
    private volatile List<Video> mVideoes = new ArrayList<>();
    private VideoListAdapter mVideoListAdapter;
    private SharedPreferences preferences;
    private User mUser;
    private  int videoNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
        //获得数据库帮助器实例
        mVideoHelper = VideoDataBaseHelper.getInstance(this);
        mCollectHelper = CollectDataBaseHelper.getInstance(this);
        mUserHelper = UserDataBaseHelper.getInstance(this);
        //打开数据库读写连接
        mVideoHelper.openReadLink();
        mVideoHelper.openWriteLink();
        mCollectHelper.openReadLink();
        mCollectHelper.openWriteLink();
        mUserHelper.openReadLink();
        mUserHelper.openWriteLink();
        initData();
    }
    private void initData() {
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户，用于后续查询
        if(preferences.contains("account")){
            String tempName = preferences.getString("account",null);
            mUser= mUserHelper.querryByUsername(tempName);
        }
        //       从数据库中获取favorite
        List<Collects> mCollects = mCollectHelper.queryByUid(mUser);
//        //从数据库中获取Video
        for(int i=0;i<mCollects.size();i++){
            mVideoes.add(mVideoHelper.queryByVId(mCollects.get(i).getVid()));
        }

        mVideoListAdapter = new VideoListAdapter(mVideoes,videoNum);
        recyclerView.setAdapter(mVideoListAdapter);
        @SuppressLint("WrongConstant") GridLayoutManager mLayoutManager = new GridLayoutManager(this,1, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        int spanCount = 1; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
    }

    private void initView() {
        recyclerView = findViewById(R.id.ry_myCollect);
        //设置布局显示分隔线
        DividerItemDecoration mDivider = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
    }
}