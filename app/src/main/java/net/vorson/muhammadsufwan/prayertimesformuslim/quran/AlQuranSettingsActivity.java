package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.customWidget.mtDialog.MaterialDialog;
import net.vorson.muhammadsufwan.prayertimesformuslim.customWidget.mtDialog.Theme;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;

import androidx.appcompat.app.AppCompatActivity;


public class AlQuranSettingsActivity extends AppCompatActivity {


    private LinearLayout readingMethodLayout;
    private AppSettings appSettings;
    private TextView readingMethodTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_al_quran_settings);
        readingMethodLayout = findViewById(R.id.readingSettingLayout);
        readingMethodTV = findViewById(R.id.readingStyleTV);
        appSettings = AppSettings.getInstance();
        if (appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD) != null && !appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD).equals("")) {
            readingMethodTV.setText(appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD));
            readingMethodTV.setAllCaps(true);
        } else {
            readingMethodTV.setText("Defualt (Scrolling)");
        }
        readingMethodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForReadingMethod();
            }
        });
    }

    private void openDialogForReadingMethod() {
        new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .title(R.string.select_reading_style)
                .items(R.array.itemsViewQuranOptions)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        switch (which) {
                            case 0:
                                appSettings.set(AppSettings.Key.AL_QURAN_BROWSING_METHOD, "scrolling");
                                readingMethodTV.setText(appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD));
                                readingMethodTV.setAllCaps(true);
                                break;
                            case 1:
                                appSettings.set(AppSettings.Key.AL_QURAN_BROWSING_METHOD, "paging");
                                readingMethodTV.setText(appSettings.getString(AppSettings.Key.AL_QURAN_BROWSING_METHOD));
                                readingMethodTV.setAllCaps(true);
                                break;
                        }
                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .show();

    }


}
