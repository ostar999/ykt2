package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class SynchronousMediaCodecAdapter implements MediaCodecAdapter {
    private final MediaCodec codec;

    @Nullable
    private ByteBuffer[] inputByteBuffers;

    @Nullable
    private final Surface inputSurface;

    @Nullable
    private ByteBuffer[] outputByteBuffers;

    @RequiresApi(18)
    public static final class Api18 {
        private Api18() {
        }

        @DoNotInline
        public static Surface createCodecInputSurface(MediaCodec mediaCodec) {
            return mediaCodec.createInputSurface();
        }

        @DoNotInline
        public static void signalEndOfInputStream(MediaCodec mediaCodec) {
            mediaCodec.signalEndOfInputStream();
        }
    }

    public static class Factory implements MediaCodecAdapter.Factory {
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter$1] */
        /* JADX WARN: Type inference failed for: r0v1, types: [android.view.Surface] */
        /* JADX WARN: Type inference failed for: r0v3 */
        /* JADX WARN: Type inference failed for: r0v4 */
        /* JADX WARN: Type inference failed for: r0v5 */
        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
        public MediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws Throwable {
            MediaCodec mediaCodecCreateCodec;
            Surface surfaceCreateCodecInputSurface;
            ?? r02 = 0;
            r02 = 0;
            r02 = 0;
            try {
                mediaCodecCreateCodec = createCodec(configuration);
                try {
                    TraceUtil.beginSection("configureCodec");
                    mediaCodecCreateCodec.configure(configuration.mediaFormat, configuration.surface, configuration.crypto, configuration.flags);
                    TraceUtil.endSection();
                    if (!configuration.createInputSurface) {
                        surfaceCreateCodecInputSurface = null;
                    } else {
                        if (Util.SDK_INT < 18) {
                            throw new IllegalStateException("Encoding from a surface is only supported on API 18 and up.");
                        }
                        surfaceCreateCodecInputSurface = Api18.createCodecInputSurface(mediaCodecCreateCodec);
                    }
                    try {
                        TraceUtil.beginSection("startCodec");
                        mediaCodecCreateCodec.start();
                        TraceUtil.endSection();
                        return new SynchronousMediaCodecAdapter(mediaCodecCreateCodec, surfaceCreateCodecInputSurface);
                    } catch (IOException | RuntimeException e2) {
                        r02 = surfaceCreateCodecInputSurface;
                        e = e2;
                        if (r02 != 0) {
                            r02.release();
                        }
                        if (mediaCodecCreateCodec != null) {
                            mediaCodecCreateCodec.release();
                        }
                        throw e;
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (RuntimeException e4) {
                    e = e4;
                }
            } catch (IOException | RuntimeException e5) {
                e = e5;
                mediaCodecCreateCodec = null;
            }
        }

        public MediaCodec createCodec(MediaCodecAdapter.Configuration configuration) throws IOException {
            Assertions.checkNotNull(configuration.codecInfo);
            String str = configuration.codecInfo.name;
            String strValueOf = String.valueOf(str);
            TraceUtil.beginSection(strValueOf.length() != 0 ? "createCodec:".concat(strValueOf) : new String("createCodec:"));
            MediaCodec mediaCodecCreateByCodecName = MediaCodec.createByCodecName(str);
            TraceUtil.endSection();
            return mediaCodecCreateByCodecName;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnFrameRenderedListener$0(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, MediaCodec mediaCodec, long j2, long j3) {
        onFrameRenderedListener.onFrameRendered(this, j2, j3);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueInputBufferIndex() {
        return this.codec.dequeueInputBuffer(0L);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        int iDequeueOutputBuffer;
        do {
            iDequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, 0L);
            if (iDequeueOutputBuffer == -3 && Util.SDK_INT < 21) {
                this.outputByteBuffers = this.codec.getOutputBuffers();
            }
        } while (iDequeueOutputBuffer == -3);
        return iDequeueOutputBuffer;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void flush() {
        this.codec.flush();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getInputBuffer(int i2) {
        return Util.SDK_INT >= 21 ? this.codec.getInputBuffer(i2) : ((ByteBuffer[]) Util.castNonNull(this.inputByteBuffers))[i2];
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public Surface getInputSurface() {
        return this.inputSurface;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getOutputBuffer(int i2) {
        return Util.SDK_INT >= 21 ? this.codec.getOutputBuffer(i2) : ((ByteBuffer[]) Util.castNonNull(this.outputByteBuffers))[i2];
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public MediaFormat getOutputFormat() {
        return this.codec.getOutputFormat();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public boolean needsReconfiguration() {
        return false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueInputBuffer(int i2, int i3, int i4, long j2, int i5) throws MediaCodec.CryptoException {
        this.codec.queueInputBuffer(i2, i3, i4, j2, i5);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueSecureInputBuffer(int i2, int i3, CryptoInfo cryptoInfo, long j2, int i4) throws MediaCodec.CryptoException {
        this.codec.queueSecureInputBuffer(i2, i3, cryptoInfo.getFrameworkCryptoInfo(), j2, i4);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void release() {
        this.inputByteBuffers = null;
        this.outputByteBuffers = null;
        Surface surface = this.inputSurface;
        if (surface != null) {
            surface.release();
        }
        this.codec.release();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int i2, boolean z2) {
        this.codec.releaseOutputBuffer(i2, z2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(23)
    public void setOnFrameRenderedListener(final MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        this.codec.setOnFrameRenderedListener(new MediaCodec.OnFrameRenderedListener() { // from class: com.google.android.exoplayer2.mediacodec.n
            @Override // android.media.MediaCodec.OnFrameRenderedListener
            public final void onFrameRendered(MediaCodec mediaCodec, long j2, long j3) {
                this.f6812a.lambda$setOnFrameRenderedListener$0(onFrameRenderedListener, mediaCodec, j2, j3);
            }
        }, handler);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(23)
    public void setOutputSurface(Surface surface) {
        this.codec.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(19)
    public void setParameters(Bundle bundle) {
        this.codec.setParameters(bundle);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setVideoScalingMode(int i2) {
        this.codec.setVideoScalingMode(i2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(18)
    public void signalEndOfInputStream() {
        Api18.signalEndOfInputStream(this.codec);
    }

    private SynchronousMediaCodecAdapter(MediaCodec mediaCodec, @Nullable Surface surface) {
        this.codec = mediaCodec;
        this.inputSurface = surface;
        if (Util.SDK_INT < 21) {
            this.inputByteBuffers = mediaCodec.getInputBuffers();
            this.outputByteBuffers = mediaCodec.getOutputBuffers();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @RequiresApi(21)
    public void releaseOutputBuffer(int i2, long j2) {
        this.codec.releaseOutputBuffer(i2, j2);
    }
}
