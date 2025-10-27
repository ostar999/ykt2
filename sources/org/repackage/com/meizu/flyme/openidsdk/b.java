package org.repackage.com.meizu.flyme.openidsdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes9.dex */
class b {

    /* renamed from: e, reason: collision with root package name */
    private static volatile b f28005e = null;

    /* renamed from: f, reason: collision with root package name */
    private static boolean f28006f = false;

    /* renamed from: h, reason: collision with root package name */
    private BroadcastReceiver f28012h;

    /* renamed from: a, reason: collision with root package name */
    OpenId f28007a = new OpenId("udid");

    /* renamed from: b, reason: collision with root package name */
    OpenId f28008b = new OpenId("oaid");

    /* renamed from: d, reason: collision with root package name */
    OpenId f28010d = new OpenId("vaid");

    /* renamed from: c, reason: collision with root package name */
    OpenId f28009c = new OpenId("aaid");

    /* renamed from: g, reason: collision with root package name */
    private SupportInfo f28011g = new SupportInfo();

    private b() {
    }

    private static String a(PackageManager packageManager, String str) {
        ProviderInfo providerInfoResolveContentProvider;
        if (packageManager == null || (providerInfoResolveContentProvider = packageManager.resolveContentProvider(str, 0)) == null || (providerInfoResolveContentProvider.applicationInfo.flags & 1) == 0) {
            return null;
        }
        return providerInfoResolveContentProvider.packageName;
    }

