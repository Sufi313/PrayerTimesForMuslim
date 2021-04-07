package com.sufi.prayertimes.quran.interfaces;

public interface IDownloadTafsirManagerListener {
    void onCalculateFileLengthesFinished(int i);

    void onCurrentDownloadProgress(int i);

    void onTotalDownloadCompleted();

    long onTotalDownloadPacket(long j);

    void onTotalDownloadProgress(int i);
}
