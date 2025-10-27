package com.google.android.exoplayer2.mediacodec;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
final class C2Mp3TimestampTracker {
    private static final long DECODER_DELAY_FRAMES = 529;
    private static final String TAG = "C2Mp3TimestampTracker";
    private long anchorTimestampUs;
    private long processedFrames;
    private boolean seenInvalidMpegAudioHeader;

    private long getBufferTimestampUs(long j2) {
        return this.anchorTimestampUs + Math.max(0L, ((this.processedFrames - DECODER_DELAY_FRAMES) * 1000000) / j2);
    }

    public long getLastOutputBufferPresentationTimeUs(Format format) {
        return getBufferTimestampUs(format.sampleRate);
    }

    public void reset() {
        this.anchorTimestampUs = 0L;
        this.processedFrames = 0L;
        this.seenInvalidMpegAudioHeader = false;
    }

    public long updateAndGetPresentationTimeUs(Format format, DecoderInputBuffer decoderInputBuffer) {
        if (this.processedFrames == 0) {
            this.anchorTimestampUs = decoderInputBuffer.timeUs;
        }
        if (this.seenInvalidMpegAudioHeader) {
            return decoderInputBuffer.timeUs;
        }
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.data);
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            i2 = (i2 << 8) | (byteBuffer.get(i3) & 255);
        }
        int mpegAudioFrameSampleCount = MpegAudioUtil.parseMpegAudioFrameSampleCount(i2);
        if (mpegAudioFrameSampleCount != -1) {
            long bufferTimestampUs = getBufferTimestampUs(format.sampleRate);
            this.processedFrames += mpegAudioFrameSampleCount;
            return bufferTimestampUs;
        }
        this.seenInvalidMpegAudioHeader = true;
        this.processedFrames = 0L;
        this.anchorTimestampUs = decoderInputBuffer.timeUs;
        Log.w(TAG, "MPEG audio header is invalid.");
        return decoderInputBuffer.timeUs;
    }
}
