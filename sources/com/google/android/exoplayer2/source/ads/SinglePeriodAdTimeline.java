package com.google.android.exoplayer2.source.ads;

import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.util.Assertions;

@VisibleForTesting(otherwise = 3)
/* loaded from: classes3.dex */
public final class SinglePeriodAdTimeline extends ForwardingTimeline {
    private final AdPlaybackState adPlaybackState;

    public SinglePeriodAdTimeline(Timeline timeline, AdPlaybackState adPlaybackState) {
        super(timeline);
        Assertions.checkState(timeline.getPeriodCount() == 1);
        Assertions.checkState(timeline.getWindowCount() == 1);
        this.adPlaybackState = adPlaybackState;
    }

    @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
    public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
        this.timeline.getPeriod(i2, period, z2);
        long j2 = period.durationUs;
        if (j2 == C.TIME_UNSET) {
            j2 = this.adPlaybackState.contentDurationUs;
        }
        period.set(period.id, period.uid, period.windowIndex, j2, period.getPositionInWindowUs(), this.adPlaybackState, period.isPlaceholder);
        return period;
    }
}
