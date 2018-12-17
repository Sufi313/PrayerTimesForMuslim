package net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.Constants;
import net.vorson.muhammadsufwan.prayertimesformuslim.util.AlarmUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements Constants,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    int mIndex = 0;
    AppSettings settings;
    private TextView setRingtoneTextView;
    private LinearLayout mRingtone;

    Map<String, Uri> mRingtonesMap;
    private Uri mLastSelectedRingtone = null;
    private String mLastSelectedRingtoneName = "Default";
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setRingtoneTextView = findViewById(R.id.ringtoneSelectedTV);
        mRingtone = findViewById(R.id.ringtoneSelectedLayout);

        settings = AppSettings.getInstance(this);
        setupRingtoneSelection();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void setupRingtoneSelection() {
        mRingtonesMap = AlarmUtils.getRingtones(SettingsActivity.this);

        setRingtoneTextView.setText(getString(R.string.set_alarm_select_ringtone,
                settings.getString(AppSettings.Key.SELECTED_RINGTONE_NAME, mLastSelectedRingtoneName)));

        mRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> ringtoneKeys = new ArrayList<>(mRingtonesMap.keySet());
                String ringtoneUri = settings.getString(AppSettings.Key.SELECTED_RINGTONE, "");
                int selected = -1;
                for (int i = 0; i < ringtoneKeys.size(); i++) {
                    String ring = ringtoneKeys.get(i);
                    if (mRingtonesMap.get(ring).toString().equalsIgnoreCase(ringtoneUri)) {
                        selected = i;
                        break;
                    }
                }

                AlarmUtils.getRingtonesDialog(SettingsActivity.this, ringtoneKeys, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //item selected
                        String name = ringtoneKeys.get(which);
                        mLastSelectedRingtoneName = name;
                        mLastSelectedRingtone = mRingtonesMap.get(name);

                        try {
                            if (mMediaPlayer == null) {
                                mMediaPlayer = new MediaPlayer();
                            } else {
                                mMediaPlayer.stop();
                                mMediaPlayer.reset();
                            }
                            mMediaPlayer.setDataSource(SettingsActivity.this, mLastSelectedRingtone);
                            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mMediaPlayer.setLooping(false);
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                        } catch (IOException ioe) {
                            //do nothing
                        }

                    }
                }, new DialogInterface.OnClickListener() { //ok
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mMediaPlayer != null) {
                            mMediaPlayer.stop();
                            mMediaPlayer.release();
                            mMediaPlayer = null;
                        }
                        settings.set(AppSettings.Key.SELECTED_RINGTONE, mLastSelectedRingtone.toString());
                        settings.set(AppSettings.Key.SELECTED_RINGTONE_NAME, mLastSelectedRingtoneName);
                        //Update the textview
                        setRingtoneTextView.setText(getString(R.string.set_alarm_select_ringtone,
                                settings.getString(AppSettings.Key.SELECTED_RINGTONE_NAME, mLastSelectedRingtoneName)));
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() { //cancel
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mMediaPlayer != null) {
                            mMediaPlayer.stop();
                            mMediaPlayer.release();
                            mMediaPlayer = null;
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}
