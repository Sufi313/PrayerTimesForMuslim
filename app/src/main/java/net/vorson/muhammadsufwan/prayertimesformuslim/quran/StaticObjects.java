package net.vorson.muhammadsufwan.prayertimesformuslim.quran;



import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.SearchResultObj;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordOfQuran;

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
