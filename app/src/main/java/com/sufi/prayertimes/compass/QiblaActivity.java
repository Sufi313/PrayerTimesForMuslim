package com.sufi.prayertimes.compass;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sufi.prayertimes.R;
import com.sufi.prayertimes.compass.classes.OrientationCalculator;
import com.sufi.prayertimes.compass.classes.OrientationCalculatorImpl;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;

import com.sufi.prayertimes.HomeActivity;
import com.sufi.prayertimes.IslamicCalendarActivity;


import com.sufi.prayertimes.compass.classes.math.Matrix4;
import com.sufi.prayertimes.compass.classes.rotation.MagAccelListener;
import com.sufi.prayertimes.compass.classes.rotation.RotationUpdateDelegate;
import com.sufi.prayertimes.util.GpsTracker;
import com.sufi.prayertimes.util.ScreenUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.annotations.NonNull;

public class QiblaActivity extends AppCompatActivity implements LocationListener, RotationUpdateDelegate {

    private double mQAngle;
    private float mDist;
    public MagAccelListener mMagAccel;
    private GpsTracker gpsTracker;
    @NonNull
    private Matrix4 mRotationMatrix = new Matrix4();
    private int mDisplayRotation;
    private SensorManager mSensorManager;
    private boolean mOnlyNew;
    private MyCompassListener mList;
    @NonNull
    private OrientationCalculator mOrientationCalculator = new OrientationCalculatorImpl();
    @NonNull
    private float[] mDerivedDeviceOrientation = {0, 0, 0};
    private Frag2D mFrag2D;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.lockOrientation(this);
        setContentView(R.layout.activity_qibla);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationQibla);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home_view:
                        startActivity(new Intent(QiblaActivity.this, HomeActivity.class));
                        break;
                    case R.id.action_qibla_view:

                        break;
                    case R.id.action_quran_view:
                        break;
                    case R.id.action_islamic_calendar:
                        startActivity(new Intent(QiblaActivity.this, IslamicCalendarActivity.class));
                        break;
                }
                return false;
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mDisplayRotation = display.getRotation();

        // sensor listeners
        mMagAccel = new MagAccelListener(this);

        mFrag2D = new Frag2D();
        mList = mFrag2D;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag2D, mFrag2D, "2d");
        fragmentTransaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.unregisterListener(mMagAccel);

        mSensorManager.registerListener(mMagAccel, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mMagAccel, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);

        gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            Location lastKnownLocation = gpsTracker.getLocation();
            if (lastKnownLocation != null) {
                calcQiblaAngel(lastKnownLocation);
            }
        } else {
            AppSettings appSettings = AppSettings.getInstance();
            if (appSettings.getLatFor(0) != 0) {
                Location loc = new Location("custom");
                loc.setLatitude(appSettings.getLatFor(0));
                loc.setLongitude(appSettings.getLngFor(0));
                calcQiblaAngel(loc);
            } else {
                Toast.makeText(this, "There is no location found", Toast.LENGTH_SHORT).show();
                gpsTracker.showSettingsAlert();
            }
        }

    }

    public Location getLocation() {
        return mLocation;
    }

    public float getDistance() {
        return mDist;
    }

    public double getQiblaAngle() {
        return mQAngle;
    }

    @Override
    public void onPause() {
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locMan.removeUpdates(this);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    // RotationUpdateDelegate methods
    @Override
    public void onRotationUpdate(@NonNull float[] newMatrix) {
        // remap matrix values according to display rotation, as in
        // SensorManager documentation.
        switch (mDisplayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                break;
            case Surface.ROTATION_90:
                SensorManager.remapCoordinateSystem(newMatrix, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, newMatrix);
                break;
            case Surface.ROTATION_270:
                SensorManager.remapCoordinateSystem(newMatrix, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, newMatrix);
                break;
            default:
                break;
        }
        mRotationMatrix.set(newMatrix);
        mOrientationCalculator.getOrientation(mRotationMatrix, mDisplayRotation, mDerivedDeviceOrientation);

        mList.onUpdateSensors(mDerivedDeviceOrientation);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if ((System.currentTimeMillis() - location.getTime()) < (mOnlyNew ? (1000 * 60) : (1000 * 60 * 60 * 24))) {
            LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locMan.removeUpdates(this);
        }
    }

    private void calcQiblaAngel(@NonNull Location location) {
        mLocation = location;
        double lat1 = location.getLatitude();// Latitude of Desired Location
        double lng1 = location.getLongitude();// Longitude of Desired Location
        double lat2 = 21.4224698;// Latitude of Mecca (+21.45° north of Equator)
        double lng2 = 39.8262066;// Longitude of Mecca (-39.75° east of Prime
        // Meridian)

        double q = -getDirection(lat1, lng1, lat2, lng2);

        Location loc = new Location(location);
        loc.setLatitude(lat2);
        loc.setLongitude(lng2);
        mQAngle = q;
        mDist = location.distanceTo(loc) / 1000;
        mList.onUpdateDirection();

    }

    private double getDirection(double lat1, double lng1, double lat2, double lng2) {
        double dLng = lng1 - lng2;
        return Math.toDegrees(getDirectionRad(Math.toRadians(lat1), Math.toRadians(lat2), Math.toRadians(dLng)));
    }

    private double getDirectionRad(double lat1, double lat2, double dLng) {
        return Math.atan2(Math.sin(dLng), (Math.cos(lat1) * Math.tan(lat2)) - (Math.sin(lat1) * Math.cos(dLng)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public interface MyCompassListener {
        void onUpdateDirection();

        void onUpdateSensors(float[] rot);
    }
}
