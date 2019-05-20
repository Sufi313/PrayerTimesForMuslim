package net.vorson.muhammadsufwan.prayertimesformuslim.quran.models;


import net.vorson.muhammadsufwan.prayertimesformuslim.quran.SurasNames;

import io.reactivex.annotations.NonNull;

public class SuraInfo {
    private int suraId;
    private String suraTitle;
    private String fontName;
    private String unicodeSuraNumber;
    private boolean enabled = true;
    private boolean selected;

    public SuraInfo(int suraId, String suraTitle, String fontName, String unicodeSuraNumber) {
        this.suraId = suraId;
        this.suraTitle = suraTitle;
        this.fontName = fontName;
        this.unicodeSuraNumber = unicodeSuraNumber;
    }

    public SuraInfo(int i) {
        suraId = i;
    }

    public String getSuraTitle() {
        return suraTitle;
    }

    public int getSuraId() {
        return suraId;
    }

    @NonNull
    public String getThreeCharsSuraId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(suraId);
        stringBuilder.append("");
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(new String(new char[(3 - stringBuilder2.length())]).replace("\u0000", "0"));
        stringBuilder3.append(stringBuilder2);
        return stringBuilder3.toString();
    }

    public String getFontName() {
        return fontName;
    }

    public String getUnicodeSuraNumber() {
        return unicodeSuraNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean z) {
        selected = z;
    }

    public void setEnabled(boolean z) {
        enabled = z;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public int getNumOfAyat() {
        return SurasNames.surasAyahs[getSuraId() - 1];
    }

    public int getSuraKind() {
        return SurasNames.suraKinds[getSuraId() - 1];
    }

    public String getSuraNameEn() {
        return SurasNames.suraNamesEn[suraId - 1];
    }

    public String getSuraNameAr() {
        return SurasNames.suraNamesAr[suraId - 1];
    }
}
