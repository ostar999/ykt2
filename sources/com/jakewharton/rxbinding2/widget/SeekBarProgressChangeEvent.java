package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class SeekBarProgressChangeEvent extends SeekBarChangeEvent {
    @NonNull
    @CheckResult
    public static SeekBarProgressChangeEvent create(@NonNull SeekBar seekBar, int i2, boolean z2) {
        return new AutoValue_SeekBarProgressChangeEvent(seekBar, i2, z2);
    }

    public abstract boolean fromUser();

    public abstract int progress();
}
