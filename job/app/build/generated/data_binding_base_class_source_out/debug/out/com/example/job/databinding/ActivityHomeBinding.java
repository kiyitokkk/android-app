// Generated by view binder compiler. Do not edit!
package com.example.job.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.job.R;
import com.example.job.view.FixedViewpager;
import com.flyco.tablayout.CommonTabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CommonTabLayout CommonTabLayout;

  @NonNull
  public final FixedViewpager viewpager;

  private ActivityHomeBinding(@NonNull LinearLayout rootView,
      @NonNull CommonTabLayout CommonTabLayout, @NonNull FixedViewpager viewpager) {
    this.rootView = rootView;
    this.CommonTabLayout = CommonTabLayout;
    this.viewpager = viewpager;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.CommonTabLayout;
      CommonTabLayout CommonTabLayout = ViewBindings.findChildViewById(rootView, id);
      if (CommonTabLayout == null) {
        break missingId;
      }

      id = R.id.viewpager;
      FixedViewpager viewpager = ViewBindings.findChildViewById(rootView, id);
      if (viewpager == null) {
        break missingId;
      }

      return new ActivityHomeBinding((LinearLayout) rootView, CommonTabLayout, viewpager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
