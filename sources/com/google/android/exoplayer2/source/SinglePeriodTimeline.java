package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes3.dex */
public final class SinglePeriodTimeline extends Timeline {
    private final long elapsedRealtimeEpochOffsetMs;
    private final boolean isDynamic;
    private final boolean isSeekable;

    @Nullable
    private final MediaItem.LiveConfiguration liveConfiguration;

    @Nullable
    private final Object manifest;

    @Nullable
    private final MediaItem mediaItem;
    private final long periodDurationUs;
    private final long presentationStartTimeMs;
    private final boolean suppressPositionProjection;
    private final long windowDefaultStartPositionUs;
    private final long windowDurationUs;
    private final long windowPositionInPeriodUs;
    private final long windowStartTimeMs;
    private static final Object UID = new Object();
    private static final MediaItem MEDIA_ITEM = new MediaItem.Builder().setMediaId("SinglePeriodTimeline").setUri(Uri.EMPTY).build();

    @Deprecated
    public SinglePeriodTimeline(long j2, boolean z2, boolean z3, boolean z4, @Nullable Object obj, @Nullable Object obj2) {
        this(j2, j2, 0L, 0L, z2, z3, z4, obj, obj2);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getIndexOfPeriod(Object obj) {
        return UID.equals(obj) ? 0 : -1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
        Assertions.checkIndex(i2, 0, 1);
        return period.set(null, z2 ? UID : null, 0, this.periodDurationUs, -this.windowPositionInPeriodUs);
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getPeriodCount() {
        return 1;
    }

    @Override // com.google.android.exoplayer2.Timeline
    public Object getUidOfPeriod(int i2) {
        Assertions.checkIndex(i2, 0, 1);
        return UID;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002e A[PHI: r1
      0x002e: PHI (r1v2 long) = (r1v1 long), (r1v1 long), (r1v1 long), (r1v6 long) binds: [B:3:0x000d, B:5:0x0011, B:7:0x0017, B:12:0x002b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.exoplayer2.Timeline
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.Timeline.Window getWindow(int r25, com.google.android.exoplayer2.Timeline.Window r26, long r27) {
        /*
            r24 = this;
            r0 = r24
            r1 = 0
            r2 = 1
            r3 = r25
            com.google.android.exoplayer2.util.Assertions.checkIndex(r3, r1, r2)
            long r1 = r0.windowDefaultStartPositionUs
            boolean r14 = r0.isDynamic
            if (r14 == 0) goto L2e
            boolean r3 = r0.suppressPositionProjection
            if (r3 != 0) goto L2e
            r3 = 0
            int r3 = (r27 > r3 ? 1 : (r27 == r3 ? 0 : -1))
            if (r3 == 0) goto L2e
            long r3 = r0.windowDurationUs
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L27
        L24:
            r16 = r5
            goto L30
        L27:
            long r1 = r1 + r27
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 <= 0) goto L2e
            goto L24
        L2e:
            r16 = r1
        L30:
            java.lang.Object r4 = com.google.android.exoplayer2.Timeline.Window.SINGLE_WINDOW_UID
            com.google.android.exoplayer2.MediaItem r5 = r0.mediaItem
            java.lang.Object r6 = r0.manifest
            long r7 = r0.presentationStartTimeMs
            long r9 = r0.windowStartTimeMs
            long r11 = r0.elapsedRealtimeEpochOffsetMs
            boolean r13 = r0.isSeekable
            com.google.android.exoplayer2.MediaItem$LiveConfiguration r15 = r0.liveConfiguration
            long r1 = r0.windowDurationUs
            r18 = r1
            r20 = 0
            r21 = 0
            long r1 = r0.windowPositionInPeriodUs
            r22 = r1
            r3 = r26
            com.google.android.exoplayer2.Timeline$Window r1 = r3.set(r4, r5, r6, r7, r9, r11, r13, r14, r15, r16, r18, r20, r21, r22)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.SinglePeriodTimeline.getWindow(int, com.google.android.exoplayer2.Timeline$Window, long):com.google.android.exoplayer2.Timeline$Window");
    }

    @Override // com.google.android.exoplayer2.Timeline
    public int getWindowCount() {
        return 1;
    }

    public SinglePeriodTimeline(long j2, boolean z2, boolean z3, boolean z4, @Nullable Object obj, MediaItem mediaItem) {
        this(j2, j2, 0L, 0L, z2, z3, z4, obj, mediaItem);
    }

    @Deprecated
    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, boolean z2, boolean z3, boolean z4, @Nullable Object obj, @Nullable Object obj2) {
        this(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, j2, j3, j4, j5, z2, z3, z4, obj, obj2);
    }

    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, boolean z2, boolean z3, boolean z4, @Nullable Object obj, MediaItem mediaItem) {
        this(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, j2, j3, j4, j5, z2, z3, false, obj, mediaItem, z4 ? mediaItem.liveConfiguration : null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    @Deprecated
    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, long j6, long j7, long j8, boolean z2, boolean z3, boolean z4, @Nullable Object obj, @Nullable Object obj2) {
        MediaItem mediaItem = MEDIA_ITEM;
        this(j2, j3, j4, j5, j6, j7, j8, z2, z3, false, obj, mediaItem.buildUpon().setTag(obj2).build(), z4 ? mediaItem.liveConfiguration : null);
    }

    @Deprecated
    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, long j6, long j7, long j8, boolean z2, boolean z3, @Nullable Object obj, MediaItem mediaItem, @Nullable MediaItem.LiveConfiguration liveConfiguration) {
        this(j2, j3, j4, j5, j6, j7, j8, z2, z3, false, obj, mediaItem, liveConfiguration);
    }

    public SinglePeriodTimeline(long j2, long j3, long j4, long j5, long j6, long j7, long j8, boolean z2, boolean z3, boolean z4, @Nullable Object obj, MediaItem mediaItem, @Nullable MediaItem.LiveConfiguration liveConfiguration) {
        this.presentationStartTimeMs = j2;
        this.windowStartTimeMs = j3;
        this.elapsedRealtimeEpochOffsetMs = j4;
        this.periodDurationUs = j5;
        this.windowDurationUs = j6;
        this.windowPositionInPeriodUs = j7;
        this.windowDefaultStartPositionUs = j8;
        this.isSeekable = z2;
        this.isDynamic = z3;
        this.suppressPositionProjection = z4;
        this.manifest = obj;
        this.mediaItem = (MediaItem) Assertions.checkNotNull(mediaItem);
        this.liveConfiguration = liveConfiguration;
    }
}
