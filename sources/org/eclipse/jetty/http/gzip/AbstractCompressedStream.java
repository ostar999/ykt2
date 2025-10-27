package org.eclipse.jetty.http.gzip;

import cn.hutool.core.text.CharPool;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

/* loaded from: classes9.dex */
public abstract class AbstractCompressedStream extends ServletOutputStream {
    protected ByteArrayOutputStream2 _bOut;
    protected boolean _closed;
    protected DeflaterOutputStream _compressedOutputStream;
    protected boolean _doNotCompress;
    private final String _encoding;
    protected OutputStream _out;
    protected final HttpServletResponse _response;
    protected final String _vary;
    protected final CompressedResponseWrapper _wrapper;

    public AbstractCompressedStream(String str, HttpServletRequest httpServletRequest, CompressedResponseWrapper compressedResponseWrapper, String str2) throws IOException {
        this._encoding = str;
        this._wrapper = compressedResponseWrapper;
        this._response = compressedResponseWrapper.getResponse();
        this._vary = str2;
        if (compressedResponseWrapper.getMinCompressSize() == 0) {
            doCompress();
        }
    }

    private void checkOut(int i2) throws IOException {
        if (this._closed) {
            throw new IOException("CLOSED");
        }
        if (this._out != null) {
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
            if (byteArrayOutputStream2 == null || i2 < byteArrayOutputStream2.getBuf().length - this._bOut.getCount()) {
                return;
            }
            long contentLength = this._wrapper.getContentLength();
            if (contentLength < 0 || contentLength >= this._wrapper.getMinCompressSize()) {
                doCompress();
                return;
            } else {
                doNotCompress(false);
                return;
            }
        }
        if (i2 <= this._wrapper.getBufferSize()) {
            ByteArrayOutputStream2 byteArrayOutputStream22 = new ByteArrayOutputStream2(this._wrapper.getBufferSize());
            this._bOut = byteArrayOutputStream22;
            this._out = byteArrayOutputStream22;
        } else {
            long contentLength2 = this._wrapper.getContentLength();
            if (contentLength2 < 0 || contentLength2 >= this._wrapper.getMinCompressSize()) {
                doCompress();
            } else {
                doNotCompress(false);
            }
        }
    }

    public void addHeader(String str, String str2) {
        this._response.addHeader(str, str2);
    }

    public void close() throws IOException {
        if (this._closed) {
            return;
        }
        if (this._wrapper.getRequest().getAttribute("javax.servlet.include.request_uri") != null) {
            flush();
            return;
        }
        if (this._bOut != null) {
            long contentLength = this._wrapper.getContentLength();
            if (contentLength < 0) {
                contentLength = this._bOut.getCount();
                this._wrapper.setContentLength(contentLength);
            }
            if (contentLength < this._wrapper.getMinCompressSize()) {
                doNotCompress(false);
            } else {
                doCompress();
            }
        } else if (this._out == null) {
            doNotCompress(false);
        }
        DeflaterOutputStream deflaterOutputStream = this._compressedOutputStream;
        if (deflaterOutputStream != null) {
            deflaterOutputStream.close();
        } else {
            this._out.close();
        }
        this._closed = true;
    }

    public abstract DeflaterOutputStream createStream() throws IOException;

    public void doCompress() throws IOException {
        if (this._compressedOutputStream == null) {
            if (this._response.isCommitted()) {
                throw new IllegalStateException();
            }
            String str = this._encoding;
            if (str != null) {
                setHeader("Content-Encoding", str);
                if (this._response.containsHeader("Content-Encoding")) {
                    addHeader("Vary", this._vary);
                    DeflaterOutputStream deflaterOutputStreamCreateStream = createStream();
                    this._compressedOutputStream = deflaterOutputStreamCreateStream;
                    this._out = deflaterOutputStreamCreateStream;
                    if (deflaterOutputStreamCreateStream != null) {
                        ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
                        if (byteArrayOutputStream2 != null) {
                            deflaterOutputStreamCreateStream.write(byteArrayOutputStream2.getBuf(), 0, this._bOut.getCount());
                            this._bOut = null;
                        }
                        String eTag = this._wrapper.getETag();
                        if (eTag != null) {
                            setHeader("ETag", eTag.substring(0, eTag.length() - 1) + CharPool.DASHED + this._encoding + '\"');
                            return;
                        }
                        return;
                    }
                }
            }
            doNotCompress(true);
        }
    }

    public void doNotCompress(boolean z2) throws IOException {
        if (this._compressedOutputStream != null) {
            throw new IllegalStateException("Compressed output stream is already assigned.");
        }
        if (this._out == null || this._bOut != null) {
            if (z2) {
                addHeader("Vary", this._vary);
            }
            if (this._wrapper.getETag() != null) {
                setHeader("ETag", this._wrapper.getETag());
            }
            this._doNotCompress = true;
            this._out = this._response.getOutputStream();
            setContentLength();
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
            if (byteArrayOutputStream2 != null) {
                this._out.write(byteArrayOutputStream2.getBuf(), 0, this._bOut.getCount());
            }
            this._bOut = null;
        }
    }

    public void finish() throws IOException {
        if (this._closed) {
            return;
        }
        if (this._out == null || this._bOut != null) {
            long contentLength = this._wrapper.getContentLength();
            if (contentLength < 0 || contentLength >= this._wrapper.getMinCompressSize()) {
                doCompress();
            } else {
                doNotCompress(false);
            }
        }
        DeflaterOutputStream deflaterOutputStream = this._compressedOutputStream;
        if (deflaterOutputStream == null || this._closed) {
            return;
        }
        this._closed = true;
        deflaterOutputStream.close();
    }

    public void flush() throws IOException {
        if (this._out == null || this._bOut != null) {
            long contentLength = this._wrapper.getContentLength();
            if (contentLength <= 0 || contentLength >= this._wrapper.getMinCompressSize()) {
                doCompress();
            } else {
                doNotCompress(false);
            }
        }
        this._out.flush();
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    public boolean isClosed() {
        return this._closed;
    }

    public PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }

    public void resetBuffer() {
        if (this._response.isCommitted() || this._compressedOutputStream != null) {
            throw new IllegalStateException("Committed");
        }
        this._closed = false;
        this._out = null;
        this._bOut = null;
        this._doNotCompress = false;
    }

    public void setBufferSize(int i2) throws IOException {
        ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
        if (byteArrayOutputStream2 == null || byteArrayOutputStream2.getBuf().length >= i2) {
            return;
        }
        ByteArrayOutputStream2 byteArrayOutputStream22 = new ByteArrayOutputStream2(i2);
        byteArrayOutputStream22.write(this._bOut.getBuf(), 0, this._bOut.size());
        this._bOut = byteArrayOutputStream22;
    }

    public void setContentLength() {
        if (this._doNotCompress) {
            long contentLength = this._wrapper.getContentLength();
            if (contentLength >= 0) {
                if (contentLength < 2147483647L) {
                    this._response.setContentLength((int) contentLength);
                } else {
                    this._response.setHeader("Content-Length", Long.toString(contentLength));
                }
            }
        }
    }

    public void setHeader(String str, String str2) {
        this._response.setHeader(str, str2);
    }

    public void write(int i2) throws IOException {
        checkOut(1);
        this._out.write(i2);
    }

    public void write(byte[] bArr) throws IOException {
        checkOut(bArr.length);
        this._out.write(bArr);
    }

    public void write(byte[] bArr, int i2, int i3) throws IOException {
        checkOut(i3);
        this._out.write(bArr, i2, i3);
    }
}
