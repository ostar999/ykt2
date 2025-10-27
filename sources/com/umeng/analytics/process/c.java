package com.umeng.analytics.process;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.commonsdk.service.UMGlobalContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f22995a;

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap<String, a> f22996b = new ConcurrentHashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private Context f22997c;

    private c() {
    }

    public static c a(Context context) {
        if (f22995a == null) {
            synchronized (c.class) {
                if (f22995a == null) {
                    f22995a = new c();
                }
            }
        }
        c cVar = f22995a;
        cVar.f22997c = context;
        return cVar;
    }

    private a c(String str) {
        if (this.f22996b.get(str) != null) {
            return this.f22996b.get(str);
        }
        a aVarA = a.a(this.f22997c, str);
        this.f22996b.put(str, aVarA);
        return aVarA;
    }

    public synchronized void b(String str) {
        c(str).b();
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private AtomicInteger f22998a = new AtomicInteger();

        /* renamed from: b, reason: collision with root package name */
        private SQLiteOpenHelper f22999b;

        /* renamed from: c, reason: collision with root package name */
        private SQLiteDatabase f23000c;

        private a() {
        }

        public static a a(Context context, String str) {
            Context appContext = UMGlobalContext.getAppContext(context);
            a aVar = new a();
            aVar.f22999b = b.a(appContext, str);
            return aVar;
        }

        public synchronized void b() {
            try {
                if (this.f22998a.decrementAndGet() == 0) {
                    this.f23000c.close();
                }
            } catch (Throwable unused) {
            }
        }

        public synchronized SQLiteDatabase a() {
            if (this.f22998a.incrementAndGet() == 1) {
                this.f23000c = this.f22999b.getWritableDatabase();
            }
            return this.f23000c;
        }
    }

    public synchronized SQLiteDatabase a(String str) {
        return c(str).a();
    }
}
