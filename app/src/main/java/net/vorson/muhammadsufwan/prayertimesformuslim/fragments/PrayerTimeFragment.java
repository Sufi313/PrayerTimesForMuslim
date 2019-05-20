package net.vorson.muhammadsufwan.prayertimesformuslim.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.vorson.muhammadsufwan.prayertimesformuslim.HomeActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.MainActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.GpsTracker;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.Hijri;
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

        int mIndex = 0;
        settings.setCalcMethodFor(mIndex, PrayTime.KARACHI);
        settings.setAsrMethodFor(mIndex, PrayTime.HANAFI);
        settings.setTimeFormatFor(mIndex, PrayTime.TIME_12);
        settings.setHighLatitudeAdjustmentMethodFor(mIndex, PrayTime.NONE);

        LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(getActivity(), mIndex, settings.getLatFor(mIndex), settings.getLngFor(mIndex));

        TextView fajr = view.findViewById(R.id.fajr);
        TextView sunrise = view.findViewById(R.id.sunrise);
        TextView dhuhr = view.findViewById(R.id.dhuhr);
        TextView asr = view.findViewById(R.id.asr);
        TextView maghrib = view.findViewById(R.id.maghrib);
        TextView isha = view.findViewById(R.id.isha);

        TextView city = view.findViewById(R.id.cityTV);
        TextView date = view.findViewById(R.id.dateTV);
        TextView hicriDate = view.findViewById(R.id.hicriDateTV);
        TextView remainingPrayNameTv = view.findViewById(R.id.remainigPrayNameTV);
        TextView remainingPrayTimeTv = view.findViewById(R.id.remainigPrayTimeTV);

//        remainingPrayNameTv.setShadowLayer(2.5f, 0, 2, R.color.divider);
//        remainingPrayTimeTv.setShadowLayer(2.5f, 0, 2, R.color.divider);

        fajr.setText(prayerTimes.get("Fajr"));
        sunrise.setText(prayerTimes.get("Sunrise"));
        dhuhr.setText(prayerTimes.get("Dhuhr"));
        asr.setText(prayerTimes.get("Asr"));
        maghrib.setText(prayerTimes.get("Maghrib"));
        isha.setText(prayerTimes.get("Isha"));

        String gregorianDate = LocalDate.now().toString(FULL_DATE_FORMATTTER);

        date.setText(gregorianDate);
        hicriDate.setText(Hijri.HijriDisplay(getActivity()));
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



}
