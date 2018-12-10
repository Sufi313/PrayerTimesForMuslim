package net.vorson.muhammadsufwan.prayertimesformuslim;

import androidx.appcompat.app.AppCompatActivity;
import io.fabric.sdk.android.Fabric;

import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.crashlytics.android.Crashlytics;

import net.vorson.muhammadsufwan.prayertimesformuslim.util.PermissionUtils;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PrayTime;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class PrayerViewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paryer_view);
        ScreenUtils.lockOrientation(this);

        TextView fajr = findViewById(R.id.fajr);
        TextView dhuhr = findViewById(R.id.dhuhr);
        TextView asr = findViewById(R.id.asr);
        TextView maghrib = findViewById(R.id.maghrib);
        TextView isha = findViewById(R.id.isha);

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        if (PermissionUtils.get(this).pLocation==false){
            PermissionUtils.get(this).needLocation(this);
        }

        double latitude = 25.008781;
        double longitude = 67.0618058;

        //Get NY time zone instance
        TimeZone defaultTz = TimeZone.getDefault();

        //Get NY calendar object with current date/time
        Calendar defaultCalc = Calendar.getInstance(defaultTz);

        //Get offset from UTC, accounting for DST
        int defaultTzOffsetMs = defaultCalc.get(Calendar.ZONE_OFFSET) + defaultCalc.get(Calendar.DST_OFFSET);
        double timezone = defaultTzOffsetMs / (1000 * 60 * 60);
        // Test Prayer times here
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(PrayTime.TIME_12);
        prayers.setCalcMethod(PrayTime.KARACHI);
        prayers.setAsrJuristic(PrayTime.HANAFI);
        prayers.setAdjustHighLats(PrayTime.ANGLE_BASED);

        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                latitude, longitude, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println( prayerTimes.get(i));
        }

        fajr.setText(prayerTimes.get(0));
        dhuhr.setText(prayerTimes.get(2));
        asr.setText(prayerTimes.get(3));
        maghrib.setText(prayerTimes.get(5));
        isha.setText(prayerTimes.get(6));

        final LottieAnimationView lottieAnimationView =  findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("world_spin.json");
        lottieAnimationView.playAnimation();

    }

}
