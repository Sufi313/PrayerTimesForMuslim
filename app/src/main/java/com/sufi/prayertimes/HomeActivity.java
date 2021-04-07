package com.sufi.prayertimes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sufi.prayertimes.compass.QiblaActivity;
import com.sufi.prayertimes.customWidget.ViewPagerAdapter;
import com.sufi.prayertimes.findMosque.FindMosqueActivity;
import com.sufi.prayertimes.fragments.MonthlyPrayersTimeFragment;
import com.sufi.prayertimes.fragments.PrayerTimeFragment;
import com.sufi.prayertimes.fragments.WeeklyPrayersTimeFragment;
import com.sufi.prayertimes.quran.QuranActivity;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;
import com.sufi.prayertimes.settingsAndPreferences.SettingsActivity;
import com.sufi.prayertimes.util.GetData;
import com.sufi.prayertimes.util.GpsTracker;
import com.sufi.prayertimes.util.PermissionUtils;

import com.sufi.prayertimes.util.ScreenUtils;

import java.util.HashMap;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity implements GetData.GetDataListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PrayerTimeFragment prayerTimeFragment;
    private WeeklyPrayersTimeFragment weeklyPrayersTimeFragment;
    private MonthlyPrayersTimeFragment monthlyPrayersTimeFragment;
    private Fragment mFragment;

    private AppSettings settings;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.lockOrientation(this);
        setContentView(R.layout.activity_home);

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
            if (PermissionUtils.get(this).pLocation) {
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

        ImageView imageViewTest = findViewById(R.id.toolbarImageTicketActivity);
        imageViewTest.setOnClickListener(v -> {
            //openFrag(new NamesFragment());
            startActivity(new Intent(HomeActivity.this , FindMosqueActivity.class));
        });

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

    public static abstract class MainFragment extends Fragment {
        public MainFragment() {
            super();
            setHasOptionsMenu(true);
        }

        public boolean onlyPortrait() {
            return false;
        }


        public boolean onBackPressed() {
            return false;
        }

        public HomeActivity getBaseActivity() {
            return (HomeActivity) getActivity();
        }

        public void backToMain() {
            FragmentManager fm = getBaseActivity().getSupportFragmentManager();
            int c = fm.getBackStackEntryCount();
            for (int i = 0; i < c; i++) {
                fm.popBackStack();
            }
        }

        public boolean back() {
            FragmentManager fm = getBaseActivity().getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();

                return true;
            }
            return false;
        }

        public void moveToFrag(Fragment frag) {
            getBaseActivity().moveToFrag(frag);
        }
    }

    public void moveToFrag(Fragment frag) {
        mFragment = frag;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basecontent, frag)
                .addToBackStack(null)
                .commit();
        if (frag instanceof MainFragment)
            setRequestedOrientation(((MainFragment)frag).onlyPortrait() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();

        for (int i = fm.getBackStackEntryCount() - 1; i >= 0; i--) {
            fm.popBackStack();
        }
    }

    private void openFrag(Fragment frag) {
        clearBackStack();
        mFragment = frag;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.basecontent, frag)
                .commit();
        if (frag instanceof MainFragment)
            setRequestedOrientation(((MainFragment)frag).onlyPortrait() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

}
