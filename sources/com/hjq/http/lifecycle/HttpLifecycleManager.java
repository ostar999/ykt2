package com.hjq.http.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyHttp;

/* loaded from: classes4.dex */
public final class HttpLifecycleManager implements LifecycleEventObserver {
    public static void bind(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new HttpLifecycleManager());
    }

    public static boolean isLifecycleActive(LifecycleOwner lifecycleOwner) {
        return (lifecycleOwner == null || lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) ? false : true;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event != Lifecycle.Event.ON_DESTROY) {
            return;
        }
        lifecycleOwner.getLifecycle().removeObserver(this);
        EasyHttp.cancel(lifecycleOwner);
    }
}
