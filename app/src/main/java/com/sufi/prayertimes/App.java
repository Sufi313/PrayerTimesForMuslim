package com.sufi.prayertimes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.squareup.leakcanary.LeakCanary;
import com.sufi.prayertimes.quran.SettingsManager;
import com.sufi.prayertimes.quran.models.WordMeaning;
import com.sufi.prayertimes.util.AndroidTimeZoneProvider;
import com.sufi.prayertimes.util.TimeZoneChangedReceiver;

import org.joda.time.DateTimeZone;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.annotations.NonNull;

import static androidx.core.app.NotificationCompat.CATEGORY_SYSTEM;


public class App extends Application {

    public static final String TAG = App.class.getSimpleName();
    private static App mInstance;
    
    private Locale mSystemLocale;

    //    AL-QURAN VARIABLES
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_WIFI = 1;
    public static int browsingContent;
    public static int browsingMethod;
    public static int currentHighlightWordIndexDurngJoza;
    public static int currentHighlightWordIndexDurngSura;
    public static int currentJozaId;
    public static int currentLineIndex;
    public static int currentQuarterId;
    public static int currentSuraId;
    public static int currentTahfeezStage;
    public static String currentThemeIndex;
    public static int currentWordIndexDurngJozaForRotationScrolling;
    public static int currentWordIndexDurngSuraForRotationScrolling;
    public static int currentWordIndexForSoundPlaying;
    public static int currentWordIndexHilightForSoundPlay;
    public static boolean displayLastPageOnLoad;
    public static boolean haveToPlayReciter;
    public static boolean highLight;
    public static boolean isSoundPlaying;
    public static int[] listOfQuranHeight = new int[2];
    private static String[] pathString;
    public static HashMap<String, WordMeaning> wordMeaningHashMapForScrolling;


    @NonNull
    private Handler mHandler = new Handler();
    

    @NonNull
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        
        mSystemLocale = Locale.getDefault();

