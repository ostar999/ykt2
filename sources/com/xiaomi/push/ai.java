package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class ai {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ai f24599a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f181a;

    /* renamed from: a, reason: collision with other field name */
    private ScheduledThreadPoolExecutor f184a = new ScheduledThreadPoolExecutor(1);

    /* renamed from: a, reason: collision with other field name */
    private SparseArray<ScheduledFuture> f182a = new SparseArray<>();

    /* renamed from: a, reason: collision with other field name */
    private Object f183a = new Object();

    public static abstract class a implements Runnable {
        /* renamed from: a */
        public abstract int mo185a();
    }

    public static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        a f24600a;

        public b(a aVar) {
            this.f24600a = aVar;
        }

        public void a() {
        }

        public void b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a();
            this.f24600a.run();
            b();
        }
    }

    private ai(Context context) {
        this.f181a = context.getSharedPreferences("mipush_extra", 0);
    }

    public static ai a(Context context) {
        if (f24599a == null) {
            synchronized (ai.class) {
                if (f24599a == null) {
                    f24599a = new ai(context);
                }
            }
        }
        return f24599a;
    }

    private static String a(int i2) {
        return "last_job_time" + i2;
    }

    private ScheduledFuture a(a aVar) {
        ScheduledFuture scheduledFuture;
        synchronized (this.f183a) {
            scheduledFuture = this.f182a.get(aVar.mo185a());
        }
        return scheduledFuture;
    }

    public void a(Runnable runnable) {
        a(runnable, 0);
    }

    public void a(Runnable runnable, int i2) {
        this.f184a.schedule(runnable, i2, TimeUnit.SECONDS);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m196a(int i2) {
        synchronized (this.f183a) {
            ScheduledFuture scheduledFuture = this.f182a.get(i2);
            if (scheduledFuture == null) {
                return false;
            }
            this.f182a.remove(i2);
            return scheduledFuture.cancel(false);
        }
    }

    public boolean a(a aVar, int i2) {
        return a(aVar, i2, 0);
    }

    public boolean a(a aVar, int i2, int i3) {
        if (aVar == null || a(aVar) != null) {
            return false;
        }
        String strA = a(aVar.mo185a());
        aj ajVar = new aj(this, aVar, strA);
        long jAbs = Math.abs(System.currentTimeMillis() - this.f181a.getLong(strA, 0L)) / 1000;
        if (jAbs < i2 - i3) {
            i3 = (int) (i2 - jAbs);
        }
        ScheduledFuture<?> scheduledFutureScheduleAtFixedRate = this.f184a.scheduleAtFixedRate(ajVar, i3, i2, TimeUnit.SECONDS);
        synchronized (this.f183a) {
            this.f182a.put(aVar.mo185a(), scheduledFutureScheduleAtFixedRate);
        }
        return true;
    }

    public boolean b(a aVar, int i2) {
        if (aVar == null || a(aVar) != null) {
            return false;
        }
        ScheduledFuture<?> scheduledFutureSchedule = this.f184a.schedule(new ak(this, aVar), i2, TimeUnit.SECONDS);
        synchronized (this.f183a) {
            this.f182a.put(aVar.mo185a(), scheduledFutureSchedule);
        }
        return true;
    }
}
