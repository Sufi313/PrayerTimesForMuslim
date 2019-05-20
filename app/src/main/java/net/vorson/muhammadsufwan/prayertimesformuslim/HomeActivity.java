package net.vorson.muhammadsufwan.prayertimesformuslim;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import net.vorson.muhammadsufwan.prayertimesformuslim.compass.QiblaActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.customWidget.ViewPagerAdapter;
import net.vorson.muhammadsufwan.prayertimesformuslim.fragments.MonthlyPrayersTimeFragment;
import net.vorson.muhammadsufwan.prayertimesformuslim.fragments.PrayerTimeFragment;
import net.vorson.muhammadsufwan.prayertimesformuslim.fragments.WeeklyPrayersTimeFragment;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.QuranActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.SettingsActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.GetData;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.GpsTracker;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PermissionUtils;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.ScreenUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity implements GetData.GetDataListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PrayerTimeFragment prayerTimeFragment;
    private WeeklyPrayersTimeFragment weeklyPrayersTimeFragment;
    private MonthlyPrayersTimeFragment monthlyPrayersTimeFragment;

    private AppSettings settings;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ScreenUtils.lockOrientation(this);

        PermissionUtils.get(this).needLocation(this);

        settings = AppSettings.getInstance(this);
        if (!settings.getBoolean(AppSettings.Key.IS_INIT)) {
            settings.set(settings.getKeyFor(AppSettings.Key.IS_ALARM_SET, 0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_FAJR_ALARM_SET, 0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_DHUHR_ALARM_SET, 0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_ASR_ALARM_SET, 0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_MAGHRIB_ALARM_SET, 0), true);
            settings.set(settings.getKeyFor(AppSettings.Key.IS_ISHA_ALARM_SET, 0), true);
            settings.set(AppSettings.Key.USE_ADHAN, true);
            settings.set(AppSettings.Key.IS_INIT, true);
        }
        if (settings.getString(AppSettings.Key.LAT_FOR) == null){
            if (!PermissionUtils.get(this).pLocation) {
                gpsTracker = new GpsTracker(this);
                if (gpsTracker.canGetLocation()) {
                    if (gpsTracker.getLatitude() != 0 && gpsTracker.getLongitude() != 0) {
                        settings.setLatFor(0, gpsTracker.getLatitude());
                        settings.setLngFor(0, gpsTracker.getLongitude());
                    }
                }else{
                    Toast.makeText(this, "An error accourd Device not found location please select menual", Toast.LENGTH_SHORT).show();
                }
            }
            else if (App.isOnline()){
                HashMap<String,String> params = new HashMap<>();
                new GetData("http://ip-api.com/json",params,10,this).execute();
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("");
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home_view:
                        break;
                    case R.id.action_qibla_view:
                        startActivity(new Intent(HomeActivity.this, QiblaActivity.class));
                        break;
                    case R.id.action_quran_view:
                        startActivity(new Intent(HomeActivity.this, QuranActivity.class));
                        break;
                    case R.id.action_islamic_calendar:
                        startActivity(new Intent(HomeActivity.this, IslamicCalendarActivity.class));
                        break;
                }
                return false;
            }
        });

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpagerHome);
        viewPager.setOffscreenPageLimit(2);

        //Initializing the tablayout
        tabLayout = findViewById(R.id.tablayoutHome);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        prayerTimeFragment = new PrayerTimeFragment();
        monthlyPrayersTimeFragment = new MonthlyPrayersTimeFragment();
        weeklyPrayersTimeFragment = new WeeklyPrayersTimeFragment();
        adapter.addFragment(prayerTimeFragment, "Pray Times");
        adapter.addFragment(weeklyPrayersTimeFragment, "Weekly Time");
        adapter.addFragment(monthlyPrayersTimeFragment, "Monthly Time");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getUserCountry(Context context) {
        StringBuilder ret = new StringBuilder();
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                ret.append(simCountry.toLowerCase(Locale.US));
                ret.append("\n");
                ret.append(tm.getNetworkOperatorName());
                ret.append("\n");
                ret.append(tm.getNetworkType());
                ret.append("\n");
                ret.append(tm.getPhoneType());
                return ret.toString();
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    ret.append(simCountry.toLowerCase(Locale.US));
                    ret.append("\n");
                    ret.append(tm.getNetworkOperatorName());
                    ret.append("\n");
                    ret.append(tm.getNetworkType());
                    ret.append("\n");
                    ret.append(tm.getPhoneType());
                    return ret.toString();

                }
            }
        }
        catch (Exception e) {
            ret.append(e.getMessage());
        }
        return ret.toString();
    }

    @Override
    public void getDownloadData(String result, int request) {
        if (request == 10){
            Log.d(TAG, "getDownloadData: "+result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.get(this).onRequestPermissionResult(permissions, grantResults);
    }
}
