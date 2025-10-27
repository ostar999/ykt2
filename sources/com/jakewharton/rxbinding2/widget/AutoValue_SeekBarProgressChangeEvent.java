package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.NonNull;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
final class AutoValue_SeekBarProgressChangeEvent extends SeekBarProgressChangeEvent {
    private final boolean fromUser;
    private final int progress;
    private final SeekBar view;

    public AutoValue_SeekBarProgressChangeEvent(SeekBar seekBar, int i2, boolean z2) {
        if (seekBar == null) {
            throw new NullPointerException("Null view");
        }
        this.view = seekBar;
        this.progress = i2;
        this.fromUser = z2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SeekBarProgressChangeEvent)) {
            return false;
        }
        SeekBarProgressChangeEvent seekBarProgressChangeEvent = (SeekBarProgressChangeEvent) obj;
        return this.view.equals(seekBarProgressChangeEvent.view()) && this.progress == seekBarProgressChangeEvent.progress() && this.fromUser == seekBarProgressChangeEvent.fromUser();
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarProgressChangeEvent
    public boolean fromUser() {
        return this.fromUser;
    }

    public int hashCode() {
        return ((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.progress) * 1000003) ^ (this.fromUser ? R2.attr.customColorDrawableValue : R2.attr.customIsDrag);
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarProgressChangeEvent
    public int progress() {
        return this.progress;
    }

    public String toString() {
        return "SeekBarProgressChangeEvent{view=" + this.view + ", progress=" + this.progress + ", fromUser=" + this.fromUser + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.SeekBarChangeEvent
    @NonNull
    public SeekBar view() {
        return this.view;
    }
}
