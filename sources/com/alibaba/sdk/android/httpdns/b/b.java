package com.alibaba.sdk.android.httpdns.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import com.alipay.sdk.cons.c;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends SQLiteOpenHelper {

    /* renamed from: d, reason: collision with root package name */
    private String f2709d;
    private Object lock;

    public b(Context context, String str) {
        super(context, "aliclound_httpdns_" + str + ".db", (SQLiteDatabase.CursorFactory) null, 1);
        this.lock = new Object();
        this.f2709d = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00e4 A[Catch: all -> 0x00ed, TryCatch #4 {, blocks: (B:4:0x0003, B:15:0x00b2, B:16:0x00b5, B:31:0x00e0, B:34:0x00e4, B:36:0x00e9, B:37:0x00ec, B:28:0x00da), top: B:44:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e9 A[Catch: all -> 0x00ed, TryCatch #4 {, blocks: (B:4:0x0003, B:15:0x00b2, B:16:0x00b5, B:31:0x00e0, B:34:0x00e4, B:36:0x00e9, B:37:0x00ec, B:28:0x00da), top: B:44:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<com.alibaba.sdk.android.httpdns.b.a> a() {
        /*
            r14 = this;
            java.lang.Object r0 = r14.lock
            monitor-enter(r0)
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Led
            r1.<init>()     // Catch: java.lang.Throwable -> Led
            com.alibaba.sdk.android.httpdns.net.c r2 = com.alibaba.sdk.android.httpdns.net.c.a()     // Catch: java.lang.Throwable -> Led
            java.lang.String r2 = r2.k()     // Catch: java.lang.Throwable -> Led
            r3 = 0
            android.database.sqlite.SQLiteDatabase r12 = r14.getReadableDatabase()     // Catch: java.lang.Throwable -> Lbd java.lang.Exception -> Lc0
            java.lang.String r5 = "host"
            r6 = 0
            java.lang.String r7 = "sp = ?"
            r13 = 1
            java.lang.String[] r8 = new java.lang.String[r13]     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r4 = 0
            r8[r4] = r2     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r9 = 0
            r10 = 0
            r11 = 0
            r4 = r12
            android.database.Cursor r3 = r4.query(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            if (r3 == 0) goto Lb0
            int r2 = r3.getCount()     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            if (r2 <= 0) goto Lb0
            r3.moveToFirst()     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
        L33:
            com.alibaba.sdk.android.httpdns.b.a r2 = new com.alibaba.sdk.android.httpdns.b.a     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.<init>()     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "id"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            long r4 = r3.getLong(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.b(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "host"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.setHost(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "ips"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String[] r4 = com.alibaba.sdk.android.httpdns.j.a.m56b(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.a(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "type"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            int r4 = r3.getInt(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.setType(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "ttl"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            int r4 = r3.getInt(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.a(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "time"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            long r4 = r3.getLong(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.a(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "extra"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.a(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = "cache_key"
            int r4 = r3.getColumnIndex(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            java.lang.String r4 = r3.getString(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.b(r4)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r2.a(r13)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            r1.add(r2)     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            boolean r2 = r3.moveToNext()     // Catch: java.lang.Throwable -> Lb9 java.lang.Exception -> Lbb
            if (r2 != 0) goto L33
        Lb0:
            if (r3 == 0) goto Lb5
            r3.close()     // Catch: java.lang.Throwable -> Led
        Lb5:
            r12.close()     // Catch: java.lang.Throwable -> Led
            goto Le0
        Lb9:
            r1 = move-exception
            goto Le2
        Lbb:
            r2 = move-exception
            goto Lc2
        Lbd:
            r1 = move-exception
            r12 = r3
            goto Le2
        Lc0:
            r2 = move-exception
            r12 = r3
        Lc2:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb9
            r4.<init>()     // Catch: java.lang.Throwable -> Lb9
            java.lang.String r5 = "read from db fail "
            r4.append(r5)     // Catch: java.lang.Throwable -> Lb9
            java.lang.String r5 = r14.f2709d     // Catch: java.lang.Throwable -> Lb9
            r4.append(r5)     // Catch: java.lang.Throwable -> Lb9
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> Lb9
            com.alibaba.sdk.android.httpdns.log.HttpDnsLog.w(r4, r2)     // Catch: java.lang.Throwable -> Lb9
            if (r3 == 0) goto Ldd
            r3.close()     // Catch: java.lang.Throwable -> Led
        Ldd:
            if (r12 == 0) goto Le0
            goto Lb5
        Le0:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Led
            return r1
        Le2:
            if (r3 == 0) goto Le7
            r3.close()     // Catch: java.lang.Throwable -> Led
        Le7:
            if (r12 == 0) goto Lec
            r12.close()     // Catch: java.lang.Throwable -> Led
        Lec:
            throw r1     // Catch: java.lang.Throwable -> Led
        Led:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Led
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.b.b.a():java.util.List");
    }

    public void a(List<a> list) {
        synchronized (this.lock) {
            SQLiteDatabase writableDatabase = null;
            try {
                try {
                    writableDatabase = getWritableDatabase();
                    writableDatabase.beginTransaction();
                    Iterator<a> it = list.iterator();
                    while (it.hasNext()) {
                        writableDatabase.delete(c.f3231f, "id = ? ", new String[]{String.valueOf(it.next().getId())});
                    }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                } catch (Throwable th) {
                    if (writableDatabase != null) {
                        writableDatabase.endTransaction();
                        writableDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                HttpDnsLog.w("delete record fail " + this.f2709d, e2);
                if (writableDatabase != null) {
                    writableDatabase.endTransaction();
                }
            }
            writableDatabase.close();
        }
    }

    public void b(List<a> list) {
        synchronized (this.lock) {
            String strK = com.alibaba.sdk.android.httpdns.net.c.a().k();
            SQLiteDatabase sQLiteDatabase = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = getWritableDatabase();
                    try {
                        writableDatabase.beginTransaction();
                        for (a aVar : list) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(c.f3231f, aVar.getHost());
                            contentValues.put("ips", com.alibaba.sdk.android.httpdns.j.a.a(aVar.getIps()));
                            contentValues.put("cache_key", aVar.m31a());
                            contentValues.put(PushConstants.EXTRA, aVar.getExtra());
                            contentValues.put(CrashHianalyticsData.TIME, Long.valueOf(aVar.m30a()));
                            contentValues.put("type", Integer.valueOf(aVar.getType()));
                            contentValues.put(RemoteMessageConst.TTL, Integer.valueOf(aVar.a()));
                            contentValues.put("sp", strK);
                            if (aVar.getId() != -1) {
                                writableDatabase.update(c.f3231f, contentValues, "id = ?", new String[]{String.valueOf(aVar.getId())});
                            } else {
                                aVar.b(writableDatabase.insert(c.f3231f, null, contentValues));
                            }
                        }
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                        writableDatabase.close();
                    } catch (Exception e2) {
                        e = e2;
                        sQLiteDatabase = writableDatabase;
                        HttpDnsLog.w("delete record fail " + this.f2709d, e);
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.endTransaction();
                            sQLiteDatabase.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        sQLiteDatabase = writableDatabase;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.endTransaction();
                            sQLiteDatabase.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE host (id INTEGER PRIMARY KEY,host TEXT,ips TEXT,type INTEGER,time INTEGER,ttl INTEGER,extra TEXT,cache_key TEXT,sp TEXT);");
        } catch (Exception e2) {
            HttpDnsLog.w("create db fail " + this.f2709d, e2);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        if (i2 != i3) {
            try {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS host;");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                onCreate(sQLiteDatabase);
            } catch (Exception e2) {
                HttpDnsLog.w("upgrade db fail " + this.f2709d, e2);
            }
        }
    }
}
