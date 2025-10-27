package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.http.io.EofSensor;
import org.apache.http.params.HttpParams;

/* loaded from: classes9.dex */
public class SocketInputBuffer extends AbstractSessionInputBuffer implements EofSensor {
    private static final Class SOCKET_TIMEOUT_CLASS = SocketTimeoutExceptionClass();
    private boolean eof;
    private final Socket socket;

    public SocketInputBuffer(Socket socket, int i2, HttpParams httpParams) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("Socket may not be null");
        }
        this.socket = socket;
        this.eof = false;
        i2 = i2 < 0 ? socket.getReceiveBufferSize() : i2;
        init(socket.getInputStream(), i2 < 1024 ? 1024 : i2, httpParams);
    }

    private static Class SocketTimeoutExceptionClass() {
        try {
            return Class.forName("java.net.SocketTimeoutException");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private static boolean isSocketTimeoutException(InterruptedIOException interruptedIOException) {
        Class cls = SOCKET_TIMEOUT_CLASS;
        if (cls != null) {
            return cls.isInstance(interruptedIOException);
        }
        return true;
    }

    @Override // org.apache.http.impl.io.AbstractSessionInputBuffer
    public int fillBuffer() throws IOException {
        int iFillBuffer = super.fillBuffer();
        this.eof = iFillBuffer == -1;
        return iFillBuffer;
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public boolean isDataAvailable(int i2) throws IOException {
        boolean zHasBufferedData = hasBufferedData();
        if (!zHasBufferedData) {
            int soTimeout = this.socket.getSoTimeout();
            try {
                try {
                    this.socket.setSoTimeout(i2);
                    fillBuffer();
                    zHasBufferedData = hasBufferedData();
                } catch (InterruptedIOException e2) {
                    if (!isSocketTimeoutException(e2)) {
                        throw e2;
                    }
                }
            } finally {
                this.socket.setSoTimeout(soTimeout);
            }
        }
        return zHasBufferedData;
    }

    @Override // org.apache.http.io.EofSensor
    public boolean isEof() {
        return this.eof;
    }
}
