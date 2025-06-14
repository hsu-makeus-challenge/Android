// Generated by view binder compiler. Do not edit!
package com.example.week1_remind.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.week1_remind.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton blueFace;

  @NonNull
  public final ImageButton goBack;

  @NonNull
  public final ImageButton greenFace;

  @NonNull
  public final ImageButton purpleFace;

  @NonNull
  public final ImageButton redFace;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final ImageButton yellowFace;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton blueFace,
      @NonNull ImageButton goBack, @NonNull ImageButton greenFace, @NonNull ImageButton purpleFace,
      @NonNull ImageButton redFace, @NonNull TextView textView, @NonNull TextView textView2,
      @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5,
      @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8,
      @NonNull ImageButton yellowFace) {
    this.rootView = rootView;
    this.blueFace = blueFace;
    this.goBack = goBack;
    this.greenFace = greenFace;
    this.purpleFace = purpleFace;
    this.redFace = redFace;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView3 = textView3;
    this.textView4 = textView4;
    this.textView5 = textView5;
    this.textView6 = textView6;
    this.textView7 = textView7;
    this.textView8 = textView8;
    this.yellowFace = yellowFace;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.blue_face;
      ImageButton blueFace = ViewBindings.findChildViewById(rootView, id);
      if (blueFace == null) {
        break missingId;
      }

      id = R.id.go_back;
      ImageButton goBack = ViewBindings.findChildViewById(rootView, id);
      if (goBack == null) {
        break missingId;
      }

      id = R.id.green_face;
      ImageButton greenFace = ViewBindings.findChildViewById(rootView, id);
      if (greenFace == null) {
        break missingId;
      }

      id = R.id.purple_face;
      ImageButton purpleFace = ViewBindings.findChildViewById(rootView, id);
      if (purpleFace == null) {
        break missingId;
      }

      id = R.id.red_face;
      ImageButton redFace = ViewBindings.findChildViewById(rootView, id);
      if (redFace == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      id = R.id.yellow_face;
      ImageButton yellowFace = ViewBindings.findChildViewById(rootView, id);
      if (yellowFace == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, blueFace, goBack, greenFace,
          purpleFace, redFace, textView, textView2, textView3, textView4, textView5, textView6,
          textView7, textView8, yellowFace);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
