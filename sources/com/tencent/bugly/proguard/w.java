package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class w {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f17902a = false;

    /* renamed from: b, reason: collision with root package name */
    private static w f17903b;

    /* renamed from: c, reason: collision with root package name */
    private static x f17904c;

    public class a extends Thread {

        /* renamed from: b, reason: collision with root package name */
        private int f17906b = 4;

        /* renamed from: c, reason: collision with root package name */
        private v f17907c = null;

        /* renamed from: d, reason: collision with root package name */
        private String f17908d;

        /* renamed from: e, reason: collision with root package name */
        private ContentValues f17909e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f17910f;

        /* renamed from: g, reason: collision with root package name */
        private String[] f17911g;

        /* renamed from: h, reason: collision with root package name */
        private String f17912h;

        /* renamed from: i, reason: collision with root package name */
        private String[] f17913i;

        /* renamed from: j, reason: collision with root package name */
        private String f17914j;

        /* renamed from: k, reason: collision with root package name */
        private String f17915k;

        /* renamed from: l, reason: collision with root package name */
        private String f17916l;

        /* renamed from: m, reason: collision with root package name */
        private String f17917m;

        /* renamed from: n, reason: collision with root package name */
        private String f17918n;

        /* renamed from: o, reason: collision with root package name */
        private String[] f17919o;

        /* renamed from: p, reason: collision with root package name */
        private int f17920p;

        /* renamed from: q, reason: collision with root package name */
        private String f17921q;

        /* renamed from: r, reason: collision with root package name */
        private byte[] f17922r;

        public a() {
        }

        public final void a(int i2, String str, byte[] bArr) {
            this.f17920p = i2;
            this.f17921q = str;
            this.f17922r = bArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            switch (this.f17906b) {
                case 1:
                    w.this.a(this.f17908d, this.f17909e, this.f17907c);
                    break;
                case 2:
                    w.this.a(this.f17908d, this.f17918n, this.f17919o, this.f17907c);
                    break;
                case 3:
                    Cursor cursorA = w.this.a(this.f17910f, this.f17908d, this.f17911g, this.f17912h, this.f17913i, this.f17914j, this.f17915k, this.f17916l, this.f17917m, this.f17907c);
                    if (cursorA != null) {
                        cursorA.close();
                        break;
                    }
                    break;
                case 4:
                    w.this.a(this.f17920p, this.f17921q, this.f17922r, this.f17907c);
                    break;
                case 5:
                    w.this.a(this.f17920p, this.f17907c);
                    break;
                case 6:
                    w.this.a(this.f17920p, this.f17921q, this.f17907c);
                    break;
            }
        }
    }

    private w(Context context, List<o> list) {
        f17904c = new x(context, list);
    }

    private synchronized boolean b(y yVar) {
        ContentValues contentValuesD;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f17904c.getWritableDatabase();
            if (writableDatabase == null || (contentValuesD = d(yVar)) == null) {
                return false;
            }
            long jReplace = writableDatabase.replace("t_pf", com.umeng.analytics.pro.aq.f22519d, contentValuesD);
            if (jReplace < 0) {
                if (f17902a) {
                    writableDatabase.close();
                }
                return false;
            }
            al.c("[Database] insert %s success.", "t_pf");
            yVar.f17927a = jReplace;
            if (f17902a) {
                writableDatabase.close();
            }
            return true;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f17902a && writableDatabase != null) {
                    writableDatabase.close();
                }
                return false;
            } finally {
                if (f17902a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
    }

    private static ContentValues c(y yVar) {
        if (yVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j2 = yVar.f17927a;
            if (j2 > 0) {
                contentValues.put(com.umeng.analytics.pro.aq.f22519d, Long.valueOf(j2));
            }
            contentValues.put(com.umeng.analytics.pro.aq.f22520e, Integer.valueOf(yVar.f17928b));
            contentValues.put("_pc", yVar.f17929c);
            contentValues.put("_th", yVar.f17930d);
            contentValues.put("_tm", Long.valueOf(yVar.f17931e));
            byte[] bArr = yVar.f17933g;
            if (bArr != null) {
                contentValues.put("_dt", bArr);
            }
            return contentValues;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static ContentValues d(y yVar) {
        if (yVar != null && !ap.b(yVar.f17932f)) {
            try {
                ContentValues contentValues = new ContentValues();
                long j2 = yVar.f17927a;
                if (j2 > 0) {
                    contentValues.put(com.umeng.analytics.pro.aq.f22519d, Long.valueOf(j2));
                }
                contentValues.put(com.umeng.analytics.pro.aq.f22520e, yVar.f17932f);
                contentValues.put("_tm", Long.valueOf(yVar.f17931e));
                byte[] bArr = yVar.f17933g;
                if (bArr != null) {
                    contentValues.put("_dt", bArr);
                }
                return contentValues;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static synchronized w a(Context context, List<o> list) {
        if (f17903b == null) {
            f17903b = new w(context, list);
        }
        return f17903b;
    }

    public static synchronized w a() {
        return f17903b;
    }

    public final Cursor a(String str, String[] strArr, String str2) {
        return a(str, strArr, str2, (String) null, (String) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ad A[Catch: all -> 0x00b1, PHI: r1
      0x00ad: PHI (r1v2 android.database.sqlite.SQLiteDatabase) = (r1v1 android.database.sqlite.SQLiteDatabase), (r1v4 android.database.sqlite.SQLiteDatabase) binds: [B:53:0x00c8, B:39:0x00ab] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #1 {, blocks: (B:9:0x0026, B:10:0x0029, B:12:0x002d, B:28:0x0096, B:30:0x009d, B:50:0x00c1, B:51:0x00c4, B:40:0x00ad, B:59:0x00d0, B:60:0x00d3, B:63:0x00d9, B:64:0x00dc, B:37:0x00a7, B:46:0x00b6, B:48:0x00bc), top: B:69:0x0002, inners: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized java.util.List<com.tencent.bugly.proguard.y> c(int r12) {
        /*
            Method dump skipped, instructions count: 223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.c(int):java.util.List");
    }

    public final Cursor a(String str, String[] strArr, String str2, String str3, String str4) {
        return a(false, str, strArr, str2, null, null, null, str3, str4, null);
    }

    public final int a(String str, String str2) {
        return a(str, str2, (String[]) null, (v) null);
    }

    public final synchronized long a(String str, ContentValues contentValues, v vVar) {
        long j2;
        j2 = -1;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f17904c.getWritableDatabase();
            if (writableDatabase != null && contentValues != null) {
                long jReplace = writableDatabase.replace(str, com.umeng.analytics.pro.aq.f22519d, contentValues);
                if (jReplace >= 0) {
                    al.c("[Database] insert %s success.", str);
                } else {
                    al.d("[Database] replace %s error.", str);
                }
                j2 = jReplace;
            }
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f17902a && 0 != 0) {
                }
            } finally {
                if (f17902a && 0 != 0) {
                    writableDatabase.close();
                }
            }
        }
        return j2;
    }

    public final synchronized void b(int i2) {
        String strConcat;
        SQLiteDatabase writableDatabase = f17904c.getWritableDatabase();
        if (writableDatabase != null) {
            if (i2 >= 0) {
                try {
                    strConcat = "_tp = ".concat(String.valueOf(i2));
                } catch (Throwable th) {
                    try {
                        if (!al.a(th)) {
                            th.printStackTrace();
                        }
                        if (f17902a) {
                            writableDatabase.close();
                            return;
                        }
                    } finally {
                        if (f17902a) {
                            writableDatabase.close();
                        }
                    }
                }
            } else {
                strConcat = null;
            }
            al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", strConcat, null)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Cursor a(boolean z2, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, v vVar) {
        Cursor cursorQuery;
        cursorQuery = null;
        try {
            SQLiteDatabase writableDatabase = f17904c.getWritableDatabase();
            if (writableDatabase != null) {
                cursorQuery = writableDatabase.query(z2, str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
        } finally {
            try {
                return cursorQuery;
            } finally {
            }
        }
        return cursorQuery;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized int a(String str, String str2, String[] strArr, v vVar) {
        int iDelete;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f17904c.getWritableDatabase();
            iDelete = writableDatabase != null ? writableDatabase.delete(str, str2, strArr) : 0;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f17902a && writableDatabase != null) {
                }
            } finally {
                if (f17902a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
        return iDelete;
    }

    private static y b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.f17927a = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.aq.f22519d));
            yVar.f17931e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f17932f = cursor.getString(cursor.getColumnIndex(com.umeng.analytics.pro.aq.f22520e));
            yVar.f17933g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final boolean a(int i2, String str, byte[] bArr, boolean z2) {
        if (!z2) {
            a aVar = new a();
            aVar.a(i2, str, bArr);
            ak.a().a(aVar);
            return true;
        }
        return a(i2, str, bArr, (v) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i2, String str, byte[] bArr, v vVar) {
        try {
            y yVar = new y();
            yVar.f17927a = i2;
            yVar.f17932f = str;
            yVar.f17931e = System.currentTimeMillis();
            yVar.f17933g = bArr;
            return b(yVar);
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public final Map<String, byte[]> a(int i2, v vVar) {
        HashMap map = null;
        try {
            List<y> listC = c(i2);
            if (listC == null) {
                return null;
            }
            HashMap map2 = new HashMap();
            try {
                for (y yVar : listC) {
                    byte[] bArr = yVar.f17933g;
                    if (bArr != null) {
                        map2.put(yVar.f17932f, bArr);
                    }
                }
                return map2;
            } catch (Throwable th) {
                th = th;
                map = map2;
                if (al.a(th)) {
                    return map;
                }
                th.printStackTrace();
                return map;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final synchronized boolean a(y yVar) {
        ContentValues contentValuesC;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f17904c.getWritableDatabase();
            if (writableDatabase == null || (contentValuesC = c(yVar)) == null) {
                return false;
            }
            long jReplace = writableDatabase.replace("t_lr", com.umeng.analytics.pro.aq.f22519d, contentValuesC);
            if (jReplace >= 0) {
                al.c("[Database] insert %s success.", "t_lr");
                yVar.f17927a = jReplace;
                if (f17902a) {
                    writableDatabase.close();
                }
                return true;
            }
            if (f17902a) {
                writableDatabase.close();
            }
            return false;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f17902a && writableDatabase != null) {
                    writableDatabase.close();
                }
                return false;
            } finally {
                if (f17902a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b0 A[Catch: all -> 0x00c0, TRY_LEAVE, TryCatch #0 {all -> 0x00c0, blocks: (B:40:0x00aa, B:42:0x00b0), top: B:60:0x00aa, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b5 A[Catch: all -> 0x00d0, TRY_ENTER, TryCatch #1 {, blocks: (B:3:0x0001, B:14:0x002e, B:15:0x0031, B:17:0x0035, B:33:0x009a, B:35:0x00a1, B:44:0x00b5, B:45:0x00b8, B:47:0x00bc, B:50:0x00c3, B:51:0x00c6, B:53:0x00ca, B:54:0x00cd, B:40:0x00aa, B:42:0x00b0), top: B:62:0x0001, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bc A[Catch: all -> 0x00d0, TryCatch #1 {, blocks: (B:3:0x0001, B:14:0x002e, B:15:0x0031, B:17:0x0035, B:33:0x009a, B:35:0x00a1, B:44:0x00b5, B:45:0x00b8, B:47:0x00bc, B:50:0x00c3, B:51:0x00c6, B:53:0x00ca, B:54:0x00cd, B:40:0x00aa, B:42:0x00b0), top: B:62:0x0001, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.util.List<com.tencent.bugly.proguard.y> a(int r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            com.tencent.bugly.proguard.x r0 = com.tencent.bugly.proguard.w.f17904c     // Catch: java.lang.Throwable -> Ld0
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch: java.lang.Throwable -> Ld0
            r9 = 0
            if (r0 == 0) goto Lce
            if (r12 < 0) goto L1c
            java.lang.String r1 = "_tp = "
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch: java.lang.Throwable -> L18
            java.lang.String r12 = r1.concat(r12)     // Catch: java.lang.Throwable -> L18
            r4 = r12
            goto L1d
        L18:
            r12 = move-exception
            r1 = r9
            goto Laa
        L1c:
            r4 = r9
        L1d:
            java.lang.String r2 = "t_lr"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r0
            android.database.Cursor r12 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L18
            if (r12 != 0) goto L3a
            if (r12 == 0) goto L31
            r12.close()     // Catch: java.lang.Throwable -> Ld0
        L31:
            boolean r12 = com.tencent.bugly.proguard.w.f17902a     // Catch: java.lang.Throwable -> Ld0
            if (r12 == 0) goto L38
            r0.close()     // Catch: java.lang.Throwable -> Ld0
        L38:
            monitor-exit(r11)
            return r9
        L3a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La6
            r1.<init>()     // Catch: java.lang.Throwable -> La6
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La6
            r2.<init>()     // Catch: java.lang.Throwable -> La6
        L44:
            boolean r3 = r12.moveToNext()     // Catch: java.lang.Throwable -> La6
            r4 = 0
            if (r3 == 0) goto L70
            com.tencent.bugly.proguard.y r3 = a(r12)     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto L55
            r2.add(r3)     // Catch: java.lang.Throwable -> La6
            goto L44
        L55:
            java.lang.String r3 = "_id"
            int r3 = r12.getColumnIndex(r3)     // Catch: java.lang.Throwable -> L68
            long r5 = r12.getLong(r3)     // Catch: java.lang.Throwable -> L68
            java.lang.String r3 = " or _id = "
            r1.append(r3)     // Catch: java.lang.Throwable -> L68
            r1.append(r5)     // Catch: java.lang.Throwable -> L68
            goto L44
        L68:
            java.lang.String r3 = "[Database] unknown id."
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> La6
            com.tencent.bugly.proguard.al.d(r3, r4)     // Catch: java.lang.Throwable -> La6
            goto L44
        L70:
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> La6
            int r3 = r1.length()     // Catch: java.lang.Throwable -> La6
            if (r3 <= 0) goto L9a
            r3 = 4
            java.lang.String r1 = r1.substring(r3)     // Catch: java.lang.Throwable -> La6
            java.lang.String r3 = "t_lr"
            int r1 = r0.delete(r3, r1, r9)     // Catch: java.lang.Throwable -> La6
            java.lang.String r3 = "[Database] deleted %s illegal data %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> La6
            java.lang.String r6 = "t_lr"
            r5[r4] = r6     // Catch: java.lang.Throwable -> La6
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> La6
            r4 = 1
            r5[r4] = r1     // Catch: java.lang.Throwable -> La6
            com.tencent.bugly.proguard.al.d(r3, r5)     // Catch: java.lang.Throwable -> La6
        L9a:
            r12.close()     // Catch: java.lang.Throwable -> Ld0
            boolean r12 = com.tencent.bugly.proguard.w.f17902a     // Catch: java.lang.Throwable -> Ld0
            if (r12 == 0) goto La4
            r0.close()     // Catch: java.lang.Throwable -> Ld0
        La4:
            monitor-exit(r11)
            return r2
        La6:
            r1 = move-exception
            r10 = r1
            r1 = r12
            r12 = r10
        Laa:
            boolean r2 = com.tencent.bugly.proguard.al.a(r12)     // Catch: java.lang.Throwable -> Lc0
            if (r2 != 0) goto Lb3
            r12.printStackTrace()     // Catch: java.lang.Throwable -> Lc0
        Lb3:
            if (r1 == 0) goto Lb8
            r1.close()     // Catch: java.lang.Throwable -> Ld0
        Lb8:
            boolean r12 = com.tencent.bugly.proguard.w.f17902a     // Catch: java.lang.Throwable -> Ld0
            if (r12 == 0) goto Lce
            r0.close()     // Catch: java.lang.Throwable -> Ld0
            goto Lce
        Lc0:
            r12 = move-exception
            if (r1 == 0) goto Lc6
            r1.close()     // Catch: java.lang.Throwable -> Ld0
        Lc6:
            boolean r1 = com.tencent.bugly.proguard.w.f17902a     // Catch: java.lang.Throwable -> Ld0
            if (r1 == 0) goto Lcd
            r0.close()     // Catch: java.lang.Throwable -> Ld0
        Lcd:
            throw r12     // Catch: java.lang.Throwable -> Ld0
        Lce:
            monitor-exit(r11)
            return r9
        Ld0:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.a(int):java.util.List");
    }

    public final synchronized void a(List<y> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = f17904c.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder sb = new StringBuilder();
                    for (y yVar : list) {
                        sb.append(" or _id = ");
                        sb.append(yVar.f17927a);
                    }
                    String string = sb.toString();
                    if (string.length() > 0) {
                        string = string.substring(4);
                    }
                    sb.setLength(0);
                    try {
                        al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", string, null)));
                    } catch (Throwable th) {
                        try {
                            if (!al.a(th)) {
                                th.printStackTrace();
                            }
                            if (f17902a) {
                                writableDatabase.close();
                            }
                        } finally {
                            if (f17902a) {
                                writableDatabase.close();
                            }
                        }
                    }
                }
            }
        }
    }

    private static y a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.f17927a = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.aq.f22519d));
            yVar.f17928b = cursor.getInt(cursor.getColumnIndex(com.umeng.analytics.pro.aq.f22520e));
            yVar.f17929c = cursor.getString(cursor.getColumnIndex("_pc"));
            yVar.f17930d = cursor.getString(cursor.getColumnIndex("_th"));
            yVar.f17931e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f17933g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean a(int i2, String str, v vVar) {
        boolean z2;
        String strConcat;
        z2 = false;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            SQLiteDatabase writableDatabase = f17904c.getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    if (ap.b(str)) {
                        strConcat = "_id = ".concat(String.valueOf(i2));
                    } else {
                        strConcat = "_id = " + i2 + " and _tp = \"" + str + "\"";
                    }
                    int iDelete = writableDatabase.delete("t_pf", strConcat, null);
                    al.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(iDelete));
                    if (iDelete > 0) {
                        z2 = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = writableDatabase;
                    try {
                        if (!al.a(th)) {
                            th.printStackTrace();
                        }
                        return z2;
                    } finally {
                        if (f17902a && sQLiteDatabase != null) {
                            sQLiteDatabase.close();
                        }
                    }
                }
            }
            if (f17902a && writableDatabase != null) {
                writableDatabase.close();
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return z2;
    }
}
