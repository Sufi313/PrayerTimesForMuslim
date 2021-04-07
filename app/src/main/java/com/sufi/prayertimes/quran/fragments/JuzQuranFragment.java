package com.sufi.prayertimes.quran.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.sufi.prayertimes.R;
import com.sufi.prayertimes.quran.BackgroundView;
import com.sufi.prayertimes.quran.adapters.JozaHezbListAdapter;
import com.sufi.prayertimes.quran.models.JozaInfo;

import java.util.ArrayList;
import java.util.List;


public class JuzQuranFragment extends Fragment {
    private BackgroundView backgroundView;

    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_juz_quran, viewGroup, false);
        ((ListView) inflate.findViewById(R.id.lst_joza_hezb_tab)).setAdapter(new JozaHezbListAdapter(getContext(), getJozasList()));
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.relativeLayout);
        this.backgroundView = new BackgroundView(getContext());
        relativeLayout.addView(this.backgroundView, 1, new RelativeLayout.LayoutParams(-1, -1));
        return inflate;
    }

    @NonNull
    private List<JozaInfo> getJozasList() {
        List<JozaInfo> arrayList = new ArrayList();
        int i = 1;
        while (i <= 30) {
            String str = i <= 16 ? "jozaa1.ttf" : "jozaa2.ttf";
            String ch = Character.toString((char) (61440 + i));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i);
            stringBuilder.append("");
            arrayList.add(new JozaInfo(i, ch, str, getUnicodeNumber(stringBuilder.toString())));
            i++;
        }
        return arrayList;
    }

    @NonNull
    private String getUnicodeNumber(@NonNull String str) {
        String str2 = "";
        int i = 0;
        while (i < str.length()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            int i2 = i + 1;
            stringBuilder.append(Character.toString((char) (Integer.parseInt(str.substring(i, i2)) + 61440)));
            str2 = stringBuilder.toString();
            i = i2;
        }
        return str2;
    }

    public void onResume() {
        super.onResume();
        this.backgroundView.init();
        this.backgroundView.invalidate();
    }
}
