package com.google.android.exoplayer2.extractor.jpeg;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;

/* loaded from: classes3.dex */
public final class StartOffsetExtractorOutput implements ExtractorOutput {
    private final ExtractorOutput extractorOutput;
    private final long startOffset;

    public StartOffsetExtractorOutput(long j2, ExtractorOutput extractorOutput) {
        this.startOffset = j2;
        this.extractorOutput = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        this.extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(final SeekMap seekMap) {
        this.extractorOutput.seekMap(new SeekMap() { // from class: com.google.android.exoplayer2.extractor.jpeg.StartOffsetExtractorOutput.1
            @Override // com.google.android.exoplayer2.extractor.SeekMap
            public long getDurationUs() {
                return seekMap.getDurationUs();
            }

            @Override // com.google.android.exoplayer2.extractor.SeekMap
            public SeekMap.SeekPoints getSeekPoints(long j2) {
                SeekMap.SeekPoints seekPoints = seekMap.getSeekPoints(j2);
                SeekPoint seekPoint = seekPoints.first;
                SeekPoint seekPoint2 = new SeekPoint(seekPoint.timeUs, seekPoint.position + StartOffsetExtractorOutput.this.startOffset);
                SeekPoint seekPoint3 = seekPoints.second;
                return new SeekMap.SeekPoints(seekPoint2, new SeekPoint(seekPoint3.timeUs, seekPoint3.position + StartOffsetExtractorOutput.this.startOffset));
            }

            @Override // com.google.android.exoplayer2.extractor.SeekMap
            public boolean isSeekable() {
                return seekMap.isSeekable();
            }
        });
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int i2, int i3) {
        return this.extractorOutput.track(i2, i3);
    }
}
