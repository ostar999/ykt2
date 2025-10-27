package com.vivo.push;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.google.android.exoplayer2.C;

/* loaded from: classes6.dex */
public final class m {

    /* renamed from: a, reason: collision with root package name */
    private static final Handler f24391a = new Handler(Looper.getMainLooper());

    /* renamed from: b, reason: collision with root package name */
    private static final HandlerThread f24392b;

    /* renamed from: c, reason: collision with root package name */
    private static final Handler f24393c;

    static {
        HandlerThread handlerThread = new HandlerThread("push_client_thread");
        f24392b = handlerThread;
        handlerThread.start();
        f24393c = new n(handlerThread.getLooper());
    }

    public static void a(l lVar) {
        if (lVar == null) {
            com.vivo.push.util.p.a("PushClientThread", "client thread error, task is null!");
            return;
        }
        int iA = lVar.a();
        Message message = new Message();
        message.what = iA;
        message.obj = lVar;
        f24393c.sendMessageDelayed(message, 0L);
    }

    public static void b(Runnable runnable) {
        f24391a.post(runnable);
    }

    public static void c(Runnable runnable) {
        Handler handler = f24393c;
        if (handler != null) {
            handler.post(runnable);
        }
    }

    public static void a(Runnable runnable) {
        Handler handler = f24393c;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }
}
