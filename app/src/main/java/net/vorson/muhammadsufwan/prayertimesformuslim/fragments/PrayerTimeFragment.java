package net.vorson.muhammadsufwan.prayertimesformuslim.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.GpsTracker;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PrayTime;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;

public class PrayerTimeFragment extends Fragment implements Constants {

    private static final String TAG = PrayerTimeFragment.class.getSimpleName();
    private static final String FULL_DATE_PATTERN = "dd MMMM YYYY";
    private static final DateTimeFormatter FULL_DATE_FORMATTTER = DateTimeFormat.forPattern(FULL_DATE_PATTERN);
    private Timer timer;
    private TimerTask timerTask;
    private TextView fajr;
    private TextView sunrise;
    private TextView dhuhr;
    private TextView asr;
    private TextView maghrib;
    private TextView isha;
    private TextView remainingPrayTimeTv;
    private TextView remainingPrayNameTv;

    private int mIndex = 0;

    public PrayerTimeFragment() {
        // Required empty public constructor
    }

    public static PrayerTimeFragment newInstance(int index, Location location) {
        PrayerTimeFragment fragment = new PrayerTimeFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ALARM_INDEX, index);
        args.putParcelable(EXTRA_LAST_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prayer_time, container, false);
        init(view);
        return view;
    }

    protected void init(View view) {

        AppSettings settings = AppSettings.getInstance(getActivity());

        settings.setCalcMethodFor(mIndex, PrayTime.KARACHI);
        settings.setAsrMethodFor(mIndex, PrayTime.HANAFI);
        settings.setTimeFormatFor(mIndex, PrayTime.TIME_12);
        settings.setHighLatitudeAdjustmentMethodFor(mIndex, PrayTime.NONE);

        LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(getActivity(), mIndex, settings.getLatFor(mIndex), settings.getLngFor(mIndex));

        TextView city = view.findViewById(R.id.cityTV);
        TextView date = view.findViewById(R.id.dateTV);
        TextView hicriDate = view.findViewById(R.id.hicriDateTV);

        fajr = view.findViewById(R.id.fajr);
        sunrise = view.findViewById(R.id.sunrise);
        dhuhr = view.findViewById(R.id.dhuhr);
        asr = view.findViewById(R.id.asr);
        maghrib = view.findViewById(R.id.maghrib);
        isha = view.findViewById(R.id.isha);
        remainingPrayTimeTv = view.findViewById(R.id.remainigPrayTimeTV);
        remainingPrayNameTv = view.findViewById(R.id.remainigPrayNameTV);

        fajr.setText(prayerTimes.get("Fajr"));
        sunrise.setText(prayerTimes.get("Sunrise"));
        dhuhr.setText(prayerTimes.get("Dhuhr"));
        asr.setText(prayerTimes.get("Asr"));
        maghrib.setText(prayerTimes.get("Maghrib"));
        isha.setText(prayerTimes.get("Isha"));

        String gregorianDate = LocalDate.now().toString(FULL_DATE_FORMATTTER);
        String hijriDate = getHijriDate();

        date.setText(gregorianDate);
        hicriDate.setText(hijriDate);
        city.setText(getCompleteAddressString(settings.getLatFor(0), settings.getLngFor(0)));

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                strReturnedAddress.append(returnedAddress.getLocality()).append(" ").append(returnedAddress.getCountryName());

                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", returnedAddress.toString());

            } else {
                Toast.makeText(getActivity(), "No Address Found", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "No Address Found--->" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }

    private String getHijriDate() {
        LocalDateTime hijriNow = LocalDateTime.now(IslamicChronology.getInstance());
        String originalHijriDate = hijriNow.toString(FULL_DATE_PATTERN, Locale.getDefault());

        String hijriMonthName = getString(getResources().getIdentifier("hijriMonth" + hijriNow.getMonthOfYear(), "string", getContext().getApplicationInfo().packageName));

        return originalHijriDate.replaceAll("^(.+) (.+) (.+)$", "$1 " + hijriMonthName + " $3");
    }

}
