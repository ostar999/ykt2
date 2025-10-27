package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes6.dex */
public final class bf implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final Handler f17687a;

    /* renamed from: d, reason: collision with root package name */
    long f17690d;

    /* renamed from: e, reason: collision with root package name */
    private final String f17691e;

    /* renamed from: f, reason: collision with root package name */
    private final List<ba> f17692f = new LinkedList();

    /* renamed from: b, reason: collision with root package name */
    long f17688b = 5000;

    /* renamed from: g, reason: collision with root package name */
    private final long f17693g = 5000;

    /* renamed from: c, reason: collision with root package name */
    boolean f17689c = true;

    public bf(Handler handler, String str) {
        this.f17687a = handler;
        this.f17691e = str;
    }

    private Thread e() {
        return this.f17687a.getLooper().getThread();
    }

    public final boolean a() {
        return !this.f17689c && SystemClock.uptimeMillis() >= this.f17690d + this.f17688b;
    }

    public final long b() {
        return SystemClock.uptimeMillis() - this.f17690d;
    }

    public final List<ba> c() {
        ArrayList arrayList;
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (this.f17692f) {
            arrayList = new ArrayList(this.f17692f.size());
            for (int i2 = 0; i2 < this.f17692f.size(); i2++) {
                ba baVar = this.f17692f.get(i2);
                if (!baVar.f17668e && jCurrentTimeMillis - baVar.f17665b < 200000) {
                    arrayList.add(baVar);
                    baVar.f17668e = true;
                }
            }
        }
        return arrayList;
    }

    public final void d() {
        StringBuilder sb = new StringBuilder(1024);
        long jNanoTime = System.nanoTime();
        try {
            StackTraceElement[] stackTrace = e().getStackTrace();
            if (stackTrace.length == 0) {
                sb.append("Thread does not have stack trace.\n");
            } else {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append(stackTraceElement);
                    sb.append("\n");
                }
            }
        } catch (SecurityException e2) {
            sb.append("getStackTrace() encountered:\n");
            sb.append(e2.getMessage());
            sb.append("\n");
            al.a(e2);
        }
        long jNanoTime2 = System.nanoTime();
        ba baVar = new ba(sb.toString(), System.currentTimeMillis());
        baVar.f17667d = jNanoTime2 - jNanoTime;
        String name = e().getName();
        if (name == null) {
            name = "";
        }
        baVar.f17664a = name;
        synchronized (this.f17692f) {
            while (this.f17692f.size() >= 32) {
                this.f17692f.remove(0);
            }
            this.f17692f.add(baVar);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f17689c = true;
        this.f17688b = this.f17693g;
    }
}
