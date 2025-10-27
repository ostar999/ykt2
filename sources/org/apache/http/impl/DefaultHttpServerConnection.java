package org.apache.http.impl;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/* loaded from: classes9.dex */
public class DefaultHttpServerConnection extends SocketHttpServerConnection {
    @Override // org.apache.http.impl.SocketHttpServerConnection
    public void bind(Socket socket, HttpParams httpParams) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("Socket may not be null");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        assertNotOpen();
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int linger = HttpConnectionParams.getLinger(httpParams);
        if (linger >= 0) {
            socket.setSoLinger(linger > 0, linger);
        }
        super.bind(socket, httpParams);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StrPool.BRACKET_START);
        if (isOpen()) {
            stringBuffer.append(getRemotePort());
        } else {
            stringBuffer.append("closed");
        }
        stringBuffer.append(StrPool.BRACKET_END);
        return stringBuffer.toString();
    }
}
