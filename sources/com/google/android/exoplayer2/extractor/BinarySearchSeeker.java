package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class BinarySearchSeeker {
    private static final long MAX_SKIP_BYTES = 262144;
    private final int minimumSearchRange;
    protected final BinarySearchSeekMap seekMap;

    @Nullable
    protected SeekOperationParams seekOperationParams;
    protected final TimestampSeeker timestampSeeker;

    public static class BinarySearchSeekMap implements SeekMap {
        private final long approxBytesPerFrame;
        private final long ceilingBytePosition;
        private final long ceilingTimePosition;
        private final long durationUs;
        private final long floorBytePosition;
        private final long floorTimePosition;
        private final SeekTimestampConverter seekTimestampConverter;

        public BinarySearchSeekMap(SeekTimestampConverter seekTimestampConverter, long j2, long j3, long j4, long j5, long j6, long j7) {
            this.seekTimestampConverter = seekTimestampConverter;
            this.durationUs = j2;
            this.floorTimePosition = j3;
            this.ceilingTimePosition = j4;
            this.floorBytePosition = j5;
            this.ceilingBytePosition = j6;
            this.approxBytesPerFrame = j7;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return this.durationUs;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long j2) {
            return new SeekMap.SeekPoints(new SeekPoint(j2, SeekOperationParams.calculateNextSearchBytePosition(this.seekTimestampConverter.timeUsToTargetTime(j2), this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame)));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        public long timeUsToTargetTime(long j2) {
            return this.seekTimestampConverter.timeUsToTargetTime(j2);
        }
    }

    public static final class DefaultSeekTimestampConverter implements SeekTimestampConverter {
        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.SeekTimestampConverter
        public long timeUsToTargetTime(long j2) {
            return j2;
        }
    }

    public static class SeekOperationParams {
        private final long approxBytesPerFrame;
        private long ceilingBytePosition;
        private long ceilingTimePosition;
        private long floorBytePosition;
        private long floorTimePosition;
        private long nextSearchBytePosition;
        private final long seekTimeUs;
        private final long targetTimePosition;

        public SeekOperationParams(long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
            this.seekTimeUs = j2;
            this.targetTimePosition = j3;
            this.floorTimePosition = j4;
            this.ceilingTimePosition = j5;
            this.floorBytePosition = j6;
            this.ceilingBytePosition = j7;
            this.approxBytesPerFrame = j8;
            this.nextSearchBytePosition = calculateNextSearchBytePosition(j3, j4, j5, j6, j7, j8);
        }

        public static long calculateNextSearchBytePosition(long j2, long j3, long j4, long j5, long j6, long j7) {
            if (j5 + 1 >= j6 || j3 + 1 >= j4) {
                return j5;
            }
            long j8 = (long) ((j2 - j3) * ((j6 - j5) / (j4 - j3)));
            return Util.constrainValue(((j8 + j5) - j7) - (j8 / 20), j5, j6 - 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getCeilingBytePosition() {
            return this.ceilingBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getFloorBytePosition() {
            return this.floorBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getNextSearchBytePosition() {
            return this.nextSearchBytePosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getSeekTimeUs() {
            return this.seekTimeUs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getTargetTimePosition() {
            return this.targetTimePosition;
        }

        private void updateNextSearchBytePosition() {
            this.nextSearchBytePosition = calculateNextSearchBytePosition(this.targetTimePosition, this.floorTimePosition, this.ceilingTimePosition, this.floorBytePosition, this.ceilingBytePosition, this.approxBytesPerFrame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSeekCeiling(long j2, long j3) {
            this.ceilingTimePosition = j2;
            this.ceilingBytePosition = j3;
            updateNextSearchBytePosition();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSeekFloor(long j2, long j3) {
            this.floorTimePosition = j2;
            this.floorBytePosition = j3;
            updateNextSearchBytePosition();
        }
    }

    public interface SeekTimestampConverter {
        long timeUsToTargetTime(long j2);
    }

    public static final class TimestampSearchResult {
        public static final TimestampSearchResult NO_TIMESTAMP_IN_RANGE_RESULT = new TimestampSearchResult(-3, C.TIME_UNSET, -1);
        public static final int TYPE_NO_TIMESTAMP = -3;
        public static final int TYPE_POSITION_OVERESTIMATED = -1;
        public static final int TYPE_POSITION_UNDERESTIMATED = -2;
        public static final int TYPE_TARGET_TIMESTAMP_FOUND = 0;
        private final long bytePositionToUpdate;
        private final long timestampToUpdate;
        private final int type;

        private TimestampSearchResult(int i2, long j2, long j3) {
            this.type = i2;
            this.timestampToUpdate = j2;
            this.bytePositionToUpdate = j3;
        }

        public static TimestampSearchResult overestimatedResult(long j2, long j3) {
            return new TimestampSearchResult(-1, j2, j3);
        }

        public static TimestampSearchResult targetFoundResult(long j2) {
            return new TimestampSearchResult(0, C.TIME_UNSET, j2);
        }

        public static TimestampSearchResult underestimatedResult(long j2, long j3) {
            return new TimestampSearchResult(-2, j2, j3);
        }
    }

    public interface TimestampSeeker {
        void onSeekFinished();

        TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j2) throws IOException;
    }

    public BinarySearchSeeker(SeekTimestampConverter seekTimestampConverter, TimestampSeeker timestampSeeker, long j2, long j3, long j4, long j5, long j6, long j7, int i2) {
        this.timestampSeeker = timestampSeeker;
        this.minimumSearchRange = i2;
        this.seekMap = new BinarySearchSeekMap(seekTimestampConverter, j2, j3, j4, j5, j6, j7);
    }

    public SeekOperationParams createSeekParamsForTargetTimeUs(long j2) {
        return new SeekOperationParams(j2, this.seekMap.timeUsToTargetTime(j2), this.seekMap.floorTimePosition, this.seekMap.ceilingTimePosition, this.seekMap.floorBytePosition, this.seekMap.ceilingBytePosition, this.seekMap.approxBytesPerFrame);
    }

    public final SeekMap getSeekMap() {
        return this.seekMap;
    }

    public int handlePendingSeek(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        while (true) {
            SeekOperationParams seekOperationParams = (SeekOperationParams) Assertions.checkStateNotNull(this.seekOperationParams);
            long floorBytePosition = seekOperationParams.getFloorBytePosition();
            long ceilingBytePosition = seekOperationParams.getCeilingBytePosition();
            long nextSearchBytePosition = seekOperationParams.getNextSearchBytePosition();
            if (ceilingBytePosition - floorBytePosition <= this.minimumSearchRange) {
                markSeekOperationFinished(false, floorBytePosition);
                return seekToPosition(extractorInput, floorBytePosition, positionHolder);
            }
            if (!skipInputUntilPosition(extractorInput, nextSearchBytePosition)) {
                return seekToPosition(extractorInput, nextSearchBytePosition, positionHolder);
            }
            extractorInput.resetPeekPosition();
            TimestampSearchResult timestampSearchResultSearchForTimestamp = this.timestampSeeker.searchForTimestamp(extractorInput, seekOperationParams.getTargetTimePosition());
            int i2 = timestampSearchResultSearchForTimestamp.type;
            if (i2 == -3) {
                markSeekOperationFinished(false, nextSearchBytePosition);
                return seekToPosition(extractorInput, nextSearchBytePosition, positionHolder);
            }
            if (i2 == -2) {
                seekOperationParams.updateSeekFloor(timestampSearchResultSearchForTimestamp.timestampToUpdate, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
            } else {
                if (i2 != -1) {
                    if (i2 != 0) {
                        throw new IllegalStateException("Invalid case");
                    }
                    skipInputUntilPosition(extractorInput, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
                    markSeekOperationFinished(true, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
                    return seekToPosition(extractorInput, timestampSearchResultSearchForTimestamp.bytePositionToUpdate, positionHolder);
                }
                seekOperationParams.updateSeekCeiling(timestampSearchResultSearchForTimestamp.timestampToUpdate, timestampSearchResultSearchForTimestamp.bytePositionToUpdate);
            }
        }
    }

    public final boolean isSeeking() {
        return this.seekOperationParams != null;
    }

    public final void markSeekOperationFinished(boolean z2, long j2) {
        this.seekOperationParams = null;
        this.timestampSeeker.onSeekFinished();
        onSeekOperationFinished(z2, j2);
    }

    public void onSeekOperationFinished(boolean z2, long j2) {
    }

    public final int seekToPosition(ExtractorInput extractorInput, long j2, PositionHolder positionHolder) {
        if (j2 == extractorInput.getPosition()) {
            return 0;
        }
        positionHolder.position = j2;
        return 1;
    }

    public final void setSeekTargetUs(long j2) {
        SeekOperationParams seekOperationParams = this.seekOperationParams;
        if (seekOperationParams == null || seekOperationParams.getSeekTimeUs() != j2) {
            this.seekOperationParams = createSeekParamsForTargetTimeUs(j2);
        }
    }

    public final boolean skipInputUntilPosition(ExtractorInput extractorInput, long j2) throws IOException {
        long position = j2 - extractorInput.getPosition();
        if (position < 0 || position > 262144) {
            return false;
        }
        extractorInput.skipFully((int) position);
        return true;
    }
}
