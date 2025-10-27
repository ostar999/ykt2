package com.plv.foundationsdk.component.livedata;

import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class Event<T> {
    private T value;

    public Event(T t2) {
        this.value = t2;
    }

    @Nullable
    public synchronized T get() {
        T t2;
        t2 = this.value;
        this.value = null;
        return t2;
    }
}
