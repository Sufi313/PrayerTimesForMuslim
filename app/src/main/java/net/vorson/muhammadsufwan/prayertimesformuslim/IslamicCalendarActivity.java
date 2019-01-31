package net.vorson.muhammadsufwan.prayertimesformuslim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.vorson.muhammadsufwan.prayertimesformuslim.compass.QiblaActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.fragments.HijriCalendarFragment;
import net.vorson.muhammadsufwan.prayertimesformuslim.fragments.WeeklyPrayersTimeFragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class IslamicCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamic_calendar);
        Toolbar toolbar = findViewById(R.id.toolbarIslamicCalendar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("");
        }
        setFragment(new HijriCalendarFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home_view:
                        startActivity(new Intent(IslamicCalendarActivity.this, HomeActivity.class));
                        break;
                    case R.id.action_qibla_view:
                        startActivity(new Intent(IslamicCalendarActivity.this, QiblaActivity.class));
                        break;
                    case R.id.action_quran_view:
                        break;

                    case R.id.action_islamic_calendar:

                        break;
                }
                return false;
            }
        });
    }


    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayoutIslamicCalendar, fragment).addToBackStack(null).commit();
    }
}
