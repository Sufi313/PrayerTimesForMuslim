package com.sufi.prayertimes.quran;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.sufi.prayertimes.quran.models.CustomTypefaceSpan;

import java.util.Vector;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class FontManager {
    private int fontSize;
    private Paint paint;
    public Vector<CustomTypefaceSpan> spanTable;
    public Typeface typeface;

    @Nullable
    public CustomTypefaceSpan getTypeFaceSpan(String str) {
        return null;
    }

    public void loadFonts(@NonNull Context context, int size) {
        this.fontSize = size;
        this.paint = new Paint();
        float f = (float) size;
        this.paint.setTextSize(f);
        this.typeface = Typeface.createFromAsset(context.getAssets(), "fonts/quran_font.ttf");
        this.paint.setTypeface(this.typeface);
        this.paint.setTextSize(f);
    }

    public Typeface getTypeFace(String str) {
        return typeface;
    }

    public float getCharWidth(@NonNull String str) {
        if (str.equals("")) {
            return 0.0f;
        }
        if (str.equals("132")) {
            return paint.measureText(" ");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.charAt(0));
        stringBuilder.append("");
        int parseInt = Integer.parseInt(str.substring(1)) + 61440;
        switch (Integer.parseInt(stringBuilder.toString())) {
            case 1:
                parseInt += 224;
                break;
            case 2:
                parseInt += 467;
                break;
            case 3:
                parseInt += 710;
                break;
            case 4:
                parseInt += 953;
                break;
            case 5:
                parseInt += 1196;
                break;
        }
        return paint.measureText(Character.toString((char) parseInt));
    }

    public float getCharWidth(@NonNull String str, int fontSize) {
        paint.setTypeface(typeface);
        float fontSizef = (float) fontSize;
        paint.setTextSize(fontSizef);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.charAt(0));
        stringBuilder.append("");
        int parseInt = Integer.parseInt(str.substring(1)) + 61440;
        switch (Integer.parseInt(stringBuilder.toString())) {
            case 1:
                parseInt += 224;
                break;
            case 2:
                parseInt += 467;
                break;
            case 3:
                parseInt += 710;
                break;
            case 4:
                parseInt += 953;
                break;
            case 5:
                parseInt += 1196;
                break;
        }
        float measureText = paint.measureText(Character.toString((char) parseInt));
        paint.setTextSize(fontSizef);
        return measureText;
    }

    public float getWordWidth(@NonNull String str) {
        String[] split = str.split(",");
        float f = 0.0f;
        for (String str2 : split) {
            if (str2.equals("442") || str2.equals("441")) {
                f += getCharWidth(str2);
            }
            if (!(str2.startsWith("4") || str2.startsWith("5"))) {
                f += getCharWidth(str2);
            }
        }
        return f;
    }

    public float getSpaceWidth() {
        return getCharWidth("132");
    }

    public float getKashidaWidth() {
        return getCharWidth("1128");
    }

    public int getLineHeight() {
        return fontSize * 2;
    }

    public int getFontNameFromChar(@NonNull String str) {
        return (((Integer.parseInt(str.substring(0, 1)) - 1) * 2) + (Integer.parseInt(str.substring(1)) < 140 ? 1 : 2)) - 1;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int i) {
        fontSize = i;
        paint.setTextSize((float) fontSize);
    }

    public Paint getPaint() {
        return this.paint;
    }
}
