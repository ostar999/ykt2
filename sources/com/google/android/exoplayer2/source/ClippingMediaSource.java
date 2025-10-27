package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class ClippingMediaSource extends CompositeMediaSource<Void> {
    private final boolean allowDynamicClippingUpdates;

    @Nullable
    private IllegalClippingException clippingError;

    @Nullable
    private ClippingTimeline clippingTimeline;
    private final boolean enableInitialDiscontinuity;
    private final long endUs;
    private final ArrayList<ClippingMediaPeriod> mediaPeriods;
    private final MediaSource mediaSource;
    private long periodEndUs;
    private long periodStartUs;
    private final boolean relativeToDefaultPosition;
    private final long startUs;
    private final Timeline.Window window;

    public static final class ClippingTimeline extends ForwardingTimeline {
        private final long durationUs;
        private final long endUs;
        private final boolean isDynamic;
        private final long startUs;

        public ClippingTimeline(Timeline timeline, long j2, long j3) throws IllegalClippingException {
            super(timeline);
            boolean z2 = false;
            if (timeline.getPeriodCount() != 1) {
                throw new IllegalClippingException(0);
            }
            Timeline.Window window = timeline.getWindow(0, new Timeline.Window());
            long jMax = Math.max(0L, j2);
            if (!window.isPlaceholder && jMax != 0 && !window.isSeekable) {
                throw new IllegalClippingException(1);
            }
            long jMax2 = j3 == Long.MIN_VALUE ? window.durationUs : Math.max(0L, j3);
            long j4 = window.durationUs;
            if (j4 != C.TIME_UNSET) {
                jMax2 = jMax2 > j4 ? j4 : jMax2;
                if (jMax > jMax2) {
                    throw new IllegalClippingException(2);
                }
            }
            this.startUs = jMax;
            this.endUs = jMax2;
            this.durationUs = jMax2 == C.TIME_UNSET ? -9223372036854775807L : jMax2 - jMax;
            if (window.isDynamic && (jMax2 == C.TIME_UNSET || (j4 != C.TIME_UNSET && jMax2 == j4))) {
                z2 = true;
            }
            this.isDynamic = z2;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
            this.timeline.getPeriod(0, period, z2);
            long positionInWindowUs = period.getPositionInWindowUs() - this.startUs;
            long j2 = this.durationUs;
            return period.set(period.id, period.uid, 0, j2 == C.TIME_UNSET ? -9223372036854775807L : j2 - positionInWindowUs, positionInWindowUs);
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
            this.timeline.getWindow(0, window, 0L);
            long j3 = window.positionInFirstPeriodUs;
            long j4 = this.startUs;
            window.positionInFirstPeriodUs = j3 + j4;
            window.durationUs = this.durationUs;
            window.isDynamic = this.isDynamic;
            long j5 = window.defaultPositionUs;
            if (j5 != C.TIME_UNSET) {
                long jMax = Math.max(j5, j4);
                window.defaultPositionUs = jMax;
                long j6 = this.endUs;
                if (j6 != C.TIME_UNSET) {
                    jMax = Math.min(jMax, j6);
                }
                window.defaultPositionUs = jMax - this.startUs;
            }
            long jUsToMs = Util.usToMs(this.startUs);
            long j7 = window.presentationStartTimeMs;
            if (j7 != C.TIME_UNSET) {
                window.presentationStartTimeMs = j7 + jUsToMs;
            }
            long j8 = window.windowStartTimeMs;
            if (j8 != C.TIME_UNSET) {
                window.windowStartTimeMs = j8 + jUsToMs;
            }
            return window;
        }
    }

    public static final class IllegalClippingException extends IOException {
        public static final int REASON_INVALID_PERIOD_COUNT = 0;
        public static final int REASON_NOT_SEEKABLE_TO_START = 1;
        public static final int REASON_START_EXCEEDS_END = 2;
        public final int reason;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public IllegalClippingException(int i2) {
            String strValueOf = String.valueOf(getReasonDescription(i2));
            super(strValueOf.length() != 0 ? "Illegal clipping: ".concat(strValueOf) : new String("Illegal clipping: "));
            this.reason = i2;
        }

        private static String getReasonDescription(int i2) {
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? "unknown" : "start exceeds end" : "not seekable to start" : "invalid period count";
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2, long j3) {
        this(mediaSource, j2, j3, true, false, false);
    }

    private void refreshClippedTimeline(Timeline timeline) {
        long j2;
        long j3;
        timeline.getWindow(0, this.window);
        long positionInFirstPeriodUs = this.window.getPositionInFirstPeriodUs();
        if (this.clippingTimeline == null || this.mediaPeriods.isEmpty() || this.allowDynamicClippingUpdates) {
            long j4 = this.startUs;
            long j5 = this.endUs;
            if (this.relativeToDefaultPosition) {
                long defaultPositionUs = this.window.getDefaultPositionUs();
                j4 += defaultPositionUs;
                j5 += defaultPositionUs;
            }
            this.periodStartUs = positionInFirstPeriodUs + j4;
            this.periodEndUs = this.endUs != Long.MIN_VALUE ? positionInFirstPeriodUs + j5 : Long.MIN_VALUE;
            int size = this.mediaPeriods.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mediaPeriods.get(i2).updateClipping(this.periodStartUs, this.periodEndUs);
            }
            j2 = j4;
            j3 = j5;
        } else {
            long j6 = this.periodStartUs - positionInFirstPeriodUs;
            j3 = this.endUs != Long.MIN_VALUE ? this.periodEndUs - positionInFirstPeriodUs : Long.MIN_VALUE;
            j2 = j6;
        }
        try {
            ClippingTimeline clippingTimeline = new ClippingTimeline(timeline, j2, j3);
            this.clippingTimeline = clippingTimeline;
            refreshSourceInfo(clippingTimeline);
        } catch (IllegalClippingException e2) {
            this.clippingError = e2;
            for (int i3 = 0; i3 < this.mediaPeriods.size(); i3++) {
                this.mediaPeriods.get(i3).setClippingError(this.clippingError);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        ClippingMediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(this.mediaSource.createPeriod(mediaPeriodId, allocator, j2), this.enableInitialDiscontinuity, this.periodStartUs, this.periodEndUs);
        this.mediaPeriods.add(clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        IllegalClippingException illegalClippingException = this.clippingError;
        if (illegalClippingException != null) {
            throw illegalClippingException;
        }
        super.maybeThrowSourceInfoRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        prepareChildSource(null, this.mediaSource);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.mediaPeriods.remove(mediaPeriod));
        this.mediaSource.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
        if (!this.mediaPeriods.isEmpty() || this.allowDynamicClippingUpdates) {
            return;
        }
        refreshClippedTimeline(((ClippingTimeline) Assertions.checkNotNull(this.clippingTimeline)).timeline);
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.clippingError = null;
        this.clippingTimeline = null;
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2) {
        this(mediaSource, 0L, j2, true, false, true);
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    /* renamed from: onChildSourceInfoRefreshed, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public void lambda$prepareChildSource$0(Void r12, MediaSource mediaSource, Timeline timeline) {
        if (this.clippingError != null) {
            return;
        }
        refreshClippedTimeline(timeline);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j2, long j3, boolean z2, boolean z3, boolean z4) {
        Assertions.checkArgument(j2 >= 0);
        this.mediaSource = (MediaSource) Assertions.checkNotNull(mediaSource);
        this.startUs = j2;
        this.endUs = j3;
        this.enableInitialDiscontinuity = z2;
        this.allowDynamicClippingUpdates = z3;
        this.relativeToDefaultPosition = z4;
        this.mediaPeriods = new ArrayList<>();
        this.window = new Timeline.Window();
    }
}
