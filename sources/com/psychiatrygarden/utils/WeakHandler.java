package com.psychiatrygarden.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class WeakHandler extends Handler {
    private final HandlerCallback callback;
    final WeakReference<Activity> tWeakReference;

    public interface HandlerCallback {
        void handlerMessage(Message msg);
    }

    public WeakHandler(Activity activity, HandlerCallback callback) {
        super(activity.getMainLooper());
        this.tWeakReference = new WeakReference<>(activity);
        this.callback = callback;
    }

    @Override // android.os.Handler
    public void handleMessage(@NonNull Message msg) {
        HandlerCallback handlerCallback;
        super.handleMessage(msg);
        if (this.tWeakReference.get() == null || (handlerCallback = this.callback) == null) {
            return;
        }
        handlerCallback.handlerMessage(msg);
    }
}
