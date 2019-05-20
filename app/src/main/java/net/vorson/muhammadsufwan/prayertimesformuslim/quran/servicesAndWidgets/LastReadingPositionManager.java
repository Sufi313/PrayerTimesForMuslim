package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.BookmarkObj;

public class LastReadingPositionManager {
    BookmarkObj bookmarkObj;
    SharedPreferences persist;

    public LastReadingPositionManager(@NonNull Context context) {
        persist = context.getSharedPreferences("lastReadingPosition", Context.MODE_PRIVATE);
    }

    @Nullable
    public int[] getBookmarks() {
        if (persist.getString("lastReadingPositionString", "").split(",").length != 4) {
            return null;
        }
        return new int[]{Integer.parseInt(persist.getString("lastReadingPositionString", "")
                .split(",")[0]), Integer.parseInt(persist.getString("lastReadingPositionString", "")
                .split(",")[1]), Integer.parseInt(persist.getString("lastReadingPositionString", "")
                .split(",")[2]), Integer.parseInt(persist.getString("lastReadingPositionString", "")
                .split(",")[3])};
    }

    public void addBookmark(int i, int i2, int i3, int i4) {
        Editor edit = persist.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(",");
        stringBuilder.append(i2);
        stringBuilder.append(",");
        stringBuilder.append(i3);
        stringBuilder.append(",");
        stringBuilder.append(i4);
        stringBuilder.append("");
        edit.putString("lastReadingPositionString", stringBuilder.toString());
        edit.apply();
    }

    public boolean isAddBookmark(int i, int i2, int i3, int i4) {
        Editor edit = persist.edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(",");
        stringBuilder.append(i2);
        stringBuilder.append(",");
        stringBuilder.append(i3);
        stringBuilder.append(",");
        stringBuilder.append(i4);
        stringBuilder.append("");
        edit.putString("lastReadingPositionString", stringBuilder.toString());
        return edit.commit();
    }
}
