package com.sufi.prayertimes.quran.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.annotations.NonNull;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "word_meanings.db";
    private String DB_PATH;
    private Context context;
    private SQLiteDatabase myDataBase;

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DatabaseHandler(@NonNull Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        this.DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    public SQLiteDatabase getDB() {
        if (this.myDataBase == null) {
            opendDb();
        }
        return this.myDataBase;
    }

    public void opendDb() {
        try {
            createDataBase();
            try {
                openDataBase();
            } catch (SQLException e) {
                throw e;
            }
        } catch (IOException unused) {
            throw new Error("Unable to create database");
        }
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            try {
                copyDataBase();
            } catch (IOException unused) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.DB_PATH);
            stringBuilder.append(DB_NAME);
            sQLiteDatabase = SQLiteDatabase.openDatabase(stringBuilder.toString(), null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        return sQLiteDatabase != null;
    }

    private void copyDataBase() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.DB_PATH);
        stringBuilder.append(DB_NAME);
        String stringBuilder2 = stringBuilder.toString();
        File file = new File(stringBuilder2);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            InputStream open = this.context.getAssets().open("db/word_meanings.db");
            OutputStream fileOutputStream = new FileOutputStream(stringBuilder2);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    open.close();
                    return;
                }
            }
        }
    }

    public void openDataBase() throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.DB_PATH);
        stringBuilder.append(DB_NAME);
        myDataBase = SQLiteDatabase.openDatabase(stringBuilder.toString(), null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close() {
        if (this.myDataBase != null) {
            this.myDataBase.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
