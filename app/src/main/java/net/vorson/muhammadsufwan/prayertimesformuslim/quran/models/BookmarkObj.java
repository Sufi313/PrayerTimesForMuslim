package net.vorson.muhammadsufwan.prayertimesformuslim.quran.models;

public class BookmarkObj {
    private int ayaNum;
    private int startWordIndex;
    private int suraNum;

    public BookmarkObj(int i, int i2, int i3) {
        this.ayaNum = i;
        this.suraNum = i2;
        this.startWordIndex = i3;
    }

    public int getAyaNum() {
        return this.ayaNum;
    }

    public int getSuraNum() {
        return this.suraNum;
    }

    public int getStartWordIndex() {
        return this.startWordIndex;
    }
}
