package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
final class TsBinarySearchSeeker extends BinarySearchSeeker {
    private static final int MINIMUM_SEARCH_RANGE_BYTES = 940;
    private static final long SEEK_TOLERANCE_US = 100000;

    public static final class TsPcrSeeker implements BinarySearchSeeker.TimestampSeeker {
        private final ParsableByteArray packetBuffer = new ParsableByteArray();
        private final int pcrPid;
        private final TimestampAdjuster pcrTimestampAdjuster;
        private final int timestampSearchBytes;

        public TsPcrSeeker(int i2, TimestampAdjuster timestampAdjuster, int i3) {
            this.pcrPid = i2;
            this.pcrTimestampAdjuster = timestampAdjuster;
            this.timestampSearchBytes = i3;
        }

        private BinarySearchSeeker.TimestampSearchResult searchForPcrValueInBuffer(ParsableByteArray parsableByteArray, long j2, long j3) {
            int iFindSyncBytePosition;
            int iFindSyncBytePosition2;
            int iLimit = parsableByteArray.limit();
            long j4 = -1;
            long j5 = -1;
            long j6 = -9223372036854775807L;
            while (parsableByteArray.bytesLeft() >= 188 && (iFindSyncBytePosition2 = (iFindSyncBytePosition = TsUtil.findSyncBytePosition(parsableByteArray.getData(), parsableByteArray.getPosition(), iLimit)) + 188) <= iLimit) {
                long pcrFromPacket = TsUtil.readPcrFromPacket(parsableByteArray, iFindSyncBytePosition, this.pcrPid);
                if (pcrFromPacket != C.TIME_UNSET) {
                    long jAdjustTsTimestamp = this.pcrTimestampAdjuster.adjustTsTimestamp(pcrFromPacket);
                    if (jAdjustTsTimestamp > j2) {
                        return j6 == C.TIME_UNSET ? BinarySearchSeeker.TimestampSearchResult.overestimatedResult(jAdjustTsTimestamp, j3) : BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j3 + j5);
                    }
                    if (TsBinarySearchSeeker.SEEK_TOLERANCE_US + jAdjustTsTimestamp > j2) {
                        return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j3 + iFindSyncBytePosition);
                    }
                    j5 = iFindSyncBytePosition;
                    j6 = jAdjustTsTimestamp;
                }
                parsableByteArray.setPosition(iFindSyncBytePosition2);
                j4 = iFindSyncBytePosition2;
            }
            return j6 != C.TIME_UNSET ? BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j6, j3 + j4) : BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public void onSeekFinished() {
            this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j2) throws IOException {
            long position = extractorInput.getPosition();
            int iMin = (int) Math.min(this.timestampSearchBytes, extractorInput.getLength() - position);
            this.packetBuffer.reset(iMin);
            extractorInput.peekFully(this.packetBuffer.getData(), 0, iMin);
            return searchForPcrValueInBuffer(this.packetBuffer, j2, position);
        }
    }

    public TsBinarySearchSeeker(TimestampAdjuster timestampAdjuster, long j2, long j3, int i2, int i3) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new TsPcrSeeker(i2, timestampAdjuster, i3), j2, 0L, j2 + 1, 0L, j3, 188L, 940);
    }
}
