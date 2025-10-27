package com.tencent.tbs.one.impl.a;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.exoplayer2.ExoPlayer;

/* loaded from: classes6.dex */
public final class o {

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ boolean f21765a = true;

    /* renamed from: b, reason: collision with root package name */
    public static final Object f21766b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public static HandlerThread f21767c;

    /* renamed from: d, reason: collision with root package name */
    public static Handler f21768d;

    public static Handler a() {
        Handler handler;
        synchronized (f21766b) {
            if (f21768d == null) {
                HandlerThread handlerThread = new HandlerThread("TBSOneThread");
                f21767c = handlerThread;
                handlerThread.start();
                f21768d = new Handler(f21767c.getLooper());
            }
            handler = f21768d;
        }
        return handler;
    }

    public static void a(Runnable runnable) {
        if (b()) {
            runnable.run();
        } else {
            a().post(runnable);
        }
    }

    public static void b(Runnable runnable) {
        a().post(runnable);
    }

    public static boolean b() {
        return a().getLooper() == Looper.myLooper();
    }

    public static void c(Runnable runnable) {
        a().postDelayed(runnable, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public static void d(Runnable runnable) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(runnable);
    }
}
