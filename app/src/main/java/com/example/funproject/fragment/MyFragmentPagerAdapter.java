package com.example.funproject.fragment;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.funproject.activity.HomeActivity;

/**
 * Created by Jay on 2015/8/31 0031.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
//    private MyFragment1 myFragment1 = null;
//    private MyFragment2 myFragment2 = null;
//    private MyFragment3 myFragment3 = null;
    private MyFragment4 myFragment4 = null;
    private  shouye shouyeFragment = null;
    private  MessageFragment messageFragment =null;
    private  MyFragment myFragment = null;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        shouyeFragment = new shouye();
        messageFragment = new MessageFragment();
        myFragment = new MyFragment();
//        myFragment1 = new MyFragment1();
//        myFragment2 = new MyFragment2();
//        myFragment3 = new MyFragment3();
        myFragment4 = new MyFragment4();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case HomeActivity.PAGE_ONE:
                fragment = shouyeFragment;
                break;
            case HomeActivity.PAGE_TWO:
                fragment = messageFragment;
                break;
            case HomeActivity.PAGE_THREE:
                fragment = myFragment;
                break;
            case HomeActivity.PAGE_FOUR:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }


}

