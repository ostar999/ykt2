package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.annotation.Immutable;
import org.apache.http.io.EofSensor;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

@Immutable
/* loaded from: classes9.dex */
public class LoggingSessionInputBuffer implements SessionInputBuffer, EofSensor {
    private final String charset;
    private final EofSensor eofSensor;
    private final SessionInputBuffer in;
    private final Wire wire;

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire, String str) {
        this.in = sessionInputBuffer;
        this.eofSensor = sessionInputBuffer instanceof EofSensor ? (EofSensor) sessionInputBuffer : null;
        this.wire = wire;
        this.charset = str == null ? HTTP.ASCII : str;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.in.getMetrics();
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public boolean isDataAvailable(int i2) throws IOException {
        return this.in.isDataAvailable(i2);
    }

    @Override // org.apache.http.io.EofSensor
    public boolean isEof() {
        EofSensor eofSensor = this.eofSensor;
        if (eofSensor != null) {
            return eofSensor.isEof();
        }
        return false;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.in.read(bArr, i2, i3);
        if (this.wire.enabled() && i4 > 0) {
            this.wire.input(bArr, i2, i4);
        }
        return i4;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public String readLine() throws IOException {
        String line = this.in.readLine();
        if (this.wire.enabled() && line != null) {
            this.wire.input((line + "\r\n").getBytes(this.charset));
        }
        return line;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int read() throws IOException {
        int i2 = this.in.read();
        if (this.wire.enabled() && i2 != -1) {
            this.wire.input(i2);
        }
        return i2;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        int line = this.in.readLine(charArrayBuffer);
        if (this.wire.enabled() && line >= 0) {
            this.wire.input((new String(charArrayBuffer.buffer(), charArrayBuffer.length() - line, line) + "\r\n").getBytes(this.charset));
        }
        return line;
    }

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire) {
        this(sessionInputBuffer, wire, null);
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int read(byte[] bArr) throws IOException {
        int i2 = this.in.read(bArr);
        if (this.wire.enabled() && i2 > 0) {
            this.wire.input(bArr, 0, i2);
        }
        return i2;
    }
}
