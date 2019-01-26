package net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.rotation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import io.reactivex.annotations.NonNull;


/**
 * Magnetometer / Accelerometer sensor fusion Smoothed by means of simple high
 * pass filter
 * <p/>
 * When it receives an event it will notify the constructor-specified delegate.
 *
 * @author Adam
 */
public class MagAccelListener implements SensorEventListener {
    // smoothed accelerometer values
    @NonNull
    public float[] mAccelVals = {0f, 0f, 9.8f};
    // smoothed magnetometer values
    @NonNull
    public float[] mMagVals = {0.5f, 0f, 0f};
    @NonNull
    private float[] mRotationM = new float[16];
    private boolean mIsReady;
    private RotationUpdateDelegate mRotationUpdateDelegate;

    public MagAccelListener(RotationUpdateDelegate rotationUpdateDelegate) {
        mRotationUpdateDelegate = rotationUpdateDelegate;
    }

    @Override
    public void onSensorChanged(@NonNull SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                smooth(event.values, mAccelVals, mAccelVals);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                smooth(event.values, mMagVals, mMagVals);
                mIsReady = true;
                break;
            default:
                break;
        }
        // wait until we have both a new accelerometer and magnetometer sample
        if (mIsReady) {
            mIsReady = false;
            fuseValues();
        }
    }

    private void fuseValues() {
        SensorManager.getRotationMatrix(mRotationM, null, mAccelVals, mMagVals);
        mRotationUpdateDelegate.onRotationUpdate(mRotationM);
    }

    private void smooth(float[] inv, float[] prevv, float[] outv) {
        float filterFactor = 0.05f;
        float filterFactorInv = 1.0f - filterFactor;
        outv[0] = (inv[0] * filterFactor) + (prevv[0] * filterFactorInv);
        outv[1] = (inv[1] * filterFactor) + (prevv[1] * filterFactorInv);
        outv[2] = (inv[2] * filterFactor) + (prevv[2] * filterFactorInv);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}