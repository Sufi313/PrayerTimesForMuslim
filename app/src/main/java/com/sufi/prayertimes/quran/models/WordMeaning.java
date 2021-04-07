package com.sufi.prayertimes.quran.models;

public class WordMeaning {
    int ayaNum;
    int id;
    private int linenumber;
    String meaning;
    int suraNum;
    String word;
    private int wotdIndex;
    String wotdIndexes;

    public WordMeaning(int i, int i2, int i3, String str, String str2, String str3) {
        this.id = i;
        this.ayaNum = i2;
        this.suraNum = i3;
        this.wotdIndexes = str;
        this.word = str2;
        this.meaning = str3;
    }

    public WordMeaning(int i, int i2, int i3, int i4, String str, String str2) {
        this.id = i;
        this.ayaNum = i2;
        this.suraNum = i3;
        this.wotdIndex = i4;
        this.word = str;
        this.meaning = str2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getAyaNum() {
        return this.ayaNum;
    }

    public void setAyaNum(int i) {
        this.ayaNum = i;
    }

    public int getSuraNum() {
        return this.suraNum;
    }

    public void setSuraNum(int i) {
        this.suraNum = i;
    }

    public String get_wotdIndexes() {
        return this.wotdIndexes;
    }

    public void set_wotdIndexes(String str) {
        this.wotdIndexes = str;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String str) {
        this.word = str;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public void setMeaning(String str) {
        this.meaning = str;
    }

    public int getWotdIndex() {
        return this.wotdIndex;
    }

    public void setWotdIndex(int i) {
        this.wotdIndex = i;
    }

    public int getLinenumber() {
        return this.linenumber;
    }

    public void setLinenumber(int i) {
        this.linenumber = i;
    }
}
