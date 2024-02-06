package com.example.job.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.example.job.R;
import com.example.job.entity.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.txtUsername.setText(comment.getUsername());
        holder.txtCommentText.setText(comment.getCommentText());
        String url = comment.getAvatar();
        String uploadTime = comment.getUploadTime();
        holder.uploadTime.setText(uploadTime);
        Picasso.with(mContext)
                .load(url)
                .into(holder.avatar);


//        holder.avatar
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtUsername;
        private final TextView txtCommentText;
        private final ImageView avatar;
        private final TextView uploadTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtCommentText = itemView.findViewById(R.id.txtCommentText);
            avatar = itemView.findViewById(R.id.avatar);
            uploadTime = itemView.findViewById(R.id.comment_time);
        }
    }
}