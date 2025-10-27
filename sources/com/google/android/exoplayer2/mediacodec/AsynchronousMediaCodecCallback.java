package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayDeque;

@RequiresApi(23)
/* loaded from: classes3.dex */
final class AsynchronousMediaCodecCallback extends MediaCodec.Callback {
    private final HandlerThread callbackThread;

    @Nullable
    @GuardedBy("lock")
    private MediaFormat currentFormat;
    private Handler handler;

    @Nullable
    @GuardedBy("lock")
    private IllegalStateException internalException;

    @Nullable
    @GuardedBy("lock")
    private MediaCodec.CodecException mediaCodecException;

    @GuardedBy("lock")
    private long pendingFlushCount;

    @Nullable
    @GuardedBy("lock")
    private MediaFormat pendingOutputFormat;

    @GuardedBy("lock")
    private boolean shutDown;
    private final Object lock = new Object();

    @GuardedBy("lock")
    private final IntArrayQueue availableInputBuffers = new IntArrayQueue();

    @GuardedBy("lock")
    private final IntArrayQueue availableOutputBuffers = new IntArrayQueue();

    @GuardedBy("lock")
    private final ArrayDeque<MediaCodec.BufferInfo> bufferInfos = new ArrayDeque<>();

    @GuardedBy("lock")
    private final ArrayDeque<MediaFormat> formats = new ArrayDeque<>();

    public AsynchronousMediaCodecCallback(HandlerThread handlerThread) {
        this.callbackThread = handlerThread;
    }

    @GuardedBy("lock")
    private void addOutputFormat(MediaFormat mediaFormat) {
        this.availableOutputBuffers.add(-2);
        this.formats.add(mediaFormat);
    }

    @GuardedBy("lock")
    private void flushInternal() {
        if (!this.formats.isEmpty()) {
            this.pendingOutputFormat = this.formats.getLast();
        }
        this.availableInputBuffers.clear();
        this.availableOutputBuffers.clear();
        this.bufferInfos.clear();
        this.formats.clear();
        this.mediaCodecException = null;
    }

    @GuardedBy("lock")
    private boolean isFlushingOrShutdown() {
        return this.pendingFlushCount > 0 || this.shutDown;
    }

    @GuardedBy("lock")
    private void maybeThrowException() {
        maybeThrowInternalException();
        maybeThrowMediaCodecException();
    }

    @GuardedBy("lock")
    private void maybeThrowInternalException() {
        IllegalStateException illegalStateException = this.internalException;
        if (illegalStateException == null) {
            return;
        }
        this.internalException = null;
        throw illegalStateException;
    }

    @GuardedBy("lock")
    private void maybeThrowMediaCodecException() {
        MediaCodec.CodecException codecException = this.mediaCodecException;
        if (codecException == null) {
            return;
        }
        this.mediaCodecException = null;
        throw codecException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onFlushCompleted, reason: merged with bridge method [inline-methods] */
    public void lambda$flush$0(@Nullable MediaCodec mediaCodec) {
        synchronized (this.lock) {
            if (this.shutDown) {
                return;
            }
            long j2 = this.pendingFlushCount - 1;
            this.pendingFlushCount = j2;
            if (j2 > 0) {
                return;
            }
            if (j2 < 0) {
                setInternalException(new IllegalStateException());
                return;
            }
            flushInternal();
            if (mediaCodec != null) {
                try {
                    mediaCodec.start();
                } catch (IllegalStateException e2) {
                    setInternalException(e2);
                } catch (Exception e3) {
                    setInternalException(new IllegalStateException(e3));
                }
            }
        }
    }

    private void setInternalException(IllegalStateException illegalStateException) {
        synchronized (this.lock) {
            this.internalException = illegalStateException;
        }
    }

    public int dequeueInputBufferIndex() {
        synchronized (this.lock) {
            int iRemove = -1;
            if (isFlushingOrShutdown()) {
                return -1;
            }
            maybeThrowException();
            if (!this.availableInputBuffers.isEmpty()) {
                iRemove = this.availableInputBuffers.remove();
            }
            return iRemove;
        }
    }

    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.lock) {
            if (isFlushingOrShutdown()) {
                return -1;
            }
            maybeThrowException();
            if (this.availableOutputBuffers.isEmpty()) {
                return -1;
            }
            int iRemove = this.availableOutputBuffers.remove();
            if (iRemove >= 0) {
                Assertions.checkStateNotNull(this.currentFormat);
                MediaCodec.BufferInfo bufferInfoRemove = this.bufferInfos.remove();
                bufferInfo.set(bufferInfoRemove.offset, bufferInfoRemove.size, bufferInfoRemove.presentationTimeUs, bufferInfoRemove.flags);
            } else if (iRemove == -2) {
                this.currentFormat = this.formats.remove();
            }
            return iRemove;
        }
    }

    public void flush(@Nullable final MediaCodec mediaCodec) {
        synchronized (this.lock) {
            this.pendingFlushCount++;
            ((Handler) Util.castNonNull(this.handler)).post(new Runnable() { // from class: com.google.android.exoplayer2.mediacodec.d
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6808c.lambda$flush$0(mediaCodec);
                }
            });
        }
    }

    public MediaFormat getOutputFormat() {
        MediaFormat mediaFormat;
        synchronized (this.lock) {
            mediaFormat = this.currentFormat;
            if (mediaFormat == null) {
                throw new IllegalStateException();
            }
        }
        return mediaFormat;
    }

    public void initialize(MediaCodec mediaCodec) {
        Assertions.checkState(this.handler == null);
        this.callbackThread.start();
        Handler handler = new Handler(this.callbackThread.getLooper());
        mediaCodec.setCallback(this, handler);
        this.handler = handler;
    }

    @Override // android.media.MediaCodec.Callback
    public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
        synchronized (this.lock) {
            this.mediaCodecException = codecException;
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onInputBufferAvailable(MediaCodec mediaCodec, int i2) {
        synchronized (this.lock) {
            this.availableInputBuffers.add(i2);
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputBufferAvailable(MediaCodec mediaCodec, int i2, MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.lock) {
            MediaFormat mediaFormat = this.pendingOutputFormat;
            if (mediaFormat != null) {
                addOutputFormat(mediaFormat);
                this.pendingOutputFormat = null;
            }
            this.availableOutputBuffers.add(i2);
            this.bufferInfos.add(bufferInfo);
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        synchronized (this.lock) {
            addOutputFormat(mediaFormat);
            this.pendingOutputFormat = null;
        }
    }

    public void shutdown() {
        synchronized (this.lock) {
            this.shutDown = true;
            this.callbackThread.quit();
            flushInternal();
        }
    }
}
