package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;


import androidx.core.view.PointerIconCompat;
import io.reactivex.annotations.NonNull;

public class NewQuranMarks {
    @NonNull
    private static int[][] ahzab = new int[][]{new int[]{1, 0}, new int[]{2, 356}, new int[]{2, 677}, new int[]{2, 908}, new int[]{2, 1244}, new int[]{2, 1621}, new int[]{2, 1920},
            new int[]{2, 2285}, new int[]{2, 2641}, new int[]{2, 2955}, new int[]{2, 3330}, new int[]{2, 3673}, new int[]{2, 3998}, new int[]{2, 4377}, new int[]{2, 4768},
            new int[]{2, 5038}, new int[]{2, 5340}, new int[]{2, 5719}, new int[]{2, 5933}, new int[]{2, 6282}, new int[]{3, 241}, new int[]{3, 596}, new int[]{3, 948},
            new int[]{3, 1316}, new int[]{3, 1673}, new int[]{3, 2029}, new int[]{3, 2344}, new int[]{3, 2688}, new int[]{3, 3110}, new int[]{3, 3406}, new int[]{4, 0},
            new int[]{4, 281}, new int[]{4, 626}, new int[]{4, 930}, new int[]{4, 1365}, new int[]{4, 1660}, new int[]{4, 2004}, new int[]{4, 2340}, new int[]{4, 2659},
            new int[]{4, 3057}, new int[]{4, 3337}, new int[]{4, 3622}, new int[]{5, 0}, new int[]{5, 367}, new int[]{5, 745}, new int[]{5, 1029}, new int[]{5, 1358},
            new int[]{5, 1708}, new int[]{5, 2027}, new int[]{5, 2366}, new int[]{5, 2638}, new int[]{6, 192}, new int[]{6, 595}, new int[]{6, 1003}, new int[]{6, 1320},
            new int[]{6, 1737}, new int[]{6, 2022}, new int[]{6, 2336}, new int[]{6, 2612}, new int[]{6, 2885}, new int[]{7, 0}, new int[]{7, 420}, new int[]{7, 778},
            new int[]{7, 1104}, new int[]{7, 1513}, new int[]{7, 1909}, new int[]{7, 2263}, new int[]{7, 2600}, new int[]{7, 2966}, new int[]{7, 3286}, new int[]{8, 0},
            new int[]{8, 320}, new int[]{8, 631}, new int[]{8, 1016}, new int[]{9, 0}, new int[]{9, 339}, new int[]{9, 649}, new int[]{9, 942}, new int[]{9, 1179},
            new int[]{9, 1515}, new int[]{9, 1842}, new int[]{9, 2214}, new int[]{9, 2492}, new int[]{10, 185}, new int[]{10, 548}, new int[]{10, 1002}, new int[]{10, 1305},
            new int[]{10, 1609}, new int[]{11, 76}, new int[]{11, 416}, new int[]{11, 711}, new int[]{11, 1063}, new int[]{11, 1433}, new int[]{11, 1809}, new int[]{12, 88},
            new int[]{12, 445}, new int[]{12, 901}, new int[]{12, 1292}, new int[]{12, 1710}, new int[]{13, 96}, new int[]{13, 421}, new int[]{13, 736}, new int[]{14, 184},
            new int[]{14, 530}, new int[]{15, 0}, new int[]{15, 399}, new int[]{16, 0}, new int[]{16, 369}, new int[]{16, 703}, new int[]{16, 1082}, new int[]{16, 1360},
            new int[]{16, 1703}, new int[]{17, 0}, new int[]{17, 325}, new int[]{17, 712}, new int[]{17, 1047}, new int[]{17, 1472}, new int[]{18, 204}, new int[]{18, 560},
            new int[]{18, 863}, new int[]{18, 1205}, new int[]{18, 1543}, new int[]{19, 223}, new int[]{19, 655}, new int[]{20, 0}, new int[]{20, 460}, new int[]{20, 804},
            new int[]{20, 1140}, new int[]{21, 0}, new int[]{21, 313}, new int[]{21, 600}, new int[]{21, 935}, new int[]{22, 0}, new int[]{22, 360}, new int[]{22, 680},
            new int[]{22, 1013}, new int[]{23, 0}, new int[]{23, 380}, new int[]{23, 739}, new int[]{24, 0}, new int[]{24, 302}, new int[]{24, 654}, new int[]{24, 1018},
            new int[]{25, 0}, new int[]{25, 295}, new int[]{25, 663}, new int[]{26, 0}, new int[]{26, 415}, new int[]{26, 773}, new int[]{26, 1235}, new int[]{27, 0},
            new int[]{27, 346}, new int[]{27, 732}, new int[]{27, 1069}, new int[]{28, 148}, new int[]{28, 478}, new int[]{28, 879}, new int[]{28, 1245}, new int[]{29, 0},
            new int[]{29, 377}, new int[]{29, 694}, new int[]{30, 0}, new int[]{30, 410}, new int[]{30, 769}, new int[]{31, 334}, new int[]{32, 130}, new int[]{33, 0},
            new int[]{33, 305}, new int[]{33, 550}, new int[]{33, 944}, new int[]{33, 1184}, new int[]{34, 174}, new int[]{34, 445}, new int[]{34, 814}, new int[]{35, 303},
            new int[]{35, 697}, new int[]{36, 239}, new int[]{36, 570}, new int[]{37, 129}, new int[]{37, 490}, new int[]{37, 843}, new int[]{38, 187}, new int[]{38, 542},
            new int[]{39, 162}, new int[]{39, 553}, new int[]{39, 901}, new int[]{40, 0}, new int[]{40, 285}, new int[]{40, 650}, new int[]{40, 1008},
            new int[]{41, 77}, new int[]{41, 331}, new int[]{41, 699}, new int[]{42, 169}, new int[]{42, 489}, new int[]{42, 848}, new int[]{43, 240}, new int[]{43, 598},
            new int[]{44, 110}, new int[]{45, 124}, new int[]{46, 0}, new int[]{46, 386}, new int[]{47, 121}, new int[]{47, 487}, new int[]{48, 312}, new int[]{49, 0},
            new int[]{49, 270}, new int[]{50, 235}, new int[]{51, 180}, new int[]{52, 146}, new int[]{53, 145}, new int[]{54, 61}, new int[]{55, 0}, new int[]{56, 0},
            new int[]{56, 359}, new int[]{57, 280}, new int[]{58, 0}, new int[]{58, 331}, new int[]{59, 234}, new int[]{60, 164}, new int[]{61, 0}, new int[]{63, 43},
            new int[]{65, 0}, new int[]{66, 0}, new int[]{67, 0}, new int[]{68, 0}, new int[]{69, 0}, new int[]{70, 91}, new int[]{72, 0}, new int[]{73, 140},
            new int[]{75, 0}, new int[]{76, 150}, new int[]{78, 0}, new int[]{80, 0}, new int[]{82, 0}, new int[]{84, 0}, new int[]{87, 0}, new int[]{90, 0},
            new int[]{94, 0}, new int[]{100, 32}};


    public int getHezpIndex(final int n, final int n2) {
        final int n3 = n2 + n * 1000000;
        while (true) {
            for (int i = 0; i < NewQuranMarks.ahzab.length; ++i) {
                if (1000000 * NewQuranMarks.ahzab[i][0] + NewQuranMarks.ahzab[i][1] >= n3) {
                    int n4 = i - 1;
                    if (n4 == -1) {
                        n4 = NewQuranMarks.ahzab.length - 1;
                    }
                    return n4;
                }
            }
            int n4 = -1;
            continue;
        }
    }

    @NonNull
    private String getHezpTextFromLocationIndex(int i) {
        String str = "";
        int i2 = (int) ((double) ((i / 4) + 1));
        switch (i % 4) {
            case 0:
                str = "";
                break;
            case 1:
                str = "ربع";
                break;
            case 2:
                str = "نصف";
                break;
            case 3:
                str = "ثلاث أرباع";
                break;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" الحزب ");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }


    @NonNull
    public String getHezpText(final int n, final int n2) {
        return this.getHezpTextFromLocationIndex(getHezpIndex(n, n2));
    }

}
