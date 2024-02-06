package com.example.job.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.R;
import com.example.job.api.Api;
import com.example.job.api.Apiconfig;
import com.example.job.api.RequestCallback;
import com.example.job.entity.BaseResponse;
import com.example.job.entity.VideoEntity;
import com.example.job.listener.OnItemChildClickListener;
import com.example.job.listener.OnItemClickListener;
import com.example.job.view.CircleTransform;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import xyz.doikki.videocontroller.component.PrepareView;

public class MycollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemChildClickListener mOnItemChildClickListener;

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private List<VideoEntity> datas;
    public MycollectAdapter(Context context){
          this.mContext = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mycollect, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        VideoEntity videoEntity = datas.get(position);
        viewHolder.tvTitle.setText(videoEntity.getVtitle());
        viewHolder.tvAuthor.setText(videoEntity.getAuthor());
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
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public int mPosition;



        private  TextView tvTitle;
        private  TextView tvAuthor;
        private ImageView tvimageHeader;
        private ImageView imgComment;

        public FrameLayout mPlayerContainer;
        public ImageView mThumb;
        public PrepareView mPrepareView;
        private boolean flagLike;
        private boolean flagCollect;
        private String comment;
        private ImageView img_collect;
        private ImageView img_like;
        private String collect;
        private String dz;

//        private ImageView tvimageCover;


        public ViewHolder(@NonNull View view) {
            super(view);;

            tvTitle = view.findViewById(R.id.title);
            collect = view.findViewById(R.id.collect).toString();
            tvAuthor =  view.findViewById(R.id.author);
            dz = view.findViewById(R.id.dz).toString();
            img_collect = view.findViewById(R.id.img_collect);
            tvimageHeader =  view.findViewById(R.id.img_header);
            imgComment = view.findViewById(R.id.img_comment);
            mPlayerContainer = view.findViewById(R.id.player_container);
//            mTitle = itemView.findViewById(R.id.tv_title);
            mPrepareView = view.findViewById(R.id.prepare_view);
            comment = view.findViewById(R.id.comment).toString();
            img_like = view.findViewById(R.id.img_like);
            mThumb = mPrepareView.findViewById(R.id.thumb);
            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                view.setOnClickListener(this);
            }

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


    }

}
