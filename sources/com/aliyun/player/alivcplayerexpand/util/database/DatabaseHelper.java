package com.aliyun.player.alivcplayerexpand.util.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance;
    public static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AliPlayerDemoDownload/" + DatabaseManager.DB_NAME;
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, String str, int i2) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i2);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseHelper(context, DatabaseManager.DB_NAME, 1);
                }
            }
        }
        return mInstance;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_SQL);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    public static DatabaseHelper getInstance(Context context, String str) {
        if (mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (mInstance == null) {
                    if (TextUtils.isEmpty(str)) {
                        mInstance = new DatabaseHelper(context, str, 1);
                    } else {
                        mInstance = new DatabaseHelper(context, DB_PATH, 1);
                    }
                }
            }
        }
        return mInstance;
    }
}
