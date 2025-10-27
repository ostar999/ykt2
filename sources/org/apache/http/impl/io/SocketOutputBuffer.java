package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpParams;

/* loaded from: classes9.dex */
public class SocketOutputBuffer extends AbstractSessionOutputBuffer {
    public SocketOutputBuffer(Socket socket, int i2, HttpParams httpParams) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("Socket may not be null");
        }
        i2 = i2 < 0 ? socket.getSendBufferSize() : i2;
        init(socket.getOutputStream(), i2 < 1024 ? 1024 : i2, httpParams);
    }
}