    private static ValueData a(Cursor cursor) {
        String str;
        ValueData valueData = new ValueData(null, 0);
        if (cursor == null) {
            str = "parseValue fail, cursor is null.";
        } else {
            if (!cursor.isClosed()) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex("value");
                if (columnIndex >= 0) {
                    valueData.f28002a = cursor.getString(columnIndex);
                } else {
                    a("parseValue fail, index < 0.");
                }
                int columnIndex2 = cursor.getColumnIndex("code");
                if (columnIndex2 >= 0) {
                    valueData.f28003b = cursor.getInt(columnIndex2);
                } else {
                    a("parseCode fail, index < 0.");
                }
                int columnIndex3 = cursor.getColumnIndex("expired");
                if (columnIndex3 >= 0) {
                    valueData.f28004c = cursor.getLong(columnIndex3);
                } else {
                    a("parseExpired fail, index < 0.");
                }
                return valueData;
            }
            str = "parseValue fail, cursor is closed.";
        }
        a(str);
        return valueData;
    }

    public static final b a() {
        if (f28005e == null) {
            synchronized (b.class) {
                if (f28005e == null) {
                    f28005e = new b();
                }
            }
        }
        return f28005e;
    }

    public static void a(String str) {
        if (f28006f) {
            Log.d("OpenIdManager", str);
        }
    }

    public static void a(boolean z2) {
        f28006f = z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(android.content.Context r8) {
        /*
            java.lang.String r0 = "querySupport version : 1.0.8"
            a(r0)
            java.lang.String r0 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r2 = android.net.Uri.parse(r0)
            r0 = 0
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            r3 = 0
            r4 = 0
            java.lang.String r8 = "supported"
            java.lang.String[] r5 = new java.lang.String[]{r8}     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            r6 = 0
            android.database.Cursor r7 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            if (r7 == 0) goto L39
            org.repackage.com.meizu.flyme.openidsdk.ValueData r8 = a(r7)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            int r1 = r8.f28003b     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r2 != r1) goto L34
            java.lang.String r1 = "0"
            java.lang.String r8 = r8.f28002a     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            boolean r8 = r1.equals(r8)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            if (r8 == 0) goto L35
        L34:
            r0 = 1
        L35:
            r7.close()
            return r0
        L39:
            if (r7 == 0) goto L5a
        L3b:
            r7.close()
            goto L5a
        L3f:
            r8 = move-exception
            goto L5b
        L41:
            r8 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3f
            java.lang.String r2 = "querySupport, Exception : "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L3f
            r1.append(r8)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r8 = r1.toString()     // Catch: java.lang.Throwable -> L3f
            a(r8)     // Catch: java.lang.Throwable -> L3f
            if (r7 == 0) goto L5a
            goto L3b
        L5a:
            return r0
        L5b:
            if (r7 == 0) goto L60
            r7.close()
        L60:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.repackage.com.meizu.flyme.openidsdk.b.a(android.content.Context):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0095  */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(android.content.Context r10, org.repackage.com.meizu.flyme.openidsdk.OpenId r11) throws java.lang.Throwable {
        /*
            r9 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "queryId : "
            r0.<init>(r1)
            java.lang.String r1 = r11.f27996c
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            a(r0)
            java.lang.String r0 = "content://com.meizu.flyme.openidsdk/"
            android.net.Uri r2 = android.net.Uri.parse(r0)
            r0 = 0
            android.content.ContentResolver r1 = r10.getContentResolver()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            r3 = 0
            r4 = 0
            r7 = 1
            java.lang.String[] r5 = new java.lang.String[r7]     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            java.lang.String r6 = r11.f27996c     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            r8 = 0
            r5[r8] = r6     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            r6 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La2
            if (r1 == 0) goto L7e
            org.repackage.com.meizu.flyme.openidsdk.ValueData r2 = a(r1)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r0 = r2.f28002a     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r11.a(r0)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            long r3 = r2.f28004c     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r11.a(r3)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            int r3 = r2.f28003b     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r11.a(r3)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r3.<init>()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r4 = r11.f27996c     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r3.append(r4)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r4 = " errorCode : "
            r3.append(r4)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            int r11 = r11.f27997d     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r3.append(r11)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r11 = r3.toString()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            a(r11)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            int r11 = r2.f28003b     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r11 == r2) goto L93
            r9.b(r10)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            boolean r11 = r9.a(r10, r8)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            if (r11 != 0) goto L93
            boolean r10 = r9.a(r10, r7)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r11 = "not support, forceQuery isSupported: "
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r10 = r11.concat(r10)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
        L7a:
            a(r10)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            goto L93
        L7e:
            boolean r11 = r9.a(r10, r8)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            if (r11 == 0) goto L93
            boolean r10 = r9.a(r10, r7)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r11 = "forceQuery isSupported : "
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            java.lang.String r10 = r11.concat(r10)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9c
            goto L7a
        L93:
            if (r1 == 0) goto Lbf
            r1.close()
            goto Lbf
        L99:
            r10 = move-exception
            r0 = r1
            goto Lc0
        L9c:
            r10 = move-exception
            r11 = r0
            r0 = r1
            goto La4
        La0:
            r10 = move-exception
            goto Lc0
        La2:
            r10 = move-exception
            r11 = r0
        La4:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La0
            java.lang.String r2 = "queryId, Exception : "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> La0
            java.lang.String r10 = r10.getMessage()     // Catch: java.lang.Throwable -> La0
            r1.append(r10)     // Catch: java.lang.Throwable -> La0
            java.lang.String r10 = r1.toString()     // Catch: java.lang.Throwable -> La0
            a(r10)     // Catch: java.lang.Throwable -> La0
            if (r0 == 0) goto Lbe
            r0.close()
        Lbe:
            r0 = r11
        Lbf:
            return r0
        Lc0:
            if (r0 == 0) goto Lc5
            r0.close()
        Lc5:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.repackage.com.meizu.flyme.openidsdk.b.b(android.content.Context, org.repackage.com.meizu.flyme.openidsdk.OpenId):java.lang.String");
    }

    private static String b(PackageManager packageManager, String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            a("getAppVersion, Exception : " + e2.getMessage());
            return null;
        }
    }

    private synchronized void b(Context context) {
        if (this.f28012h != null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.meizu.flyme.openid.ACTION_OPEN_ID_CHANGE");
        a aVar = new a();
        this.f28012h = aVar;
        context.registerReceiver(aVar, intentFilter, "com.meizu.flyme.openid.permission.OPEN_ID_CHANGE", null);
    }

    public final String a(Context context, OpenId openId) {
        String str;
        if (openId == null) {
            str = "getId, openId = null.";
        } else {
            if (openId.a()) {
                return openId.f27995b;
            }
            if (a(context, true)) {
                return b(context, openId);
            }
            str = "getId, isSupported = false.";
        }
        a(str);
        return null;
    }

    public final boolean a(Context context, boolean z2) throws PackageManager.NameNotFoundException {
        if (this.f28011g.a() && !z2) {
            return this.f28011g.b();
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        String strA = a(packageManager, "com.meizu.flyme.openidsdk");
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        String strB = b(packageManager, strA);
        if (this.f28011g.a() && this.f28011g.a(strB)) {
            a("use same version cache, safeVersion : ".concat(String.valueOf(strB)));
            return this.f28011g.b();
        }
        this.f28011g.b(strB);
        boolean zA = a(context);
        a("query support, result : ".concat(String.valueOf(zA)));
        this.f28011g.a(zA);
        return zA;
    }
}
