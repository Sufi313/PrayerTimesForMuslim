package net.vorson.muhammadsufwan.prayertimesformuslim.quran.models;

public class WordOfQuran {
   private int ayahNumber;
   private int lineIndexOfWord;
   private String planTextForMeaning;
   private int suraId;
   private int wordIndexOfSura;
   private int wordIndexOfWrapper;
   private String wordMeaning;
   private String wordString;
   private float wordWidthInPixel;

    public WordOfQuran(int lineIndexOfWord, int wordIndexOfSura, int wordIndexOfWrapper, String wordString, String wordMeaning, int suraId, float wordWidthInPixel, int ayahNumber) {
        this.lineIndexOfWord = lineIndexOfWord;
        this.wordIndexOfSura = wordIndexOfSura;
        this.wordIndexOfWrapper = wordIndexOfWrapper;
        this.wordString = wordString;
        this.wordMeaning = wordMeaning;
        this.suraId = suraId;
        this.wordWidthInPixel = wordWidthInPixel;
        this.ayahNumber = ayahNumber;
    }

    public int getLineIndexOfWord() {
        return this.lineIndexOfWord;
    }

    public void setLineIndexOfWord(int i) {
        this.lineIndexOfWord = i;
    }

    public int getWordIndexOfSura() {
        return this.wordIndexOfSura;
    }

    public void setWordIndexOfSura(int i) {
        this.wordIndexOfSura = i;
    }

    public int getWordIndexOfWrapper() {
        return this.wordIndexOfWrapper;
    }

    public void setWordIndexOfWrapper(int i) {
        this.wordIndexOfWrapper = i;
    }

    public String getWordMeaning() {
        return this.wordMeaning;
    }

    public void setWordMeaning(String str) {
        this.wordMeaning = str;
    }

    public int getSuraId() {
        return this.suraId;
    }

    public void setSuraId(int i) {
        this.suraId = i;
    }

    public String getWordString() {
        return this.wordString;
    }

    public void setWordString(String str) {
        this.wordString = str;
    }

    public float getWordWidthInPixel() {
        return this.wordWidthInPixel;
    }

    public void setWordWidthInPixel(float f) {
        this.wordWidthInPixel = f;
    }

    public int getAyahNumber() {
        return this.ayahNumber;
    }

    public void setAyahNumber(int i) {
        this.ayahNumber = i;
    }

    public String getPlanTextForMeaning() {
        return this.planTextForMeaning;
    }

    public void setPlanTextForMeaning(String str) {
        this.planTextForMeaning = str;
    }
}
