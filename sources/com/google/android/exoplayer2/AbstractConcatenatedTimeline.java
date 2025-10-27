package com.google.android.exoplayer2;

import android.util.Pair;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes3.dex */
public abstract class AbstractConcatenatedTimeline extends Timeline {
    private final int childCount;
    private final boolean isAtomic;
    private final ShuffleOrder shuffleOrder;

    public AbstractConcatenatedTimeline(boolean z2, ShuffleOrder shuffleOrder) {
        this.isAtomic = z2;
        this.shuffleOrder = shuffleOrder;
        this.childCount = shuffleOrder.getLength();
    }

    public static Object getChildPeriodUidFromConcatenatedUid(Object obj) {
        return ((Pair) obj).second;
    }

    public static Object getChildTimelineUidFromConcatenatedUid(Object obj) {
        return ((Pair) obj).first;
    }

    public static Object getConcatenatedUid(Object obj, Object obj2) {
        return Pair.create(obj, obj2);
    }

    private int getNextChildIndex(int i2, boolean z2) {
        if (z2) {
            return this.shuffleOrder.getNextIndex(i2);
        }
        if (i2 < this.childCount - 1) {
            return i2 + 1;
        }
        return -1;
    }

    private int getPreviousChildIndex(int i2, boolean z2) {
        if (z2) {
            return this.shuffleOrder.getPreviousIndex(i2);
        }
        if (i2 > 0) {
            return i2 - 1;
        }
        return -1;
    }

    public abstract int getChildIndexByChildUid(Object obj);

    public abstract int getChildIndexByPeriodIndex(int i2);

    public abstract int getChildIndexByWindowIndex(int i2);

    public abstract Object getChildUidByChildIndex(int i2);

    public abstract int getFirstPeriodIndexByChildIndex(int i2);

    @Override // com.google.android.exoplayer2.Timeline
    public int getFirstWindowIndex(boolean z2) {
        if (this.childCount == 0) {
            return -1;
        }
        if (this.isAtomic) {
            z2 = false;
        }
        int firstIndex = z2 ? this.shuffleOrder.getFirstIndex() : 0;
        while (getTimelineByChildIndex(firstIndex).isEmpty()) {
            firstIndex = getNextChildIndex(firstIndex, z2);
            if (firstIndex == -1) {
                return -1;
            }
        }
        return getFirstWindowIndexByChildIndex(firstIndex) + getTimelineByChildIndex(firstIndex).getFirstWindowIndex(z2);
    }

    public abstract int getFirstWindowIndexByChildIndex(int i2);

