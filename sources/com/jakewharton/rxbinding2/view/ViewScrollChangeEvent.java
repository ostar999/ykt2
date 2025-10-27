package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class ViewScrollChangeEvent {
    @NonNull
    @CheckResult
    public static ViewScrollChangeEvent create(@NonNull View view, int i2, int i3, int i4, int i5) {
        return new AutoValue_ViewScrollChangeEvent(view, i2, i3, i4, i5);
    }

    public abstract int oldScrollX();

    public abstract int oldScrollY();

    public abstract int scrollX();

    public abstract int scrollY();

    @NonNull
    public abstract View view();
}
