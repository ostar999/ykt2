package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class AdapterViewItemLongClickEvent {
    @NonNull
    @CheckResult
    public static AdapterViewItemLongClickEvent create(@NonNull AdapterView<?> adapterView, @NonNull View view, int i2, long j2) {
        return new AutoValue_AdapterViewItemLongClickEvent(adapterView, view, i2, j2);
    }

    @NonNull
    public abstract View clickedView();

    public abstract long id();

    public abstract int position();

    @NonNull
    public abstract AdapterView<?> view();
}
