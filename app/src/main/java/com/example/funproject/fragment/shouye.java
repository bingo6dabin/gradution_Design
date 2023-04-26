package com.example.funproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.funproject.R;
import com.example.funproject.adapter.MyFragmentStVpTitleAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link shouye#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shouye extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> mFragmentList;
    private MyFragmentStVpTitleAdapter mstptitleAdapter;
    private  List<String> mTitleList;
    public shouye() {
        // Required empty public constructor
    }

    public static shouye newInstance(String param1, String param2) {
        shouye fragment = new shouye();
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
        View shouyeView = inflater.inflate(R.layout.fragment_shouye, container, false);

        return shouyeView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      mViewPager =  view.findViewById(R.id.home_videoVP);
        mTabLayout =view.findViewById(R.id.tab_layout);
        initData();
        mstptitleAdapter = new MyFragmentStVpTitleAdapter(getChildFragmentManager(),mFragmentList,mTitleList);
        mViewPager.setAdapter(mstptitleAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mFragmentList = new ArrayList<>();

        MyFragment1 fragment1 = new MyFragment1();
        MyFragment1 fragment2 = new MyFragment1();
        MyFragment1 fragment3 = new MyFragment1();
        MyFragment1 fragment4 = new MyFragment1();
        MyFragment1 fragment5 = new MyFragment1();

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
        mFragmentList.add(fragment4);
        mFragmentList.add(fragment5);

        mTitleList = new ArrayList<>();

        mTitleList.add("推荐");
        mTitleList.add("关注");
        mTitleList.add("好看");
        mTitleList.add("时政");
        mTitleList.add("汽车");
    }
}