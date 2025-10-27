package com.hjq.http.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/* loaded from: classes4.dex */
public final class ApplicationLifecycle implements LifecycleOwner {
    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }
}
