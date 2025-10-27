package org.eclipse.jetty.io.nio;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class ChannelEndPoint implements EndPoint {
    private static final Logger LOG = Log.getLogger((Class<?>) ChannelEndPoint.class);
    protected final ByteChannel _channel;
    protected final ByteBuffer[] _gather2;
    private volatile boolean _ishut;
    protected final InetSocketAddress _local;
    protected volatile int _maxIdleTime;
    private volatile boolean _oshut;
    protected final InetSocketAddress _remote;
    protected final Socket _socket;

    public ChannelEndPoint(ByteChannel byteChannel) throws IOException {
        this._gather2 = new ByteBuffer[2];
        this._channel = byteChannel;
        Socket socket = byteChannel instanceof SocketChannel ? ((SocketChannel) byteChannel).socket() : null;
        this._socket = socket;
        if (socket == null) {
            this._remote = null;
            this._local = null;
        } else {
            this._local = (InetSocketAddress) socket.getLocalSocketAddress();
            this._remote = (InetSocketAddress) socket.getRemoteSocketAddress();
            this._maxIdleTime = socket.getSoTimeout();
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockReadable(long j2) throws IOException {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockWritable(long j2) throws IOException {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void close() throws IOException {
        LOG.debug("close {}", this);
        this._channel.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
    
        if (r3 >= 0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0034, code lost:
    
        if (isOpen() == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        if (isInputShutdown() != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003c, code lost:
    
        shutdownInput();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0043, code lost:
    
        if (isOutputShutdown() == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0045, code lost:
    
        r5._channel.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004b, code lost:
    
        r6 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004c, code lost:
    
        r2 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0063, code lost:
    
        org.eclipse.jetty.io.nio.ChannelEndPoint.LOG.debug("Exception while filling", r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0070, code lost:
    
        if (r5._channel.isOpen() != false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0072, code lost:
    
        r5._channel.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0078, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0079, code lost:
    
        org.eclipse.jetty.io.nio.ChannelEndPoint.LOG.ignore(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007e, code lost:
    
        if (r2 <= 0) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0080, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0081, code lost:
    
        throw r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
    
        return -1;
     */
    @Override // org.eclipse.jetty.io.EndPoint
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int fill(org.eclipse.jetty.io.Buffer r6) throws java.lang.Throwable {
        /*
            r5 = this;
            boolean r0 = r5._ishut
            r1 = -1
            if (r0 == 0) goto L6
            return r1
        L6:
            org.eclipse.jetty.io.Buffer r0 = r6.buffer()
            boolean r2 = r0 instanceof org.eclipse.jetty.io.nio.NIOBuffer
            if (r2 == 0) goto L82
            org.eclipse.jetty.io.nio.NIOBuffer r0 = (org.eclipse.jetty.io.nio.NIOBuffer) r0
            java.nio.ByteBuffer r0 = r0.getByteBuffer()
            r2 = 0
            monitor-enter(r0)     // Catch: java.io.IOException -> L62
            int r3 = r6.putIndex()     // Catch: java.lang.Throwable -> L53
            r0.position(r3)     // Catch: java.lang.Throwable -> L53
            java.nio.channels.ByteChannel r3 = r5._channel     // Catch: java.lang.Throwable -> L53
            int r3 = r3.read(r0)     // Catch: java.lang.Throwable -> L53
            int r4 = r0.position()     // Catch: java.lang.Throwable -> L50
            r6.setPutIndex(r4)     // Catch: java.lang.Throwable -> L50
            r0.position(r2)     // Catch: java.lang.Throwable -> L50
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L50
            if (r3 >= 0) goto L4e
            boolean r6 = r5.isOpen()     // Catch: java.io.IOException -> L4b
            if (r6 == 0) goto L4e
            boolean r6 = r5.isInputShutdown()     // Catch: java.io.IOException -> L4b
            if (r6 != 0) goto L3f
            r5.shutdownInput()     // Catch: java.io.IOException -> L4b
        L3f:
            boolean r6 = r5.isOutputShutdown()     // Catch: java.io.IOException -> L4b
            if (r6 == 0) goto L4e
            java.nio.channels.ByteChannel r6 = r5._channel     // Catch: java.io.IOException -> L4b
            r6.close()     // Catch: java.io.IOException -> L4b
            goto L4e
        L4b:
            r6 = move-exception
            r2 = r3
            goto L63
        L4e:
            r1 = r3
            goto L80
        L50:
            r6 = move-exception
            r2 = r3
            goto L60
        L53:
            r3 = move-exception
            int r4 = r0.position()     // Catch: java.lang.Throwable -> L5f
            r6.setPutIndex(r4)     // Catch: java.lang.Throwable -> L5f
            r0.position(r2)     // Catch: java.lang.Throwable -> L5f
            throw r3     // Catch: java.lang.Throwable -> L5f
        L5f:
            r6 = move-exception
        L60:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5f
            throw r6     // Catch: java.io.IOException -> L62
        L62:
            r6 = move-exception
        L63:
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.io.nio.ChannelEndPoint.LOG
            java.lang.String r3 = "Exception while filling"
            r0.debug(r3, r6)
            java.nio.channels.ByteChannel r0 = r5._channel     // Catch: java.lang.Exception -> L78
            boolean r0 = r0.isOpen()     // Catch: java.lang.Exception -> L78
            if (r0 == 0) goto L7e
            java.nio.channels.ByteChannel r0 = r5._channel     // Catch: java.lang.Exception -> L78
            r0.close()     // Catch: java.lang.Exception -> L78
            goto L7e
        L78:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.io.nio.ChannelEndPoint.LOG
            r3.ignore(r0)
        L7e:
            if (r2 > 0) goto L81
        L80:
            return r1
        L81:
            throw r6
        L82:
            java.io.IOException r6 = new java.io.IOException
            java.lang.String r0 = "Not Implemented"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.ChannelEndPoint.fill(org.eclipse.jetty.io.Buffer):int");
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer) throws IOException {
        int iWrite;
        Buffer buffer2 = buffer.buffer();
        if (buffer2 instanceof NIOBuffer) {
            ByteBuffer byteBufferAsReadOnlyBuffer = ((NIOBuffer) buffer2).getByteBuffer().asReadOnlyBuffer();
            byteBufferAsReadOnlyBuffer.position(buffer.getIndex());
            byteBufferAsReadOnlyBuffer.limit(buffer.putIndex());
            iWrite = this._channel.write(byteBufferAsReadOnlyBuffer);
            if (iWrite > 0) {
                buffer.skip(iWrite);
            }
        } else if (buffer2 instanceof RandomAccessFileBuffer) {
            iWrite = ((RandomAccessFileBuffer) buffer2).writeTo(this._channel, buffer.getIndex(), buffer.length());
            if (iWrite > 0) {
                buffer.skip(iWrite);
            }
        } else {
            if (buffer.array() == null) {
                throw new IOException("Not Implemented");
            }
            iWrite = this._channel.write(ByteBuffer.wrap(buffer.array(), buffer.getIndex(), buffer.length()));
            if (iWrite > 0) {
                buffer.skip(iWrite);
            }
        }
        return iWrite;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void flush() throws IOException {
    }

    public int gatheringFlush(Buffer buffer, ByteBuffer byteBuffer, Buffer buffer2, ByteBuffer byteBuffer2) throws IOException {
        int iWrite;
        synchronized (this) {
            ByteBuffer byteBufferAsReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
            byteBufferAsReadOnlyBuffer.position(buffer.getIndex());
            byteBufferAsReadOnlyBuffer.limit(buffer.putIndex());
            ByteBuffer byteBufferAsReadOnlyBuffer2 = byteBuffer2.asReadOnlyBuffer();
            byteBufferAsReadOnlyBuffer2.position(buffer2.getIndex());
            byteBufferAsReadOnlyBuffer2.limit(buffer2.putIndex());
            ByteBuffer[] byteBufferArr = this._gather2;
            byteBufferArr[0] = byteBufferAsReadOnlyBuffer;
            byteBufferArr[1] = byteBufferAsReadOnlyBuffer2;
            iWrite = (int) ((GatheringByteChannel) this._channel).write(byteBufferArr);
            int length = buffer.length();
            if (iWrite > length) {
                buffer.clear();
                buffer2.skip(iWrite - length);
            } else if (iWrite > 0) {
                buffer.skip(iWrite);
            }
        }
        return iWrite;
    }

    public ByteChannel getChannel() {
        return this._channel;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalAddr() {
        if (this._socket == null) {
            return null;
        }
        InetSocketAddress inetSocketAddress = this._local;
        return (inetSocketAddress == null || inetSocketAddress.getAddress() == null || this._local.getAddress().isAnyLocalAddress()) ? StringUtil.ALL_INTERFACES : this._local.getAddress().getHostAddress();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalHost() {
        if (this._socket == null) {
            return null;
        }
        InetSocketAddress inetSocketAddress = this._local;
        return (inetSocketAddress == null || inetSocketAddress.getAddress() == null || this._local.getAddress().isAnyLocalAddress()) ? StringUtil.ALL_INTERFACES : this._local.getAddress().getCanonicalHostName();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getLocalPort() {
        if (this._socket == null) {
            return 0;
        }
        InetSocketAddress inetSocketAddress = this._local;
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteAddr() {
        InetSocketAddress inetSocketAddress;
        if (this._socket == null || (inetSocketAddress = this._remote) == null) {
            return null;
        }
        return inetSocketAddress.getAddress().getHostAddress();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteHost() {
        InetSocketAddress inetSocketAddress;
        if (this._socket == null || (inetSocketAddress = this._remote) == null) {
            return null;
        }
        return inetSocketAddress.getAddress().getCanonicalHostName();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getRemotePort() {
        if (this._socket == null) {
            return 0;
        }
        InetSocketAddress inetSocketAddress = this._remote;
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public Object getTransport() {
        return this._channel;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isBlocking() {
        Closeable closeable = this._channel;
        return !(closeable instanceof SelectableChannel) || ((SelectableChannel) closeable).isBlocking();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isInputShutdown() {
        Socket socket;
        return this._ishut || !this._channel.isOpen() || ((socket = this._socket) != null && socket.isInputShutdown());
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOpen() {
        return this._channel.isOpen();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOutputShutdown() {
        Socket socket;
        return this._oshut || !this._channel.isOpen() || ((socket = this._socket) != null && socket.isOutputShutdown());
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void setMaxIdleTime(int i2) throws IOException {
        if (this._socket != null && i2 != this._maxIdleTime) {
            this._socket.setSoTimeout(i2 > 0 ? i2 : 0);
        }
        this._maxIdleTime = i2;
    }

    public final void shutdownChannelInput() throws IOException {
        Socket socket;
        LOG.debug("ishut {}", this);
        this._ishut = true;
        if (!this._channel.isOpen() || (socket = this._socket) == null) {
            return;
        }
        try {
            try {
                if (!socket.isInputShutdown()) {
                    this._socket.shutdownInput();
                }
                if (!this._oshut) {
                    return;
                }
            } catch (SocketException e2) {
                Logger logger = LOG;
                logger.debug(e2.toString(), new Object[0]);
                logger.ignore(e2);
                if (!this._oshut) {
                    return;
                }
            }
            close();
        } catch (Throwable th) {
            if (this._oshut) {
                close();
            }
            throw th;
        }
    }

    public final void shutdownChannelOutput() throws IOException {
        Socket socket;
        LOG.debug("oshut {}", this);
        this._oshut = true;
        if (!this._channel.isOpen() || (socket = this._socket) == null) {
            return;
        }
        try {
            try {
                if (!socket.isOutputShutdown()) {
                    this._socket.shutdownOutput();
                }
                if (!this._ishut) {
                    return;
                }
            } catch (SocketException e2) {
                Logger logger = LOG;
                logger.debug(e2.toString(), new Object[0]);
                logger.ignore(e2);
                if (!this._ishut) {
                    return;
                }
            }
            close();
        } catch (Throwable th) {
            if (this._ishut) {
                close();
            }
            throw th;
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownInput() throws IOException {
        shutdownChannelInput();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownOutput() throws IOException {
        shutdownChannelOutput();
    }

    public ChannelEndPoint(ByteChannel byteChannel, int i2) throws IOException {
        this._gather2 = new ByteBuffer[2];
        this._channel = byteChannel;
        this._maxIdleTime = i2;
        Socket socket = byteChannel instanceof SocketChannel ? ((SocketChannel) byteChannel).socket() : null;
        this._socket = socket;
        if (socket != null) {
            this._local = (InetSocketAddress) socket.getLocalSocketAddress();
            this._remote = (InetSocketAddress) socket.getRemoteSocketAddress();
            socket.setSoTimeout(this._maxIdleTime);
        } else {
            this._remote = null;
            this._local = null;
        }
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        Buffer buffer4 = buffer == null ? null : buffer.buffer();
        Buffer buffer5 = buffer2 != null ? buffer2.buffer() : null;
        if ((this._channel instanceof GatheringByteChannel) && buffer != null && buffer.length() != 0 && (buffer4 instanceof NIOBuffer) && buffer2 != null && buffer2.length() != 0 && (buffer5 instanceof NIOBuffer)) {
            return gatheringFlush(buffer, ((NIOBuffer) buffer4).getByteBuffer(), buffer2, ((NIOBuffer) buffer5).getByteBuffer());
        }
        int iFlush = (buffer == null || buffer.length() <= 0) ? 0 : flush(buffer);
        if ((buffer == null || buffer.length() == 0) && buffer2 != null && buffer2.length() > 0) {
            iFlush += flush(buffer2);
        }
        return ((buffer == null || buffer.length() == 0) && (buffer2 == null || buffer2.length() == 0) && buffer3 != null && buffer3.length() > 0) ? flush(buffer3) + iFlush : iFlush;
    }
}