        DateTimeZone.setProvider(new AndroidTimeZoneProvider());
        registerReceiver(new TimeZoneChangedReceiver(), new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED));
        LeakCanary.install(this);
        createNotificationChannel();

    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static void applyNightReadingTheme(@NonNull ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != null) {
                if (childAt instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) childAt;
                    if (childAt.getBackground() != null && (childAt.getTag() == null || !childAt.getTag().equals("no_theme"))) {
                        convertToGrayscale(viewGroup2.getBackground());
                    }
                    applyNightReadingTheme(viewGroup2);
                } else if (childAt.getBackground() != null && (childAt.getTag() == null || !childAt.getTag().equals("no_theme"))) {
                    convertToGrayscale(childAt.getBackground());
                }
            }
        }
    }

    @NonNull
    public static Drawable convertToGrayscale(@NonNull Drawable drawable) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        drawable.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        return drawable;
    }

    public static void applyMyTheme(AppCompatActivity appCompatActivity, Toolbar toolbar) {
        if (Build.VERSION.SDK_INT >= 21) {
            appCompatActivity.getWindow().setNavigationBarColor(getCurrentThemeColors()[0]);
            appCompatActivity.getWindow().setStatusBarColor(getCurrentThemeColors()[1]);
        }
        if (toolbar != null && getCurrentThemeColors() != null && getCurrentThemeColors().length > 0) {
            toolbar.setBackgroundColor(getCurrentThemeColors()[0]);
        }
    }

    public static int[] getCurrentThemeColors() {
        if (currentThemeIndex == null) {
            reloadThemeColors();
        }
        String[] split = currentThemeIndex.split(",");
        return new int[]{new BigInteger(split[0], 16).intValue(), new BigInteger(split[1], 16).intValue(), new BigInteger(split[2], 16).intValue()};
    }

    public static void reloadThemeColors() {
        currentThemeIndex = (String) new SettingsManager(mInstance).getSettings(SettingsManager.THEME_CHOOSE, String.class);
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public Locale getSystemLocale() {
        return mSystemLocale;
    }
    
    public static String[] getArrayOfPaths() {
        if (pathString != null) {
            return pathString;
        }
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(mInstance, null);
        int i = 0;
        if (externalFilesDirs == null || externalFilesDirs.length != 2 || externalFilesDirs[0] == null || externalFilesDirs[1] == null) {
            StringBuilder stringBuilder;
            File file;
            int i2;
            StringBuilder stringBuilder2;
            int i3;
            StringBuilder stringBuilder3;
            int i4;
            List arrayList = new ArrayList();
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (externalStorageDirectory != null && externalStorageDirectory.exists()) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                stringBuilder4.append(File.separator);
                stringBuilder4.append("Android");
                stringBuilder4.append(File.separator);
                stringBuilder4.append("data");
                externalStorageDirectory = new File(stringBuilder4.toString());
                if (externalStorageDirectory.exists()) {
                    arrayList.add(externalStorageDirectory);
                }
            }
            String str = System.getenv("EXTERNAL_STORAGE");
            if (str != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(File.separator);
                stringBuilder.append("Android");
                stringBuilder.append(File.separator);
                stringBuilder.append("data");
                file = new File(stringBuilder.toString());
                if (file.exists()) {
                    arrayList.add(file);
                }
            }
            str = System.getenv("SECONDARY_STORAGE");
            if (str != null) {
                String[] split = str.split(":");
                for (String append : split) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(append);
                    stringBuilder2.append(File.separator);
                    stringBuilder2.append("Android");
                    stringBuilder2.append(File.separator);
                    stringBuilder2.append("data");
                    File file2 = new File(stringBuilder2.toString());
                    if (file2.exists()) {
                        arrayList.add(file2);
                    }
                }
            }
            str = System.getenv("EMULATED_STORAGE_TARGET");
            if (str != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(File.separator);
                stringBuilder.append("Android");
                stringBuilder.append(File.separator);
                stringBuilder.append("data");
                file = new File(stringBuilder.toString());
                if (file.exists()) {
                    arrayList.add(file);
                }
            }
            File[] listFiles = new File("/").listFiles();
            if (listFiles != null) {
                i2 = 0;
                while (i2 < listFiles.length) {
                    if (listFiles[i2].isDirectory() && !listFiles[i2].getName().equalsIgnoreCase("preload")
                            && !listFiles[i2].getName().equalsIgnoreCase("acct")
                            && !listFiles[i2].getName().equalsIgnoreCase("vendor")
                            && !listFiles[i2].getName().equalsIgnoreCase("d")
                            && !listFiles[i2].getName().equalsIgnoreCase("etc")
                            && !listFiles[i2].getName().equalsIgnoreCase("system")
                            && !listFiles[i2].getName().equalsIgnoreCase(CATEGORY_SYSTEM)
                            && !listFiles[i2].getName().equalsIgnoreCase("proc")) {
                        File[] listFiles2 = listFiles[i2].listFiles();
                        if (listFiles2 != null) {
                            int i5 = 0;
                            while (i5 < listFiles2.length) {
                                File[] listFiles3;
                                if (listFiles2[i5].isDirectory() && listFiles2[i5].getName().equalsIgnoreCase("android")) {
                                    listFiles3 = listFiles2[i5].listFiles();
                                    if (listFiles3 != null) {
                                        i3 = 0;
                                        while (i3 < listFiles3.length) {
                                            if (listFiles3[i3].isDirectory() && listFiles3[i3].getName().equalsIgnoreCase("data")) {
                                                arrayList.add(listFiles3[i3]);
                                                break;
                                            }
                                            i3++;
                                        }
                                    }
                                } else {
                                    listFiles3 = listFiles2[i5].listFiles();
                                    if (listFiles3 == null && listFiles2[i5].getName().equalsIgnoreCase("emulated")) {
                                        stringBuilder3 = new StringBuilder();
                                        stringBuilder3.append(listFiles2[i5].getAbsoluteFile());
                                        stringBuilder3.append(File.separator);
                                        stringBuilder3.append("0");
                                        listFiles3 = new File(stringBuilder3.toString()).listFiles();
                                    }
                                    if (listFiles3 != null) {
                                        int i6 = 0;
                                        while (i6 < listFiles3.length) {
                                            File[] listFiles4;
                                            if (listFiles3[i6].isDirectory() && listFiles3[i6].getName().equalsIgnoreCase("android")) {
                                                listFiles4 = listFiles3[i6].listFiles();
                                                if (listFiles4 != null) {
                                                    int i7 = 0;
                                                    while (i7 < listFiles4.length) {
                                                        if (listFiles4[i7].isDirectory() && listFiles4[i7].getName().equalsIgnoreCase("data")) {
                                                            arrayList.add(listFiles4[i7]);
                                                            break;
                                                        }
                                                        i7++;
                                                    }
                                                }
                                            } else {
                                                listFiles4 = listFiles3[i6].listFiles();
                                                if (listFiles4 != null) {
                                                    int i8 = 0;
                                                    while (i8 < listFiles4.length) {
                                                        File[] listFiles5;
                                                        if (listFiles4[i8].isDirectory() && listFiles4[i8].getName().equalsIgnoreCase("android")) {
                                                            listFiles5 = listFiles4[i8].listFiles();
                                                            if (listFiles5 != null) {
                                                                int i9 = 0;
                                                                while (i9 < listFiles5.length) {
                                                                    if (listFiles5[i9].isDirectory() && listFiles5[i9].getName().equalsIgnoreCase("data")) {
                                                                        arrayList.add(listFiles5[i9]);
                                                                        break;
                                                                    }
                                                                    i9++;
                                                                }
                                                            }
                                                        } else {
                                                            listFiles5 = listFiles4[i8].listFiles();
                                                            if (listFiles5 != null) {
                                                                int i10 = 0;
                                                                while (i10 < listFiles5.length) {
                                                                    if (listFiles5[i10].isDirectory() && listFiles5[i10].getName().equalsIgnoreCase("android")) {
                                                                        File[] listFiles6 = listFiles5[i10].listFiles();
                                                                        if (listFiles6 != null) {
                                                                            int i11 = 0;
                                                                            while (i11 < listFiles6.length) {
                                                                                if (listFiles6[i11].isDirectory() && listFiles6[i11].getName().equalsIgnoreCase("data")) {
                                                                                    arrayList.add(listFiles6[i11]);
                                                                                    break;
                                                                                }
                                                                                i11++;
                                                                            }
                                                                        }
                                                                    }
                                                                    i10++;
                                                                }
                                                            }
                                                        }
                                                        i8++;
                                                    }
                                                }
                                            }
                                            i6++;
                                        }
                                    }
                                }
                                i5++;
                            }
                        }
                    }
                    i2++;
                }
            }
            str = "";
            File[] externalFilesDirs2 = ContextCompat.getExternalFilesDirs(mInstance, null);
            if (arrayList.size() == 0) {
                for (i3 = 0; i3 < externalFilesDirs2.length; i3++) {
                    if (externalFilesDirs2[i3] != null) {
                        arrayList.add(externalFilesDirs2[i3].getParentFile().getParentFile());
                    }
                }
            }
            if (arrayList.size() == 1 && externalFilesDirs2.length > 1 && externalFilesDirs2[1] != null) {
                arrayList.add(externalFilesDirs2[1].getParentFile().getParentFile());
            }
            externalFilesDirs2 = new File[arrayList.size()];
            String str2 = str;
            for (i4 = 0; i4 < arrayList.size(); i4++) {
                StringBuilder stringBuilder5 = new StringBuilder();
                stringBuilder5.append(((File) arrayList.get(i4)).getAbsolutePath());
                stringBuilder5.append(File.separator);
                stringBuilder5.append(mInstance.getPackageName());
                stringBuilder5.append(File.separator);
                stringBuilder5.append("files");
                externalFilesDirs2[i4] = new File(stringBuilder5.toString());
                externalFilesDirs2[i4].mkdirs();
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str2);
                stringBuilder2.append("* ");
                stringBuilder2.append(externalFilesDirs2[i4].getAbsolutePath());
                stringBuilder2.append(" ");
                stringBuilder2.append(externalFilesDirs2[i4].getParentFile().getTotalSpace());
                stringBuilder2.append(" ");
                stringBuilder2.append(externalFilesDirs2[i4].exists());
                stringBuilder2.append(" ");
                stringBuilder2.append(externalFilesDirs2[i4].canWrite());
                stringBuilder2.append(" \r\n");
                str2 = stringBuilder2.toString();
            }
            arrayList = new ArrayList();
            i4 = 0;
            while (i4 < externalFilesDirs2.length) {
                Object obj = null;
                for (i3 = 0; i3 < arrayList.size(); i3++) {
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(externalFilesDirs2[i4].getFreeSpace());
                    stringBuilder3.append("");
                    Log.d("fullAppPath[i]", stringBuilder3.toString());
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(((File) arrayList.get(i3)).getFreeSpace());
                    stringBuilder3.append("");
                    Log.d("filteredPaths.get(j)", stringBuilder3.toString());
                    if (externalFilesDirs2[i4].getFreeSpace() == ((File) arrayList.get(i3)).getFreeSpace()) {
                        obj = 1;
                    }
                }
                if (obj == null && externalFilesDirs2[i4].canWrite()) {
                    arrayList.add(externalFilesDirs2[i4]);
                }
                i4++;
            }
            pathString = new String[arrayList.size()];
            for (int i12 = 0; i12 < arrayList.size(); i12++) {
                pathString[i12] = ((File) arrayList.get(i12)).getAbsolutePath();
            }
            if (pathString.length == 0 && Build.VERSION.SDK_INT >= 19) {
                externalFilesDirs = new ContextWrapper(mInstance).getExternalFilesDirs(null);
                pathString = new String[externalFilesDirs.length];
                while (i < externalFilesDirs.length) {
                    pathString[i] = externalFilesDirs[i] != null ? externalFilesDirs[i].getAbsolutePath() : null;
                    i++;
                }
            }
            return pathString;
        }
        pathString = new String[]{externalFilesDirs[0].getAbsolutePath(), externalFilesDirs[1].getAbsolutePath()};
        return pathString;
    }



    public static String[] getArrayOfDisplayName() {
        String[] strArr = new String[getArrayOfPaths().length];
        String[] r1 = new String[6];
        int i = 0;
        r1[0] = mInstance.getString(R.string.internal_storage);
        r1[1] = mInstance.getString(R.string.external_storage);
        r1[2] = "USB 1";
        r1[3] = "USB 2";
        r1[4] = "USB 3";
        r1[5] = "USB 4";
        while (i < getArrayOfPaths().length) {
            strArr[i] = r1[i];
            i++;
        }
        return strArr;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("SimpleNotification", name, importance);
            channel.setDescription(description);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableLights(true);
            channel.setLightColor(2184028);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
