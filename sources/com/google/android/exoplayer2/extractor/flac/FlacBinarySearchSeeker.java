package com.google.android.exoplayer2.extractor.flac;

import com.google.android.exoplayer2.extractor.BinarySearchSeeker;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.FlacFrameReader;
import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes3.dex */
final class FlacBinarySearchSeeker extends BinarySearchSeeker {

    public static final class FlacTimestampSeeker implements BinarySearchSeeker.TimestampSeeker {
        private final FlacStreamMetadata flacStreamMetadata;
        private final int frameStartMarker;
        private final FlacFrameReader.SampleNumberHolder sampleNumberHolder;

        private long findNextFrame(ExtractorInput extractorInput) throws IOException {
            while (extractorInput.getPeekPosition() < extractorInput.getLength() - 6 && !FlacFrameReader.checkFrameHeaderFromPeek(extractorInput, this.flacStreamMetadata, this.frameStartMarker, this.sampleNumberHolder)) {
                extractorInput.advancePeekPosition(1);
            }
            if (extractorInput.getPeekPosition() < extractorInput.getLength() - 6) {
                return this.sampleNumberHolder.sampleNumber;
            }
            extractorInput.advancePeekPosition((int) (extractorInput.getLength() - extractorInput.getPeekPosition()));
            return this.flacStreamMetadata.totalSamples;
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public /* synthetic */ void onSeekFinished() {
            com.google.android.exoplayer2.extractor.a.a(this);
        }

        @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.TimestampSeeker
        public BinarySearchSeeker.TimestampSearchResult searchForTimestamp(ExtractorInput extractorInput, long j2) throws IOException {
            long position = extractorInput.getPosition();
            long jFindNextFrame = findNextFrame(extractorInput);
            long peekPosition = extractorInput.getPeekPosition();
            extractorInput.advancePeekPosition(Math.max(6, this.flacStreamMetadata.minFrameSize));
            long jFindNextFrame2 = findNextFrame(extractorInput);
            return (jFindNextFrame > j2 || jFindNextFrame2 <= j2) ? jFindNextFrame2 <= j2 ? BinarySearchSeeker.TimestampSearchResult.underestimatedResult(jFindNextFrame2, extractorInput.getPeekPosition()) : BinarySearchSeeker.TimestampSearchResult.overestimatedResult(jFindNextFrame, position) : BinarySearchSeeker.TimestampSearchResult.targetFoundResult(peekPosition);
        }

        private FlacTimestampSeeker(FlacStreamMetadata flacStreamMetadata, int i2) {
            this.flacStreamMetadata = flacStreamMetadata;
            this.frameStartMarker = i2;
            this.sampleNumberHolder = new FlacFrameReader.SampleNumberHolder();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlacBinarySearchSeeker(final FlacStreamMetadata flacStreamMetadata, int i2, long j2, long j3) {
        super(new BinarySearchSeeker.SeekTimestampConverter() { // from class: com.google.android.exoplayer2.extractor.flac.a
            @Override // com.google.android.exoplayer2.extractor.BinarySearchSeeker.SeekTimestampConverter
            public final long timeUsToTargetTime(long j4) {
                return flacStreamMetadata.getSampleNumber(j4);
            }
        }, new FlacTimestampSeeker(flacStreamMetadata, i2), flacStreamMetadata.getDurationUs(), 0L, flacStreamMetadata.totalSamples, j2, j3, flacStreamMetadata.getApproxBytesPerFrame(), Math.max(6, flacStreamMetadata.minFrameSize));
        Objects.requireNonNull(flacStreamMetadata);
    }
}
