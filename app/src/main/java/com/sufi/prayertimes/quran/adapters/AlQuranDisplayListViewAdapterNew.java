package com.sufi.prayertimes.quran.adapters;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.sufi.prayertimes.R;
import com.sufi.prayertimes.quran.interfaces.AlQuranDisplayListViewAdapterListener;
import com.sufi.prayertimes.quran.models.WordMeaningsRectObj;
import com.sufi.prayertimes.quran.models.WordOfQuran;
import com.sufi.prayertimes.quran.servicesAndWidgets.TextWrapper;
import com.sufi.prayertimes.App;

import com.sufi.prayertimes.quran.FontManager;
import com.sufi.prayertimes.quran.QuranView;
import com.sufi.prayertimes.quran.RichTextView;
import com.sufi.prayertimes.quran.ScrollingStyleQuranActivity;
import com.sufi.prayertimes.quran.StaticObjects;
import com.sufi.prayertimes.quran.TafsirTranslationManager;
import com.sufi.prayertimes.quran.TextRendererNew;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class AlQuranDisplayListViewAdapterNew extends BaseAdapter implements OnTouchListener, OnLongClickListener {
    private static final String TAG = AlQuranDisplayListViewAdapterNew.class.getSimpleName();
    private int RIGHT_LEFT_MARGIN;
    private AlQuranDisplayListViewAdapterListener alQuranDisplayListViewAdapterListener;
    private DisplayMetrics displayMetrics;
    private MotionEvent event;
    private int firstVisibleItem = -1;
    private int mfontColor;
    private FontManager mFontManager;
    private List<List<Integer>> lines;
    private Context mContext;
    private final boolean mNeightReadingMode;
    private int numberOfRowsInt;
    private int mStrokeColor;
    private Boolean strokeEnabled;
    private final boolean mTafsirEnabled;
    private final int mTafsirFontSize;
    private final String mTafsirId;
    @NonNull
    private final TafsirTranslationManager tafsirTranslationManager;
    private final boolean mTextBold;
    private boolean mWordMeaingEnabled;

    public AlQuranDisplayListViewAdapterNew(Context context, FontManager fontManager, int fontColor, Boolean bool, int strokeColor, boolean textBold,
                                            DisplayMetrics displayMetrics, List<List<Integer>> list, int RightLeftMargin, boolean wordMeaingEnabled, boolean tafsirEnabled,
                                            String tafsirId, boolean neightReadingMode, TextWrapper textWrapper, int tafsirFontSize) {
        this.mContext = context;
        this.mFontManager = fontManager;
        this.mfontColor = fontColor;
        this.strokeEnabled = bool;
        this.mStrokeColor = strokeColor;
        this.displayMetrics = displayMetrics;
        this.lines = list;
        this.RIGHT_LEFT_MARGIN = RightLeftMargin;
        this.mWordMeaingEnabled = wordMeaingEnabled;
        this.tafsirTranslationManager = new TafsirTranslationManager(context);
        this.mTafsirEnabled = tafsirEnabled;
        this.mTafsirId = tafsirId;
        this.mTextBold = textBold;
        this.mNeightReadingMode = neightReadingMode;
        this.mTafsirFontSize = tafsirFontSize;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public boolean isEnabled(int i) {
        return false;
    }

    public boolean onLongClick(View view) {
        return false;
    }

    public void setLines(List<List<Integer>> list) {
        lines = list;
    }

    public int getCount() {
        return lines.size();
    }

    public Object getItem(int i) {
        return lines.get(i);
    }

    public void setAlQuranDisplayListViewAdapterListener(AlQuranDisplayListViewAdapterListener alQuranDisplayListViewAdapterListener) {
        this.alQuranDisplayListViewAdapterListener = alQuranDisplayListViewAdapterListener;
    }

    /**
     * @Nullable public View getView(int position, @Nullable View view, ViewGroup viewGroup) {
     * int width;
     * int height;
     * int i5 = 0;
     * int ayahNumber;
     * View convertView;
     * final List<Integer> list = this.lines.get(position);
     * if (view == null) {
     * convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.alquran_screen_row, viewGroup, false);
     * LinearLayout linearLayout = convertView.findViewById(R.id.listViewContainer);
     * //            convertView.setId(R.id.);
     * convertView.setOnTouchListener(this);
     * convertView.setOnLongClickListener(this);
     * View quranViewNew = new QuranView(mContext, fontManager, mfontColor, strokeEnabled, mStrokeColor, mTextBold, TextRendererNew.MODE_VIEW_SCROLLLING,
     * position, mWordMeaingEnabled);
     * quranViewNew.setId(R.id.list_item2);
     * linearLayout.addView(quranViewNew);
     * ((QuranView) quranViewNew).setmFontManager(fontManager);
     * LayoutParams quranViewNewLayoutParams = quranViewNew.getLayoutParams();
     * quranViewNewLayoutParams.width = LayoutParams.MATCH_PARENT;
     * quranViewNewLayoutParams.height = LayoutParams.WRAP_CONTENT;
     * quranViewNew.requestLayout();
     * <p>
     * LayoutParams layoutParams = linearLayout.getLayoutParams();
     * if (layoutParams == null) {
     * width = LayoutParams.MATCH_PARENT;
     * height = LayoutParams.WRAP_CONTENT;
     * layoutParams = new LayoutParams(width, height);
     * } else {
     * width = LayoutParams.MATCH_PARENT;
     * height = LayoutParams.WRAP_CONTENT;
     * layoutParams.width = width;
     * layoutParams.height = height;
     * }
     * //            quranViewNew.setLayoutParams(layoutParams);
     * if (position < 0) {
     * Log.e("Error", "lineNumber<0");
     * }
     * ((QuranView) quranViewNew).setLineIndex(position);
     * ((QuranView) quranViewNew).setWordRectsMap(createWordRectsMap((QuranView) quranViewNew));
     * quranViewNew = new RichTextView(mContext);
     * quranViewNew.setLayoutParams(new LinearLayout.LayoutParams(width, height));
     * quranViewNew.setId(R.id.list_item);
     * ((RichTextView) quranViewNew).setFontSize(mTafsirFontSize);
     * ((RichTextView) quranViewNew).setText("");
     * quranViewNew.setVisibility(View.INVISIBLE);
     * if (mNeightReadingMode) {
     * ((RichTextView) quranViewNew).setFontColor(mContext.getResources().getColor(R.color.textColorPrimary));
     * } else {
     * ((RichTextView) quranViewNew).setFontColor(ViewCompat.MEASURED_STATE_MASK);
     * }
     * linearLayout.addView(quranViewNew);
     * //            convertView = linearLayout;
     * <p>
     * } else {
     * width = LayoutParams.MATCH_PARENT;
     * height = LayoutParams.WRAP_CONTENT;
     * convertView = view;
     * }
     * QuranView quranView2 = convertView.findViewById(R.id.list_item2);
     * double widthInPixels = (double) displayMetrics.widthPixels;
     * double marginRL = (double) RIGHT_LEFT_MARGIN;
     * double density = (double) displayMetrics.density;
     * marginRL *= 2.3d;
     * marginRL *= density;
     * Log.d(TAG, "getView:"+" LIST " + list.toString() + (widthInPixels - marginRL) + "height " + displayMetrics.heightPixels + "LastLineWidth " + i5 );
     * quranView2.setLine(0, list, (int) (widthInPixels - marginRL), displayMetrics.heightPixels, (float) i5);
     * quranView2.setLineIndex(position);
     * //        quranView2.setmFontManager(fontManager);
     * quranView2.setWordRectsMap(createWordRectsMap(quranView2));
     * if (App.highLight && App.currentHighlightWordIndexDurngSura > width) {
     * if ((StaticObjects.contentWords.get(App.currentHighlightWordIndexDurngSura)).getLineIndexOfWord() != position || list.size() <= 0) {
     * highLightWords(quranView2, width);
     * } else {
     * highLightWords(quranView2, App.currentHighlightWordIndexDurngSura - (Integer) list.get(i5));
     * }
     * }
     * if (mTafsirEnabled && App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
     * Object obj;
     * //            RichTextView richTextView = (RichTextView) convertView.findViewById(80097);
     * RichTextView richTextView = convertView.findViewById(R.id.list_item);
     * for (mfontColor = 0; mfontColor < list.size(); mfontColor++) {
     * if ((StaticObjects.contentWords.get((Integer) list.get(mfontColor))).getWordString().contains("2200")) {
     * obj = 1;
     * break;
     * }
     * }
     * obj = null;
     * if (obj != null) {
     * richTextView.setVisibility(View.VISIBLE);
     * mfontColor = (StaticObjects.contentWords.get((Integer) list.get(i5))).getSuraId();
     * ayahNumber = (StaticObjects.contentWords.get((Integer) list.get(i5))).getAyahNumber();
     * if (mfontColor == 1 && ayahNumber == width) {
     * ayahNumber = 1;
     * }
     * richTextView.setText(tafsirTranslationManager.getTranslation(mTafsirId, mfontColor, ayahNumber));
     * richTextView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
     * } else {
     * richTextView.setVisibility(View.INVISIBLE);
     * richTextView.setLayoutParams(new LinearLayout.LayoutParams(width, i5));
     * }
     * }
     * if (App.isSoundPlaying) {
     * if (position == width || list.size() == 0 || (Integer) list.get(i5) >= StaticObjects.contentWords.size()) {
     * return convertView;
     * }
     * int wordIndexOfSura = (StaticObjects.contentWords.get((Integer) list.get(i5))).getWordIndexOfSura();
     * mfontColor = (StaticObjects.contentWords.get((Integer) list.get(list.size() - 1))).getWordIndexOfSura();
     * ayahNumber = (StaticObjects.contentWords.get(App.currentWordIndexHilightForSoundPlay)).getWordIndexOfSura();
     * int i8 = ayahNumber - wordIndexOfSura;
     * if (ayahNumber < wordIndexOfSura || ayahNumber > mfontColor + 1) {
     * highLightWords(quranView2, width);
     * } else if (App.currentSuraId == 9) {
     * highLightWords(quranView2, i8);
     * } else {
     * highLightWords(quranView2, i8);
     * }
     * } else if (!App.highLight) {
     * highLightWords(quranView2, width);
     * }
     * if (firstVisibleItem == width) {
     * return convertView;
     * }
     * if (position < firstVisibleItem || position >= firstVisibleItem + numberOfRowsInt) {
     * convertView.setVisibility(View.INVISIBLE);
     * } else {
     * convertView.setVisibility(View.VISIBLE);
     * }
     * return convertView;
     * }
     */

    //TESTING PERPUSE
    public View getView(final int postion, @Nullable final View view, final ViewGroup viewGroup) {
        final List<Integer> list = this.lines.get(postion);
        LinearLayout linearLayout2;
        int width;
        int height;
        if (view == null) {
            new LinearLayout(mContext);
            final LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.alquran_screen_row, viewGroup, false);
            final int mode_VIEW_SCROLLLING = TextRendererNew.MODE_VIEW_SCROLLLING;
            linearLayout2 = linearLayout;
            final QuranView quranViewNew = new QuranView(mContext, mFontManager, mfontColor, strokeEnabled, mStrokeColor, mTextBold, mode_VIEW_SCROLLLING, postion, mWordMeaingEnabled);
            quranViewNew.setId(R.id.list_item2);
            linearLayout2.addView(quranViewNew);
            quranViewNew.setOnTouchListener(this);
            quranViewNew.setOnLongClickListener(this);
            LayoutParams layoutParams = quranViewNew.getLayoutParams();
            if (layoutParams == null) {
                width = LayoutParams.MATCH_PARENT;;
                height = LayoutParams.WRAP_CONTENT;
                layoutParams = new LayoutParams(width, height);
            } else {
                width = -1;
                height = -2;
                layoutParams.height = height;
                layoutParams.width = width;
            }
            quranViewNew.setLayoutParams(layoutParams);
            if (postion < 0) {
                Log.e("Error", "lineNumber<0");
            }
            quranViewNew.setLineIndex(postion);
            quranViewNew.setWordRectsMap(createWordRectsMap(quranViewNew));
            final RichTextView richTextView = new RichTextView(this.mContext);
            richTextView.setLayoutParams(new LayoutParams(width, 0));
            richTextView.setId(R.id.list_item);
            richTextView.setFontSize(mTafsirFontSize);
            richTextView.setText("");
            richTextView.setVisibility(View.INVISIBLE);
            if (mNeightReadingMode) {
                richTextView.setFontColor(width);
            } else {
                richTextView.setFontColor(-16777216);
            }
            linearLayout2.addView(richTextView);
        } else {
            width = LayoutParams.MATCH_PARENT;
            height = LayoutParams.WRAP_CONTENT;
            linearLayout2 = (LinearLayout) view;
        }
        final QuranView quranViewNew2 = linearLayout2.findViewById(R.id.list_item2);
        final double n3 = displayMetrics.widthPixels;
        final double n4 = RIGHT_LEFT_MARGIN;
        final double n5 = n4 * 2.3;
        final double n6 = displayMetrics.density;
        final double n7 = n5 * n6;
        quranViewNew2.setLine(0, list, (int) (n3 - n7), this.displayMetrics.heightPixels, (float) 0);
        quranViewNew2.setLineIndex(postion);
        quranViewNew2.setWordRectsMap(createWordRectsMap(quranViewNew2));
        if (App.highLight && App.currentHighlightWordIndexDurngSura > width) {
            if (StaticObjects.contentWords.get(App.currentHighlightWordIndexDurngSura).getLineIndexOfWord() == postion && list.size() > 0) {
                this.highLightWords(quranViewNew2, App.currentHighlightWordIndexDurngSura - list.get(0));
            } else {
                this.highLightWords(quranViewNew2, width);
            }
        }
        Label_0798:
        {
            if (mTafsirEnabled && App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
                final RichTextView richTextView2 = linearLayout2.findViewById(R.id.list_item);
                int i = 0;
                while (true) {
                    while (i < list.size()) {
                        if ((StaticObjects.contentWords.get(list.get(i))).getWordString().contains("2200")) {
                            final boolean b = true;
                            if (b) {
                                richTextView2.setVisibility(View.VISIBLE);
                                final int suraId = StaticObjects.contentWords.get(list.get(0)).getSuraId();
                                int ayahNumber = StaticObjects.contentWords.get(list.get(0)).getAyahNumber();
                                if (suraId == 1 && ayahNumber == width) {
                                    ayahNumber = 1;
                                }
                                richTextView2.setText(tafsirTranslationManager.getTranslation(mTafsirId, suraId, ayahNumber));
                                richTextView2.setLayoutParams(new LayoutParams(width, height));
                                break Label_0798;
                            }
                            richTextView2.setVisibility(View.VISIBLE);
                            richTextView2.setLayoutParams(new LayoutParams(width, 0));
                            break Label_0798;
                        } else {
                            ++i;
                        }
                    }
                    final boolean b = false;
                    continue;
                }
            }
        }
        if (App.isSoundPlaying) {
            if (postion == width || list.size() == 0) {
                return linearLayout2;
            }
            if (list.get(0) >= StaticObjects.contentWords.size()) {
                return linearLayout2;
            }
            final int wordIndexOfSura = StaticObjects.contentWords.get(list.get(0)).getWordIndexOfSura();
            final int wordIndexOfSura2 = StaticObjects.contentWords.get(list.get(list.size() - 1)).getWordIndexOfSura();
            final int wordIndexOfSura3 = StaticObjects.contentWords.get(App.currentWordIndexHilightForSoundPlay).getWordIndexOfSura();
            final int n8 = wordIndexOfSura3 - wordIndexOfSura;
            if (wordIndexOfSura3 >= wordIndexOfSura && wordIndexOfSura3 <= wordIndexOfSura2 + 1) {
                if (App.currentSuraId == 9) {
                    this.highLightWords(quranViewNew2, n8);
                } else {
                    this.highLightWords(quranViewNew2, n8);
                }
            } else {
                this.highLightWords(quranViewNew2, width);
            }
        } else if (!App.highLight) {
            this.highLightWords(quranViewNew2, width);
        }
        if (this.firstVisibleItem == width) {
            return linearLayout2;
        }
        if (postion >= this.firstVisibleItem && postion < this.firstVisibleItem + this.numberOfRowsInt) {
            linearLayout2.setVisibility(View.VISIBLE);
            return  linearLayout2;
        }
        linearLayout2.setVisibility(View.INVISIBLE);
        return linearLayout2;
    }
    //TESTING PERPUSE


    private void highLightWords(@NonNull QuranView quranView, int i) {
        Vector<Integer> vector = new Vector<>();
        vector.add(i);
        quranView.setHightWordIndexes(vector);
        quranView.invalidate();
    }

    @NonNull
    public List<WordMeaningsRectObj> createWordRectsMap(@NonNull QuranView quranView) {
        List<WordMeaningsRectObj> arrayList = new ArrayList<>();
        if (quranView.getWidth() == 0 || quranView.getLineIndex() < 0) {
            return arrayList;
        }
        List list = lines.get(quranView.getLineIndex());
        int width = quranView.getWidth() - 5;
        float spaceWidth = mFontManager.getSpaceWidth();
        if (list == null
                || list.size() == 0 || StaticObjects.contentWords == null || (Integer) list.get(0) >= StaticObjects.contentWords.size()
                || (StaticObjects.contentWords.get((Integer) list.get(0)).getWordString().equals("3101"))
                || (StaticObjects.contentWords.get((Integer) list.get(0)).getWordString().equals("3102"))){
            return arrayList;
        }
        int i = width;
        width = 0;
        while (width < list.size() && StaticObjects.contentWords != null && StaticObjects.contentWords.size() != 0 && (Integer) list.get(width) < StaticObjects.contentWords.size()) {
            WordOfQuran wordOfQuran = StaticObjects.contentWords.get((Integer) list.get(width));
            float wordWidthInPixel = wordOfQuran.getWordWidthInPixel();
            float f = ((float) i) - wordWidthInPixel;
            arrayList.add(new WordMeaningsRectObj((int) f, 0, (int) wordWidthInPixel, mFontManager.getLineHeight(), wordOfQuran.getWordIndexOfSura(), quranView.getLineIndex(), width));
            i = (int) (f - spaceWidth);
            width++;
        }
        return arrayList;
    }

    @Nullable
    public WordMeaningsRectObj getWordMeaningRecOfPoint(@NonNull QuranView quranView, @NonNull Point point) {
        List createWordRectsMap = createWordRectsMap(quranView);
        WordMeaningsRectObj wordMeaningsRectObj = null;
        if (createWordRectsMap == null) {
            return null;
        }
        int i = 0;
        while (i < createWordRectsMap.size()) {
            if (point.x >= ((WordMeaningsRectObj) createWordRectsMap.get(i)).getX() && point.x <= ((WordMeaningsRectObj) createWordRectsMap.get(i)).getX() + ((WordMeaningsRectObj) createWordRectsMap.get(i)).getWidth()) {
                wordMeaningsRectObj = (WordMeaningsRectObj) createWordRectsMap.get(i);
                break;
            }
            i++;
        }
        return wordMeaningsRectObj;
    }

    public boolean onTouch(final View view, @NonNull final MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        doOnWordPress(view, motionEvent);
                    }
                }).start();
                break;
            case 2:
                return true;
        }
        return false;
    }

    public void doOnWordPress(View view, @NonNull MotionEvent motionEvent) {
        Log.e(TAG, "Longpress detected");
        if (mWordMeaingEnabled) {
            QuranView quranView = (QuranView) view;
            WordMeaningsRectObj wordMeaningRecOfPoint = getWordMeaningRecOfPoint(quranView, new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
            if (wordMeaningRecOfPoint != null) {
                alQuranDisplayListViewAdapterListener.onWordClick((Integer) ((List) lines.get(quranView.getLineIndex())).get(wordMeaningRecOfPoint.getWordIndexInThisLine()));
            }
        }
    }

    public void setVisibleRowsCount(int i) {
        numberOfRowsInt = i;
    }

    public int getVisibleRowsCount() {
        return numberOfRowsInt;
    }

    public void setFirstVisibleItem(int i) {
        firstVisibleItem = i;
    }

    public int getNumberOfRowsInt() {
        return numberOfRowsInt;
    }

    public int getFirstVisibleItem() {
        return firstVisibleItem;
    }
}
