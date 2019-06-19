package net.vorson.muhammadsufwan.prayertimesformuslim.util.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CallLog;
import android.telecom.Call;
//import com.app.androidnewsapp.models.News;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_android_news_app";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_COMMENTS_COUNT = "comments_count";
    private static final String KEY_CONTENT_TYPE = "content_type";
    private static final String KEY_ID = "id";
    private static final String KEY_NEWS_DATE = "news_date";
    private static final String KEY_NEWS_DESCRIPTION = "news_description";
    private static final String KEY_NEWS_IMAGE = "news_image";
    private static final String KEY_NEWS_TITLE = "news_title";
    private static final String KEY_NID = "nid";
    private static final String KEY_VIDEO_ID = "video_id";
    private static final String KEY_VIDEO_URL = "video_url";
    private static final String TABLE_NAME = "tbl_favorite";

    public enum DatabaseManager {
        INSTANCE;

        private SQLiteDatabase db;
        DbHandler dbHelper;
        private boolean isDbClosed;

        public void init(Context context) {
            this.dbHelper = new DbHandler(context);
            if (this.isDbClosed) {
                this.isDbClosed = false;
                this.db = this.dbHelper.getWritableDatabase();
            }
        }

        public boolean isDatabaseClosed() {
            return this.isDbClosed;
        }

        public void closeDatabase() {
            if (!this.isDbClosed) {
                SQLiteDatabase sQLiteDatabase = this.db;
                if (sQLiteDatabase != null) {
                    this.isDbClosed = true;
                    sQLiteDatabase.close();
                    this.dbHelper.close();
                }
            }
        }
    }

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_favorite(id INTEGER PRIMARY KEY,nid INTEGER,news_title TEXT,category_name TEXT,news_date TEXT,news_image TEXT,news_description TEXT,content_type TEXT,video_url TEXT,video_id TEXT,comments_count INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_favorite");
        onCreate(db);
        }

//    public void AddtoFavorite(News pj) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_NID, Long.valueOf(pj.getNid()));
//        values.put(KEY_NEWS_TITLE, pj.getNews_title());
//        values.put(KEY_CATEGORY_NAME, pj.getCategory_name());
//        values.put(KEY_NEWS_DATE, pj.getNews_date());
//        values.put(KEY_NEWS_IMAGE, pj.getNews_image());
//        values.put(KEY_NEWS_DESCRIPTION, pj.getNews_description());
//        values.put("content_type", pj.getContent_type());
//        values.put(KEY_VIDEO_URL, pj.getVideo_url());
//        values.put(KEY_VIDEO_ID, pj.getVideo_id());
//        values.put(KEY_COMMENTS_COUNT, Long.valueOf(pj.getComments_count()));
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }
//
//    public List<News> getAllData() {
//        List<News> dataList = new ArrayList();
//        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM tbl_favorite ORDER BY id DESC", null);
//        if (cursor.moveToFirst()) {
//            while (true) {
//                News contact = new News();
//                contact.setId(Integer.parseInt(cursor.getString(0)));
//                contact.setNid((long) Integer.parseInt(cursor.getString(1)));
//                contact.setNews_title(cursor.getString(2));
//                contact.setCategory_name(cursor.getString(3));
//                contact.setNews_date(cursor.getString(4));
//                contact.setNews_image(cursor.getString(5));
//                contact.setNews_description(cursor.getString(6));
//                contact.setContent_type(cursor.getString(7));
//                contact.setVideo_url(cursor.getString(8));
//                contact.setVideo_id(cursor.getString(9));
//                contact.setComments_count((long) Integer.parseInt(cursor.getString(10)));
//                dataList.add(contact);
//                if (!cursor.moveToNext()) {
//                    break;
//                }
//            }
//        }
//        return dataList;
//    }
//
//    public List<News> getFavRow(long id) {
//        List<News> dataList = new ArrayList();
//        String selectQuery = new StringBuilder();
//        selectQuery.append("SELECT  * FROM tbl_favorite WHERE nid=");
//        selectQuery.append(id);
//        Cursor cursor = getWritableDatabase().rawQuery(selectQuery.toString(), null);
//        if (cursor.moveToFirst()) {
//            while (true) {
//                News contact = new News();
//                contact.setId(Integer.parseInt(cursor.getString(0)));
//                contact.setNid((long) Integer.parseInt(cursor.getString(1)));
//                contact.setNews_title(cursor.getString(2));
//                contact.setCategory_name(cursor.getString(3));
//                contact.setNews_date(cursor.getString(4));
//                contact.setNews_image(cursor.getString(5));
//                contact.setNews_description(cursor.getString(6));
//                contact.setContent_type(cursor.getString(7));
//                contact.setVideo_url(cursor.getString(8));
//                contact.setVideo_id(cursor.getString(9));
//                contact.setComments_count((long) Integer.parseInt(cursor.getString(10)));
//                dataList.add(contact);
//                if (!cursor.moveToNext()) {
//                    break;
//                }
//            }
//        }
//        return dataList;
//    }
//
//    public void RemoveFav(News contact) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.delete(TABLE_NAME, "nid = ?", new String[]{String.valueOf(contact.getNid())});
//        db.close();
//    }
}

