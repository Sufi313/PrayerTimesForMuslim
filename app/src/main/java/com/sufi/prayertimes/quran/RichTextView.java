package com.sufi.prayertimes.quran;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import com.sufi.prayertimes.R;


public class RichTextView extends LinearLayout implements OnGlobalLayoutListener {
    private int fontColor;
    private int fontSize;
    private Paint paint;
    @NonNull
    private final LinearLayout.LayoutParams paramsTextView;
    private float spaceWidth;
    private String text;

    public RichTextView(final Context context) {
        super(context);
        this.fontColor = -16777216;
        this.setOrientation(VERTICAL);
        this.paramsTextView = new LinearLayout.LayoutParams(-2, -2);
        this.paramsTextView.gravity = 17;
        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.setPadding(0, 0, 0, 0);
        this.setBackgroundResource(R.drawable.border_bottom);
    }

    private void addLines() {
        if (this.getWidth() == 0) {
            return;
        }
        removeAllViews();
        final Object[] wrapTextWords = wrapTextWords(getWidth());
        final List list = (List) wrapTextWords[0];
        final List list2 = (List) wrapTextWords[1];
        for (int i = 0; i < list.size(); ++i) {
            final TextView textView = new TextView(getContext());
            textView.setSingleLine(true);
            textView.setLayoutParams(paramsTextView);
            textView.setTextSize(0, (float) fontSize);
            textView.setTextColor(fontColor);
            textView.setText((CharSequence) list.get(i));
            if (i < list.size() - 1) {
                textView.setTextScaleX((-10 + (float)getWidth()) / (float)list2.get(i));
            }
            addView(textView, paramsTextView);
        }
    }

    private float getSpaceWidth() {
        if (spaceWidth == 0.0f) {
            spaceWidth = getWordWidth(" ");
        }
        return spaceWidth;
    }

    private float getWordWidth(final String s) {
        return paint.measureText(s);
    }

    public void onGlobalLayout() {
        this.addLines();
        if (Build.VERSION.SDK_INT >= 16) {
            getViewTreeObserver().removeOnGlobalLayoutListener( this);
            return;
        }
        getViewTreeObserver().removeGlobalOnLayoutListener( this);
    }

    public void setFontColor(final int fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }

    public void setText(final String text) {
        this.text = text;
        addLines();
    }

    @NonNull
    public Object[] wrapTextWords(final int n) {
        (paint = new Paint()).setTextSize((float) fontSize);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayList<Float> list2 = new ArrayList<>();
        final String[] split = this.text.split(" ");
        String string = "";
        int i = 0;
        float n2 = 0.0f;
        while (i < split.length) {
            final float wordWidth = this.getWordWidth(split[i]);
            if (n2 + wordWidth >= n) {
                list.add(string.trim());
                list2.add(n2 - this.getSpaceWidth());
                string = "";
                n2 = 0.0f;
            }
            final StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(split[i]);
            sb.append(" ");
            string = sb.toString();
            n2 = n2 + wordWidth + this.getSpaceWidth();
            ++i;
        }
        if (string != "") {
            list.add(string.trim());
            list2.add(n2 - this.getSpaceWidth());
        }
        return new Object[]{list, list2};
    }
}
