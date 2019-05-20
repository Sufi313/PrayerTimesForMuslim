package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.net.http.RequestHandle;
import android.util.Log;
import android.util.SparseArray;

import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordMeaning;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordOfQuran;

import java.util.List;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.core.internal.view.SupportMenu;
import io.reactivex.annotations.Nullable;

public class TextRendererNew
{
    public static int MODE_VIEW_PAGING = 0;
    public static int MODE_VIEW_SCROLLLING = 1;
    private Canvas canvas;
    private float fWidth;
    private FontManager fontManager;
    private int forModeView;
    private Paint mPaint;
    private int strokeColor;
    private boolean strokeEnabled;
    private boolean textBold;
    private int workingDrawingColor;
    private int workingDrawingFontSize;
    private boolean workingDrawingTextBold;

    public TextRendererNew(final int forModeView) {
        this.fWidth = 0.0f;
        this.forModeView = forModeView;
    }

    private void drawChar(@NonNull final String s, float n, final float n2) {
        if (s == "" || s == null || this.fontManager == null || this.canvas == null) {
            return;
        }
        if (this.mPaint == null) {
            return;
        }
        if (s.equals("3102")) {
            n = (this.canvas.getWidth() - this.fontManager.getCharWidth("3102", this.workingDrawingFontSize)) / 2.0f;
        }
        if (s.equals("3101")) {
            n = (this.canvas.getWidth() - this.fontManager.getCharWidth("3101", this.workingDrawingFontSize)) / 2.0f;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        sb.append("");
        final int int1 = Integer.parseInt(sb.toString());
        int n3 = 61440 + Integer.parseInt(s.substring(1));
        switch (int1) {
            case 5: {
                n3 += 1196;
                break;
            }
            case 4: {
                n3 += 953;
                break;
            }
            case 3: {
                n3 += 710;
                break;
            }
            case 2: {
                n3 += 467;
                break;
            }
            case 1: {
                n3 += 224;
                break;
            }
        }
        this.mPaint.setTextSize((float)this.workingDrawingFontSize);
        if (this.strokeEnabled) {
            this.mPaint.setColor(this.strokeColor);
            this.mPaint.setStyle(Style.STROKE);
            this.mPaint.setStrokeWidth(2.0f);
            final Canvas canvas = this.canvas;
            final StringBuilder sb2 = new StringBuilder();
            sb2.append((char)n3);
            sb2.append("");
            canvas.drawText(sb2.toString(), n, n2, this.mPaint);
        }
        this.mPaint.setColor(this.workingDrawingColor);
        this.mPaint.setFakeBoldText(this.workingDrawingTextBold);
        this.mPaint.setStyle(Style.FILL);
        final Canvas canvas2 = this.canvas;
        final StringBuilder sb3 = new StringBuilder();
        sb3.append((char)n3);
        sb3.append("");
        canvas2.drawText(sb3.toString(), n, n2, this.mPaint);
    }

    public float calculateLineWidth(@NonNull final List<Integer> list) {
        float n = 0.0f;
        for (int i = 0; i < list.size(); ++i) {
            n = n + ((WordOfQuran)StaticObjects.contentWords.get(list.get(i))).getWordWidthInPixel() + this.fontManager.getSpaceWidth();
        }
        return n - this.fontManager.getSpaceWidth();
    }

    public void renderText(final Canvas canvas, final Paint mPaint, final FontManager fontManager, @NonNull final List<Integer> list, @Nullable final Vector<Integer> vector, @Nullable final SparseArray<WordMeaning> sparseArray, final int n, final float fWidth, final float n2, final float n3, final float n4, final Boolean b, final int strokeColor, final boolean b2, final int n5, final int workingDrawingFontSize, final int n6, final boolean b3) {
        List list2 = list;
        if (StaticObjects.contentWords == null) {
            return;
        }
        this.fontManager = fontManager;
        this.canvas = canvas;
        this.mPaint = mPaint;
        this.strokeColor = strokeColor;
        this.strokeEnabled = b;
        this.textBold = b2;
        this.fWidth = fWidth;
        float n7 = fWidth;
        int i = 0;
        int n8 = 0;
        while (i < list.size()) {
            final WordOfQuran wordOfQuran = StaticObjects.contentWords.get((Integer) list2.get(i));
            int workingDrawingColor;
            float n9;
            if (!wordOfQuran.getWordString().equals("3101") && !wordOfQuran.getWordString().equals("3102")) {
                this.workingDrawingFontSize = workingDrawingFontSize;
                workingDrawingColor = n5;
                n9 = n7;
            }
            else {
                int workingDrawingFontSize2 = workingDrawingFontSize;
                while (true) {
                    final FontManager fontManager2 = this.fontManager;
                    n9 = n7;
                    if (fontManager2.getCharWidth(StaticObjects.contentWords.get((Integer) list2.get(0)).getWordString(), workingDrawingFontSize2) < -20 + this.canvas.getWidth()) {
                        break;
                    }
                    --workingDrawingFontSize2;
                    n7 = n9;
                    list2 = list;
                }
                this.workingDrawingFontSize = workingDrawingFontSize2;
                workingDrawingColor = n5;
            }
            this.workingDrawingColor = workingDrawingColor;
            this.workingDrawingTextBold = b2;
            ++n8;
            if (this.forModeView == 0) {
                if (sparseArray != null && b3 && sparseArray.indexOfKey(-2 + (n8 + n)) >= 0) {
                    this.workingDrawingColor = -6737152;
                }
            }
            else if (this.forModeView == 1 && b3 && wordOfQuran.getWordMeaning() != null && !wordOfQuran.getWordMeaning().equals("")) {
                this.workingDrawingColor = -6737152;
            }
            if (vector != null && !wordOfQuran.getWordString().contains("2200") && vector.contains(n8 + n - 1)) {
                this.workingDrawingColor = -65536;
            }
            if (wordOfQuran.getWordString().contains("2200")) {
                this.workingDrawingTextBold = false;
            }
            final String[] split = wordOfQuran.getWordString().split(",");
            for (int j = 0; j < split.length; ++j) {
                if ((split[j].startsWith("4") || split[j].startsWith("5")) && !split[j].equals("441") && !split[j].equals("442")) {
                    float n10;
                    if (split.length == 1 && i == 0 && j == 0) {
                        n10 = n9 - this.fontManager.getCharWidth(split[j]);
                    }
                    else {
                        n10 = n9;
                    }
                    if (split.length == 1 && j == 0 && split[0].equals("441")) {
                        n10 = n9 - this.fontManager.getCharWidth(split[j]);
                    }
                    this.drawChar(split[j], n10, n2);
                }
                else {
                    final float n11 = n9 - this.fontManager.getCharWidth(split[j]);
                    this.drawChar(split[j], n11, n2);
                    final String s = split[j];
                    n9 = n11;
                }
            }
            n7 = n9 - this.fontManager.getSpaceWidth();
            ++i;
            list2 = list;
        }
        this.fontManager.getLineHeight();
    }
}