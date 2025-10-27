package com.aliyun.vod.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public abstract class WeakReferenceHandler<T> extends Handler {
    private WeakReference<T> mReference;

    public WeakReferenceHandler(Looper looper, T t2) {
        super(looper);
        this.mReference = new WeakReference<>(t2);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        T t2 = this.mReference.get();
        if (t2 == null) {
            return;
        }
        handleMessage(t2, message);
    }

    public abstract void handleMessage(T t2, Message message);
}
