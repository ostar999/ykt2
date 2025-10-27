package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.http.Generator;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.Parser;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class BlockingHttpConnection extends AbstractHttpConnection {
    private static final Logger LOG = Log.getLogger((Class<?>) BlockingHttpConnection.class);

    public BlockingHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(connector, endPoint, server);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v21, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r4v39, types: [org.eclipse.jetty.io.Connection] */
    @Override // org.eclipse.jetty.server.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    public Connection handle() throws IOException {
        EndPoint endPoint;
        ?? r4;
        ?? r42;
        try {
            AbstractHttpConnection.setCurrentConnection(this);
            BlockingHttpConnection blockingHttpConnection = this;
            while (this._endp.isOpen() && blockingHttpConnection == this) {
                try {
                    try {
                        if (!this._parser.isComplete() && !this._endp.isInputShutdown()) {
                            this._parser.parseAvailable();
                        }
                        if (this._generator.isCommitted() && !this._generator.isComplete() && !this._endp.isOutputShutdown()) {
                            this._generator.flushBuffer();
                        }
                        this._endp.flush();
                        if (this._parser.isComplete() && this._generator.isComplete()) {
                            reset();
                            if (this._response.getStatus() == 101 && (r42 = (Connection) this._request.getAttribute("org.eclipse.jetty.io.Connection")) != 0) {
                                blockingHttpConnection = r42;
                            }
                            if (!this._generator.isPersistent() && !this._endp.isOutputShutdown()) {
                                LOG.warn("Safety net oshut!!! Please open a bugzilla", new Object[0]);
                                this._endp.shutdownOutput();
                            }
                        }
                    } catch (HttpException e2) {
                        Logger logger = LOG;
                        if (logger.isDebugEnabled()) {
                            logger.debug("uri=" + this._uri, new Object[0]);
                            logger.debug("fields=" + this._requestFields, new Object[0]);
                            logger.debug(e2);
                        }
                        this._generator.sendError(e2.getStatus(), e2.getReason(), null, true);
                        this._parser.reset();
                        this._endp.shutdownOutput();
                        if (this._parser.isComplete() && this._generator.isComplete()) {
                            reset();
                            if (this._response.getStatus() == 101 && (r4 = (Connection) this._request.getAttribute("org.eclipse.jetty.io.Connection")) != 0) {
                                blockingHttpConnection = r4;
                            }
                            if (!this._generator.isPersistent() && !this._endp.isOutputShutdown()) {
                                logger.warn("Safety net oshut!!! Please open a bugzilla", new Object[0]);
                                this._endp.shutdownOutput();
                            }
                        }
                        if (this._endp.isInputShutdown() && this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
                            endPoint = this._endp;
                        }
                    }
                    if (this._endp.isInputShutdown() && this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
                        endPoint = this._endp;
                        endPoint.close();
                    }
                } finally {
                }
            }
            return blockingHttpConnection;
        } finally {
            AbstractHttpConnection.setCurrentConnection(null);
            this._parser.returnBuffers();
            this._generator.returnBuffers();
        }
    }

    @Override // org.eclipse.jetty.server.AbstractHttpConnection
    public void handleRequest() throws Throwable {
        super.handleRequest();
    }

    public BlockingHttpConnection(Connector connector, EndPoint endPoint, Server server, Parser parser, Generator generator, Request request) {
        super(connector, endPoint, server, parser, generator, request);
    }
}
