package com.nirvana.tools.logger.cache.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* loaded from: classes4.dex */
public class DBHelper extends SQLiteOpenHelper {
    private String mCreateSql;
    private String mDeleteSql;
    private String mIndexSql;

    public DBHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i2, String str2, String str3, String str4) {
        super(context, str, cursorFactory, i2);
        this.mCreateSql = str2;
        this.mDeleteSql = str3;
        this.mIndexSql = str4;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            sQLiteDatabase.execSQL(this.mCreateSql);
            sQLiteDatabase.execSQL(this.mIndexSql);
            sQLiteDatabase.execSQL("PRAGMA auto_vacuum = FULL");
        } catch (Exception e2) {
            Log.e("DbHelper", "Database onCreate Exception", e2);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        try {
            sQLiteDatabase.execSQL(this.mDeleteSql);
            onCreate(sQLiteDatabase);
        } catch (Exception e2) {
            Log.e("DbHelper", "Database onUpgrade Exception", e2);
        }
    }
}
