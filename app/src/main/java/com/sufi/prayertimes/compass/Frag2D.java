package com.sufi.prayertimes.compass;

import android.annotation.SuppressLint;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.sufi.prayertimes.R;

public class Frag2D extends Fragment implements QiblaActivity.MyCompassListener {

    private CompassView mCompassView;
    private TextView mAngle;
    private TextView mDist;
    private float[] mGravity = new float[3];
    private float[] mGeo = new float[3];
    public boolean mHidden;

    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, ViewGroup container, Bundle bdl) {
        View v = inflater.inflate(R.layout.compass_2d, container, false);
        mCompassView = v.findViewById(R.id.compass);
        if (mHidden) {
            mCompassView.setScaleX(0);
            mCompassView.setScaleY(0);
        }

        mAngle = v.findViewById(R.id.angle);
        mDist = v.findViewById(R.id.distance);

        View info = (View) mAngle.getParent();
        ViewCompat.setElevation(info, info.getPaddingTop());
        onUpdateDirection();
        return v;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdateDirection() {
        if (mCompassView != null) {
            mCompassView.setQiblaAngle((int) ((QiblaActivity)getActivity()).getQiblaAngle());
            mAngle.setText(Math.round(mCompassView.getQiblaAngle()) + "°");
            mDist.setText(Math.round(((QiblaActivity)getActivity()).getDistance()) + "km");
        }
    }

    @Override
    public void onUpdateSensors(float[] rot) {
        if (mCompassView != null && getActivity() != null) {
            mCompassView.setAngle(rot[0]);
            mGravity = LowPassFilter.filter(((QiblaActivity)getActivity()).mMagAccel.mAccelVals, mGravity);
            mGeo = LowPassFilter.filter(((QiblaActivity)getActivity()).mMagAccel.mMagVals, mGeo);

            if ((mGravity != null) && (mGeo != null)) {
                float[] R = new float[9];
                float[] I = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeo);
                if (success) {
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(R, orientation);

                    mCompassView.setAngle((int) Math.toDegrees(orientation[0]));

                }
            }
        }

    }

}
