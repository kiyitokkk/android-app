package com.example.job.adapter;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static com.example.job.api.Apiconfig.GETCOMMENT_LIST;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.MainActivity;
import com.example.job.R;
import com.example.job.activity.MycollectActivity;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.BaseResponse;
import com.example.job.entity.Comment;
import com.example.job.entity.CommonList;
import com.example.job.entity.VideoEntity;
import com.example.job.fragment.MessageRef;
import com.example.job.fragment.VideoFragment;
import com.example.job.listener.OnItemChildClickListener;
import com.example.job.listener.OnItemClickListener;
import com.example.job.util.CommentBottomDialog;
import com.example.job.view.CircleTransform;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.doikki.videocontroller.component.PrepareView;
import okhttp3.Callback;
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CommentBottomDialog.OnCommentListener {
    private OnItemChildClickListener mOnItemChildClickListener;
    private CommentAdapter commentAdapter;
    OkHttpClient client = new OkHttpClient();

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private  List<Comment> commentList;
    private List<VideoEntity> datas;
    private View bottomView;
    private BottomSheetDialog bottomSheetDialog;

    public MessageAdapter(Context context){
        this.mContext = context;

    }
    public void showToastSyc(String msg){
        Looper.prepare();
        Toast toast =  Toast.makeText(mContext,null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
        Looper.loop();


    }
    private List<Comment> getCommentData(int Vid) throws IOException {
//        List<Comment> commentList = new ArrayList<>();
        commentList = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("vid", Vid);
        Api.config(Apiconfig.GETCOMMENT_LIST, params).getRequest(mContext , new RequestCallback() {
            @Override
            public void onSucess(String res) {
                Gson gson = new Gson();
                CommonList baseResponse = gson.fromJson(res, CommonList.class);
                if (baseResponse.getCode() == 0) {
                    List<CommonList.CommentlistDTO> commentlistDTOList = baseResponse.getCommentlist();
                    for (CommonList.CommentlistDTO commentlistDTO : commentlistDTOList) {
                        String userName = commentlistDTO.getUsername();
                        String content = commentlistDTO.getContent();
                        String avatar = commentlistDTO.getAvatar();
                        String uploadTime = commentlistDTO.getUploadTime();
                        commentList.add(new Comment(userName, content, avatar, uploadTime));
                    }
                }
            }
            @Override
            public void onFailure(Exception err) {

            }
        });
        // Add fake comment data
        return commentList;
    }
    public void SendComment(String username, String content, int Vid){
        HashMap<String,Object> params = new HashMap <String,Object> ();
        params.put("username", username);
        params.put("user", getUser("account"));
        params.put("content", content);
        params.put("Vid", Vid);
        Api.config(Apiconfig.SEND_COMMENT, params).postRequest(mContext , new RequestCallback() {
            @Override
            public void onSucess(String res) {

            }
            @Override
            public void onFailure(Exception err) {
            }
        });
    }
    private void showCommentDialog(int Vid) throws IOException {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        View commentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_comment, null);
        View mineView = LayoutInflater.from(mContext).inflate(R.layout.fragment_mine, null);
        Button btnSend = commentView.findViewById(R.id.btn_send);
        TextView usernameTextView = mineView.findViewById(R.id.username);
        EditText editText = commentView.findViewById(R.id.edit_comment);
        String text = editText.getText().toString();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length() > 0){
                    SendComment(usernameTextView.getText().toString(), editText.getText().toString(), Vid);
                    editText.setText("");
                    showToast("评论发布成功");
                }else {
                    showToast("评论不能为空");
                }
            }
        });


        // Get comment data (using fake data)
        List<Comment> commentList = getCommentData(Vid);
        // Get RecyclerView for displaying comments
        RecyclerView recyclerView = commentView.findViewById(R.id.recyclerView);
        // Create an adapter to display the comment data
        commentAdapter = new CommentAdapter(commentList, mContext);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        bottomSheetDialog.setContentView(commentView);
        bottomSheetDialog.show();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void showToast(String msg){
        Toast toast =  Toast.makeText(mContext,null,Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        VideoEntity videoEntity = datas.get(position);
        viewHolder.tvTitle.setText(videoEntity.getVtitle());
        viewHolder.upload_time.setText(videoEntity.getUpload_time());
        viewHolder.tvAuthor.setText(videoEntity.getAuthor());
        if (videoEntity != null) {
            int likenum = videoEntity.getLikeNum();
            int commentnum = videoEntity.getCommentNum();
            int collectnum = videoEntity.getCollectNum();
            boolean flagLike = videoEntity.isFlagLike();
            boolean flagCollect = videoEntity.isFlagCollect();
            if (flagLike) {
                viewHolder.imgLike.setImageResource(R.mipmap.dianzan_select);
            }
            if (flagLike == false) {
                viewHolder.imgLike.setImageResource(R.mipmap.dianzan);
            }
            if (flagCollect) {
                viewHolder.imgCollect.setImageResource(R.mipmap.collect_select);
            }
            if (flagCollect == false) {
                viewHolder.imgCollect.setImageResource(R.mipmap.collect);
            }
            viewHolder.tvDZ.setText(String.valueOf(likenum));
            viewHolder.tvComment.setText(String.valueOf(commentnum));
            viewHolder.tvCollect.setText(String.valueOf(collectnum));
            viewHolder.flagCollect = flagCollect;
            viewHolder.flagLike = flagLike;
        }
        viewHolder.tvCollect.setText(String.valueOf(videoEntity.getCollectNum()));
        viewHolder.tvComment.setText(String.valueOf(videoEntity.getCommentNum()));
        viewHolder.tvDZ.setText(String.valueOf(videoEntity.getLikeNum()));
        Picasso.with(mContext)
                .load(videoEntity.getHeadurl())
                .transform(new CircleTransform())
                .into(viewHolder.tvimageHeader);
        Picasso.with(mContext)
                .load(videoEntity
                        .getCoverurl())
                .into(viewHolder.mThumb);
        viewHolder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size()>0){
            return datas.size();
        }else{
            return 0;
        }

    }

    public void setDatas(List<VideoEntity> datas) {
        this.datas = datas;
        System.out.println(datas);
    }

    public void setOnItemChildClickListener(VideoFragment videoFragment) {
        mOnItemChildClickListener = videoFragment;
    }
    public void setOnItemChildClickListener(MycollectActivity mycollectActivity) {
        mOnItemChildClickListener = mycollectActivity;
    }
    public String getUser(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sp_user", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    private void updateCount(String categoryId, int vid, int type, boolean flag) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("user", getUser("account"));
        params.put("vid", vid);
        params.put("type", type);
        params.put("flag", flag);
        params.put("categoryId", categoryId);
        Api.config(Apiconfig.UNPDTE_COUNT, params).postRequest(mContext , new RequestCallback() {
            @Override
            public void onSucess(String res) {
//                Log.e("onSuccess", res);
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(res, BaseResponse.class);
                if (baseResponse.getCode() == 0) {
                }
            }

            @Override
            public void onFailure(Exception err) {

            }
        });
    }

    @Override
    public void onCommentSubmitted(String comment) {

    }

    public void setOnItemChildClickListener(MessageRef messageRef) {
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private  Context context;
        public int mPosition;


        private TextView tvDZ;
        private  TextView tvComment;
        private TextView tvCollect;
        private  TextView tvTitle;
        private  TextView tvAuthor;
        private ImageView tvimageHeader;
        private ImageView imgComment;
        private TextView upload_time;
        private ImageView imgLike;
        private ImageView imgCollect;
        public FrameLayout mPlayerContainer;
        public ImageView mThumb;
        public PrepareView mPrepareView;
        private boolean flagLike;
        private boolean flagCollect;
        private ImageView img_comment;
        private List<String> comments;
        private ImageView img_delete;
        private ImageView img_edit;
        private EditText inputText;
        private Button editTitle;
        private RecyclerView recyclerView;
        private Button delete_button;
        private Button out_button;
        private Button btnAddComment;
        private void showInputDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = LayoutInflater.from(mContext).inflate(R.layout.edit_dialog_layout, null);
            builder.setView(dialogView);
           inputText = dialogView.findViewById(R.id.input_text);
           editTitle = dialogView.findViewById(R.id.edit_button);
           AlertDialog dialog = builder.create();
           dialog.show();
           editTitle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String inputValue = inputText.getText().toString();
                   HashMap<String,Object> params = new HashMap<>();
                   params.put("user", getUser("account"));
                   params.put("newtitle", inputValue);
                   params.put("vid", datas.get(mPosition).getVid());
                   Api.config(Apiconfig.EDIT_RELEASE, params).postRequest(mContext, new RequestCallback() {
                       @Override
                       public void onSucess(String res) {
                           dialog.dismiss();
                           showToastSyc(inputValue);
                       }
                       @Override
                       public void onFailure(Exception err) {
                       }
                   });
               }
           });
        }
        private void showDeleteDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = LayoutInflater.from(mContext).inflate(R.layout.delete_dialog_layout, null);
            builder.setView(dialogView);
            delete_button = dialogView.findViewById(R.id.delete_button);
            out_button = dialogView.findViewById(R.id.out_button);
            AlertDialog dialog = builder.create();
            dialog.show();
            out_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String,Object> params = new HashMap<>();
                    params.put("user", getUser("account"));
                    params.put("vid", datas.get(mPosition).getVid());
                    Api.config(Apiconfig.DELETE_RELEASE, params).postRequest(mContext, new RequestCallback() {
                        @Override
                        public void onSucess(String res) {
                            dialog.dismiss();
                            showToastSyc("删除成功");
                        }
                        @Override
                        public void onFailure(Exception err) {
                        }
                    });
                }
            });
        }
        public ViewHolder(@NonNull View view) {
            super(view);;
            tvDZ = view.findViewById(R.id.dz);
            tvComment = view.findViewById(R.id.comment);
            tvCollect = view.findViewById(R.id.collect);
            tvTitle = view.findViewById(R.id.title);
            upload_time = view.findViewById(R.id.times);
            tvAuthor =  view.findViewById(R.id.author);
            tvimageHeader =  view.findViewById(R.id.img_header);
            imgCollect = view.findViewById(R.id.img_collect);
            imgLike = view.findViewById(R.id.img_like);
            img_comment = view.findViewById(R.id.img_comment);
            mPlayerContainer = view.findViewById(R.id.player_container);
//            mTitle = itemView.findViewById(R.id.tv_title);
            img_delete = view.findViewById(R.id.img_delete);
            img_edit = view.findViewById(R.id.img_edit);
            mPrepareView = view.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);
            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                view.setOnClickListener(this);
            }
            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInputDialog();
                }


            });
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteDialog();
                }
            });
            imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int collectNum = Integer.parseInt(tvCollect.getText().toString());
                    if (flagCollect) { //已收藏
                        if (collectNum > 0) {
                            tvCollect.setText(String.valueOf(--collectNum));
                            tvCollect.setTextColor(Color.parseColor("#161616"));
                            imgCollect.setImageResource(R.mipmap.collect);
                            updateCount(datas.get(mPosition).getCategoryId(),datas.get(mPosition).getVid(), 1, !flagCollect);
                        }
                    } else {//未收藏
                        tvCollect.setText(String.valueOf(++collectNum));
                        tvCollect.setTextColor(Color.parseColor("#161616"));
                        imgCollect.setImageResource(R.mipmap.collect_select);
                        updateCount(datas.get(mPosition).getCategoryId(),datas.get(mPosition).getVid(), 1, !flagCollect);
                    }
                    flagCollect = !flagCollect;
                }
            });
            img_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        showCommentDialog(datas.get(mPosition).getVid());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            imgLike.setOnClickListener(view1 -> {
                int likeNum = Integer.parseInt(tvDZ.getText().toString());
                if (flagLike) { //已点赞
                    if (likeNum > 0) {
                        tvDZ.setText(String.valueOf(--likeNum));
                        tvDZ.setTextColor(Color.parseColor("#161616"));
                        imgLike.setImageResource(R.mipmap.dianzan);
                        updateCount(datas.get(mPosition).getCategoryId(),datas.get(mPosition).getVid(), 2, !flagCollect);
                    }
                } else {//未点赞
                    tvDZ.setText(String.valueOf(++likeNum));
                    tvDZ.setTextColor(Color.parseColor("#161616"));
                    imgLike.setImageResource(R.mipmap.dianzan_select);
                    updateCount(datas.get(mPosition).getCategoryId(),datas.get(mPosition).getVid(), 2, !flagCollect);
                }
                flagLike = !flagLike;


            });
            //通过tag将ViewHolder和itemView绑定
            view.setTag(this);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }

        }
        public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
            mOnItemChildClickListener = onItemChildClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

    }

}
