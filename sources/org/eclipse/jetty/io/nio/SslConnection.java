package org.eclipse.jetty.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes9.dex */
public class SslConnection extends AbstractConnection implements AsyncConnection {
    private static final NIOBuffer __ZERO_BUFFER = new IndirectNIOBuffer(0);
    private static final ThreadLocal<SslBuffers> __buffers = new ThreadLocal<>();
    private AsyncEndPoint _aEndp;
    private int _allocations;
    private boolean _allowRenegotiate;
    private SslBuffers _buffers;
    private AsyncConnection _connection;
    private final SSLEngine _engine;
    private boolean _handshook;
    private NIOBuffer _inbound;
    private boolean _ishut;
    private final Logger _logger;
    private boolean _oshut;
    private NIOBuffer _outbound;
    private final AtomicBoolean _progressed;
    private final SSLSession _session;
    private final SslEndPoint _sslEndPoint;
    private NIOBuffer _unwrapBuf;

    /* renamed from: org.eclipse.jetty.io.nio.SslConnection$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status;

        static {
            int[] iArr = new int[SSLEngineResult.Status.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status = iArr;
            try {
                iArr[SSLEngineResult.Status.BUFFER_UNDERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.BUFFER_OVERFLOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.OK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr2;
            try {
                iArr2[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static class SslBuffers {
        final NIOBuffer _in;
        final NIOBuffer _out;
        final NIOBuffer _unwrap;

        public SslBuffers(int i2, int i3) {
            this._in = new IndirectNIOBuffer(i2);
            this._out = new IndirectNIOBuffer(i2);
            this._unwrap = new IndirectNIOBuffer(i3);
        }
    }

    public SslConnection(SSLEngine sSLEngine, EndPoint endPoint) {
        this(sSLEngine, endPoint, System.currentTimeMillis());
    }

    private void allocateBuffers() {
        synchronized (this) {
            int i2 = this._allocations;
            this._allocations = i2 + 1;
            if (i2 == 0 && this._buffers == null) {
                ThreadLocal<SslBuffers> threadLocal = __buffers;
                SslBuffers sslBuffers = threadLocal.get();
                this._buffers = sslBuffers;
                if (sslBuffers == null) {
                    this._buffers = new SslBuffers(this._session.getPacketBufferSize() * 2, this._session.getApplicationBufferSize() * 2);
                }
                SslBuffers sslBuffers2 = this._buffers;
                this._inbound = sslBuffers2._in;
                this._outbound = sslBuffers2._out;
                this._unwrapBuf = sslBuffers2._unwrap;
                threadLocal.set(null);
            }
        }
    }

    private void closeInbound() throws SSLException {
        try {
            this._engine.closeInbound();
        } catch (SSLException e2) {
            this._logger.debug(e2);
        }
    }

    private ByteBuffer extractByteBuffer(Buffer buffer) {
        return buffer.buffer() instanceof NIOBuffer ? ((NIOBuffer) buffer.buffer()).getByteBuffer() : ByteBuffer.wrap(buffer.array());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean process(Buffer buffer, Buffer buffer2) throws IOException {
        int iFill;
        boolean z2;
        int iFlush;
        Buffer buffer3 = buffer;
        Buffer buffer4 = buffer2;
        synchronized (this) {
            boolean z3 = false;
            try {
                allocateBuffers();
                if (buffer3 == null) {
                    this._unwrapBuf.compact();
                    buffer3 = this._unwrapBuf;
                } else {
                    if (buffer.capacity() < this._session.getApplicationBufferSize()) {
                        boolean zProcess = process(null, buffer4);
                        NIOBuffer nIOBuffer = this._unwrapBuf;
                        if (nIOBuffer == null || !nIOBuffer.hasContent()) {
                            releaseBuffers();
                            return zProcess;
                        }
                        NIOBuffer nIOBuffer2 = this._unwrapBuf;
                        nIOBuffer2.skip(buffer3.put(nIOBuffer2));
                        releaseBuffers();
                        return true;
                    }
                    NIOBuffer nIOBuffer3 = this._unwrapBuf;
                    if (nIOBuffer3 != null && nIOBuffer3.hasContent()) {
                        NIOBuffer nIOBuffer4 = this._unwrapBuf;
                        nIOBuffer4.skip(buffer3.put(nIOBuffer4));
                        releaseBuffers();
                        return true;
                    }
                }
                if (buffer4 == null) {
                    buffer4 = __ZERO_BUFFER;
                }
                boolean z4 = true;
                boolean z5 = false;
                while (z4) {
                    try {
                        if (this._inbound.space() > 0) {
                            iFill = this._endp.fill(this._inbound);
                            z2 = iFill > 0;
                        } else {
                            iFill = 0;
                            z2 = false;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        iFill = 0;
                    } catch (Throwable th) {
                        th = th;
                        iFill = 0;
                        this._logger.debug("{} {} {} filled={}/{} flushed={}/{}", this._session, this, this._engine.getHandshakeStatus(), Integer.valueOf(iFill), Integer.valueOf(this._inbound.length()), 0, Integer.valueOf(this._outbound.length()));
                        throw th;
                    }
                    try {
                        try {
                            if (this._outbound.hasContent()) {
                                iFlush = this._endp.flush(this._outbound);
                                if (iFlush > 0) {
                                    z2 = true;
                                }
                            } else {
                                iFlush = 0;
                            }
                            try {
                                this._logger.debug("{} {} {} filled={}/{} flushed={}/{}", this._session, this, this._engine.getHandshakeStatus(), Integer.valueOf(iFill), Integer.valueOf(this._inbound.length()), Integer.valueOf(iFlush), Integer.valueOf(this._outbound.length()));
                                int i2 = AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[this._engine.getHandshakeStatus().ordinal()];
                                if (i2 == 1) {
                                    throw new IllegalStateException();
                                }
                                if (i2 != 2) {
                                    if (i2 == 3) {
                                        while (true) {
                                            Runnable delegatedTask = this._engine.getDelegatedTask();
                                            if (delegatedTask == null) {
                                                break;
                                            }
                                            delegatedTask.run();
                                            z2 = true;
                                        }
                                    } else if (i2 != 4) {
                                        if (i2 == 5) {
                                            if (this._handshook && !this._allowRenegotiate) {
                                                this._endp.close();
                                            } else if (!this._inbound.hasContent() && iFill == -1) {
                                                this._endp.shutdownInput();
                                            } else if (unwrap(buffer3)) {
                                                z4 = true;
                                            }
                                        }
                                    } else if (this._handshook && !this._allowRenegotiate) {
                                        this._endp.close();
                                    } else if (wrap(buffer4)) {
                                        z4 = true;
                                    }
                                    z4 = z2;
                                } else {
                                    if (buffer3.space() > 0 && this._inbound.hasContent() && unwrap(buffer3)) {
                                        z2 = true;
                                    }
                                    if (buffer4.hasContent() && this._outbound.space() > 0 && wrap(buffer4)) {
                                        z4 = true;
                                    }
                                    z4 = z2;
                                }
                                if (this._endp.isOpen() && this._endp.isInputShutdown() && !this._inbound.hasContent()) {
                                    closeInbound();
                                }
                                if (this._endp.isOpen() && this._engine.isOutboundDone() && !this._outbound.hasContent()) {
                                    this._endp.shutdownOutput();
                                }
                                z5 |= z4;
                            } catch (Throwable th2) {
                                th = th2;
                                z3 = z5;
                                releaseBuffers();
                                if (z3) {
                                    this._progressed.set(true);
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            this._logger.debug("{} {} {} filled={}/{} flushed={}/{}", this._session, this, this._engine.getHandshakeStatus(), Integer.valueOf(iFill), Integer.valueOf(this._inbound.length()), 0, Integer.valueOf(this._outbound.length()));
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        this._endp.close();
                        throw e;
                    }
                }
                NIOBuffer nIOBuffer5 = this._unwrapBuf;
                if (buffer3 == nIOBuffer5 && nIOBuffer5.hasContent() && !this._connection.isSuspended()) {
                    this._aEndp.dispatch();
                }
                releaseBuffers();
                if (z5) {
                    this._progressed.set(true);
                }
                return z5;
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    private void releaseBuffers() {
        synchronized (this) {
            int i2 = this._allocations - 1;
            this._allocations = i2;
            if (i2 == 0 && this._buffers != null && this._inbound.length() == 0 && this._outbound.length() == 0 && this._unwrapBuf.length() == 0) {
                this._inbound = null;
                this._outbound = null;
                this._unwrapBuf = null;
                __buffers.set(this._buffers);
                this._buffers = null;
            }
        }
    }

    private synchronized boolean unwrap(Buffer buffer) throws IOException {
        SSLEngineResult sSLEngineResultUnwrap;
        int i2 = 0;
        int i3 = 0;
        if (!this._inbound.hasContent()) {
            return false;
        }
        ByteBuffer byteBufferExtractByteBuffer = extractByteBuffer(buffer);
        synchronized (byteBufferExtractByteBuffer) {
            ByteBuffer byteBuffer = this._inbound.getByteBuffer();
            synchronized (byteBuffer) {
                try {
                    try {
                        byteBufferExtractByteBuffer.position(buffer.putIndex());
                        byteBufferExtractByteBuffer.limit(buffer.capacity());
                        byteBuffer.position(this._inbound.getIndex());
                        byteBuffer.limit(this._inbound.putIndex());
                        sSLEngineResultUnwrap = this._engine.unwrap(byteBuffer, byteBufferExtractByteBuffer);
                        if (this._logger.isDebugEnabled()) {
                            this._logger.debug("{} unwrap {} {} consumed={} produced={}", this._session, sSLEngineResultUnwrap.getStatus(), sSLEngineResultUnwrap.getHandshakeStatus(), Integer.valueOf(sSLEngineResultUnwrap.bytesConsumed()), Integer.valueOf(sSLEngineResultUnwrap.bytesProduced()));
                        }
                        this._inbound.skip(sSLEngineResultUnwrap.bytesConsumed());
                        this._inbound.compact();
                        buffer.setPutIndex(buffer.putIndex() + sSLEngineResultUnwrap.bytesProduced());
                    } catch (SSLException e2) {
                        this._logger.debug(String.valueOf(this._endp), e2);
                        this._endp.close();
                        throw e2;
                    }
                } finally {
                    byteBuffer.position(0);
                    byteBuffer.limit(byteBuffer.capacity());
                    byteBufferExtractByteBuffer.position(0);
                    byteBufferExtractByteBuffer.limit(byteBufferExtractByteBuffer.capacity());
                }
            }
        }
        int i4 = AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[sSLEngineResultUnwrap.getStatus().ordinal()];
        if (i4 != 1) {
            if (i4 != 2) {
                if (i4 != 3) {
                    if (i4 != 4) {
                        this._logger.debug("{} wrap default {}", this._session, sSLEngineResultUnwrap);
                        throw new IOException(sSLEngineResultUnwrap.toString());
                    }
                    this._logger.debug("unwrap CLOSE {} {}", this, sSLEngineResultUnwrap);
                    if (sSLEngineResultUnwrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                        this._endp.close();
                    }
                } else if (sSLEngineResultUnwrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._handshook = true;
                }
            } else if (this._logger.isDebugEnabled()) {
                this._logger.debug("{} unwrap {} {}->{}", this._session, sSLEngineResultUnwrap.getStatus(), this._inbound.toDetailString(), buffer.toDetailString());
            }
        } else if (this._endp.isInputShutdown()) {
            this._inbound.clear();
        }
        return sSLEngineResultUnwrap.bytesConsumed() > 0 || sSLEngineResultUnwrap.bytesProduced() > 0;
    }

    private synchronized boolean wrap(Buffer buffer) throws IOException {
        SSLEngineResult sSLEngineResultWrap;
        ByteBuffer byteBufferExtractByteBuffer = extractByteBuffer(buffer);
        synchronized (byteBufferExtractByteBuffer) {
            this._outbound.compact();
            ByteBuffer byteBuffer = this._outbound.getByteBuffer();
            synchronized (byteBuffer) {
                int i2 = 0;
                int i3 = 0;
                try {
                    try {
                        byteBufferExtractByteBuffer.position(buffer.getIndex());
                        byteBufferExtractByteBuffer.limit(buffer.putIndex());
                        byteBuffer.position(this._outbound.putIndex());
                        byteBuffer.limit(byteBuffer.capacity());
                        sSLEngineResultWrap = this._engine.wrap(byteBufferExtractByteBuffer, byteBuffer);
                        if (this._logger.isDebugEnabled()) {
                            this._logger.debug("{} wrap {} {} consumed={} produced={}", this._session, sSLEngineResultWrap.getStatus(), sSLEngineResultWrap.getHandshakeStatus(), Integer.valueOf(sSLEngineResultWrap.bytesConsumed()), Integer.valueOf(sSLEngineResultWrap.bytesProduced()));
                        }
                        buffer.skip(sSLEngineResultWrap.bytesConsumed());
                        NIOBuffer nIOBuffer = this._outbound;
                        nIOBuffer.setPutIndex(nIOBuffer.putIndex() + sSLEngineResultWrap.bytesProduced());
                    } catch (SSLException e2) {
                        this._logger.debug(String.valueOf(this._endp), e2);
                        this._endp.close();
                        throw e2;
                    }
                } finally {
                    byteBuffer.position(0);
                    byteBuffer.limit(byteBuffer.capacity());
                    byteBufferExtractByteBuffer.position(0);
                    byteBufferExtractByteBuffer.limit(byteBufferExtractByteBuffer.capacity());
                }
            }
        }
        int i4 = AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[sSLEngineResultWrap.getStatus().ordinal()];
        if (i4 == 1) {
            throw new IllegalStateException();
        }
        if (i4 != 2) {
            if (i4 != 3) {
                if (i4 != 4) {
                    this._logger.debug("{} wrap default {}", this._session, sSLEngineResultWrap);
                    throw new IOException(sSLEngineResultWrap.toString());
                }
                this._logger.debug("wrap CLOSE {} {}", this, sSLEngineResultWrap);
                if (sSLEngineResultWrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._endp.close();
                }
            } else if (sSLEngineResultWrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                this._handshook = true;
            }
        }
        return sSLEngineResultWrap.bytesConsumed() > 0 || sSLEngineResultWrap.bytesProduced() > 0;
    }

    public AsyncEndPoint getSslEndPoint() {
        return this._sslEndPoint;
    }

    @Override // org.eclipse.jetty.io.Connection
    public Connection handle() throws IOException {
        try {
            allocateBuffers();
            boolean zProcess = true;
            while (zProcess) {
                zProcess = this._engine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING ? process(null, null) : false;
                AsyncConnection asyncConnection = (AsyncConnection) this._connection.handle();
                if (asyncConnection != this._connection && asyncConnection != null) {
                    this._connection = asyncConnection;
                    zProcess = true;
                }
                this._logger.debug("{} handle {} progress={}", this._session, this, Boolean.valueOf(zProcess));
            }
            return this;
        } finally {
            releaseBuffers();
            if (!this._ishut && this._sslEndPoint.isInputShutdown() && this._sslEndPoint.isOpen()) {
                this._ishut = true;
                try {
                    this._connection.onInputShutdown();
                } catch (Throwable th) {
                    this._logger.warn("onInputShutdown failed", th);
                    try {
                        this._sslEndPoint.close();
                    } catch (IOException e2) {
                        this._logger.ignore(e2);
                    }
                }
            }
        }
    }

    public boolean isAllowRenegotiate() {
        return this._allowRenegotiate;
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isIdle() {
        return false;
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return false;
    }

    public SslEndPoint newSslEndPoint() {
        return new SslEndPoint();
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onClose() {
        Connection connection = this._sslEndPoint.getConnection();
        if (connection == null || connection == this) {
            return;
        }
        connection.onClose();
    }

    @Override // org.eclipse.jetty.io.AbstractConnection, org.eclipse.jetty.io.Connection
    public void onIdleExpired(long j2) {
        try {
            this._logger.debug("onIdleExpired {}ms on {}", Long.valueOf(j2), this);
            if (this._endp.isOutputShutdown()) {
                this._sslEndPoint.close();
            } else {
                this._sslEndPoint.shutdownOutput();
            }
        } catch (IOException e2) {
            this._logger.warn(e2);
            super.onIdleExpired(j2);
        }
    }

    @Override // org.eclipse.jetty.io.nio.AsyncConnection
    public void onInputShutdown() throws IOException {
    }

    public void setAllowRenegotiate(boolean z2) {
        this._allowRenegotiate = z2;
    }

    @Override // org.eclipse.jetty.io.AbstractConnection
    public String toString() {
        return String.format("%s %s", super.toString(), this._sslEndPoint);
    }

    public SslConnection(SSLEngine sSLEngine, EndPoint endPoint, long j2) {
        super(endPoint, j2);
        this._logger = Log.getLogger("org.eclipse.jetty.io.nio.ssl");
        this._allowRenegotiate = true;
        this._progressed = new AtomicBoolean();
        this._engine = sSLEngine;
        this._session = sSLEngine.getSession();
        this._aEndp = (AsyncEndPoint) endPoint;
        this._sslEndPoint = newSslEndPoint();
    }

    public class SslEndPoint implements AsyncEndPoint {
        public SslEndPoint() {
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void asyncDispatch() {
            SslConnection.this._aEndp.asyncDispatch();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean blockReadable(long j2) throws IOException {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j3 = j2 > 0 ? j2 + jCurrentTimeMillis : Long.MAX_VALUE;
            while (jCurrentTimeMillis < j3 && !SslConnection.this.process(null, null)) {
                ((AbstractConnection) SslConnection.this)._endp.blockReadable(j3 - jCurrentTimeMillis);
                jCurrentTimeMillis = System.currentTimeMillis();
            }
            return jCurrentTimeMillis < j3;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean blockWritable(long j2) throws IOException {
            return ((AbstractConnection) SslConnection.this)._endp.blockWritable(j2);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void cancelTimeout(Timeout.Task task) {
            SslConnection.this._aEndp.cancelTimeout(task);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void close() throws IOException {
            SslConnection.this._logger.debug("{} ssl endp.close", SslConnection.this._session);
            ((AbstractConnection) SslConnection.this)._endp.close();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void dispatch() {
            SslConnection.this._aEndp.dispatch();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int fill(Buffer buffer) throws IOException {
            int length = buffer.length();
            SslConnection.this.process(buffer, null);
            int length2 = buffer.length() - length;
            if (length2 == 0 && isInputShutdown()) {
                return -1;
            }
            return length2;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int flush(Buffer buffer) throws IOException {
            int length = buffer.length();
            SslConnection.this.process(null, buffer);
            return length - buffer.length();
        }

        @Override // org.eclipse.jetty.io.ConnectedEndPoint
        public Connection getConnection() {
            return SslConnection.this._connection;
        }

        public AsyncEndPoint getEndpoint() {
            return SslConnection.this._aEndp;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getLocalAddr() {
            return SslConnection.this._aEndp.getLocalAddr();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getLocalHost() {
            return SslConnection.this._aEndp.getLocalHost();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int getLocalPort() {
            return SslConnection.this._aEndp.getLocalPort();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int getMaxIdleTime() {
            return SslConnection.this._aEndp.getMaxIdleTime();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getRemoteAddr() {
            return SslConnection.this._aEndp.getRemoteAddr();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getRemoteHost() {
            return SslConnection.this._aEndp.getRemoteHost();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int getRemotePort() {
            return SslConnection.this._aEndp.getRemotePort();
        }

        public SSLEngine getSslEngine() {
            return SslConnection.this._engine;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public Object getTransport() {
            return ((AbstractConnection) SslConnection.this)._endp;
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public boolean hasProgressed() {
            return SslConnection.this._progressed.getAndSet(false);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isBlocking() {
            return false;
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public boolean isCheckForIdle() {
            return SslConnection.this._aEndp.isCheckForIdle();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isInputShutdown() {
            boolean z2;
            synchronized (SslConnection.this) {
                z2 = ((AbstractConnection) SslConnection.this)._endp.isInputShutdown() && (SslConnection.this._unwrapBuf == null || !SslConnection.this._unwrapBuf.hasContent()) && (SslConnection.this._inbound == null || !SslConnection.this._inbound.hasContent());
            }
            return z2;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isOpen() {
            return ((AbstractConnection) SslConnection.this)._endp.isOpen();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isOutputShutdown() {
            boolean z2;
            synchronized (SslConnection.this) {
                z2 = SslConnection.this._oshut || !isOpen() || SslConnection.this._engine.isOutboundDone();
            }
            return z2;
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public boolean isWritable() {
            return SslConnection.this._aEndp.isWritable();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void onIdleExpired(long j2) {
            SslConnection.this._aEndp.onIdleExpired(j2);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void scheduleTimeout(Timeout.Task task, long j2) {
            SslConnection.this._aEndp.scheduleTimeout(task, j2);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void scheduleWrite() {
            SslConnection.this._aEndp.scheduleWrite();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void setCheckForIdle(boolean z2) {
            SslConnection.this._aEndp.setCheckForIdle(z2);
        }

        @Override // org.eclipse.jetty.io.ConnectedEndPoint
        public void setConnection(Connection connection) {
            SslConnection.this._connection = (AsyncConnection) connection;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void setMaxIdleTime(int i2) throws IOException {
            SslConnection.this._aEndp.setMaxIdleTime(i2);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void shutdownInput() throws IOException {
            SslConnection.this._logger.debug("{} ssl endp.ishut!", SslConnection.this._session);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void shutdownOutput() throws IOException {
            synchronized (SslConnection.this) {
                SslConnection.this._logger.debug("{} ssl endp.oshut {}", SslConnection.this._session, this);
                SslConnection.this._engine.closeOutbound();
                SslConnection.this._oshut = true;
            }
            flush();
        }

        public String toString() {
            NIOBuffer nIOBuffer = SslConnection.this._inbound;
            NIOBuffer nIOBuffer2 = SslConnection.this._outbound;
            NIOBuffer nIOBuffer3 = SslConnection.this._unwrapBuf;
            return String.format("SSL %s i/o/u=%d/%d/%d ishut=%b oshut=%b {%s}", SslConnection.this._engine.getHandshakeStatus(), Integer.valueOf(nIOBuffer == null ? -1 : nIOBuffer.length()), Integer.valueOf(nIOBuffer2 == null ? -1 : nIOBuffer2.length()), Integer.valueOf(nIOBuffer3 != null ? nIOBuffer3.length() : -1), Boolean.valueOf(SslConnection.this._ishut), Boolean.valueOf(SslConnection.this._oshut), SslConnection.this._connection);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            if (buffer != null && buffer.hasContent()) {
                return flush(buffer);
            }
            if (buffer2 != null && buffer2.hasContent()) {
                return flush(buffer2);
            }
            if (buffer3 == null || !buffer3.hasContent()) {
                return 0;
            }
            return flush(buffer3);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void flush() throws IOException {
            SslConnection.this.process(null, null);
        }
    }
}
