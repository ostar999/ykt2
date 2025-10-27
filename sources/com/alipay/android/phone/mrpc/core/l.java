package com.alipay.android.phone.mrpc.core;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public final class l implements ab {

    /* renamed from: b, reason: collision with root package name */
    private static l f2961b;

    /* renamed from: i, reason: collision with root package name */
    private static final ThreadFactory f2962i = new n();

    /* renamed from: a, reason: collision with root package name */
    Context f2963a;

    /* renamed from: c, reason: collision with root package name */
    private ThreadPoolExecutor f2964c;

    /* renamed from: d, reason: collision with root package name */
    private b f2965d = b.a("android");

    /* renamed from: e, reason: collision with root package name */
    private long f2966e;

    /* renamed from: f, reason: collision with root package name */
    private long f2967f;

    /* renamed from: g, reason: collision with root package name */
    private long f2968g;

    /* renamed from: h, reason: collision with root package name */
    private int f2969h;

    private l(Context context) {
        this.f2963a = context;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 11, 3L, TimeUnit.SECONDS, new ArrayBlockingQueue(20), f2962i, new ThreadPoolExecutor.CallerRunsPolicy());
        this.f2964c = threadPoolExecutor;
        try {
            threadPoolExecutor.allowCoreThreadTimeOut(true);
        } catch (Exception unused) {
        }
        CookieSyncManager.createInstance(this.f2963a);
        CookieManager.getInstance().setAcceptCookie(true);
    }

    public static final l a(Context context) {
        l lVar = f2961b;
        return lVar != null ? lVar : b(context);
    }

    private static final synchronized l b(Context context) {
        l lVar = f2961b;
        if (lVar != null) {
            return lVar;
        }
        l lVar2 = new l(context);
        f2961b = lVar2;
        return lVar2;
    }

    public final b a() {
        return this.f2965d;
    }

    @Override // com.alipay.android.phone.mrpc.core.ab
    public final Future<u> a(t tVar) {
        if (s.a(this.f2963a)) {
            String str = "HttpManager" + hashCode() + ": Active Task = %d, Completed Task = %d, All Task = %d,Avarage Speed = %d KB/S, Connetct Time = %d ms, All data size = %d bytes, All enqueueConnect time = %d ms, All socket time = %d ms, All request times = %d times";
            Object[] objArr = new Object[9];
            objArr[0] = Integer.valueOf(this.f2964c.getActiveCount());
            objArr[1] = Long.valueOf(this.f2964c.getCompletedTaskCount());
            objArr[2] = Long.valueOf(this.f2964c.getTaskCount());
            long j2 = this.f2968g;
            objArr[3] = Long.valueOf(j2 == 0 ? 0L : ((this.f2966e * 1000) / j2) >> 10);
            int i2 = this.f2969h;
            objArr[4] = Long.valueOf(i2 != 0 ? this.f2967f / i2 : 0L);
            objArr[5] = Long.valueOf(this.f2966e);
            objArr[6] = Long.valueOf(this.f2967f);
            objArr[7] = Long.valueOf(this.f2968g);
            objArr[8] = Integer.valueOf(this.f2969h);
            String.format(str, objArr);
        }
        q qVar = new q(this, (o) tVar);
        m mVar = new m(this, qVar, qVar);
        this.f2964c.execute(mVar);
        return mVar;
    }

    public final void a(long j2) {
        this.f2966e += j2;
    }

    public final void b(long j2) {
        this.f2967f += j2;
        this.f2969h++;
    }

    public final void c(long j2) {
        this.f2968g += j2;
    }
}
