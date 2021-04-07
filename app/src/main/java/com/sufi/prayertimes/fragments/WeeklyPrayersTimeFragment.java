package com.sufi.prayertimes.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.angads25.filepicker.model.DialogConfigs;
import com.sufi.prayertimes.R;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;



import com.sufi.prayertimes.util.Hijri;
import com.sufi.prayertimes.util.PrayTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WeeklyPrayersTimeFragment extends Fragment {

    private static String Hijri_Adjust = "";
    private AppSettings settings;
    private Context mContext;
    Calendar cal;
    int currentMonth;
    LinearLayout table;
    int today;
    private ImageButton nextWeek;
    private ImageButton previewWeek;
    private TextView hmonthTv;
    private TextView gmonthTv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_prayers_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        settings = AppSettings.getInstance(mContext);
        Hijri_Adjust = settings.getAdjustHijriDiff(0);
        table = view.findViewById(R.id.week);
        hmonthTv = view.findViewById(R.id.table_main_month);
        gmonthTv = view.findViewById(R.id.table_second_month);
        Date now = new Date();
        cal = Calendar.getInstance();
        cal.setTime(now);
        today = cal.get(Calendar.DATE);
        currentMonth = cal.get(Calendar.MONTH) + 1;
        weeklyGregorian(-1);
        nextWeek = view.findViewById(R.id.table_next_button);
        previewWeek = view.findViewById(R.id.table_previews_button);

        previewWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.removeAllViews();
                table.refreshDrawableState();
                weeklyGregorian(-14);
            }
        });

        nextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.removeAllViews();
                table.refreshDrawableState();
                weeklyGregorian(0);
            }
        });
    }


    private void weeklyGregorian(int days) {
        table.setVisibility(View.VISIBLE);
        cal.add(Calendar.DAY_OF_YEAR, days);
        addViews();
    }

    private void addViews() {
        String s1 = HijriTitleDisplay(mContext, cal)[0];
        String t1 = HijriTitleDisplay(mContext, cal)[1];
        String gs1 = gregorianTitleMonth(cal)[0];
        String gt1 = gregorianTitleMonth(cal)[1];
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            View row = LayoutInflater.from(mContext).inflate(R.layout.table_weekly, null);
            LinearLayout rowHolder = row.findViewById(R.id.table_row_holder);
            TextView dateUp = row.findViewById(R.id.tup);
            TextView dateDown = row.findViewById(R.id.tdown);
            TextView timeF = row.findViewById(R.id.t1);
            TextView timeD = row.findViewById(R.id.t2);
            TextView timeA = row.findViewById(R.id.t3);
            TextView timeM = row.findViewById(R.id.t4);
            TextView timeI = row.findViewById(R.id.t5);

            int dayg = cal.get(Calendar.DATE);
            int month = cal.get(Calendar.MONTH) + 1;

            //Get offset from UTC, accounting for DST
            int defaultTzOffsetMs = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
            double timezone = defaultTzOffsetMs / (1000 * 60 * 60);

            // Test Prayer times here
            PrayTime prayers = new PrayTime();

            prayers.setTimeFormat(PrayTime.TIME_12);
            prayers.setCalcMethod(PrayTime.KARACHI);
            prayers.setAsrJuristic(PrayTime.HANAFI);
            prayers.setAdjustHighLats(PrayTime.ANGLE_BASED);
            prayers.setTimeFormat(PrayTime.TIME_12_NS);

            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
            prayers.tune(offsets);

            ArrayList<String> PTimes = prayers.getPrayerTimes(cal, settings.getLatFor(0), settings.getLngFor(0), timezone);

            dateDown.setText(dayg + DialogConfigs.DIRECTORY_SEPERATOR + month);
            timeF.setText(PTimes.get(0));
            timeD.setText(PTimes.get(2));
            timeA.setText(PTimes.get(3));
            timeM.setText(PTimes.get(5));
            timeI.setText(PTimes.get(6));
            dateUp.setText(HijriDisplay(cal));
            if (i % 2 == 0) {
                row.setBackgroundColor(getResources().getColor(R.color.white));
            }
            if (dayg == today && month == currentMonth) {
                rowHolder.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                dateDown.setTextColor(getResources().getColor(R.color.white));
                dateUp.setTextColor(getResources().getColor(R.color.white));
                timeF.setTextColor(getResources().getColor(R.color.white));
                timeD.setTextColor(getResources().getColor(R.color.white));
                timeA.setTextColor(getResources().getColor(R.color.white));
                timeM.setTextColor(getResources().getColor(R.color.white));
                timeI.setTextColor(getResources().getColor(R.color.white));
            }
            table.addView(row, new LinearLayout.LayoutParams(-1, -2, 1.0f));
        }
        table.requestLayout();
        String s2 = HijriTitleDisplay(mContext, cal)[0];
        String t2 = HijriTitleDisplay(mContext, cal)[1];
        String gs2 = gregorianTitleMonth(cal)[0];
        String gt2 = gregorianTitleMonth(cal)[1];
        hmonthTv.setText(multipleMonthsDisplay(s1, t1, s2, t2));
        gmonthTv.setText(multipleMonthsDisplay(gs1, gt1, gs2, gt2));
    }

    private String[] HijriTitleDisplay(Context ctx, Calendar calt) {
        String[] datedisplayed = new String[2];
        String[] hijint = Hijri.isToString(ctx, calt.get(Calendar.YEAR), calt.get(Calendar.MONTH) + 1, calt.get(Calendar.DATE), Integer.parseInt(Hijri_Adjust));
        datedisplayed[0] = hijint[2];
        datedisplayed[1] = hijint[3];
        return datedisplayed;
    }

    private String multipleMonthsDisplay(String s1, String t1, String s2, String t2) {
        String hijriMonths = "";
        String currentMonth = "";
        String firstmonth = s1;
        String firstyear = t1;
        String lastmonth = s2;
        String lastyear = t2;
        if (firstyear.equals(lastyear)) {
            currentMonth = firstmonth;
        } else {
            currentMonth = firstmonth + " " + firstyear;
        }
        if (firstmonth.equals(lastmonth)) {
            return firstmonth + " " + firstyear;
        }
        return currentMonth + " / " + lastmonth + " " + lastyear;
    }

    private static String HijriDisplay(Calendar cal) {
        return Hijri.chrToIsl(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), Integer.parseInt(Hijri_Adjust))[0] + "";
    }

    private String[] gregorianTitleMonth(Calendar cale) {
        Resources res = mContext.getResources();
        String[] monthName = new String[2];
        int mIndex = cale.get(Calendar.MONTH);
        int myear = cale.get(Calendar.YEAR);
        monthName[0] = res.getStringArray(R.array.month_gregorian_names)[mIndex];
        monthName[1] = myear + "";
        return monthName;
    }

}