    @Override // com.google.android.exoplayer2.Timeline
    public final int getIndexOfPeriod(Object obj) {
        int indexOfPeriod;
        if (!(obj instanceof Pair)) {
            return -1;
        }
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(obj);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(obj);
        int childIndexByChildUid = getChildIndexByChildUid(childTimelineUidFromConcatenatedUid);
        if (childIndexByChildUid == -1 || (indexOfPeriod = getTimelineByChildIndex(childIndexByChildUid).getIndexOfPeriod(childPeriodUidFromConcatenatedUid)) == -1) {
            return -1;
        }
        return getFirstPeriodIndexByChildIndex(childIndexByChildUid) + indexOfPeriod;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getLastWindowIndex(boolean z2) {
        int i2 = this.childCount;
        if (i2 == 0) {
            return -1;
        }
        if (this.isAtomic) {
            z2 = false;
        }
        int lastIndex = z2 ? this.shuffleOrder.getLastIndex() : i2 - 1;
        while (getTimelineByChildIndex(lastIndex).isEmpty()) {
            lastIndex = getPreviousChildIndex(lastIndex, z2);
            if (lastIndex == -1) {
                return -1;
            }
        }
        return getFirstWindowIndexByChildIndex(lastIndex) + getTimelineByChildIndex(lastIndex).getLastWindowIndex(z2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getNextWindowIndex(int i2, int i3, boolean z2) {
        if (this.isAtomic) {
            if (i3 == 1) {
                i3 = 2;
            }
            z2 = false;
        }
        int childIndexByWindowIndex = getChildIndexByWindowIndex(i2);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByWindowIndex);
        int nextWindowIndex = getTimelineByChildIndex(childIndexByWindowIndex).getNextWindowIndex(i2 - firstWindowIndexByChildIndex, i3 != 2 ? i3 : 0, z2);
        if (nextWindowIndex != -1) {
            return firstWindowIndexByChildIndex + nextWindowIndex;
        }
        int nextChildIndex = getNextChildIndex(childIndexByWindowIndex, z2);
        while (nextChildIndex != -1 && getTimelineByChildIndex(nextChildIndex).isEmpty()) {
            nextChildIndex = getNextChildIndex(nextChildIndex, z2);
        }
        if (nextChildIndex != -1) {
            return getFirstWindowIndexByChildIndex(nextChildIndex) + getTimelineByChildIndex(nextChildIndex).getFirstWindowIndex(z2);
        }
        if (i3 == 2) {
            return getFirstWindowIndex(z2);
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
        int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i2);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByPeriodIndex);
        getTimelineByChildIndex(childIndexByPeriodIndex).getPeriod(i2 - getFirstPeriodIndexByChildIndex(childIndexByPeriodIndex), period, z2);
        period.windowIndex += firstWindowIndexByChildIndex;
        if (z2) {
            period.uid = getConcatenatedUid(getChildUidByChildIndex(childIndexByPeriodIndex), Assertions.checkNotNull(period.uid));
        }
        return period;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Timeline.Period getPeriodByUid(Object obj, Timeline.Period period) {
        Object childTimelineUidFromConcatenatedUid = getChildTimelineUidFromConcatenatedUid(obj);
        Object childPeriodUidFromConcatenatedUid = getChildPeriodUidFromConcatenatedUid(obj);
        int childIndexByChildUid = getChildIndexByChildUid(childTimelineUidFromConcatenatedUid);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByChildUid);
        getTimelineByChildIndex(childIndexByChildUid).getPeriodByUid(childPeriodUidFromConcatenatedUid, period);
        period.windowIndex += firstWindowIndexByChildIndex;
        period.uid = obj;
        return period;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
        if (this.isAtomic) {
            if (i3 == 1) {
                i3 = 2;
            }
            z2 = false;
        }
        int childIndexByWindowIndex = getChildIndexByWindowIndex(i2);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByWindowIndex);
        int previousWindowIndex = getTimelineByChildIndex(childIndexByWindowIndex).getPreviousWindowIndex(i2 - firstWindowIndexByChildIndex, i3 != 2 ? i3 : 0, z2);
        if (previousWindowIndex != -1) {
            return firstWindowIndexByChildIndex + previousWindowIndex;
        }
        int previousChildIndex = getPreviousChildIndex(childIndexByWindowIndex, z2);
        while (previousChildIndex != -1 && getTimelineByChildIndex(previousChildIndex).isEmpty()) {
            previousChildIndex = getPreviousChildIndex(previousChildIndex, z2);
        }
        if (previousChildIndex != -1) {
            return getFirstWindowIndexByChildIndex(previousChildIndex) + getTimelineByChildIndex(previousChildIndex).getLastWindowIndex(z2);
        }
        if (i3 == 2) {
            return getLastWindowIndex(z2);
        }
        return -1;
    }

    public abstract Timeline getTimelineByChildIndex(int i2);

    @Override // com.google.android.exoplayer2.Timeline
    public final Object getUidOfPeriod(int i2) {
        int childIndexByPeriodIndex = getChildIndexByPeriodIndex(i2);
        return getConcatenatedUid(getChildUidByChildIndex(childIndexByPeriodIndex), getTimelineByChildIndex(childIndexByPeriodIndex).getUidOfPeriod(i2 - getFirstPeriodIndexByChildIndex(childIndexByPeriodIndex)));
    }

    @Override // com.google.android.exoplayer2.Timeline
    public final Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
        int childIndexByWindowIndex = getChildIndexByWindowIndex(i2);
        int firstWindowIndexByChildIndex = getFirstWindowIndexByChildIndex(childIndexByWindowIndex);
        int firstPeriodIndexByChildIndex = getFirstPeriodIndexByChildIndex(childIndexByWindowIndex);
        getTimelineByChildIndex(childIndexByWindowIndex).getWindow(i2 - firstWindowIndexByChildIndex, window, j2);
        Object childUidByChildIndex = getChildUidByChildIndex(childIndexByWindowIndex);
        if (!Timeline.Window.SINGLE_WINDOW_UID.equals(window.uid)) {
            childUidByChildIndex = getConcatenatedUid(childUidByChildIndex, window.uid);
        }
        window.uid = childUidByChildIndex;
        window.firstPeriodIndex += firstPeriodIndexByChildIndex;
        window.lastPeriodIndex += firstPeriodIndexByChildIndex;
        return window;
    }
}
