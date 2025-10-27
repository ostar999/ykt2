package com.google.android.exoplayer2.decoder;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayDeque;

/* loaded from: classes3.dex */
public abstract class SimpleDecoder<I extends DecoderInputBuffer, O extends DecoderOutputBuffer, E extends DecoderException> implements Decoder<I, O, E> {
    private int availableInputBufferCount;
    private final I[] availableInputBuffers;
    private int availableOutputBufferCount;
    private final O[] availableOutputBuffers;
    private final Thread decodeThread;

    @Nullable
    private I dequeuedInputBuffer;

    @Nullable
    private E exception;
    private boolean flushed;
    private final Object lock = new Object();
    private final ArrayDeque<I> queuedInputBuffers = new ArrayDeque<>();
    private final ArrayDeque<O> queuedOutputBuffers = new ArrayDeque<>();
    private boolean released;
    private int skippedOutputBufferCount;

    public SimpleDecoder(I[] iArr, O[] oArr) {
        this.availableInputBuffers = iArr;
        this.availableInputBufferCount = iArr.length;
        for (int i2 = 0; i2 < this.availableInputBufferCount; i2++) {
            ((I[]) this.availableInputBuffers)[i2] = createInputBuffer();
        }
        this.availableOutputBuffers = oArr;
        this.availableOutputBufferCount = oArr.length;
        for (int i3 = 0; i3 < this.availableOutputBufferCount; i3++) {
            ((O[]) this.availableOutputBuffers)[i3] = createOutputBuffer();
        }
        Thread thread = new Thread("ExoPlayer:SimpleDecoder") { // from class: com.google.android.exoplayer2.decoder.SimpleDecoder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SimpleDecoder.this.run();
            }
        };
        this.decodeThread = thread;
        thread.start();
    }

    private boolean canDecodeBuffer() {
        return !this.queuedInputBuffers.isEmpty() && this.availableOutputBufferCount > 0;
    }

    private boolean decode() throws InterruptedException {
        E e2;
        synchronized (this.lock) {
            while (!this.released && !canDecodeBuffer()) {
                this.lock.wait();
            }
            if (this.released) {
                return false;
            }
            I iRemoveFirst = this.queuedInputBuffers.removeFirst();
            O[] oArr = this.availableOutputBuffers;
            int i2 = this.availableOutputBufferCount - 1;
            this.availableOutputBufferCount = i2;
            O o2 = oArr[i2];
            boolean z2 = this.flushed;
            this.flushed = false;
            if (iRemoveFirst.isEndOfStream()) {
                o2.addFlag(4);
            } else {
                if (iRemoveFirst.isDecodeOnly()) {
                    o2.addFlag(Integer.MIN_VALUE);
                }
                try {
                    e2 = (E) decode(iRemoveFirst, o2, z2);
                } catch (OutOfMemoryError e3) {
                    e2 = (E) createUnexpectedDecodeException(e3);
                } catch (RuntimeException e4) {
                    e2 = (E) createUnexpectedDecodeException(e4);
                }
                if (e2 != null) {
                    synchronized (this.lock) {
                        this.exception = e2;
                    }
                    return false;
                }
            }
            synchronized (this.lock) {
                if (this.flushed) {
                    o2.release();
                } else if (o2.isDecodeOnly()) {
                    this.skippedOutputBufferCount++;
                    o2.release();
                } else {
                    o2.skippedOutputBufferCount = this.skippedOutputBufferCount;
                    this.skippedOutputBufferCount = 0;
                    this.queuedOutputBuffers.addLast(o2);
                }
                releaseInputBufferInternal(iRemoveFirst);
            }
            return true;
        }
    }

    private void maybeNotifyDecodeLoop() {
        if (canDecodeBuffer()) {
            this.lock.notify();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: E extends com.google.android.exoplayer2.decoder.DecoderException */
    private void maybeThrowException() throws E, DecoderException {
        E e2 = this.exception;
        if (e2 != null) {
            throw e2;
        }
    }

    private void releaseInputBufferInternal(I i2) {
        i2.clear();
        I[] iArr = this.availableInputBuffers;
        int i3 = this.availableInputBufferCount;
        this.availableInputBufferCount = i3 + 1;
        iArr[i3] = i2;
    }

    private void releaseOutputBufferInternal(O o2) {
        o2.clear();
        O[] oArr = this.availableOutputBuffers;
        int i2 = this.availableOutputBufferCount;
        this.availableOutputBufferCount = i2 + 1;
        oArr[i2] = o2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void run() {
        do {
            try {
            } catch (InterruptedException e2) {
                throw new IllegalStateException(e2);
            }
        } while (decode());
    }

    public abstract I createInputBuffer();

    public abstract O createOutputBuffer();

    public abstract E createUnexpectedDecodeException(Throwable th);

    @Nullable
    public abstract E decode(I i2, O o2, boolean z2);

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public final void flush() {
        synchronized (this.lock) {
            this.flushed = true;
            this.skippedOutputBufferCount = 0;
            I i2 = this.dequeuedInputBuffer;
            if (i2 != null) {
                releaseInputBufferInternal(i2);
                this.dequeuedInputBuffer = null;
            }
            while (!this.queuedInputBuffers.isEmpty()) {
                releaseInputBufferInternal(this.queuedInputBuffers.removeFirst());
            }
            while (!this.queuedOutputBuffers.isEmpty()) {
                this.queuedOutputBuffers.removeFirst().release();
            }
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    @CallSuper
    public void release() throws InterruptedException {
        synchronized (this.lock) {
            this.released = true;
            this.lock.notify();
        }
        try {
            this.decodeThread.join();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    @CallSuper
    public void releaseOutputBuffer(O o2) {
        synchronized (this.lock) {
            releaseOutputBufferInternal(o2);
            maybeNotifyDecodeLoop();
        }
    }

    public final void setInitialInputBufferSize(int i2) {
        Assertions.checkState(this.availableInputBufferCount == this.availableInputBuffers.length);
        for (I i3 : this.availableInputBuffers) {
            i3.ensureSpaceForWrite(i2);
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public final I dequeueInputBuffer() throws DecoderException {
        I i2;
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkState(this.dequeuedInputBuffer == null);
            int i3 = this.availableInputBufferCount;
            if (i3 == 0) {
                i2 = null;
            } else {
                I[] iArr = this.availableInputBuffers;
                int i4 = i3 - 1;
                this.availableInputBufferCount = i4;
                i2 = iArr[i4];
            }
            this.dequeuedInputBuffer = i2;
        }
        return i2;
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public final O dequeueOutputBuffer() throws DecoderException {
        synchronized (this.lock) {
            maybeThrowException();
            if (this.queuedOutputBuffers.isEmpty()) {
                return null;
            }
            return this.queuedOutputBuffers.removeFirst();
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public final void queueInputBuffer(I i2) throws DecoderException {
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkArgument(i2 == this.dequeuedInputBuffer);
            this.queuedInputBuffers.addLast(i2);
            maybeNotifyDecodeLoop();
            this.dequeuedInputBuffer = null;
        }
    }
}
