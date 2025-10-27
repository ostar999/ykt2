package com.psychiatrygarden.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes5.dex */
public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "mytitle.db";
    public static final String ID = "id";
    public static final String Initials = "Initials";
    public static final String NAME = "name";
    public static final String ORDERID = "orderId";
    public static final String SELECTED = "selected";
    public static final String SORT = "sort";
    public static final String TABLE_CHANNEL = "channel";
    public static final String TABLE_CHANNEL1 = "channel1";
    public static final String Today_topic_num = "Today_topic_num";
    public static final int VERSION = 6;
    private Context context;

    public SQLHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 6);
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) throws SQLException {
        db.execSQL("create table if not exists channel1(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER , name TEXT , orderId INTEGER , sort INTEGER , Today_topic_num TEXT,Initials TEXT,selected SELECTED)");
        db.execSQL("create table if not exists channel(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER , name TEXT , orderId INTEGER , sort INTEGER , Today_topic_num TEXT,selected SELECTED)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
        try {
            db.execSQL("DROP TABLE channel");
            db.execSQL("DROP TABLE channel1");
        } catch (Exception unused) {
        }
        onCreate(db);
    }
}
