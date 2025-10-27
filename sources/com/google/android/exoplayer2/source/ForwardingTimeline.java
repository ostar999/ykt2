package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.Timeline;

/* loaded from: classes3.dex */
public abstract class ForwardingTimeline extends Timeline {
    protected final Timeline timeline;

    public ForwardingTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getFirstWindowIndex(boolean z2) {
        return this.timeline.getFirstWindowIndex(z2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getIndexOfPeriod(Object obj) {
        return this.timeline.getIndexOfPeriod(obj);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getLastWindowIndex(boolean z2) {
        return this.timeline.getLastWindowIndex(z2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getNextWindowIndex(int i2, int i3, boolean z2) {
        return this.timeline.getNextWindowIndex(i2, i3, z2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
        return this.timeline.getPeriod(i2, period, z2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPeriodCount() {
        return this.timeline.getPeriodCount();
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
        return this.timeline.getPreviousWindowIndex(i2, i3, z2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Object getUidOfPeriod(int i2) {
        return this.timeline.getUidOfPeriod(i2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
        return this.timeline.getWindow(i2, window, j2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getWindowCount() {
        return this.timeline.getWindowCount();
    }
}
