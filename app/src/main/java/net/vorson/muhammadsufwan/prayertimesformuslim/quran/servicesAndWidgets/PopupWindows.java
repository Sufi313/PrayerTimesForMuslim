package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class PopupWindows {
    @Nullable
    protected Drawable mBackground = null;
    protected Context mContext;
    protected View mRootView;
    protected PopupWindow mWindow;
    protected WindowManager mWindowManager;

    protected void onDismiss() {
    }

    protected void onShow() {
    }

    public PopupWindows(@NonNull Context context) {
        this.mContext = context;
        this.mWindow = new PopupWindow(context);
        this.mWindow.setTouchInterceptor(new OnTouchListener() {
            public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
                if (motionEvent.getAction() != 4) {
                    return false;
                }
                PopupWindows.this.mWindow.dismiss();
                return true;
            }
        });
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }

    protected void preShow() {
        if (this.mRootView != null) {
            onShow();
            if (this.mBackground == null) {
                this.mWindow.setBackgroundDrawable(new BitmapDrawable());
            } else {
                this.mWindow.setBackgroundDrawable(this.mBackground);
            }
            this.mWindow.setWidth(-2);
            this.mWindow.setHeight(-2);
            this.mWindow.setTouchable(true);
            this.mWindow.setFocusable(true);
            this.mWindow.setOutsideTouchable(true);
            this.mWindow.setContentView(this.mRootView);
            return;
        }
        throw new IllegalStateException("setContentView was not called with a view to display.");
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackground = drawable;
    }

    public void setContentView(View view) {
        this.mRootView = view;
        this.mWindow.setContentView(view);
    }

    public void setContentView(int i) {
        setContentView(((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(i, null));
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mWindow.setOnDismissListener(onDismissListener);
    }

    public void dismiss() {
        this.mWindow.dismiss();
    }
}
