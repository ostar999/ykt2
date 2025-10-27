package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.AbstractConcatenatedTimeline;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.Map;

@Deprecated
/* loaded from: classes3.dex */
public final class LoopingMediaSource extends CompositeMediaSource<Void> {
    private final Map<MediaSource.MediaPeriodId, MediaSource.MediaPeriodId> childMediaPeriodIdToMediaPeriodId;
    private final int loopCount;
    private final MaskingMediaSource maskingMediaSource;
    private final Map<MediaPeriod, MediaSource.MediaPeriodId> mediaPeriodToChildMediaPeriodId;

    public static final class InfinitelyLoopingTimeline extends ForwardingTimeline {
        public InfinitelyLoopingTimeline(Timeline timeline) {
            super(timeline);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getNextWindowIndex(int i2, int i3, boolean z2) {
            int nextWindowIndex = this.timeline.getNextWindowIndex(i2, i3, z2);
            return nextWindowIndex == -1 ? getFirstWindowIndex(z2) : nextWindowIndex;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
            int previousWindowIndex = this.timeline.getPreviousWindowIndex(i2, i3, z2);
            return previousWindowIndex == -1 ? getLastWindowIndex(z2) : previousWindowIndex;
        }
    }

    public static final class LoopingTimeline extends AbstractConcatenatedTimeline {
        private final int childPeriodCount;
        private final Timeline childTimeline;
        private final int childWindowCount;
        private final int loopCount;

        public LoopingTimeline(Timeline timeline, int i2) {
            super(false, new ShuffleOrder.UnshuffledShuffleOrder(i2));
            this.childTimeline = timeline;
            int periodCount = timeline.getPeriodCount();
            this.childPeriodCount = periodCount;
            this.childWindowCount = timeline.getWindowCount();
            this.loopCount = i2;
            if (periodCount > 0) {
                Assertions.checkState(i2 <= Integer.MAX_VALUE / periodCount, "LoopingMediaSource contains too many periods");
            }
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public int getChildIndexByChildUid(Object obj) {
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public int getChildIndexByPeriodIndex(int i2) {
            return i2 / this.childPeriodCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public int getChildIndexByWindowIndex(int i2) {
            return i2 / this.childWindowCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public Object getChildUidByChildIndex(int i2) {
            return Integer.valueOf(i2);
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public int getFirstPeriodIndexByChildIndex(int i2) {
            return i2 * this.childPeriodCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public int getFirstWindowIndexByChildIndex(int i2) {
            return i2 * this.childWindowCount;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.childPeriodCount * this.loopCount;
        }

        @Override // com.google.android.exoplayer2.AbstractConcatenatedTimeline
        public Timeline getTimelineByChildIndex(int i2) {
            return this.childTimeline;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.childWindowCount * this.loopCount;
        }
    }

    public LoopingMediaSource(MediaSource mediaSource) {
        this(mediaSource, Integer.MAX_VALUE);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        if (this.loopCount == Integer.MAX_VALUE) {
            return this.maskingMediaSource.createPeriod(mediaPeriodId, allocator, j2);
        }
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(AbstractConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(mediaPeriodId.periodUid));
        this.childMediaPeriodIdToMediaPeriodId.put(mediaPeriodIdCopyWithPeriodUid, mediaPeriodId);
        MaskingMediaPeriod maskingMediaPeriodCreatePeriod = this.maskingMediaSource.createPeriod(mediaPeriodIdCopyWithPeriodUid, allocator, j2);
        this.mediaPeriodToChildMediaPeriodId.put(maskingMediaPeriodCreatePeriod, mediaPeriodIdCopyWithPeriodUid);
        return maskingMediaPeriodCreatePeriod;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource, com.google.android.exoplayer2.source.MediaSource
    @Nullable
    public Timeline getInitialTimeline() {
        return this.loopCount != Integer.MAX_VALUE ? new LoopingTimeline(this.maskingMediaSource.getTimeline(), this.loopCount) : new InfinitelyLoopingTimeline(this.maskingMediaSource.getTimeline());
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.maskingMediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource, com.google.android.exoplayer2.source.MediaSource
    public boolean isSingleWindow() {
        return false;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        prepareChildSource(null, this.maskingMediaSource);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.maskingMediaSource.releasePeriod(mediaPeriod);
        MediaSource.MediaPeriodId mediaPeriodIdRemove = this.mediaPeriodToChildMediaPeriodId.remove(mediaPeriod);
        if (mediaPeriodIdRemove != null) {
            this.childMediaPeriodIdToMediaPeriodId.remove(mediaPeriodIdRemove);
        }
    }

    public LoopingMediaSource(MediaSource mediaSource, int i2) {
        Assertions.checkArgument(i2 > 0);
        this.maskingMediaSource = new MaskingMediaSource(mediaSource, false);
        this.loopCount = i2;
        this.childMediaPeriodIdToMediaPeriodId = new HashMap();
        this.mediaPeriodToChildMediaPeriodId = new HashMap();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Void r2, MediaSource.MediaPeriodId mediaPeriodId) {
        return this.loopCount != Integer.MAX_VALUE ? this.childMediaPeriodIdToMediaPeriodId.get(mediaPeriodId) : mediaPeriodId;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    /* renamed from: onChildSourceInfoRefreshed, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public void lambda$prepareChildSource$0(Void r12, MediaSource mediaSource, Timeline timeline) {
        refreshSourceInfo(this.loopCount != Integer.MAX_VALUE ? new LoopingTimeline(timeline, this.loopCount) : new InfinitelyLoopingTimeline(timeline));
    }
}
