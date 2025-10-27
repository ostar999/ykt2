package com.beizi.fusion.a;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.beizi.fusion.g.ac;

/* loaded from: classes2.dex */
public class a extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f4729a;

    private a(Context context) {
        super(context, "beizi_ad.db", (SQLiteDatabase.CursorFactory) null, 2);
    }

    public static a a(Context context) {
        if (f4729a == null) {
            synchronized (a.class) {
                if (f4729a == null) {
                    f4729a = new a(context.getApplicationContext());
                }
            }
        }
        return f4729a;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        if (i3 > i2) {
            Cursor cursorQuery = null;
            try {
                try {
                    cursorQuery = sQLiteDatabase.query("Sqlite_master", new String[]{"name"}, "type=?", new String[]{"table"}, null, null, null);
                    while (cursorQuery.moveToNext()) {
                        String string = cursorQuery.getString(0);
                        if (string != null && string.startsWith("T_")) {
                            ac.c("BeiZis", "before alter table ");
                            sQLiteDatabase.execSQL("alter table " + string + " add column space_id");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (cursorQuery == null) {
                        return;
                    }
                }
                cursorQuery.close();
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        }
    }
}
