package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public interface ChunkExtractor {

    public interface Factory {
        @Nullable
        ChunkExtractor createProgressiveMediaExtractor(int i2, Format format, boolean z2, List<Format> list, @Nullable TrackOutput trackOutput);
    }

    public interface TrackOutputProvider {
        TrackOutput track(int i2, int i3);
    }

    @Nullable
    ChunkIndex getChunkIndex();

    @Nullable
    Format[] getSampleFormats();

    void init(@Nullable TrackOutputProvider trackOutputProvider, long j2, long j3);

    boolean read(ExtractorInput extractorInput) throws IOException;

    void release();
}
