package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public final class MergingMediaSource extends CompositeMediaSource<Integer> {
    private static final MediaItem EMPTY_MEDIA_ITEM = new MediaItem.Builder().setMediaId("MergingMediaSource").build();
    private static final int PERIOD_COUNT_UNSET = -1;
    private final boolean adjustPeriodTimeOffsets;
    private final boolean clipDurations;
    private final Map<Object, Long> clippedDurationsUs;
    private final Multimap<Object, ClippingMediaPeriod> clippedMediaPeriods;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final MediaSource[] mediaSources;

    @Nullable
    private IllegalMergeException mergeError;
    private final ArrayList<MediaSource> pendingTimelineSources;
    private int periodCount;
    private long[][] periodTimeOffsetsUs;
    private final Timeline[] timelines;

    public static final class ClippedTimeline extends ForwardingTimeline {
        private final long[] periodDurationsUs;
        private final long[] windowDurationsUs;

        public ClippedTimeline(Timeline timeline, Map<Object, Long> map) {
            super(timeline);
            int windowCount = timeline.getWindowCount();
            this.windowDurationsUs = new long[timeline.getWindowCount()];
            Timeline.Window window = new Timeline.Window();
            for (int i2 = 0; i2 < windowCount; i2++) {
                this.windowDurationsUs[i2] = timeline.getWindow(i2, window).durationUs;
            }
            int periodCount = timeline.getPeriodCount();
            this.periodDurationsUs = new long[periodCount];
            Timeline.Period period = new Timeline.Period();
            for (int i3 = 0; i3 < periodCount; i3++) {
                timeline.getPeriod(i3, period, true);
                long jLongValue = ((Long) Assertions.checkNotNull(map.get(period.uid))).longValue();
                long[] jArr = this.periodDurationsUs;
                jLongValue = jLongValue == Long.MIN_VALUE ? period.durationUs : jLongValue;
                jArr[i3] = jLongValue;
                long j2 = period.durationUs;
                if (j2 != C.TIME_UNSET) {
                    long[] jArr2 = this.windowDurationsUs;
                    int i4 = period.windowIndex;
                    jArr2[i4] = jArr2[i4] - (j2 - jLongValue);
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
            super.getPeriod(i2, period, z2);
            period.durationUs = this.periodDurationsUs[i2];
            return period;
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.google.android.exoplayer2.Timeline.Window getWindow(int r5, com.google.android.exoplayer2.Timeline.Window r6, long r7) {
            /*
                r4 = this;
                super.getWindow(r5, r6, r7)
                long[] r7 = r4.windowDurationsUs
                r0 = r7[r5]
                r6.durationUs = r0
                r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
                int r5 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                if (r5 == 0) goto L1e
                long r2 = r6.defaultPositionUs
                int r5 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
                if (r5 != 0) goto L19
                goto L1e
            L19:
                long r7 = java.lang.Math.min(r2, r0)
                goto L20
            L1e:
                long r7 = r6.defaultPositionUs
            L20:
                r6.defaultPositionUs = r7
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.MergingMediaSource.ClippedTimeline.getWindow(int, com.google.android.exoplayer2.Timeline$Window, long):com.google.android.exoplayer2.Timeline$Window");
        }
    }

    public static final class IllegalMergeException extends IOException {
        public static final int REASON_PERIOD_COUNT_MISMATCH = 0;
        public final int reason;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        public IllegalMergeException(int i2) {
            this.reason = i2;
        }
    }

    public MergingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    private void computePeriodTimeOffsets() {
        Timeline.Period period = new Timeline.Period();
        for (int i2 = 0; i2 < this.periodCount; i2++) {
            long j2 = -this.timelines[0].getPeriod(i2, period).getPositionInWindowUs();
            int i3 = 1;
            while (true) {
                Timeline[] timelineArr = this.timelines;
                if (i3 < timelineArr.length) {
                    this.periodTimeOffsetsUs[i2][i3] = j2 - (-timelineArr[i3].getPeriod(i2, period).getPositionInWindowUs());
                    i3++;
                }
            }
        }
    }

    private void updateClippedDuration() {
        Timeline[] timelineArr;
        Timeline.Period period = new Timeline.Period();
        for (int i2 = 0; i2 < this.periodCount; i2++) {
            int i3 = 0;
            long j2 = Long.MIN_VALUE;
            while (true) {
                timelineArr = this.timelines;
                if (i3 >= timelineArr.length) {
                    break;
                }
                long durationUs = timelineArr[i3].getPeriod(i2, period).getDurationUs();
                if (durationUs != C.TIME_UNSET) {
                    long j3 = durationUs + this.periodTimeOffsetsUs[i2][i3];
                    if (j2 == Long.MIN_VALUE || j3 < j2) {
                        j2 = j3;
                    }
                }
                i3++;
            }
            Object uidOfPeriod = timelineArr[0].getUidOfPeriod(i2);
            this.clippedDurationsUs.put(uidOfPeriod, Long.valueOf(j2));
            Iterator<ClippingMediaPeriod> it = this.clippedMediaPeriods.get(uidOfPeriod).iterator();
            while (it.hasNext()) {
                it.next().updateClipping(0L, j2);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        int length = this.mediaSources.length;
        MediaPeriod[] mediaPeriodArr = new MediaPeriod[length];
        int indexOfPeriod = this.timelines[0].getIndexOfPeriod(mediaPeriodId.periodUid);
        for (int i2 = 0; i2 < length; i2++) {
            mediaPeriodArr[i2] = this.mediaSources[i2].createPeriod(mediaPeriodId.copyWithPeriodUid(this.timelines[i2].getUidOfPeriod(indexOfPeriod)), allocator, j2 - this.periodTimeOffsetsUs[indexOfPeriod][i2]);
        }
        MergingMediaPeriod mergingMediaPeriod = new MergingMediaPeriod(this.compositeSequenceableLoaderFactory, this.periodTimeOffsetsUs[indexOfPeriod], mediaPeriodArr);
        if (!this.clipDurations) {
            return mergingMediaPeriod;
        }
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(mergingMediaPeriod, true, 0L, ((Long) Assertions.checkNotNull(this.clippedDurationsUs.get(mediaPeriodId.periodUid))).longValue());
        this.clippedMediaPeriods.put(mediaPeriodId.periodUid, clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        MediaSource[] mediaSourceArr = this.mediaSources;
        return mediaSourceArr.length > 0 ? mediaSourceArr[0].getMediaItem() : EMPTY_MEDIA_ITEM;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalMergeException illegalMergeException = this.mergeError;
        if (illegalMergeException != null) {
            throw illegalMergeException;
        }
        super.maybeThrowSourceInfoRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        for (int i2 = 0; i2 < this.mediaSources.length; i2++) {
            prepareChildSource(Integer.valueOf(i2), this.mediaSources[i2]);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        if (this.clipDurations) {
            ClippingMediaPeriod clippingMediaPeriod = (ClippingMediaPeriod) mediaPeriod;
            Iterator<Map.Entry<Object, ClippingMediaPeriod>> it = this.clippedMediaPeriods.entries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Object, ClippingMediaPeriod> next = it.next();
                if (next.getValue().equals(clippingMediaPeriod)) {
                    this.clippedMediaPeriods.remove(next.getKey(), next.getValue());
                    break;
                }
            }
            mediaPeriod = clippingMediaPeriod.mediaPeriod;
        }
        MergingMediaPeriod mergingMediaPeriod = (MergingMediaPeriod) mediaPeriod;
        int i2 = 0;
        while (true) {
            MediaSource[] mediaSourceArr = this.mediaSources;
            if (i2 >= mediaSourceArr.length) {
                return;
            }
            mediaSourceArr[i2].releasePeriod(mergingMediaPeriod.getChildPeriod(i2));
            i2++;
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        Arrays.fill(this.timelines, (Object) null);
        this.periodCount = -1;
        this.mergeError = null;
        this.pendingTimelineSources.clear();
        Collections.addAll(this.pendingTimelineSources, this.mediaSources);
    }

    public MergingMediaSource(boolean z2, MediaSource... mediaSourceArr) {
        this(z2, false, mediaSourceArr);
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(Integer num, MediaSource.MediaPeriodId mediaPeriodId) {
        if (num.intValue() == 0) {
            return mediaPeriodId;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    /* renamed from: onChildSourceInfoRefreshed, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public void lambda$prepareChildSource$0(Integer num, MediaSource mediaSource, Timeline timeline) {
        if (this.mergeError != null) {
            return;
        }
        if (this.periodCount == -1) {
            this.periodCount = timeline.getPeriodCount();
        } else if (timeline.getPeriodCount() != this.periodCount) {
            this.mergeError = new IllegalMergeException(0);
            return;
        }
        if (this.periodTimeOffsetsUs.length == 0) {
            this.periodTimeOffsetsUs = (long[][]) Array.newInstance((Class<?>) Long.TYPE, this.periodCount, this.timelines.length);
        }
        this.pendingTimelineSources.remove(mediaSource);
        this.timelines[num.intValue()] = timeline;
        if (this.pendingTimelineSources.isEmpty()) {
            if (this.adjustPeriodTimeOffsets) {
                computePeriodTimeOffsets();
            }
            Timeline clippedTimeline = this.timelines[0];
            if (this.clipDurations) {
                updateClippedDuration();
                clippedTimeline = new ClippedTimeline(clippedTimeline, this.clippedDurationsUs);
            }
            refreshSourceInfo(clippedTimeline);
        }
    }

    public MergingMediaSource(boolean z2, boolean z3, MediaSource... mediaSourceArr) {
        this(z2, z3, new DefaultCompositeSequenceableLoaderFactory(), mediaSourceArr);
    }

    public MergingMediaSource(boolean z2, boolean z3, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, MediaSource... mediaSourceArr) {
        this.adjustPeriodTimeOffsets = z2;
        this.clipDurations = z3;
        this.mediaSources = mediaSourceArr;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.pendingTimelineSources = new ArrayList<>(Arrays.asList(mediaSourceArr));
        this.periodCount = -1;
        this.timelines = new Timeline[mediaSourceArr.length];
        this.periodTimeOffsetsUs = new long[0][];
        this.clippedDurationsUs = new HashMap();
        this.clippedMediaPeriods = MultimapBuilder.hashKeys().arrayListValues().build();
    }
}
