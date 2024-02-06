package com.example.job.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.job.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentBottomDialog extends BottomSheetDialogFragment {

    private EditText editComment;
    private Button btnSend;
    private OnCommentListener onCommentListener;

    public void setOnCommentListener(OnCommentListener listener) {
        this.onCommentListener = listener;
    }

    public interface OnCommentListener {
        void onCommentSubmitted(String comment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_comment, container, false);
        editComment = view.findViewById(R.id.edit_comment);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editComment.getText().toString().trim();
                if (!comment.isEmpty() && onCommentListener != null) {
                    onCommentListener.onCommentSubmitted(comment);
                }
                dismiss();
            }
        });
        return view;
    }
}
