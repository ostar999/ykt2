package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public abstract class AbstractSessionInputBuffer implements SessionInputBuffer, BufferInfo {
    private byte[] buffer;
    private int bufferlen;
    private int bufferpos;
    private InputStream instream;
    private HttpTransportMetricsImpl metrics;
    private ByteArrayBuffer linebuffer = null;
    private String charset = "US-ASCII";
    private boolean ascii = true;
    private int maxLineLen = -1;
    private int minChunkLimit = 512;

    private int lineFromLineBuffer(CharArrayBuffer charArrayBuffer) throws IOException {
        int length = this.linebuffer.length();
        if (length > 0) {
            if (this.linebuffer.byteAt(length - 1) == 10) {
                length--;
                this.linebuffer.setLength(length);
            }
            if (length > 0 && this.linebuffer.byteAt(length - 1) == 13) {
                this.linebuffer.setLength(length - 1);
            }
        }
        int length2 = this.linebuffer.length();
        if (this.ascii) {
            charArrayBuffer.append(this.linebuffer, 0, length2);
        } else {
            String str = new String(this.linebuffer.buffer(), 0, length2, this.charset);
            length2 = str.length();
            charArrayBuffer.append(str);
        }
        this.linebuffer.clear();
        return length2;
    }

    private int lineFromReadBuffer(CharArrayBuffer charArrayBuffer, int i2) throws IOException {
        int i3 = this.bufferpos;
        this.bufferpos = i2 + 1;
        if (i2 > 0 && this.buffer[i2 - 1] == 13) {
            i2--;
        }
        int i4 = i2 - i3;
        if (this.ascii) {
            charArrayBuffer.append(this.buffer, i3, i4);
            return i4;
        }
        String str = new String(this.buffer, i3, i4, this.charset);
        charArrayBuffer.append(str);
        return str.length();
    }

    private int locateLF() {
        for (int i2 = this.bufferpos; i2 < this.bufferlen; i2++) {
            if (this.buffer[i2] == 10) {
                return i2;
            }
        }
        return -1;
    }

    @Override // org.apache.http.io.BufferInfo
    public int available() {
        return capacity() - length();
    }

    @Override // org.apache.http.io.BufferInfo
    public int capacity() {
        return this.buffer.length;
    }

    public HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
    }

    public int fillBuffer() throws IOException {
        int i2 = this.bufferpos;
        if (i2 > 0) {
            int i3 = this.bufferlen - i2;
            if (i3 > 0) {
                byte[] bArr = this.buffer;
                System.arraycopy(bArr, i2, bArr, 0, i3);
            }
            this.bufferpos = 0;
            this.bufferlen = i3;
        }
        int i4 = this.bufferlen;
        byte[] bArr2 = this.buffer;
        int i5 = this.instream.read(bArr2, i4, bArr2.length - i4);
        if (i5 == -1) {
            return -1;
        }
        this.bufferlen = i4 + i5;
        this.metrics.incrementBytesTransferred(i5);
        return i5;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }

    public boolean hasBufferedData() {
        return this.bufferpos < this.bufferlen;
    }

    public void init(InputStream inputStream, int i2, HttpParams httpParams) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream may not be null");
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("Buffer size may not be negative or zero");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        this.instream = inputStream;
        this.buffer = new byte[i2];
        this.bufferpos = 0;
        this.bufferlen = 0;
        this.linebuffer = new ByteArrayBuffer(i2);
        String httpElementCharset = HttpProtocolParams.getHttpElementCharset(httpParams);
        this.charset = httpElementCharset;
        this.ascii = httpElementCharset.equalsIgnoreCase("US-ASCII") || this.charset.equalsIgnoreCase(HTTP.ASCII);
        this.maxLineLen = httpParams.getIntParameter(CoreConnectionPNames.MAX_LINE_LENGTH, -1);
        this.minChunkLimit = httpParams.getIntParameter(CoreConnectionPNames.MIN_CHUNK_LIMIT, 512);
        this.metrics = createTransportMetrics();
    }

    @Override // org.apache.http.io.BufferInfo
    public int length() {
        return this.bufferlen - this.bufferpos;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int read() throws IOException {
        while (!hasBufferedData()) {
            if (fillBuffer() == -1) {
                return -1;
            }
        }
        byte[] bArr = this.buffer;
        int i2 = this.bufferpos;
        this.bufferpos = i2 + 1;
        return bArr[i2] & 255;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) {
            throw new IllegalArgumentException("Char array buffer may not be null");
        }
        boolean z2 = true;
        int iFillBuffer = 0;
        while (z2) {
            int iLocateLF = locateLF();
            if (iLocateLF == -1) {
                if (hasBufferedData()) {
                    int i2 = this.bufferlen;
                    int i3 = this.bufferpos;
                    this.linebuffer.append(this.buffer, i3, i2 - i3);
                    this.bufferpos = this.bufferlen;
                }
                iFillBuffer = fillBuffer();
                if (iFillBuffer == -1) {
                }
                if (this.maxLineLen <= 0 && this.linebuffer.length() >= this.maxLineLen) {
                    throw new IOException("Maximum line length limit exceeded");
                }
            } else {
                if (this.linebuffer.isEmpty()) {
                    return lineFromReadBuffer(charArrayBuffer, iLocateLF);
                }
                int i4 = iLocateLF + 1;
                int i5 = this.bufferpos;
                this.linebuffer.append(this.buffer, i5, i4 - i5);
                this.bufferpos = i4;
            }
            z2 = false;
            if (this.maxLineLen <= 0) {
            }
        }
        if (iFillBuffer == -1 && this.linebuffer.isEmpty()) {
            return -1;
        }
        return lineFromLineBuffer(charArrayBuffer);
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (bArr == null) {
            return 0;
        }
        if (hasBufferedData()) {
            int iMin = Math.min(i3, this.bufferlen - this.bufferpos);
            System.arraycopy(this.buffer, this.bufferpos, bArr, i2, iMin);
            this.bufferpos += iMin;
            return iMin;
        }
        if (i3 > this.minChunkLimit) {
            int i4 = this.instream.read(bArr, i2, i3);
            if (i4 > 0) {
                this.metrics.incrementBytesTransferred(i4);
            }
            return i4;
        }
        while (!hasBufferedData()) {
            if (fillBuffer() == -1) {
                return -1;
            }
        }
        int iMin2 = Math.min(i3, this.bufferlen - this.bufferpos);
        System.arraycopy(this.buffer, this.bufferpos, bArr, i2, iMin2);
        this.bufferpos += iMin2;
        return iMin2;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public int read(byte[] bArr) throws IOException {
        if (bArr == null) {
            return 0;
        }
        return read(bArr, 0, bArr.length);
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public String readLine() throws IOException {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        if (readLine(charArrayBuffer) != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }
}
