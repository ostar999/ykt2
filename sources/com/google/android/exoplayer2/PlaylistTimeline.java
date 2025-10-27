package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
final class PlaylistTimeline extends AbstractConcatenatedTimeline {
    private final HashMap<Object, Integer> childIndexByUid;
    private final int[] firstPeriodInChildIndices;
    private final int[] firstWindowInChildIndices;
    private final int periodCount;
    private final Timeline[] timelines;
    private final Object[] uids;
    private final int windowCount;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PlaylistTimeline(Collection<? extends MediaSourceInfoHolder> collection, ShuffleOrder shuffleOrder) {
        super(false, shuffleOrder);
        int windowCount = 0;
        int size = collection.size();
        this.firstPeriodInChildIndices = new int[size];
        this.firstWindowInChildIndices = new int[size];
        this.timelines = new Timeline[size];
        this.uids = new Object[size];
        this.childIndexByUid = new HashMap<>();
        int periodCount = 0;
        int i2 = 0;
        for (MediaSourceInfoHolder mediaSourceInfoHolder : collection) {
            this.timelines[i2] = mediaSourceInfoHolder.getTimeline();
            this.firstWindowInChildIndices[i2] = windowCount;
            this.firstPeriodInChildIndices[i2] = periodCount;
            windowCount += this.timelines[i2].getWindowCount();
            periodCount += this.timelines[i2].getPeriodCount();
            this.uids[i2] = mediaSourceInfoHolder.getUid();
            this.childIndexByUid.put(this.uids[i2], Integer.valueOf(i2));
            i2++;
        }
        this.windowCount = windowCount;
        this.periodCount = periodCount;
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public int getChildIndexByChildUid(Object obj) {
        Integer num = this.childIndexByUid.get(obj);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public int getChildIndexByPeriodIndex(int i2) {
        return Util.binarySearchFloor(this.firstPeriodInChildIndices, i2 + 1, false, false);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public int getChildIndexByWindowIndex(int i2) {
        return Util.binarySearchFloor(this.firstWindowInChildIndices, i2 + 1, false, false);
    }

    public List<Timeline> getChildTimelines() {
        return Arrays.asList(this.timelines);
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public Object getChildUidByChildIndex(int i2) {
        return this.uids[i2];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public int getFirstPeriodIndexByChildIndex(int i2) {
        return this.firstPeriodInChildIndices[i2];
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public int getFirstWindowIndexByChildIndex(int i2) {
        return this.firstWindowInChildIndices[i2];
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPeriodCount() {
        return this.periodCount;
    }

    @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
    public Timeline getTimelineByChildIndex(int i2) {
        return this.timelines[i2];
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getWindowCount() {
        return this.windowCount;
    }
}
