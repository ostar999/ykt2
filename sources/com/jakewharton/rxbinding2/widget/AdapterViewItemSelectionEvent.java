package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class AdapterViewItemSelectionEvent extends AdapterViewSelectionEvent {
    @NonNull
    @CheckResult
    public static AdapterViewSelectionEvent create(@NonNull AdapterView<?> adapterView, @NonNull View view, int i2, long j2) {
        return new AutoValue_AdapterViewItemSelectionEvent(adapterView, view, i2, j2);
    }

    public abstract long id();

    public abstract int position();

    @NonNull
    public abstract View selectedView();
}
