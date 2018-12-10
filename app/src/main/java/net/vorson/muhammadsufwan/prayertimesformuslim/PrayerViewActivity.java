package net.vorson.muhammadsufwan.prayertimesformuslim;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.annotations.NonNull;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants;

import net.vorson.muhammadsufwan.prayertimesformuslim.util.LocationHelper;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PermissionUtils;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PrayTime;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.ScreenUtils;

import java.util.LinkedHashMap;


public class PrayerViewActivity extends AppCompatActivity implements Constants, LocationHelper.LocationCallback {

    private static boolean sIsAlarmInit = false;
    int mIndex = 0;

    private PermissionUtils mNeedPermissions;
    private Location mLastLocation = null;

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

        mNeedPermissions = PermissionUtils.get(this);

        LocationHelper locationHelper = new LocationHelper();
        mLastLocation = locationHelper.getLocation();

        Toast.makeText(this, "fdsfsdfdsf", Toast.LENGTH_SHORT).show();

        PrayTime prayTime = new PrayTime();
        prayTime.setCalcMethod(1);
        prayTime.setAsrJuristic(1);
        prayTime.setAdjustHighLats(0);
        prayTime.setTimeFormat(1);

        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayTime.tune(offsets);

        LinkedHashMap<String, String> prayerTimes = PrayTime.getPrayerTimes(this, mIndex, 25.008781, 67.0618058);

        fajr.setText(prayerTimes.get(String.valueOf(fajr.getTag())));
        dhuhr.setText(prayerTimes.get(String.valueOf(dhuhr.getTag())));
        asr.setText(prayerTimes.get(String.valueOf(asr.getTag())));
        maghrib.setText(prayerTimes.get(String.valueOf(maghrib.getTag())));
        isha.setText(prayerTimes.get(String.valueOf(isha.getTag())));

        final LottieAnimationView lottieAnimationView =  findViewById(R.id.animation_view);
        lottieAnimationView.setImageAssetsFolder("images/");
        lottieAnimationView.setAnimation("world_spin.json");
        lottieAnimationView.playAnimation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.get(this).onRequestPermissionResult(permissions, grantResults);
    }

    @Override
    public void onLocationSettingsFailed() {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }
}
