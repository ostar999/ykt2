package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes3.dex */
public abstract class MediaChunk extends Chunk {
    public final long chunkIndex;

    public MediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i2, @Nullable Object obj, long j2, long j3, long j4) {
        super(dataSource, dataSpec, 1, format, i2, obj, j2, j3);
        Assertions.checkNotNull(format);
        this.chunkIndex = j4;
    }

    public long getNextChunkIndex() {
        long j2 = this.chunkIndex;
        if (j2 != -1) {
            return 1 + j2;
        }
        return -1L;
    }

    public abstract boolean isLoadCompleted();
}
