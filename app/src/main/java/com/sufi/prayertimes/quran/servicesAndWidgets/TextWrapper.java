package com.sufi.prayertimes.quran.servicesAndWidgets;

import android.util.Log;

import com.sufi.prayertimes.quran.FontManager;
import com.sufi.prayertimes.quran.StaticObjects;
import com.sufi.prayertimes.quran.interfaces.OnTextWrapcomplete;
import com.sufi.prayertimes.quran.models.WordOfQuran;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class TextWrapper {
    private static final String TAG = TextWrapper.class.getSimpleName();
    public static final int WRAP_AYAT = 1;
    public static final int WRAP_NORMAL = 0;
    private Vector<Integer> ayaLineMap;
    private int currentSuraId;
    private FontManager fontManager;
    private List<String> lines;
    private List<List<Integer>> linesOfIndexsOfWords;
    private Vector<Float> linesWidths;
    private OnTextWrapcomplete onTextWrapcomplete;
    private int pageHeight;
    private int pageWidth;
    private int requiredPage;
    private int requiredWordIndex;
    private Vector<Integer> wordIndexesOfLines;
    private Vector<Integer> wordIndexesOfPages;
    private Vector<Float> wordWidthsArr;
    private boolean wrapCompleted;

    public void wrapNow(@Nullable final String s, final int n, final int pageHeight, final int currentSuraId, final int n2) {
        int pageWidth = n;
        String s2;
        if (s == null) {
            s2 = "";
        } else {
            s2 = s;
        }
        this.wrapCompleted = false;
        this.pageHeight = pageHeight;
        this.pageWidth = pageWidth;
        this.currentSuraId = currentSuraId;
        this.linesWidths = new Vector<>();
        this.wordIndexesOfPages = new Vector<>();
        this.wordIndexesOfLines = new Vector<>();
        this.wordIndexesOfPages.add(0);
        this.lines = new Vector<>();
        this.ayaLineMap = new Vector<>();
        this.wordWidthsArr = new Vector<>();
        final String[] split = s2.split(" ");
        final int linesPerPage = this.getLinesPerPage();
        float spaceWidth = this.fontManager.getSpaceWidth();
        if (currentSuraId != 1 && currentSuraId != 9) {
            this.lines.add("3102");
            this.wordIndexesOfLines.add(0);
            this.linesWidths.add(0.0f);
        }
        if (currentSuraId == 1) {
            this.lines.add("3101");
            this.wordIndexesOfLines.add(0);
            this.ayaLineMap.add(0);
            this.linesWidths.add(0.0f);
        }
        String s3 = "0\t1\t1\n";
        int i = 0;
        int n3 = 0;
        boolean b = true;
        int n4 = 0;
        int n5 = 0;
        String string = "";
        float n6 = 0.0f;
        while (i < split.length) {
            final String s4 = split[i];
            final float wordWidth = this.fontManager.getWordWidth(s4);
            float n7 = n6 + spaceWidth;
            float n9;
            int n10;
            if (n7 + wordWidth <= pageWidth && !s4.equals("3102") && !s4.equals("147,5116,1141,5116,135,533,2228,5117,2111,4215") && (!s4.endsWith(",2199") || n2 != 1)) {
                if (string != "") {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(string);
                    sb.append(" ");
                    string = sb.toString();
                    ++n4;
                } else {
                    n7 = n6;
                }
                n4 += s4.length();
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(string);
                sb2.append(s4);
                final String string2 = sb2.toString();
                final float n8 = n7 + wordWidth;
                this.wordWidthsArr.add(wordWidth);
                string = string2;
                n9 = spaceWidth;
                n6 = n8;
                n10 = 1;
            } else {
                lines.add(string);
                final Vector<Integer> wordIndexesOfLines = this.wordIndexesOfLines;
                n9 = spaceWidth;
                wordIndexesOfLines.add(i);
                if (lines.size() == 1 + (linesPerPage + linesPerPage * (-1 + requiredPage))) {
                    this.onTextWrapcomplete.onRequiredPageReady();
                    b = false;
                }
                this.linesWidths.add(n6);
                final int ayaNumberOfLine = this.getAyaNumberOfLine(n3, string);
                this.ayaLineMap.add(ayaNumberOfLine);
                String string3 = "";
                float n12 = 0.0f;
                Label_0770:
                {
                    if (!s4.equals("3102") && !s4.equals("147,5116,1141,5116,135,533,2228,5117,2111,4215")) {
                        --i;
                    } else {
                        if (s4.equals("147,5116,1141,5116,135,533,2228,5117,2111,4215")) {
                            n4 += s4.length();
                            final StringBuilder sb3 = new StringBuilder();
                            sb3.append(string3);
                            sb3.append(s4);
                            string3 = sb3.toString();
                            final float n11 = wordWidth + 0.0f;
                            this.wordWidthsArr.add(wordWidth);
                            n12 = n11;
                            break Label_0770;
                        }
                        this.lines.add("3102");
                        this.wordIndexesOfLines.add(i);
                        this.linesWidths.add(this.fontManager.getWordWidth("3102"));
                        this.ayaLineMap.add(ayaNumberOfLine);
                    }
                    n12 = 0.0f;
                }
                if (this.lines.size() % linesPerPage == 0) {
                    this.wordIndexesOfPages.add(i + 2);
                    final StringBuilder sb4 = new StringBuilder();
                    sb4.append(s3);
                    sb4.append(n4);
                    sb4.append("\t");
                    sb4.append(i);
                    sb4.append("\t");
                    sb4.append(ayaNumberOfLine);
                    sb4.append("\n");
                    final String string4 = sb4.toString();
                    if (n5 == 0 && -2 + wordIndexesOfPages.size() >= 0 && wordIndexesOfPages.get(-2 + wordIndexesOfPages.size()) > requiredWordIndex) {
                        onTextWrapcomplete.onRequiredPageReady();
                        n3 = ayaNumberOfLine;
                        s3 = string4;
                        n10 = 1;
                        n5 = 1;
                        b = false;
                    } else {
                        n3 = ayaNumberOfLine;
                        s3 = string4;
                        n10 = 1;
                    }
                    string = string3;
                    n6 = n12;
                } else {
                    n3 = ayaNumberOfLine;
                    string = string3;
                    n6 = n12;
                    n10 = 1;
                }
            }
            i += n10;
            spaceWidth = n9;
            pageWidth = n;
        }
        if (string != "") {
            this.lines.add(string);
            this.wordIndexesOfLines.add(split.length - 1);
            this.ayaLineMap.add(1 + this.getAyaNumberOfLine(n3, string));
            this.linesWidths.add(n6);
        }
        this.wrapCompleted = true;
        this.onTextWrapcomplete.onTextWrapComplete(b);
    }

    public void wrapNowWords(final int pageWidth, final int pageHeight, final int n) {
        if (StaticObjects.contentWords == null) {
            StaticObjects.contentWords = new ArrayList();
        }
        Log.d(TAG, "wrapNowWords: Start");
        int i = 0;
        this.wrapCompleted = false;
        this.pageHeight = pageHeight;
        this.pageWidth = pageWidth;
        this.linesOfIndexsOfWords = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        final float spaceWidth = this.fontManager.getSpaceWidth();
        float n2 = 0.0f;
        while (i < StaticObjects.contentWords.size()) {
            final WordOfQuran wordOfQuran = StaticObjects.contentWords.get(i);
            WordOfQuran wordOfQuran2;
            if (i > 0) {
                wordOfQuran2 = StaticObjects.contentWords.get(i - 1);
            } else {
                wordOfQuran2 = null;
            }
            wordOfQuran.setWordWidthInPixel(fontManager.getWordWidth(wordOfQuran.getWordString()));
            L:
            {
                if (!wordOfQuran.getWordString().equals("3101") && !wordOfQuran.getWordString().equals("3102")) {
                    if (n == 1 && wordOfQuran2 != null && wordOfQuran2.getWordString().endsWith(",2199")) {
                        linesOfIndexsOfWords.add(list);
                        list = new ArrayList<>();
                        n2 = 0.0f;
                    }
                    final float n3 = n2 + spaceWidth;
                    if (n3 + wordOfQuran.getWordWidthInPixel() <= pageWidth && (!wordOfQuran.getWordString().equals("147,5116,1141,5116,135,533,2228,5117,2111,4215") || list.size() <= 0)) {
                        if (list.size() > 0) {
                            n2 = n3;
                        }
                        list.add(i);
                        wordOfQuran.setLineIndexOfWord(linesOfIndexsOfWords.size());
                        n2 += wordOfQuran.getWordWidthInPixel();
                        break L;
                    }
                    linesOfIndexsOfWords.add(list);
                    list = new ArrayList<>();
                    if (!wordOfQuran.getWordString().equals("3101") && !wordOfQuran.getWordString().equals("3102") && !wordOfQuran.getWordString().equals("147,5116,1141,5116,135,533,2228,5117,2111,4215")) {
                        --i;
                    } else if (wordOfQuran.getWordString().equals("147,5116,1141,5116,135,533,2228,5117,2111,4215") || (wordOfQuran.getWordString().endsWith(",2199") && n == 1)) {
                        float n4;
                        if (list.size() > 0) {
                            n4 = spaceWidth + 0.0f;
                        } else {
                            n4 = 0.0f;
                        }
                        list.add(i);
                        wordOfQuran.setLineIndexOfWord(linesOfIndexsOfWords.size());
                        n2 = n4 + wordOfQuran.getWordWidthInPixel();
                        break L;
                    }
                } else {
                    if (list.size() > 0) {
                        linesOfIndexsOfWords.add(list);
                    }
                    final ArrayList<Integer> list2 = new ArrayList<>();
                    list2.add(i);
                    linesOfIndexsOfWords.add(list2);
                    list = new ArrayList<>();
                    wordOfQuran.setLineIndexOfWord(linesOfIndexsOfWords.size());
                }
                n2 = 0.0f;
            }
            ++i;
        }
        if (list.size() > 0) {
            linesOfIndexsOfWords.add(list);
            Log.d(TAG, "wrapNowWords: "+list.toString());
        }
        wrapCompleted = true;
        onTextWrapcomplete.onTextWrapComplete(true);
        Log.d(TAG, "wrapNowWords: "+wrapCompleted);
    }

    public int getAyaNumberOfLine(int ayaNumber, @NonNull String substring) {
        if (substring.indexOf("2200") != -1) {
            final int index = substring.indexOf("442");
            int n = 0;
            if (index != -1) {
                substring = substring.substring(substring.indexOf("2200"), index);
            }
            while (true) {
                final int index2 = substring.indexOf("2200", n + 1);
                if (index2 == -1) {
                    break;
                }
                n = index2;
            }
            final int index3 = substring.indexOf("2200", n);
            final int index4 = substring.indexOf("2199", index3);
            if (index3 != -1 && index4 != -1) {
                ayaNumber = this.parseAyaNumber(substring.substring(index3 + 5, index4 - 1));
            }
        }
        return ayaNumber;
    }

    public int getAyaNumberOfLineFromAyaMap(final int n) {
        return ayaLineMap.get(n);
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public int getFristAyaNumOfPage(final int n) {
        if (n == 1) {
            return 1;
        }
        final int n2 = (n - 1) * getLinesPerPage() - 1;
        final int size = this.ayaLineMap.size();
        int intValue = 0;
        if (n2 < size) {
            intValue = this.ayaLineMap.get(n2);
        }
        return intValue;
    }

    public int getLastAyaNumOfLine(final int n) {
        if (n < this.ayaLineMap.size()) {
            return this.ayaLineMap.get(n);
        }
        if (-1 + this.currentSuraId < QuranMarks.surasAyahs.length && -1 + this.currentSuraId > -1) {
            return QuranMarks.surasAyahs[-1 + this.currentSuraId];
        }
        return 0;
    }

    public int getLastAyaNumOfPage(final int n) {
        final int n2 = -1 + n * this.getLinesPerPage();
        if (n2 < this.ayaLineMap.size()) {
            return this.ayaLineMap.get(n2);
        }
        return QuranMarks.surasAyahs[-1 + this.currentSuraId];
    }

    public float getLastLineWidth() {
        return this.linesWidths.get(-1 + this.linesWidths.size());
    }

    @Nullable
    public float[] getLastLineWidths(final int n) {
        final int linesPerPage = this.getLinesPerPage();
        for (int i = this.linesWidths.size(); i >= 0; --i) {
            final int n2 = i + linesPerPage * (n - 1);
            if (n2 < this.linesWidths.size() && this.linesWidths.get(n2) != null && this.linesWidths.get(n2) != 0.0f) {
                return new float[]{i, this.linesWidths.get(n2)};
            }
        }
        return null;
    }

    public float getLineWidth(final int n) {
        return this.linesWidths.get(n);
    }

    public int getLinesPerPage() {
        return 1 + this.pageHeight / this.fontManager.getLineHeight();
    }

    public Vector<Float> getLinesWidths() {
        return this.linesWidths;
    }

    @NonNull
    public Vector<Float> getLinesWidths(final int n) {
        final Vector<Float> vector = new Vector<>();
        for (int linesPerPage = this.getLinesPerPage(), i = 0; i < linesPerPage; ++i) {
            final int n2 = i + linesPerPage * (n - 1);
            if (n2 >= this.linesWidths.size()) {
                return vector;
            }
            vector.add(this.linesWidths.get(n2));
        }
        return vector;
    }

    @NonNull
    public Vector<String> getPage(final int n) {
        final Vector<String> vector = new Vector<>();
        if (this.lines == null) {
            return vector;
        }
        for (int linesPerPage = this.getLinesPerPage(), i = 0; i < linesPerPage; ++i) {
            final int n2 = i + linesPerPage * (n - 1);
            if (n2 >= this.lines.size()) {
                return vector;
            }
            if (n2 < this.lines.size()) {
                vector.add(this.lines.get(n2));
            }
        }
        return vector;
    }

    public int getPageOfWordIndex(final int n) {
        if (wordIndexesOfPages == null) {
            return -1;
        }
        while (true) {
            for (int i = 0; i < this.wordIndexesOfPages.size(); ++i) {
                if (wordIndexesOfPages.get(i) > n + 1) {
                    if (i == -1) {
                        i = wordIndexesOfPages.size();
                    }
                    return i;
                }
            }
            int i = -1;
            continue;
        }
    }

    public int getPagesCount() {
        if (lines == null) {
            return 0;
        }
        final double n = lines.size() / getLinesPerPage();
        return (int) (n + 0.99);
    }

    public int getWordIndexOfPage(final int n) {
        if (this.wordIndexesOfPages == null) {
            return -1;
        }
        final int n2 = n - 1;
        if (n2 < this.wordIndexesOfPages.size()) {
            return this.wordIndexesOfPages.get(n2);
        }
        return -1;
    }

    public Vector<Integer> getWordIndexesOfLine() {
        return wordIndexesOfLines;
    }

    public float getWordWidthOfWordIndex(final int n) {
        return wordWidthsArr.get(n);
    }

    public Vector<Float> getWordWidthsArr() {
        return wordWidthsArr;
    }

    public boolean isWrapCompleted() {
        return wrapCompleted;
    }

    public int parseAyaNumber(final String s) {
        return Integer.parseInt(new StringBuffer(s.replace("2201", "0").replace("2202", "1").replace("2203", "2").replace("2204", "3").replace("2205", "4").replace("2206", "5").replace("2207", "6").replace("2208", "7").replace("2209", "8").replace("2210", "9").replace(",", "")).reverse().toString());
    }

    public void readFromfile() {
    }

    public void setOnTextWrapComplete(final OnTextWrapcomplete onTextWrapcomplete) {
        this.onTextWrapcomplete = onTextWrapcomplete;
    }

    public void setRequiredWordIndex(final int requiredWordIndex) {
        this.requiredWordIndex = requiredWordIndex;
    }

    public void writeToFile(final String s) {
    }

    public TextWrapper(final FontManager fontManager) {
        linesWidths = new Vector<>();
        this.fontManager = fontManager;
    }

    public List<List<Integer>> getAllLinesOfContent() {
        return linesOfIndexsOfWords;
    }

    public List<String> getAllLinesOfSura() {
        return lines;
    }
}