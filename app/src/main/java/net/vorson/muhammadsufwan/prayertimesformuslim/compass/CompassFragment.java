package net.vorson.muhammadsufwan.prayertimesformuslim.compass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.OrientationCalculator;
import net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.OrientationCalculatorImpl;
import net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.math.Matrix4;
import net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.rotation.MagAccelListener;
import net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.rotation.RotationUpdateDelegate;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.GpsTracker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class CompassFragment extends Fragment implements LocationListener, RotationUpdateDelegate {

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


    public Location getLocation() {
        return mLocation;
    }

    public float getDistance() {
        return mDist;
    }

    public double getQiblaAngle() {
        return mQAngle;
    }

    @Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.compass_main, container, false);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);


        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mDisplayRotation = display.getRotation();

        // sensor listeners
        mMagAccel = new MagAccelListener(this);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFrag2D = new Frag2D();
        mList = mFrag2D;
        fragmentTransaction.add(R.id.frag2D, mFrag2D, "2d");
        fragmentTransaction.commit();

        return v;
    }

    @Override
    public void onSaveInstanceState(@androidx.annotation.NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.unregisterListener(mMagAccel);

        mSensorManager.registerListener(mMagAccel, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mMagAccel, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);

        gpsTracker = new GpsTracker(getActivity());
        if (gpsTracker.canGetLocation()) {
            Location lastKnownLocation = gpsTracker.getLocation();
            if (lastKnownLocation != null) {
                calcQiblaAngel(lastKnownLocation);
            }
        }else{
            AppSettings appSettings = AppSettings.getInstance();
            if (appSettings.getLatFor(0) != 0) {
                Location loc = new Location("custom");
                loc.setLatitude(appSettings.getLatFor(0));
                loc.setLongitude(appSettings.getLngFor(0));
                calcQiblaAngel(loc);
            } else {
                Toast.makeText(getActivity(), "There is no location found", Toast.LENGTH_SHORT).show();
                gpsTracker.showSettingsAlert();
            }
        }

    }

    @Override
    public void onPause() {
        LocationManager locMan = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
        if (getActivity() != null && (System.currentTimeMillis() - location.getTime()) < (mOnlyNew ? (1000 * 60) : (1000 * 60 * 60 * 24))) {
            LocationManager locMan = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
