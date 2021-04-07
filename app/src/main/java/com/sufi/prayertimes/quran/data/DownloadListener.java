package com.sufi.prayertimes.quran.data;

public interface DownloadListener {
    void error();

    void onComplete();

    long onPacketDownloaded(long j);

    void onProgress(int i);
}
