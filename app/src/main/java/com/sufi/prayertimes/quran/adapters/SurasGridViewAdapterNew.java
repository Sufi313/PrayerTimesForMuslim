package com.sufi.prayertimes.quran.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sufi.prayertimes.R;
import com.sufi.prayertimes.quran.models.SuraInfo;

import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.Nullable;

public class SurasGridViewAdapterNew extends BaseAdapter {
    private Context mContext;
    private List<SuraInfo> mListAppInfo;
    private HashMap<String, Typeface> typefaceHashMap = new HashMap();

    public long getItemId(int i) {
        return (long) i;
    }

    public SurasGridViewAdapterNew(Context context, List<SuraInfo> list) {
        mContext = context;
        mListAppInfo = list;
        for (int i = 1; i < 6; i++) {
            AssetManager assets = mContext.getAssets();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fonts/suras");
            stringBuilder.append(i);
            stringBuilder.append(".ttf");
            Typeface createFromAsset = Typeface.createFromAsset(assets, stringBuilder.toString());
            HashMap hashMap = typefaceHashMap;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("suras");
            stringBuilder2.append(i);
            stringBuilder2.append(".ttf");
            hashMap.put(stringBuilder2.toString(), createFromAsset);
        }
    }

    public int getCount() {
        return mListAppInfo.size();
    }

    public Object getItem(int i) {
        return mListAppInfo.get(i);
    }

    @Nullable
    public View getView(int i, @Nullable View view, ViewGroup viewGroup) {
        SuraInfo suraInfo = (SuraInfo) mListAppInfo.get(i);
        if (view == null) {
            view = new View(mContext);
            view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.suras_grid_row_layout_new, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.txt_sura_num_en);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(suraInfo.getSuraId());
        stringBuilder.append("");
        textView.setText(stringBuilder.toString());
        ((TextView) view.findViewById(R.id.txt_sura_name_en)).setText(suraInfo.getSuraNameEn());
        textView = (TextView) view.findViewById(R.id.txt_sura_num_ar);
        textView.setText(suraInfo.getUnicodeSuraNumber());
        textView.setTypeface((Typeface) typefaceHashMap.get("suras5.ttf"));
        textView = (TextView) view.findViewById(R.id.txt_sura_name_ar);
        textView.setText(suraInfo.getSuraTitle());
        textView.setTypeface((Typeface) typefaceHashMap.get(suraInfo.getFontName()));
        ((ImageView) view.findViewById(R.id.img_sura_kind)).setImageResource(suraInfo.getSuraKind() == 0 ? R.drawable.ic_kaaba_mecca : R.drawable.ic_compass);
        return view;
    }
}
