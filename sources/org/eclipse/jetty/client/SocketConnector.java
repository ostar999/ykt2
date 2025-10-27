package org.eclipse.jetty.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import javax.net.SocketFactory;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.bio.SocketEndPoint;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
class SocketConnector extends AbstractLifeCycle implements HttpClient.Connector {
    private static final Logger LOG = Log.getLogger((Class<?>) SocketConnector.class);
    private final HttpClient _httpClient;

    public SocketConnector(HttpClient httpClient) {
        this._httpClient = httpClient;
    }

    @Override // org.eclipse.jetty.client.HttpClient.Connector
    public void startConnection(final HttpDestination httpDestination) throws InterruptedException, IOException {
        Socket socketNewSslSocket = httpDestination.isSecure() ? this._httpClient.getSslContextFactory().newSslSocket() : SocketFactory.getDefault().createSocket();
        socketNewSslSocket.setSoTimeout(0);
        socketNewSslSocket.setTcpNoDelay(true);
        socketNewSslSocket.connect((httpDestination.isProxied() ? httpDestination.getProxy() : httpDestination.getAddress()).toSocketAddress(), this._httpClient.getConnectTimeout());
        final BlockingHttpConnection blockingHttpConnection = new BlockingHttpConnection(this._httpClient.getRequestBuffers(), this._httpClient.getResponseBuffers(), new SocketEndPoint(socketNewSslSocket));
        blockingHttpConnection.setDestination(httpDestination);
        httpDestination.onNewConnection(blockingHttpConnection);
        this._httpClient.getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.client.SocketConnector.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    try {
                        try {
                            Connection connection = blockingHttpConnection;
                            while (true) {
                                Connection connectionHandle = connection.handle();
                                if (connectionHandle == connection) {
                                    break;
                                } else {
                                    connection = connectionHandle;
                                }
                            }
                            httpDestination.returnConnection(blockingHttpConnection, true);
                        } catch (IOException e2) {
                            if (e2 instanceof InterruptedIOException) {
                                SocketConnector.LOG.ignore(e2);
                            } else {
                                SocketConnector.LOG.debug(e2);
                                httpDestination.onException(e2);
                            }
                            httpDestination.returnConnection(blockingHttpConnection, true);
                        }
                    } catch (IOException e3) {
                        SocketConnector.LOG.debug(e3);
                    }
                } catch (Throwable th) {
                    try {
                        httpDestination.returnConnection(blockingHttpConnection, true);
                    } catch (IOException e4) {
                        SocketConnector.LOG.debug(e4);
                    }
                    throw th;
                }
            }
        });
    }
}
