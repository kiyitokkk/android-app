// Generated by view binder compiler. Do not edit!
package com.example.job.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.job.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class YourFloatingLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView floatingText;

  private YourFloatingLayoutBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView floatingText) {
    this.rootView = rootView;
    this.floatingText = floatingText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static YourFloatingLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static YourFloatingLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.your_floating_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static YourFloatingLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.floating_text;
      TextView floatingText = ViewBindings.findChildViewById(rootView, id);
      if (floatingText == null) {
        break missingId;
      }

      return new YourFloatingLayoutBinding((RelativeLayout) rootView, floatingText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
