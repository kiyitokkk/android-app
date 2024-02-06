package com.example.job.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.job.activity.BaseActivity;
import com.example.job.activity.SearchKeyActivity;
import com.example.job.adapter.VideoAdapter;
import com.example.job.entity.CategoryEntity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.job.activity.LoginActivity;
import com.example.job.api.*;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.job.R;
import com.example.job.adapter.HomePagerAdapter;
import com.example.job.entity.CategoryEntity;
import com.example.job.entity.VideoCategoryResponse;
import com.example.job.entity.VideoCategoryResponse;
import com.example.job.entity.VideoEntity;
import com.example.job.entity.VideoListResponse;
import com.example.job.util.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    private  ArrayList<Fragment> mFragments = new ArrayList<>();
    private  String[] mTitles;
    private SlidingTabLayout slidingTabLayout;
    private EditText editText;

    private ViewPager viewPager;
    private ImageView imageSearch;
    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void intview() {
        viewPager = mRootView.findViewById(R.id.fixedViewpager);
        editText = mRootView.findViewById(R.id.et_search);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
        imageSearch = mRootView.findViewById(R.id.search_video);
    }
    @Override
    protected void initData() {
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = editText.getText().toString();
                if (key.equals("")){
                    showToast("请输入需要搜索的关键字");
                }else {
                    jumpsearch(SearchKeyActivity.class, key);
                }
            }
        });
        getVideoCategoryList();
    }
    public void jumpsearch(Class cla, String parameter) {
        Intent intent = new Intent(getActivity(), cla);
        intent.putExtra("searchkeyword", parameter);
        startActivity(intent);
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    private void getVideoCategoryList(){
//        String token = getToken("token");
        HashMap<String,Object> params = new HashMap<>();
        Api.config(Apiconfig.VIDEO_CATEGORY_LIST, params).getRequest(getActivity(),new RequestCallback() {
            @Override
            public void onSucess(String res) {
                getActivity().runOnUiThread(() -> {
                    VideoCategoryResponse response = new Gson().fromJson(res, VideoCategoryResponse.class);
                    if (response != null && response.getCode() == 0){
                        List<CategoryEntity> list = response.getPage().getList();
                        if (list != null && list.size()  > 0){
                            mTitles = new String[list.size()];
                            for (int i = 0; i < list.size(); i++) {
                                mTitles[i]  =   list.get(i).getCategoryName();
                                mFragments.add(VideoFragment.newInstance(list.get(i).getCategoryId()));
                            }
                            viewPager.setOffscreenPageLimit(mFragments.size());
                            viewPager.setAdapter(new HomePagerAdapter(getFragmentManager(),mTitles,mFragments));
                            slidingTabLayout.setViewPager((viewPager));
                        }
                    }
                });
            }
            @Override
            public void onFailure(Exception err) {
            }
        });
    }
}