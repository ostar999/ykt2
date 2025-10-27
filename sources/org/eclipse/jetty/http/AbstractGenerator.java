package org.eclipse.jetty.http;

import java.io.IOException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.View;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class AbstractGenerator implements Generator {
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractGenerator.class);
    public static final byte[] NO_BYTES = new byte[0];
    public static final int STATE_CONTENT = 2;
    public static final int STATE_END = 4;
    public static final int STATE_FLUSHING = 3;
    public static final int STATE_HEADER = 0;
    protected Buffer _buffer;
    protected final Buffers _buffers;
    protected Buffer _content;
    protected Buffer _date;
    protected final EndPoint _endp;
    protected Buffer _header;
    protected Buffer _method;
    protected Buffer _reason;
    private boolean _sendServerVersion;
    protected String _uri;
    protected int _state = 0;
    protected int _status = 0;
    protected int _version = 11;
    protected long _contentWritten = 0;
    protected long _contentLength = -3;
    protected boolean _last = false;
    protected boolean _head = false;
    protected boolean _noContent = false;
    protected Boolean _persistent = null;

    public AbstractGenerator(Buffers buffers, EndPoint endPoint) {
        this._buffers = buffers;
        this._endp = endPoint;
    }

    public void blockForOutput(long j2) throws IOException {
        if (this._endp.isBlocking()) {
            try {
                flushBuffer();
                return;
            } catch (IOException e2) {
                this._endp.close();
                throw e2;
            }
        }
        if (this._endp.blockWritable(j2)) {
            flushBuffer();
        } else {
            this._endp.close();
            throw new EofException("timeout");
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void complete() throws IOException {
        if (this._state == 0) {
            throw new IllegalStateException("State==HEADER");
        }
        long j2 = this._contentLength;
        if (j2 < 0 || j2 == this._contentWritten || this._head) {
            return;
        }
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("ContentLength written==" + this._contentWritten + " != contentLength==" + this._contentLength, new Object[0]);
        }
        this._persistent = Boolean.FALSE;
    }

    @Override // org.eclipse.jetty.http.Generator
    public abstract void completeHeader(HttpFields httpFields, boolean z2) throws IOException;

    public void completeUncheckedAddContent() {
        if (this._noContent) {
            Buffer buffer = this._buffer;
            if (buffer != null) {
                buffer.clear();
                return;
            }
            return;
        }
        this._contentWritten += this._buffer.length();
        if (this._head) {
            this._buffer.clear();
        }
    }

    public void flush(long j2) throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j3 = j2 + jCurrentTimeMillis;
        Buffer buffer = this._content;
        Buffer buffer2 = this._buffer;
        if ((buffer == null || buffer.length() <= 0) && ((buffer2 == null || buffer2.length() <= 0) && !isBufferFull())) {
            return;
        }
        flushBuffer();
        while (jCurrentTimeMillis < j3) {
            if (((buffer == null || buffer.length() <= 0) && (buffer2 == null || buffer2.length() <= 0)) || !this._endp.isOpen() || this._endp.isOutputShutdown()) {
                return;
            }
            blockForOutput(j3 - jCurrentTimeMillis);
            jCurrentTimeMillis = System.currentTimeMillis();
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public abstract int flushBuffer() throws IOException;

    @Override // org.eclipse.jetty.http.Generator
    public int getContentBufferSize() {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        return this._buffer.capacity();
    }

    @Override // org.eclipse.jetty.http.Generator
    public long getContentWritten() {
        return this._contentWritten;
    }

    public boolean getSendServerVersion() {
        return this._sendServerVersion;
    }

    public int getState() {
        return this._state;
    }

    public Buffer getUncheckedBuffer() {
        return this._buffer;
    }

    public int getVersion() {
        return this._version;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void increaseContentBufferSize(int i2) {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        if (i2 > this._buffer.capacity()) {
            Buffer buffer = this._buffers.getBuffer(i2);
            buffer.put(this._buffer);
            this._buffers.returnBuffer(this._buffer);
            this._buffer = buffer;
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isAllContentWritten() {
        long j2 = this._contentLength;
        return j2 >= 0 && this._contentWritten >= j2;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isBufferFull() {
        Buffer buffer = this._buffer;
        if (buffer == null || buffer.space() != 0) {
            Buffer buffer2 = this._content;
            return buffer2 != null && buffer2.length() > 0;
        }
        if (this._buffer.length() == 0 && !this._buffer.isImmutable()) {
            this._buffer.compact();
        }
        return this._buffer.space() == 0;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isCommitted() {
        return this._state != 0;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isComplete() {
        return this._state == 4;
    }

    public boolean isHead() {
        return this._head;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isIdle() {
        return this._state == 0 && this._method == null && this._status == 0;
    }

    public boolean isOpen() {
        return this._endp.isOpen();
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isPersistent() {
        Boolean bool = this._persistent;
        return bool != null ? bool.booleanValue() : isRequest() || this._version > 10;
    }

    public abstract boolean isRequest();

    public abstract boolean isResponse();

    public boolean isState(int i2) {
        return this._state == i2;
    }

    @Override // org.eclipse.jetty.http.Generator
    public boolean isWritten() {
        return this._contentWritten > 0;
    }

    public abstract int prepareUncheckedAddContent() throws IOException;

    @Override // org.eclipse.jetty.http.Generator
    public void reset() {
        this._state = 0;
        this._status = 0;
        this._version = 11;
        this._reason = null;
        this._last = false;
        this._head = false;
        this._noContent = false;
        this._persistent = null;
        this._contentWritten = 0L;
        this._contentLength = -3L;
        this._date = null;
        this._content = null;
        this._method = null;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void resetBuffer() {
        if (this._state >= 3) {
            throw new IllegalStateException("Flushed");
        }
        this._last = false;
        this._persistent = null;
        this._contentWritten = 0L;
        this._contentLength = -3L;
        this._content = null;
        Buffer buffer = this._buffer;
        if (buffer != null) {
            buffer.clear();
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void returnBuffers() {
        Buffer buffer = this._buffer;
        if (buffer != null && buffer.length() == 0) {
            this._buffers.returnBuffer(this._buffer);
            this._buffer = null;
        }
        Buffer buffer2 = this._header;
        if (buffer2 == null || buffer2.length() != 0) {
            return;
        }
        this._buffers.returnBuffer(this._header);
        this._header = null;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void sendError(int i2, String str, String str2, boolean z2) throws IOException {
        if (z2) {
            this._persistent = Boolean.FALSE;
        }
        if (isCommitted()) {
            LOG.debug("sendError on committed: {} {}", Integer.valueOf(i2), str);
            return;
        }
        LOG.debug("sendError: {} {}", Integer.valueOf(i2), str);
        setResponse(i2, str);
        if (str2 != null) {
            completeHeader(null, false);
            addContent(new View(new ByteArrayBuffer(str2)), true);
        } else {
            completeHeader(null, true);
        }
        complete();
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setContentLength(long j2) {
        if (j2 < 0) {
            this._contentLength = -3L;
        } else {
            this._contentLength = j2;
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setDate(Buffer buffer) {
        this._date = buffer;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setHead(boolean z2) {
        this._head = z2;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setPersistent(boolean z2) {
        this._persistent = Boolean.valueOf(z2);
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setRequest(String str, String str2) {
        if (str == null || "GET".equals(str)) {
            this._method = HttpMethods.GET_BUFFER;
        } else {
            this._method = HttpMethods.CACHE.lookup(str);
        }
        this._uri = str2;
        if (this._version == 9) {
            this._noContent = true;
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setResponse(int i2, String str) {
        if (this._state != 0) {
            throw new IllegalStateException("STATE!=START");
        }
        this._method = null;
        this._status = i2;
        if (str != null) {
            int length = str.length();
            if (length > 1024) {
                length = 1024;
            }
            this._reason = new ByteArrayBuffer(length);
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt = str.charAt(i3);
                if (cCharAt == '\r' || cCharAt == '\n') {
                    this._reason.put((byte) 32);
                } else {
                    this._reason.put((byte) cCharAt);
                }
            }
        }
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setSendServerVersion(boolean z2) {
        this._sendServerVersion = z2;
    }

    @Override // org.eclipse.jetty.http.Generator
    public void setVersion(int i2) {
        if (this._state != 0) {
            throw new IllegalStateException("STATE!=START " + this._state);
        }
        this._version = i2;
        if (i2 != 9 || this._method == null) {
            return;
        }
        this._noContent = true;
    }

    public void uncheckedAddContent(int i2) {
        this._buffer.put((byte) i2);
    }
}
