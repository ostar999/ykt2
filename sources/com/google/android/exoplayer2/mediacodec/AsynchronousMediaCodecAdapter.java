package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.common.base.Supplier;
import java.nio.ByteBuffer;

@RequiresApi(23)
/* loaded from: classes3.dex */
final class AsynchronousMediaCodecAdapter implements MediaCodecAdapter {
    private static final int STATE_CREATED = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_SHUT_DOWN = 2;
    private final AsynchronousMediaCodecCallback asynchronousMediaCodecCallback;
    private final AsynchronousMediaCodecBufferEnqueuer bufferEnqueuer;
    private final MediaCodec codec;
    private boolean codecReleased;
    private final boolean enableImmediateCodecStartAfterFlush;

    @Nullable
    private Surface inputSurface;
    private int state;
    private final boolean synchronizeCodecInteractionsWithQueueing;

    public static final class Factory implements MediaCodecAdapter.Factory {
        private final Supplier<HandlerThread> callbackThreadSupplier;
        private final boolean enableImmediateCodecStartAfterFlush;
        private final Supplier<HandlerThread> queueingThreadSupplier;
        private final boolean synchronizeCodecInteractionsWithQueueing;

        public Factory(final int i2, boolean z2, boolean z3) {
            this(new Supplier() { // from class: com.google.android.exoplayer2.mediacodec.b
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return AsynchronousMediaCodecAdapter.Factory.lambda$new$0(i2);
                }
            }, new Supplier() { // from class: com.google.android.exoplayer2.mediacodec.c
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return AsynchronousMediaCodecAdapter.Factory.lambda$new$1(i2);
                }
            }, z2, z3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ HandlerThread lambda$new$0(int i2) {
            return new HandlerThread(AsynchronousMediaCodecAdapter.createCallbackThreadLabel(i2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ HandlerThread lambda$new$1(int i2) {
            return new HandlerThread(AsynchronousMediaCodecAdapter.createQueueingThreadLabel(i2));
        }

        @VisibleForTesting
        public Factory(Supplier<HandlerThread> supplier, Supplier<HandlerThread> supplier2, boolean z2, boolean z3) {
            this.callbackThreadSupplier = supplier;
            this.queueingThreadSupplier = supplier2;
            this.synchronizeCodecInteractionsWithQueueing = z2;
            this.enableImmediateCodecStartAfterFlush = z3;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.Factory
        public AsynchronousMediaCodecAdapter createAdapter(MediaCodecAdapter.Configuration configuration) throws Exception {
            MediaCodec mediaCodecCreateByCodecName;
            String str = configuration.codecInfo.name;
            AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter = null;
            try {
                String strValueOf = String.valueOf(str);
                TraceUtil.beginSection(strValueOf.length() != 0 ? "createCodec:".concat(strValueOf) : new String("createCodec:"));
                mediaCodecCreateByCodecName = MediaCodec.createByCodecName(str);
                try {
                    AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter2 = new AsynchronousMediaCodecAdapter(mediaCodecCreateByCodecName, this.callbackThreadSupplier.get(), this.queueingThreadSupplier.get(), this.synchronizeCodecInteractionsWithQueueing, this.enableImmediateCodecStartAfterFlush);
                    try {
                        TraceUtil.endSection();
                        asynchronousMediaCodecAdapter2.initialize(configuration.mediaFormat, configuration.surface, configuration.crypto, configuration.flags, configuration.createInputSurface);
                        return asynchronousMediaCodecAdapter2;
                    } catch (Exception e2) {
                        e = e2;
                        asynchronousMediaCodecAdapter = asynchronousMediaCodecAdapter2;
                        if (asynchronousMediaCodecAdapter != null) {
                            asynchronousMediaCodecAdapter.release();
                        } else if (mediaCodecCreateByCodecName != null) {
                            mediaCodecCreateByCodecName.release();
                        }
                        throw e;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                mediaCodecCreateByCodecName = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String createCallbackThreadLabel(int i2) {
        return createThreadLabel(i2, "ExoPlayer:MediaCodecAsyncAdapter:");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String createQueueingThreadLabel(int i2) {
        return createThreadLabel(i2, "ExoPlayer:MediaCodecQueueingThread:");
    }

    private static String createThreadLabel(int i2, String str) {
        StringBuilder sb = new StringBuilder(str);
        if (i2 == 1) {
            sb.append("Audio");
        } else if (i2 == 2) {
            sb.append("Video");
        } else {
            sb.append("Unknown(");
            sb.append(i2);
            sb.append(")");
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize(@Nullable MediaFormat mediaFormat, @Nullable Surface surface, @Nullable MediaCrypto mediaCrypto, int i2, boolean z2) {
        this.asynchronousMediaCodecCallback.initialize(this.codec);
        TraceUtil.beginSection("configureCodec");
        this.codec.configure(mediaFormat, surface, mediaCrypto, i2);
        TraceUtil.endSection();
        if (z2) {
            this.inputSurface = this.codec.createInputSurface();
        }
        this.bufferEnqueuer.start();
        TraceUtil.beginSection("startCodec");
        this.codec.start();
        TraceUtil.endSection();
        this.state = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnFrameRenderedListener$0(MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, MediaCodec mediaCodec, long j2, long j3) {
        onFrameRenderedListener.onFrameRendered(this, j2, j3);
    }

    private void maybeBlockOnQueueing() {
        if (this.synchronizeCodecInteractionsWithQueueing) {
            try {
                this.bufferEnqueuer.waitUntilQueueingComplete();
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e2);
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueInputBufferIndex() {
        return this.asynchronousMediaCodecCallback.dequeueInputBufferIndex();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        return this.asynchronousMediaCodecCallback.dequeueOutputBufferIndex(bufferInfo);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void flush() {
        this.bufferEnqueuer.flush();
        this.codec.flush();
        if (!this.enableImmediateCodecStartAfterFlush) {
            this.asynchronousMediaCodecCallback.flush(this.codec);
        } else {
            this.asynchronousMediaCodecCallback.flush(null);
            this.codec.start();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getInputBuffer(int i2) {
        return this.codec.getInputBuffer(i2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public Surface getInputSurface() {
        return this.inputSurface;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    @Nullable
    public ByteBuffer getOutputBuffer(int i2) {
        return this.codec.getOutputBuffer(i2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public MediaFormat getOutputFormat() {
        return this.asynchronousMediaCodecCallback.getOutputFormat();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public boolean needsReconfiguration() {
        return false;
    }

    @VisibleForTesting
    public void onError(MediaCodec.CodecException codecException) {
        this.asynchronousMediaCodecCallback.onError(this.codec, codecException);
    }

    @VisibleForTesting
    public void onOutputFormatChanged(MediaFormat mediaFormat) {
        this.asynchronousMediaCodecCallback.onOutputFormatChanged(this.codec, mediaFormat);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueInputBuffer(int i2, int i3, int i4, long j2, int i5) {
        this.bufferEnqueuer.queueInputBuffer(i2, i3, i4, j2, i5);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void queueSecureInputBuffer(int i2, int i3, CryptoInfo cryptoInfo, long j2, int i4) {
        this.bufferEnqueuer.queueSecureInputBuffer(i2, i3, cryptoInfo, j2, i4);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void release() {
        try {
            if (this.state == 1) {
                this.bufferEnqueuer.shutdown();
                this.asynchronousMediaCodecCallback.shutdown();
            }
            this.state = 2;
        } finally {
            Surface surface = this.inputSurface;
            if (surface != null) {
                surface.release();
            }
            if (!this.codecReleased) {
                this.codec.release();
                this.codecReleased = true;
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int i2, boolean z2) {
        this.codec.releaseOutputBuffer(i2, z2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOnFrameRenderedListener(final MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, Handler handler) {
        maybeBlockOnQueueing();
        this.codec.setOnFrameRenderedListener(new MediaCodec.OnFrameRenderedListener() { // from class: com.google.android.exoplayer2.mediacodec.a
            @Override // android.media.MediaCodec.OnFrameRenderedListener
            public final void onFrameRendered(MediaCodec mediaCodec, long j2, long j3) {
                this.f6804a.lambda$setOnFrameRenderedListener$0(onFrameRenderedListener, mediaCodec, j2, j3);
            }
        }, handler);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setOutputSurface(Surface surface) {
        maybeBlockOnQueueing();
        this.codec.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setParameters(Bundle bundle) {
        maybeBlockOnQueueing();
        this.codec.setParameters(bundle);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void setVideoScalingMode(int i2) {
        maybeBlockOnQueueing();
        this.codec.setVideoScalingMode(i2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void signalEndOfInputStream() {
        maybeBlockOnQueueing();
        this.codec.signalEndOfInputStream();
    }

    private AsynchronousMediaCodecAdapter(MediaCodec mediaCodec, HandlerThread handlerThread, HandlerThread handlerThread2, boolean z2, boolean z3) {
        this.codec = mediaCodec;
        this.asynchronousMediaCodecCallback = new AsynchronousMediaCodecCallback(handlerThread);
        this.bufferEnqueuer = new AsynchronousMediaCodecBufferEnqueuer(mediaCodec, handlerThread2);
        this.synchronizeCodecInteractionsWithQueueing = z2;
        this.enableImmediateCodecStartAfterFlush = z3;
        this.state = 0;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter
    public void releaseOutputBuffer(int i2, long j2) {
        this.codec.releaseOutputBuffer(i2, j2);
    }
}
