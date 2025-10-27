package com.umeng.analytics.pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
class g {

    /* renamed from: b, reason: collision with root package name */
    private static SQLiteOpenHelper f22775b;

    /* renamed from: d, reason: collision with root package name */
    private static Context f22776d;

    /* renamed from: a, reason: collision with root package name */
    private AtomicInteger f22777a;

    /* renamed from: c, reason: collision with root package name */
    private SQLiteDatabase f22778c;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final g f22779a = new g();

        private a() {
        }
    }

    public static g a(Context context) {
        if (f22776d == null && context != null) {
            Context applicationContext = context.getApplicationContext();
            f22776d = applicationContext;
            f22775b = f.a(applicationContext);
        }
        return a.f22779a;
    }

    public synchronized void b() {
        try {
            if (this.f22777a.decrementAndGet() == 0) {
                this.f22778c.close();
            }
        } catch (Throwable unused) {
        }
    }

    private g() {
        this.f22777a = new AtomicInteger();
    }

    public synchronized SQLiteDatabase a() {
        if (this.f22777a.incrementAndGet() == 1) {
            this.f22778c = f22775b.getWritableDatabase();
        }
        return this.f22778c;
    }
}
