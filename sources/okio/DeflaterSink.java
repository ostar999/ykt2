package okio;

import com.tencent.open.SocialConstants;
import io.socket.engineio.client.Socket;
import java.io.IOException;
import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0006\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\tH\u0003J\r\u0010\u000e\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u000fJ\b\u0010\u0010\u001a\u00020\u000bH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lokio/DeflaterSink;", "Lokio/Sink;", "sink", "deflater", "Ljava/util/zip/Deflater;", "(Lokio/Sink;Ljava/util/zip/Deflater;)V", "Lokio/BufferedSink;", "(Lokio/BufferedSink;Ljava/util/zip/Deflater;)V", "closed", "", "close", "", CompressorStreamFactory.DEFLATE, "syncFlush", "finishDeflate", "finishDeflate$okio", Socket.EVENT_FLUSH, "timeout", "Lokio/Timeout;", "toString", "", "write", SocialConstants.PARAM_SOURCE, "Lokio/Buffer;", "byteCount", "", "okio"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class DeflaterSink implements Sink {
    private boolean closed;

    @NotNull
    private final Deflater deflater;

    @NotNull
    private final BufferedSink sink;

    public DeflaterSink(@NotNull BufferedSink sink, @NotNull Deflater deflater) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(deflater, "deflater");
        this.sink = sink;
        this.deflater = deflater;
    }

    @IgnoreJRERequirement
    private final void deflate(boolean syncFlush) throws IOException {
        Segment segmentWritableSegment$okio;
        int iDeflate;
        Buffer buffer = this.sink.getBuffer();
        while (true) {
            segmentWritableSegment$okio = buffer.writableSegment$okio(1);
            if (syncFlush) {
                Deflater deflater = this.deflater;
                byte[] bArr = segmentWritableSegment$okio.data;
                int i2 = segmentWritableSegment$okio.limit;
                iDeflate = deflater.deflate(bArr, i2, 8192 - i2, 2);
            } else {
                Deflater deflater2 = this.deflater;
                byte[] bArr2 = segmentWritableSegment$okio.data;
                int i3 = segmentWritableSegment$okio.limit;
                iDeflate = deflater2.deflate(bArr2, i3, 8192 - i3);
            }
            if (iDeflate > 0) {
                segmentWritableSegment$okio.limit += iDeflate;
                buffer.setSize$okio(buffer.size() + iDeflate);
                this.sink.emitCompleteSegments();
            } else if (this.deflater.needsInput()) {
                break;
            }
        }
        if (segmentWritableSegment$okio.pos == segmentWritableSegment$okio.limit) {
            buffer.head = segmentWritableSegment$okio.pop();
            SegmentPool.recycle(segmentWritableSegment$okio);
        }
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.closed) {
            return;
        }
        try {
            finishDeflate$okio();
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.deflater.end();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        try {
            this.sink.close();
        } catch (Throwable th3) {
            if (th == null) {
                th = th3;
            }
        }
        this.closed = true;
        if (th != null) {
            throw th;
        }
    }

    public final void finishDeflate$okio() throws IOException {
        this.deflater.finish();
        deflate(false);
    }

    @Override // okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        deflate(true);
        this.sink.flush();
    }

    @Override // okio.Sink
    @NotNull
    public Timeout timeout() {
        return this.sink.timeout();
    }

    @NotNull
    public String toString() {
        return "DeflaterSink(" + this.sink + ')';
    }

    @Override // okio.Sink
    public void write(@NotNull Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        _UtilKt.checkOffsetAndCount(source.size(), 0L, byteCount);
        while (byteCount > 0) {
            Segment segment = source.head;
            Intrinsics.checkNotNull(segment);
            int iMin = (int) Math.min(byteCount, segment.limit - segment.pos);
            this.deflater.setInput(segment.data, segment.pos, iMin);
            deflate(false);
            long j2 = iMin;
            source.setSize$okio(source.size() - j2);
            int i2 = segment.pos + iMin;
            segment.pos = i2;
            if (i2 == segment.limit) {
                source.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            byteCount -= j2;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeflaterSink(@NotNull Sink sink, @NotNull Deflater deflater) {
        this(Okio.buffer(sink), deflater);
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(deflater, "deflater");
    }
}
