package okio;

import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.tencent.open.SocialConstants;
import io.socket.engineio.client.Socket;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u0018H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lokio/CipherSink;", "Lokio/Sink;", "sink", "Lokio/BufferedSink;", "cipher", "Ljavax/crypto/Cipher;", "(Lokio/BufferedSink;Ljavax/crypto/Cipher;)V", "blockSize", "", "getCipher", "()Ljavax/crypto/Cipher;", "closed", "", "close", "", "doFinal", "", Socket.EVENT_FLUSH, "timeout", "Lokio/Timeout;", PLVUpdateMicSiteEvent.EVENT_NAME, SocialConstants.PARAM_SOURCE, "Lokio/Buffer;", "remaining", "", "write", "byteCount", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class CipherSink implements Sink {
    private final int blockSize;

    @NotNull
    private final Cipher cipher;
    private boolean closed;

    @NotNull
    private final BufferedSink sink;

    public CipherSink(@NotNull BufferedSink sink, @NotNull Cipher cipher) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(cipher, "cipher");
        this.sink = sink;
        this.cipher = cipher;
        int blockSize = cipher.getBlockSize();
        this.blockSize = blockSize;
        if (blockSize > 0) {
            return;
        }
        throw new IllegalArgumentException(("Block cipher required " + cipher).toString());
    }

    private final Throwable doFinal() {
        int outputSize = this.cipher.getOutputSize(0);
        Throwable th = null;
        if (outputSize == 0) {
            return null;
        }
        if (outputSize > 8192) {
            try {
                BufferedSink bufferedSink = this.sink;
                byte[] bArrDoFinal = this.cipher.doFinal();
                Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "cipher.doFinal()");
                bufferedSink.write(bArrDoFinal);
                return null;
            } catch (Throwable th2) {
                return th2;
            }
        }
        Buffer buffer = this.sink.getBuffer();
        Segment segmentWritableSegment$okio = buffer.writableSegment$okio(outputSize);
        try {
            int iDoFinal = this.cipher.doFinal(segmentWritableSegment$okio.data, segmentWritableSegment$okio.limit);
            segmentWritableSegment$okio.limit += iDoFinal;
            buffer.setSize$okio(buffer.size() + iDoFinal);
        } catch (Throwable th3) {
            th = th3;
        }
        if (segmentWritableSegment$okio.pos == segmentWritableSegment$okio.limit) {
            buffer.head = segmentWritableSegment$okio.pop();
            SegmentPool.recycle(segmentWritableSegment$okio);
        }
        return th;
    }

    private final int update(Buffer source, long remaining) throws IOException, ShortBufferException {
        Segment segment = source.head;
        Intrinsics.checkNotNull(segment);
        int iMin = (int) Math.min(remaining, segment.limit - segment.pos);
        Buffer buffer = this.sink.getBuffer();
        int outputSize = this.cipher.getOutputSize(iMin);
        while (outputSize > 8192) {
            int i2 = this.blockSize;
            if (iMin <= i2) {
                BufferedSink bufferedSink = this.sink;
                byte[] bArrUpdate = this.cipher.update(source.readByteArray(remaining));
                Intrinsics.checkNotNullExpressionValue(bArrUpdate, "cipher.update(source.readByteArray(remaining))");
                bufferedSink.write(bArrUpdate);
                return (int) remaining;
            }
            iMin -= i2;
            outputSize = this.cipher.getOutputSize(iMin);
        }
        Segment segmentWritableSegment$okio = buffer.writableSegment$okio(outputSize);
        int iUpdate = this.cipher.update(segment.data, segment.pos, iMin, segmentWritableSegment$okio.data, segmentWritableSegment$okio.limit);
        segmentWritableSegment$okio.limit += iUpdate;
        buffer.setSize$okio(buffer.size() + iUpdate);
        if (segmentWritableSegment$okio.pos == segmentWritableSegment$okio.limit) {
            buffer.head = segmentWritableSegment$okio.pop();
            SegmentPool.recycle(segmentWritableSegment$okio);
        }
        this.sink.emitCompleteSegments();
        source.setSize$okio(source.size() - iMin);
        int i3 = segment.pos + iMin;
        segment.pos = i3;
        if (i3 == segment.limit) {
            source.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return iMin;
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        this.closed = true;
        Throwable thDoFinal = doFinal();
        try {
            this.sink.close();
        } catch (Throwable th) {
            if (thDoFinal == null) {
                thDoFinal = th;
            }
        }
        if (thDoFinal != null) {
            throw thDoFinal;
        }
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        this.sink.flush();
    }

    @NotNull
    public final Cipher getCipher() {
        return this.cipher;
    }

    @Override // okio.Sink
    @NotNull
    /* renamed from: timeout */
    public Timeout getTimeout() {
        return this.sink.getTimeout();
    }

    @Override // okio.Sink
    public void write(@NotNull Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        _UtilKt.checkOffsetAndCount(source.size(), 0L, byteCount);
        if (!(!this.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        while (byteCount > 0) {
            byteCount -= update(source, byteCount);
        }
    }
}
