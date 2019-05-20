package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import net.vorson.muhammadsufwan.prayertimesformuslim.App;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.StoragePath;

import androidx.core.view.ViewCompat;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class SettingsManager {
    @NonNull
    public static String ARABIC_RESHAPPING = "ARABIC_RESHAPPING";
    @NonNull
    public static String AUDIO_FILES_PATH = "AUDIO_FILES_PATH";
    public static String AYAHAT_REPEAT = "AYAHAT_REPEAT";
    public static String AYAHAT_REPEAT_NUM = "AYAHAT_REPEAT_NUM";
    public static String BACKGROUND_ENABLED = "BACKGROUND_ENABLED";
    @NonNull
    public static String BACKLIGHT = "BACKLIGHT";
    @NonNull
    public static String BROWSING_METHOD_OPTIONS = "BROWSING_METHOD_OPTIONS";
    @NonNull
    public static String DEFAULT_RECIETER = "DEFAULT_RECIETER";
    @NonNull
    public static String DEFAULT_TAFSIR = "DEFAULT_TAFSIR";
    @NonNull
    public static String FONT_COLOR = "FONT_COLOR";
    @NonNull
    public static String FONT_COLOR_STROKE = "FONT_COLOR_STROKE";
    @NonNull
    public static String FONT_SIZE = "FONT_SIZE";
    @NonNull
    public static String NEIGHT_READING_MODE = "NEIGHT_READING_MODE";
    @NonNull
    public static String REPAIRVIEW_CURRENT_SURA = "REPAIRVIEW_CURRENT_SURA";
    @NonNull
    public static String REPAIRVIEW_CURRENT_WORD_INDEX = "REPAIRVIEW_CURRENT_WORD_INDEX";
    @NonNull
    public static String RUN_ONCE = "RUN_ONCE";
    public static String SESSION_REPEAT = "SESSION_REPEAT";
    @NonNull
    public static String STROKE_ENABLED = "STROKE_ENABLED";
    @NonNull
    public static String TAFSIR_ENABLED = "TAFSIR_ENABLED";
    public static String TAFSIR_FONT_SIZE = "TAFSIR_FONT_SIZE";
    public static String TEXT_BOLD = "TEXT_BOLD";
    public static String THEME_CHOOSE = "THEME_CHOOSE";
    @NonNull
    public static String WORD_MEANING = "WORD_MEANING";
    private final Context context;
    private SharedPreferences sharedPreferences;

    public SettingsManager(@NonNull Context context) {
        PackageInfo packageInfo;
        this.context = context;
        String str = "";
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            str = packageInfo.versionName;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("vers");
        stringBuilder.append(str.replace(".", ""));
        str = stringBuilder.toString();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getString(str, "") == "") {
            ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(new DisplayMetrics());
            Editor edit = sharedPreferences.edit();
            edit.putString(FONT_SIZE, "28");
            edit.putInt(FONT_COLOR, -16777216);
            edit.putInt(REPAIRVIEW_CURRENT_SURA, 1);
            edit.putInt(REPAIRVIEW_CURRENT_WORD_INDEX, 0);
            edit.putString(DEFAULT_RECIETER, "133");
            StoragePath storagePath = new StoragePath(context);
            edit.putString(AUDIO_FILES_PATH, App.getArrayOfPaths()[0]);
            edit.putBoolean(BACKLIGHT, true);
            edit.putBoolean(ARABIC_RESHAPPING, false);
            edit.putBoolean(WORD_MEANING, true);
            edit.putBoolean(RUN_ONCE, false);
            edit.putInt(FONT_COLOR_STROKE, -16776961);
            edit.putBoolean(RUN_ONCE, false);
            edit.putBoolean(STROKE_ENABLED, false);
            edit.putString(str, "ok");
            edit.putString(BROWSING_METHOD_OPTIONS, "");
            edit.putBoolean(NEIGHT_READING_MODE, false);
            edit.putString(DEFAULT_TAFSIR, "31");
            edit.putBoolean(TAFSIR_ENABLED, false);
            edit.putBoolean(TEXT_BOLD, true);
            edit.putBoolean(BACKGROUND_ENABLED, true);
            edit.putString(TAFSIR_FONT_SIZE, "20");
            edit.putBoolean(SESSION_REPEAT, false);
            edit.putBoolean(AYAHAT_REPEAT, false);
            edit.putString(AYAHAT_REPEAT_NUM, "3");
            edit.putString(THEME_CHOOSE, "FF009688,FF00796B,FF1DE9B6");
            edit.commit();
        }
    }

    public void setDefaultStorageAfterRemoveSDCard() {
        StoragePath storagePath = new StoragePath(this.context);
        setSettings(AUDIO_FILES_PATH, App.getArrayOfPaths()[0]);
    }

    public void setSettings(String str, int i) {
        Editor edit = sharedPreferences.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("");
        edit.putInt(stringBuilder.toString(), i);
        edit.commit();
    }

    public void setSettings(String str, Boolean bool) {
        Editor edit = sharedPreferences.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("");
        edit.putBoolean(stringBuilder.toString(), bool);
        edit.commit();
    }

    public void setSettings(String str, String str2) {
        Editor edit = sharedPreferences.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("");
        edit.putString(stringBuilder.toString(), str2);
        edit.commit();
    }

    @Nullable
    public Object getSettings(String str, Class cls) {
        if (cls == Integer.TYPE) {
            return Integer.valueOf(sharedPreferences.getInt(str, 0));
        }
        if (cls == String.class) {
            return sharedPreferences.getString(str, "");
        }
        return cls == Boolean.class ? Boolean.valueOf(sharedPreferences.getBoolean(str, false)) : null;
    }

    public static void clearPreferances() {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).edit();
        edit.clear();
        edit.commit();
    }

    public static boolean isRunBefore() {
        return PreferenceManager.getDefaultSharedPreferences(App.getAppContext()).getBoolean(RUN_ONCE, false);
    }
}
