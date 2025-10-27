package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;

/* loaded from: classes9.dex */
public interface LayeredSchemeSocketFactory extends SchemeSocketFactory {
    Socket createLayeredSocket(Socket socket, String str, int i2, boolean z2) throws IOException;
}
