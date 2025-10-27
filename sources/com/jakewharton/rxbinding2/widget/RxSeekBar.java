package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;

/* loaded from: classes4.dex */
public final class RxSeekBar {
    private RxSeekBar() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<SeekBarChangeEvent> changeEvents(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new SeekBarChangeEventObservable(seekBar);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> changes(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new SeekBarChangeObservable(seekBar, null);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> systemChanges(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new SeekBarChangeObservable(seekBar, Boolean.FALSE);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> userChanges(@NonNull SeekBar seekBar) {
        Preconditions.checkNotNull(seekBar, "view == null");
        return new SeekBarChangeObservable(seekBar, Boolean.TRUE);
    }
}
