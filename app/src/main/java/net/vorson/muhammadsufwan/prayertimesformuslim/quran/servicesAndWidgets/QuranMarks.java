package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import io.reactivex.annotations.NonNull;

public class QuranMarks {
    @NonNull
    private static String[] ArbaaStr = new String[]{"الحزب", "ربع الحزب", "نصف الحزب", "ثلاث 4 الحزب", "جزء", "سجدة", "سكتة"};
    @NonNull
    public static int[] surasAyahs = new int[]{7, 286, 200, 176, 120, 165, 206, 75, 129, 109, 123, 111, 43, 52, 99, 128, 111, 110, 98, 135, 112, 78, 118, 64, 77, 227, 93, 88, 69, 60, 34, 30, 73, 54, 45, 83, 182, 88, 75, 85, 54, 53, 89, 59, 37, 35, 38, 29, 18, 45, 60, 49, 62, 55, 78, 96, 29, 22, 24, 13, 14, 11, 11, 18, 12, 12, 30, 52, 52, 44, 28, 28, 20, 56, 40, 31, 50, 40, 46, 42, 29, 19, 36, 25, 22, 17, 19, 26, 30, 20, 15, 21, 11, 8, 8, 19, 5, 8, 8, 11, 11, 8, 3, 9, 5, 4, 7, 3, 6, 3, 5, 4, 5, 6};
    @NonNull
    private int[][] ahzab = new int[][]{new int[]{2, 25, 1, 1}, new int[]{2, 43, 1, 2}, new int[]{2, 59, 1, 3}, new int[]{2, 74, 2, 0}, new int[]{2, 91, 2, 1}, new int[]{2, 105, 2, 2}, new int[]{2, 123, 2, 3}, new int[]{2, 141, 3, 0}, new int[]{2, 157, 3, 1}, new int[]{2, 176, 3, 2}, new int[]{2, 188, 3, 3}, new int[]{2, 202, 4, 0}, new int[]{2, 218, 4, 1}, new int[]{2, 232, 4, 2}, new int[]{2, 242, 4, 3}, new int[]{2, 252, 5, 0}, new int[]{2, 262, 5, 1}, new int[]{2, 271, 5, 2}, new int[]{2, 282, 5, 3}, new int[]{3, 14, 6, 0}, new int[]{3, 32, 6, 1}, new int[]{3, 51, 6, 2}, new int[]{3, 74, 6, 3}, new int[]{3, 92, 7, 0}, new int[]{3, 112, 7, 1}, new int[]{3, 132, 7, 2}, new int[]{3, 152, 7, 3}, new int[]{3, 170, 8, 0}, new int[]{3, 185, 8, 1}, new int[]{4, 0, 8, 2}, new int[]{4, 11, 8, 3}, new int[]{4, 23, 9, 0}, new int[]{4, 35, 9, 1}, new int[]{4, 57, 9, 2}, new int[]{4, 73, 9, 3}, new int[]{4, 87, 10, 0}, new int[]{4, 99, 10, 1}, new int[]{4, 113, 10, 2}, new int[]{4, 134, 10, 3}, new int[]{4, 147, 11, 0}, new int[]{4, 162, 11, 1}, new int[]{5, 0, 11, 2}, new int[]{5, 11, 11, 3}, new int[]{5, 26, 12, 0}, new int[]{5, 40, 12, 1}, new int[]{5, 50, 12, 2}, new int[]{5, 66, 12, 3}, new int[]{5, 81, 13, 0}, new int[]{5, 96, 13, 1}, new int[]{5, 108, 13, 2}, new int[]{6, 12, 13, 3}, new int[]{6, 35, 14, 0}, new int[]{6, 58, 14, 1}, new int[]{6, 73, 14, 2}, new int[]{6, 94, 14, 3}, new int[]{6, 110, 15, 0}, new int[]{6, 126, 15, 1}, new int[]{6, 140, 15, 2}, new int[]{6, 150, 15, 3}, new int[]{7, 0, 16, 0}, new int[]{7, 30, 16, 1}, new int[]{7, 46, 16, 2}, new int[]{7, 64, 16, 3}, new int[]{7, 87, 17, 0}, new int[]{7, 116, 17, 1}, new int[]{7, 141, 17, 2}, new int[]{7, 155, 17, 3}, new int[]{7, 170, 18, 0}, new int[]{7, 188, 18, 1}, new int[]{8, 0, 18, 2}, new int[]{8, 21, 18, 3}, new int[]{8, 40, 19, 0}, new int[]{8, 60, 19, 1}, new int[]{9, 0, 19, 2}, new int[]{9, 18, 19, 3}, new int[]{9, 33, 20, 0}, new int[]{9, 45, 20, 1}, new int[]{9, 59, 20, 2}, new int[]{9, 74, 20, 3}, new int[]{9, 92, 21, 0}, new int[]{9, 110, 21, 1}, new int[]{9, 121, 21, 2}, new int[]{10, 10, 21, 3}, new int[]{10, 25, 22, 0}, new int[]{10, 52, 22, 1}, new int[]{10, 70, 22, 2}, new int[]{10, 89, 22, 3}, new int[]{11, 5, 23, 0}, new int[]{11, 23, 23, 1}, new int[]{11, 40, 23, 2}, new int[]{11, 60, 23, 3}, new int[]{11, 83, 24, 0}, new int[]{11, 107, 24, 1}, new int[]{12, 6, 24, 2}, new int[]{12, 29, 24, 3}, new int[]{12, 52, 25, 0}, new int[]{12, 76, 25, 1}, new int[]{12, 100, 25, 2}, new int[]{13, 4, 25, 3}, new int[]{13, 18, 26, 0}, new int[]{13, 34, 26, 1}, new int[]{14, 9, 26, 2}, new int[]{14, 27, 26, 3}, new int[]{15, 0, 27, 0}, new int[]{15, 48, 27, 1}, new int[]{16, 0, 27, 2}, new int[]{16, 29, 27, 3}, new int[]{16, 50, 28, 0}, new int[]{16, 74, 28, 1}, new int[]{16, 89, 28, 2}, new int[]{16, 110, 28, 3}, new int[]{17, 0, 29, 0}, new int[]{17, 22, 29, 1}, new int[]{17, 49, 29, 2}, new int[]{17, 69, 29, 3}, new int[]{17, 98, 30, 0}, new int[]{18, 16, 30, 1}, new int[]{18, 31, 30, 2}, new int[]{18, 50, 30, 3}, new int[]{18, 74, 31, 0}, new int[]{18, 98, 31, 1}, new int[]{19, 21, 31, 2}, new int[]{19, 58, 31, 3}, new int[]{20, 0, 32, 0}, new int[]{20, 54, 32, 1}, new int[]{20, 82, 32, 2}, new int[]{20, 110, 32, 3}, new int[]{21, 0, 33, 0}, new int[]{21, 28, 33, 1}, new int[]{21, 50, 33, 2}, new int[]{21, 82, 33, 3}, new int[]{22, 0, 34, 0}, new int[]{22, 18, 34, 1}, new int[]{22, 37, 34, 2}, new int[]{22, 59, 34, 3}, new int[]{23, 0, 35, 0}, new int[]{23, 35, 35, 1}, new int[]{23, 74, 35, 2}, new int[]{24, 0, 35, 3}, new int[]{24, 20, 36, 0}, new int[]{24, 34, 36, 1}, new int[]{24, 52, 36, 2}, new int[]{25, 0, 36, 3}, new int[]{25, 20, 37, 0}, new int[]{25, 52, 37, 1}, new int[]{26, 0, 37, 2}, new int[]{26, 51, 37, 3}, new int[]{26, 110, 38, 0}, new int[]{26, 180, 38, 1}, new int[]{27, 0, 38, 2}, new int[]{27, 26, 38, 3}, new int[]{27, 55, 39, 0}, new int[]{27, 81, 39, 1}, new int[]{28, 11, 39, 2}, new int[]{28, 28, 39, 3}, new int[]{28, 50, 40, 0}, new int[]{28, 75, 40, 1}, new int[]{29, 0, 40, 2}, new int[]{29, 25, 40, 3}, new int[]{29, 45, 41, 0}, new int[]{30, 0, 41, 1}, new int[]{30, 30, 41, 2}, new int[]{30, 53, 41, 3}, new int[]{31, 21, 42, 0}, new int[]{32, 10, 42, 1}, new int[]{33, 0, 42, 2}, new int[]{33, 17, 42, 3}, new int[]{33, 30, 43, 0}, new int[]{33, 50, 43, 1}, new int[]{33, 59, 43, 2}, new int[]{34, 9, 43, 3}, new int[]{34, 23, 44, 0}, new int[]{34, 45, 44, 1}, new int[]{35, 14, 44, 2}, new int[]{35, 40, 44, 3}, new int[]{36, 27, 45, 0}, new int[]{36, 59, 45, 1}, new int[]{37, 21, 45, 2}, new int[]{37, 82, 45, 3}, new int[]{37, 144, 46, 0}, new int[]{38, 20, 46, 1}, new int[]{38, 51, 46, 2}, new int[]{39, 7, 46, 3}, new int[]{39, 31, 47, 0}, new int[]{39, 52, 47, 1}, new int[]{40, 0, 47, 2}, new int[]{40, 20, 47, 3}, new int[]{40, 40, 48, 0}, new int[]{40, 65, 48, 1}, new int[]{41, 8, 48, 2}, new int[]{41, 24, 48, 3}, new int[]{41, 46, 49, 0}, new int[]{42, 12, 49, 1}, new int[]{42, 26, 49, 2}, new int[]{42, 50, 49, 3}, new int[]{43, 23, 50, 0}, new int[]{43, 56, 50, 1}, new int[]{44, 16, 50, 2}, new int[]{45, 11, 50, 3}, new int[]{46, 0, 51, 0}, new int[]{46, 20, 51, 1}, new int[]{47, 9, 51, 2}, new int[]{47, 32, 51, 3}, new int[]{48, 17, 52, 0}, new int[]{49, 0, 52, 1}, new int[]{49, 13, 52, 2}, new int[]{50, 26, 52, 3}, new int[]{51, 30, 53, 0}, new int[]{52, 23, 53, 1}, new int[]{53, 25, 53, 2}, new int[]{54, 8, 53, 3}, new int[]{55, 0, 54, 0}, new int[]{56, 0, 54, 1}, new int[]{56, 74, 54, 2}, new int[]{57, 15, 54, 3}, new int[]{58, 0, 55, 0}, new int[]{58, 13, 55, 1}, new int[]{59, 10, 55, 2}, new int[]{60, 6, 55, 3}, new int[]{61, 0, 56, 0}, new int[]{62, 3, 56, 1}, new int[]{63, 3, 56, 1}, new int[]{65, 0, 56, 2}, new int[]{66, 0, 56, 3}, new int[]{67, 0, 57, 0}, new int[]{68, 0, 57, 1}, new int[]{69, 0, 57, 2}, new int[]{70, 18, 57, 3}, new int[]{72, 0, 58, 0}, new int[]{73, 19, 58, 1}, new int[]{75, 0, 58, 2}, new int[]{76, 18, 58, 3}, new int[]{78, 0, 59, 0}, new int[]{80, 0, 59, 1}, new int[]{82, 0, 59, 2}, new int[]{84, 0, 59, 3}, new int[]{87, 0, 60, 0}, new int[]{90, 0, 60, 1}, new int[]{94, 0, 60, 2}, new int[]{100, 8, 60, 3}};
    @NonNull
    private int[][] ajzaa = new int[][]{new int[]{1, 1}, new int[]{2, 142}, new int[]{2, 253}, new int[]{3, 93}, new int[]{4, 24}, new int[]{4, 148}, new int[]{5, 82}, new int[]{6, 111}, new int[]{7, 88}, new int[]{8, 41}, new int[]{9, 93}, new int[]{11, 6}, new int[]{12, 53}, new int[]{15, 1}, new int[]{17, 1}, new int[]{18, 75}, new int[]{21, 1}, new int[]{23, 1}, new int[]{25, 21}, new int[]{27, 56}, new int[]{29, 46}, new int[]{33, 31}, new int[]{36, 28}, new int[]{39, 32}, new int[]{41, 47}, new int[]{46, 1}, new int[]{51, 31}, new int[]{58, 1}, new int[]{67, 1}, new int[]{78, 1}};
    private boolean isThereMarkInStart = false;
    private int startHezbNumber;
    private int startSplitHezb;
    private int suraId;

