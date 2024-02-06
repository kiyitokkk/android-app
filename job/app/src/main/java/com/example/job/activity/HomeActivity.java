package com.example.job.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.job.R;
import com.example.job.adapter.MyPagerAdapter;
import com.example.job.entity.TabEntity;
import com.example.job.fragment.HomeFragment;
import com.example.job.fragment.MessageFragment;
import com.example.job.fragment.MineFragment;
import com.example.job.manager.FloatingWindowManager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import android.os.Bundle;

import java.util.ArrayList;

public  class HomeActivity extends BaseActivity {
    private String[] mTitles = {"发现", "发布",  "我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.search_, R.mipmap.release,
            R.mipmap.user};
    private int[] mIconSelectIds = {
            R.mipmap.search_1, R.mipmap.release_select,
            R.mipmap.user_select};
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;
    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void intview() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.CommonTabLayout);
    }

    @Override
    protected void initData() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(MessageFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);

        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
//        预先加载防止报错闪退
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments));
    }





}