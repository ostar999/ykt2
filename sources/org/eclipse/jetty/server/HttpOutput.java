package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletOutputStream;
import org.eclipse.jetty.http.AbstractGenerator;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

/* loaded from: classes9.dex */
public class HttpOutput extends ServletOutputStream {
    ByteArrayOutputStream2 _bytes;
    String _characterEncoding;
    char[] _chars;
    private boolean _closed;
    protected final AbstractHttpConnection _connection;
    Writer _converter;
    protected final AbstractGenerator _generator;
    private ByteArrayBuffer _onebyte;

    public HttpOutput(AbstractHttpConnection abstractHttpConnection) {
        this._connection = abstractHttpConnection;
        this._generator = (AbstractGenerator) abstractHttpConnection.getGenerator();
    }

    public void close() throws IOException {
        this._closed = true;
    }

    public void flush() throws IOException {
        this._generator.flush(getMaxIdleTime());
    }

    public int getMaxIdleTime() {
        return this._connection.getMaxIdleTime();
    }

    public boolean isClosed() {
        return this._closed;
    }

    public boolean isWritten() {
        return this._generator.getContentWritten() > 0;
    }

    public void print(String str) throws IOException {
        write(str.getBytes());
    }

    public void reopen() {
        this._closed = false;
    }

    public void write(byte[] bArr, int i2, int i3) throws IOException {
        write(new ByteArrayBuffer(bArr, i2, i3));
    }

    public void write(byte[] bArr) throws IOException {
        write(new ByteArrayBuffer(bArr));
    }

    public void write(int i2) throws IOException {
        ByteArrayBuffer byteArrayBuffer = this._onebyte;
        if (byteArrayBuffer == null) {
            this._onebyte = new ByteArrayBuffer(1);
        } else {
            byteArrayBuffer.clear();
        }
        this._onebyte.put((byte) i2);
        write(this._onebyte);
    }

    private void write(Buffer buffer) throws IOException {
        if (!this._closed) {
            if (this._generator.isOpen()) {
                while (this._generator.isBufferFull()) {
                    this._generator.blockForOutput(getMaxIdleTime());
                    if (!this._closed) {
                        if (!this._generator.isOpen()) {
                            throw new EofException();
                        }
                    } else {
                        throw new IOException("Closed");
                    }
                }
                this._generator.addContent(buffer, false);
                if (this._generator.isAllContentWritten()) {
                    flush();
                    close();
                } else if (this._generator.isBufferFull()) {
                    this._connection.commitResponse(false);
                }
                while (buffer.length() > 0 && this._generator.isOpen()) {
                    this._generator.blockForOutput(getMaxIdleTime());
                }
                return;
            }
            throw new EofException();
        }
        throw new IOException("Closed");
    }
}
