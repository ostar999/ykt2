package com.jakewharton.rxbinding2.widget;

import android.widget.AdapterView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class AdapterViewNothingSelectionEvent extends AdapterViewSelectionEvent {
    @NonNull
    @CheckResult
    public static AdapterViewSelectionEvent create(@NonNull AdapterView<?> adapterView) {
        return new AutoValue_AdapterViewNothingSelectionEvent(adapterView);
    }
}
