package com.alipay.sdk.tid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: c, reason: collision with root package name */
    private static b f3337c;

    /* renamed from: a, reason: collision with root package name */
    public String f3338a;

    /* renamed from: b, reason: collision with root package name */
    public String f3339b;

    private b() {
    }

    private void a(String str) {
        this.f3338a = str;
    }

    private String b() {
        return this.f3338a;
    }

    private String c() {
        return this.f3339b;
    }

    private boolean d() {
        return TextUtils.isEmpty(this.f3338a);
    }

    private static void e() {
        Context context = com.alipay.sdk.sys.b.a().f3333a;
        String strA = com.alipay.sdk.util.a.a(context).a();
        String strB = com.alipay.sdk.util.a.a(context).b();
        a aVar = new a(context);
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = aVar.getWritableDatabase();
            aVar.a(writableDatabase, strA, strB, "", "");
            a.a(writableDatabase, a.c(strA, strB));
        } catch (Exception unused) {
            if (writableDatabase != null && writableDatabase.isOpen()) {
            }
        } catch (Throwable th) {
            if (writableDatabase != null && writableDatabase.isOpen()) {
                writableDatabase.close();
            }
            throw th;
        }
        if (writableDatabase != null && writableDatabase.isOpen()) {
            writableDatabase.close();
        }
        aVar.close();
    }

    private static String f() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        return hexString.length() > 10 ? hexString.substring(hexString.length() - 10) : hexString;
    }

    private void a(Context context) {
        a aVar = new a(context);
        try {
            aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), this.f3338a, this.f3339b);
        } catch (Exception unused) {
        } finally {
            aVar.close();
        }
    }

    private void b(String str) {
        this.f3339b = str;
    }

    public static synchronized b a() {
        if (f3337c == null) {
            f3337c = new b();
            Context context = com.alipay.sdk.sys.b.a().f3333a;
            a aVar = new a(context);
            String strA = com.alipay.sdk.util.a.a(context).a();
            String strB = com.alipay.sdk.util.a.a(context).b();
            f3337c.f3338a = aVar.a(strA, strB);
            f3337c.f3339b = aVar.b(strA, strB);
            if (TextUtils.isEmpty(f3337c.f3339b)) {
                b bVar = f3337c;
                String hexString = Long.toHexString(System.currentTimeMillis());
                if (hexString.length() > 10) {
                    hexString = hexString.substring(hexString.length() - 10);
                }
                bVar.f3339b = hexString;
            }
            b bVar2 = f3337c;
            aVar.a(strA, strB, bVar2.f3338a, bVar2.f3339b);
        }
        return f3337c;
    }
}
