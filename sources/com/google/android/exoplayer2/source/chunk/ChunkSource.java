package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public interface ChunkSource {
    long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters);

    void getNextChunk(long j2, long j3, List<? extends MediaChunk> list, ChunkHolder chunkHolder);

    int getPreferredQueueSize(long j2, List<? extends MediaChunk> list);

    void maybeThrowError() throws IOException;

    void onChunkLoadCompleted(Chunk chunk);

    boolean onChunkLoadError(Chunk chunk, boolean z2, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy);

    void release();

    boolean shouldCancelLoad(long j2, Chunk chunk, List<? extends MediaChunk> list);
}
