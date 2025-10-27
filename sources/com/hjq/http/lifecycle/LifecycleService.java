package com.hjq.http.lifecycle;

import android.app.Service;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/* loaded from: classes4.dex */
public abstract class LifecycleService extends Service implements LifecycleOwner {
    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }
}
