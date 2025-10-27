package com.google.android.exoplayer2.ui;

import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public interface TimeBar {

    public interface OnScrubListener {
        void onScrubMove(TimeBar timeBar, long j2);

        void onScrubStart(TimeBar timeBar, long j2);

        void onScrubStop(TimeBar timeBar, long j2, boolean z2);
    }

    void addListener(OnScrubListener onScrubListener);

    long getPreferredUpdateDelay();

    void removeListener(OnScrubListener onScrubListener);

    void setAdGroupTimesMs(@Nullable long[] jArr, @Nullable boolean[] zArr, int i2);

    void setBufferedPosition(long j2);

    void setDuration(long j2);

    void setEnabled(boolean z2);

    void setKeyCountIncrement(int i2);

    void setKeyTimeIncrement(long j2);

    void setPosition(long j2);
}
