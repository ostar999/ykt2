package com.google.android.gms.libs.punchclock.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.CallSuper;

/* loaded from: classes3.dex */
public class TracingHandler extends Handler {
    private static zza zzbi;

    public interface zza {
    }

    public TracingHandler() {
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        dispatchMessageTraced(message);
    }

    @CallSuper
    public void dispatchMessageTraced(Message message) {
        super.dispatchMessage(message);
    }

    public boolean postAtFrontOfQueueTraced(Runnable runnable) {
        return postAtFrontOfQueue(runnable);
    }

    public boolean sendMessageAtFrontOfQueueTraced(Message message) {
        return sendMessageAtFrontOfQueue(message);
    }

    @Override // android.os.Handler
    public boolean sendMessageAtTime(Message message, long j2) {
        return super.sendMessageAtTime(message, j2);
    }

    public TracingHandler(Handler.Callback callback) {
        super(callback);
    }

    public TracingHandler(Looper looper) {
        super(looper);
    }

    public TracingHandler(Looper looper, Handler.Callback callback) {
        super(looper, callback);
    }
}
