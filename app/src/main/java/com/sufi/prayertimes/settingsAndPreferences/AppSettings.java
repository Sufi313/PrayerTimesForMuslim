package com.sufi.prayertimes.settingsAndPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;

import com.sufi.prayertimes.util.PrayTime;

import java.lang.ref.WeakReference;
import java.util.UUID;

import io.reactivex.annotations.NonNull;

public class AppSettings {
    public static final PrayTime sDefaults = new PrayTime();

    private static final String SETTINGS_NAME = "default_settings";
    private static AppSettings sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;
    private WeakReference<Context> mContextRef;

    /**
     * Class for keeping all the keys used for shared preferences in one place.
     */
    public static class Key {

        //ALARM RELATED
        public static final String IS_ALARM_SET = "is_alarm_set_for_%d";
        public static final String IS_FAJR_ALARM_SET = "is_fajr_alarm_set_for_%d";
        public static final String IS_DHUHR_ALARM_SET = "is_dhuhr_alarm_set_for_%d";
        public static final String IS_ASR_ALARM_SET = "is_asr_alarm_set_for_%d";
        public static final String IS_MAGHRIB_ALARM_SET = "is_maghrib_alarm_set_for_%d";
        public static final String IS_ISHA_ALARM_SET = "is_isha_alarm_set_for_%d";
        public static final String IS_RAMADAN = "is_ramadan";
        public static final String SUHOOR_OFFSET = "suhoor_offset";
        public static final String IFTAR_OFFSET = "iftar_offset";
        public static final String IS_ASCENDING_ALARM = "is_ascending_alarm";
        public static final String IS_RANDOM_ALARM = "is_random_alarm";
        public static final String SELECTED_RINGTONE = "ringtone_selected";
        public static final String SELECTED_RINGTONE_NAME = "ringtone_selected_name";
        public static final String USE_ADHAN = "use_adhan";
        public static final String UUID = "uuid";
        public static final String CALENDER_INTEGRATION = "calendarIntegration";

//        DEVICE TOKEN
        public static final String DEVICE_TOKEN = "device_token";

        //CONFIG RELATED
        public static final String HAS_DEFAULT_SET = "has_default_set";
        public static final String CALC_METHOD = "calc_method_for_%d";
        public static final String ASR_METHOD = "asr_method_for_%d";
        public static final String ADJUST_METHOD = "adjust_high_latitudes_method_for_%d";
        public static final String TIME_FORMAT = "time_format_for_%d";

        //LOCATION RELATED
        public static final String LAT_FOR = "lat_for_%d";
        public static final String LNG_FOR = "lng_for_%d";
        public static final String SHOW_ORIENATATION_INSTRACTIONS = "showOrientationInstructions";

        //APP RELATED
        public static final String IS_INIT = "app_init";
        public static final String APP_VERSION_CODE = "current_version_code";
        public static final String IS_TNC_ACCEPTED = "is_tnc_accepted";

        //    DISPLAY RELATED
        public static final String sHijriAdjust = "Hijri_Adjust";


        //     QURAN SETTINGS
        public static final String AL_QURAN_BROWSING_METHOD = "quran_browsing_method";
        public static final String AL_QURAN_BACKGROUND = "quran_background";
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

    }


    private AppSettings(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
        mContextRef = new WeakReference<>(context);
    }


    public static AppSettings getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new AppSettings(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static AppSettings getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        //Option 1:
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");

        //Option 2:
        // Alternatively, you can create a new instance here
        // with something like this:
        // getInstance(MyCustomApplication.getAppContext());
    }


    public String getUUID() {
        String uuid = mPref.getString(Key.UUID, null);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            set(getKeyFor(Key.UUID, 0), uuid);
        }
        return uuid;
    }

    public void set(String key, String val) {
        doEdit();
        mEditor.putString(key, val);
        doCommit();
    }

    public void set(String key, int val) {
        doEdit();
        mEditor.putInt(key, val);
        doCommit();
    }

    public void set(String key, boolean val) {
        doEdit();
        mEditor.putBoolean(key, val);
        doCommit();
    }

    public void set(String key, float val) {
        doEdit();
        mEditor.putFloat(key, val);
        doCommit();
    }

    public void set(String key, double val) {
        doEdit();
        mEditor.putString(key, String.valueOf(val));
        doCommit();
    }

    public void set(String key, long val) {
        doEdit();
        mEditor.putLong(key, val);
        doCommit();
    }

    public String getString(String key) {
        return mPref.getString(key, null);
    }

    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    public float getFloat(String key) {
        return mPref.getFloat(key, 0);
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return mPref.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return mPref.getFloat(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    public void remove(String... keys) {
        doEdit();
        for (String key : keys) {
            mEditor.remove(key);
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    public String getKeyFor(String key, int index) {
        return String.format(key, index);
    }

    public boolean isAlarmSetFor(int index) {
        return getBoolean(getKeyFor(Key.IS_ALARM_SET, index));
    }

    public void setAlarmFor(int index, boolean alarmOn) {
        set(getKeyFor(Key.IS_ALARM_SET, index), alarmOn);
    }

    //  SET AND GET CALCULATION METHODS
    public void setCalcMethodFor(int index, int value) {
        set(getKeyFor(Key.CALC_METHOD, index), value);
    }

    public void setAsrMethodFor(int index, int value) {
        set(getKeyFor(Key.ASR_METHOD, index), value);
    }

    public void setHighLatitudeAdjustmentMethodFor(int index, int value) {
        set(getKeyFor(Key.ADJUST_METHOD, index), value);
    }

    public void setTimeFormatFor(int index, int format) {
        set(getKeyFor(Key.TIME_FORMAT, index), format);
    }

    public int getCalcMethodSetFor(int index) {
        return getInt(getKeyFor(Key.CALC_METHOD, index), PrayTime.MWL);
    }

    public int getAsrMethodSetFor(int index) {
        return getInt(String.format(Key.ASR_METHOD, index), PrayTime.SHAFII);
    }

    public int getHighLatitudeAdjustmentFor(int index) {
        return getInt(getKeyFor(Key.ADJUST_METHOD, index), PrayTime.ONE_SEVENTH);
    }

    public int getTimeFormatFor(int index) {
        return getInt(getKeyFor(Key.TIME_FORMAT, index), DateFormat.is24HourFormat(mContextRef.get()) ? PrayTime.TIME_24 : PrayTime.TIME_12);
    }

    /*********************************************************/

//  SET AND GET DISPLAY SETTINGS VALUES
    public void setAdjustHijriDiff(int index, String dayDiff) {
        set(getKeyFor(Key.sHijriAdjust, index), dayDiff);
    }

    public String getAdjustHijriDiff(int index) {
        return getString(getKeyFor(Key.sHijriAdjust, index), "0");
    }

//  SET AND GET LOCATION VALUES

    public void setLatFor(int index, double lat) {
        set(getKeyFor(Key.LAT_FOR, index), lat);
    }

    public void setLngFor(int index, double lng) {
        set(getKeyFor(Key.LNG_FOR, index), lng);
    }

    public double getLatFor(int index) {
        return getDouble(getKeyFor(Key.LAT_FOR, index));
    }

    public double getLngFor(int index) {
        return getDouble(getKeyFor(Key.LNG_FOR, index));
    }

    /****************************************************/
    public boolean isDefaultSet() {
        return getBoolean(Key.HAS_DEFAULT_SET);
    }

    public String getCalendar() {
        return mPref.getString(Key.CALENDER_INTEGRATION, "-1");
    }

    public void setCalendar(int index, String cal) {
        set(getKeyFor(Key.CALENDER_INTEGRATION, index), cal);
    }


}