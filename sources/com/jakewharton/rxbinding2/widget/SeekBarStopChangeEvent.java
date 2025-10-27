package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes4.dex */
public abstract class SeekBarStopChangeEvent extends SeekBarChangeEvent {
    @NonNull
    @CheckResult
    public static SeekBarStopChangeEvent create(@NonNull SeekBar seekBar) {
        return new AutoValue_SeekBarStopChangeEvent(seekBar);
    }
}
