package org.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
class TlsInputStream extends InputStream {
    private byte[] buf = new byte[1];
    private TlsProtocolHandler handler;

    public TlsInputStream(TlsProtocolHandler tlsProtocolHandler) {
        this.handler = tlsProtocolHandler;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.handler.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.buf) < 0) {
            return -1;
        }
        return this.buf[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        return this.handler.readApplicationData(bArr, i2, i3);
    }
}
