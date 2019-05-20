package net.vorson.muhammadsufwan.prayertimesformuslim.quran.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import net.vorson.muhammadsufwan.prayertimesformuslim.quran.StaticObjects;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.IndexKey;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordMeaning;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordOfQuran;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class WordMeaningDataManager
{
    private static final String KEY_AYA_NUM = "ayaNum";
    private static final String KEY_ID = "_id";
    private static final String KEY_MEANING = "meaning";
    private static final String KEY_SURA_NUM = "suraId";
    private static final String KEY_WORD = "word";
    private static final String KEY_WORD_INDEXES = "wordIndexes";
    private SQLiteDatabase myDataBase;

    public WordMeaningDataManager(final SQLiteDatabase myDataBase) {
        this.myDataBase = myDataBase;
    }

    @Nullable
    public WordMeaning getWordMeaning1(final String s, final int n) {
        final SQLiteDatabase myDataBase = this.myDataBase;
        final String[] array = new String[]{KEY_ID, KEY_AYA_NUM, KEY_SURA_NUM, KEY_WORD_INDEXES, KEY_WORD, KEY_MEANING};
        final StringBuilder sb = new StringBuilder();
        sb.append("wordIndexes like '%");
        sb.append(s);
        sb.append("%' and ");
        sb.append("suraId");
        sb.append("=");
        sb.append(n);
        final Cursor query = myDataBase.query("word_meanings", array, sb.toString(), null, null, null, null);
        WordMeaning wordMeaning = null;
        if (query != null) {
            query.moveToFirst();
            final int count = query.getCount();
            wordMeaning = null;
            if (count > 0) {
                wordMeaning = new WordMeaning(Integer.parseInt(query.getString(0)), Integer.parseInt(query.getString(1)), Integer.parseInt(query.getString(2)), query.getString(3), query.getString(4), query.getString(5));
            }
            query.close();
        }
        return wordMeaning;
    }

    public void getWordMeaningVectorOfJoza() {
        int i = 0;
        while (i < StaticObjects.contentWords.size()) {
            final int suraId = StaticObjects.contentWords.get(i).getSuraId();
            final SQLiteDatabase myDataBase = this.myDataBase;
            final String[] array = new String[]{KEY_ID, KEY_AYA_NUM, KEY_SURA_NUM, KEY_WORD_INDEXES, KEY_WORD, KEY_MEANING};
            final StringBuilder sb = new StringBuilder();
            sb.append("suraId=");
            sb.append(suraId);
            final Cursor query = myDataBase.query("word_meanings", array, sb.toString(), null, null, null, null);
            if (query != null) {
                query.moveToFirst();
                int n = i;
                for (int j = 0; j < query.getCount(); ++j) {
                    query.moveToPosition(j);
                    final String[] split = query.getString(3).split(",");
                    int n2 = n;
                    for (int k = 0; k < split.length; ++k) {
                        if (split[k] != null && !split[k].equals("")) {
                            int int1;
                            for (int1 = Integer.parseInt(split[k]); n2 < StaticObjects.contentWords.size() && StaticObjects.contentWords.get(n2).getWordIndexOfSura() < int1; ++n2) {}
                            if (n2 >= StaticObjects.contentWords.size()) {
                                break;
                            }
                            if (StaticObjects.contentWords.get(n2).getWordIndexOfSura() == int1) {
                                (StaticObjects.contentWords.get(n2)).setPlanTextForMeaning(query.getString(4));
                                (StaticObjects.contentWords.get(n2)).setWordMeaning(query.getString(5));
                            }
                        }
                    }
                    n = n2;
                }
                i = n;
            }
            query.close();
            for (int n3 = suraId + 1; i < StaticObjects.contentWords.size() && StaticObjects.contentWords.get(i).getSuraId() < n3; ++i) {}
        }
    }

    @NonNull
    public SparseArray<WordMeaning> getWordMeaningVectorOfSura(final int n) {
        final SparseArray sparseArray = new SparseArray();
        final SQLiteDatabase myDataBase = this.myDataBase;
        final String[] array = new String[]{KEY_ID, KEY_AYA_NUM, KEY_SURA_NUM, KEY_WORD_INDEXES, KEY_WORD, KEY_MEANING};
        final StringBuilder sb = new StringBuilder();
        sb.append("suraId=");
        sb.append(n);
        final Cursor query = myDataBase.query("word_meanings", array, sb.toString(), null, null, null, null);
        if (query != null) {
            query.moveToFirst();
            for (int i = 0; i < query.getCount(); ++i) {
                query.moveToPosition(i);
                final String[] split = query.getString(3).split(",");
                for (int j = 0; j < split.length; ++j) {
                    if (split[j] != null && !split[j].equals("")) {
                        sparseArray.put(Integer.parseInt(split[j]), new WordMeaning(Integer.parseInt(query.getString(0)), Integer.parseInt(query.getString(1)), Integer.parseInt(query.getString(2)), Integer.parseInt(split[j]), query.getString(4), query.getString(5)));
                    }
                }
            }
        }
        query.close();
        return (SparseArray<WordMeaning>)sparseArray;
    }

    @NonNull
    public HashMap<IndexKey, WordMeaning> getWordMeaningVectorOfSura(final int n, @NonNull final ArrayList<Integer> list, @NonNull final ArrayList<ArrayList<Integer>> list2) {
        final HashMap<IndexKey, WordMeaning> hashMap = new HashMap<>();
        final SQLiteDatabase myDataBase = this.myDataBase;
        final String[] array = new String[6];
        array[0] = "_id";
        int n2 = 1;
        array[n2] = "ayaNum";
        int n3 = 2;
        array[n3] = "suraId";
        array[3] = "wordIndexes";
        array[4] = "word";
        array[5] = "meaning";
        final StringBuilder sb = new StringBuilder();
        sb.append("suraId=");
        sb.append(n);
        final Cursor query = myDataBase.query("word_meanings", array, sb.toString(), null, null, null, null);
        if (query != null) {
            query.moveToFirst();
            for (int i = 0; i < query.getCount(); ++i, n2 = 1, n3 = 2) {
                query.moveToPosition(i);
                final String[] split = query.getString(3).split(",");
                for (int j = 0; j < split.length; ++j, n2 = 1, n3 = 2) {
                    if (split[j] != null && !split[j].equals("")) {
                        final WordMeaning wordMeaning = new WordMeaning(Integer.parseInt(query.getString(0)), Integer.parseInt(query.getString(n2)), Integer.parseInt(query.getString(n3)), Integer.parseInt(split[j]), query.getString(4), query.getString(5));
                        final int intValue = list.get(Integer.parseInt(split[j]));
                        hashMap.put(new IndexKey(intValue, Integer.parseInt(split[j]) - list2.get(intValue).get(0)), wordMeaning);
                    }
                }
            }
        }
        query.close();
        return hashMap;
    }

    @NonNull
    public HashMap<String, WordMeaning> getWordMeaningVectorOfSura1(final int n, @NonNull final ArrayList<Integer> list, @NonNull final ArrayList<ArrayList<Integer>> list2) {
        final HashMap<String, WordMeaning> hashMap = new HashMap<>();
        final SQLiteDatabase myDataBase = this.myDataBase;
        final String[] array = new String[6];
        array[0] = "_id";
        int n2 = 1;
        array[n2] = "ayaNum";
        int n3 = 2;
        array[n3] = "suraId";
        array[3] = "wordIndexes";
        array[4] = "word";
        array[5] = "meaning";
        final StringBuilder sb = new StringBuilder();
        sb.append("suraId=");
        sb.append(n);
        final Cursor query = myDataBase.query("word_meanings", array, sb.toString(), null, null, null, null);
        if (query != null) {
            query.moveToFirst();
            for (int i = 0; i < query.getCount(); ++i, n2 = 1, n3 = 2) {
                query.moveToPosition(i);
                final String[] split = query.getString(3).split(",");
                for (int j = 0; j < split.length; ++j, n2 = 1, n3 = 2) {
                    if (split[j] != null && !split[j].equals("")) {
                        final WordMeaning wordMeaning = new WordMeaning(Integer.parseInt(query.getString(0)), Integer.parseInt(query.getString(n2)), Integer.parseInt(query.getString(n3)), Integer.parseInt(split[j]), query.getString(4), query.getString(5));
                        final int intValue = list.get(Integer.parseInt(split[j]));
                        int n4;
                        if (intValue < list2.size()) {
                            n4 = Integer.parseInt(split[j]) - list2.get(intValue).get(0);
                        }
                        else {
                            n4 = 0;
                        }
                        wordMeaning.setLinenumber(intValue);
                        final StringBuilder sb2 = new StringBuilder();
                        sb2.append(intValue);
                        sb2.append("_");
                        sb2.append(n4);
                        hashMap.put(sb2.toString(), wordMeaning);
                    }
                }
            }
        }
        query.close();
        return hashMap;
    }

    public void updateWordMeaning() {
        final Cursor query = myDataBase.query("word_meanings", new String[] {KEY_ID, KEY_AYA_NUM, KEY_SURA_NUM, KEY_WORD_INDEXES, KEY_WORD, KEY_MEANING},
                null, null, null, null, null);
        if (query != null) {
            query.moveToFirst();
            myDataBase.beginTransaction();
            for (int i = 0; i < query.getCount(); ++i) {
                query.moveToPosition(0);
                final WordMeaning wordMeaning = new WordMeaning(Integer.parseInt(query.getString(0)), Integer.parseInt(query.getString(1)), Integer.parseInt(query.getString(2)), query.getString(3), query.getString(4), query.getString(5));
                final ContentValues contentValues = new ContentValues();
                final StringBuilder sb = new StringBuilder();
                sb.append(",");
                sb.append(wordMeaning.get_wotdIndexes());
                contentValues.put("wordIndexes", sb.toString());
                final String[] array = { null };
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(wordMeaning.getId());
                sb2.append("");
                array[0] = sb2.toString();
                final int update = myDataBase.update("word_meanings", contentValues, "_id=?", array);
                final PrintStream out = System.out;
                final StringBuilder sb3 = new StringBuilder();
                sb3.append(update);
                sb3.append(" ");
                sb3.append(i);
                out.println(sb3.toString());
            }
            myDataBase.endTransaction();
            query.close();
        }
    }
}