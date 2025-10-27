package org.eclipse.jetty.server;

import java.io.IOException;
import javax.servlet.ServletInputStream;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.EofException;

/* loaded from: classes9.dex */
public class HttpInput extends ServletInputStream {
    protected final AbstractHttpConnection _connection;
    protected final HttpParser _parser;

    public HttpInput(AbstractHttpConnection abstractHttpConnection) {
        this._connection = abstractHttpConnection;
        this._parser = (HttpParser) abstractHttpConnection.getParser();
    }

    public int available() throws IOException {
        return this._parser.available();
    }

    public int read() throws IOException {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) < 0) {
            return -1;
        }
        return bArr[0] & 255;
    }

    public int read(byte[] bArr, int i2, int i3) throws IOException {
        Buffer bufferBlockForContent = this._parser.blockForContent(this._connection.getMaxIdleTime());
        if (bufferBlockForContent != null) {
            return bufferBlockForContent.get(bArr, i2, i3);
        }
        if (this._connection.isEarlyEOF()) {
            throw new EofException("early EOF");
        }
        return -1;
    }
}
