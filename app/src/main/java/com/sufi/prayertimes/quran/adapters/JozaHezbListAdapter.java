package com.sufi.prayertimes.quran.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sufi.prayertimes.App;

import com.sufi.prayertimes.R;
import com.sufi.prayertimes.quran.models.JozaInfo;
import com.sufi.prayertimes.quran.ScrollingStyleQuranActivity;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JozaHezbListAdapter extends BaseAdapter implements OnClickListener {
    public CheckBox dontShowAgain;
    private Context mContext;
    private List<JozaInfo> mListAppInfo;
    private static String QURAN_LAYOUT_STYLE = "scrolling";
    public long getItemId(int i) {
        return (long) i;
    }

    public JozaHezbListAdapter(Context context, List<JozaInfo> list) {
        mContext = context;
        mListAppInfo = list;
    }

    public int getCount() {
        return mListAppInfo.size();
    }

    public Object getItem(int i) {
        return mListAppInfo.get(i);
    }

    @Nullable
    public View getView(int i, @Nullable View view, ViewGroup viewGroup) {
        JozaInfo jozaInfo = (JozaInfo) mListAppInfo.get(i);
        if (view == null) {
            view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ajzaa_ahzab_row, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.btn_joza);
        TextView textView2 = (TextView) view.findViewById(R.id.btn_hezb1);
        TextView textView3 = (TextView) view.findViewById(R.id.btn_hezb1_1_4);
        TextView textView4 = (TextView) view.findViewById(R.id.btn_hezb1_1_2);
        TextView textView5 = (TextView) view.findViewById(R.id.btn_hezb1_3_4);
        TextView textView6 = (TextView) view.findViewById(R.id.btn_hezb2);
        TextView textView7 = (TextView) view.findViewById(R.id.btn_hezb2_1_4);
        TextView textView8 = (TextView) view.findViewById(R.id.btn_hezb2_1_2);
        TextView textView9 = (TextView) view.findViewById(R.id.btn_hezb2_3_4);
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        textView9.setOnClickListener(this);
        textView.setTag(Integer.valueOf(i));
        textView2.setTag(Integer.valueOf(i));
        textView3.setTag(Integer.valueOf(i));
        textView4.setTag(Integer.valueOf(i));
        textView5.setTag(Integer.valueOf(i));
        textView6.setTag(Integer.valueOf(i));
        textView7.setTag(Integer.valueOf(i));
        textView8.setTag(Integer.valueOf(i));
        textView9.setTag(Integer.valueOf(i));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mContext.getString(R.string.joza));
        stringBuilder.append(" ");
        stringBuilder.append(i + 1);
        textView.setText(stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(mContext.getString(R.string.hezb));
        stringBuilder2.append(" ");
        i *= 2;
        stringBuilder2.append(i + 1);
        textView2.setText(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(mContext.getString(R.string.hezb));
        stringBuilder2.append(" ");
        stringBuilder2.append(i + 2);
        textView6.setText(stringBuilder2.toString());
        return view;
    }

    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_hezb1:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 0);
                return;
            case R.id.btn_hezb1_1_2:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 2);
                return;
            case R.id.btn_hezb1_1_4:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 1);
                return;
            case R.id.btn_hezb1_3_4:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 3);
                return;
            case R.id.btn_hezb2:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 4);
                return;
            case R.id.btn_hezb2_1_2:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 6);
                return;
            case R.id.btn_hezb2_1_4:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 5);
                return;
            case R.id.btn_hezb2_3_4:
                openContentNew(ScrollingStyleQuranActivity.QUARTER_MODE, (((Integer) view.getTag()).intValue() * 8) + 7);
                return;
            case R.id.btn_joza:
                openContentNew(ScrollingStyleQuranActivity.AJZAA_MODE, ((Integer) view.getTag()).intValue() + 1);
                return;
            default:
                return;
        }
    }

    private void openjozaActivityScrolling(int i, int i2) {
        Intent intent = new Intent(mContext, ScrollingStyleQuranActivity.class);
        App.browsingContent = i;
        if (i == ScrollingStyleQuranActivity.AJZAA_MODE) {
            App.currentJozaId = i2;
        } else if (i == ScrollingStyleQuranActivity.QUARTER_MODE) {
            App.currentQuarterId = i2;
        }
        App.browsingMethod = ScrollingStyleQuranActivity.SCROLL_MODE;
        App.currentHighlightWordIndexDurngJoza = 0;
        App.currentWordIndexDurngJozaForRotationScrolling = 0;
        App.currentWordIndexForSoundPlaying = 0;
        App.currentWordIndexHilightForSoundPlay = 0;
        App.currentWordIndexDurngSuraForRotationScrolling = 0;
        App.highLight = false;
        App.haveToPlayReciter = false;
        App.isSoundPlaying = false;
        mContext.startActivity(intent);
    }

    private void openjozaActivityPaging(int i, int i2) {
        Intent intent = new Intent(mContext, ScrollingStyleQuranActivity.class);
        App.browsingContent = i;
        if (i == ScrollingStyleQuranActivity.AJZAA_MODE) {
            App.currentJozaId = i2;
        } else if (i == ScrollingStyleQuranActivity.QUARTER_MODE) {
            App.currentQuarterId = i2;
        }
        App.browsingMethod = ScrollingStyleQuranActivity.PAGING_MODE;
        App.currentJozaId = i2;
        App.currentHighlightWordIndexDurngJoza = 0;
        App.currentWordIndexDurngJozaForRotationScrolling = 0;
        App.currentWordIndexForSoundPlaying = 0;
        App.currentWordIndexHilightForSoundPlay = 0;
        App.currentWordIndexDurngSuraForRotationScrolling = 0;
        App.highLight = false;
        App.haveToPlayReciter = false;
        App.isSoundPlaying = false;
        mContext.startActivity(intent);
    }

    private void openContentNew(final int i, final int i2) {
        AppSettings appSettings = AppSettings.getInstance(mContext);
        if (appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD)!= null){
            QURAN_LAYOUT_STYLE = appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD);
        }else{
            appSettings.set(AppSettings.Key.AL_QURAN_BROWSING_METHOD,"scrolling");
        }
        if (QURAN_LAYOUT_STYLE.equalsIgnoreCase("scrolling")) {
            openjozaActivityScrolling(i, i2);
        } else if (QURAN_LAYOUT_STYLE.equalsIgnoreCase("paging")) {
            openjozaActivityPaging(i, i2);
        }  else {
            openjozaActivityScrolling(i, i2);

        }
    }
}
