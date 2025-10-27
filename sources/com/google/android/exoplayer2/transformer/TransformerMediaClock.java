package com.google.android.exoplayer2.transformer;

import android.util.SparseLongArray;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.Util;

@RequiresApi(18)
/* loaded from: classes3.dex */
final class TransformerMediaClock implements MediaClock {
    private long minTrackTimeUs;
    private final SparseLongArray trackTypeToTimeUs = new SparseLongArray();

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return PlaybackParameters.DEFAULT;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        return this.minTrackTimeUs;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
    }

    public void updateTimeForTrackType(int i2, long j2) {
        long j3 = this.trackTypeToTimeUs.get(i2, C.TIME_UNSET);
        if (j3 == C.TIME_UNSET || j2 > j3) {
            this.trackTypeToTimeUs.put(i2, j2);
            if (j3 == C.TIME_UNSET || j3 == this.minTrackTimeUs) {
                this.minTrackTimeUs = Util.minValue(this.trackTypeToTimeUs);
            }
        }
    }
}
