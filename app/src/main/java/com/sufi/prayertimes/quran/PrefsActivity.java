package com.sufi.prayertimes.quran;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sufi.prayertimes.App;

import com.sufi.prayertimes.R;
import com.sufi.prayertimes.quran.data.TafsirTranslationObj;
import com.sufi.prayertimes.quran.models.StoragePath;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.reactivex.annotations.Nullable;


public class PrefsActivity extends AppCompatActivity {
    private static final String TAG = PrefsActivity.class.getSimpleName();
    private static int prefs = 2131820544;
    private PF pf;
    @Nullable
    private Thread thread;

    public static class PF extends PreferenceFragment {
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            addPreferencesFromResource(PrefsActivity.prefs);
            initialComboEntries();
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return super.onCreateView(layoutInflater, viewGroup, bundle);
        }

        private void setAkkTextViewForeColor(ViewGroup viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof TextView) {
                    ((TextView) childAt).setTextColor(App.getCurrentThemeColors()[0]);
                }
            }
        }

        public void initialComboEntries() {
            SettingsManager settingsManager = new SettingsManager(getActivity());
            StoragePath storagePath = new StoragePath(getActivity());
            ListPreference listPreference = (ListPreference) findPreference("AUDIO_FILES_PATH");
            CharSequence[] arrayOfDisplayName = App.getArrayOfDisplayName();
            CharSequence[] arrayOfPaths = App.getArrayOfPaths();
            listPreference.setEntries(arrayOfDisplayName);
            listPreference.setEntryValues(arrayOfPaths);
            Vector loadAllReciters = new AudioSettingsManager(getActivity()).loadAllReciters();
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            String language = Locale.getDefault().getLanguage();
            for (int i = 0; i < loadAllReciters.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((Reciter) loadAllReciters.get(i)).getReciterId());
                stringBuilder.append("");
                arrayList2.add(stringBuilder.toString());
                if (language.equalsIgnoreCase("ar")) {
                    arrayList.add(((Reciter) loadAllReciters.get(i)).getReciterArName());
                } else {
                    arrayList.add(((Reciter) loadAllReciters.get(i)).getReciterEnName());
                }
            }
            listPreference = (ListPreference) findPreference("DEFAULT_RECIETER");
            listPreference.setEntries((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]));
            listPreference.setEntryValues((CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]));
            listPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    return false;
                }
            });
            List allTafsir = new TafsirTranslationManager(getActivity()).getAllTafsir();
            arrayList = new ArrayList();
            arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < allTafsir.size(); i2++) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(((TafsirTranslationObj) allTafsir.get(i2)).getId());
                stringBuilder2.append("");
                arrayList2.add(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(((TafsirTranslationObj) allTafsir.get(i2)).getLangauge());
                stringBuilder2.append(" - ");
                stringBuilder2.append(((TafsirTranslationObj) allTafsir.get(i2)).getTitle());
                arrayList.add(stringBuilder2.toString());
            }
            ListPreference listPreference2 = (ListPreference) findPreference("DEFAULT_TAFSIR");
            listPreference2.setEntries((CharSequence[]) arrayList.toArray(new CharSequence[allTafsir.size()]));
            listPreference2.setEntryValues((CharSequence[]) arrayList2.toArray(new CharSequence[allTafsir.size()]));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pref_with_toolbar);
        Toolbar toolbar =  findViewById(R.id.prefActivityToolBar);
//        App.applyMyTheme(this, toolbar);
        toolbar.setTitle(R.string.settings);
        setSupportActionBar(toolbar);
        AddResourceApi11AndGreater();
        try {
            getClass().getMethod("getFragmentManager", new Class[0]);
            AddResourceApi11AndGreater();
        } catch (NoSuchMethodException unused) {
            setTitle(R.string.settings);
        }
    }

    private void checkNewReciter() {
        this.thread = new Thread(new Runnable() {
            public void run() {
                    if (new AudioSettingsManager(PrefsActivity.this).downloadAllRecitersProfiles()) {
                        PrefsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Builder builder = new Builder(PrefsActivity.this);
                                builder.setTitle(R.string.alert);
                                builder.setMessage(R.string.new_reciter_alert);
                                builder.setPositiveButton(R.string.ok, new OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = PrefsActivity.this.getIntent();
                                        PrefsActivity.this.finish();
                                        PrefsActivity.this.startActivityForResult(intent, 7);
                                    }
                                });
                                AlertDialog create = builder.create();
                                create.show();
                                Button button = create.getButton(-2);
                                if (button != null) {
                                    button.setTextColor(App.getCurrentThemeColors()[0]);
                                }
                                Button button2 = create.getButton(-1);
                                if (button2 != null) {
                                    button2.setTextColor(App.getCurrentThemeColors()[0]);
                                }
                            }
                        });
                    }

            }
        });
    }

    @TargetApi(11)
    protected void AddResourceApi11AndGreater() {
        pf = new PF();
//        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, pf).commit();
        Log.d(TAG, "AddResourceApi11AndGreater");
    }

    protected void onStop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        super.onStop();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onRestart() {
        super.onRestart();
    }

    public void onActivityReenter(int i, Intent intent) {
        super.onActivityReenter(i, intent);
    }

    protected void onPostResume() {
        super.onPostResume();
    }

    protected void onResumeFragments() {
        super.onResumeFragments();
    }
}
