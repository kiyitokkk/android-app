package com.example.job.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitle;
    private ArrayList<Fragment> mFragments;
    public MyPagerAdapter(FragmentManager fm, String[] mTitle, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mTitle = mTitle;
        this.mFragments =mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
