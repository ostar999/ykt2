package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class u {

    /* renamed from: a, reason: collision with root package name */
    public static final long f17891a = System.currentTimeMillis();

    /* renamed from: b, reason: collision with root package name */
    private static u f17892b;

    /* renamed from: c, reason: collision with root package name */
    private Context f17893c;

    /* renamed from: f, reason: collision with root package name */
    private SharedPreferences f17896f;

    /* renamed from: e, reason: collision with root package name */
    private Map<Integer, Map<String, t>> f17895e = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private String f17894d = aa.b().f17418d;

    private u(Context context) {
        this.f17893c = context;
        this.f17896f = context.getSharedPreferences("crashrecord", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003e A[Catch: all -> 0x0054, Exception -> 0x0056, PHI: r6
      0x003e: PHI (r6v10 java.io.ObjectInputStream) = (r6v9 java.io.ObjectInputStream), (r6v11 java.io.ObjectInputStream) binds: [B:17:0x003c, B:22:0x004a] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x0056, blocks: (B:4:0x0003, B:10:0x002c, B:18:0x003e, B:26:0x0050, B:27:0x0053), top: B:37:0x0003, outer: #1 }] */
    /* JADX WARN: Type inference failed for: r6v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized <T extends java.util.List<?>> T d(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            android.content.Context r3 = r5.f17893c     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r4 = "crashrecord"
            java.io.File r3 = r3.getDir(r4, r1)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            r2.<init>(r3, r6)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            boolean r6 = r2.exists()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            if (r6 != 0) goto L1c
            monitor-exit(r5)
            return r0
        L1c:
            java.io.ObjectInputStream r6 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L31 java.lang.ClassNotFoundException -> L34 java.io.IOException -> L42
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L31 java.lang.ClassNotFoundException -> L34 java.io.IOException -> L42
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L31 java.lang.ClassNotFoundException -> L34 java.io.IOException -> L42
            r6.<init>(r3)     // Catch: java.lang.Throwable -> L31 java.lang.ClassNotFoundException -> L34 java.io.IOException -> L42
            java.lang.Object r2 = r6.readObject()     // Catch: java.lang.ClassNotFoundException -> L35 java.io.IOException -> L43 java.lang.Throwable -> L4d
            java.util.List r2 = (java.util.List) r2     // Catch: java.lang.ClassNotFoundException -> L35 java.io.IOException -> L43 java.lang.Throwable -> L4d
            r6.close()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            monitor-exit(r5)
            return r2
        L31:
            r2 = move-exception
            r6 = r0
            goto L4e
        L34:
            r6 = r0
        L35:
            java.lang.String r2 = "get object error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L4d
            com.tencent.bugly.proguard.al.a(r2, r3)     // Catch: java.lang.Throwable -> L4d
            if (r6 == 0) goto L5e
        L3e:
            r6.close()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            goto L5e
        L42:
            r6 = r0
        L43:
            java.lang.String r2 = "open record file error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L4d
            com.tencent.bugly.proguard.al.a(r2, r3)     // Catch: java.lang.Throwable -> L4d
            if (r6 == 0) goto L5e
            goto L3e
        L4d:
            r2 = move-exception
        L4e:
            if (r6 == 0) goto L53
            r6.close()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
        L53:
            throw r2     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
        L54:
            r6 = move-exception
            goto L60
        L56:
            java.lang.String r6 = "readCrashRecord error"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L54
            com.tencent.bugly.proguard.al.e(r6, r1)     // Catch: java.lang.Throwable -> L54
        L5e:
            monitor-exit(r5)
            return r0
        L60:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.d(int):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean c(int i2) {
        try {
            List<t> listD = d(i2);
            if (listD == null) {
                return false;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (t tVar : listD) {
                String str = tVar.f17885b;
                if (str != null && str.equalsIgnoreCase(this.f17894d) && tVar.f17887d > 0) {
                    arrayList.add(tVar);
                }
                if (tVar.f17886c + 86400000 < jCurrentTimeMillis) {
                    arrayList2.add(tVar);
                }
            }
            Collections.sort(arrayList);
            if (arrayList.size() < 2) {
                listD.removeAll(arrayList2);
                a(i2, (int) listD);
                return false;
            }
            if (arrayList.size() <= 0 || ((t) arrayList.get(arrayList.size() - 1)).f17886c + 86400000 >= jCurrentTimeMillis) {
                return true;
            }
            listD.clear();
            a(i2, (int) listD);
            return false;
        } catch (Exception unused) {
            al.e("isFrequentCrash failed", new Object[0]);
            return false;
        }
    }

    public final synchronized boolean b(final int i2) {
        boolean z2;
        z2 = true;
        try {
            z2 = this.f17896f.getBoolean(i2 + StrPool.UNDERLINE + this.f17894d, true);
            ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.u.2
                @Override // java.lang.Runnable
                public final void run() {
                    boolean zC = u.this.c(i2);
                    u.this.f17896f.edit().putBoolean(i2 + StrPool.UNDERLINE + u.this.f17894d, !zC).commit();
                }
            });
        } catch (Exception unused) {
            al.e("canInit error", new Object[0]);
            return z2;
        }
        return z2;
    }

    public static synchronized u a(Context context) {
        if (f17892b == null) {
            f17892b = new u(context);
        }
        return f17892b;
    }

    public static synchronized u a() {
        return f17892b;
    }

    public static /* synthetic */ boolean b(t tVar, t tVar2) {
        String str = tVar.f17888e;
        if (str != null && !str.equalsIgnoreCase(tVar2.f17888e)) {
            return true;
        }
        String str2 = tVar.f17889f;
        return !(str2 == null || str2.equalsIgnoreCase(tVar2.f17889f)) || tVar.f17887d <= 0;
    }

    public final void a(final int i2) {
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.u.1

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ int f17897a = 1004;

            @Override // java.lang.Runnable
            public final void run() {
                t tVar;
                try {
                    if (TextUtils.isEmpty(u.this.f17894d)) {
                        return;
                    }
                    List<t> listD = u.this.d(this.f17897a);
                    if (listD == null) {
                        listD = new ArrayList();
                    }
                    if (u.this.f17895e.get(Integer.valueOf(this.f17897a)) == null) {
                        u.this.f17895e.put(Integer.valueOf(this.f17897a), new HashMap());
                    }
                    if (((Map) u.this.f17895e.get(Integer.valueOf(this.f17897a))).get(u.this.f17894d) == null) {
                        tVar = new t();
                        tVar.f17884a = this.f17897a;
                        tVar.f17890g = u.f17891a;
                        tVar.f17885b = u.this.f17894d;
                        tVar.f17889f = aa.b().f17429o;
                        tVar.f17888e = aa.b().f17422h;
                        tVar.f17886c = System.currentTimeMillis();
                        tVar.f17887d = i2;
                        ((Map) u.this.f17895e.get(Integer.valueOf(this.f17897a))).put(u.this.f17894d, tVar);
                    } else {
                        tVar = (t) ((Map) u.this.f17895e.get(Integer.valueOf(this.f17897a))).get(u.this.f17894d);
                        tVar.f17887d = i2;
                    }
                    ArrayList arrayList = new ArrayList();
                    boolean z2 = false;
                    for (t tVar2 : listD) {
                        if (u.a(tVar2, tVar)) {
                            tVar2.f17887d = tVar.f17887d;
                            z2 = true;
                        }
                        if (u.b(tVar2, tVar)) {
                            arrayList.add(tVar2);
                        }
                    }
                    listD.removeAll(arrayList);
                    if (!z2) {
                        listD.add(tVar);
                    }
                    u.this.a(this.f17897a, (int) listD);
                } catch (Exception unused) {
                    al.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0049 A[Catch: all -> 0x004d, Exception -> 0x004f, TRY_ENTER, TryCatch #1 {Exception -> 0x004f, blocks: (B:7:0x0006, B:11:0x0025, B:22:0x003f, B:29:0x0049, B:30:0x004c), top: B:40:0x0006, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized <T extends java.util.List<?>> void a(int r5, T r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r6 != 0) goto L5
            monitor-exit(r4)
            return
        L5:
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            android.content.Context r2 = r4.f17893c     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            java.lang.String r3 = "crashrecord"
            java.io.File r2 = r2.getDir(r3, r0)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r1.<init>(r2, r5)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            r5 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L30
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L30
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L30
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L30
            r2.writeObject(r6)     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L46
            r2.close()     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            monitor-exit(r4)
            return
        L2a:
            r5 = move-exception
            goto L33
        L2c:
            r6 = move-exception
            r2 = r5
            r5 = r6
            goto L47
        L30:
            r6 = move-exception
            r2 = r5
            r5 = r6
        L33:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L46
            java.lang.String r5 = "open record file error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L46
            com.tencent.bugly.proguard.al.a(r5, r6)     // Catch: java.lang.Throwable -> L46
            if (r2 == 0) goto L44
            r2.close()     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
            monitor-exit(r4)
            return
        L44:
            monitor-exit(r4)
            return
        L46:
            r5 = move-exception
        L47:
            if (r2 == 0) goto L4c
            r2.close()     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
        L4c:
            throw r5     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L4f
        L4d:
            r5 = move-exception
            goto L59
        L4f:
            java.lang.String r5 = "writeCrashRecord error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L4d
            com.tencent.bugly.proguard.al.e(r5, r6)     // Catch: java.lang.Throwable -> L4d
            monitor-exit(r4)
            return
        L59:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.a(int, java.util.List):void");
    }

    public static /* synthetic */ boolean a(t tVar, t tVar2) {
        String str;
        return tVar.f17890g == tVar2.f17890g && (str = tVar.f17885b) != null && str.equalsIgnoreCase(tVar2.f17885b);
    }
}
