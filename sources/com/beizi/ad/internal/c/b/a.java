package com.beizi.ad.internal.c.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.beizi.ad.internal.c.k;
import com.beizi.ad.internal.c.p;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.umeng.analytics.pro.aq;

/* loaded from: classes2.dex */
class a extends SQLiteOpenHelper implements c {

    /* renamed from: a, reason: collision with root package name */
    private static final String[] f4060a = {aq.f22519d, "url", SessionDescription.ATTR_LENGTH, IMediaFormat.KEY_MIME};

    public a(Context context) {
        super(context, "AndroidVideoCache.db", (SQLiteDatabase.CursorFactory) null, 1);
        k.a(context);
    }

    @Override // com.beizi.ad.internal.c.b.c
    public p a(String str) throws Throwable {
        Throwable th;
        Cursor cursorQuery;
        k.a(str);
        p pVarA = null;
        try {
            cursorQuery = getReadableDatabase().query("SourceInfo", f4060a, "url=?", new String[]{str}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        pVarA = a(cursorQuery);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return pVarA;
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        k.a(sQLiteDatabase);
        sQLiteDatabase.execSQL("CREATE TABLE SourceInfo (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,url TEXT NOT NULL,mime TEXT,length INTEGER);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        throw new IllegalStateException("Should not be called. There is no any migration");
    }

    @Override // com.beizi.ad.internal.c.b.c
    public void a(String str, p pVar) {
        k.a(str, pVar);
        boolean z2 = a(str) != null;
        ContentValues contentValuesA = a(pVar);
        if (z2) {
            getWritableDatabase().update("SourceInfo", contentValuesA, "url=?", new String[]{str});
        } else {
            getWritableDatabase().insert("SourceInfo", null, contentValuesA);
        }
    }

    private p a(Cursor cursor) {
        return new p(cursor.getString(cursor.getColumnIndexOrThrow("url")), cursor.getInt(cursor.getColumnIndexOrThrow(SessionDescription.ATTR_LENGTH)), cursor.getString(cursor.getColumnIndexOrThrow(IMediaFormat.KEY_MIME)));
    }

    private ContentValues a(p pVar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", pVar.f4118a);
        contentValues.put(SessionDescription.ATTR_LENGTH, Integer.valueOf(pVar.f4119b));
        contentValues.put(IMediaFormat.KEY_MIME, pVar.f4120c);
        return contentValues;
    }
}
