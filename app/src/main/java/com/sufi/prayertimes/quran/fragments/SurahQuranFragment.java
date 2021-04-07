package com.sufi.prayertimes.quran.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.sufi.prayertimes.App;

import com.sufi.prayertimes.R;
import com.sufi.prayertimes.quran.BackgroundView;
import com.sufi.prayertimes.quran.ScrollingStyleQuranActivity;
import com.sufi.prayertimes.quran.models.SuraInfo;
import com.sufi.prayertimes.quran.adapters.SurasGridViewAdapterNew;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;

import java.util.List;
import java.util.ArrayList;

import android.content.Intent;
import android.widget.GridView;

public class SurahQuranFragment extends Fragment implements AdapterView.OnItemClickListener {
//    public static CheckBox dontShowAgain;
    private static SurasGridViewAdapterNew surasGridViewAdapter;
    private Animation animFadein;
    private BackgroundView backgroundView;
    private GridView surasGridView;
    private static String QURAN_LAYOUT_STYLE = "scrolling";

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_surah_quran, viewGroup, false);
        surasGridView = inflate.findViewById(R.id.suras_gridview);
        surasGridView.setNumColumns(1);
        surasGridViewAdapter = new SurasGridViewAdapterNew(getContext(), getSurasList());
        surasGridView.setAdapter(surasGridViewAdapter);
        surasGridView.setOnItemClickListener(this);
        RelativeLayout relativeLayout = inflate.findViewById(R.id.relativeLayout);
        backgroundView = new BackgroundView(getContext());
        relativeLayout.addView(backgroundView, 1, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return inflate;
    }

    @NonNull
    public static List<SuraInfo> getSurasList() {
        List<SuraInfo> arrayList = new ArrayList<>();
        for (int i = 1; i <= 114; i++) {
            String str = "";
            if (i <= 23) {
                str = "suras1.ttf";
            } else if (i <= 47) {
                str = "suras2.ttf";
            } else if (i <= 69) {
                str = "suras3.ttf";
            } else if (i <= 92) {
                str = "suras4.ttf";
            } else if (i <= 114) {
                str = "suras5.ttf";
            }
            String ch = Character.toString((char) (61440 + i));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i);
            stringBuilder.append("");
            arrayList.add(new SuraInfo(i, ch, str, getUnicodeNumber(stringBuilder.toString())));
        }
        return arrayList;
    }

    @NonNull
    public static String getUnicodeNumber(@NonNull String str) {
        String unicodeNumber = "";
        int i = 0;
        while (i < str.length()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(unicodeNumber);
            int i2 = i + 1;
            stringBuilder.append(Character.toString((char) (Integer.parseInt(str.substring(i, i2)) + 61440)));
            unicodeNumber = stringBuilder.toString();
            i = i2;
        }
        return unicodeNumber;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        openSura(getContext(), i, SurahQuranFragment.class.getName());
    }

    public static void openSuraActivityScrolling(@NonNull Context context, int i, String str) {
        SuraInfo suraInfo = (SuraInfo) getSurasList().get(i);
        Intent intent = new Intent(context, ScrollingStyleQuranActivity.class);
        intent.putExtra("FromClassName", str);
        App.browsingContent = ScrollingStyleQuranActivity.SURAS_MODE;
        App.browsingMethod = ScrollingStyleQuranActivity.SCROLL_MODE;
        App.currentSuraId = suraInfo.getSuraId();
        App.currentHighlightWordIndexDurngSura = -1;
        App.currentWordIndexDurngSuraForRotationScrolling = -1;
        App.currentWordIndexForSoundPlaying = 0;
        App.highLight = false;
        context.startActivity(intent);
    }

    public static void openSuraActivityPaging(@NonNull Context context, int i, String str) {
        SuraInfo suraInfo = getSurasList().get(i);
        Intent intent = new Intent(context, ScrollingStyleQuranActivity.class);
        intent.putExtra("FromClassName", str);
        App.browsingContent = ScrollingStyleQuranActivity.SURAS_MODE;
        App.browsingMethod = ScrollingStyleQuranActivity.PAGING_MODE;
        App.currentSuraId = suraInfo.getSuraId();
        App.currentHighlightWordIndexDurngSura = -1;
        App.currentWordIndexDurngSuraForRotationScrolling = -1;
        App.currentWordIndexForSoundPlaying = 0;
        App.highLight = false;
        context.startActivity(intent);
    }


    public static void openSura(@NonNull final Context context, final int i, final String str) {
        AppSettings appSettings = AppSettings.getInstance(context);
        if (appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD)!= null){
            QURAN_LAYOUT_STYLE = appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD);
        }else{
            appSettings.set(AppSettings.Key.AL_QURAN_BROWSING_METHOD,"scrolling");
        }
        if (QURAN_LAYOUT_STYLE.equalsIgnoreCase("scrolling")) {
            openSuraActivityScrolling(context, i, str);
        } else if (QURAN_LAYOUT_STYLE.equalsIgnoreCase("paging")) {
            openSuraActivityPaging(context, i, str);
        }  else {
            openSuraActivityScrolling(context, i, str);
        }
    }

    public void onResume() {
        super.onResume();
        backgroundView.init();
        backgroundView.invalidate();
    }
}
