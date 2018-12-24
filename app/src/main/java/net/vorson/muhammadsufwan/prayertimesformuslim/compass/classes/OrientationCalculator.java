package net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes;

import net.vorson.muhammadsufwan.prayertimesformuslim.compass.classes.math.Matrix4;

public interface OrientationCalculator {
    /**
     * Given a rotation matrix and the current device screen rotation, produce
     * values for azimuth, altitude, roll (yaw, pitch, roll)
     *
     * @param rotationMatrix - device rotation
     * @param screenRotation - device screen rotation
     * @param out            - array of float[3] to dump values into
     */
    void getOrientation(Matrix4 rotationMatrix, int screenRotation, float[] out);
}
