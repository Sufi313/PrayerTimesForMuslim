package com.sufi.prayertimes.quran.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

public class BookmarksManager {
    List<BookmarkObj> bookmarks;
    SharedPreferences persist;

    public BookmarksManager(@NonNull Context context) {
        this.persist = context.getSharedPreferences("BookMarks", 0);
        reloadBookmarks();
    }

    public void reloadBookmarks() {
        Map all = persist.getAll();
        bookmarks = new ArrayList();
        Object[] toArray = all.values().toArray();
        for (Object obj : toArray) {
            String[] split = ((String) obj).split(",");
            bookmarks.add(new BookmarkObj(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
    }

    public List<BookmarkObj> getBookmarks() {
        return bookmarks;
    }

    public void addBookmarks(@NonNull BookmarkObj bookmarkObj) {
        Editor edit = persist.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bookmarkObj.getAyaNum());
        stringBuilder.append("");
        stringBuilder.append(bookmarkObj.getSuraNum());
        stringBuilder.append("");
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(bookmarkObj.getAyaNum());
        stringBuilder3.append(",");
        stringBuilder3.append(bookmarkObj.getSuraNum());
        stringBuilder3.append(",");
        stringBuilder3.append(bookmarkObj.getStartWordIndex());
        edit.putString(stringBuilder2, stringBuilder3.toString());
        edit.commit();
    }

    public void removeBookmarks(@NonNull BookmarkObj bookmarkObj) {
        Editor edit = persist.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bookmarkObj.getAyaNum());
        stringBuilder.append("");
        stringBuilder.append(bookmarkObj.getSuraNum());
        stringBuilder.append("");
        edit.remove(stringBuilder.toString());
        edit.commit();
        reloadBookmarks();
    }
}
