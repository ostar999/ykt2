package org.eclipse.jetty.http.gzip;

import com.alipay.sdk.packet.d;
import com.alipay.sdk.util.h;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes9.dex */
public abstract class CompressedResponseWrapper extends HttpServletResponseWrapper {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int DEFAULT_MIN_COMPRESS_SIZE = 256;
    private int _bufferSize;
    private AbstractCompressedStream _compressedStream;
    private long _contentLength;
    private String _etag;
    private Set<String> _mimeTypes;
    private int _minCompressSize;
    private boolean _noCompression;
    protected HttpServletRequest _request;
    private PrintWriter _writer;

    public CompressedResponseWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        this._bufferSize = 8192;
        this._minCompressSize = 256;
        this._contentLength = -1L;
        this._request = httpServletRequest;
    }

    private void setDeferredHeaders() {
        if (isCommitted()) {
            return;
        }
        long j2 = this._contentLength;
        if (j2 >= 0) {
            if (j2 < 2147483647L) {
                super.setContentLength((int) j2);
            } else {
                super.setHeader("Content-Length", Long.toString(j2));
            }
        }
        String str = this._etag;
        if (str != null) {
            super.setHeader("ETag", str);
        }
    }

    public void addHeader(String str, String str2) {
        if ("content-length".equalsIgnoreCase(str)) {
            this._contentLength = Long.parseLong(str2);
            AbstractCompressedStream abstractCompressedStream = this._compressedStream;
            if (abstractCompressedStream != null) {
                abstractCompressedStream.setContentLength();
                return;
            }
            return;
        }
        if (d.f3288d.equalsIgnoreCase(str)) {
            setContentType(str2);
            return;
        }
        if ("content-encoding".equalsIgnoreCase(str)) {
            super.addHeader(str, str2);
            if (isCommitted()) {
                return;
            }
            noCompression();
            return;
        }
        if ("etag".equalsIgnoreCase(str)) {
            this._etag = str2;
        } else {
            super.addHeader(str, str2);
        }
    }

    public boolean containsHeader(String str) {
        if (this._noCompression || !"etag".equalsIgnoreCase(str) || this._etag == null) {
            return super.containsHeader(str);
        }
        return true;
    }

    public void finish() throws IOException {
        if (this._writer != null && !this._compressedStream.isClosed()) {
            this._writer.flush();
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.finish();
        } else {
            setDeferredHeaders();
        }
    }

    public void flushBuffer() throws IOException {
        PrintWriter printWriter = this._writer;
        if (printWriter != null) {
            printWriter.flush();
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.flush();
        } else {
            getResponse().flushBuffer();
        }
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public long getContentLength() {
        return this._contentLength;
    }

    public String getETag() {
        return this._etag;
    }

    public int getMinCompressSize() {
        return this._minCompressSize;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this._compressedStream == null) {
            if (getResponse().isCommitted() || this._noCompression) {
                return getResponse().getOutputStream();
            }
            this._compressedStream = newCompressedStream(this._request, (HttpServletResponse) getResponse());
        } else if (this._writer != null) {
            throw new IllegalStateException("getWriter() called");
        }
        return this._compressedStream;
    }

    public HttpServletRequest getRequest() {
        return this._request;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.OutputStream, org.eclipse.jetty.http.gzip.AbstractCompressedStream] */
    public PrintWriter getWriter() throws IOException {
        if (this._writer == null) {
            if (this._compressedStream != null) {
                throw new IllegalStateException("getOutputStream() called");
            }
            if (getResponse().isCommitted() || this._noCompression) {
                return getResponse().getWriter();
            }
            ?? NewCompressedStream = newCompressedStream(this._request, getResponse());
            this._compressedStream = NewCompressedStream;
            this._writer = newWriter(NewCompressedStream, getCharacterEncoding());
        }
        return this._writer;
    }

    public abstract AbstractCompressedStream newCompressedStream(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;

    public PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }

    public void noCompression() {
        if (!this._noCompression) {
            setDeferredHeaders();
        }
        this._noCompression = true;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            try {
                abstractCompressedStream.doNotCompress(false);
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    public void reset() {
        super.reset();
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.resetBuffer();
        }
        this._writer = null;
        this._compressedStream = null;
        this._noCompression = false;
        this._contentLength = -1L;
    }

    public void resetBuffer() {
        super.resetBuffer();
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.resetBuffer();
        }
        this._writer = null;
        this._compressedStream = null;
    }

    public void sendError(int i2, String str) throws IOException {
        resetBuffer();
        super.sendError(i2, str);
    }

    public void sendRedirect(String str) throws IOException {
        resetBuffer();
        super.sendRedirect(str);
    }

    public void setBufferSize(int i2) throws IOException {
        this._bufferSize = i2;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.setBufferSize(i2);
        }
    }

    public void setContentLength(int i2) {
        if (this._noCompression) {
            super.setContentLength(i2);
        } else {
            setContentLength(i2);
        }
    }

    public void setContentType(String str) {
        int iIndexOf;
        super.setContentType(str);
        if (this._noCompression) {
            return;
        }
        if (str != null && (iIndexOf = str.indexOf(h.f3376b)) > 0) {
            str = str.substring(0, iIndexOf);
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream == null || abstractCompressedStream.getOutputStream() == null) {
            if (this._mimeTypes != null || str == null || !str.contains(HttpHeaderValues.GZIP)) {
                Set<String> set = this._mimeTypes;
                if (set == null) {
                    return;
                }
                if (str != null && set.contains(StringUtil.asciiToLowerCase(str))) {
                    return;
                }
            }
            noCompression();
        }
    }

    public void setHeader(String str, String str2) {
        if (this._noCompression) {
            super.setHeader(str, str2);
            return;
        }
        if ("content-length".equalsIgnoreCase(str)) {
            setContentLength(Long.parseLong(str2));
            return;
        }
        if (d.f3288d.equalsIgnoreCase(str)) {
            setContentType(str2);
            return;
        }
        if ("content-encoding".equalsIgnoreCase(str)) {
            super.setHeader(str, str2);
            if (isCommitted()) {
                return;
            }
            noCompression();
            return;
        }
        if ("etag".equalsIgnoreCase(str)) {
            this._etag = str2;
        } else {
            super.setHeader(str, str2);
        }
    }

    public void setIntHeader(String str, int i2) {
        if (!"content-length".equalsIgnoreCase(str)) {
            super.setIntHeader(str, i2);
            return;
        }
        this._contentLength = i2;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.setContentLength();
        }
    }

    public void setMimeTypes(Set<String> set) {
        this._mimeTypes = set;
    }

    public void setMinCompressSize(int i2) {
        this._minCompressSize = i2;
    }

    public void setStatus(int i2, String str) {
        super.setStatus(i2, str);
        if (i2 < 200 || i2 == 204 || i2 == 205 || i2 >= 300) {
            noCompression();
        }
    }

    public void sendError(int i2) throws IOException {
        resetBuffer();
        super.sendError(i2);
    }

    public void setStatus(int i2) {
        super.setStatus(i2);
        if (i2 < 200 || i2 == 204 || i2 == 205 || i2 >= 300) {
            noCompression();
        }
    }

    public void setContentLength(long j2) {
        this._contentLength = j2;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.setContentLength();
            return;
        }
        if (!this._noCompression || j2 < 0) {
            return;
        }
        HttpServletResponse response = getResponse();
        long j3 = this._contentLength;
        if (j3 < 2147483647L) {
            response.setContentLength((int) j3);
        } else {
            response.setHeader("Content-Length", Long.toString(j3));
        }
    }
}
