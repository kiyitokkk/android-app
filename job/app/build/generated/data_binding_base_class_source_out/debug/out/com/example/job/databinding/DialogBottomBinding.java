// Generated by view binder compiler. Do not edit!
package com.example.job.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.job.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogBottomBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView tvCancel;

  @NonNull
  public final TextView tvOpenAlbum;

  private DialogBottomBinding(@NonNull LinearLayout rootView, @NonNull TextView tvCancel,
      @NonNull TextView tvOpenAlbum) {
    this.rootView = rootView;
    this.tvCancel = tvCancel;
    this.tvOpenAlbum = tvOpenAlbum;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogBottomBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogBottomBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_bottom, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogBottomBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tv_cancel;
      TextView tvCancel = ViewBindings.findChildViewById(rootView, id);
      if (tvCancel == null) {
        break missingId;
      }

      id = R.id.tv_open_album;
      TextView tvOpenAlbum = ViewBindings.findChildViewById(rootView, id);
      if (tvOpenAlbum == null) {
        break missingId;
      }

      return new DialogBottomBinding((LinearLayout) rootView, tvCancel, tvOpenAlbum);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
