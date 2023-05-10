package com.example.funproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.funproject.MainActivity;
import com.example.funproject.R;
import com.example.funproject.activity.HomeActivity;
import com.example.funproject.activity.LoginActivity;
import com.example.funproject.activity.SettingActivity;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.entity.User;

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
    private RelativeLayout rl_logout;
    private  RelativeLayout rl_changeInfo;
  private ImageView myHeader;
  private TextView mName;
  private  TextView myIntroduce;
  private TextView favoratesNum;
    private TextView collectNum;
    private TextView commentNum;
    private TextView shareNum;
    private SharedPreferences sharedPreferences;
    private UserDataBaseHelper mUserHelper;
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
            //获得数据库帮助器实例
            mUserHelper = UserDataBaseHelper.getInstance(getActivity());
            //打开数据库读写连接
            mUserHelper.openReadLink();
            mUserHelper.openWriteLink();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        initData();
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
                initData();
            }
        });
        return view;
    }
    private void initData() {
        sharedPreferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户的所有信息加载到“我的界面”
     if(sharedPreferences.contains("account")){
         String tempName = sharedPreferences.getString("account",null);
        User user= mUserHelper.querryByUsername(tempName);

         mName.setText(user.getUsername());

//         myHeader.setImageURI();
         myIntroduce.setText(user.getIntroduce());
         favoratesNum.setText(user.getFavoratesNum().toString());
         commentNum.setText(user.getCommentNum().toString());
         collectNum.setText(user.getCollectesNum().toString());
         shareNum.setText(user.getShareNum().toString());
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
    }


}