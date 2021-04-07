package com.sufi.prayertimes.quran;



import com.sufi.prayertimes.quran.models.SearchResultObj;
import com.sufi.prayertimes.quran.models.WordOfQuran;

import java.util.List;

import io.reactivex.annotations.Nullable;

public class StaticObjects {
    @Nullable
    public static List<WordOfQuran> contentWords;
    public static MediaService mediaService;
    @Nullable
    public static QuranMediaPlayer quranMediaPlayer;
    public static SearchResultObj searchResultObj;
}
