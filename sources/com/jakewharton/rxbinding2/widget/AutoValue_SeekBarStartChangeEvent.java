package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class AutoValue_SeekBarStartChangeEvent extends SeekBarStartChangeEvent {
    private final SeekBar view;

    public AutoValue_SeekBarStartChangeEvent(SeekBar seekBar) {
        if (seekBar == null) {
            throw new NullPointerException("Null view");
        }
        this.view = seekBar;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SeekBarStartChangeEvent) {
            return this.view.equals(((SeekBarStartChangeEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.view.hashCode() ^ 1000003;
    }

    public String toString() {
        return "SeekBarStartChangeEvent{view=" + this.view + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarChangeEvent
    @NonNull
    public SeekBar view() {
        return this.view;
    }
}
