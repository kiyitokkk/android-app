package com.example.job.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.job.R;
import com.example.job.activity.HomeActivity;
import com.example.job.activity.LoginActivity;
import com.example.job.activity.MycollectActivity;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.ChangeImgHead;
import com.example.job.entity.GetHomeInformation;
import com.example.job.entity.LoginResponse;
import com.example.job.entity.MyCollectResponse;
import com.example.job.util.BitmapUtils;
import com.example.job.util.CameraUtils;
import com.example.job.util.SPUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment {
    private   String TAG ;
    //    @BindView(R.id.img_header)
    ImageView imgHeader;
    private RelativeLayout myRelativeLayout;
    private RelativeLayout imgheadRelativeLayout;
    private RelativeLayout quitRelativeLayout;
    private RefreshLayout refreshLayout;
    private RxPermissions rxPermissions;
    private TextView nameText;
    private boolean hasPermissions = false;
    private String userName;
    public String headUrl;
    //存储拍完照后的图片
    private File outputImagePath;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;

    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;
    private ShapeableImageView ivHead;
    //Base64
    private String base64Pic;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;
    private RelativeLayout changeNameRelativeLayout;
    Handler handler = new Handler();


    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform();

    private void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imgurl) {
        Glide.with(this)
                .load(imgurl)
                .into(ivHead);
    }
    public String getUser(String key){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sp_user", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
               //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //显示图片
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, getActivity());
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data, getActivity());
                    }
                    orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
                    //转Base64
                    base64Pic = "data:image/jpeg;base64," + BitmapUtils.bitmapToBase64(orc_bitmap) ;
                    HashMap<String,Object> params = new HashMap <String,Object> ();
                    params.put("img",base64Pic);
                    params.put("User", getUser("account"));
                    Api.config(Apiconfig.CHANGE_HEADER, params).postRequest(getActivity() , new RequestCallback() {
                        @Override
                        public void onSucess(String res) {
                            Gson gson = new Gson();
                            ChangeImgHead changeImgHead = gson.fromJson(res, ChangeImgHead.class);
//               设置token
                            if(changeImgHead.getCode() == 0){
                                headUrl = changeImgHead.getImgurl();
                            }else{
                                showToast("login flase");
                            }
                        }
                        @Override
                        public void onFailure(Exception err) {
                        }
                    });
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 这里是休眠结束后要执行的代码
                        }
                    }, 3000); // 休眠 5 秒

                    displayImage(headUrl);

                    //显示图片

                }
                break;
            default:
                break;
        }
    }

    private void takePhoto() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        Context context = getActivity(); // 获取活动的上下文
        File cacheDir = context.getExternalCacheDir(); // 获取外部缓存目录


        String filename = String.valueOf(System.currentTimeMillis());

        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(context, outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }
    private void openAlbum() {
        if (!hasPermissions) {
            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }




    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
                            hasPermissions = true;
                            showMsg("已获取权限");

                        } else {//申请失败
                            showMsg("权限未开启");
                        }
                    });
        } else {
            //Android6.0以下
            showMsg("无需请求动态权限");
        }
    }



    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void intview() {
//        RelativeLayout relativeLayout = mRootView.findViewById(R.id.rl_collect);
        myRelativeLayout = mRootView.findViewById(R.id.rl_collect);
        quitRelativeLayout = mRootView.findViewById(R.id.rl_logout);
        imgheadRelativeLayout = mRootView.findViewById(R.id.rl_head);
        refreshLayout = mRootView.findViewById(R.id.refreshLayoutmine);
        ivHead = mRootView.findViewById(R.id.iv_head);
        nameText = mRootView.findViewById(R.id.username);
        changeNameRelativeLayout = mRootView.findViewById(R.id.change_name);
        checkVersion();
        String imageUrl = SPUtils.getString("imageUrl",null, getActivity());
        if(imageUrl != null){
            Glide.with(getActivity()).load(imageUrl).apply(requestOptions).into(ivHead);
        }





    }
    public  void getHomeInformation (){
        HashMap<String,Object> params = new HashMap<>();
        params.put("User", getUser("account"));
        Api.config(Apiconfig.MY_HOME,params).getRequest(getActivity(), new RequestCallback() {
            @Override
            public void onSucess(String res) {
                GetHomeInformation getHomeInformation = new Gson().fromJson(res, GetHomeInformation.class);
                String username = getHomeInformation.getUsername();
                String headurls = getHomeInformation.getHeadurl();
//                headUrl = headurls;
//                showToastSyc(headUrl);
//                userName = username;
                setInfoemation(headurls,username);
            }
            @Override
            public void onFailure(Exception err) {

            }
        });
    }

    public void setInfoemation(String headUrls,String userNames){
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayImage(headUrls);
                nameText.setText(userNames);
            }
        });

    }
    protected void removeUser(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_user", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }


    @Override
    protected void initData() {
        getHomeInformation();
        myRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(MycollectActivity.class);
            }
        });
        quitRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeToken("token");
                removeUser("User");
                navigateToWithFlag(LoginActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                removeToken("token");

            }
        });
        imgheadRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
                bottomSheetDialog.setContentView(bottomView);

                TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
                TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);


                //打开相册
                tvOpenAlbum.setOnClickListener(v -> {
                    openAlbum();
                    showMsg("打开相册");
                    bottomSheetDialog.cancel();
                });
                //取消
                tvCancel.setOnClickListener(v -> {
                    bottomSheetDialog.cancel();
                });
                bottomSheetDialog.show();

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                try {
                    getHomeInformation ();
                    refreshlayout.finishRefresh(true);
                }catch (Exception e){
                    refreshlayout.finishRefresh(false);
                }


            }
        });
//        改名字
        changeNameRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_changename, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        EditText inputEditText = dialogView.findViewById(R.id.dialog_input);
        TextView button = dialogView.findViewById(R.id.dialog_button);
        TextView buttonexit = dialogView.findViewById(R.id.dialog_buttonout);
        String User = getUser("account");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputEditText.getText().toString();
                if (userInput.length() >= 9){
                    showToast("起名不能过长哦");
                }else {
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("user", User);
                    params.put("newname", userInput);
                    Api.config(Apiconfig.CHANGE_THE_NAME, params).postRequest(mRootView.getContext(), new RequestCallback() {
                        @Override
                        public void onSucess(String res) {

                        }

                        @Override
                        public void onFailure(Exception err) {

                        }
                    });
                    showToast(userInput);
                    alertDialog.dismiss();
                }

            }
        });
        buttonexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });

    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_mine, container, false);
//    }

}