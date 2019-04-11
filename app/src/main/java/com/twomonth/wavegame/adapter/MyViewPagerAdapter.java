package com.twomonth.wavegame.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<Fragment> list;
    List<String> titleList;
    public MyViewPagerAdapter(Context context, FragmentManager fm, ArrayList<Fragment> list,List<String> titleList) {
        super(fm);
        this.context = context;
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
