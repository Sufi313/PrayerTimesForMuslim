package com.sufi.prayertimes.quran.models;

import io.reactivex.annotations.NonNull;

public class JozaInfo {
    private boolean enabled = true;
    private String fontName;
    private int jozaId;
    private String jozaTitle;
    private boolean selected;
    private String unicodeJozaNumber;

    public JozaInfo(int i, String str, String str2, String str3) {
        this.jozaTitle = str;
        this.jozaId = i;
        this.fontName = str2;
        this.unicodeJozaNumber = str3;
    }

    public JozaInfo(int i) {
        this.jozaId = i;
    }

    public String getJozaTitle() {
        return this.jozaTitle;
    }

    public int getJozaId() {
        return this.jozaId;
    }

    @NonNull
    public String getThreeCharsJozaId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.jozaId);
        stringBuilder.append("");
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(new String(new char[(3 - stringBuilder2.length())]).replace("\u0000", "0"));
        stringBuilder3.append(stringBuilder2);
        return stringBuilder3.toString();
    }

    public String getFontName() {
        return this.fontName;
    }

    public String getUnicodeJozaNumber() {
        return this.unicodeJozaNumber;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public boolean getEnabled() {
        return this.enabled;
    }
}
