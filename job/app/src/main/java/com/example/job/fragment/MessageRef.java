package com.example.job.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.R;
import com.example.job.activity.LoginActivity;
import com.example.job.adapter.MessageAdapter;
import com.example.job.adapter.VideoAdapter;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
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

public class MessageRef extends BaseFragment implements OnItemChildClickListener{
    private static String TAG ;
    private int categoryId;
    private int pageNum = 1;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private MessageAdapter messageAdapter;
    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;
    private LinearLayoutManager linearLayoutManager;
    private List<VideoEntity> datas = new ArrayList<>();

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    messageAdapter.setDatas(datas);
                    messageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    public static MessageRef newInstance(int categoryId) {
        MessageRef fragment = new MessageRef();
        fragment.categoryId = categoryId;
        return fragment;
    }
    @Override
    protected int initLayout() {
        return R.layout.fragment_message_ref;
    }

    @Override
    protected void intview() {
        initVideoView();
        recyclerView = mRootView.findViewById(R.id.recyclerview_release);
        refreshLayout = mRootView.findViewById(R.id.refreshLayoutmessage);
    }



    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(getActivity());
        messageAdapter.setOnItemChildClickListener(this);
//        videoAdapter.se
        recyclerView.setAdapter(messageAdapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                pageNum = 1;
                getInformationList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                pageNum ++;
                getInformationList(false);
            }
        });
        getInformationList(true);

    }
    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
//                    Utils.Tag.java
//                    Utils.javaremoveViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    public void setDatas(List<VideoEntity> datas) {
        this.datas = datas;
    }


    public String getToken(String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_token", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;

        //恢复上次播放的位置
        startPlay(mLastPos);
    }
    /**
     * PrepareView被点击
     */
//    @Override
//    public void onItemChildClick(int position) {
//        startPlay(position);
//    }

    /**
     * 开始播放
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoEntity videoEntity = datas.get(position);
        //边播边存
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(videoBean.getUrl());
//        mVideoView.setUrl(proxyUrl);

        mVideoView.setUrl(videoEntity.getPlayurl());
        mTitleView.setTitle(videoEntity.getVtitle());
        View itemView = linearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;
        MessageAdapter.ViewHolder viewHolder = (MessageAdapter.ViewHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isDissociate此处只能为true, 请点进去看isDissociate的解释
        mController.addControlComponent(viewHolder.mPrepareView, true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, Tag.LIST);
        mVideoView.start();
        mCurPos = position;

    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if(getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }
    private void getInformationList(boolean isRefresh){
//        List<VideoEntity> datas = new ArrayList<>();
        String token = getToken("token");
        if (!StringUtils.isEmpty(token)){
            HashMap<String,Object> params = new HashMap<>();
            params.put("user", getUser("account"));
            Api.config(Apiconfig.GET_MESSAGE, params).getRequest(getActivity(),new RequestCallback() {
                @Override
                public void onSucess(String res) {
                    if(isRefresh){
                        refreshLayout.finishRefresh(true);
                    }else{
                        refreshLayout.finishLoadMore(true);
                    }
                    VideoListResponse response = new Gson().fromJson(res,VideoListResponse.class);
                    if (response != null && response.getCode() == 0){
                        List<VideoEntity> list = response.getPage().getList();
                        if (list != null && list.size()  > 0){
                            if (isRefresh){
                                datas = list;
                            }else{
//                                datas.addAll(list);
                            }
                            mHandler.sendEmptyMessage(0);
                        } else {
                            if(isRefresh){
                                datas = list;
                                mHandler.sendEmptyMessage(0);
                            }else{
                                showToast("数据已经全部加载");
                            }
                        }


                    }
                }

                @Override
                public void onFailure(Exception err) {
                    if(isRefresh){
                        refreshLayout.finishRefresh(true);
                    }else{
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }else {
            jump(LoginActivity.class);
        }



    }

    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
    }
}
