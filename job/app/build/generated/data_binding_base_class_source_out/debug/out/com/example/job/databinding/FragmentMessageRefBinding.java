// Generated by view binder compiler. Do not edit!
package com.example.job.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.job.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMessageRefBinding implements ViewBinding {
  @NonNull
  private final SmartRefreshLayout rootView;

  @NonNull
  public final RecyclerView recyclerviewRelease;

  @NonNull
  public final SmartRefreshLayout refreshLayoutmessage;

  private FragmentMessageRefBinding(@NonNull SmartRefreshLayout rootView,
      @NonNull RecyclerView recyclerviewRelease, @NonNull SmartRefreshLayout refreshLayoutmessage) {
    this.rootView = rootView;
    this.recyclerviewRelease = recyclerviewRelease;
    this.refreshLayoutmessage = refreshLayoutmessage;
  }

  @Override
  @NonNull
  public SmartRefreshLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMessageRefBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMessageRefBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_message_ref, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMessageRefBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recyclerview_release;
      RecyclerView recyclerviewRelease = ViewBindings.findChildViewById(rootView, id);
      if (recyclerviewRelease == null) {
        break missingId;
      }

      SmartRefreshLayout refreshLayoutmessage = (SmartRefreshLayout) rootView;

      return new FragmentMessageRefBinding((SmartRefreshLayout) rootView, recyclerviewRelease,
          refreshLayoutmessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
