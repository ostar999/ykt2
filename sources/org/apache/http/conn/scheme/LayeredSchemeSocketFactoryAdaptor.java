package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;

@Deprecated
/* loaded from: classes9.dex */
class LayeredSchemeSocketFactoryAdaptor extends SchemeSocketFactoryAdaptor implements LayeredSchemeSocketFactory {
    private final LayeredSocketFactory factory;

    public LayeredSchemeSocketFactoryAdaptor(LayeredSocketFactory layeredSocketFactory) {
        super(layeredSocketFactory);
        this.factory = layeredSocketFactory;
    }

    @Override // org.apache.http.conn.scheme.LayeredSchemeSocketFactory
    public Socket createLayeredSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return this.factory.createSocket(socket, str, i2, z2);
    }
}
