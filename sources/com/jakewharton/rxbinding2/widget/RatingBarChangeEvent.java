package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class RatingBarChangeEvent {
    @NonNull
    @CheckResult
    public static RatingBarChangeEvent create(@NonNull RatingBar ratingBar, float f2, boolean z2) {
        return new AutoValue_RatingBarChangeEvent(ratingBar, f2, z2);
    }

    public abstract boolean fromUser();

    public abstract float rating();

    @NonNull
    public abstract RatingBar view();
}
