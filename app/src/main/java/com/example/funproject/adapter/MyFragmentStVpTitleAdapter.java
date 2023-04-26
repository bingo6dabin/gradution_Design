package com.example.funproject.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyFragmentStVpTitleAdapter extends FragmentStatePagerAdapter {
private  List<Fragment> mFragmnetList;
private List<String> mtitleList;
    public MyFragmentStVpTitleAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
        super(fm);
        this.mFragmnetList=fragmentList;
        this.mtitleList = titleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmnetList ==null?null: mFragmnetList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmnetList ==null?null:mFragmnetList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mtitleList ==null?" ":mtitleList.get(position);
    }
}
