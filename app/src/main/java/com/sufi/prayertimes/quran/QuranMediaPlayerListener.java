package com.sufi.prayertimes.quran;

public interface QuranMediaPlayerListener {
    void doNext();

    void doPrev();

    void onGotoPage(int i);

    void onNextPagePlaying();

    void onPageInvalidated();

    void onPrevPagePlaying();

    void onSoundFilePlayingComplete();

    void onSoundPlayingComplete();
}
