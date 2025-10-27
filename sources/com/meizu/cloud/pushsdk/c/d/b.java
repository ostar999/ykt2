package com.meizu.cloud.pushsdk.c.d;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes4.dex */
public class b extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9372a = "com.meizu.cloud.pushsdk.c.d.b";

    /* renamed from: b, reason: collision with root package name */
    private static b f9373b;

    private b(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public static b a(Context context, String str) {
        if (f9373b == null) {
            f9373b = new b(context.getApplicationContext(), str);
        }
        return f9373b;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS 'events' (id INTEGER PRIMARY KEY, eventData BLOB, dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        com.meizu.cloud.pushsdk.c.f.c.b(f9372a, "Upgrading database from version " + i2 + " to " + i3 + ". Destroying old data now..", new Object[0]);
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS 'events'");
        onCreate(sQLiteDatabase);
    }
}
