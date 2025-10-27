package com.arialyy.aria.orm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/* loaded from: classes2.dex */
abstract class AbsDelegate {
    static final String TAG = "AbsDelegate";

    public SQLiteDatabase checkDb(SQLiteDatabase sQLiteDatabase) {
        return SqlUtil.checkDb(sQLiteDatabase);
    }

    public void closeCursor(Cursor cursor) {
        SqlUtil.closeCursor(cursor);
    }
}
