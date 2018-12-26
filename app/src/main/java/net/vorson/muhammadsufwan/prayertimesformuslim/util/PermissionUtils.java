package net.vorson.muhammadsufwan.prayertimesformuslim.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import net.vorson.muhammadsufwan.prayertimesformuslim.App;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.reactivex.annotations.NonNull;

import static net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants.REQUEST_CALENDAR;
import static net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants.REQUEST_CAMERA;
import static net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants.REQUEST_LOCATION;
import static net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants.REQUEST_NOTIFICATION;
import static net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants.REQUEST_WRITE_EXTERNAL;

/**
 * Created by Sufwan on 14.12.2018.
 */
public class PermissionUtils {

    private static PermissionUtils mInstance;
    public boolean pCalendar;
    public boolean pCamera;
    public boolean pStorage;
    public boolean pLocation;
    public boolean pNotPolicy;
    public AppSettings appSettings;

    private PermissionUtils(@NonNull Context c) {
        checkPermissions(c);
    }

    public static PermissionUtils get(@NonNull Context c) {
        if (mInstance == null) {
            mInstance = new PermissionUtils(c);
        }
        return mInstance;
    }

    private void checkPermissions(@NonNull Context c) {
        pCalendar = ContextCompat.checkSelfPermission(c, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED;
        pCamera = ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        pStorage = ContextCompat.checkSelfPermission(c, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        pLocation = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            pNotPolicy = nm.isNotificationPolicyAccessGranted();

        } else
            pNotPolicy = true;

    }

    public void needNotificationPolicy(@NonNull final Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && act.isDestroyed())
            return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        NotificationManager nm = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);
        pNotPolicy = nm.isNotificationPolicyAccessGranted();
        if (!pNotPolicy) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            PackageManager packageManager = act.getPackageManager();
            if (intent.resolveActivity(packageManager) != null) {
                act.startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, REQUEST_NOTIFICATION);
            }
        }
    }

    public void needCamera(@NonNull final Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && act.isDestroyed())
            return;

        if (!pCamera) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);

            builder.setTitle(R.string.permissionCameraTitle)
                    .setMessage(R.string.permissionCameraText)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        }
                    });

            builder.show();

        }
    }


    public void needLocation(@NonNull final Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && act.isDestroyed())
            return;

        if (!pLocation) {

            AlertDialog.Builder builder = new AlertDialog.Builder(act);

            builder.setTitle(R.string.permissionLocationTitle).setMessage(R.string.permissionLocationText).setPositiveButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        }
                    });


            builder.show();

        }
    }


    public void needCalendar(@NonNull final Activity act, boolean force) {
        appSettings = AppSettings.getInstance(App.get());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && act.isDestroyed())
            return;

        if (!pCalendar && (!"-1".equals(appSettings.getCalendar()) || force)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(act);

            builder.setTitle(R.string.permissionCalendarTitle).setMessage(R.string.permissionCalendarText).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_CALENDAR);
                }
            });


            builder.show();
        }

    }


    public void needStorage(@NonNull final Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && act.isDestroyed())
            return;

        if (!pStorage) {

            AlertDialog.Builder builder = new AlertDialog.Builder(act);

            builder.setTitle(R.string.permissionStorageTitle)
                    .setMessage(R.string.permissionStorageText)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL);
                        }
                    });


            builder.show();

        }
    }


    public void onRequestPermissionResult(@NonNull String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            switch (permissions[i]) {
                case Manifest.permission.CAMERA:
                    pCamera = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Manifest.permission.WRITE_CALENDAR:
                    appSettings = AppSettings.getInstance(App.get());
                    pCalendar = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    if (!pCalendar) appSettings.setCalendar(0,"-1");
                    break;
                case Manifest.permission.ACCESS_NOTIFICATION_POLICY:
                    pNotPolicy = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    pLocation = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    pStorage = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                    break;
            }
        }
    }

}

