package com.sufi.prayertimes.quran;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;


import com.sufi.prayertimes.R;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;

import java.util.Random;

import androidx.annotation.NonNull;

public class BackgroundView extends View{
    @NonNull
    private static int[] chars = new int[]{70, 75, 83, 94, 95, 99, 107, 111, 116, 119, 161, 162, 163, 183, 186, 194, 195, 196};
    private Rect charBounds;
    private int currentChar = 0;
    private boolean gBg = true;
    private Paint mTextPaint;

    public BackgroundView(Context context) {
        super(context);
        AppSettings appSettings = AppSettings.getInstance(context);
        gBg = appSettings.getBoolean(AppSettings.Key.AL_QURAN_BACKGROUND);
        init();
    }

    public BackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void init() {
        if (gBg) {
            mTextPaint = new Paint(1);
            mTextPaint.setColor(getResources().getColor(R.color.white));
            mTextPaint.setTextSize(150.0f);
            mTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/my_shapes.ttf"));
            charBounds = new Rect();
            currentChar = chars[new Random().nextInt(chars.length)];
            mTextPaint.getTextBounds(Character.toString((char) currentChar), 0, 1, charBounds);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (gBg) {
            int i = 0;
            while (i < getHeight() + charBounds.height()) {
                int i2 = 0;
                while (i2 < getWidth() + charBounds.width()) {
                    canvas.drawText(Character.toString((char) currentChar), (float) i2, (float) i, mTextPaint);
                    i2 += charBounds.width();
                }
                i += charBounds.height();
            }
        }
    }

}
