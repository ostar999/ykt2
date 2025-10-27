package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
/* loaded from: classes9.dex */
public class EofSensorInputStream extends InputStream implements ConnectionReleaseTrigger {
    private final EofSensorWatcher eofWatcher;
    private boolean selfClosed;
    protected InputStream wrappedStream;

    public EofSensorInputStream(InputStream inputStream, EofSensorWatcher eofSensorWatcher) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Wrapped stream may not be null.");
        }
        this.wrappedStream = inputStream;
        this.selfClosed = false;
        this.eofWatcher = eofSensorWatcher;
    }

    @Override // org.apache.http.conn.ConnectionReleaseTrigger
    public void abortConnection() throws IOException {
        this.selfClosed = true;
        checkAbort();
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (!isReadAllowed()) {
            return 0;
        }
        try {
            return this.wrappedStream.available();
        } catch (IOException e2) {
            checkAbort();
            throw e2;
        }
    }

    public void checkAbort() throws IOException {
        InputStream inputStream = this.wrappedStream;
        if (inputStream != null) {
            try {
                EofSensorWatcher eofSensorWatcher = this.eofWatcher;
                if (eofSensorWatcher != null ? eofSensorWatcher.streamAbort(inputStream) : true) {
                    this.wrappedStream.close();
                }
            } finally {
                this.wrappedStream = null;
            }
        }
    }

    public void checkClose() throws IOException {
        InputStream inputStream = this.wrappedStream;
        if (inputStream != null) {
            try {
                EofSensorWatcher eofSensorWatcher = this.eofWatcher;
                if (eofSensorWatcher != null ? eofSensorWatcher.streamClosed(inputStream) : true) {
                    this.wrappedStream.close();
                }
            } finally {
                this.wrappedStream = null;
            }
        }
    }

    public void checkEOF(int i2) throws IOException {
        InputStream inputStream = this.wrappedStream;
        if (inputStream == null || i2 >= 0) {
            return;
        }
        try {
            EofSensorWatcher eofSensorWatcher = this.eofWatcher;
            if (eofSensorWatcher != null ? eofSensorWatcher.eofDetected(inputStream) : true) {
                this.wrappedStream.close();
            }
        } finally {
            this.wrappedStream = null;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.selfClosed = true;
        checkClose();
    }

    public boolean isReadAllowed() throws IOException {
        if (this.selfClosed) {
            throw new IOException("Attempted read on closed stream.");
        }
        return this.wrappedStream != null;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!isReadAllowed()) {
            return -1;
        }
        try {
            int i2 = this.wrappedStream.read();
            checkEOF(i2);
            return i2;
        } catch (IOException e2) {
            checkAbort();
            throw e2;
        }
    }

    @Override // org.apache.http.conn.ConnectionReleaseTrigger
    public void releaseConnection() throws IOException {
        close();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (!isReadAllowed()) {
            return -1;
        }
        try {
            int i4 = this.wrappedStream.read(bArr, i2, i3);
            checkEOF(i4);
            return i4;
        } catch (IOException e2) {
            checkAbort();
            throw e2;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        if (!isReadAllowed()) {
            return -1;
        }
        try {
            int i2 = this.wrappedStream.read(bArr);
            checkEOF(i2);
            return i2;
        } catch (IOException e2) {
            checkAbort();
            throw e2;
        }
    }
}
