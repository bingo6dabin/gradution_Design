package com.example.funproject.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.funproject.MainActivity;
import com.example.funproject.R;
import com.example.funproject.activity.CollectActivity;
import com.example.funproject.activity.FavoratesActivity;
import com.example.funproject.activity.HomeActivity;
import com.example.funproject.activity.LoginActivity;
import com.example.funproject.activity.SettingActivity;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.database.VideoDataBaseHelper;
import com.example.funproject.entity.User;
import com.example.funproject.util.PermissionUtil;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  User user;
    private RelativeLayout rl_logout;
    private  RelativeLayout rl_changeInfo;
  private ImageView myHeader;
  private TextView mName;
  private  TextView myIntroduce;
  private LinearLayout mFavoriteLL;
  private  LinearLayout mCollectLL;
  private TextView favoratesNum;
    private TextView collectNum;
    private TextView commentNum;
    private TextView shareNum;
    private SharedPreferences sharedPreferences;
    private UserDataBaseHelper mUserHelper;
    private VideoDataBaseHelper mVideoHelper;
    private  User mUser;
    private SharedPreferences preferences;
    private static final String[] PERMISSIONS_EXTERNAL_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE_STORAGE = 1;
    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        //获得数据库帮助器实例
        mUserHelper = UserDataBaseHelper.getInstance(getActivity());
        mVideoHelper= VideoDataBaseHelper.getInstance(getActivity());
        //打开数据库读写连接
        mUserHelper.openReadLink();
        mUserHelper.openWriteLink();
        mVideoHelper.openReadLink();
        mVideoHelper.openWriteLink();
        preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户，用于后续查询
        if(preferences.contains("account")){
            String tempName = preferences.getString("account",null);
            mUser= mUserHelper.querryByUsername(tempName);
        }
        try {
            initData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogout", true);
                editor.commit();
                Intent in = new Intent(getActivity(), MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });
        rl_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), SettingActivity.class);
                startActivity(in);
            }
        });
        myHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    initData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        mFavoriteLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FavoratesActivity.class);
                startActivity(intent);
            }
        });
        mCollectLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initData() throws FileNotFoundException {
        sharedPreferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户的所有信息加载到“我的界面”
     if(sharedPreferences.contains("account")){
         String tempName = sharedPreferences.getString("account",null);
         user= mUserHelper.querryByUsername(tempName);
         setHeader();
         mName.setText(user.getUsername());
         myIntroduce.setText(user.getIntroduce());
         favoratesNum.setText(user.getFavoratesNum().toString());
         commentNum.setText(user.getCommentNum().toString());
         collectNum.setText(user.getCollectesNum().toString());
         shareNum.setText(user.getShareNum().toString());
     }
    }
    private  void setHeader() throws FileNotFoundException {
        Context context = getActivity().getApplicationContext();
        //获取访问权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 已经被授予该权限，可以访问外部存储器中的文件
            Glide.with(getActivity()).load(Uri.parse(user.getHeadImage())).into(myHeader);
        } else {
            // 未被授予该权限，需要申请权限
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_EXTERNAL_STORAGE, REQUEST_CODE_STORAGE);

            PermissionUtil.checkPermission(getActivity(),PERMISSIONS_EXTERNAL_STORAGE,REQUEST_CODE_STORAGE);
            // 已经被授予该权限，可以访问外部存储器中的文件
//            InputStream inputStream = resolver.openInputStream(Uri.parse(user.getHeadImage()));
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Glide.with(getActivity()).load(Uri.parse(user.getHeadImage())).into(myHeader);
        }
    }

    private void initView(View view) {
        rl_logout = view.findViewById(R.id.rl_logout);
        rl_changeInfo =view.findViewById(R.id.rl_changeMessage);
        myHeader =view.findViewById(R.id.iv_header);
        mName = view.findViewById(R.id.tv_name);
        myIntroduce = view.findViewById(R.id.tv_introduce);
        favoratesNum = view.findViewById(R.id.tv_favoratsNum);
        collectNum = view.findViewById(R.id.tv_collectNum);
        commentNum = view.findViewById(R.id.tv_commentNum);
        shareNum = view.findViewById(R.id.tv_shareNum);
        mFavoriteLL = view.findViewById(R.id.ll_JumpToFavorite);
        mCollectLL = view.findViewById(R.id.ll_jumpToCollect);
    }


}