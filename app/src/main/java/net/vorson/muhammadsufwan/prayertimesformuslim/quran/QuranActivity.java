package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import net.vorson.muhammadsufwan.prayertimesformuslim.HomeActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.IslamicCalendarActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.compass.QiblaActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.customWidget.ViewPagerAdapter;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.fragments.JuzQuranFragment;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.fragments.SurahQuranFragment;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.PermissionUtils;

public class QuranActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SurahQuranFragment surahQuranFragment;
    private JuzQuranFragment juzQuranFragment;
    private AppSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpagerQuran);
        viewPager.setOffscreenPageLimit(2);

        //Initializing the tablayout
        tabLayout = findViewById(R.id.tablayoutQuran);
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
        PermissionUtils.get(this).needStorage(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationQuran);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home_view:
                        startActivity(new Intent(QuranActivity.this, HomeActivity.class));
                        break;
                    case R.id.action_qibla_view:
                        startActivity(new Intent(QuranActivity.this, QiblaActivity.class));
                        break;
                    case R.id.action_quran_view:

                        break;
                    case R.id.action_islamic_calendar:
                        startActivity(new Intent(QuranActivity.this, IslamicCalendarActivity.class));
                        break;
                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        surahQuranFragment = new SurahQuranFragment();
        juzQuranFragment = new JuzQuranFragment();
        adapter.addFragment(surahQuranFragment, "Surah");
        adapter.addFragment(juzQuranFragment, "Juz");
        viewPager.setAdapter(adapter);
    }
}
