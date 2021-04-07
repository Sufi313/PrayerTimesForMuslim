package com.sufi.prayertimes.quran.data;

import java.util.zip.ZipFile;

public class TafsirTranslationObj {
    private String id;
    private boolean isEnabled;
    private boolean isSelected;
    private String langauge;
    private String link;
    private String title;
    private String translator;
    private ZipFile zipFile;

    public TafsirTranslationObj(String str, String str2, String str3, String str4, ZipFile zipFile) {
        this.id = str;
        this.title = str2;
        this.langauge = str3;
        this.translator = str4;
        this.zipFile = zipFile;
    }

    public TafsirTranslationObj(String str, String str2, String str3, String str4, String str5) {
        this.id = str;
        this.title = str2;
        this.langauge = str3;
        this.translator = str4;
        this.link = str5;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLangauge() {
        return this.langauge;
    }

    public String getTranslator() {
        return this.translator;
    }

    public ZipFile getZipFile() {
        return this.zipFile;
    }

    public void setIsSelected(boolean z) {
        this.isSelected = z;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public boolean getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(boolean z) {
        this.isEnabled = z;
    }

    public String getLink() {
        return this.link;
    }
}
