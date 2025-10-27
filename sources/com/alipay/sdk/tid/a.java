package com.alipay.sdk.tid;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public final class a extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static final String f3334a = "msp.db";

    /* renamed from: b, reason: collision with root package name */
    private static final int f3335b = 1;

    /* renamed from: c, reason: collision with root package name */
    private WeakReference<Context> f3336c;

    public a(Context context) {
        super(context, f3334a, (SQLiteDatabase.CursorFactory) null, 1);
        this.f3336c = new WeakReference<>(context);
    }

    public static String c(String str, String str2) {
        return str + str2;
    }

    private void d(String str, String str2) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = getWritableDatabase();
            a(writableDatabase, str, str2, "", "");
            a(writableDatabase, c(str, str2));
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return;
            }
            writableDatabase.close();
        } catch (Exception unused) {
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return;
            }
            writableDatabase.close();
        } catch (Throwable th) {
            if (writableDatabase != null && writableDatabase.isOpen()) {
                writableDatabase.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003e A[PHI: r1 r4
      0x003e: PHI (r1v2 long) = (r1v0 long), (r1v3 long) binds: [B:28:0x0063, B:10:0x003c] A[DONT_GENERATE, DONT_INLINE]
      0x003e: PHI (r4v4 android.database.sqlite.SQLiteDatabase) = (r4v3 android.database.sqlite.SQLiteDatabase), (r4v5 android.database.sqlite.SQLiteDatabase) binds: [B:28:0x0063, B:10:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long e(java.lang.String r7, java.lang.String r8) throws java.lang.Throwable {
        /*
            r6 = this;
            java.lang.String r0 = "select dt from tb_tid where name=?"
            r1 = 0
            r3 = 0
            android.database.sqlite.SQLiteDatabase r4 = r6.getReadableDatabase()     // Catch: java.lang.Throwable -> L44 java.lang.Exception -> L57
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            java.lang.String r7 = c(r7, r8)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            r8 = 0
            r5[r8] = r7     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            android.database.Cursor r3 = r4.rawQuery(r0, r5)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            boolean r7 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            if (r7 == 0) goto L35
            java.text.SimpleDateFormat r7 = new java.text.SimpleDateFormat     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            java.lang.String r0 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r5 = java.util.Locale.getDefault()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            r7.<init>(r0, r5)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            java.lang.String r8 = r3.getString(r8)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            java.util.Date r7 = r7.parse(r8)     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            long r7 = r7.getTime()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L58
            r1 = r7
        L35:
            r3.close()
            boolean r7 = r4.isOpen()
            if (r7 == 0) goto L66
        L3e:
            r4.close()
            goto L66
        L42:
            r7 = move-exception
            goto L46
        L44:
            r7 = move-exception
            r4 = r3
        L46:
            if (r3 == 0) goto L4b
            r3.close()
        L4b:
            if (r4 == 0) goto L56
            boolean r8 = r4.isOpen()
            if (r8 == 0) goto L56
            r4.close()
        L56:
            throw r7
        L57:
            r4 = r3
        L58:
            if (r3 == 0) goto L5d
            r3.close()
        L5d:
            if (r4 == 0) goto L66
            boolean r7 = r4.isOpen()
            if (r7 == 0) goto L66
            goto L3e
        L66:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.e(java.lang.String, java.lang.String):long");
    }

    public final void a(String str, String str2, String str3, String str4) throws Throwable {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            try {
                if (a(writableDatabase, str, str2)) {
                    a(writableDatabase, str, str2, str3, str4);
                } else {
                    String strA = com.alipay.sdk.encrypt.b.a(1, str3, com.alipay.sdk.util.a.c(this.f3336c.get()));
                    String strC = c(str, str2);
                    writableDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{strC, strA, str4});
                    Cursor cursorRawQuery = writableDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
                    if (cursorRawQuery.getCount() <= 14) {
                        cursorRawQuery.close();
                    } else {
                        int count = cursorRawQuery.getCount() - 14;
                        String[] strArr = new String[count];
                        if (cursorRawQuery.moveToFirst()) {
                            int i2 = 0;
                            do {
                                strArr[i2] = cursorRawQuery.getString(0);
                                i2++;
                                if (!cursorRawQuery.moveToNext()) {
                                    break;
                                }
                            } while (count > i2);
                        }
                        cursorRawQuery.close();
                        for (int i3 = 0; i3 < count; i3++) {
                            if (!TextUtils.isEmpty(strArr[i3])) {
                                a(writableDatabase, strArr[i3]);
                            }
                        }
                    }
                }
                if (writableDatabase == null || !writableDatabase.isOpen()) {
                    return;
                }
                writableDatabase.close();
            } catch (Exception unused) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                    return;
                }
                sQLiteDatabase.close();
            } catch (Throwable th) {
                th = th;
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0028 A[PHI: r1 r2
      0x0028: PHI (r1v4 java.lang.String) = (r1v13 java.lang.String), (r1v7 java.lang.String) binds: [B:31:0x0053, B:10:0x0026] A[DONT_GENERATE, DONT_INLINE]
      0x0028: PHI (r2v4 android.database.sqlite.SQLiteDatabase) = (r2v3 android.database.sqlite.SQLiteDatabase), (r2v5 android.database.sqlite.SQLiteDatabase) binds: [B:31:0x0053, B:10:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String b(java.lang.String r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            r4 = this;
            java.lang.String r0 = "select key_tid from tb_tid where name=?"
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r4.getReadableDatabase()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L46
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            java.lang.String r5 = c(r5, r6)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r6 = 0
            r3[r6] = r5     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            android.database.Cursor r5 = r2.rawQuery(r0, r3)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            boolean r0 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L48
            if (r0 == 0) goto L1f
            java.lang.String r1 = r5.getString(r6)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L48
        L1f:
            r5.close()
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L56
        L28:
            r2.close()
            goto L56
        L2c:
            r6 = move-exception
            r1 = r5
            goto L35
        L2f:
            r6 = move-exception
            goto L35
        L31:
            r5 = r1
            goto L48
        L33:
            r6 = move-exception
            r2 = r1
        L35:
            if (r1 == 0) goto L3a
            r1.close()
        L3a:
            if (r2 == 0) goto L45
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L45
            r2.close()
        L45:
            throw r6
        L46:
            r5 = r1
            r2 = r5
        L48:
            if (r5 == 0) goto L4d
            r5.close()
        L4d:
            if (r2 == 0) goto L56
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L56
            goto L28
        L56:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.b(java.lang.String, java.lang.String):java.lang.String");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL("create table if not exists tb_tid (name text primary key, tid text, key_tid text, dt datetime);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        sQLiteDatabase.execSQL("drop table if exists tb_tid");
        onCreate(sQLiteDatabase);
    }

    private void b(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, SQLException {
        String strA = com.alipay.sdk.encrypt.b.a(1, str3, com.alipay.sdk.util.a.c(this.f3336c.get()));
        String strC = c(str, str2);
        sQLiteDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{strC, strA, str4});
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
        if (cursorRawQuery.getCount() <= 14) {
            cursorRawQuery.close();
            return;
        }
        int count = cursorRawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (cursorRawQuery.moveToFirst()) {
            int i2 = 0;
            do {
                strArr[i2] = cursorRawQuery.getString(0);
                i2++;
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
            } while (count > i2);
        }
        cursorRawQuery.close();
        for (int i3 = 0; i3 < count; i3++) {
            if (!TextUtils.isEmpty(strArr[i3])) {
                a(sQLiteDatabase, strArr[i3]);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0028 A[PHI: r1 r2
      0x0028: PHI (r1v5 java.lang.String) = (r1v14 java.lang.String), (r1v8 java.lang.String) binds: [B:31:0x0053, B:10:0x0026] A[DONT_GENERATE, DONT_INLINE]
      0x0028: PHI (r2v4 android.database.sqlite.SQLiteDatabase) = (r2v3 android.database.sqlite.SQLiteDatabase), (r2v5 android.database.sqlite.SQLiteDatabase) binds: [B:31:0x0053, B:10:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a(java.lang.String r5, java.lang.String r6) throws java.lang.Throwable {
        /*
            r4 = this;
            java.lang.String r0 = "select tid from tb_tid where name=?"
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r4.getReadableDatabase()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L46
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            java.lang.String r5 = c(r5, r6)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r6 = 0
            r3[r6] = r5     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            android.database.Cursor r5 = r2.rawQuery(r0, r3)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            boolean r0 = r5.moveToFirst()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L48
            if (r0 == 0) goto L1f
            java.lang.String r1 = r5.getString(r6)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L48
        L1f:
            r5.close()
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L56
        L28:
            r2.close()
            goto L56
        L2c:
            r6 = move-exception
            r1 = r5
            goto L35
        L2f:
            r6 = move-exception
            goto L35
        L31:
            r5 = r1
            goto L48
        L33:
            r6 = move-exception
            r2 = r1
        L35:
            if (r1 == 0) goto L3a
            r1.close()
        L3a:
            if (r2 == 0) goto L45
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L45
            r2.close()
        L45:
            throw r6
        L46:
            r5 = r1
            r2 = r5
        L48:
            if (r5 == 0) goto L4d
            r5.close()
        L4d:
            if (r2 == 0) goto L56
            boolean r5 = r2.isOpen()
            if (r5 == 0) goto L56
            goto L28
        L56:
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 != 0) goto L6d
            java.lang.ref.WeakReference<android.content.Context> r5 = r4.f3336c
            java.lang.Object r5 = r5.get()
            android.content.Context r5 = (android.content.Context) r5
            java.lang.String r5 = com.alipay.sdk.util.a.c(r5)
            r6 = 2
            java.lang.String r1 = com.alipay.sdk.encrypt.b.a(r6, r1, r5)
        L6d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0063 A[PHI: r2
      0x0063: PHI (r2v4 android.database.sqlite.SQLiteDatabase) = (r2v3 android.database.sqlite.SQLiteDatabase), (r2v5 android.database.sqlite.SQLiteDatabase) binds: [B:30:0x0061, B:12:0x003d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<java.lang.String> a() throws java.lang.Throwable {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r6.getReadableDatabase()     // Catch: java.lang.Throwable -> L42 java.lang.Exception -> L55
            java.lang.String r3 = "select tid from tb_tid"
            android.database.Cursor r1 = r2.rawQuery(r3, r1)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
        L10:
            boolean r3 = r1.moveToNext()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            if (r3 == 0) goto L36
            r3 = 0
            java.lang.String r3 = r1.getString(r3)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            if (r4 != 0) goto L10
            java.lang.ref.WeakReference<android.content.Context> r4 = r6.f3336c     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            java.lang.Object r4 = r4.get()     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            android.content.Context r4 = (android.content.Context) r4     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            java.lang.String r4 = com.alipay.sdk.util.a.c(r4)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            r5 = 2
            java.lang.String r3 = com.alipay.sdk.encrypt.b.a(r5, r3, r4)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            r0.add(r3)     // Catch: java.lang.Throwable -> L40 java.lang.Exception -> L56
            goto L10
        L36:
            r1.close()
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L66
            goto L63
        L40:
            r0 = move-exception
            goto L44
        L42:
            r0 = move-exception
            r2 = r1
        L44:
            if (r1 == 0) goto L49
            r1.close()
        L49:
            if (r2 == 0) goto L54
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L54
            r2.close()
        L54:
            throw r0
        L55:
            r2 = r1
        L56:
            if (r1 == 0) goto L5b
            r1.close()
        L5b:
            if (r2 == 0) goto L66
            boolean r1 = r2.isOpen()
            if (r1 == 0) goto L66
        L63:
            r2.close()
        L66:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.a.a():java.util.List");
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        int i2;
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = sQLiteDatabase.rawQuery("select count(*) from tb_tid where name=?", new String[]{c(str, str2)});
            i2 = cursorRawQuery.moveToFirst() ? cursorRawQuery.getInt(0) : 0;
            cursorRawQuery.close();
        } catch (Exception unused) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            i2 = 0;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            throw th;
        }
        return i2 > 0;
    }

    public final void a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) throws SQLException {
        sQLiteDatabase.execSQL("update tb_tid set tid=?, key_tid=?, dt=datetime('now', 'localtime') where name=?", new Object[]{com.alipay.sdk.encrypt.b.a(1, str3, com.alipay.sdk.util.a.c(this.f3336c.get())), str4, c(str, str2)});
    }

    public static void a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.delete("tb_tid", "name=?", new String[]{str});
        } catch (Exception unused) {
        }
    }

    private static void a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
        if (cursorRawQuery.getCount() <= 14) {
            cursorRawQuery.close();
            return;
        }
        int count = cursorRawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (cursorRawQuery.moveToFirst()) {
            int i2 = 0;
            do {
                strArr[i2] = cursorRawQuery.getString(0);
                i2++;
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
            } while (count > i2);
        }
        cursorRawQuery.close();
        for (int i3 = 0; i3 < count; i3++) {
            if (!TextUtils.isEmpty(strArr[i3])) {
                a(sQLiteDatabase, strArr[i3]);
            }
        }
    }
}
