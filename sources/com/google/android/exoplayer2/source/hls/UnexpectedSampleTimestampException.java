package com.google.android.exoplayer2.source.hls;

import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
final class UnexpectedSampleTimestampException extends IOException {
    public final long lastAcceptedSampleTimeUs;
    public final MediaChunk mediaChunk;
    public final long rejectedSampleTimeUs;

    public UnexpectedSampleTimestampException(MediaChunk mediaChunk, long j2, long j3) {
        long jUsToMs = Util.usToMs(j3);
        long j4 = mediaChunk.startTimeUs;
        long j5 = mediaChunk.endTimeUs;
        StringBuilder sb = new StringBuilder(103);
        sb.append("Unexpected sample timestamp: ");
        sb.append(jUsToMs);
        sb.append(" in chunk [");
        sb.append(j4);
        sb.append(", ");
        sb.append(j5);
        sb.append(StrPool.BRACKET_END);
        super(sb.toString());
        this.mediaChunk = mediaChunk;
        this.lastAcceptedSampleTimeUs = j2;
        this.rejectedSampleTimeUs = j3;
    }
}
