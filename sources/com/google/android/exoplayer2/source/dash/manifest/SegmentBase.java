package com.google.android.exoplayer2.source.dash.manifest;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Util;
import com.google.common.math.BigIntegerMath;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class SegmentBase {

    @Nullable
    final RangedUri initialization;
    final long presentationTimeOffset;
    final long timescale;

    public static abstract class MultiSegmentBase extends SegmentBase {

        @VisibleForTesting
        final long availabilityTimeOffsetUs;
        final long duration;
        private final long periodStartUnixTimeUs;

        @Nullable
        final List<SegmentTimelineElement> segmentTimeline;
        final long startNumber;
        private final long timeShiftBufferDepthUs;

        public MultiSegmentBase(@Nullable RangedUri rangedUri, long j2, long j3, long j4, long j5, @Nullable List<SegmentTimelineElement> list, long j6, long j7, long j8) {
            super(rangedUri, j2, j3);
            this.startNumber = j4;
            this.duration = j5;
            this.segmentTimeline = list;
            this.availabilityTimeOffsetUs = j6;
            this.timeShiftBufferDepthUs = j7;
            this.periodStartUnixTimeUs = j8;
        }

        public long getAvailableSegmentCount(long j2, long j3) {
            long segmentCount = getSegmentCount(j2);
            return segmentCount != -1 ? segmentCount : (int) (getSegmentNum((j3 - this.periodStartUnixTimeUs) + this.availabilityTimeOffsetUs, j2) - getFirstAvailableSegmentNum(j2, j3));
        }

        public long getFirstAvailableSegmentNum(long j2, long j3) {
            if (getSegmentCount(j2) == -1) {
                long j4 = this.timeShiftBufferDepthUs;
                if (j4 != C.TIME_UNSET) {
                    return Math.max(getFirstSegmentNum(), getSegmentNum((j3 - this.periodStartUnixTimeUs) - j4, j2));
                }
            }
            return getFirstSegmentNum();
        }

        public long getFirstSegmentNum() {
            return this.startNumber;
        }

        public long getNextSegmentAvailableTimeUs(long j2, long j3) {
            if (this.segmentTimeline != null) {
                return C.TIME_UNSET;
            }
            long firstAvailableSegmentNum = getFirstAvailableSegmentNum(j2, j3) + getAvailableSegmentCount(j2, j3);
            return (getSegmentTimeUs(firstAvailableSegmentNum) + getSegmentDurationUs(firstAvailableSegmentNum, j2)) - this.availabilityTimeOffsetUs;
        }

        public abstract long getSegmentCount(long j2);

        public final long getSegmentDurationUs(long j2, long j3) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            if (list != null) {
                return (list.get((int) (j2 - this.startNumber)).duration * 1000000) / this.timescale;
            }
            long segmentCount = getSegmentCount(j3);
            return (segmentCount == -1 || j2 != (getFirstSegmentNum() + segmentCount) - 1) ? (this.duration * 1000000) / this.timescale : j3 - getSegmentTimeUs(j2);
        }

        public long getSegmentNum(long j2, long j3) {
            long firstSegmentNum = getFirstSegmentNum();
            long segmentCount = getSegmentCount(j3);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            if (this.segmentTimeline == null) {
                long j4 = this.startNumber + (j2 / ((this.duration * 1000000) / this.timescale));
                return j4 < firstSegmentNum ? firstSegmentNum : segmentCount == -1 ? j4 : Math.min(j4, (firstSegmentNum + segmentCount) - 1);
            }
            long j5 = (segmentCount + firstSegmentNum) - 1;
            long j6 = firstSegmentNum;
            while (j6 <= j5) {
                long j7 = ((j5 - j6) / 2) + j6;
                long segmentTimeUs = getSegmentTimeUs(j7);
                if (segmentTimeUs < j2) {
                    j6 = j7 + 1;
                } else {
                    if (segmentTimeUs <= j2) {
                        return j7;
                    }
                    j5 = j7 - 1;
                }
            }
            return j6 == firstSegmentNum ? j6 : j5;
        }

        public final long getSegmentTimeUs(long j2) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            return Util.scaleLargeTimestamp(list != null ? list.get((int) (j2 - this.startNumber)).startTime - this.presentationTimeOffset : (j2 - this.startNumber) * this.duration, 1000000L, this.timescale);
        }

        public abstract RangedUri getSegmentUrl(Representation representation, long j2);

        public boolean isExplicit() {
            return this.segmentTimeline != null;
        }
    }

    public static final class SegmentList extends MultiSegmentBase {

        @Nullable
        final List<RangedUri> mediaSegments;

        public SegmentList(RangedUri rangedUri, long j2, long j3, long j4, long j5, @Nullable List<SegmentTimelineElement> list, long j6, @Nullable List<RangedUri> list2, long j7, long j8) {
            super(rangedUri, j2, j3, j4, j5, list, j6, j7, j8);
            this.mediaSegments = list2;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j2) {
            return this.mediaSegments.size();
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j2) {
            return this.mediaSegments.get((int) (j2 - this.startNumber));
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public boolean isExplicit() {
            return true;
        }
    }

    public static final class SegmentTemplate extends MultiSegmentBase {
        final long endNumber;

        @Nullable
        final UrlTemplate initializationTemplate;

        @Nullable
        final UrlTemplate mediaTemplate;

        public SegmentTemplate(RangedUri rangedUri, long j2, long j3, long j4, long j5, long j6, @Nullable List<SegmentTimelineElement> list, long j7, @Nullable UrlTemplate urlTemplate, @Nullable UrlTemplate urlTemplate2, long j8, long j9) {
            super(rangedUri, j2, j3, j4, j6, list, j7, j8, j9);
            this.initializationTemplate = urlTemplate;
            this.mediaTemplate = urlTemplate2;
            this.endNumber = j5;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase
        @Nullable
        public RangedUri getInitialization(Representation representation) {
            UrlTemplate urlTemplate = this.initializationTemplate;
            if (urlTemplate == null) {
                return super.getInitialization(representation);
            }
            Format format = representation.format;
            return new RangedUri(urlTemplate.buildUri(format.id, 0L, format.bitrate, 0L), 0L, -1L);
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public long getSegmentCount(long j2) {
            if (this.segmentTimeline != null) {
                return r0.size();
            }
            long j3 = this.endNumber;
            if (j3 != -1) {
                return (j3 - this.startNumber) + 1;
            }
            if (j2 != C.TIME_UNSET) {
                return BigIntegerMath.divide(BigInteger.valueOf(j2).multiply(BigInteger.valueOf(this.timescale)), BigInteger.valueOf(this.duration).multiply(BigInteger.valueOf(1000000L)), RoundingMode.CEILING).longValue();
            }
            return -1L;
        }

        @Override // com.google.android.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase
        public RangedUri getSegmentUrl(Representation representation, long j2) {
            List<SegmentTimelineElement> list = this.segmentTimeline;
            long j3 = list != null ? list.get((int) (j2 - this.startNumber)).startTime : (j2 - this.startNumber) * this.duration;
            UrlTemplate urlTemplate = this.mediaTemplate;
            Format format = representation.format;
            return new RangedUri(urlTemplate.buildUri(format.id, j2, format.bitrate, j3), 0L, -1L);
        }
    }

    public static final class SegmentTimelineElement {
        final long duration;
        final long startTime;

        public SegmentTimelineElement(long j2, long j3) {
            this.startTime = j2;
            this.duration = j3;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SegmentTimelineElement.class != obj.getClass()) {
                return false;
            }
            SegmentTimelineElement segmentTimelineElement = (SegmentTimelineElement) obj;
            return this.startTime == segmentTimelineElement.startTime && this.duration == segmentTimelineElement.duration;
        }

        public int hashCode() {
            return (((int) this.startTime) * 31) + ((int) this.duration);
        }
    }

    public SegmentBase(@Nullable RangedUri rangedUri, long j2, long j3) {
        this.initialization = rangedUri;
        this.timescale = j2;
        this.presentationTimeOffset = j3;
    }

    @Nullable
    public RangedUri getInitialization(Representation representation) {
        return this.initialization;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.presentationTimeOffset, 1000000L, this.timescale);
    }

    public static class SingleSegmentBase extends SegmentBase {
        final long indexLength;
        final long indexStart;

        public SingleSegmentBase(@Nullable RangedUri rangedUri, long j2, long j3, long j4, long j5) {
            super(rangedUri, j2, j3);
            this.indexStart = j4;
            this.indexLength = j5;
        }

        @Nullable
        public RangedUri getIndex() {
            long j2 = this.indexLength;
            if (j2 <= 0) {
                return null;
            }
            return new RangedUri(null, this.indexStart, j2);
        }

        public SingleSegmentBase() {
            this(null, 1L, 0L, 0L, 0L);
        }
    }
}
