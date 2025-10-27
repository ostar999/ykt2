package com.jakewharton.rxbinding2.widget;

import android.widget.Adapter;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;

/* loaded from: classes4.dex */
public final class RxAdapter {
    private RxAdapter() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static <T extends Adapter> InitialValueObservable<T> dataChanges(@NonNull T t2) {
        Preconditions.checkNotNull(t2, "adapter == null");
        return new AdapterDataChangeObservable(t2);
    }
}
