package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import io.reactivex.annotations.NonNull;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class WordMeaningPopup extends PopupWindows implements PopupWindow.OnDismissListener
{
    public static final int ANIM_AUTO = 5;
    public static final int ANIM_GROW_FROM_CENTER = 3;
    public static final int ANIM_GROW_FROM_LEFT = 1;
    public static final int ANIM_GROW_FROM_RIGHT = 2;
    public static final int ANIM_REFLECT = 4;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int mAnimStyle;
    private boolean mDidAction;
    private WordMeaningPopup.OnDismissListener mDismissListener;
    private LayoutInflater mInflater;
    private ViewGroup mTrack;

    public WordMeaningPopup(@NonNull final Context context) {
        this(context, 1);
    }

    public WordMeaningPopup(@NonNull final Context context, final int n) {
        super(context);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (n == 0) {
            setRootViewId(R.layout.popup_horizontal);
        }
        else {
            setRootViewId(R.layout.popup_vertical);
        }
        mAnimStyle = 5;
    }

    private void setAnimationStyle(final boolean b) {
        switch (this.mAnimStyle) {
            default: {}
            case 4: {
                final PopupWindow mWindow = this.mWindow;
                int animationStyle;
                if (b) {
                    animationStyle = R.style.Animations_PopUpMenu_Reflect;
                }
                else {
                    animationStyle = R.style.Animations_PopDownMenu_Reflect;
                }
                mWindow.setAnimationStyle(animationStyle);
            }
            case 3: {
                final PopupWindow mWindow2 = mWindow;
                int animationStyle2;
                if (b) {
                    animationStyle2 = R.style.Animations_PopUpMenu_Center;
                }
                else {
                    animationStyle2 = R.style.Animations_PopDownMenu_Center;
                }
                mWindow2.setAnimationStyle(animationStyle2);
            }
            case 2: {
                final PopupWindow mWindow3 = mWindow;
                int animationStyle3;
                if (b) {
                    animationStyle3 = R.style.Animations_PopUpMenu_Right;
                }
                else {
                    animationStyle3 = R.style.Animations_PopDownMenu_Right;
                }
                mWindow3.setAnimationStyle(animationStyle3);
            }
            case 1: {
                final PopupWindow mWindow4 = mWindow;
                int animationStyle4;
                if (b) {
                    animationStyle4 = R.style.Animations_PopUpMenu_Left;
                }
                else {
                    animationStyle4 = R.style.Animations_PopDownMenu_Left;
                }
                mWindow4.setAnimationStyle(animationStyle4);
            }
        }
    }

    public void addText(final String text) {
        final TextView textView = new TextView(mContext);
        textView.setTextColor(-1);
        textView.setTextSize(30.0f);
        textView.setText(text);
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });
        mTrack.addView(textView);
    }

    public void onDismiss() {
        if (!mDidAction && mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    public void setAnimStyle(final int mAnimStyle) {
        this.mAnimStyle = mAnimStyle;
    }

    public void setOnDismissListener(final WordMeaningPopup.OnDismissListener mDismissListener) {
        setOnDismissListener(this);
        this.mDismissListener = mDismissListener;
    }

    public void setRootViewId(final int n) {
        final View inflate = this.mInflater.inflate(n, null);
        this.mTrack = inflate.findViewById(R.id.tracks);
        final ScrollView scrollView = inflate.findViewById(R.id.scroller);
        inflate.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        setContentView(inflate);
    }

    public void show(final View view) {
        preShow();
        setAnimationStyle(true);
        mWindow.showAtLocation(view, 17, 0, 0);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}