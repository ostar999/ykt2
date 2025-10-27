package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.annotation.Immutable;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

@Immutable
/* loaded from: classes9.dex */
public class LoggingSessionOutputBuffer implements SessionOutputBuffer {
    private final String charset;
    private final SessionOutputBuffer out;
    private final Wire wire;

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire, String str) {
        this.out = sessionOutputBuffer;
        this.wire = wire;
        this.charset = str == null ? HTTP.ASCII : str;
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.out.getMetrics();
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.out.write(bArr, i2, i3);
        if (this.wire.enabled()) {
            this.wire.output(bArr, i2, i3);
        }
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public void writeLine(CharArrayBuffer charArrayBuffer) throws IOException {
        this.out.writeLine(charArrayBuffer);
        if (this.wire.enabled()) {
            this.wire.output((new String(charArrayBuffer.buffer(), 0, charArrayBuffer.length()) + "\r\n").getBytes(this.charset));
        }
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public void write(int i2) throws IOException {
        this.out.write(i2);
        if (this.wire.enabled()) {
            this.wire.output(i2);
        }
    }

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire) {
        this(sessionOutputBuffer, wire, null);
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public void writeLine(String str) throws IOException {
        this.out.writeLine(str);
        if (this.wire.enabled()) {
            this.wire.output((str + "\r\n").getBytes(this.charset));
        }
    }

    @Override // org.apache.http.io.SessionOutputBuffer
    public void write(byte[] bArr) throws IOException {
        this.out.write(bArr);
        if (this.wire.enabled()) {
            this.wire.output(bArr);
        }
    }
}
