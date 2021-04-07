package com.sufi.prayertimes.quran.servicesAndWidgets;

import android.content.Context;

import com.sufi.prayertimes.quran.FontManager;
import com.sufi.prayertimes.quran.StaticObjects;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import io.reactivex.annotations.NonNull;

public class JustifierNew
{
    private Vector<String> canInsetKashidaAfter;
    private FontManager fontsManager;
    private float kashidaWidth;
    private int recallCounter;

    public JustifierNew(final Context context, final FontManager fontsManager) {
        this.kashidaWidth = 0.0f;
        this.canInsetKashidaAfter = this.getCanAddKashidaAfterItArray();
        this.fontsManager = fontsManager;
    }

    @NonNull
    private String justify(@NonNull String s, int i) {
        if (i <= 0) {
            return s;
        }
        int n = -1 + s.length();
        do {
            final int lastIndex = s.lastIndexOf(44, n);
            if (this.canInsetKashidaAfter.indexOf(s.substring(lastIndex + 1, n + 1)) != -1) {
                while (true) {
                    final int n2 = n + 2;
                    final int index = s.indexOf(44, n2);
                    if (index == -1) {
                        break;
                    }
                    final String substring = s.substring(n2, index);
                    if ((!substring.startsWith("4") && !substring.startsWith("5")) || substring.equals("441") || substring.equals("442")) {
                        break;
                    }
                    n = index - 1;
                }
                s = this.insertString(s, ",1128", n + 1);
                --i;
            }
            n = lastIndex - 1;
            if (lastIndex == -1) {
                break;
            }
        } while (i > 0);
        if (i > 0 && this.recallCounter < 50) {
            ++this.recallCounter;
            s = this.justify(s, i);
        }
        return s;
    }

    private boolean justifyWordNow(final int n) {
        final String[] split = StaticObjects.contentWords.get(n).getWordString().split(",");
        int n2 = 0;
        int i;
        boolean b;
        while (true) {
            final int length = split.length;
            i = 1;
            if (n2 >= length - i) {
                b = false;
                break;
            }
            if (this.canInsetKashidaAfter.indexOf(split[n2]) > -1) {
                final int n3 = n2 + 1;
                final String s = split[n3];
                if ((s.startsWith("4") || s.startsWith("5")) && !s.equals("441") && !s.equals("442")) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(split[n3]);
                    sb.append(",1128");
                    split[n3] = sb.toString();
                }
                else {
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append(split[n2]);
                    sb2.append(",1128");
                    split[n2] = sb2.toString();
                }
                StaticObjects.contentWords.get(n).setWordWidthInPixel(StaticObjects.contentWords.get(n).getWordWidthInPixel() + this.getKashidaWidth());
                b = true;
                break;
            }
            ++n2;
        }
        if (b) {
            String string = split[0];
            while (i < split.length) {
                final StringBuilder sb3 = new StringBuilder();
                sb3.append(string);
                sb3.append(",");
                sb3.append(split[i]);
                string = sb3.toString();
                ++i;
            }
            StaticObjects.contentWords.get(n).setWordString(string);
        }
        return b;
    }

    private void justifyLine(@NonNull final List<Integer> list, final double n, final double n2) {
        final double n3 = n2 - n;
        final double n4 = this.getKashidaWidth();
        int n5 = (int)(n3 / n4);
        this.recallCounter = 0;
        Label_0032:
        while (true) {
            int n6 = 0;
            while (n5 > 0 && this.recallCounter < 50) {
                if (this.justifyWordNow(list.get(n6))) {
                    --n5;
                }
                ++this.recallCounter;
                if (++n6 >= list.size()) {
                    continue Label_0032;
                }
            }
            break;
        }
    }

    public void justifyLines(@NonNull final List<List<Integer>> list, final int n, final int n2) {
        Label_0247:
        for (int size = list.size(), i = 0; i < size; ++i) {
            final List<Integer> list2 = list.get(i);
            if (list2.size() != 0 && i + 1 != list.size()) {
                final int size2 = list2.size();
                int n3 = 1;
                if (size2 == n3) {
                    if ((StaticObjects.contentWords.get(list2.get(0))).equals("3101")) {
                        continue;
                    }
                    if ((StaticObjects.contentWords.get(list2.get(0))).equals("3102")) {
                        continue;
                    }
                }
                Label_0223: {
                    if (n2 == n3) {
                        int j = 0;
                        while (true) {
                            while (j < list2.size()) {
                                if ((StaticObjects.contentWords.get(list2.get(j))).getWordString().contains("2200")) {
                                    if (n3 != 0) {
                                        continue Label_0247;
                                    }
                                    break Label_0223;
                                }
                                else {
                                    ++j;
                                }
                            }
                            n3 = 0;
                        }
                    }
                }
                justifyLine(list2, calculateLineWidth(list.get(i)), n);
            }
        }
    }

    @NonNull
    private Vector<String> getCanAddKashidaAfterItArray() {
        return new Vector<>(Arrays.asList("147", "148", "154", "155", "159", "163", "164", "170", "171", "175", "179", "180", "186", "187", "191", "195", "196", "1102", "1103", "1109", "1110", "1115", "1116", "1122", "1123", "1130", "1131", "1153", "1161", "1196", "1177", "1185", "1193", "1202", "1210", "1219", "1220", "1223", "1224", "1227", "1230", "1232", "1238", "1241", "1243", "1249", "237", "241", "246", "250", "251", "254", "261", "266", "272", "273", "274", "275", "282", "283", "289", "290", "294", "2100", "2103", "2104", "2107", "2108", "2131", "2132", "2138", "2139", "2143", "2157", "2158", "2164", "2165", "2171", "2172", "2173", "2179", "2180", "2183", "2185", "2187", "2189", "2191", "2193", "2195", "2218", "2219", "2220", "2255", "349", "350", "356", "357", "360", "363", "364", "371"));
    }

    private float getKashidaWidth() {
        if (this.kashidaWidth == 0.0f) {
            this.kashidaWidth = this.fontsManager.getKashidaWidth();
        }
        return this.kashidaWidth;
    }

    private String insertString(@NonNull final String s, final String s2, final int n) {
        final StringBuffer sb = new StringBuffer(s);
        sb.insert(n, s2);
        return sb.toString();
    }

    public float calculateLineWidth(@NonNull final List<Integer> list) {
        float n = 0.0f;
        for (int i = 0; i < list.size(); ++i) {
            n = n + (StaticObjects.contentWords.get(list.get(i))).getWordWidthInPixel() + fontsManager.getSpaceWidth();
        }
        return n - fontsManager.getSpaceWidth();
    }
}
