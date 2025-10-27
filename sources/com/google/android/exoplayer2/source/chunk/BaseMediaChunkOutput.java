package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes3.dex */
public final class BaseMediaChunkOutput implements ChunkExtractor.TrackOutputProvider {
    private static final String TAG = "BaseMediaChunkOutput";
    private final SampleQueue[] sampleQueues;
    private final int[] trackTypes;

    public BaseMediaChunkOutput(int[] iArr, SampleQueue[] sampleQueueArr) {
        this.trackTypes = iArr;
        this.sampleQueues = sampleQueueArr;
    }

    public int[] getWriteIndices() {
        int[] iArr = new int[this.sampleQueues.length];
        int i2 = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            if (i2 >= sampleQueueArr.length) {
                return iArr;
            }
            iArr[i2] = sampleQueueArr[i2].getWriteIndex();
            i2++;
        }
    }

    public void setSampleOffsetUs(long j2) {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.setSampleOffsetUs(j2);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.TrackOutputProvider
    public TrackOutput track(int i2, int i3) {
        int i4 = 0;
        while (true) {
            int[] iArr = this.trackTypes;
            if (i4 >= iArr.length) {
                StringBuilder sb = new StringBuilder(36);
                sb.append("Unmatched track of type: ");
                sb.append(i3);
                Log.e(TAG, sb.toString());
                return new DummyTrackOutput();
            }
            if (i3 == iArr[i4]) {
                return this.sampleQueues[i4];
            }
            i4++;
        }
    }
}
