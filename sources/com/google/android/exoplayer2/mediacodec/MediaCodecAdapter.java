package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public interface MediaCodecAdapter {

    public static final class Configuration {
        public final MediaCodecInfo codecInfo;
        public final boolean createInputSurface;

        @Nullable
        public final MediaCrypto crypto;
        public final int flags;
        public final Format format;
        public final MediaFormat mediaFormat;

        @Nullable
        public final Surface surface;

        private Configuration(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat, Format format, @Nullable Surface surface, @Nullable MediaCrypto mediaCrypto, int i2, boolean z2) {
            this.codecInfo = mediaCodecInfo;
            this.mediaFormat = mediaFormat;
            this.format = format;
            this.surface = surface;
            this.crypto = mediaCrypto;
            this.flags = i2;
            this.createInputSurface = z2;
        }

        public static Configuration createForAudioDecoding(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat, Format format, @Nullable MediaCrypto mediaCrypto) {
            return new Configuration(mediaCodecInfo, mediaFormat, format, null, mediaCrypto, 0, false);
        }

        public static Configuration createForAudioEncoding(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat, Format format) {
            return new Configuration(mediaCodecInfo, mediaFormat, format, null, null, 1, false);
        }

        public static Configuration createForVideoDecoding(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat, Format format, @Nullable Surface surface, @Nullable MediaCrypto mediaCrypto) {
            return new Configuration(mediaCodecInfo, mediaFormat, format, surface, mediaCrypto, 0, false);
        }

        @RequiresApi(18)
        public static Configuration createForVideoEncoding(MediaCodecInfo mediaCodecInfo, MediaFormat mediaFormat, Format format) {
            return new Configuration(mediaCodecInfo, mediaFormat, format, null, null, 1, true);
        }
    }

    public interface Factory {
        public static final Factory DEFAULT = new DefaultMediaCodecAdapterFactory();

        MediaCodecAdapter createAdapter(Configuration configuration) throws IOException;
    }

    public interface OnFrameRenderedListener {
        void onFrameRendered(MediaCodecAdapter mediaCodecAdapter, long j2, long j3);
    }

    int dequeueInputBufferIndex();

    int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo);

    void flush();

    @Nullable
    ByteBuffer getInputBuffer(int i2);

    @Nullable
    Surface getInputSurface();

    @Nullable
    ByteBuffer getOutputBuffer(int i2);

    MediaFormat getOutputFormat();

    boolean needsReconfiguration();

    void queueInputBuffer(int i2, int i3, int i4, long j2, int i5);

    void queueSecureInputBuffer(int i2, int i3, CryptoInfo cryptoInfo, long j2, int i4);

    void release();

    @RequiresApi(21)
    void releaseOutputBuffer(int i2, long j2);

    void releaseOutputBuffer(int i2, boolean z2);

    @RequiresApi(23)
    void setOnFrameRenderedListener(OnFrameRenderedListener onFrameRenderedListener, Handler handler);

    @RequiresApi(23)
    void setOutputSurface(Surface surface);

    @RequiresApi(19)
    void setParameters(Bundle bundle);

    void setVideoScalingMode(int i2);

    @RequiresApi(18)
    void signalEndOfInputStream();
}
