package com.vivo.push;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes6.dex */
public abstract class q {

    /* renamed from: a, reason: collision with root package name */
    protected Context f24408a;

    /* renamed from: b, reason: collision with root package name */
    protected Handler f24409b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f24410c = new Object();

    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            q.this.b(message);
        }
    }

    public q() {
        HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName(), 1);
        handlerThread.start();
        this.f24409b = new a(handlerThread.getLooper());
    }

    public final void a(Context context) {
        this.f24408a = context;
    }

    public abstract void b(Message message);

    public final void a(Message message) {
        synchronized (this.f24410c) {
            Handler handler = this.f24409b;
            if (handler == null) {
                String str = "Dead worker dropping a message: " + message.what;
                com.vivo.push.util.p.e(getClass().getSimpleName(), str + " (Thread " + Thread.currentThread().getId() + ")");
            } else {
                handler.sendMessage(message);
            }
        }
    }
}
