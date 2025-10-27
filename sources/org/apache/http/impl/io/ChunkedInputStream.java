package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.TruncatedChunkException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.ExceptionUtils;

/* loaded from: classes9.dex */
public class ChunkedInputStream extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private static final int CHUNK_CRLF = 3;
    private static final int CHUNK_DATA = 2;
    private static final int CHUNK_LEN = 1;
    private final CharArrayBuffer buffer;
    private int chunkSize;
    private final SessionInputBuffer in;
    private int pos;
    private int state;
    private boolean eof = false;
    private boolean closed = false;
    private Header[] footers = new Header[0];

    public ChunkedInputStream(SessionInputBuffer sessionInputBuffer) {
        if (sessionInputBuffer == null) {
            throw new IllegalArgumentException("Session input buffer may not be null");
        }
        this.in = sessionInputBuffer;
        this.pos = 0;
        this.buffer = new CharArrayBuffer(16);
        this.state = 1;
    }

    private int getChunkSize() throws IOException {
        int i2 = this.state;
        if (i2 != 1) {
            if (i2 != 3) {
                throw new IllegalStateException("Inconsistent codec state");
            }
            this.buffer.clear();
            if (this.in.readLine(this.buffer) == -1) {
                return 0;
            }
            if (!this.buffer.isEmpty()) {
                throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
            }
            this.state = 1;
        }
        this.buffer.clear();
        if (this.in.readLine(this.buffer) == -1) {
            return 0;
        }
        int iIndexOf = this.buffer.indexOf(59);
        if (iIndexOf < 0) {
            iIndexOf = this.buffer.length();
        }
        try {
            return Integer.parseInt(this.buffer.substringTrimmed(0, iIndexOf), 16);
        } catch (NumberFormatException unused) {
            throw new MalformedChunkCodingException("Bad chunk header");
        }
    }

    private void nextChunk() throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        int chunkSize = getChunkSize();
        this.chunkSize = chunkSize;
        if (chunkSize < 0) {
            throw new MalformedChunkCodingException("Negative chunk size");
        }
        this.state = 2;
        this.pos = 0;
        if (chunkSize == 0) {
            this.eof = true;
            parseTrailerHeaders();
        }
    }

    private void parseTrailerHeaders() throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        try {
            this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, null);
        } catch (HttpException e2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid footer: ");
            stringBuffer.append(e2.getMessage());
            MalformedChunkCodingException malformedChunkCodingException = new MalformedChunkCodingException(stringBuffer.toString());
            ExceptionUtils.initCause(malformedChunkCodingException, e2);
            throw malformedChunkCodingException;
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        SessionInputBuffer sessionInputBuffer = this.in;
        if (sessionInputBuffer instanceof BufferInfo) {
            return Math.min(((BufferInfo) sessionInputBuffer).length(), this.chunkSize - this.pos);
        }
        return 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        try {
            if (!this.eof) {
                do {
                } while (read(new byte[2048]) >= 0);
            }
        } finally {
            this.eof = true;
            this.closed = true;
        }
    }

    public Header[] getFooters() {
        return (Header[]) this.footers.clone();
    }

    @Override // java.io.InputStream
    public int read() throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.eof) {
            return -1;
        }
        if (this.state != 2) {
            nextChunk();
            if (this.eof) {
                return -1;
            }
        }
        int i2 = this.in.read();
        if (i2 != -1) {
            int i3 = this.pos + 1;
            this.pos = i3;
            if (i3 >= this.chunkSize) {
                this.state = 3;
            }
        }
        return i2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        if (!this.closed) {
            if (this.eof) {
                return -1;
            }
            if (this.state != 2) {
                nextChunk();
                if (this.eof) {
                    return -1;
                }
            }
            int i4 = this.in.read(bArr, i2, Math.min(i3, this.chunkSize - this.pos));
            if (i4 != -1) {
                int i5 = this.pos + i4;
                this.pos = i5;
                if (i5 >= this.chunkSize) {
                    this.state = 3;
                }
                return i4;
            }
            this.eof = true;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Truncated chunk ( expected size: ");
            stringBuffer.append(this.chunkSize);
            stringBuffer.append("; actual size: ");
            stringBuffer.append(this.pos);
            stringBuffer.append(")");
            throw new TruncatedChunkException(stringBuffer.toString());
        }
        throw new IOException("Attempted read from closed stream.");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }
}
