package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_SeekBarStopChangeEvent extends SeekBarStopChangeEvent {
    private final SeekBar view;

    public AutoValue_SeekBarStopChangeEvent(SeekBar seekBar) {
        if (seekBar == null) {
            throw new NullPointerException("Null view");
        }
        this.view = seekBar;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SeekBarStopChangeEvent) {
            return this.view.equals(((SeekBarStopChangeEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.view.hashCode() ^ 1000003;
    }

    public String toString() {
        return "SeekBarStopChangeEvent{view=" + this.view + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarChangeEvent
    @NonNull
    public SeekBar view() {
        return this.view;
    }
}
