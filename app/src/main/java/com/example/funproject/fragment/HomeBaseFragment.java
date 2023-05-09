package com.example.funproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.funproject.R;
import com.example.funproject.adapter.VideoListAdapter;
import com.example.funproject.dataSourse.Analysis;
import com.example.funproject.dedigned_class.ModifyClass.GridSpacingItemDecoration;
import com.example.funproject.entity.Video;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeBaseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isGetData = false;
    private  int videoNum=0;
    private volatile List<Video> mVideoes = new ArrayList<>();
    private VideoListAdapter mVideoListAdapter;
    private String categoryId;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeBaseFragment() {
        // Required empty public constructor
    }
    public  HomeBaseFragment(String categoryId){
        this.categoryId = categoryId;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeBaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeBaseFragment newInstance(String param1, String param2) {
        HomeBaseFragment fragment = new HomeBaseFragment();
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

        View view = inflater.inflate(R.layout.fragment_games, container, false);
        recyclerView = view.findViewById(R.id.ry_games);

        mVideoListAdapter = new VideoListAdapter(mVideoes,0);
        recyclerView.setAdapter(mVideoListAdapter);

        @SuppressLint("WrongConstant") GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        int spanCount = 2; // 2 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
        return view;
    }


    private void initialData() {

    }
    @Override
    //销毁
    public void onDestroy() {
        super.onDestroy();
    }

}