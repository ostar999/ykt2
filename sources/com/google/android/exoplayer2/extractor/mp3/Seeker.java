package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;

/* loaded from: classes3.dex */
interface Seeker extends SeekMap {

    public static class UnseekableSeeker extends SeekMap.Unseekable implements Seeker {
        public UnseekableSeeker() {
            super(C.TIME_UNSET);
        }

        @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
        public long getDataEndPosition() {
            return -1L;
        }

        @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
        public long getTimeUs(long j2) {
            return 0L;
        }
    }

    long getDataEndPosition();

    long getTimeUs(long j2);
}
