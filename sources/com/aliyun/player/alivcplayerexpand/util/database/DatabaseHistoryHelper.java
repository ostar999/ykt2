package com.aliyun.player.alivcplayerexpand.util.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes2.dex */
public class DatabaseHistoryHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static DatabaseHistoryHelper mInstance;

    public DatabaseHistoryHelper(Context context, String str, int i2) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i2);
    }

    public static DatabaseHistoryHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DatabaseHistoryHelper.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseHistoryHelper(context, DatabaseManager.HISTORY_DB_NAME, 1);
                }
            }
        }
        return mInstance;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_SQL_WATCH_HISTORY);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}
