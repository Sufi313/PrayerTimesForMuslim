package com.sufi.prayertimes.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sufi.prayertimes.R;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;
import com.sufi.prayertimes.util.helpers.DateAndTimeHelper;



import java.util.Calendar;

public class Hijri {

    static int intPart(int floatNum) {
        if (((double) floatNum) < -1.0E-7d) {
            return (int) Math.ceil(((double) floatNum) - 1.0E-7d);
        }
        return (int) Math.floor(((double) floatNum) + 1.0E-7d);
    }

    public static int[] chrToIsl(int y, int m, int d, int diff) {
        int jd;
        if (y > 1582 || ((y == 1582 && m > 10) || (y == 1582 && m == 10 && d > 14))) {
            jd = (((intPart((((y + 4800) + intPart((m - 14) / 12)) * 1461) / 4) + intPart((((m - 2) - (intPart((m - 14) / 12) * 12)) * 367) / 12)) - intPart((intPart(((y + 4900) + intPart((m - 14) / 12)) / 100) * 3) / 4)) + d) - 32075;
        } else {
            jd = ((((y * 367) - intPart((((y + 5001) + intPart((m - 9) / 7)) * 7) / 4)) + intPart((m * 275) / 9)) + d) + 1729777;
        }
        int l = (jd - 1948440) + 10632;
        int n = intPart((l - 1) / 10631);
        l = ((l - (n * 10631)) + 354) + diff;
        int j = (intPart((10985 - l) / 5316) * intPart((l * 50) / 17719)) + (intPart(l / 5670) * intPart((l * 43) / 15238));
        l = ((l - (intPart((30 - j) / 15) * intPart((j * 17719) / 50))) - (intPart(j / 16) * intPart((j * 15238) / 43))) + 29;
        d = l - intPart((intPart((l * 24) / 709) * 709) / 24);
        y = ((n * 30) + j) - 30;
        return new int[]{d, intPart((l * 24) / 709), y};
    }

    public static int[] islToChr(int y, int m, int d, int diff) {
        int jd = ((((((intPart(((y * 11) + 3) / 30) + (y * 354)) + (m * 30)) - intPart((m - 1) / 2)) + d) + 1948440) - 385) - diff;
        int l;
        int n;
        int i;
        int j;
        if (jd > 2299160) {
            l = jd + 68569;
            n = intPart((l * 4) / 146097);
            l -= intPart(((146097 * n) + 3) / 4);
            i = intPart(((l + 1) * 4000) / 1461001);
            l = (l - intPart((i * 1461) / 4)) + 31;
            j = intPart((l * 80) / 2447);
            d = l - intPart((j * 2447) / 80);
            l = intPart(j / 11);
            m = (j + 2) - (l * 12);
            y = (((n - 49) * 100) + i) + l;
        } else {
            j = jd + 1402;
            int k = intPart((j - 1) / 1461);
            l = j - (k * 1461);
            n = intPart((l - 1) / 365) - intPart(l / 1461);
            i = (l - (n * 365)) + 30;
            j = intPart((i * 80) / 2447);
            d = i - intPart((j * 2447) / 80);
            i = intPart(j / 11);
            m = (j + 2) - (i * 12);
            y = (((k * 4) + n) + i) - 4716;
        }
        return new int[]{d, m, y};
    }

    public static String[] isToString(Context ctx, int y, int m, int d, int diff) {
        String[] monthsH = ctx.getResources().getStringArray(R.array.MonthNames);
        String[] months = new String[]{"Muharram", "Safar", "Rabi-al Awwal", "Rabi-al Thani", "Jumada al-Ula", "Jumada al-Thani", "Rajab", "Sha'ban", "Ramadhan", "Shawwal", "Dhul Qa'dah", "Dhul Hijjah"};
        int[] r2 = chrToIsl(y, m, d, diff);
//        int[] r2 = new int[3];
//        r2 = chrToIsl(y, m, d, diff);
        return new String[]{Integer.toString(r2[0]), months[r2[1] - 1], monthsH[r2[1] - 1], Integer.toString(r2[2])};
    }

    String[] chrToString(int y, int m, int d, int diff) {
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int[] r1 = islToChr(y, m, d, diff);
//        int[] r1 = new int[3];
//        r1 = islToChr(y, m, d, diff);
        return new String[]{Integer.toString(r1[0]), months[r1[1] - 1], Integer.toString(r1[2])};
    }

    public static String HijriDisplay(Context ctx) {
        AppSettings settings = AppSettings.getInstance(ctx);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String Hijri_Adjust = settings.getAdjustHijriDiff(0);
//        String Hijri_Adjust = AppSettings.getInstance(ctx).getString(AppSettings.Key.sHijriAdjust);
        String Hijri_Format = prefs.getString("Hijri_Format", "0");
        String datedisplayed = "";
        String dayofweek;
        String[] daynames = ctx.getResources().getStringArray(R.array.weekdays);
        Calendar cal = DateAndTimeHelper.GetToday();
        int d = cal.get(Calendar.DATE);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int dname = cal.get(Calendar.DAY_OF_WEEK);
        int adjst = Integer.parseInt(Hijri_Adjust);
        int format = Integer.parseInt(Hijri_Format);
        dayofweek = daynames[dname - 1];
        if (format == 0) {
            String[] hij = isToString(ctx, y, m, d, adjst);
            return dayofweek + " " + hij[0] + " " + hij[2] + " " + hij[3];
        }
        int[] hijint = chrToIsl(y, m, d, adjst);
        return dayofweek + " " + hijint[0] + "/ " + hijint[1] + "/ " + hijint[2];
    }
}
