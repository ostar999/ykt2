package com.jakewharton.rxbinding2.widget;

import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class TextViewBeforeTextChangeEvent {
    @NonNull
    @CheckResult
    public static TextViewBeforeTextChangeEvent create(@NonNull TextView textView, @NonNull CharSequence charSequence, int i2, int i3, int i4) {
        return new AutoValue_TextViewBeforeTextChangeEvent(textView, charSequence, i2, i3, i4);
    }

    public abstract int after();

    public abstract int count();

    public abstract int start();

    @NonNull
    public abstract CharSequence text();

    @NonNull
    public abstract TextView view();
}
