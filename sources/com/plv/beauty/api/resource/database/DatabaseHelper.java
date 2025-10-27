package com.plv.beauty.api.resource.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.plv.beauty.api.resource.database.DatabaseManager;

/* loaded from: classes4.dex */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table if not exists RemoteResItem (id integer primary key autoincrement, name text, hash text, path text)";
    public static final String TABLE_NAME = "RemoteResItem";

    public DatabaseHelper(@Nullable Context context, @Nullable String str, @Nullable SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        super(context, str, cursorFactory, i2);
    }

    public synchronized void deleteDownloadResourceItem(SQLiteDatabase sQLiteDatabase, String str) {
        sQLiteDatabase.delete(TABLE_NAME, "name=?", new String[]{str});
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    public synchronized DatabaseManager.DownloadResourceItem queryDownloadResourceItem(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorQuery;
        cursorQuery = sQLiteDatabase.query(TABLE_NAME, null, "name=?", new String[]{str}, null, null, null);
        return cursorQuery.moveToFirst() ? new DatabaseManager.DownloadResourceItem(str, cursorQuery.getString(cursorQuery.getColumnIndex("hash")), cursorQuery.getString(cursorQuery.getColumnIndex("path"))) : null;
    }

    public String queryResource(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorQuery = sQLiteDatabase.query(TABLE_NAME, null, "name=?", new String[]{str}, null, null, null);
        if (cursorQuery.moveToFirst()) {
            return cursorQuery.getString(cursorQuery.getColumnIndex("path"));
        }
        return null;
    }

    public String queryhash(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursorQuery = sQLiteDatabase.query(TABLE_NAME, null, "name=?", new String[]{str}, null, null, null);
        if (cursorQuery.moveToFirst()) {
            return cursorQuery.getString(cursorQuery.getColumnIndex("hash"));
        }
        return null;
    }

    public synchronized void removeAllResource(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.delete(TABLE_NAME, "", new String[0]);
    }

    public synchronized void updateItem(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        contentValues.put("hash", str2);
        contentValues.put("path", str3);
        Cursor cursorQuery = sQLiteDatabase.query(TABLE_NAME, null, "name=?", new String[]{str}, null, null, null);
        if (cursorQuery.moveToFirst()) {
            contentValues.put("id", Integer.valueOf(cursorQuery.getInt(cursorQuery.getColumnIndex("id"))));
            sQLiteDatabase.replace(TABLE_NAME, null, contentValues);
        } else {
            sQLiteDatabase.insert(TABLE_NAME, null, contentValues);
        }
    }
}
