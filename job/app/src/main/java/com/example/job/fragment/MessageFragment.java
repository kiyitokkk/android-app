package com.example.job.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.job.R;
import com.example.job.activity.LoginActivity;
import com.example.job.adapter.MycollectAdapter;
import com.example.job.adapter.VideoAdapter;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.MyCollectResponse;
import com.example.job.entity.VideoEntity;
import com.example.job.entity.VideoListResponse;
import com.example.job.listener.OnItemChildClickListener;
import com.example.job.util.StringUtils;
import com.example.job.util.Tag;
import com.example.job.util.Utils;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.player.VideoView;

public class MessageFragment extends BaseFragment{
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();

        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_messages;
    }

    @Override
    protected void intview() {

    }

    @Override
    protected void initData() {
        addFragment();


    }
    private void addFragment() {
        // 创建要添加的Fragment实例
        MessageRef messageRef = new MessageRef();
        // 获取FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // 开始事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 添加Fragment到容器中
        fragmentTransaction.add(R.id.messagemain, messageRef);
        // 提交事务
        fragmentTransaction.commit();
    }





}