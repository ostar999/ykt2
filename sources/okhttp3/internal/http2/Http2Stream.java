package okhttp3.internal.http2;

import com.arialyy.aria.core.inf.IOptionConstant;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.tencent.open.SocialConstants;
import io.socket.engineio.client.Socket;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 _2\u00020\u0001:\u0004_`abB1\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u000e\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020#J\r\u0010C\u001a\u00020AH\u0000¢\u0006\u0002\bDJ\r\u0010E\u001a\u00020AH\u0000¢\u0006\u0002\bFJ\u0018\u0010G\u001a\u00020A2\u0006\u0010H\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001a\u0010I\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u000e\u0010J\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010K\u001a\u00020A2\u0006\u0010L\u001a\u00020\nJ\u0006\u0010M\u001a\u00020NJ\u0006\u0010O\u001a\u00020PJ\u0006\u0010,\u001a\u00020QJ\u0016\u0010R\u001a\u00020A2\u0006\u00104\u001a\u00020S2\u0006\u0010T\u001a\u00020\u0003J\u0016\u0010U\u001a\u00020A2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010V\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010W\u001a\u00020\nJ\u0006\u0010L\u001a\u00020\nJ\r\u0010X\u001a\u00020AH\u0000¢\u0006\u0002\bYJ$\u0010Z\u001a\u00020A2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010^\u001a\u00020\u0007J\u0006\u0010>\u001a\u00020QR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u000f8@X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010$\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R$\u0010)\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u0018\u0010,\u001a\u00060-R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0018\u00100\u001a\u000601R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0018\u00104\u001a\u000605R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R$\u00108\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010&\"\u0004\b:\u0010(R$\u0010;\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010&\"\u0004\b=\u0010(R\u0018\u0010>\u001a\u00060-R\u00020\u0000X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010/¨\u0006c"}, d2 = {"Lokhttp3/internal/http2/Http2Stream;", "", "id", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "outFinished", "", "inFinished", IOptionConstant.headers, "Lokhttp3/Headers;", "(ILokhttp3/internal/http2/Http2Connection;ZZLokhttp3/Headers;)V", "getConnection", "()Lokhttp3/internal/http2/Http2Connection;", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "getErrorCode$okhttp", "()Lokhttp3/internal/http2/ErrorCode;", "setErrorCode$okhttp", "(Lokhttp3/internal/http2/ErrorCode;)V", "errorException", "Ljava/io/IOException;", "getErrorException$okhttp", "()Ljava/io/IOException;", "setErrorException$okhttp", "(Ljava/io/IOException;)V", "hasResponseHeaders", "headersQueue", "Ljava/util/ArrayDeque;", "getId", "()I", "isLocallyInitiated", "()Z", "isOpen", "<set-?>", "", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "setReadBytesAcknowledged$okhttp", "(J)V", "readBytesTotal", "getReadBytesTotal", "setReadBytesTotal$okhttp", "readTimeout", "Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "getReadTimeout$okhttp", "()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "sink", "Lokhttp3/internal/http2/Http2Stream$FramingSink;", "getSink$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSink;", SocialConstants.PARAM_SOURCE, "Lokhttp3/internal/http2/Http2Stream$FramingSource;", "getSource$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSource;", "writeBytesMaximum", "getWriteBytesMaximum", "setWriteBytesMaximum$okhttp", "writeBytesTotal", "getWriteBytesTotal", "setWriteBytesTotal$okhttp", "writeTimeout", "getWriteTimeout$okhttp", "addBytesToWriteWindow", "", "delta", "cancelStreamIfNecessary", "cancelStreamIfNecessary$okhttp", "checkOutNotClosed", "checkOutNotClosed$okhttp", "close", "rstStatusCode", "closeInternal", "closeLater", "enqueueTrailers", "trailers", "getSink", "Lokio/Sink;", "getSource", "Lokio/Source;", "Lokio/Timeout;", "receiveData", "Lokio/BufferedSource;", SessionDescription.ATTR_LENGTH, "receiveHeaders", "receiveRstStream", "takeHeaders", "waitForIo", "waitForIo$okhttp", "writeHeaders", "responseHeaders", "", "Lokhttp3/internal/http2/Header;", "flushHeaders", "Companion", "FramingSink", "FramingSource", "StreamTimeout", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class Http2Stream {
    public static final long EMIT_BUFFER_SIZE = 16384;

    @NotNull
    private final Http2Connection connection;

    @Nullable
    private ErrorCode errorCode;

    @Nullable
    private IOException errorException;
    private boolean hasResponseHeaders;

    @NotNull
    private final ArrayDeque<Headers> headersQueue;
    private final int id;
    private long readBytesAcknowledged;
    private long readBytesTotal;

    @NotNull
    private final StreamTimeout readTimeout;

    @NotNull
    private final FramingSink sink;

    @NotNull
    private final FramingSource source;
    private long writeBytesMaximum;
    private long writeBytesTotal;

    @NotNull
    private final StreamTimeout writeTimeout;

    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0003H\u0016J\u001d\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u0003H\u0000¢\u0006\u0002\b\"J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0002R\u001a\u0010\u0007\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006&"}, d2 = {"Lokhttp3/internal/http2/Http2Stream$FramingSource;", "Lokio/Source;", "maxByteCount", "", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;JZ)V", "closed", "getClosed$okhttp", "()Z", "setClosed$okhttp", "(Z)V", "getFinished$okhttp", "setFinished$okhttp", "readBuffer", "Lokio/Buffer;", "getReadBuffer", "()Lokio/Buffer;", "receiveBuffer", "getReceiveBuffer", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "read", "sink", "byteCount", "receive", SocialConstants.PARAM_SOURCE, "Lokio/BufferedSource;", "receive$okhttp", "timeout", "Lokio/Timeout;", "updateConnectionFlowControl", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class FramingSource implements Source {
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;

        @NotNull
        private final Buffer readBuffer;

        @NotNull
        private final Buffer receiveBuffer;
        final /* synthetic */ Http2Stream this$0;

        @Nullable
        private Headers trailers;

        public FramingSource(Http2Stream this$0, long j2, boolean z2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
            this.maxByteCount = j2;
            this.finished = z2;
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
        }

        private final void updateConnectionFlowControl(long read) {
            Http2Stream http2Stream = this.this$0;
            if (!Util.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                this.this$0.getConnection().updateConnectionFlowControl$okhttp(read);
                return;
            }
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + http2Stream);
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            long size;
            Http2Stream http2Stream = this.this$0;
            synchronized (http2Stream) {
                setClosed$okhttp(true);
                size = getReadBuffer().size();
                getReadBuffer().clear();
                http2Stream.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            if (size > 0) {
                updateConnectionFlowControl(size);
            }
            this.this$0.cancelStreamIfNecessary$okhttp();
        }

        /* renamed from: getClosed$okhttp, reason: from getter */
        public final boolean getClosed() {
            return this.closed;
        }

        /* renamed from: getFinished$okhttp, reason: from getter */
        public final boolean getFinished() {
            return this.finished;
        }

        @NotNull
        public final Buffer getReadBuffer() {
            return this.readBuffer;
        }

        @NotNull
        public final Buffer getReceiveBuffer() {
            return this.receiveBuffer;
        }

        @Nullable
        public final Headers getTrailers() {
            return this.trailers;
        }

        @Override // okio.Source
        public long read(@NotNull Buffer sink, long byteCount) throws IOException {
            IOException errorException;
            long j2;
            boolean z2;
            Intrinsics.checkNotNullParameter(sink, "sink");
            long j3 = 0;
            if (!(byteCount >= 0)) {
                throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount < 0: ", Long.valueOf(byteCount)).toString());
            }
            while (true) {
                Http2Stream http2Stream = this.this$0;
                synchronized (http2Stream) {
                    http2Stream.getReadTimeout().enter();
                    try {
                        if (http2Stream.getErrorCode$okhttp() == null || getFinished()) {
                            errorException = null;
                        } else {
                            errorException = http2Stream.getErrorException();
                            if (errorException == null) {
                                ErrorCode errorCode$okhttp = http2Stream.getErrorCode$okhttp();
                                Intrinsics.checkNotNull(errorCode$okhttp);
                                errorException = new StreamResetException(errorCode$okhttp);
                            }
                        }
                        if (getClosed()) {
                            throw new IOException("stream closed");
                        }
                        if (getReadBuffer().size() > j3) {
                            j2 = getReadBuffer().read(sink, Math.min(byteCount, getReadBuffer().size()));
                            http2Stream.setReadBytesTotal$okhttp(http2Stream.getReadBytesTotal() + j2);
                            long readBytesTotal = http2Stream.getReadBytesTotal() - http2Stream.getReadBytesAcknowledged();
                            if (errorException == null && readBytesTotal >= http2Stream.getConnection().getOkHttpSettings().getInitialWindowSize() / 2) {
                                http2Stream.getConnection().writeWindowUpdateLater$okhttp(http2Stream.getId(), readBytesTotal);
                                http2Stream.setReadBytesAcknowledged$okhttp(http2Stream.getReadBytesTotal());
                            }
                        } else if (getFinished() || errorException != null) {
                            j2 = -1;
                        } else {
                            http2Stream.waitForIo$okhttp();
                            j2 = -1;
                            z2 = true;
                            http2Stream.getReadTimeout().exitAndThrowIfTimedOut();
                            Unit unit = Unit.INSTANCE;
                        }
                        z2 = false;
                        http2Stream.getReadTimeout().exitAndThrowIfTimedOut();
                        Unit unit2 = Unit.INSTANCE;
                    } finally {
                    }
                }
                if (!z2) {
                    if (j2 != -1) {
                        updateConnectionFlowControl(j2);
                        return j2;
                    }
                    if (errorException == null) {
                        return -1L;
                    }
                    throw errorException;
                }
                j3 = 0;
            }
        }

        public final void receive$okhttp(@NotNull BufferedSource source, long byteCount) throws IOException {
            boolean finished;
            boolean z2;
            boolean z3;
            long size;
            Intrinsics.checkNotNullParameter(source, "source");
            Http2Stream http2Stream = this.this$0;
            if (Util.assertionsEnabled && Thread.holdsLock(http2Stream)) {
                throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + http2Stream);
            }
            while (byteCount > 0) {
                synchronized (this.this$0) {
                    finished = getFinished();
                    z2 = true;
                    z3 = getReadBuffer().size() + byteCount > this.maxByteCount;
                    Unit unit = Unit.INSTANCE;
                }
                if (z3) {
                    source.skip(byteCount);
                    this.this$0.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (finished) {
                    source.skip(byteCount);
                    return;
                }
                long j2 = source.read(this.receiveBuffer, byteCount);
                if (j2 == -1) {
                    throw new EOFException();
                }
                byteCount -= j2;
                Http2Stream http2Stream2 = this.this$0;
                synchronized (http2Stream2) {
                    if (getClosed()) {
                        size = getReceiveBuffer().size();
                        getReceiveBuffer().clear();
                    } else {
                        if (getReadBuffer().size() != 0) {
                            z2 = false;
                        }
                        getReadBuffer().writeAll(getReceiveBuffer());
                        if (z2) {
                            http2Stream2.notifyAll();
                        }
                        size = 0;
                    }
                }
                if (size > 0) {
                    updateConnectionFlowControl(size);
                }
            }
        }

        public final void setClosed$okhttp(boolean z2) {
            this.closed = z2;
        }

        public final void setFinished$okhttp(boolean z2) {
            this.finished = z2;
        }

        public final void setTrailers(@Nullable Headers headers) {
            this.trailers = headers;
        }

        @Override // okio.Source
        @NotNull
        /* renamed from: timeout */
        public Timeout getThis$0() {
            return this.this$0.getReadTimeout();
        }
    }

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0014J\b\u0010\b\u001a\u00020\u0004H\u0014¨\u0006\t"}, d2 = {"Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "Lokio/AsyncTimeout;", "(Lokhttp3/internal/http2/Http2Stream;)V", "exitAndThrowIfTimedOut", "", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class StreamTimeout extends AsyncTimeout {
        final /* synthetic */ Http2Stream this$0;

        public StreamTimeout(Http2Stream this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        public final void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }

        @Override // okio.AsyncTimeout
        @NotNull
        public IOException newTimeoutException(@Nullable IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }

        @Override // okio.AsyncTimeout
        public void timedOut() {
            this.this$0.closeLater(ErrorCode.CANCEL);
            this.this$0.getConnection().sendDegradedPingLater$okhttp();
        }
    }

    public Http2Stream(int i2, @NotNull Http2Connection connection, boolean z2, boolean z3, @Nullable Headers headers) {
        Intrinsics.checkNotNullParameter(connection, "connection");
        this.id = i2;
        this.connection = connection;
        this.writeBytesMaximum = connection.getPeerSettings().getInitialWindowSize();
        ArrayDeque<Headers> arrayDeque = new ArrayDeque<>();
        this.headersQueue = arrayDeque;
        this.source = new FramingSource(this, connection.getOkHttpSettings().getInitialWindowSize(), z3);
        this.sink = new FramingSink(this, z2);
        this.readTimeout = new StreamTimeout(this);
        this.writeTimeout = new StreamTimeout(this);
        if (headers == null) {
            if (!isLocallyInitiated()) {
                throw new IllegalStateException("remotely-initiated streams should have headers".toString());
            }
        } else {
            if (!(!isLocallyInitiated())) {
                throw new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString());
            }
            arrayDeque.add(headers);
        }
    }

    private final boolean closeInternal(ErrorCode errorCode, IOException errorException) {
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + this);
        }
        synchronized (this) {
            if (getErrorCode$okhttp() != null) {
                return false;
            }
            if (getSource().getFinished() && getSink().getFinished()) {
                return false;
            }
            setErrorCode$okhttp(errorCode);
            setErrorException$okhttp(errorException);
            notifyAll();
            Unit unit = Unit.INSTANCE;
            this.connection.removeStream$okhttp(this.id);
            return true;
        }
    }

    public final void addBytesToWriteWindow(long delta) {
        this.writeBytesMaximum += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    public final void cancelStreamIfNecessary$okhttp() throws IOException {
        boolean z2;
        boolean zIsOpen;
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + this);
        }
        synchronized (this) {
            z2 = !getSource().getFinished() && getSource().getClosed() && (getSink().getFinished() || getSink().getClosed());
            zIsOpen = isOpen();
            Unit unit = Unit.INSTANCE;
        }
        if (z2) {
            close(ErrorCode.CANCEL, null);
        } else {
            if (zIsOpen) {
                return;
            }
            this.connection.removeStream$okhttp(this.id);
        }
    }

    public final void checkOutNotClosed$okhttp() throws IOException {
        if (this.sink.getClosed()) {
            throw new IOException("stream closed");
        }
        if (this.sink.getFinished()) {
            throw new IOException("stream finished");
        }
        if (this.errorCode != null) {
            IOException iOException = this.errorException;
            if (iOException != null) {
                throw iOException;
            }
            ErrorCode errorCode = this.errorCode;
            Intrinsics.checkNotNull(errorCode);
            throw new StreamResetException(errorCode);
        }
    }

    public final void close(@NotNull ErrorCode rstStatusCode, @Nullable IOException errorException) throws IOException {
        Intrinsics.checkNotNullParameter(rstStatusCode, "rstStatusCode");
        if (closeInternal(rstStatusCode, errorException)) {
            this.connection.writeSynReset$okhttp(this.id, rstStatusCode);
        }
    }

    public final void closeLater(@NotNull ErrorCode errorCode) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        if (closeInternal(errorCode, null)) {
            this.connection.writeSynResetLater$okhttp(this.id, errorCode);
        }
    }

    public final void enqueueTrailers(@NotNull Headers trailers) {
        Intrinsics.checkNotNullParameter(trailers, "trailers");
        synchronized (this) {
            boolean z2 = true;
            if (!(!getSink().getFinished())) {
                throw new IllegalStateException("already finished".toString());
            }
            if (trailers.size() == 0) {
                z2 = false;
            }
            if (!z2) {
                throw new IllegalArgumentException("trailers.size() == 0".toString());
            }
            getSink().setTrailers(trailers);
            Unit unit = Unit.INSTANCE;
        }
    }

    @NotNull
    public final Http2Connection getConnection() {
        return this.connection;
    }

    @Nullable
    public final synchronized ErrorCode getErrorCode$okhttp() {
        return this.errorCode;
    }

    @Nullable
    /* renamed from: getErrorException$okhttp, reason: from getter */
    public final IOException getErrorException() {
        return this.errorException;
    }

    public final int getId() {
        return this.id;
    }

    public final long getReadBytesAcknowledged() {
        return this.readBytesAcknowledged;
    }

    public final long getReadBytesTotal() {
        return this.readBytesTotal;
    }

    @NotNull
    /* renamed from: getReadTimeout$okhttp, reason: from getter */
    public final StreamTimeout getReadTimeout() {
        return this.readTimeout;
    }

    @NotNull
    public final Sink getSink() {
        synchronized (this) {
            if (!(this.hasResponseHeaders || isLocallyInitiated())) {
                throw new IllegalStateException("reply before requesting the sink".toString());
            }
            Unit unit = Unit.INSTANCE;
        }
        return this.sink;
    }

    @NotNull
    /* renamed from: getSink$okhttp, reason: from getter */
    public final FramingSink getSink() {
        return this.sink;
    }

    @NotNull
    public final Source getSource() {
        return this.source;
    }

    @NotNull
    /* renamed from: getSource$okhttp, reason: from getter */
    public final FramingSource getSource() {
        return this.source;
    }

    public final long getWriteBytesMaximum() {
        return this.writeBytesMaximum;
    }

    public final long getWriteBytesTotal() {
        return this.writeBytesTotal;
    }

    @NotNull
    /* renamed from: getWriteTimeout$okhttp, reason: from getter */
    public final StreamTimeout getWriteTimeout() {
        return this.writeTimeout;
    }

    public final boolean isLocallyInitiated() {
        return this.connection.getClient() == ((this.id & 1) == 1);
    }

    public final synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.getFinished() || this.source.getClosed()) && (this.sink.getFinished() || this.sink.getClosed())) {
            if (this.hasResponseHeaders) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public final Timeout readTimeout() {
        return this.readTimeout;
    }

    public final void receiveData(@NotNull BufferedSource source, int length) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        if (!Util.assertionsEnabled || !Thread.holdsLock(this)) {
            this.source.receive$okhttp(source, length);
            return;
        }
        throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + this);
    }

    public final void receiveHeaders(@NotNull Headers headers, boolean inFinished) {
        boolean zIsOpen;
        Intrinsics.checkNotNullParameter(headers, "headers");
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + this);
        }
        synchronized (this) {
            if (this.hasResponseHeaders && inFinished) {
                getSource().setTrailers(headers);
            } else {
                this.hasResponseHeaders = true;
                this.headersQueue.add(headers);
            }
            if (inFinished) {
                getSource().setFinished$okhttp(true);
            }
            zIsOpen = isOpen();
            notifyAll();
            Unit unit = Unit.INSTANCE;
        }
        if (zIsOpen) {
            return;
        }
        this.connection.removeStream$okhttp(this.id);
    }

    public final synchronized void receiveRstStream(@NotNull ErrorCode errorCode) {
        Intrinsics.checkNotNullParameter(errorCode, "errorCode");
        if (this.errorCode == null) {
            this.errorCode = errorCode;
            notifyAll();
        }
    }

    public final void setErrorCode$okhttp(@Nullable ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public final void setErrorException$okhttp(@Nullable IOException iOException) {
        this.errorException = iOException;
    }

    public final void setReadBytesAcknowledged$okhttp(long j2) {
        this.readBytesAcknowledged = j2;
    }

    public final void setReadBytesTotal$okhttp(long j2) {
        this.readBytesTotal = j2;
    }

    public final void setWriteBytesMaximum$okhttp(long j2) {
        this.writeBytesMaximum = j2;
    }

    public final void setWriteBytesTotal$okhttp(long j2) {
        this.writeBytesTotal = j2;
    }

    @NotNull
    public final synchronized Headers takeHeaders() throws IOException {
        Headers headersRemoveFirst;
        this.readTimeout.enter();
        while (this.headersQueue.isEmpty() && this.errorCode == null) {
            try {
                waitForIo$okhttp();
            } catch (Throwable th) {
                this.readTimeout.exitAndThrowIfTimedOut();
                throw th;
            }
        }
        this.readTimeout.exitAndThrowIfTimedOut();
        if (!(!this.headersQueue.isEmpty())) {
            IOException iOException = this.errorException;
            if (iOException != null) {
                throw iOException;
            }
            ErrorCode errorCode = this.errorCode;
            Intrinsics.checkNotNull(errorCode);
            throw new StreamResetException(errorCode);
        }
        headersRemoveFirst = this.headersQueue.removeFirst();
        Intrinsics.checkNotNullExpressionValue(headersRemoveFirst, "headersQueue.removeFirst()");
        return headersRemoveFirst;
    }

    @NotNull
    public final synchronized Headers trailers() throws IOException {
        Headers trailers;
        if (!this.source.getFinished() || !this.source.getReceiveBuffer().exhausted() || !this.source.getReadBuffer().exhausted()) {
            if (this.errorCode == null) {
                throw new IllegalStateException("too early; can't read the trailers yet");
            }
            IOException iOException = this.errorException;
            if (iOException != null) {
                throw iOException;
            }
            ErrorCode errorCode = this.errorCode;
            Intrinsics.checkNotNull(errorCode);
            throw new StreamResetException(errorCode);
        }
        trailers = this.source.getTrailers();
        if (trailers == null) {
            trailers = Util.EMPTY_HEADERS;
        }
        return trailers;
    }

    public final void waitForIo$okhttp() throws InterruptedException, InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    public final void writeHeaders(@NotNull List<Header> responseHeaders, boolean outFinished, boolean flushHeaders) throws IOException {
        boolean z2;
        Intrinsics.checkNotNullParameter(responseHeaders, "responseHeaders");
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + this);
        }
        synchronized (this) {
            this.hasResponseHeaders = true;
            if (outFinished) {
                getSink().setFinished(true);
            }
            Unit unit = Unit.INSTANCE;
        }
        if (!flushHeaders) {
            synchronized (this.connection) {
                z2 = getConnection().getWriteBytesTotal() >= getConnection().getWriteBytesMaximum();
            }
            flushHeaders = z2;
        }
        this.connection.writeHeaders$okhttp(this.id, outFinished, responseHeaders);
        if (flushHeaders) {
            this.connection.flush();
        }
    }

    @NotNull
    public final Timeout writeTimeout() {
        return this.writeTimeout;
    }

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0003H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lokhttp3/internal/http2/Http2Stream$FramingSink;", "Lokio/Sink;", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;Z)V", "closed", "getClosed", "()Z", "setClosed", "(Z)V", "getFinished", "setFinished", "sendBuffer", "Lokio/Buffer;", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "emitFrame", "outFinishedOnLastFrame", Socket.EVENT_FLUSH, "timeout", "Lokio/Timeout;", "write", SocialConstants.PARAM_SOURCE, "byteCount", "", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public final class FramingSink implements Sink {
        private boolean closed;
        private boolean finished;

        @NotNull
        private final Buffer sendBuffer;

        @Nullable
        private Headers trailers;

        public FramingSink(Http2Stream this$0, boolean z2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Http2Stream.this = this$0;
            this.finished = z2;
            this.sendBuffer = new Buffer();
        }

        private final void emitFrame(boolean outFinishedOnLastFrame) throws IOException {
            long jMin;
            boolean z2;
            Http2Stream http2Stream = Http2Stream.this;
            synchronized (http2Stream) {
                http2Stream.getWriteTimeout().enter();
                while (http2Stream.getWriteBytesTotal() >= http2Stream.getWriteBytesMaximum() && !getFinished() && !getClosed() && http2Stream.getErrorCode$okhttp() == null) {
                    try {
                        http2Stream.waitForIo$okhttp();
                    } finally {
                        http2Stream.getWriteTimeout().exitAndThrowIfTimedOut();
                    }
                }
                http2Stream.getWriteTimeout().exitAndThrowIfTimedOut();
                http2Stream.checkOutNotClosed$okhttp();
                jMin = Math.min(http2Stream.getWriteBytesMaximum() - http2Stream.getWriteBytesTotal(), this.sendBuffer.size());
                http2Stream.setWriteBytesTotal$okhttp(http2Stream.getWriteBytesTotal() + jMin);
                z2 = outFinishedOnLastFrame && jMin == this.sendBuffer.size();
                Unit unit = Unit.INSTANCE;
            }
            Http2Stream.this.getWriteTimeout().enter();
            try {
                Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), z2, this.sendBuffer, jMin);
            } finally {
                http2Stream = Http2Stream.this;
            }
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            Http2Stream http2Stream = Http2Stream.this;
            if (Util.assertionsEnabled && Thread.holdsLock(http2Stream)) {
                throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + http2Stream);
            }
            Http2Stream http2Stream2 = Http2Stream.this;
            synchronized (http2Stream2) {
                if (getClosed()) {
                    return;
                }
                boolean z2 = http2Stream2.getErrorCode$okhttp() == null;
                Unit unit = Unit.INSTANCE;
                if (!Http2Stream.this.getSink().finished) {
                    boolean z3 = this.sendBuffer.size() > 0;
                    if (this.trailers != null) {
                        while (this.sendBuffer.size() > 0) {
                            emitFrame(false);
                        }
                        Http2Connection connection = Http2Stream.this.getConnection();
                        int id = Http2Stream.this.getId();
                        Headers headers = this.trailers;
                        Intrinsics.checkNotNull(headers);
                        connection.writeHeaders$okhttp(id, z2, Util.toHeaderList(headers));
                    } else if (z3) {
                        while (this.sendBuffer.size() > 0) {
                            emitFrame(true);
                        }
                    } else if (z2) {
                        Http2Stream.this.getConnection().writeData(Http2Stream.this.getId(), true, null, 0L);
                    }
                }
                synchronized (Http2Stream.this) {
                    setClosed(true);
                    Unit unit2 = Unit.INSTANCE;
                }
                Http2Stream.this.getConnection().flush();
                Http2Stream.this.cancelStreamIfNecessary$okhttp();
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            Http2Stream http2Stream = Http2Stream.this;
            if (Util.assertionsEnabled && Thread.holdsLock(http2Stream)) {
                throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + http2Stream);
            }
            Http2Stream http2Stream2 = Http2Stream.this;
            synchronized (http2Stream2) {
                http2Stream2.checkOutNotClosed$okhttp();
                Unit unit = Unit.INSTANCE;
            }
            while (this.sendBuffer.size() > 0) {
                emitFrame(false);
                Http2Stream.this.getConnection().flush();
            }
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final boolean getFinished() {
            return this.finished;
        }

        @Nullable
        public final Headers getTrailers() {
            return this.trailers;
        }

        public final void setClosed(boolean z2) {
            this.closed = z2;
        }

        public final void setFinished(boolean z2) {
            this.finished = z2;
        }

        public final void setTrailers(@Nullable Headers headers) {
            this.trailers = headers;
        }

        @Override // okio.Sink
        @NotNull
        /* renamed from: timeout */
        public Timeout getThis$0() {
            return Http2Stream.this.getWriteTimeout();
        }

        @Override // okio.Sink
        public void write(@NotNull Buffer source, long byteCount) throws IOException {
            Intrinsics.checkNotNullParameter(source, "source");
            Http2Stream http2Stream = Http2Stream.this;
            if (!Util.assertionsEnabled || !Thread.holdsLock(http2Stream)) {
                this.sendBuffer.write(source, byteCount);
                while (this.sendBuffer.size() >= 16384) {
                    emitFrame(false);
                }
            } else {
                throw new AssertionError("Thread " + ((Object) Thread.currentThread().getName()) + " MUST NOT hold lock on " + http2Stream);
            }
        }

        public /* synthetic */ FramingSink(boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(Http2Stream.this, (i2 & 1) != 0 ? false : z2);
        }
    }
}
