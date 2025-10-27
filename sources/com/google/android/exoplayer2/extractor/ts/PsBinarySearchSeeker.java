package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
final class PsBinarySearchSeeker extends BinarySearchSeeker {
    private static final int MINIMUM_SEARCH_RANGE_BYTES = 1000;
    private static final long SEEK_TOLERANCE_US = 100000;
    private static final int TIMESTAMP_SEARCH_BYTES = 20000;

    public static final class PsScrSeeker implements BinarySearchSeeker.TimestampSeeker {
        private final ParsableByteArray packetBuffer;
        private final TimestampAdjuster scrTimestampAdjuster;

        private BinarySearchSeeker.TimestampSearchResult searchForScrValueInBuffer(ParsableByteArray parsableByteArray, long j2, long j3) {
            int position = -1;
            int position2 = -1;
            long j4 = -9223372036854775807L;
            while (parsableByteArray.bytesLeft() >= 4) {
                if (PsBinarySearchSeeker.peekIntAtPosition(parsableByteArray.getData(), parsableByteArray.getPosition()) != 442) {
                    parsableByteArray.skipBytes(1);
                } else {
                    parsableByteArray.skipBytes(4);
                    long scrValueFromPack = PsDurationReader.readScrValueFromPack(parsableByteArray);
                    if (scrValueFromPack != C.TIME_UNSET) {
                        long jAdjustTsTimestamp = this.scrTimestampAdjuster.adjustTsTimestamp(scrValueFromPack);
                        if (jAdjustTsTimestamp > j2) {
                            return j4 == C.TIME_UNSET ? BinarySearchSeeker.TimestampSearchResult.overestimatedResult(jAdjustTsTimestamp, j3) : BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j3 + position2);
                        }
                        if (PsBinarySearchSeeker.SEEK_TOLERANCE_US + jAdjustTsTimestamp > j2) {
                            return BinarySearchSeeker.TimestampSearchResult.targetFoundResult(j3 + parsableByteArray.getPosition());
                        }
                        position2 = parsableByteArray.getPosition();
                        j4 = jAdjustTsTimestamp;
                    }
                    skipToEndOfCurrentPack(parsableByteArray);
                    position = parsableByteArray.getPosition();
                }
            }
            return j4 != C.TIME_UNSET ? BinarySearchSeeker.TimestampSearchResult.underestimatedResult(j4, j3 + position) : BinarySearchSeeker.TimestampSearchResult.NO_TIMESTAMP_IN_RANGE_RESULT;
        }

        private static void skipToEndOfCurrentPack(ParsableByteArray parsableByteArray) {
            int iPeekIntAtPosition;
            int iLimit = parsableByteArray.limit();
            if (parsableByteArray.bytesLeft() < 10) {
                parsableByteArray.setPosition(iLimit);
                return;
            }
            parsableByteArray.skipBytes(9);
            int unsignedByte = parsableByteArray.readUnsignedByte() & 7;
            if (parsableByteArray.bytesLeft() < unsignedByte) {
                parsableByteArray.setPosition(iLimit);
                return;
            }
            parsableByteArray.skipBytes(unsignedByte);
            if (parsableByteArray.bytesLeft() < 4) {
                parsableByteArray.setPosition(iLimit);
                return;
            }
            if (PsBinarySearchSeeker.peekIntAtPosition(parsableByteArray.getData(), parsableByteArray.getPosition()) == 443) {
                parsableByteArray.skipBytes(4);
                int unsignedShort = parsableByteArray.readUnsignedShort();
                if (parsableByteArray.bytesLeft() < unsignedShort) {
                    parsableByteArray.setPosition(iLimit);
                    return;
                }
                parsableByteArray.skipBytes(unsignedShort);
            }
            while (parsableByteArray.bytesLeft() >= 4 && (iPeekIntAtPosition = PsBinarySearchSeeker.peekIntAtPosition(parsableByteArray.getData(), parsableByteArray.getPosition())) != 442 && iPeekIntAtPosition != 441 && (iPeekIntAtPosition >>> 8) == 1) {
                parsableByteArray.skipBytes(4);
                if (parsableByteArray.bytesLeft() < 2) {
                    parsableByteArray.setPosition(iLimit);
                    return;
                }
                parsableByteArray.setPosition(Math.min(parsableByteArray.limit(), parsableByteArray.getPosition() + parsableByteArray.readUnsignedShort()));
            }
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public void onSeekFinished() {
            this.packetBuffer.reset(Util.EMPTY_BYTE_ARRAY);
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j2) throws IOException {
            long position = extractorInput.getPosition();
            int iMin = (int) Math.min(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, extractorInput.getLength() - position);
            this.packetBuffer.reset(iMin);
            extractorInput.peekFully(this.packetBuffer.getData(), 0, iMin);
            return searchForScrValueInBuffer(this.packetBuffer, j2, position);
        }

        private PsScrSeeker(TimestampAdjuster timestampAdjuster) {
            this.scrTimestampAdjuster = timestampAdjuster;
            this.packetBuffer = new ParsableByteArray();
        }
    }

    public PsBinarySearchSeeker(TimestampAdjuster timestampAdjuster, long j2, long j3) {
        super(new BinarySearchSeeker.DefaultSeekTimestampConverter(), new PsScrSeeker(timestampAdjuster), j2, 0L, j2 + 1, 0L, j3, 188L, 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int peekIntAtPosition(byte[] bArr, int i2) {
        return (bArr[i2 + 3] & 255) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
    }
}
