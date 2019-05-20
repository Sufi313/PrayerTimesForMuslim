package net.vorson.muhammadsufwan.prayertimesformuslim.quran.models;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import io.reactivex.annotations.NonNull;

public class CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface newType;

    public CustomTypefaceSpan(String str, Typeface typeface) {
        super(str);
        this.newType = typeface;
    }

    public void updateDrawState(@NonNull TextPaint textPaint) {
        applyCustomTypeFace(textPaint, this.newType);
    }

    public void updateMeasureState(@NonNull TextPaint textPaint) {
        applyCustomTypeFace(textPaint, this.newType);
    }

    private void applyCustomTypeFace(@NonNull Paint paint, @NonNull Typeface typeface) {
        int i;
        Typeface typeface2 = paint.getTypeface();
        if (typeface2 == null) {
            i = 0;
        } else {
            i = typeface2.getStyle();
        }
        i &= typeface.getStyle() ^ -1;
        if ((i & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((i & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(typeface);
    }
}
