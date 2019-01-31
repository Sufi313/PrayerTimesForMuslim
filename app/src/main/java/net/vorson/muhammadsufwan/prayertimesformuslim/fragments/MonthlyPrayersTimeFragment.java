package net.vorson.muhammadsufwan.prayertimesformuslim.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.angads25.filepicker.model.DialogConfigs;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.Hijri;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PrayTime;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class MonthlyPrayersTimeFragment extends Fragment {

    private static String Hijri_Adjust = "";
    private AppSettings settings;
    private Context mContext;
    private Spinner spinner;
    Calendar cal;
    LinearLayout table;
    ListView listView;
    TextView headerTv;
    private ImageButton nextMonth;
    private ImageButton previewMonth;
    TextView subHeaderTv;
    int HIJRI = 0;
    int GREGORIAN = 1;
    int currentMonth;
    int currentYear;
    int mode;
    int today;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_prayers_time, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        settings = AppSettings.getInstance(mContext);
        Hijri_Adjust = settings.getAdjustHijriDiff(0);
        table = view.findViewById(R.id.week);
        table.setVisibility(View.VISIBLE);
        cal = Calendar.getInstance();
        today = cal.get(Calendar.DATE);
        currentMonth = cal.get(Calendar.MONTH) + 1;
        currentYear = cal.get(Calendar.YEAR);
        nextMonth = view.findViewById(R.id.table_next_button);
        previewMonth = view.findViewById(R.id.table_previews_button);
        subHeaderTv = view.findViewById(R.id.table_second_month);
        headerTv = view.findViewById(R.id.table_main_month);
        headerTv.setVisibility(View.VISIBLE);
        spinner = view.findViewById(R.id.table_month_spinner);
        spinner.setVisibility(View.VISIBLE);
        listView = view.findViewById(R.id.table_list);
        mode = this.HIJRI;
        listView.setAdapter(getHijriDays(Calendar.getInstance()));
        setHijriHeaders();
        this.nextMonth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar ada = (Calendar) listView.getAdapter().getItem(listView.getCount() - 1);
                ada.add(Calendar.DAY_OF_YEAR, 1);
                listView.setAdapter(getHijriDays(ada));
                setHijriHeaders();
            }
        });
        this.previewMonth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar ada = (Calendar) listView.getAdapter().getItem(0);
                ada.add(Calendar.DAY_OF_YEAR, -5);
                listView.setAdapter(getHijriDays(ada));
                setHijriHeaders();
            }
        });

    }

    private static String HijriDisplay(Calendar cal) {
        return Hijri.chrToIsl(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), Integer.parseInt(Hijri_Adjust))[0] + "";
    }

    private String[] HijriTitleDisplay(Context ctx, Calendar calt) {
        String[] datedisplayed = new String[2];
        String[] hijint = Hijri.isToString(ctx, calt.get(Calendar.YEAR), calt.get(Calendar.MONTH) + 1, calt.get(Calendar.DAY_OF_MONTH), Integer.parseInt(Hijri_Adjust));
        datedisplayed[0] = hijint[2];
        datedisplayed[1] = hijint[3];
        return datedisplayed;
    }

    private String[] gregorianTitleMonth(Calendar cale) {
        String[] monthName = new String[2];
        int mIndex = cale.get(Calendar.MONTH);
        int myear = cale.get(Calendar.YEAR);
        monthName[0] = mContext.getResources().getStringArray(R.array.month_gregorian_names)[mIndex];
        monthName[1] = myear + "";
        return monthName;
    }

    private ArrayAdapter<Calendar> getHijriDays(Calendar mCalendar) {
        int i;
        int monthMaxDays;
        ArrayList<Calendar> mHijriCalendarList = new ArrayList();
        final ArrayList<String> mHijriList = new ArrayList();
        final ArrayList<TablePrayerTimes> mPrayerTimes = new ArrayList();
        if (mode == HIJRI) {
            int lastHijriDay = 0;
            for (i = 0; i < 32; i++) {
                mCalendar.add(Calendar.DAY_OF_YEAR, 1);
                int dh = Integer.parseInt(HijriDisplay(mCalendar));
                if (dh <= lastHijriDay) {
                    break;
                }
                lastHijriDay = dh;
            }
            mCalendar.add(Calendar.DAY_OF_MONTH, (-lastHijriDay) - 1);
            monthMaxDays = lastHijriDay;
        } else {
            monthMaxDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            mCalendar.set(Calendar.DATE, 0);
        }
        for (i = 0; i < monthMaxDays; i++) {
            mCalendar.add(Calendar.DAY_OF_YEAR, 1);
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(mCalendar.getTimeInMillis());
            mHijriCalendarList.add(cl);
            mHijriList.add(HijriDisplay(cl));

            //Get offset from UTC, accounting for DST
            int defaultTzOffsetMs = cl.get(Calendar.ZONE_OFFSET) + cl.get(Calendar.DST_OFFSET);
            double timezone = defaultTzOffsetMs / (1000 * 60 * 60);

            // Test Prayer times here
            PrayTime prayers = new PrayTime();

            prayers.setTimeFormat(PrayTime.TIME_12);
            prayers.setCalcMethod(PrayTime.KARACHI);
            prayers.setAsrJuristic(PrayTime.HANAFI);
            prayers.setAdjustHighLats(PrayTime.ANGLE_BASED);
            prayers.setTimeFormat(PrayTime.TIME_24);

            int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
            prayers.tune(offsets);

            ArrayList<String> PTimes = prayers.getPrayerTimes(cl, settings.getLatFor(0), settings.getLngFor(0), timezone);
            TablePrayerTimes pt = new TablePrayerTimes();

            pt.p1 = PTimes.get(0);
            pt.p2 = PTimes.get(2);
            pt.p3 = PTimes.get(3);
            pt.p4 = PTimes.get(5);
            pt.p5 = PTimes.get(6);
            mPrayerTimes.add(pt);
        }
        final ArrayList<Calendar> arrayList = mHijriCalendarList;
        return new ArrayAdapter<Calendar>(mContext, R.layout.table_weekly, mHijriCalendarList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                TableViewHolder viewHolder;
                View row = convertView;
                if (convertView == null) {
                    row = getLayoutInflater().inflate(R.layout.table_weekly, parent, false);
                    viewHolder = new TableViewHolder();
                    viewHolder.text1 = row.findViewById(R.id.t1);
                    viewHolder.text2 = row.findViewById(R.id.t2);
                    viewHolder.text3 = row.findViewById(R.id.t3);
                    viewHolder.text4 = row.findViewById(R.id.t4);
                    viewHolder.text5 = row.findViewById(R.id.t5);
                    viewHolder.up = row.findViewById(R.id.tup);
                    viewHolder.down = row.findViewById(R.id.tdown);
                    viewHolder.holder = row.findViewById(R.id.table_row_holder);
                    row.setTag(viewHolder);
                } else {
                    viewHolder = (TableViewHolder) row.getTag();
                }
                Calendar hCalendar = arrayList.get(position);
                int dayg = hCalendar.get(Calendar.DATE);
                int month = hCalendar.get(Calendar.MONTH) + 1;
                TablePrayerTimes PTimes = mPrayerTimes.get(position);
                viewHolder.text1.setText(PTimes.p1);
                viewHolder.text2.setText(PTimes.p2);
                viewHolder.text3.setText(PTimes.p3);
                viewHolder.text4.setText(PTimes.p4);
                viewHolder.text5.setText(PTimes.p5);
                String up = mode == HIJRI ? mHijriList.get(position) : String.valueOf(dayg);
                String down = mode == HIJRI ? dayg + DialogConfigs.DIRECTORY_SEPERATOR + month : mHijriList.get(position);
                viewHolder.up.setText(up);
                viewHolder.down.setText(down);
                if (dayg == today && month == currentMonth && cal.get(Calendar.YEAR) == currentYear) {
                    viewHolder.holder.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                } else if (position % 2 == 0) {
                    viewHolder.holder.setBackgroundColor(mContext.getResources().getColor(R.color.blue_grey));
                } else {
                    viewHolder.holder.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    viewHolder.text1.setTextColor(mContext.getResources().getColor(R.color.white));
                    viewHolder.text2.setTextColor(mContext.getResources().getColor(R.color.white));
                    viewHolder.text3.setTextColor(mContext.getResources().getColor(R.color.white));
                    viewHolder.text4.setTextColor(mContext.getResources().getColor(R.color.white));
                    viewHolder.text5.setTextColor(mContext.getResources().getColor(R.color.white));
                    viewHolder.up.setTextColor(mContext.getResources().getColor(R.color.white));
                    viewHolder.down.setTextColor(mContext.getResources().getColor(R.color.white));
                }
                return row;
            }
        };
    }

    public String getMonthsNames(String[] firstDayTitle, String[] lastDayTitle) {
        String month = firstDayTitle[0];
        if (!month.equals(lastDayTitle[0])) {
            month = month + DialogConfigs.DIRECTORY_SEPERATOR + lastDayTitle[0];
        }
        String year = firstDayTitle[1];
        if (!year.equals(lastDayTitle[1])) {
            year = year + DialogConfigs.DIRECTORY_SEPERATOR + lastDayTitle[1];
        }
        return month + " " + year;
    }

    public void setHijriHeaders() {
        final Calendar firstDay = (Calendar) listView.getAdapter().getItem(listView.getFirstVisiblePosition());
        Calendar lastDay = (Calendar) listView.getAdapter().getItem(listView.getCount() - 1);
        String[] hFirstDayTitle = new String[]{HijriTitleDisplay(getActivity(), firstDay)[0], HijriTitleDisplay(getActivity(), firstDay)[1]};
        String[] hLastDayTitle = new String[]{HijriTitleDisplay(getActivity(), lastDay)[0], HijriTitleDisplay(getActivity(), lastDay)[1]};
        String gregorianHeader = getMonthsNames(gregorianTitleMonth((Calendar) listView.getAdapter().getItem(0)), gregorianTitleMonth(lastDay));
        String hijriHeader = getMonthsNames(hFirstDayTitle, hLastDayTitle);
        spinner.setAdapter(new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item,
                new String[]{HijriTitleDisplay(mContext, firstDay)[0] + " " + HijriTitleDisplay(mContext, firstDay)[1], gregorianTitleMonth(firstDay)[0] + " " + gregorianTitleMonth(firstDay)[1]}));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mode = position;
                listView.setAdapter(getHijriDays((Calendar) listView.getAdapter().getItem(0)));
                Calendar lastDay = (Calendar) listView.getAdapter().getItem(listView.getCount() - 1);
                String[] hFirstDayTitle = new String[]{HijriTitleDisplay(mContext, firstDay)[0], HijriTitleDisplay(mContext, firstDay)[1]};
                String[] hLastDayTitle = new String[]{HijriTitleDisplay(mContext, lastDay)[0], HijriTitleDisplay(mContext, lastDay)[1]};
                CharSequence gregorianHeader = getMonthsNames(gregorianTitleMonth((Calendar) listView.getAdapter().getItem(0)), gregorianTitleMonth(lastDay));
                String hijriHeader = getMonthsNames(hFirstDayTitle, hLastDayTitle);
                if (mode != HIJRI) {
                    Object gregorianHeader2 = hijriHeader;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner.setSelection(mode);
    }

    class TablePrayerTimes {
        String p1;
        String p2;
        String p3;
        String p4;
        String p5;

        TablePrayerTimes() {
        }
    }

    class TableViewHolder {
        TextView down;
        LinearLayout holder;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        TextView up;

        TableViewHolder() {
        }
    }

}

