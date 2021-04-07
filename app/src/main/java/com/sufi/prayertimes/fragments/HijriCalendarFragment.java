package com.sufi.prayertimes.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sufi.prayertimes.R;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;
import com.sufi.prayertimes.util.Hijri;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HijriCalendarFragment extends Fragment {

    static int adjst;
    private int HijriMonth;
    private AppSettings settings;
    private int Hijriyear;
    Calendar cal;
    LinearLayout content;
    int currentMonth;
    int currentYear;
    TextView holidayTv;
    String[] holidaynames;
    int today;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hijri_calendar, container, false);


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        settings = AppSettings.getInstance(context);
        String Hijri_Adjust = settings.getAdjustHijriDiff(0);
        adjst = Integer.parseInt(Hijri_Adjust);
        Date now = new Date();
        cal = Calendar.getInstance();
        cal.setTime(now);
        int[] hijcur = Hijri.chrToIsl(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE), adjst);
        HijriMonth = hijcur[1];
        Hijriyear = hijcur[2];
        today = hijcur[0];
        currentMonth = hijcur[1];
        currentYear = hijcur[2];
        addViews(view);
        holidaynames = context.getResources().getStringArray(R.array.hijri_holidays_names);
        holidayTv = view.findViewById(R.id.hijri_holidays);
        ImageButton previewMonth = view.findViewById(R.id.hijri_previews_button);
        previewMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViews();
                decreaseMonth();
                addViews(view);
            }
        });
        ImageButton nextMonth = view.findViewById(R.id.hijri_next_button);
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViews();
                increaseMonth();
                addViews(view);
            }
        });
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout weekday = view.findViewById(R.id.hijri_week_days);
        View weekdaysView = layoutInflater.inflate(R.layout.hijri_row_week, weekday, false);
        weekDayNamesEn(layoutInflater, weekday, weekdaysView);

    }

    private void weekDayNamesEn(LayoutInflater layoutInflater, LinearLayout weekday, View weekdaysView) {
        for (int day = 0; day < 7; day++) {
            String[] weekd = context.getResources().getStringArray(R.array.weekdays);
            View weedDaycell = layoutInflater.inflate(R.layout.hijri_cell, weekday, false);
            TextView daycell =  weedDaycell.findViewById(R.id.hijri_cell_g);
            weedDaycell.findViewById(R.id.hijri_cell_h).setVisibility(View.VISIBLE);
            int index = day;
            if (day == 6) {
                index = -1;
            }
            daycell.setText(weekd[index + 1]);
            ((ViewGroup) weekdaysView).addView(weedDaycell);
        }
        weekday.addView(weekdaysView);
    }

    private void addViews(View view) {
        cal.setTime(HijriFirstDayOfMonth(context, HijriMonth, Hijriyear).getTime());
        TextView gmonthTv = view.findViewById(R.id.hijri_g_month);
        ((TextView) view.findViewById(R.id.hijri_h_month)).setText(context.getResources().getStringArray(R.array.MonthNames)[HijriMonth - 1] + " " + Hijriyear);
        gmonthTv.setText(gregorianMonth(this.cal));
        int todayDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (todayDayOfWeek == 1) {
            todayDayOfWeek = 8;
        }
        cal.add(Calendar.DAY_OF_MONTH, 2 - todayDayOfWeek);
        for (int week = 0; week < 6; week++) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View weekView = layoutInflater.inflate(R.layout.hijri_row_week, content, false);
            content = view.findViewById(R.id.hijri_content);
            boolean outweek = false;
            if (week == 5) {
                if (HijriMonth(context, cal) != HijriMonth) {
                    outweek = true;
                } else {
                    outweek = false;
                }
            }
            if (!outweek) {

                addWeekDaysEn(layoutInflater, weekView);

            }
        }
    }

    private void addWeekDaysEn(LayoutInflater layoutInflater, View weekView) {
        for (int day = 0; day < 7; day++) {
            int[] hijint = Hijri.chrToIsl(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), adjst);
            boolean inThisMonth = hijint[1] == HijriMonth;
            int cellLayoutResId = R.layout.hijri_cell;
            if (inThisMonth) {
                cellLayoutResId = R.layout.hijri_cell_in;
            }
            boolean inCurrentMonth = false;
            if (inThisMonth) {
                inCurrentMonth = currentMonth == HijriMonth;
            }
            boolean isToday = false;
            if (inCurrentMonth) {
                isToday = today == hijint[0] && currentYear == hijint[2];
            }
            View dayView = layoutInflater.inflate(cellLayoutResId, (ViewGroup) weekView, false);
            TextView dayGcell = dayView.findViewById(R.id.hijri_cell_g);
            TextView dayHcell = dayView.findViewById(R.id.hijri_cell_h);
            dayHcell.setText(hijint[0] + "");
            dayGcell.setText(cal.get(Calendar.DATE) + "");
            LinearLayout cellLay = dayView.findViewById(R.id.hijri_cell_in_layout);
            if (isToday) {
                cellLay.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            }
            String holidayname = checkIfHolyDay(hijint[1], hijint[0]);
            if (!holidayname.equalsIgnoreCase("") && inThisMonth) {
                cellLay.setBackgroundColor(Color.parseColor("#009900"));
                Log.i("holidayname", holidayname + "");
                holidayTv.append(holidayname + "\n");
            }
            ((ViewGroup) weekView).addView(dayView);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        content.addView(weekView);
    }

    private static int HijriMonth(Context ctx, Calendar cal) {
        return Hijri.chrToIsl(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), adjst)[1];
    }


    private static Calendar HijriFirstDayOfMonth(Context ctx, int m, int y) {
        Calendar cal = Calendar.getInstance();
        int[] hijint = Hijri.islToChr(y, m, 1, adjst);
        cal.set(Calendar.YEAR, hijint[2]);
        cal.set(Calendar.MONTH, hijint[1] - 1);
        cal.set(Calendar.DATE, hijint[0]);
        return cal;
    }

    private void increaseMonth() {
        if (HijriMonth != 12) {
            HijriMonth++;
            return;
        }
        HijriMonth = 1;
        Hijriyear++;
    }

    private void decreaseMonth() {
        if (HijriMonth != 1) {
            HijriMonth--;
            return;
        }
        HijriMonth = 12;
        Hijriyear--;
    }

    private String gregorianMonth(Calendar cl) {
        Calendar c = Calendar.getInstance();
        c.setTime(cl.getTime());
        String[] monthnames = context.getResources().getStringArray(R.array.month_gregorian_names);
        int[] monthLength = new int[]{30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29};
        int mIndex = c.get(Calendar.MONTH);
        int mYear = c.get(Calendar.YEAR);
        c.add(Calendar.DATE, monthLength[this.HijriMonth - 1]);
        int nIndex = c.get(Calendar.MONTH);
        int nYear = c.get(Calendar.YEAR);
        String monthStr = monthnames[mIndex];
        if (mIndex != nIndex) {
            monthStr = monthnames[mIndex] + " / " + monthnames[nIndex];
        }
        String yearStr = mYear + "";
        if (nYear != mYear) {
            yearStr = mYear + " / " + nYear;
        }
        return monthStr + "  " + yearStr;
    }

    private void clearViews() {
        content.removeAllViews();
        content.refreshDrawableState();
        holidayTv.setText(" ");
    }

    public String checkIfHolyDay(int hijriMonth, int hijriDay) {
        String holyDay = "";
        switch (hijriMonth) {
            case 1:
                if (hijriDay == 1) {
                    return holidaynames[0];
                }
                if (hijriDay == 10) {
                    return holidaynames[1];
                }
                return holyDay;
            case 3:
                if (hijriDay == 12) {
                }
                return holyDay;
            case 7:
                if (hijriDay == 27) {
                }
                return holyDay;
            case 9:
                if (hijriDay == 27) {
                    return holidaynames[4];
                }
                return holyDay;
            case 10:
                if (hijriDay == 1) {
                    return holidaynames[5];
                }
                return holyDay;
            case 12:
                if (hijriDay == 9) {
                    holyDay = holidaynames[6];
                }
                if (hijriDay == 10) {
                    return holidaynames[7];
                }
                return holyDay;
            default:
                return holyDay;
        }
    }

}



