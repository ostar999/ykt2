package org.eclipse.jetty.io;

import java.io.IOException;

/* loaded from: classes9.dex */
public class ByteArrayEndPoint implements ConnectedEndPoint {
    protected boolean _closed;
    protected Connection _connection;
    protected boolean _growOutput;
    protected ByteArrayBuffer _in;
    protected byte[] _inBytes;
    protected int _maxIdleTime;
    protected boolean _nonBlocking;
    protected ByteArrayBuffer _out;

    public ByteArrayEndPoint() {
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockReadable(long j2) {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean blockWritable(long j2) {
        return true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void close() throws IOException {
        this._closed = true;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int fill(Buffer buffer) throws IOException {
        if (this._closed) {
            throw new IOException("CLOSED");
        }
        ByteArrayBuffer byteArrayBuffer = this._in;
        if (byteArrayBuffer != null && byteArrayBuffer.length() > 0) {
            int iPut = buffer.put(this._in);
            this._in.skip(iPut);
            return iPut;
        }
        ByteArrayBuffer byteArrayBuffer2 = this._in;
        if (byteArrayBuffer2 != null && byteArrayBuffer2.length() == 0 && this._nonBlocking) {
            return 0;
        }
        close();
        return -1;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer) throws IOException {
        if (this._closed) {
            throw new IOException("CLOSED");
        }
        if (this._growOutput && buffer.length() > this._out.space()) {
            this._out.compact();
            if (buffer.length() > this._out.space()) {
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(this._out.putIndex() + buffer.length());
                ByteArrayBuffer byteArrayBuffer2 = this._out;
                byteArrayBuffer.put(byteArrayBuffer2.peek(0, byteArrayBuffer2.putIndex()));
                if (this._out.getIndex() > 0) {
                    byteArrayBuffer.mark();
                    byteArrayBuffer.setGetIndex(this._out.getIndex());
                }
                this._out = byteArrayBuffer;
            }
        }
        int iPut = this._out.put(buffer);
        if (!buffer.isImmutable()) {
            buffer.skip(iPut);
        }
        return iPut;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void flush() throws IOException {
    }

    @Override // org.eclipse.jetty.io.ConnectedEndPoint
    public Connection getConnection() {
        return this._connection;
    }

    public ByteArrayBuffer getIn() {
        return this._in;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalAddr() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getLocalHost() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getLocalPort() {
        return 0;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    public ByteArrayBuffer getOut() {
        return this._out;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteAddr() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public String getRemoteHost() {
        return null;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int getRemotePort() {
        return 0;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public Object getTransport() {
        return this._inBytes;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isBlocking() {
        return !this._nonBlocking;
    }

    public boolean isGrowOutput() {
        return this._growOutput;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isInputShutdown() {
        return this._closed;
    }

    public boolean isNonBlocking() {
        return this._nonBlocking;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOpen() {
        return !this._closed;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public boolean isOutputShutdown() {
        return this._closed;
    }

    public void reset() {
        this._closed = false;
        this._in.clear();
        this._out.clear();
        byte[] bArr = this._inBytes;
        if (bArr != null) {
            this._in.setPutIndex(bArr.length);
        }
    }

    @Override // org.eclipse.jetty.io.ConnectedEndPoint
    public void setConnection(Connection connection) {
        this._connection = connection;
    }

    public void setGrowOutput(boolean z2) {
        this._growOutput = z2;
    }

    public void setIn(ByteArrayBuffer byteArrayBuffer) {
        this._in = byteArrayBuffer;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void setMaxIdleTime(int i2) throws IOException {
        this._maxIdleTime = i2;
    }

    public void setNonBlocking(boolean z2) {
        this._nonBlocking = z2;
    }

    public void setOut(ByteArrayBuffer byteArrayBuffer) {
        this._out = byteArrayBuffer;
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownInput() throws IOException {
        close();
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public void shutdownOutput() throws IOException {
        close();
    }

    public ByteArrayEndPoint(byte[] bArr, int i2) {
        this._inBytes = bArr;
        this._in = new ByteArrayBuffer(bArr);
        this._out = new ByteArrayBuffer(i2);
    }

    @Override // org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        if (!this._closed) {
            int iFlush = (buffer == null || buffer.length() <= 0) ? 0 : flush(buffer);
            if (buffer != null && buffer.length() != 0) {
                return iFlush;
            }
            if (buffer2 != null && buffer2.length() > 0) {
                iFlush += flush(buffer2);
            }
            return ((buffer2 == null || buffer2.length() == 0) && buffer3 != null && buffer3.length() > 0) ? iFlush + flush(buffer3) : iFlush;
        }
        throw new IOException("CLOSED");
    }
}
