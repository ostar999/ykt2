package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;

/* loaded from: classes9.dex */
public class ContentLengthInputStream extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private long contentLength;
    private SessionInputBuffer in;
    private long pos = 0;
    private boolean closed = false;

    public ContentLengthInputStream(SessionInputBuffer sessionInputBuffer, long j2) {
        this.in = null;
        if (sessionInputBuffer == null) {
            throw new IllegalArgumentException("Input stream may not be null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("Content length may not be negative");
        }
        this.in = sessionInputBuffer;
        this.contentLength = j2;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        SessionInputBuffer sessionInputBuffer = this.in;
        if (sessionInputBuffer instanceof BufferInfo) {
            return Math.min(((BufferInfo) sessionInputBuffer).length(), (int) (this.contentLength - this.pos));
        }
        return 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        try {
            if (this.pos < this.contentLength) {
                do {
                } while (read(new byte[2048]) >= 0);
            }
        } finally {
            this.closed = true;
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.pos >= this.contentLength) {
            return -1;
        }
        int i2 = this.in.read();
        if (i2 != -1) {
            this.pos++;
        } else if (this.pos < this.contentLength) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Premature end of Content-Length delimited message body (expected: ");
            stringBuffer.append(this.contentLength);
            stringBuffer.append("; received: ");
            stringBuffer.append(this.pos);
            throw new ConnectionClosedException(stringBuffer.toString());
        }
        return i2;
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        int i2;
        if (j2 <= 0) {
            return 0L;
        }
        byte[] bArr = new byte[2048];
        long jMin = Math.min(j2, this.contentLength - this.pos);
        long j3 = 0;
        while (jMin > 0 && (i2 = read(bArr, 0, (int) Math.min(2048L, jMin))) != -1) {
            long j4 = i2;
            j3 += j4;
            jMin -= j4;
        }
        return j3;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (!this.closed) {
            long j2 = this.pos;
            long j3 = this.contentLength;
            if (j2 >= j3) {
                return -1;
            }
            if (i3 + j2 > j3) {
                i3 = (int) (j3 - j2);
            }
            int i4 = this.in.read(bArr, i2, i3);
            if (i4 != -1 || this.pos >= this.contentLength) {
                if (i4 > 0) {
                    this.pos += i4;
                }
                return i4;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Premature end of Content-Length delimited message body (expected: ");
            stringBuffer.append(this.contentLength);
            stringBuffer.append("; received: ");
            stringBuffer.append(this.pos);
            throw new ConnectionClosedException(stringBuffer.toString());
        }
        throw new IOException("Attempted read from closed stream.");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }
}
