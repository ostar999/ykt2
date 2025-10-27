package com.xiaomi.push.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static volatile i f25685a;

    /* renamed from: a, reason: collision with other field name */
    private Context f1062a;

    private i(Context context) {
        this.f1062a = context;
    }

    private synchronized Cursor a(SQLiteDatabase sQLiteDatabase) {
        com.xiaomi.push.ao.a(false);
        try {
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
        return sQLiteDatabase.query("geoMessage", null, null, null, null, null, null);
    }

    public static i a(Context context) {
        if (f25685a == null) {
            synchronized (i.class) {
                if (f25685a == null) {
                    f25685a = new i(context);
                }
            }
        }
        return f25685a;
    }

    public synchronized int a(String str) {
        com.xiaomi.push.ao.a(false);
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            int iDelete = h.a(this.f1062a).a().delete("geoMessage", "message_id = ?", new String[]{str});
            h.a(this.f1062a).m743a();
            return iDelete;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return 0;
        }
    }

    public synchronized ArrayList<com.xiaomi.push.service.module.a> a() {
        ArrayList<com.xiaomi.push.service.module.a> arrayList;
        com.xiaomi.push.ao.a(false);
        try {
            Cursor cursorA = a(h.a(this.f1062a).a());
            arrayList = new ArrayList<>();
            if (cursorA != null) {
                while (cursorA.moveToNext()) {
                    com.xiaomi.push.service.module.a aVar = new com.xiaomi.push.service.module.a();
                    aVar.a(cursorA.getString(cursorA.getColumnIndex(MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID)));
                    aVar.b(cursorA.getString(cursorA.getColumnIndex("geo_id")));
                    aVar.a(cursorA.getBlob(cursorA.getColumnIndex("content")));
                    aVar.a(cursorA.getInt(cursorA.getColumnIndex("action")));
                    aVar.a(cursorA.getLong(cursorA.getColumnIndex("deadline")));
                    arrayList.add(aVar);
                }
                cursorA.close();
            }
            h.a(this.f1062a).m743a();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
        return arrayList;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized ArrayList<com.xiaomi.push.service.module.a> m744a(String str) {
        com.xiaomi.push.ao.a(false);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ArrayList<com.xiaomi.push.service.module.a> arrayListA = a();
            ArrayList<com.xiaomi.push.service.module.a> arrayList = new ArrayList<>();
            Iterator<com.xiaomi.push.service.module.a> it = arrayListA.iterator();
            while (it.hasNext()) {
                com.xiaomi.push.service.module.a next = it.next();
                if (TextUtils.equals(next.b(), str)) {
                    arrayList.add(next);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
    }

    public synchronized boolean a(ArrayList<ContentValues> arrayList) {
        boolean z2;
        com.xiaomi.push.ao.a(false);
        if (arrayList == null || arrayList.size() <= 0) {
            return false;
        }
        try {
            SQLiteDatabase sQLiteDatabaseA = h.a(this.f1062a).a();
            sQLiteDatabaseA.beginTransaction();
            Iterator<ContentValues> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    break;
                }
                if (-1 == sQLiteDatabaseA.insert("geoMessage", null, it.next())) {
                    z2 = false;
                    break;
                }
            }
            if (z2) {
                sQLiteDatabaseA.setTransactionSuccessful();
            }
            sQLiteDatabaseA.endTransaction();
            h.a(this.f1062a).m743a();
            return z2;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return false;
        }
    }

    public synchronized int b(String str) {
        com.xiaomi.push.ao.a(false);
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            int iDelete = h.a(this.f1062a).a().delete("geoMessage", "geo_id = ?", new String[]{str});
            h.a(this.f1062a).m743a();
            return iDelete;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return 0;
        }
    }
}
