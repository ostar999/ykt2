package com.google.android.gms.internal.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes3.dex */
public class zar extends Handler {
    private static volatile zaq zasi;

    public zar() {
    }

    @Override // android.os.Handler
    public final void dispatchMessage(Message message) {
        super.dispatchMessage(message);
    }

    public zar(Looper looper) {
        super(looper);
    }

    public zar(Looper looper, Handler.Callback callback) {
        super(looper, callback);
    }
}
