package com.sufi.prayertimes.quran;

import android.content.Context;
import android.text.TextUtils;

import com.sufi.prayertimes.quran.data.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class OperationClass {
    @NonNull
    private static Integer[] markInFirstAyahOfSura = new Integer[]{Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(20), Integer.valueOf(21), Integer.valueOf(22), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(26), Integer.valueOf(27), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(33), Integer.valueOf(40), Integer.valueOf(46), Integer.valueOf(49), Integer.valueOf(55), Integer.valueOf(56), Integer.valueOf(58), Integer.valueOf(61), Integer.valueOf(65), Integer.valueOf(66), Integer.valueOf(67), Integer.valueOf(68), Integer.valueOf(69), Integer.valueOf(72), Integer.valueOf(75), Integer.valueOf(78), Integer.valueOf(80), Integer.valueOf(82), Integer.valueOf(84), Integer.valueOf(87), Integer.valueOf(90), Integer.valueOf(94)};
    private final Context context;

    public OperationClass(Context context) {
        this.context = context;
    }

    public void getAzjaaWordsMap() {
        List asList = Arrays.asList(markInFirstAyahOfSura);
        List arrayList = new ArrayList();
        DataManager dataManager = new DataManager(this.context);
        for (int i = 1; i <= 114; i++) {
            if (asList.contains(Integer.valueOf(i))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i);
                stringBuilder.append(",0");
                arrayList.add(stringBuilder.toString());
            }
            String[] split = dataManager.getQuranSuraContents(i).split(" ");
            for (int i2 = 0; i2 < split.length; i2++) {
                if (split[i2].contains("442")) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(i);
                    stringBuilder2.append(",");
                    stringBuilder2.append(i2);
                    arrayList.add(stringBuilder2.toString());
                }
            }
        }
    }

    public static void getAzjaacharMap(Context context) {
        List asList = Arrays.asList(markInFirstAyahOfSura);
        Iterable arrayList = new ArrayList();
        DataManager dataManager = new DataManager(context);
        for (int i = 1; i <= 114; i++) {
            int i2 = 0;
            if (asList.contains(Integer.valueOf(i))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i);
                stringBuilder.append(",0");
                ((ArrayList) arrayList).add(stringBuilder.toString());
            }
            String quranSuraContents = dataManager.getQuranSuraContents(i);
            while (true) {
                i2 = quranSuraContents.indexOf("442", i2);
                if (i2 <= -1) {
                    break;
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(i);
                stringBuilder2.append(",");
                stringBuilder2.append(i2 + 3);
                ((ArrayList) arrayList).add(stringBuilder2.toString());
                i2++;
            }
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("{{");
        stringBuilder3.append(TextUtils.join("},{", arrayList));
        stringBuilder3.append("}};");
        stringBuilder3.toString();
    }
}
