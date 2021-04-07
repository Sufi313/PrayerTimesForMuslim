package com.sufi.prayertimes.quran;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;

import com.sufi.prayertimes.quran.models.WordMeaning;
import com.sufi.prayertimes.quran.models.WordMeaningsRectObj;

import java.util.List;
import java.util.Vector;

import androidx.annotation.NonNull;

public class QuranView extends View {
    private static final String TAG = QuranView.class.getSimpleName();
    private float density;
    private int mFontColor;

    private FontManager mFontManager;

    private Vector<Integer> hightWordIndexes;
    private float lastLinewidth;
    private List<Integer> line;
    private int mLineIndex;
    private int pageHieght;
    private int pageWidth;
    Paint mPaint;
    private int startPageWordIndex;
    private int mStrokeColor;
    private Boolean mStrokeEnabled;
    private boolean mTextBold;
    private TextRendererNew mTextRenderer;
    private boolean mWordMeaningEnabled;
    private SparseArray<WordMeaning> wordMeaningsMapOfSura;
    List<WordMeaningsRectObj> wordRectsMap;

    public QuranView(@NonNull Context context, @NonNull FontManager fontManager, int fontColor, Boolean strokeEnabled, int strokeColor, boolean textBold,
                     int textRenderer, int lineIndex, boolean wordMeaningEnabled) {
        super(context);
        this.mFontManager = fontManager;
        this.mFontColor = fontColor;
        this.mStrokeColor = strokeColor;
        this.mStrokeEnabled = strokeEnabled;
        this.mTextBold = textBold;
        this.mLineIndex = lineIndex;
        this.mWordMeaningEnabled = wordMeaningEnabled;
        this.mTextRenderer = new TextRendererNew(textRenderer);
        this.mPaint = fontManager.getPaint();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        this.density = displayMetrics.density;
    }

    public void setLine(int startWordIndex, List<Integer> list, int PageWidth, int PageHeight, float LastLineWidth) {
        line = list;
        pageWidth = PageWidth;
        pageHieght = PageHeight;
        lastLinewidth = LastLineWidth;
        startPageWordIndex = startWordIndex;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        if (line != null && line.size() != 0) {
            int calculateLineWidth = (int) calculateLineWidth(line);
            TextRendererNew textRendererNew = mTextRenderer;
            Paint paint = mPaint;
            FontManager fontManager = mFontManager;
            List list = line;
            Vector vector = hightWordIndexes;
            SparseArray sparseArray = wordMeaningsMapOfSura;
            int i = startPageWordIndex;
            float width = (float) (getWidth() - ((getWidth() - calculateLineWidth) / 2));
            double fontSize = (double) fontManager.getFontSize();
            textRendererNew.renderText(canvas, paint, fontManager, list, vector, sparseArray, i, width, (float) (fontSize * 1.5d),
                    lastLinewidth, density, mStrokeEnabled, mStrokeColor, mTextBold, mFontColor, mFontManager.getFontSize(), mLineIndex, mWordMeaningEnabled);
        }
    }

    private int measureWidth(int i) {
        int mode = MeasureSpec.getMode(i);
        i = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return i;
        }
        int i2 = pageWidth;
        return mode == Integer.MIN_VALUE ? Math.min(i2, i) : i2;
    }

    private int measureHeight(int i) {
        int mode = MeasureSpec.getMode(i);
        i = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return i;
        }
        double fontSize = (double) mFontManager.getFontSize();
        int i2 = (int) (fontSize * 2.4d);
        return mode == Integer.MIN_VALUE ? Math.min(i2, i) : i2;
    }

    public float calculateLineWidth(@NonNull List<Integer> list) {
        float f = 0.0f;
        if (StaticObjects.contentWords == null) {
            return 0.0f;
        }
        for (int i = 0; i < list.size(); i++) {
            f = (f + (StaticObjects.contentWords.get(list.get(i))).getWordWidthInPixel()) + mFontManager.getSpaceWidth();
        }
        return f - mFontManager.getSpaceWidth();
    }

    public List<Integer> getLinesStrings() {
        return line;
    }

    public int getLineIndex() {
        return mLineIndex;
    }

    public void setLineIndex(int lineIndex) {
        mLineIndex = lineIndex;
    }

    public void setHightWordIndexes(Vector<Integer> vector) {
        hightWordIndexes = vector;
    }

    public void setWordMeaningsMapOfSura(SparseArray<WordMeaning> sparseArray) {
        wordMeaningsMapOfSura = sparseArray;
    }

    public List<WordMeaningsRectObj> getWordRectsMap() {
        return wordRectsMap;
    }

    public void setWordRectsMap(List<WordMeaningsRectObj> list) {
        wordRectsMap = list;
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    public FontManager getmFontManager() {
        return mFontManager;
    }

    public void setmFontManager(FontManager fontManager) {
        this.mFontManager = fontManager;
    }
}
