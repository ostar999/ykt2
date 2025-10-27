package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;

/* loaded from: classes3.dex */
final class ConstantBitrateSeeker extends ConstantBitrateSeekMap implements Seeker {
    public ConstantBitrateSeeker(long j2, long j3, MpegAudioUtil.Header header, boolean z2) {
        super(j2, j3, header.bitrate, header.frameSize, z2);
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return getTimeUsAtPosition(j2);
    }
}
