package com.sufi.prayertimes.quran;

import android.content.Context;
import android.os.Build;
import android.util.SparseIntArray;

import com.sufi.prayertimes.quran.data.DataManager;
import com.sufi.prayertimes.quran.models.WordOfQuran;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.reactivex.annotations.NonNull;

public class SurasAjzaaManager
{
    @NonNull
    private static int[][] jozahezbCharMap;
    private static int[][] jozahezbWordMap;
    private final Context context;
    private SparseIntArray suraGenaricWordIndexMap;

    static {
        SurasAjzaaManager.jozahezbCharMap = new int[][] { { 1, 0 }, { 2, 12711 }, { 2, 24368 }, { 2, 33303 }, { 2, 45091 }, { 2, 58862 }, { 2, 69628 }, { 2, 81873 }, { 2, 94675 }, { 2, 106392 }, { 2, 119444 }, { 2, 132503 }, { 2, 144132 }, { 2, 157601 }, { 2, 172238 }, { 2, 182299 }, { 2, 192488 }, { 2, 205200 }, { 2, 213018 }, { 2, 225391 }, { 3, 8383 }, { 3, 20733 }, { 3, 33101 }, { 3, 46048 }, { 3, 58560 }, { 3, 71419 }, { 3, 83065 }, { 3, 95631 }, { 3, 109921 }, { 3, 120329 }, { 4, 0 }, { 4, 10463 }, { 4, 22797 }, { 4, 33877 }, { 4, 49338 }, { 4, 59650 }, { 4, 71421 }, { 4, 84007 }, { 4, 95139 }, { 4, 108577 }, { 4, 118744 }, { 4, 129251 }, { 5, 0 }, { 5, 14143 }, { 5, 27638 }, { 5, 37366 }, { 5, 49408 }, { 5, 62456 }, { 5, 73305 }, { 5, 86045 }, { 5, 95630 }, { 6, 6679 }, { 6, 20526 }, { 6, 34493 }, { 6, 45404 }, { 6, 60463 }, { 6, 70743 }, { 6, 82011 }, { 6, 92062 }, { 6, 101658 }, { 7, 0 }, { 7, 15190 }, { 7, 27883 }, { 7, 39689 }, { 7, 54425 }, { 7, 68419 }, { 7, 81792 }, { 7, 94215 }, { 7, 108389 }, { 7, 119857 }, { 8, 0 }, { 8, 11692 }, { 8, 23061 }, { 8, 36697 }, { 9, 0 }, { 9, 12581 }, { 9, 24071 }, { 9, 34816 }, { 9, 43064 }, { 9, 56335 }, { 9, 68087 }, { 9, 81606 }, { 9, 91239 }, { 10, 6640 }, { 10, 19387 }, { 10, 34809 }, { 10, 44407 }, { 10, 55437 }, { 11, 2524 }, { 11, 14264 }, { 11, 24093 }, { 11, 35916 }, { 11, 48568 }, { 11, 61749 }, { 12, 3142 }, { 12, 15548 }, { 12, 31528 }, { 12, 44910 }, { 12, 58687 }, { 13, 3418 }, { 13, 14927 }, { 13, 25763 }, { 14, 6417 }, { 14, 18464 }, { 15, 0 }, { 15, 13760 }, { 16, 0 }, { 16, 13275 }, { 16, 24811 }, { 16, 37847 }, { 16, 47594 }, { 16, 59732 }, { 17, 0 }, { 17, 12108 }, { 17, 25763 }, { 17, 37655 }, { 17, 52153 }, { 18, 6969 }, { 18, 19818 }, { 18, 30327 }, { 18, 42050 }, { 18, 53354 }, { 19, 7213 }, { 19, 21976 }, { 20, 0 }, { 20, 14867 }, { 20, 27023 }, { 20, 38285 }, { 21, 0 }, { 21, 10988 }, { 21, 21298 }, { 21, 33762 }, { 22, 0 }, { 22, 11721 }, { 22, 23263 }, { 22, 35122 }, { 23, 0 }, { 23, 13333 }, { 23, 26235 }, { 24, 0 }, { 24, 10732 }, { 24, 24309 }, { 24, 36653 }, { 25, 0 }, { 25, 10179 }, { 25, 23611 }, { 26, 0 }, { 26, 14179 }, { 26, 26273 }, { 26, 42043 }, { 27, 0 }, { 27, 12103 }, { 27, 25714 }, { 27, 37037 }, { 28, 5336 }, { 28, 16596 }, { 28, 30688 }, { 28, 43932 }, { 29, 0 }, { 29, 13309 }, { 29, 24931 }, { 30, 0 }, { 30, 14794 }, { 30, 27557 }, { 31, 10986 }, { 32, 4096 }, { 33, 0 }, { 33, 10827 }, { 33, 19723 }, { 33, 34418 }, { 33, 42778 }, { 34, 5732 }, { 34, 15180 }, { 34, 28203 }, { 35, 10441 }, { 35, 24161 }, { 36, 8393 }, { 36, 19536 }, { 37, 4410 }, { 37, 16944 }, { 37, 29885 }, { 38, 6252 }, { 38, 19096 }, { 39, 5432 }, { 39, 18807 }, { 39, 30584 }, { 40, 0 }, { 40, 10227 }, { 40, 22454 }, { 40, 35008 }, { 41, 2853 }, { 41, 12193 }, { 41, 25179 }, { 42, 5865 }, { 42, 17005 }, { 42, 29241 }, { 43, 8404 }, { 43, 21520 }, { 44, 3610 }, { 45, 4171 }, { 46, 0 }, { 46, 13369 }, { 47, 4551 }, { 47, 18076 }, { 48, 11368 }, { 49, 0 }, { 49, 9845 }, { 50, 7884 }, { 51, 6300 }, { 52, 5090 }, { 53, 4488 }, { 54, 2187 }, { 55, 0 }, { 56, 0 }, { 56, 12859 }, { 57, 9961 }, { 58, 0 }, { 58, 11864 }, { 59, 8419 }, { 60, 6098 }, { 61, 0 }, { 63, 1522 }, { 65, 0 }, { 66, 0 }, { 67, 0 }, { 68, 0 }, { 69, 0 }, { 70, 3094 }, { 72, 0 }, { 73, 4795 }, { 75, 0 }, { 76, 5280 }, { 78, 0 }, { 80, 0 }, { 82, 0 }, { 84, 0 }, { 87, 0 }, { 90, 0 }, { 94, 0 }, { 100, 1120 }, { 114, 761 } };
        SurasAjzaaManager.jozahezbWordMap = new int[][] { { 1, 0 }, { 2, 357 }, { 2, 678 }, { 2, 909 }, { 2, 1245 }, { 2, 1622 }, { 2, 1921 }, { 2, 2286 }, { 2, 2642 }, { 2, 2956 }, { 2, 3331 }, { 2, 3674 }, { 2, 3999 }, { 2, 4378 }, { 2, 4769 }, { 2, 5039 }, { 2, 5341 }, { 2, 5720 }, { 2, 5934 }, { 2, 6283 }, { 3, 242 }, { 3, 597 }, { 3, 949 }, { 3, 1317 }, { 3, 1674 }, { 3, 2030 }, { 3, 2345 }, { 3, 2689 }, { 3, 3111 }, { 3, 3407 }, { 4, 0 }, { 4, 282 }, { 4, 627 }, { 4, 931 }, { 4, 1366 }, { 4, 1661 }, { 4, 2005 }, { 4, 2341 }, { 4, 2660 }, { 4, 3058 }, { 4, 3338 }, { 4, 3623 }, { 5, 0 }, { 5, 368 }, { 5, 746 }, { 5, 1030 }, { 5, 1359 }, { 5, 1709 }, { 5, 2028 }, { 5, 2367 }, { 5, 2639 }, { 6, 193 }, { 6, 596 }, { 6, 1004 }, { 6, 1321 }, { 6, 1738 }, { 6, 2023 }, { 6, 2337 }, { 6, 2613 }, { 6, 2886 }, { 7, 0 }, { 7, 421 }, { 7, 779 }, { 7, 1105 }, { 7, 1514 }, { 7, 1910 }, { 7, 2264 }, { 7, 2601 }, { 7, 2967 }, { 7, 3287 }, { 8, 0 }, { 8, 321 }, { 8, 632 }, { 8, 1017 }, { 9, 0 }, { 9, 340 }, { 9, 650 }, { 9, 943 }, { 9, 1180 }, { 9, 1516 }, { 9, 1843 }, { 9, 2215 }, { 9, 2493 }, { 10, 186 }, { 10, 549 }, { 10, 1003 }, { 10, 1306 }, { 10, 1610 }, { 11, 77 }, { 11, 417 }, { 11, 712 }, { 11, 1064 }, { 11, 1434 }, { 11, 1810 }, { 12, 89 }, { 12, 446 }, { 12, 902 }, { 12, 1293 }, { 12, 1711 }, { 13, 97 }, { 13, 422 }, { 13, 737 }, { 14, 185 }, { 14, 531 }, { 15, 0 }, { 15, 400 }, { 16, 0 }, { 16, 370 }, { 16, 704 }, { 16, 1083 }, { 16, 1361 }, { 16, 1704 }, { 17, 0 }, { 17, 326 }, { 17, 713 }, { 17, 1048 }, { 17, 1473 }, { 18, 205 }, { 18, 561 }, { 18, 864 }, { 18, 1206 }, { 18, 1544 }, { 19, 224 }, { 19, 656 }, { 20, 0 }, { 20, 461 }, { 20, 805 }, { 20, 1141 }, { 21, 0 }, { 21, 314 }, { 21, 601 }, { 21, 936 }, { 22, 0 }, { 22, 361 }, { 22, 681 }, { 22, 1014 }, { 23, 0 }, { 23, 381 }, { 23, 740 }, { 24, 0 }, { 24, 303 }, { 24, 655 }, { 24, 1019 }, { 25, 0 }, { 25, 296 }, { 25, 664 }, { 26, 0 }, { 26, 416 }, { 26, 774 }, { 26, 1236 }, { 27, 0 }, { 27, 347 }, { 27, 733 }, { 27, 1070 }, { 28, 149 }, { 28, 479 }, { 28, 880 }, { 28, 1246 }, { 29, 0 }, { 29, 378 }, { 29, 695 }, { 30, 0 }, { 30, 411 }, { 30, 770 }, { 31, 335 }, { 32, 131 }, { 33, 0 }, { 33, 306 }, { 33, 551 }, { 33, 945 }, { 33, 1185 }, { 34, 175 }, { 34, 446 }, { 34, 815 }, { 35, 304 }, { 35, 698 }, { 36, 240 }, { 36, 571 }, { 37, 130 }, { 37, 491 }, { 37, 844 }, { 38, 188 }, { 38, 543 }, { 39, 163 }, { 39, 554 }, { 39, 902 }, { 40, 0 }, { 40, 286 }, { 40, 651 }, { 40, 1009 }, { 41, 78 }, { 41, 332 }, { 41, 700 }, { 42, 170 }, { 42, 490 }, { 42, 849 }, { 43, 241 }, { 43, 599 }, { 44, 111 }, { 45, 125 }, { 46, 0 }, { 46, 387 }, { 47, 122 }, { 47, 488 }, { 48, 313 }, { 49, 0 }, { 49, 271 }, { 50, 236 }, { 51, 181 }, { 52, 147 }, { 53, 146 }, { 54, 62 }, { 55, 0 }, { 56, 0 }, { 56, 360 }, { 57, 281 }, { 58, 0 }, { 58, 332 }, { 59, 235 }, { 60, 165 }, { 61, 0 }, { 63, 44 }, { 65, 0 }, { 66, 0 }, { 67, 0 }, { 68, 0 }, { 69, 0 }, { 70, 92 }, { 72, 0 }, { 73, 141 }, { 75, 0 }, { 76, 151 }, { 78, 0 }, { 80, 0 }, { 82, 0 }, { 84, 0 }, { 87, 0 }, { 90, 0 }, { 94, 0 }, { 100, 33 }, { 114, 26 } };
    }

