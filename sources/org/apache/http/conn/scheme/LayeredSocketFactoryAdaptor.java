package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;

@Deprecated
/* loaded from: classes9.dex */
class LayeredSocketFactoryAdaptor extends SocketFactoryAdaptor implements LayeredSocketFactory {
    private final LayeredSchemeSocketFactory factory;

    public LayeredSocketFactoryAdaptor(LayeredSchemeSocketFactory layeredSchemeSocketFactory) {
        super(layeredSchemeSocketFactory);
        this.factory = layeredSchemeSocketFactory;
    }

    @Override // org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return this.factory.createLayeredSocket(socket, str, i2, z2);
    }
}
