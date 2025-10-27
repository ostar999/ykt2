package com.umeng.analytics.pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.e;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    public static final int f22780a = 2049;

    /* renamed from: b, reason: collision with root package name */
    public static final int f22781b = 2050;

    /* renamed from: c, reason: collision with root package name */
    private static final int f22782c = 1000;

    /* renamed from: d, reason: collision with root package name */
    private static Context f22783d = null;

    /* renamed from: e, reason: collision with root package name */
    private static String f22784e = null;

    /* renamed from: f, reason: collision with root package name */
    private static final String f22785f = "umeng+";

    /* renamed from: g, reason: collision with root package name */
    private static final String f22786g = "ek__id";

    /* renamed from: h, reason: collision with root package name */
    private static final String f22787h = "ek_key";

    /* renamed from: i, reason: collision with root package name */
    private List<String> f22788i;

    /* renamed from: j, reason: collision with root package name */
    private List<Integer> f22789j;

    /* renamed from: k, reason: collision with root package name */
    private String f22790k;

    /* renamed from: l, reason: collision with root package name */
    private List<String> f22791l;

    public enum a {
        AUTOPAGE,
        PAGE,
        BEGIN,
        END,
        NEWSESSION,
        INSTANTSESSIONBEGIN
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        private static final i f22799a = new i();

        private b() {
        }
    }

    public static i a(Context context) {
        i iVar = b.f22799a;
        if (f22783d == null && context != null) {
            f22783d = context.getApplicationContext();
            iVar.k();
        }
        return iVar;
    }

    private void k() {
        synchronized (this) {
            l();
            this.f22788i.clear();
            this.f22791l.clear();
            this.f22789j.clear();
        }
    }

    private void l() {
        try {
            if (TextUtils.isEmpty(f22784e)) {
                String multiProcessSP = UMUtils.getMultiProcessSP(f22783d, f22786g);
                if (TextUtils.isEmpty(multiProcessSP)) {
                    multiProcessSP = PreferenceWrapper.getDefault(f22783d).getString(f22786g, null);
                    if (TextUtils.isEmpty(multiProcessSP)) {
                        multiProcessSP = UMUtils.genId();
                    }
                    if (!TextUtils.isEmpty(multiProcessSP)) {
                        UMUtils.setMultiProcessSP(f22783d, f22786g, multiProcessSP);
                    }
                }
                if (!TextUtils.isEmpty(multiProcessSP)) {
                    String strSubstring = multiProcessSP.substring(1, 9);
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                        char cCharAt = strSubstring.charAt(i2);
                        if (!Character.isDigit(cCharAt)) {
                            sb.append(cCharAt);
                        } else if (Integer.parseInt(Character.toString(cCharAt)) == 0) {
                            sb.append(0);
                        } else {
                            sb.append(10 - Integer.parseInt(Character.toString(cCharAt)));
                        }
                    }
                    f22784e = sb.toString();
                }
                if (TextUtils.isEmpty(f22784e)) {
                    return;
                }
                f22784e += new StringBuilder(f22784e).reverse().toString();
                String multiProcessSP2 = UMUtils.getMultiProcessSP(f22783d, f22787h);
                if (TextUtils.isEmpty(multiProcessSP2)) {
                    UMUtils.setMultiProcessSP(f22783d, f22787h, c(f22785f));
                } else {
                    if (f22785f.equals(d(multiProcessSP2))) {
                        return;
                    }
                    b(true, false);
                    a(true, false);
                    h();
                    i();
                }
            }
        } catch (Throwable unused) {
        }
    }

    public void b() {
        this.f22791l.clear();
    }

    public boolean c() {
        return this.f22791l.isEmpty();
    }

    public void d() {
        String strC;
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
                sQLiteDatabaseA.beginTransaction();
                strC = u.a().c();
            } catch (SQLiteDatabaseCorruptException unused) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
            if (TextUtils.isEmpty(strC)) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
                g.a(f22783d).b();
                return;
            }
            String[] strArr = {"", "-1"};
            for (int i2 = 0; i2 < 2; i2++) {
                sQLiteDatabaseA.execSQL("update __et set __i=\"" + strC + "\" where __i=\"" + strArr[i2] + "\"");
            }
            sQLiteDatabaseA.setTransactionSuccessful();
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused4) {
                }
            }
            g.a(f22783d).b();
        }
    }

    public boolean e() {
        return this.f22788i.isEmpty();
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0085 A[EXC_TOP_SPLITTER, PHI: r2 r5
      0x0085: PHI (r2v5 android.database.sqlite.SQLiteDatabase) = (r2v4 android.database.sqlite.SQLiteDatabase), (r2v6 android.database.sqlite.SQLiteDatabase) binds: [B:27:0x0083, B:34:0x009f] A[DONT_GENERATE, DONT_INLINE]
      0x0085: PHI (r5v3 org.json.JSONObject) = (r5v2 org.json.JSONObject), (r5v5 org.json.JSONObject) binds: [B:27:0x0083, B:34:0x009f] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject f() {
        /*
            r14 = this;
            java.lang.String r0 = "__vc"
            java.lang.String r1 = "__av"
            java.util.List<java.lang.String> r2 = r14.f22791l
            boolean r2 = r2.isEmpty()
            r3 = 0
            if (r2 == 0) goto Le
            return r3
        Le:
            android.content.Context r2 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L7c android.database.sqlite.SQLiteDatabaseCorruptException -> L93
            com.umeng.analytics.pro.g r2 = com.umeng.analytics.pro.g.a(r2)     // Catch: java.lang.Throwable -> L7c android.database.sqlite.SQLiteDatabaseCorruptException -> L93
            android.database.sqlite.SQLiteDatabase r2 = r2.a()     // Catch: java.lang.Throwable -> L7c android.database.sqlite.SQLiteDatabaseCorruptException -> L93
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            java.util.List<java.lang.String> r4 = r14.f22791l     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            r5 = 0
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            java.lang.String r6 = "__is"
            r7 = 0
            java.lang.String r8 = "__ii=? "
            r9 = 1
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            r9[r5] = r4     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r4 = r14
            r5 = r6
            r6 = r2
            android.database.Cursor r4 = r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            if (r4 == 0) goto L5d
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            if (r5 == 0) goto L5d
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            r5.<init>()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            int r3 = r4.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            java.lang.String r3 = r4.getString(r3)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            int r6 = r4.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            java.lang.String r6 = r4.getString(r6)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            r5.put(r1, r3)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            r5.put(r0, r6)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            r3 = r5
        L5d:
            r2.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            if (r4 == 0) goto L65
            r4.close()
        L65:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L68
        L68:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r0 = com.umeng.analytics.pro.g.a(r0)
            r0.b()
            goto La2
        L72:
            r5 = r3
        L73:
            r3 = r4
            goto L7e
        L75:
            r5 = r3
        L76:
            r3 = r4
            goto L95
        L78:
            r5 = r3
            goto L7e
        L7a:
            r5 = r3
            goto L95
        L7c:
            r2 = r3
            r5 = r2
        L7e:
            if (r3 == 0) goto L83
            r3.close()
        L83:
            if (r2 == 0) goto L88
        L85:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L88
        L88:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r0 = com.umeng.analytics.pro.g.a(r0)
            r0.b()
            r3 = r5
            goto La2
        L93:
            r2 = r3
            r5 = r2
        L95:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> La3
            com.umeng.analytics.pro.h.a(r0)     // Catch: java.lang.Throwable -> La3
            if (r3 == 0) goto L9f
            r3.close()
        L9f:
            if (r2 == 0) goto L88
            goto L85
        La2:
            return r3
        La3:
            r0 = move-exception
            if (r3 == 0) goto La9
            r3.close()
        La9:
            if (r2 == 0) goto Lae
            r2.endTransaction()     // Catch: java.lang.Throwable -> Lae
        Lae:
            android.content.Context r1 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r1 = com.umeng.analytics.pro.g.a(r1)
            r1.b()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.f():org.json.JSONObject");
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0085 A[EXC_TOP_SPLITTER, PHI: r2 r5
      0x0085: PHI (r2v5 android.database.sqlite.SQLiteDatabase) = (r2v4 android.database.sqlite.SQLiteDatabase), (r2v6 android.database.sqlite.SQLiteDatabase) binds: [B:27:0x0083, B:34:0x009f] A[DONT_GENERATE, DONT_INLINE]
      0x0085: PHI (r5v3 org.json.JSONObject) = (r5v2 org.json.JSONObject), (r5v5 org.json.JSONObject) binds: [B:27:0x0083, B:34:0x009f] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject g() {
        /*
            r14 = this;
            java.lang.String r0 = "__vc"
            java.lang.String r1 = "__av"
            java.util.List<java.lang.String> r2 = r14.f22788i
            boolean r2 = r2.isEmpty()
            r3 = 0
            if (r2 == 0) goto Le
            return r3
        Le:
            android.content.Context r2 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L7c android.database.sqlite.SQLiteDatabaseCorruptException -> L93
            com.umeng.analytics.pro.g r2 = com.umeng.analytics.pro.g.a(r2)     // Catch: java.lang.Throwable -> L7c android.database.sqlite.SQLiteDatabaseCorruptException -> L93
            android.database.sqlite.SQLiteDatabase r2 = r2.a()     // Catch: java.lang.Throwable -> L7c android.database.sqlite.SQLiteDatabaseCorruptException -> L93
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            java.util.List<java.lang.String> r4 = r14.f22788i     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            r5 = 0
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            java.lang.String r6 = "__sd"
            r7 = 0
            java.lang.String r8 = "__ii=? "
            r9 = 1
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            r9[r5] = r4     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r4 = r14
            r5 = r6
            r6 = r2
            android.database.Cursor r4 = r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L7a
            if (r4 == 0) goto L5d
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            if (r5 == 0) goto L5d
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            r5.<init>()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            int r3 = r4.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            java.lang.String r3 = r4.getString(r3)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            int r6 = r4.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            java.lang.String r6 = r4.getString(r6)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            r5.put(r1, r3)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            r5.put(r0, r6)     // Catch: java.lang.Throwable -> L73 android.database.sqlite.SQLiteDatabaseCorruptException -> L76
            r3 = r5
        L5d:
            r2.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L72 android.database.sqlite.SQLiteDatabaseCorruptException -> L75
            if (r4 == 0) goto L65
            r4.close()
        L65:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L68
        L68:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r0 = com.umeng.analytics.pro.g.a(r0)
            r0.b()
            goto La2
        L72:
            r5 = r3
        L73:
            r3 = r4
            goto L7e
        L75:
            r5 = r3
        L76:
            r3 = r4
            goto L95
        L78:
            r5 = r3
            goto L7e
        L7a:
            r5 = r3
            goto L95
        L7c:
            r2 = r3
            r5 = r2
        L7e:
            if (r3 == 0) goto L83
            r3.close()
        L83:
            if (r2 == 0) goto L88
        L85:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L88
        L88:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r0 = com.umeng.analytics.pro.g.a(r0)
            r0.b()
            r3 = r5
            goto La2
        L93:
            r2 = r3
            r5 = r2
        L95:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> La3
            com.umeng.analytics.pro.h.a(r0)     // Catch: java.lang.Throwable -> La3
            if (r3 == 0) goto L9f
            r3.close()
        L9f:
            if (r2 == 0) goto L88
            goto L85
        La2:
            return r3
        La3:
            r0 = move-exception
            if (r3 == 0) goto La9
            r3.close()
        La9:
            if (r2 == 0) goto Lae
            r2.endTransaction()     // Catch: java.lang.Throwable -> Lae
        Lae:
            android.content.Context r1 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r1 = com.umeng.analytics.pro.g.a(r1)
            r1.b()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.g():org.json.JSONObject");
    }

    public void h() {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (this.f22789j.size() > 0) {
                    for (int i2 = 0; i2 < this.f22789j.size(); i2++) {
                        sQLiteDatabaseA.execSQL("delete from __et where rowid=" + this.f22789j.get(i2));
                    }
                }
                this.f22789j.clear();
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            g.a(f22783d).b();
        }
    }

    public void i() {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
                sQLiteDatabaseA.beginTransaction();
                sQLiteDatabaseA.execSQL("delete from __er");
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            g.a(f22783d).b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0051 A[EXC_TOP_SPLITTER, PHI: r1
      0x0051: PHI (r1v8 android.database.sqlite.SQLiteDatabase) = 
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v5 android.database.sqlite.SQLiteDatabase)
      (r1v11 android.database.sqlite.SQLiteDatabase)
     binds: [B:8:0x004f, B:13:0x0064, B:5:0x0015] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void j() {
        /*
            r5 = this;
            java.lang.String r0 = "\""
            java.lang.String r1 = r5.f22790k
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            r2 = 0
            if (r1 != 0) goto L77
            android.content.Context r1 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L4e android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            com.umeng.analytics.pro.g r1 = com.umeng.analytics.pro.g.a(r1)     // Catch: java.lang.Throwable -> L4e android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: java.lang.Throwable -> L4e android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r3.<init>()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.String r4 = "delete from __er where __i=\""
            r3.append(r4)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.String r4 = r5.f22790k     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r3.append(r4)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r3.append(r0)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r1.execSQL(r3)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r3.<init>()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.String r4 = "delete from __et where __i=\""
            r3.append(r4)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.String r4 = r5.f22790k     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r3.append(r4)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r3.append(r0)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r1.execSQL(r0)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L5f
            goto L51
        L4e:
            r1 = r2
        L4f:
            if (r1 == 0) goto L54
        L51:
            r1.endTransaction()     // Catch: java.lang.Throwable -> L54
        L54:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r0 = com.umeng.analytics.pro.g.a(r0)
            r0.b()
            goto L77
        L5e:
            r1 = r2
        L5f:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L67
            com.umeng.analytics.pro.h.a(r0)     // Catch: java.lang.Throwable -> L67
            if (r1 == 0) goto L54
            goto L51
        L67:
            r0 = move-exception
            if (r1 == 0) goto L6d
            r1.endTransaction()     // Catch: java.lang.Throwable -> L6d
        L6d:
            android.content.Context r1 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r1 = com.umeng.analytics.pro.g.a(r1)
            r1.b()
            throw r0
        L77:
            r5.f22790k = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.j():void");
    }

    private i() {
        this.f22788i = new ArrayList();
        this.f22789j = new ArrayList();
        this.f22790k = null;
        this.f22791l = new ArrayList();
    }

    private void b(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        try {
            long jLongValue = ((Long) jSONObject.get("__e")).longValue();
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("__sp");
            JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("__pp");
            String strC = "";
            String strC2 = (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject.length() <= 0) ? "" : c(jSONObjectOptJSONObject.toString());
            if (jSONObjectOptJSONObject2 != null && jSONObjectOptJSONObject2.length() > 0) {
                strC = c(jSONObjectOptJSONObject2.toString());
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("__ii", str);
            contentValues.put("__e", String.valueOf(jLongValue));
            contentValues.put("__sp", strC2);
            contentValues.put("__pp", strC);
            contentValues.put("__av", UMGlobalContext.getInstance(f22783d).getAppVersion());
            contentValues.put("__vc", UMUtils.getAppVersionCode(f22783d));
            sQLiteDatabase.insert(e.c.f22734a, null, contentValues);
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(java.lang.String r20, org.json.JSONObject r21, android.database.sqlite.SQLiteDatabase r22) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.c(java.lang.String, org.json.JSONObject, android.database.sqlite.SQLiteDatabase):void");
    }

    private Cursor a(String str, SQLiteDatabase sQLiteDatabase, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        if (sQLiteDatabase == null) {
            return null;
        }
        try {
            if (sQLiteDatabase.isOpen()) {
                return sQLiteDatabase.query(str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public void a() {
        this.f22788i.clear();
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0098, code lost:
    
        if (r2 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x009a, code lost:
    
        r2.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00ac, code lost:
    
        if (r2 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(org.json.JSONArray r11) {
        /*
            r10 = this;
            java.lang.String r0 = "__t"
            java.lang.String r1 = "__i"
            r2 = 0
            android.content.Context r3 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L98 android.database.sqlite.SQLiteDatabaseCorruptException -> La7
            com.umeng.analytics.pro.g r3 = com.umeng.analytics.pro.g.a(r3)     // Catch: java.lang.Throwable -> L98 android.database.sqlite.SQLiteDatabaseCorruptException -> La7
            android.database.sqlite.SQLiteDatabase r3 = r3.a()     // Catch: java.lang.Throwable -> L98 android.database.sqlite.SQLiteDatabaseCorruptException -> La7
            r3.beginTransaction()     // Catch: java.lang.Throwable -> L94 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            r4 = 0
        L13:
            int r5 = r11.length()     // Catch: java.lang.Throwable -> L94 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            if (r4 >= r5) goto L8d
            org.json.JSONObject r5 = r11.getJSONObject(r4)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r6.<init>()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r7 = r5.optString(r1)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r9 = "-1"
            if (r8 != 0) goto L34
            boolean r8 = r9.equals(r7)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            if (r8 == 0) goto L43
        L34:
            com.umeng.analytics.pro.u r7 = com.umeng.analytics.pro.u.a()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r7 = r7.b()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            if (r8 == 0) goto L43
            r7 = r9
        L43:
            r6.put(r1, r7)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r7 = "__e"
            java.lang.String r8 = "id"
            java.lang.String r8 = r5.optString(r8)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r6.put(r7, r8)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            int r7 = r5.optInt(r0)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r6.put(r0, r7)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r7 = "__av"
            android.content.Context r8 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r8)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r6.put(r7, r8)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r7 = "__vc"
            android.content.Context r8 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r8)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r6.put(r7, r8)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r5.remove(r1)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r5.remove(r0)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r7 = "__s"
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r5 = r10.c(r5)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            r6.put(r7, r5)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
            java.lang.String r5 = "__et"
            r3.insert(r5, r2, r6)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> L94
        L8a:
            int r4 = r4 + 1
            goto L13
        L8d:
            r3.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L94 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            r3.endTransaction()     // Catch: java.lang.Throwable -> L9d
            goto L9d
        L94:
            r2 = r3
            goto L98
        L96:
            r2 = r3
            goto La7
        L98:
            if (r2 == 0) goto L9d
        L9a:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L9d
        L9d:
            android.content.Context r11 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r11 = com.umeng.analytics.pro.g.a(r11)
            r11.b()
            goto Laf
        La7:
            android.content.Context r11 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> Lb0
            com.umeng.analytics.pro.h.a(r11)     // Catch: java.lang.Throwable -> Lb0
            if (r2 == 0) goto L9d
            goto L9a
        Laf:
            return
        Lb0:
            r11 = move-exception
            if (r2 == 0) goto Lb6
            r2.endTransaction()     // Catch: java.lang.Throwable -> Lb6
        Lb6:
            android.content.Context r0 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r0 = com.umeng.analytics.pro.g.a(r0)
            r0.b()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.a(org.json.JSONArray):void");
    }

    public String d(String str) {
        try {
            return TextUtils.isEmpty(f22784e) ? str : new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f22784e.getBytes()));
        } catch (Exception unused) {
            if (Build.VERSION.SDK_INT >= 29 && !TextUtils.isEmpty(str)) {
                try {
                    new JSONObject(str);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> UMStoreManager decrypt failed, return origin data.");
                    return str;
                } catch (Throwable unused2) {
                    return null;
                }
            }
            return null;
        }
    }

    public JSONObject b(boolean z2) {
        JSONObject jSONObject = new JSONObject();
        b(jSONObject, z2);
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0096 A[PHI: r0 r1
      0x0096: PHI (r0v4 android.database.Cursor) = (r0v2 android.database.Cursor), (r0v3 android.database.Cursor), (r0v5 android.database.Cursor) binds: [B:26:0x0083, B:32:0x0091, B:33:0x0093] A[DONT_GENERATE, DONT_INLINE]
      0x0096: PHI (r1v5 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v6 android.database.sqlite.SQLiteDatabase)
     binds: [B:26:0x0083, B:32:0x0091, B:33:0x0093] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0093 A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x0093: PHI (r0v5 android.database.Cursor) = 
      (r0v2 android.database.Cursor)
      (r0v3 android.database.Cursor)
      (r0v8 android.database.Cursor)
      (r0v8 android.database.Cursor)
     binds: [B:26:0x0083, B:32:0x0091, B:20:0x0072, B:21:0x0074] A[DONT_GENERATE, DONT_INLINE]
      0x0093: PHI (r1v6 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
     binds: [B:26:0x0083, B:32:0x0091, B:20:0x0072, B:21:0x0074] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(org.json.JSONObject r13, java.lang.String r14) {
        /*
            r12 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L86
            com.umeng.analytics.pro.g r1 = com.umeng.analytics.pro.g.a(r1)     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L86
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: java.lang.Throwable -> L78 android.database.sqlite.SQLiteDatabaseCorruptException -> L86
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            boolean r2 = android.text.TextUtils.isEmpty(r14)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            if (r2 != 0) goto L2a
            java.lang.String r3 = "__er"
            r5 = 0
            java.lang.String r6 = "__i=? "
            r2 = 1
            java.lang.String[] r7 = new java.lang.String[r2]     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            r2 = 0
            r7[r2] = r14     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r2 = r12
            r4 = r1
            android.database.Cursor r14 = r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            goto L39
        L2a:
            java.lang.String r3 = "__er"
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r2 = r12
            r4 = r1
            android.database.Cursor r14 = r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
        L39:
            r0 = r14
            if (r0 == 0) goto L6f
            org.json.JSONArray r14 = new org.json.JSONArray     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            r14.<init>()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
        L41:
            boolean r2 = r0.moveToNext()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            if (r2 == 0) goto L64
            java.lang.String r2 = "__a"
            int r2 = r0.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            java.lang.String r2 = r0.getString(r2)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            if (r3 != 0) goto L41
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            java.lang.String r2 = r12.d(r2)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            r14.put(r3)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            goto L41
        L64:
            int r2 = r14.length()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            if (r2 <= 0) goto L6f
            java.lang.String r2 = "error"
            r13.put(r2, r14)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
        L6f:
            r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L87
            if (r0 == 0) goto L93
            r0.close()
            goto L93
        L78:
            r1 = r0
        L79:
            android.content.Context r13 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> La0
            com.umeng.analytics.pro.h.a(r13)     // Catch: java.lang.Throwable -> La0
            if (r0 == 0) goto L83
            r0.close()
        L83:
            if (r1 == 0) goto L96
            goto L93
        L86:
            r1 = r0
        L87:
            android.content.Context r13 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> La0
            com.umeng.analytics.pro.h.a(r13)     // Catch: java.lang.Throwable -> La0
            if (r0 == 0) goto L91
            r0.close()
        L91:
            if (r1 == 0) goto L96
        L93:
            r1.endTransaction()     // Catch: java.lang.Throwable -> L96
        L96:
            android.content.Context r13 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r13 = com.umeng.analytics.pro.g.a(r13)
            r13.b()
            return
        La0:
            r13 = move-exception
            if (r0 == 0) goto La6
            r0.close()
        La6:
            if (r1 == 0) goto Lab
            r1.endTransaction()     // Catch: java.lang.Throwable -> Lab
        Lab:
            android.content.Context r14 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r14 = com.umeng.analytics.pro.g.a(r14)
            r14.b()
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.b(org.json.JSONObject, java.lang.String):void");
    }

    public String c(String str) {
        try {
            return TextUtils.isEmpty(f22784e) ? str : Base64.encodeToString(DataHelper.encrypt(str.getBytes(), f22784e.getBytes()), 0);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0056, code lost:
    
        if (r0 == null) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0058, code lost:
    
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x006a, code lost:
    
        if (r0 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r5, java.lang.String r6, int r7) {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L56 android.database.sqlite.SQLiteDatabaseCorruptException -> L65
            com.umeng.analytics.pro.g r1 = com.umeng.analytics.pro.g.a(r1)     // Catch: java.lang.Throwable -> L56 android.database.sqlite.SQLiteDatabaseCorruptException -> L65
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: java.lang.Throwable -> L56 android.database.sqlite.SQLiteDatabaseCorruptException -> L65
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            r2.<init>()     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r3 = "__i"
            r2.put(r3, r5)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r5 = r4.c(r6)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            if (r6 != 0) goto L4b
            java.lang.String r6 = "__a"
            r2.put(r6, r5)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r5 = "__t"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r5 = "__av"
            android.content.Context r6 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r6)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r5 = "__vc"
            android.content.Context r6 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r6)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            java.lang.String r5 = "__er"
            r1.insert(r5, r0, r2)     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
        L4b:
            r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L52 android.database.sqlite.SQLiteDatabaseCorruptException -> L54
            r1.endTransaction()     // Catch: java.lang.Throwable -> L5b
            goto L5b
        L52:
            r0 = r1
            goto L56
        L54:
            r0 = r1
            goto L65
        L56:
            if (r0 == 0) goto L5b
        L58:
            r0.endTransaction()     // Catch: java.lang.Throwable -> L5b
        L5b:
            android.content.Context r5 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r5 = com.umeng.analytics.pro.g.a(r5)
            r5.b()
            goto L6d
        L65:
            android.content.Context r5 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L6f
            com.umeng.analytics.pro.h.a(r5)     // Catch: java.lang.Throwable -> L6f
            if (r0 == 0) goto L5b
            goto L58
        L6d:
            r5 = 0
            return r5
        L6f:
            r5 = move-exception
            if (r0 == 0) goto L75
            r0.endTransaction()     // Catch: java.lang.Throwable -> L75
        L75:
            android.content.Context r6 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r6 = com.umeng.analytics.pro.g.a(r6)
            r6.b()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.a(java.lang.String, java.lang.String, int):boolean");
    }

    private JSONArray b(JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
            if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.optLong("duration") > 0) {
                jSONArray2.put(jSONObjectOptJSONObject);
            }
        }
        return jSONArray2;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00e8 A[EXC_TOP_SPLITTER, PHI: r1 r13
      0x00e8: PHI (r1v4 android.database.sqlite.SQLiteDatabase) = (r1v3 android.database.sqlite.SQLiteDatabase), (r1v6 android.database.sqlite.SQLiteDatabase) binds: [B:37:0x00d7, B:43:0x00e6] A[DONT_GENERATE, DONT_INLINE]
      0x00e8: PHI (r13v5 java.lang.Object) = (r13v4 java.lang.Object), (r13v7 java.lang.Object) binds: [B:37:0x00d7, B:43:0x00e6] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r13v0, types: [org.json.JSONObject] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(org.json.JSONObject r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.b(org.json.JSONObject, boolean):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0085, code lost:
    
        if (r2 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0087, code lost:
    
        r2.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0099, code lost:
    
        if (r2 == null) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r7, org.json.JSONObject r8, com.umeng.analytics.pro.i.a r9) {
        /*
            r6 = this;
            java.lang.String r0 = "__e"
            r1 = 0
            if (r8 != 0) goto L6
            return r1
        L6:
            r2 = 0
            android.content.Context r3 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L85 android.database.sqlite.SQLiteDatabaseCorruptException -> L94
            com.umeng.analytics.pro.g r3 = com.umeng.analytics.pro.g.a(r3)     // Catch: java.lang.Throwable -> L85 android.database.sqlite.SQLiteDatabaseCorruptException -> L94
            android.database.sqlite.SQLiteDatabase r3 = r3.a()     // Catch: java.lang.Throwable -> L85 android.database.sqlite.SQLiteDatabaseCorruptException -> L94
            r3.beginTransaction()     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            com.umeng.analytics.pro.i$a r4 = com.umeng.analytics.pro.i.a.BEGIN     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            if (r9 != r4) goto L4f
            java.lang.Object r8 = r8.opt(r0)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.Long r8 = (java.lang.Long) r8     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            long r8 = r8.longValue()     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            r4.<init>()     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r5 = "__ii"
            r4.put(r5, r7)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r7 = java.lang.String.valueOf(r8)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            r4.put(r0, r7)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r7 = "__av"
            android.content.Context r8 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r8)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            r4.put(r7, r8)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r7 = "__vc"
            android.content.Context r8 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r8)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            r4.put(r7, r8)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            java.lang.String r7 = "__sd"
            r3.insert(r7, r2, r4)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            goto L7a
        L4f:
            com.umeng.analytics.pro.i$a r0 = com.umeng.analytics.pro.i.a.INSTANTSESSIONBEGIN     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            if (r9 != r0) goto L57
            r6.b(r7, r8, r3)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            goto L7a
        L57:
            com.umeng.analytics.pro.i$a r0 = com.umeng.analytics.pro.i.a.END     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            if (r9 != r0) goto L5f
            r6.a(r7, r8, r3)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            goto L7a
        L5f:
            com.umeng.analytics.pro.i$a r0 = com.umeng.analytics.pro.i.a.PAGE     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            if (r9 != r0) goto L69
            java.lang.String r9 = "__a"
            r6.a(r7, r8, r3, r9)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            goto L7a
        L69:
            com.umeng.analytics.pro.i$a r0 = com.umeng.analytics.pro.i.a.AUTOPAGE     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            if (r9 != r0) goto L73
            java.lang.String r9 = "__b"
            r6.a(r7, r8, r3, r9)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            goto L7a
        L73:
            com.umeng.analytics.pro.i$a r0 = com.umeng.analytics.pro.i.a.NEWSESSION     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            if (r9 != r0) goto L7a
            r6.c(r7, r8, r3)     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
        L7a:
            r3.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L81 android.database.sqlite.SQLiteDatabaseCorruptException -> L83
            r3.endTransaction()     // Catch: java.lang.Throwable -> L8a
            goto L8a
        L81:
            r2 = r3
            goto L85
        L83:
            r2 = r3
            goto L94
        L85:
            if (r2 == 0) goto L8a
        L87:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L8a
        L8a:
            android.content.Context r7 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r7 = com.umeng.analytics.pro.g.a(r7)
            r7.b()
            goto L9c
        L94:
            android.content.Context r7 = com.umeng.analytics.pro.i.f22783d     // Catch: java.lang.Throwable -> L9d
            com.umeng.analytics.pro.h.a(r7)     // Catch: java.lang.Throwable -> L9d
            if (r2 == 0) goto L8a
            goto L87
        L9c:
            return r1
        L9d:
            r7 = move-exception
            if (r2 == 0) goto La3
            r2.endTransaction()     // Catch: java.lang.Throwable -> La3
        La3:
            android.content.Context r8 = com.umeng.analytics.pro.i.f22783d
            com.umeng.analytics.pro.g r8 = com.umeng.analytics.pro.g.a(r8)
            r8.b()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.a(java.lang.String, org.json.JSONObject, com.umeng.analytics.pro.i$a):boolean");
    }

    private void a(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        try {
            long jLongValue = ((Long) jSONObject.opt(e.d.a.f22754g)).longValue();
            Object objOpt = jSONObject.opt(e.d.a.f22755h);
            long jLongValue2 = (objOpt == null || !(objOpt instanceof Long)) ? 0L : ((Long) objOpt).longValue();
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("__sp");
            JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("__pp");
            String strC = "";
            String strC2 = (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject.length() <= 0) ? "" : c(jSONObjectOptJSONObject.toString());
            if (jSONObjectOptJSONObject2 != null && jSONObjectOptJSONObject2.length() > 0) {
                strC = c(jSONObjectOptJSONObject2.toString());
            }
            sQLiteDatabase.execSQL("update __sd set __f=\"" + jLongValue + "\", " + e.d.a.f22755h + "=\"" + jLongValue2 + "\", __sp=\"" + strC2 + "\", __pp=\"" + strC + "\" where __ii=\"" + str + "\"");
        } catch (Throwable unused) {
        }
    }

    public void b(boolean z2, boolean z3) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (z3) {
                    if (z2) {
                        sQLiteDatabaseA.execSQL("delete from __sd");
                    }
                } else if (this.f22788i.size() > 0) {
                    for (int i2 = 0; i2 < this.f22788i.size(); i2++) {
                        sQLiteDatabaseA.execSQL("delete from __sd where __ii=\"" + this.f22788i.get(i2) + "\"");
                    }
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            g.a(f22783d).b();
        }
    }

    public long a(String str) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        Cursor cursorA = null;
        long j2 = 0;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
            } catch (Exception unused) {
                sQLiteDatabaseA = null;
            } catch (Throwable th) {
                th = th;
                sQLiteDatabaseA = null;
            }
            try {
                sQLiteDatabaseA.beginTransaction();
                cursorA = a(e.d.f22747a, sQLiteDatabaseA, new String[]{e.d.a.f22754g}, "__ii=? ", new String[]{str}, null, null, null, null);
                if (cursorA != null) {
                    cursorA.moveToFirst();
                    j2 = cursorA.getLong(cursorA.getColumnIndex(e.d.a.f22754g));
                }
                if (cursorA != null) {
                    cursorA.close();
                }
            } catch (Exception unused2) {
                if (cursorA != null) {
                    cursorA.close();
                }
                if (sQLiteDatabaseA != null) {
                    sQLiteDatabaseA.endTransaction();
                }
                g.a(f22783d).b();
                return j2;
            } catch (Throwable th2) {
                th = th2;
                if (cursorA != null) {
                    try {
                        cursorA.close();
                    } catch (Exception unused3) {
                        g.a(f22783d).b();
                        throw th;
                    }
                }
                if (sQLiteDatabaseA != null) {
                    sQLiteDatabaseA.endTransaction();
                }
                g.a(f22783d).b();
                throw th;
            }
            sQLiteDatabaseA.endTransaction();
        } catch (Exception unused4) {
        }
        g.a(f22783d).b();
        return j2;
    }

    public void b(String str) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (!TextUtils.isEmpty(str)) {
                    sQLiteDatabaseA.execSQL("delete from __is where __ii=\"" + str + "\"");
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            g.a(f22783d).b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0077 A[Catch: all -> 0x0069, TryCatch #1 {all -> 0x0069, blocks: (B:22:0x0056, B:24:0x005c, B:26:0x006c, B:28:0x0077, B:29:0x007c, B:36:0x008c, B:38:0x0092, B:40:0x0098, B:42:0x009e, B:44:0x00ac, B:41:0x009b), top: B:54:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0056 A[EXC_TOP_SPLITTER, LOOP:1: B:54:0x0056->B:24:0x005c, LOOP_START, PHI: r14
      0x0056: PHI (r14v2 java.lang.String) = (r14v7 java.lang.String), (r14v3 java.lang.String) binds: [B:21:0x0054, B:24:0x005c] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r18, org.json.JSONObject r19, android.database.sqlite.SQLiteDatabase r20, java.lang.String r21) throws org.json.JSONException {
        /*
            r17 = this;
            r10 = r17
            r11 = r18
            r0 = r19
            r12 = r21
            java.lang.String r13 = "=\""
            java.lang.String r1 = "__b"
            java.lang.String r2 = "__a"
            r14 = 0
            boolean r3 = r2.equals(r12)     // Catch: java.lang.Throwable -> Le4
            if (r3 == 0) goto L25
            org.json.JSONArray r0 = r0.optJSONArray(r2)     // Catch: java.lang.Throwable -> Le4
            if (r0 == 0) goto L24
            int r1 = r0.length()     // Catch: java.lang.Throwable -> Le4
            if (r1 > 0) goto L22
            goto L24
        L22:
            r15 = r0
            goto L39
        L24:
            return
        L25:
            boolean r2 = r1.equals(r12)     // Catch: java.lang.Throwable -> Le4
            if (r2 == 0) goto L38
            org.json.JSONArray r0 = r0.optJSONArray(r1)     // Catch: java.lang.Throwable -> Le4
            if (r0 == 0) goto L37
            int r1 = r0.length()     // Catch: java.lang.Throwable -> Le4
            if (r1 > 0) goto L22
        L37:
            return
        L38:
            r15 = r14
        L39:
            r0 = 1
            java.lang.String[] r3 = new java.lang.String[r0]     // Catch: java.lang.Throwable -> Le4
            r16 = 0
            r3[r16] = r12     // Catch: java.lang.Throwable -> Le4
            java.lang.String r1 = "__sd"
            java.lang.String r4 = "__ii=? "
            java.lang.String[] r5 = new java.lang.String[r0]     // Catch: java.lang.Throwable -> Le4
            r5[r16] = r11     // Catch: java.lang.Throwable -> Le4
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r0 = r17
            r2 = r20
            android.database.Cursor r0 = r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> Le4
            if (r0 == 0) goto L6c
        L56:
            boolean r1 = r0.moveToNext()     // Catch: java.lang.Throwable -> L69
            if (r1 == 0) goto L6c
            int r1 = r0.getColumnIndex(r12)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = r0.getString(r1)     // Catch: java.lang.Throwable -> L69
            java.lang.String r14 = r10.d(r1)     // Catch: java.lang.Throwable -> L69
            goto L56
        L69:
            r14 = r0
            goto Le4
        L6c:
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch: java.lang.Throwable -> L69
            r1.<init>()     // Catch: java.lang.Throwable -> L69
            boolean r2 = android.text.TextUtils.isEmpty(r14)     // Catch: java.lang.Throwable -> L69
            if (r2 != 0) goto L7c
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch: java.lang.Throwable -> L69
            r1.<init>(r14)     // Catch: java.lang.Throwable -> L69
        L7c:
            int r2 = r1.length()     // Catch: java.lang.Throwable -> L69
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 <= r3) goto L8a
            if (r0 == 0) goto L89
            r0.close()
        L89:
            return
        L8a:
            r2 = r16
        L8c:
            int r3 = r15.length()     // Catch: java.lang.Throwable -> L69
            if (r2 >= r3) goto L9e
            org.json.JSONObject r3 = r15.getJSONObject(r2)     // Catch: java.lang.Throwable -> L69 org.json.JSONException -> L9b
            if (r3 == 0) goto L9b
            r1.put(r3)     // Catch: java.lang.Throwable -> L69
        L9b:
            int r2 = r2 + 1
            goto L8c
        L9e:
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = r10.c(r1)     // Catch: java.lang.Throwable -> L69
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L69
            if (r2 != 0) goto Lde
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L69
            r2.<init>()     // Catch: java.lang.Throwable -> L69
            java.lang.String r3 = "update __sd set "
            r2.append(r3)     // Catch: java.lang.Throwable -> L69
            r2.append(r12)     // Catch: java.lang.Throwable -> L69
            r2.append(r13)     // Catch: java.lang.Throwable -> L69
            r2.append(r1)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = "\" where "
            r2.append(r1)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = "__ii"
            r2.append(r1)     // Catch: java.lang.Throwable -> L69
            r2.append(r13)     // Catch: java.lang.Throwable -> L69
            r2.append(r11)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = "\""
            r2.append(r1)     // Catch: java.lang.Throwable -> L69
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> L69
            r2 = r20
            r2.execSQL(r1)     // Catch: java.lang.Throwable -> L69
        Lde:
            if (r0 == 0) goto Le9
            r0.close()
            goto Le9
        Le4:
            if (r14 == 0) goto Le9
            r14.close()
        Le9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.a(java.lang.String, org.json.JSONObject, android.database.sqlite.SQLiteDatabase, java.lang.String):void");
    }

    public JSONObject a(boolean z2) {
        a();
        this.f22789j.clear();
        JSONObject jSONObject = new JSONObject();
        if (!z2) {
            a(jSONObject, z2);
            b(jSONObject, (String) null);
            a(jSONObject, (String) null);
        } else {
            String strA = a(jSONObject, z2);
            if (!TextUtils.isEmpty(strA)) {
                b(jSONObject, strA);
                a(jSONObject, strA);
            }
        }
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x0190 A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x0190: PHI (r0v5 android.database.Cursor) = 
      (r0v2 android.database.Cursor)
      (r0v3 android.database.Cursor)
      (r0v8 android.database.Cursor)
      (r0v8 android.database.Cursor)
     binds: [B:68:0x0180, B:74:0x018e, B:62:0x016f, B:63:0x0171] A[DONT_GENERATE, DONT_INLINE]
      0x0190: PHI (r1v6 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
     binds: [B:68:0x0180, B:74:0x018e, B:62:0x016f, B:63:0x0171] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0193 A[PHI: r0 r1
      0x0193: PHI (r0v4 android.database.Cursor) = (r0v2 android.database.Cursor), (r0v3 android.database.Cursor), (r0v5 android.database.Cursor) binds: [B:68:0x0180, B:74:0x018e, B:75:0x0190] A[DONT_GENERATE, DONT_INLINE]
      0x0193: PHI (r1v5 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v6 android.database.sqlite.SQLiteDatabase)
     binds: [B:68:0x0180, B:74:0x018e, B:75:0x0190] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(org.json.JSONObject r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.a(org.json.JSONObject, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0236 A[PHI: r0 r12 r14
      0x0236: PHI (r0v5 java.lang.String) = (r0v2 java.lang.String), (r0v3 java.lang.String), (r0v6 java.lang.String) binds: [B:91:0x0222, B:97:0x0231, B:98:0x0233] A[DONT_GENERATE, DONT_INLINE]
      0x0236: PHI (r12v5 android.database.sqlite.SQLiteDatabase) = 
      (r12v3 android.database.sqlite.SQLiteDatabase)
      (r12v4 android.database.sqlite.SQLiteDatabase)
      (r12v6 android.database.sqlite.SQLiteDatabase)
     binds: [B:91:0x0222, B:97:0x0231, B:98:0x0233] A[DONT_GENERATE, DONT_INLINE]
      0x0236: PHI (r14v5 android.database.Cursor) = (r14v3 android.database.Cursor), (r14v4 android.database.Cursor), (r14v6 android.database.Cursor) binds: [B:91:0x0222, B:97:0x0231, B:98:0x0233] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0233 A[EXC_TOP_SPLITTER, PHI: r0 r12 r14
      0x0233: PHI (r0v6 java.lang.String) = (r0v2 java.lang.String), (r0v3 java.lang.String), (r0v39 java.lang.String), (r0v39 java.lang.String) binds: [B:91:0x0222, B:97:0x0231, B:83:0x020c, B:84:0x020e] A[DONT_GENERATE, DONT_INLINE]
      0x0233: PHI (r12v6 android.database.sqlite.SQLiteDatabase) = 
      (r12v3 android.database.sqlite.SQLiteDatabase)
      (r12v4 android.database.sqlite.SQLiteDatabase)
      (r12v7 android.database.sqlite.SQLiteDatabase)
      (r12v7 android.database.sqlite.SQLiteDatabase)
     binds: [B:91:0x0222, B:97:0x0231, B:83:0x020c, B:84:0x020e] A[DONT_GENERATE, DONT_INLINE]
      0x0233: PHI (r14v6 android.database.Cursor) = 
      (r14v3 android.database.Cursor)
      (r14v4 android.database.Cursor)
      (r14v33 android.database.Cursor)
      (r14v33 android.database.Cursor)
     binds: [B:91:0x0222, B:97:0x0231, B:83:0x020c, B:84:0x020e] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x022e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(org.json.JSONObject r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 597
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.i.a(org.json.JSONObject, boolean):java.lang.String");
    }

    public void a(boolean z2, boolean z3) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                try {
                    sQLiteDatabaseA = g.a(f22783d).a();
                    sQLiteDatabaseA.beginTransaction();
                    if (!z3) {
                        int size = this.f22791l.size();
                        int i2 = 0;
                        if (size > 0) {
                            int i3 = 0;
                            while (i2 < size) {
                                String str = this.f22791l.get(i2);
                                if (str == null) {
                                    i3 = 1;
                                }
                                sQLiteDatabaseA.execSQL("delete from __is where __ii=\"" + str + "\"");
                                i2++;
                            }
                            i2 = i3;
                        }
                        if (i2 != 0) {
                            sQLiteDatabaseA.execSQL("delete from __is where __ii is null");
                        }
                    } else if (z2) {
                        sQLiteDatabaseA.execSQL("delete from __is");
                    }
                    sQLiteDatabaseA.setTransactionSuccessful();
                } catch (SQLiteDatabaseCorruptException unused) {
                    h.a(f22783d);
                    if (sQLiteDatabaseA != null) {
                    }
                }
            } catch (Throwable unused2) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            }
            try {
                sQLiteDatabaseA.endTransaction();
            } catch (Throwable unused3) {
            }
            g.a(f22783d).b();
        } catch (Throwable th) {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused4) {
                }
            }
            g.a(f22783d).b();
            throw th;
        }
    }

    public void a(boolean z2, String str) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = g.a(f22783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (!TextUtils.isEmpty(str)) {
                    sQLiteDatabaseA.execSQL("delete from __er where __i=\"" + str + "\"");
                    sQLiteDatabaseA.execSQL("delete from __et where __i=\"" + str + "\"");
                    this.f22789j.clear();
                    sQLiteDatabaseA.execSQL("delete from __sd where __ii=\"" + str + "\"");
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                h.a(f22783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            g.a(f22783d).b();
        }
    }
}
