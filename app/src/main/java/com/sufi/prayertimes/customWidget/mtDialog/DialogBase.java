package com.sufi.prayertimes.customWidget.mtDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

import com.sufi.prayertimes.customWidget.mtDialog.internal.MDRootLayout;

import io.reactivex.annotations.NonNull;

/** @author Aidan Follestad (afollestad) */
class DialogBase extends Dialog implements DialogInterface.OnShowListener {

  protected MDRootLayout view;
  private OnShowListener showListener;

  DialogBase(Context context, int theme) {
    super(context, theme);
  }

  @Override
  public View findViewById(int id) {
    return view.findViewById(id);
  }

  @Override
  public final void setOnShowListener(OnShowListener listener) {
    showListener = listener;
  }

  final void setOnShowListenerInternal() {
    super.setOnShowListener(this);
  }

  final void setViewInternal(View view) {
    super.setContentView(view);
  }

  @Override
  public void onShow(DialogInterface dialog) {
    if (showListener != null) {
      showListener.onShow(dialog);
    }
  }

  @Override
  @Deprecated
  public void setContentView(int layoutResID) throws IllegalAccessError {
    throw new IllegalAccessError(
        "setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
  }

  @Override
  @Deprecated
  public void setContentView(@NonNull View view) throws IllegalAccessError {
    throw new IllegalAccessError(
        "setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
  }

  @Override
  @Deprecated
  public void setContentView(@NonNull View view, ViewGroup.LayoutParams params)
      throws IllegalAccessError {
    throw new IllegalAccessError(
        "setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
  }
}