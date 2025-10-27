package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

/* loaded from: classes9.dex */
public class HandlerCollection extends AbstractHandlerContainer {
    private volatile Handler[] _handlers;
    private final boolean _mutableWhenRunning;
    private boolean _parallelStart;

    public HandlerCollection() {
        this._parallelStart = false;
        this._mutableWhenRunning = false;
    }

    public void addHandler(Handler handler) {
        setHandlers((Handler[]) LazyList.addToArray(getHandlers(), handler, Handler.class));
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Destroyable
    public void destroy() {
        if (!isStopped()) {
            throw new IllegalStateException("!STOPPED");
        }
        Handler[] childHandlers = getChildHandlers();
        setHandlers(null);
        for (Handler handler : childHandlers) {
            handler.destroy();
        }
        super.destroy();
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        final MultiException multiException = new MultiException();
        if (this._handlers != null) {
            if (this._parallelStart) {
                final CountDownLatch countDownLatch = new CountDownLatch(this._handlers.length);
                final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                for (int i2 = 0; i2 < this._handlers.length; i2++) {
                    final int i3 = i2;
                    getServer().getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.server.handler.HandlerCollection.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ClassLoader contextClassLoader2 = Thread.currentThread().getContextClassLoader();
                            try {
                                Thread.currentThread().setContextClassLoader(contextClassLoader);
                                HandlerCollection.this._handlers[i3].start();
                            } finally {
                                try {
                                } finally {
                                }
                            }
                        }
                    });
                }
                countDownLatch.await();
            } else {
                for (int i4 = 0; i4 < this._handlers.length; i4++) {
                    try {
                        this._handlers[i4].start();
                    } catch (Throwable th) {
                        multiException.add(th);
                    }
                }
            }
        }
        super.doStart();
        multiException.ifExceptionThrow();
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        MultiException multiException = new MultiException();
        try {
            super.doStop();
        } catch (Throwable th) {
            multiException.add(th);
        }
        if (this._handlers != null) {
            int length = this._handlers.length;
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    break;
                }
                try {
                    this._handlers[i2].stop();
                } catch (Throwable th2) {
                    multiException.add(th2);
                }
                length = i2;
            }
        }
        multiException.ifExceptionThrow();
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer
    public Object expandChildren(Object obj, Class cls) {
        Handler[] handlers = getHandlers();
        for (int i2 = 0; handlers != null && i2 < handlers.length; i2++) {
            obj = expandHandler(handlers[i2], obj, cls);
        }
        return obj;
    }

    @Override // org.eclipse.jetty.server.HandlerContainer
    public Handler[] getHandlers() {
        return this._handlers;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (this._handlers == null || !isStarted()) {
            return;
        }
        MultiException multiException = null;
        for (int i2 = 0; i2 < this._handlers.length; i2++) {
            try {
                this._handlers[i2].handle(str, request, httpServletRequest, httpServletResponse);
            } catch (IOException e2) {
                throw e2;
            } catch (RuntimeException e3) {
                throw e3;
            } catch (Exception e4) {
                if (multiException == null) {
                    multiException = new MultiException();
                }
                multiException.add(e4);
            }
        }
        if (multiException != null) {
            if (multiException.size() != 1) {
                throw new ServletException(multiException);
            }
            throw new ServletException(multiException.getThrowable(0));
        }
    }

    public boolean isParallelStart() {
        return this._parallelStart;
    }

    public void removeHandler(Handler handler) {
        Handler[] handlers = getHandlers();
        if (handlers == null || handlers.length <= 0) {
            return;
        }
        setHandlers((Handler[]) LazyList.removeFromArray(handlers, handler));
    }

    public void setHandlers(Handler[] handlerArr) {
        if (!this._mutableWhenRunning && isStarted()) {
            throw new IllegalStateException(AbstractLifeCycle.STARTED);
        }
        Handler[] handlerArr2 = this._handlers == null ? null : (Handler[]) this._handlers.clone();
        this._handlers = handlerArr;
        Server server = getServer();
        MultiException multiException = new MultiException();
        for (int i2 = 0; handlerArr != null && i2 < handlerArr.length; i2++) {
            if (handlerArr[i2].getServer() != server) {
                handlerArr[i2].setServer(server);
            }
        }
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) handlerArr2, (Object[]) handlerArr, "handler");
        }
        for (int i3 = 0; handlerArr2 != null && i3 < handlerArr2.length; i3++) {
            Handler handler = handlerArr2[i3];
            if (handler != null) {
                try {
                    if (handler.isStarted()) {
                        handlerArr2[i3].stop();
                    }
                } catch (Throwable th) {
                    multiException.add(th);
                }
            }
        }
        multiException.ifExceptionThrowRuntime();
    }

    public void setParallelStart(boolean z2) {
        this._parallelStart = z2;
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        if (isStarted()) {
            throw new IllegalStateException(AbstractLifeCycle.STARTED);
        }
        Server server2 = getServer();
        super.setServer(server);
        Handler[] handlers = getHandlers();
        for (int i2 = 0; handlers != null && i2 < handlers.length; i2++) {
            handlers[i2].setServer(server);
        }
        if (server == null || server == server2) {
            return;
        }
        server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._handlers, "handler");
    }

    public HandlerCollection(boolean z2) {
        this._parallelStart = false;
        this._mutableWhenRunning = z2;
    }
}