    public QuranMarks(int i) {
        boolean z = false;
        suraId = i;
        if (getMark1(0)[1] != 0) {
            z = true;
        }
        isThereMarkInStart = z;
    }

    @NonNull
    public int[] getMark1(int i) {
        int i2 = 0;
        while (i2 < ahzab.length) {
            if (ahzab[i2][0] == suraId && ahzab[i2][1] == i) {
                i = ahzab[i2][3];
                i2 = ahzab[i2][2];
                break;
            }
            i2++;
        }
        i = 0;
        i2 = 0;
        return new int[]{i, i2};
    }

    public int getJozaaIndexFromAyaNumber(int i) {
        int i2 = (suraId * 1000) + i;
        int i3 = 0;
        while (i3 < ajzaa.length) {
            if ((ajzaa[i3][0] * 1000) + ajzaa[i3][1] > i2) {
                break;
            }
            i3++;
        }
        i3 = -1;
        return i3 == -1 ? 30 : i3;
    }

    public boolean isThereMarkInTheStartOfSura() {
        return isThereMarkInStart;
    }

    @NonNull
    public static List<String> getQuranMarksArray1(int i, @NonNull List<String> list, TextWrapper textWrapper) {
        QuranMarks quranMarks = new QuranMarks(i);
        List<String> arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (((String) list.get(i2)).indexOf("442") > -1) {
                arrayList.add("*");
            } else {
                arrayList.add("-");
            }
        }
        return arrayList;
    }

    @NonNull
    public static List<String> getQuranMarksArray(int i, @NonNull List<String> list, @NonNull TextWrapper textWrapper) {
        QuranMarks quranMarks = new QuranMarks(i);
        List<String> arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < list.size() && list.get(i2) != null) {
            int indexOf = ((String) list.get(i2)).indexOf("442");
            if (indexOf != -1 || (quranMarks.isThereMarkInTheStartOfSura() && i2 == 0)) {
                Object obj;
                Object stringBuilder;
                indexOf = indexOf == 0 ? i2 - 1 : i2;
                if (indexOf == -1) {
                    indexOf = 0;
                    while (indexOf < list.size()) {
                        if (textWrapper.getAyaNumberOfLine(0, (String) list.get(indexOf)) > 0) {
                            break;
                        }
                        indexOf++;
                    }
                    indexOf = 0;
                    obj = 1;
                } else {
                    obj = null;
                }
                indexOf = textWrapper.getAyaNumberOfLine(0, (String) list.get(indexOf));
                if (obj != null) {
                    indexOf--;
                }
                int[] mark1 = quranMarks.getMark1(indexOf);
                textWrapper.getFontManager().getLineHeight();
                quranMarks.isThereMarkInTheStartOfSura();
                int checkIsItJozaaMark = checkIsItJozaaMark(mark1);
                StringBuilder stringBuilder2;
                String stringBuilder3;
                StringBuilder stringBuilder4;
                if (checkIsItJozaaMark == -1) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(mark1[1]);
                    stringBuilder2.append("");
                    stringBuilder3 = stringBuilder2.toString();
                    stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(ArbaaStr[mark1[0]]);
                    stringBuilder4.append(stringBuilder3);
                    stringBuilder = stringBuilder4.toString();
                } else {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(ArbaaStr[4]);
                    stringBuilder2.append(" ");
                    stringBuilder3 = stringBuilder2.toString();
                    StringBuilder stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(checkIsItJozaaMark);
                    stringBuilder5.append("");
                    String stringBuilder6 = stringBuilder5.toString();
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(stringBuilder3);
                    stringBuilder5.append(stringBuilder6);
                    stringBuilder5.append(" ");
                    stringBuilder3 = stringBuilder5.toString();
                    StringBuilder stringBuilder7 = new StringBuilder();
                    stringBuilder7.append(stringBuilder3);
                    stringBuilder7.append(ArbaaStr[0]);
                    stringBuilder7.append(" ");
                    stringBuilder3 = stringBuilder7.toString();
                    stringBuilder7 = new StringBuilder();
                    stringBuilder7.append(mark1[1]);
                    stringBuilder7.append("");
                    String stringBuilder8 = stringBuilder7.toString();
                    stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(stringBuilder3);
                    stringBuilder4.append(stringBuilder8);
                    stringBuilder4.append(" ");
                    stringBuilder = stringBuilder4.toString();
                }
                arrayList.add(String.valueOf(stringBuilder));
            } else {
                arrayList.add("");
            }
            i2++;
        }
        return arrayList;
    }

    public static int checkIsItJozaaMark(int[] iArr) {
        if (iArr[0] == 0 && (iArr[1] + 1) % 2 <= 0) {
            return (iArr[1] + 1) / 2;
        }
        return -1;
    }

    public static String convertNumberToUnicodeStringForSmallNumbers(String str) {
        for (int i = 0; i < 10; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i);
            stringBuilder.append("");
            str = str.replace(stringBuilder.toString(), Character.toString((char) (61440 + i)));
        }
        return str;
    }
}
