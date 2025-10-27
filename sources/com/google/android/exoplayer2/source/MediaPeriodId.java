package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
public class MediaPeriodId {
    public final int adGroupIndex;
    public final int adIndexInAdGroup;
    public final int nextAdGroupIndex;
    public final Object periodUid;
    public final long windowSequenceNumber;

    public MediaPeriodId(Object obj) {
        this(obj, -1L);
    }

    public MediaPeriodId copyWithPeriodUid(Object obj) {
        return this.periodUid.equals(obj) ? this : new MediaPeriodId(obj, this.adGroupIndex, this.adIndexInAdGroup, this.windowSequenceNumber, this.nextAdGroupIndex);
    }

    public MediaPeriodId copyWithWindowSequenceNumber(long j2) {
        return this.windowSequenceNumber == j2 ? this : new MediaPeriodId(this.periodUid, this.adGroupIndex, this.adIndexInAdGroup, j2, this.nextAdGroupIndex);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaPeriodId)) {
            return false;
        }
        MediaPeriodId mediaPeriodId = (MediaPeriodId) obj;
        return this.periodUid.equals(mediaPeriodId.periodUid) && this.adGroupIndex == mediaPeriodId.adGroupIndex && this.adIndexInAdGroup == mediaPeriodId.adIndexInAdGroup && this.windowSequenceNumber == mediaPeriodId.windowSequenceNumber && this.nextAdGroupIndex == mediaPeriodId.nextAdGroupIndex;
    }

    public int hashCode() {
        return ((((((((R2.attr.bl_checkable_gradient_endColor + this.periodUid.hashCode()) * 31) + this.adGroupIndex) * 31) + this.adIndexInAdGroup) * 31) + ((int) this.windowSequenceNumber)) * 31) + this.nextAdGroupIndex;
    }

    public boolean isAd() {
        return this.adGroupIndex != -1;
    }

    public MediaPeriodId(Object obj, long j2) {
        this(obj, -1, -1, j2, -1);
    }

    public MediaPeriodId(Object obj, long j2, int i2) {
        this(obj, -1, -1, j2, i2);
    }

    public MediaPeriodId(Object obj, int i2, int i3, long j2) {
        this(obj, i2, i3, j2, -1);
    }

    public MediaPeriodId(MediaPeriodId mediaPeriodId) {
        this.periodUid = mediaPeriodId.periodUid;
        this.adGroupIndex = mediaPeriodId.adGroupIndex;
        this.adIndexInAdGroup = mediaPeriodId.adIndexInAdGroup;
        this.windowSequenceNumber = mediaPeriodId.windowSequenceNumber;
        this.nextAdGroupIndex = mediaPeriodId.nextAdGroupIndex;
    }

    private MediaPeriodId(Object obj, int i2, int i3, long j2, int i4) {
        this.periodUid = obj;
        this.adGroupIndex = i2;
        this.adIndexInAdGroup = i3;
        this.windowSequenceNumber = j2;
        this.nextAdGroupIndex = i4;
    }
}