    public SurasAjzaaManager(final Context context) {
        this.context = context;
    }

    private int getAyaNumberOfWord(@NonNull final String s) {
        final int index = s.indexOf("2200", 0);
        if (index == -1) {
            return 0;
        }
        final int index2 = s.indexOf("2199", index);
        int ayaNumber = 0;
        if (index != -1) {
            ayaNumber = 0;
            if (index2 != -1) {
                ayaNumber = this.parseAyaNumber(s.substring(index + 5, index2 - 1));
            }
        }
        return ayaNumber;
    }

    public static List<String> getPlainTextOfHezp(final int n, final Context context) {
        final ArrayList<Object> list = new ArrayList<Object>();
        final DataManager dataManager = new DataManager(context);
        final int[] array = SurasAjzaaManager.jozahezbWordMap[n];
        final int[] array2 = SurasAjzaaManager.jozahezbWordMap[n + 1];
        for (int i = array[0]; i <= array2[0]; ++i) {
            if (i > array[0] && i != 9) {
                list.add("بسم_الله_الرحمن_الرحيم");
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("search_files/");
            sb.append(i);
            sb.append(".rtx");
            List<? extends String> list2 = Arrays.asList(dataManager.loadArabicFile(sb.toString()).trim().split("\\s+"));
            final ArrayList<String> list3 = new ArrayList<>();
            if (array[0] == array2[0] && array[0] == i) {
                list2 = list2.subList(array[1], array2[1]);
            }
            else if (i == array[0] && i != array2[0]) {
                list2 = list2.subList(array[1], list2.size());
            }
            else if (i != array[0] && i == array2[0]) {
                list2 = list2.subList(0, array2[1]);
            }
            else if (i == array[0] || i == array2[0]) {
                list2 = list3;
            }
            list.addAll(list2);
        }
        List<String> strings = new ArrayList<>(list.size());
        for (Object object : list) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                strings.add(Objects.toString(object, null));
            }
        }
        return strings;
    }

    public static List<String> getPlainTextOfJuza(final int n, final Context context) {
        final ArrayList<Object> list = new ArrayList<>();
        final DataManager dataManager = new DataManager(context);
        final int[][] jozahezbWordMap = SurasAjzaaManager.jozahezbWordMap;
        final int n2 = n * 8;
        final int[] array = jozahezbWordMap[n2 - 8];
        final int[] array2 = SurasAjzaaManager.jozahezbWordMap[n2];
        for (int i = array[0]; i <= array2[0]; ++i) {
            if (i > array[0] && i != 9) {
                list.add("بسم_الله_الرحمن_الرحيم");
//                list.add("\u0628\u0633\u0645_\u0627\u0644\u0644\u0647_\u0627\u0644\u0631\u062d\u0645\u0646_\u0627\u0644\u0631\u062d\u064a\u0645");
            }
            final StringBuilder sb = new StringBuilder();
            sb.append("search_files/");
            sb.append(i);
            sb.append(".rtx");
            List<? extends String> list2 = Arrays.asList(dataManager.loadArabicFile(sb.toString()).trim().split("\\s+"));
            final ArrayList<String> list3 = new ArrayList<String>();
            if (array[0] == array2[0] && array[0] == i) {
                list2 = list2.subList(array[1], array2[1]);
            }
            else if (i == array[0] && i != array2[0]) {
                list2 = list2.subList(array[1], list2.size());
            }
            else if (i != array[0] && i == array2[0]) {
                list2 = list2.subList(0, array2[1]);
            }
            else if (i == array[0] || i == array2[0]) {
                list2 = list3;
            }
            list.addAll(list2);
        }
        List<String> strings = new ArrayList<>(list.size());
        for (Object object : list) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                strings.add(Objects.toString(object, null));
            }
        }
        return strings;
    }

    private int parseAyaNumber(final String s) {
        return Integer.parseInt(new StringBuffer(s.replace("2201", "0").replace("2202", "1").replace("2203", "2").replace("2204", "3").replace("2205", "4").replace("2206", "5").replace("2207", "6").replace("2208", "7").replace("2209", "8").replace("2210", "9").replace(",", "")).reverse().toString());
    }

    @NonNull
    public int[][] getHezbPosition(final int n) {
        final int[][] array = (int[][])Array.newInstance(int.class, 2, 2);
        array[0] = SurasAjzaaManager.jozahezbCharMap[n];
        array[1] = SurasAjzaaManager.jozahezbCharMap[n + 1];
        return array;
    }

    @NonNull
    public int[][] getHezbWordPosition(final int n) {
        final int[][] array = (int[][])Array.newInstance(int.class, 2, 2);
        array[0] = SurasAjzaaManager.jozahezbWordMap[n];
        array[1] = SurasAjzaaManager.jozahezbWordMap[n + 1];
        return array;
    }

    @NonNull
    public List<WordOfQuran> getJozaHezbContentNew(final int n, final int n2) {
        final ArrayList<WordOfQuran> list = new ArrayList<WordOfQuran>();
        this.suraGenaricWordIndexMap = new SparseIntArray();
        int[][] array = null;
        if (n == ScrollingStyleQuranActivity.AJZAA_MODE) {
            array = this.getJozaPosition(n2);
        }
        else if (n == ScrollingStyleQuranActivity.QUARTER_MODE) {
            array = this.getHezbPosition(n2);
        }
        final int n3 = array[0][0];
        int n4 = 1;
        final int n5 = array[n4][0];
        final int n6 = array[0][n4];
        final int n7 = array[n4][n4];
        int n8;
        if (n == ScrollingStyleQuranActivity.AJZAA_MODE) {
            n8 = this.getJozaWordPosition(n2)[0][n4];
        }
        else if (n == ScrollingStyleQuranActivity.QUARTER_MODE) {
            n8 = this.getHezbWordPosition(n2)[0][n4];
        }
        else {
            n8 = 0;
        }
        this.suraGenaricWordIndexMap.put(n3, list.size() - n8);
        final DataManager dataManager = new DataManager(this.context);
        int n9 = n8;
        Label_0551:
        for (int i = n3; i <= n5; ++i, n4 = 1) {
            if (i == n5 && n7 == 0) {
                return list;
            }
            if (i > n3) {
                this.suraGenaricWordIndexMap.put(i, list.size() - 0);
                n9 = 0;
            }
            if (i == n4) {
                list.add(new WordOfQuran(-1, -1, -1, "3101", (String)null, i, 0.0f, -1));
            }
            else if (i != 9) {
                list.add(new WordOfQuran(-1, -1, -1, "3102", (String)null, i, 0.0f, -1));
            }
            String s = dataManager.getQuranSuraContents(i);
            if (n3 == n5) {
                s = s.substring(n6, n7);
            }
            else if (i == n5) {
                s = s.substring(0, n7);
            }
            else if (i == n3) {
                s = s.substring(n6, s.length());
            }
            final String trim = s.trim();
            if (trim != "" && !trim.equals("")) {
                final String[] split = trim.split("\\s+");
                while (true) {
                    for (int j = 0; j < split.length; ++j) {
                        if (split[j].indexOf("2200", 0) != -1) {
                            final int ayaNumberOfWord = this.getAyaNumberOfWord(split[j]);
                            int n10 = n9;
                            int n11 = ayaNumberOfWord;
                            for (int k = 0; k < split.length; ++k) {
                                list.add(new WordOfQuran(-1, n10, list.size(), split[k], (String)null, i, 0.0f, n11));
                                if (split[k].contains("2200")) {
                                    ++n11;
                                }
                                ++n10;
                            }
                            n9 = n10;
                            continue Label_0551;
                        }
                    }
                    final int ayaNumberOfWord = 0;
                    continue;
                }
            }
        }
        return list;
    }

    @NonNull
    public int[][] getJozaPosition(final int n) {
        final int[][] array = (int[][])Array.newInstance(int.class, 2, 2);
        final int[][] jozahezbCharMap = SurasAjzaaManager.jozahezbCharMap;
        final int n2 = n * 8;
        array[0] = jozahezbCharMap[n2 - 8];
        array[1] = SurasAjzaaManager.jozahezbCharMap[n2];
        return array;
    }

    @NonNull
    public int[][] getJozaWordPosition(final int n) {
        final int[][] array = (int[][])Array.newInstance(int.class, 2, 2);
        final int[][] jozahezbWordMap = SurasAjzaaManager.jozahezbWordMap;
        final int n2 = n * 8;
        array[0] = jozahezbWordMap[n2 - 8];
        array[1] = SurasAjzaaManager.jozahezbWordMap[n2];
        return array;
    }

    @NonNull
    public List<WordOfQuran> getSuraContentNew(final int n) {
        int n2 = n;
        if (n2 < 1 || n2 > 114) {
            n2 = 1;
        }
        final ArrayList<WordOfQuran> list = new ArrayList<WordOfQuran>();
        this.suraGenaricWordIndexMap = new SparseIntArray();
        final SparseIntArray suraGenaricWordIndexMap = this.suraGenaricWordIndexMap;
        final int size = list.size();
        int i = 0;
        suraGenaricWordIndexMap.put(n2, size - 0);
        final DataManager dataManager = new DataManager(this.context);
        if (n2 == 1) {
            list.add(new WordOfQuran(-1, -1, -1, "3101", (String)null, n2, 0.0f, -1));
        }
        else if (n2 != 9) {
            list.add(new WordOfQuran(-1, -1, -1, "3102", (String)null, n2, 0.0f, -1));
        }
        final String quranSuraContents = dataManager.getQuranSuraContents(n2);
        if (quranSuraContents != null) {
            final String trim = quranSuraContents.trim();
            if (trim != "" && !trim.equals("")) {
                final String[] split = trim.split("\\s+");
                while (true) {
                    for (int j = 0; j < split.length; ++j) {
                        if (split[j].indexOf("2200", 0) != -1) {
                            final int ayaNumberOfWord = this.getAyaNumberOfWord(split[j]);
                            int n3 = ayaNumberOfWord;
                            int n4 = 0;
                            while (i < split.length) {
                                list.add(new WordOfQuran(-1, n4, list.size(), split[i], (String)null, n2, 0.0f, n3));
                                if (split[i].contains("2200")) {
                                    ++n3;
                                }
                                ++n4;
                                ++i;
                            }
                            return list;
                        }
                    }
                    final int ayaNumberOfWord = 0;
                    continue;
                }
            }
            return list;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("suraContent=null, suraNumber=");
        sb.append(n2);
        throw new IllegalArgumentException(sb.toString());
    }

    public SparseIntArray getSuraGenaricWordIndexMap() {
        return this.suraGenaricWordIndexMap;
    }
}
