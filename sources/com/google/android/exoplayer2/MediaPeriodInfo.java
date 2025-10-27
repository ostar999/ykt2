package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
final class MediaPeriodInfo {
    public final long durationUs;
    public final long endPositionUs;
    public final MediaSource.MediaPeriodId id;
    public final boolean isFinal;
    public final boolean isFollowedByTransitionToSameStream;
    public final boolean isLastInTimelinePeriod;
    public final boolean isLastInTimelineWindow;
    public final long requestedContentPositionUs;
    public final long startPositionUs;

    public MediaPeriodInfo(MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3, long j4, long j5, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean z6 = false;
        Assertions.checkArgument(!z5 || z3);
        Assertions.checkArgument(!z4 || z3);
        if (!z2 || (!z3 && !z4 && !z5)) {
            z6 = true;
        }
        Assertions.checkArgument(z6);
        this.id = mediaPeriodId;
        this.startPositionUs = j2;
        this.requestedContentPositionUs = j3;
        this.endPositionUs = j4;
        this.durationUs = j5;
        this.isFollowedByTransitionToSameStream = z2;
        this.isLastInTimelinePeriod = z3;
        this.isLastInTimelineWindow = z4;
        this.isFinal = z5;
    }

    public MediaPeriodInfo copyWithRequestedContentPositionUs(long j2) {
        return j2 == this.requestedContentPositionUs ? this : new MediaPeriodInfo(this.id, this.startPositionUs, j2, this.endPositionUs, this.durationUs, this.isFollowedByTransitionToSameStream, this.isLastInTimelinePeriod, this.isLastInTimelineWindow, this.isFinal);
    }

    public MediaPeriodInfo copyWithStartPositionUs(long j2) {
        return j2 == this.startPositionUs ? this : new MediaPeriodInfo(this.id, j2, this.requestedContentPositionUs, this.endPositionUs, this.durationUs, this.isFollowedByTransitionToSameStream, this.isLastInTimelinePeriod, this.isLastInTimelineWindow, this.isFinal);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MediaPeriodInfo.class != obj.getClass()) {
            return false;
        }
        MediaPeriodInfo mediaPeriodInfo = (MediaPeriodInfo) obj;
        return this.startPositionUs == mediaPeriodInfo.startPositionUs && this.requestedContentPositionUs == mediaPeriodInfo.requestedContentPositionUs && this.endPositionUs == mediaPeriodInfo.endPositionUs && this.durationUs == mediaPeriodInfo.durationUs && this.isFollowedByTransitionToSameStream == mediaPeriodInfo.isFollowedByTransitionToSameStream && this.isLastInTimelinePeriod == mediaPeriodInfo.isLastInTimelinePeriod && this.isLastInTimelineWindow == mediaPeriodInfo.isLastInTimelineWindow && this.isFinal == mediaPeriodInfo.isFinal && Util.areEqual(this.id, mediaPeriodInfo.id);
    }

    public int hashCode() {
        return ((((((((((((((((R2.attr.bl_checkable_gradient_endColor + this.id.hashCode()) * 31) + ((int) this.startPositionUs)) * 31) + ((int) this.requestedContentPositionUs)) * 31) + ((int) this.endPositionUs)) * 31) + ((int) this.durationUs)) * 31) + (this.isFollowedByTransitionToSameStream ? 1 : 0)) * 31) + (this.isLastInTimelinePeriod ? 1 : 0)) * 31) + (this.isLastInTimelineWindow ? 1 : 0)) * 31) + (this.isFinal ? 1 : 0);
    }
}
