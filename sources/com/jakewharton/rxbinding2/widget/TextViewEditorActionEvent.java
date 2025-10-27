package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class TextViewEditorActionEvent {
    @NonNull
    @CheckResult
    public static TextViewEditorActionEvent create(@NonNull TextView textView, int i2, @Nullable KeyEvent keyEvent) {
        return new AutoValue_TextViewEditorActionEvent(textView, i2, keyEvent);
    }

    public abstract int actionId();

    @Nullable
    public abstract KeyEvent keyEvent();

    @NonNull
    public abstract TextView view();
}
