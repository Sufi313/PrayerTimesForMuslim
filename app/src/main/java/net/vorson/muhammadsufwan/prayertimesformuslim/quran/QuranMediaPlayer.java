package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.widget.ListView;

import net.vorson.muhammadsufwan.prayertimesformuslim.App;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.adapters.AlQuranDisplayListViewAdapterNew;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.data.DataManager;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordOfQuran;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class QuranMediaPlayer implements OnCompletionListener
{
    private Context activity;
    private AlQuranDisplayListViewAdapterNew alQuranDisplayListViewAdapter;
    private int ayahRepeatNumber;
    private Reciter currentReciter;
    private Integer fromTimeDuration;
    private boolean isPaused;
    private final List<List<Integer>> lines;
    private ListView listView;
    @Nullable
    private MediaPlayer mediaPlayer;
    public boolean pauseListInvalidate;
    private int prevStartRepeatingWordIndex;
    private QuranMediaPlayerListener quranMediaPlayerListener;
    private BroadcastReceiver receiver;
    private int repeatCounter;
    private final boolean repeatEnable;
    public boolean screenOff;
    private List<Integer>[] soundPositions;
    private final SurasAjzaaManager surasAjzaaManager;
    @NonNull
    private final SurasNames surasNames;
    private Thread thread;
    private Integer toTimeDuration;

    public QuranMediaPlayer(@NonNull final Activity activity, final int n, final AlQuranDisplayListViewAdapterNew alQuranDisplayListViewAdapter, final List<List<Integer>> lines, final ListView listView, final SurasAjzaaManager surasAjzaaManager, final boolean repeatEnable, final int ayahRepeatNumber) {
        this.repeatCounter = 0;
        this.prevStartRepeatingWordIndex = -1;
        this.activity = activity;
        this.alQuranDisplayListViewAdapter = alQuranDisplayListViewAdapter;
        this.lines = lines;
        this.listView = listView;
        this.surasAjzaaManager = surasAjzaaManager;
        this.repeatEnable = repeatEnable;
        this.ayahRepeatNumber = ayahRepeatNumber;
        StaticObjects.mediaService = new MediaService(activity.getApplicationContext());
        this.surasNames = new SurasNames();
        initailScreenEvent();
    }

    private int correctFirstLineIndex(int n) {
        while (n % alQuranDisplayListViewAdapter.getVisibleRowsCount() != 0) {
            --n;
        }
        return n;
    }

    private int getFromMediaPosFromWordIndex(final int n) {
        return soundPositions[0].get(soundPositions[2].indexOf(n));
    }

    private void initailScreenEvent() {
        final IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, @NonNull Intent intent) {
                if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                    Log.d("initailScreenEvent", "Intent.ACTION_SCREEN_OFF");
                    screenOff = true;
                } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                    Log.d("initailScreenEvent", "Intent.ACTION_SCREEN_ON");
                    screenOff = false;
                    pauseListInvalidate = false;
                    if (App.isSoundPlaying) {
                        invalidateListView();
                    }
                }
            }
        };
        activity.getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    private void invalidateListView() {
        ((AppCompatActivity) activity).runOnUiThread(new Runnable() {
            public void run() {
                if (App.currentWordIndexHilightForSoundPlay >= 0 && App.currentWordIndexHilightForSoundPlay < StaticObjects.contentWords.size() && !pauseListInvalidate) {
                    alQuranDisplayListViewAdapter.notifyDataSetChanged();
                    int lineIndexOfWord = ( StaticObjects.contentWords.get(App.currentWordIndexHilightForSoundPlay)).getLineIndexOfWord();
                    if (listView.getLastVisiblePosition() <= lineIndexOfWord) {
                        int lastVisiblePosition = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition();
                        if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
                           listView.smoothScrollToPositionFromTop(listView.getFirstVisiblePosition() + lastVisiblePosition, 0, 1000);
                        } else {
                            quranMediaPlayerListener.onGotoPage(lineIndexOfWord / alQuranDisplayListViewAdapter.getNumberOfRowsInt());
                           quranMediaPlayerListener.onPageInvalidated();
                        }
                    }
                    if (listView.getFirstVisiblePosition() > lineIndexOfWord) {
                        if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
                            listView.smoothScrollToPositionFromTop(lineIndexOfWord, 0, 1000);
                        } else if (alQuranDisplayListViewAdapter.getNumberOfRowsInt() > 0) {
                            quranMediaPlayerListener.onGotoPage(lineIndexOfWord / alQuranDisplayListViewAdapter.getNumberOfRowsInt());
                            quranMediaPlayerListener.onPageInvalidated();
                        }
                    }
                }
            }
        });
    }

    private boolean loadAudioFile() {
        final String mp3Path = currentReciter.getMp3Path(App.currentSuraId);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mp3Path);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            loadSoundPositions();
            return true;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        catch (IllegalStateException ex2) {
            ex2.printStackTrace();
            return false;
        }
        catch (IllegalArgumentException ex3) {
            ex3.printStackTrace();
            return false;
        }
    }

    private void loadSoundPositions() {
        soundPositions = (List<Integer>[])new DataManager(activity).readTextFileToListOfListNew1(new File(currentReciter.getDurFilePath(App.currentSuraId)), surasAjzaaManager.getSuraGenaricWordIndexMap().get(App.currentSuraId));
    }

    private void returnToPrevoisAyahMarkMediaPosition(final int n) {
        int n2 = 1;
        while (true) {
            for (int i = n - n2; i >= 0; --i) {
                if (((WordOfQuran)StaticObjects.contentWords.get(i)).getWordString().contains("2200")) {
                    mediaPlayer.seekTo(getFromMediaPosFromWordIndex(i));
                    mediaPlayer.start();
                    if (n2 == 0) {
                        mediaPlayer.seekTo(getFromMediaPosFromWordIndex(0));
                        mediaPlayer.start();
                    }
                    return;
                }
            }
            n2 = 0;
            continue;
        }
    }

    @Nullable
    public MediaPlayer getMediaPlayerObj() {
        return mediaPlayer;
    }

    public int getSuraId() {
        return App.currentSuraId;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void next() {
        if (quranMediaPlayerListener != null) {
            quranMediaPlayerListener.doNext();
        }
    }

    public void onCompletion(final MediaPlayer mediaPlayer) {
        if (repeatEnable && repeatCounter < ayahRepeatNumber) {
            return;
        }
    }

    public void pausePlayer() {
        if (mediaPlayer != null && thread != null) {
            mediaPlayer.pause();
            thread.interrupt();
            App.isSoundPlaying = false;
            StaticObjects.mediaService.newNotification("com.isysway.free.alquran.play", surasNames.getSuraNames(App.currentSuraId - 1));
            isPaused = true;
        }
    }

    public int playSound(final int currentWordIndexHilightForSoundPlay) {
        pauseListInvalidate = false;
        final AudioSettingsManager audioSettingsManager = new AudioSettingsManager(activity);
        currentReciter = audioSettingsManager.getCurrentReciter();
        if (currentReciter == null || !audioSettingsManager.isSoundFilesExists(currentReciter.getReciterId(), App.currentSuraId)) {
            App.haveToPlayReciter = false;
            return -1;
        }
        if (!loadAudioFile()) {
            App.haveToPlayReciter = false;
            return -1;
        }
        if (StaticObjects.contentWords.get(currentWordIndexHilightForSoundPlay).getWordIndexOfSura() >= 0) {
            fromTimeDuration = soundPositions[0].get(soundPositions[2].indexOf(currentWordIndexHilightForSoundPlay));
        }
        else if (StaticObjects.contentWords.get(currentWordIndexHilightForSoundPlay + 1).getWordIndexOfSura() > 1) {
            fromTimeDuration = soundPositions[0].get(soundPositions[2].indexOf(currentWordIndexHilightForSoundPlay));
        }
        else {
            fromTimeDuration = 0;
        }
        toTimeDuration = -1;
        App.isSoundPlaying = true;
        App.currentWordIndexHilightForSoundPlay = currentWordIndexHilightForSoundPlay;
        if (App.currentWordIndexHilightForSoundPlay <= -1) {
            App.currentWordIndexHilightForSoundPlay = 0;
        }
        mediaPlayer.seekTo((int)fromTimeDuration);
        mediaPlayer.start();
        App.isSoundPlaying = true;
        StaticObjects.mediaService.newNotification("com.isysway.free.alquran.pause", surasNames.getSuraNames(App.currentSuraId - 1));
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        thread = new Thread(new Runnable() {
            public void run() {
                testprogressNew();
            }
        });
        thread.start();
        return 0;
    }

    public void prev() {
        if (quranMediaPlayerListener != null) {
            quranMediaPlayerListener.doPrev();
        }
    }

    public void resumePlayer() {
        isPaused = false;
        mediaPlayer.start();
        thread = new Thread(new Runnable() {
            public void run() {
                testprogressNew();
            }
        });
        thread.start();
        App.isSoundPlaying = true;
        StaticObjects.mediaService.newNotification(MediaService.NOTIFY_PAUSE, surasNames.getSuraNames(App.currentSuraId - 1));
    }

    public void setNull() {
        mediaPlayer = null;
        if (receiver != null) {
            try {
                activity.getApplicationContext().unregisterReceiver(receiver);
            }
            catch (IllegalArgumentException ex) {}
        }
    }

    public void setSuraId(final int n) {
    }

    public void setquranMediaPlayerListener(final QuranMediaPlayerListener quranMediaPlayerListener) {
        this.quranMediaPlayerListener = quranMediaPlayerListener;
    }

    public void stopPlayer() {
        if (quranMediaPlayerListener == null) {
            return;
        }
        if (thread == null) {
            return;
        }
        try {
            mediaPlayer.pause();
            StaticObjects.mediaService.removeNotification();
        }
        catch (Exception ex) {}
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        App.isSoundPlaying = false;
    }

    public void testprogress() {
        while (thread.isInterrupted()) {
            final boolean screenOff = this.screenOff;
            if (listView.getLastVisiblePosition() == -1 || soundPositions == null) {
                return;
            }
            if (quranMediaPlayerListener == null) {
                return;
            }
            if (App.currentWordIndexHilightForSoundPlay >= -1 + StaticObjects.contentWords.size() && (!repeatEnable || repeatCounter >= ayahRepeatNumber)) {
                stopPlayer();
                quranMediaPlayerListener.onSoundPlayingComplete();
                return;
            }
            int n = 1 + Collections.binarySearch(soundPositions[0], mediaPlayer.getCurrentPosition());
            if (n >= 0) {
                if (n >= soundPositions[2].size()) {
                    n = -1 + soundPositions[2].size();
                }
                App.currentWordIndexHilightForSoundPlay = soundPositions[2].get(n);
            }
            else {
                final int n2 = -1 + Math.abs(n);
                if (n2 < soundPositions[2].size() && n2 >= 0) {
                    App.currentWordIndexHilightForSoundPlay = soundPositions[2].get(n2);
                }
            }
            if (repeatEnable) {
                final int currentWordIndexHilightForSoundPlay = App.currentWordIndexHilightForSoundPlay;
                if (currentWordIndexHilightForSoundPlay >= 0 && ((WordOfQuran)StaticObjects.contentWords.get(currentWordIndexHilightForSoundPlay)).getWordString().contains("2200")) {
                    if (prevStartRepeatingWordIndex == -1) {
                        prevStartRepeatingWordIndex = currentWordIndexHilightForSoundPlay;
                    }
                    if (prevStartRepeatingWordIndex == currentWordIndexHilightForSoundPlay && repeatCounter < ayahRepeatNumber) {
                        returnToPrevoisAyahMarkMediaPosition(currentWordIndexHilightForSoundPlay);
                        ++repeatCounter;
                    }
                    else if (prevStartRepeatingWordIndex < currentWordIndexHilightForSoundPlay) {
                        prevStartRepeatingWordIndex = currentWordIndexHilightForSoundPlay;
                        repeatCounter = 0;
                    }
                }
            }
            invalidateListView();
        }
    }

    public void testprogressNew() {
        if (soundPositions != null) {
            Integer valueOf = mediaPlayer.getCurrentPosition();
            int binarySearch = Collections.binarySearch(soundPositions[0], valueOf) + 1;
            if (binarySearch < 0) {
                binarySearch = Math.abs(binarySearch) - 1;
            } else if (binarySearch >= soundPositions[2].size()) {
                binarySearch = soundPositions[2].size() - 1;
            }
            try {
                if (soundPositions.length > 0 && soundPositions[0].size() > binarySearch) {
                    Thread.sleep(soundPositions[0].get(binarySearch) - valueOf >= 0 ? (long) ((Integer) soundPositions[0].get(binarySearch) - valueOf) : 0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = binarySearch;
            while (App.isSoundPlaying) {
                long currentTimeMillis = System.currentTimeMillis();
                if (repeatEnable && (StaticObjects.contentWords.get(soundPositions[2].get(binarySearch))).getWordString().contains("2200")) {
                    if (repeatCounter < ayahRepeatNumber) {
                        mediaPlayer.seekTo(soundPositions[0].get(i));
                        mediaPlayer.start();
                        repeatCounter++;
                        binarySearch = i;
                    } else {
                        repeatCounter = 0;
                        i = binarySearch;
                    }
                }
                if (binarySearch < soundPositions[2].size() && binarySearch >= 0) {
                    App.currentWordIndexHilightForSoundPlay = soundPositions[2].get(binarySearch);
                }
                if (!screenOff) {
                    invalidateListView();
                }
                int i2 = binarySearch + 1;
                if (i2 >= soundPositions[0].size() || App.currentWordIndexHilightForSoundPlay > StaticObjects.contentWords.size() - 1) {
                    Log.d("QuranMediaPlayer", "End Of Playing File");
                    if (App.currentWordIndexHilightForSoundPlay >= StaticObjects.contentWords.size() - 1) {
                        Log.d("QuranMediaPlayer", "This is end of sura or jozaa or hezb playing");
                        quranMediaPlayerListener.onSoundPlayingComplete();
                        thread.isInterrupted();
                        return;
                    }
                    Log.d("QuranMediaPlayer", "This is end of sura file but The section not ended yet, we will play next sura sound");
                    for (binarySearch = App.currentWordIndexHilightForSoundPlay; binarySearch < StaticObjects.contentWords.size(); binarySearch++) {
                        if (App.currentSuraId != ((WordOfQuran) StaticObjects.contentWords.get(binarySearch)).getSuraId()) {
                            App.currentSuraId = ((WordOfQuran) StaticObjects.contentWords.get(binarySearch)).getSuraId();
                            App.currentWordIndexHilightForSoundPlay = binarySearch;
                            playSound(App.currentWordIndexHilightForSoundPlay);
                            return;
                        }
                    }
                    binarySearch = 0;
                } else {
                    binarySearch = soundPositions[0].get(i2) - soundPositions[0].get(binarySearch);
                    if (binarySearch <= 0) {
                        try {
                            throw new InvalidTimeSleepException("sleepTime<=0");
                        } catch (InvalidTimeSleepException e2) {
                            e2.printStackTrace();
                            testprogressNew();
                            return;
                        }
                    }
                }
                Log.d("QuranMediaPlayer", "tick");
                long j = (long) binarySearch;
                if (j - (System.currentTimeMillis() - currentTimeMillis) > 0) {
                    try {
                        Thread.sleep(j - (System.currentTimeMillis() - currentTimeMillis));
                    } catch (InterruptedException e3) {
                        e3.printStackTrace();
                    }
                }
                binarySearch = i2;
            }
        }
    }

    class InvalidTimeSleepException extends Exception {
        public InvalidTimeSleepException(String str) {
            super(str);
        }
    }

}