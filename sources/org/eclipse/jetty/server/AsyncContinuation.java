package org.eclipse.jetty.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes9.dex */
public class AsyncContinuation implements AsyncContext, Continuation {
    private static final long DEFAULT_TIMEOUT = 30000;
    private static final int __ASYNCSTARTED = 2;
    private static final int __ASYNCWAIT = 4;
    private static final int __COMPLETED = 9;
    private static final int __COMPLETING = 7;
    private static final int __DISPATCHED = 1;
    private static final int __IDLE = 0;
    private static final int __REDISPATCH = 5;
    private static final int __REDISPATCHED = 6;
    private static final int __REDISPATCHING = 3;
    private static final int __UNCOMPLETED = 8;
    private List<AsyncListener> _asyncListeners;
    protected AbstractHttpConnection _connection;
    private volatile boolean _continuation;
    private List<ContinuationListener> _continuationListeners;
    private AsyncEventState _event;
    private volatile long _expireAt;
    private boolean _expired;
    private List<AsyncListener> _lastAsyncListeners;
    private volatile boolean _responseWrapped;
    private boolean _resumed;
    private static final Logger LOG = Log.getLogger((Class<?>) AsyncContinuation.class);
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private long _timeoutMs = 30000;
    private int _state = 0;
    private boolean _initial = true;

    public class AsyncEventState extends AsyncEvent {
        private ServletContext _dispatchContext;
        private String _pathInContext;
        private final ServletContext _suspendedContext;
        private Timeout.Task _timeout;

        public AsyncEventState(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
            super(AsyncContinuation.this, servletRequest, servletResponse);
            this._timeout = AsyncContinuation.this.new AsyncTimeout();
            this._suspendedContext = servletContext;
            Request request = AsyncContinuation.this._connection.getRequest();
            if (request.getAttribute("javax.servlet.async.request_uri") == null) {
                String str = (String) request.getAttribute("javax.servlet.forward.request_uri");
                if (str != null) {
                    request.setAttribute("javax.servlet.async.request_uri", str);
                    request.setAttribute("javax.servlet.async.context_path", request.getAttribute("javax.servlet.forward.context_path"));
                    request.setAttribute("javax.servlet.async.servlet_path", request.getAttribute("javax.servlet.forward.servlet_path"));
                    request.setAttribute("javax.servlet.async.path_info", request.getAttribute("javax.servlet.forward.path_info"));
                    request.setAttribute("javax.servlet.async.query_string", request.getAttribute("javax.servlet.forward.query_string"));
                    return;
                }
                request.setAttribute("javax.servlet.async.request_uri", request.getRequestURI());
                request.setAttribute("javax.servlet.async.context_path", request.getContextPath());
                request.setAttribute("javax.servlet.async.servlet_path", request.getServletPath());
                request.setAttribute("javax.servlet.async.path_info", request.getPathInfo());
                request.setAttribute("javax.servlet.async.query_string", request.getQueryString());
            }
        }

        public ServletContext getDispatchContext() {
            return this._dispatchContext;
        }

        public String getPath() {
            return this._pathInContext;
        }

        public ServletContext getServletContext() {
            ServletContext servletContext = this._dispatchContext;
            return servletContext == null ? this._suspendedContext : servletContext;
        }

        public ServletContext getSuspendedContext() {
            return this._suspendedContext;
        }
    }

    public class AsyncTimeout extends Timeout.Task implements Runnable {
        public AsyncTimeout() {
        }

        @Override // org.eclipse.jetty.util.thread.Timeout.Task
        public void expired() {
            AsyncContinuation.this.expired();
        }

        @Override // java.lang.Runnable
        public void run() {
            AsyncContinuation.this.expired();
        }
    }

    private void doSuspend(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            int i2 = this._state;
            if (i2 != 1 && i2 != 6) {
                throw new IllegalStateException(getStatusString());
            }
            this._resumed = false;
            this._expired = false;
            AsyncEventState asyncEventState = this._event;
            if (asyncEventState != null && servletRequest == asyncEventState.getSuppliedRequest() && servletResponse == this._event.getSuppliedResponse() && servletContext == this._event.getServletContext()) {
                this._event._dispatchContext = null;
                this._event._pathInContext = null;
            } else {
                this._event = new AsyncEventState(servletContext, servletRequest, servletResponse);
            }
            this._state = 2;
            List<AsyncListener> list = this._lastAsyncListeners;
            this._lastAsyncListeners = this._asyncListeners;
            this._asyncListeners = list;
            if (list != null) {
                list.clear();
            }
        }
        List<AsyncListener> list2 = this._lastAsyncListeners;
        if (list2 != null) {
            Iterator<AsyncListener> it = list2.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onStartAsync(this._event);
                } catch (Exception e2) {
                    LOG.warn(e2);
                }
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void addContinuationListener(ContinuationListener continuationListener) {
        synchronized (this) {
            if (this._continuationListeners == null) {
                this._continuationListeners = new ArrayList();
            }
            this._continuationListeners.add(continuationListener);
        }
    }

    public void addListener(AsyncListener asyncListener) {
        synchronized (this) {
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList();
            }
            this._asyncListeners.add(asyncListener);
        }
    }

    public void cancel() {
        synchronized (this) {
            cancelTimeout();
            this._continuationListeners = null;
        }
    }

    public void cancelTimeout() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (endPoint.isBlocking()) {
            synchronized (this) {
                this._expireAt = 0L;
                notifyAll();
            }
        } else {
            AsyncEventState asyncEventState = this._event;
            if (asyncEventState != null) {
                ((AsyncEndPoint) endPoint).cancelTimeout(asyncEventState._timeout);
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void complete() {
        synchronized (this) {
            int i2 = this._state;
            if (i2 != 1) {
                if (i2 == 2) {
                    this._state = 7;
                    return;
                }
                if (i2 == 4) {
                    this._state = 7;
                    boolean z2 = !this._expired;
                    if (z2) {
                        cancelTimeout();
                        scheduleDispatch();
                        return;
                    }
                    return;
                }
                if (i2 != 6) {
                    throw new IllegalStateException(getStatusString());
                }
            }
            throw new IllegalStateException(getStatusString());
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
    public <T extends AsyncListener> T createListener(Class<T> cls) throws ServletException {
        try {
            return cls.newInstance();
        } catch (Exception e2) {
            throw new ServletException(e2);
        }
    }

    public void dispatch() {
        synchronized (this) {
            int i2 = this._state;
            if (i2 == 2) {
                this._state = 3;
                this._resumed = true;
                return;
            }
            if (i2 != 4) {
                if (i2 != 5) {
                    throw new IllegalStateException(getStatusString());
                }
                return;
            }
            boolean z2 = !this._expired;
            this._state = 5;
            this._resumed = true;
            if (z2) {
                cancelTimeout();
                scheduleDispatch();
            }
        }
    }

    public void doComplete(Throwable th) {
        List<ContinuationListener> list;
        List<AsyncListener> list2;
        synchronized (this) {
            if (this._state != 8) {
                throw new IllegalStateException(getStatusString());
            }
            this._state = 9;
            list = this._continuationListeners;
            list2 = this._asyncListeners;
        }
        if (list2 != null) {
            for (AsyncListener asyncListener : list2) {
                if (th != null) {
                    try {
                        this._event.getSuppliedRequest().setAttribute("javax.servlet.error.exception", th);
                        this._event.getSuppliedRequest().setAttribute("javax.servlet.error.message", th.getMessage());
                        asyncListener.onError(this._event);
                    } catch (Exception e2) {
                        LOG.warn(e2);
                    }
                } else {
                    asyncListener.onComplete(this._event);
                }
            }
        }
        if (list != null) {
            Iterator<ContinuationListener> it = list.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onComplete(this);
                } catch (Exception e3) {
                    LOG.warn(e3);
                }
            }
        }
    }

    public void expired() {
        synchronized (this) {
            int i2 = this._state;
            if (i2 == 2 || i2 == 4) {
                List<ContinuationListener> list = this._continuationListeners;
                List<AsyncListener> list2 = this._asyncListeners;
                this._expired = true;
                if (list2 != null) {
                    Iterator<AsyncListener> it = list2.iterator();
                    while (it.hasNext()) {
                        try {
                            it.next().onTimeout(this._event);
                        } catch (Exception e2) {
                            LOG.warn(e2);
                        }
                    }
                }
                if (list != null) {
                    Iterator<ContinuationListener> it2 = list.iterator();
                    while (it2.hasNext()) {
                        try {
                            it2.next().onTimeout(this);
                        } catch (Exception e3) {
                            LOG.warn(e3);
                        }
                    }
                }
                synchronized (this) {
                    int i3 = this._state;
                    if (i3 == 2 || i3 == 4) {
                        dispatch();
                    } else if (!this._continuation) {
                        this._expired = false;
                    }
                }
                scheduleDispatch();
            }
        }
    }

    public AsyncEventState getAsyncEventState() {
        AsyncEventState asyncEventState;
        synchronized (this) {
            asyncEventState = this._event;
        }
        return asyncEventState;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public Object getAttribute(String str) {
        return this._connection.getRequest().getAttribute(str);
    }

    public Request getBaseRequest() {
        return this._connection.getRequest();
    }

    public ContextHandler getContextHandler() {
        AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            return ((ContextHandler.Context) asyncEventState.getServletContext()).getContextHandler();
        }
        return null;
    }

    public ServletRequest getRequest() {
        AsyncEventState asyncEventState = this._event;
        return asyncEventState != null ? asyncEventState.getSuppliedRequest() : this._connection.getRequest();
    }

    public ServletResponse getResponse() {
        AsyncEventState asyncEventState;
        return (!this._responseWrapped || (asyncEventState = this._event) == null || asyncEventState.getSuppliedResponse() == null) ? this._connection.getResponse() : this._event.getSuppliedResponse();
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public ServletResponse getServletResponse() {
        AsyncEventState asyncEventState;
        return (!this._responseWrapped || (asyncEventState = this._event) == null || asyncEventState.getSuppliedResponse() == null) ? this._connection.getResponse() : this._event.getSuppliedResponse();
    }

    public String getStatusString() {
        String str;
        String string;
        synchronized (this) {
            StringBuilder sb = new StringBuilder();
            int i2 = this._state;
            if (i2 == 0) {
                str = "IDLE";
            } else if (i2 == 1) {
                str = "DISPATCHED";
            } else if (i2 == 2) {
                str = "ASYNCSTARTED";
            } else if (i2 == 4) {
                str = "ASYNCWAIT";
            } else if (i2 == 3) {
                str = "REDISPATCHING";
            } else if (i2 == 5) {
                str = "REDISPATCH";
            } else if (i2 == 6) {
                str = "REDISPATCHED";
            } else if (i2 == 7) {
                str = "COMPLETING";
            } else if (i2 == 8) {
                str = "UNCOMPLETED";
            } else if (i2 == 9) {
                str = "COMPLETE";
            } else {
                str = "UNKNOWN?" + this._state;
            }
            sb.append(str);
            sb.append(this._initial ? ",initial" : "");
            sb.append(this._resumed ? ",resumed" : "");
            sb.append(this._expired ? ",expired" : "");
            string = sb.toString();
        }
        return string;
    }

    public long getTimeout() {
        long j2;
        synchronized (this) {
            j2 = this._timeoutMs;
        }
        return j2;
    }

    public boolean handling() {
        synchronized (this) {
            this._continuation = false;
            this._responseWrapped = false;
            int i2 = this._state;
            if (i2 != 0) {
                if (i2 == 7) {
                    this._state = 8;
                    return false;
                }
                if (i2 == 4) {
                    return false;
                }
                if (i2 != 5) {
                    throw new IllegalStateException(getStatusString());
                }
                this._state = 6;
                return true;
            }
            this._initial = true;
            this._state = 1;
            List<AsyncListener> list = this._lastAsyncListeners;
            if (list != null) {
                list.clear();
            }
            List<AsyncListener> list2 = this._asyncListeners;
            if (list2 != null) {
                list2.clear();
            } else {
                this._asyncListeners = this._lastAsyncListeners;
                this._lastAsyncListeners = null;
            }
            return true;
        }
    }

    public boolean hasOriginalRequestAndResponse() {
        boolean z2;
        synchronized (this) {
            AsyncEventState asyncEventState = this._event;
            z2 = asyncEventState != null && asyncEventState.getSuppliedRequest() == this._connection._request && this._event.getSuppliedResponse() == this._connection._response;
        }
        return z2;
    }

    public boolean isAsync() {
        synchronized (this) {
            int i2 = this._state;
            return (i2 == 0 || i2 == 1 || i2 == 8 || i2 == 9) ? false : true;
        }
    }

    public boolean isAsyncStarted() {
        synchronized (this) {
            int i2 = this._state;
            return i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5;
        }
    }

    public boolean isComplete() {
        boolean z2;
        synchronized (this) {
            z2 = this._state == 9;
        }
        return z2;
    }

    public boolean isCompleting() {
        boolean z2;
        synchronized (this) {
            z2 = this._state == 7;
        }
        return z2;
    }

    public boolean isContinuation() {
        return this._continuation;
    }

    public boolean isDispatchable() {
        synchronized (this) {
            int i2 = this._state;
            return i2 == 3 || i2 == 5 || i2 == 6 || i2 == 7;
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isExpired() {
        boolean z2;
        synchronized (this) {
            z2 = this._expired;
        }
        return z2;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isInitial() {
        boolean z2;
        synchronized (this) {
            z2 = this._initial;
        }
        return z2;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isResponseWrapped() {
        return this._responseWrapped;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isResumed() {
        boolean z2;
        synchronized (this) {
            z2 = this._resumed;
        }
        return z2;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isSuspended() {
        synchronized (this) {
            int i2 = this._state;
            return i2 == 2 || i2 == 3 || i2 == 4 || i2 == 7;
        }
    }

    public boolean isSuspending() {
        synchronized (this) {
            int i2 = this._state;
            return i2 == 2 || i2 == 4;
        }
    }

    public boolean isUncompleted() {
        boolean z2;
        synchronized (this) {
            z2 = this._state == 8;
        }
        return z2;
    }

    public void recycle() {
        synchronized (this) {
            int i2 = this._state;
            if (i2 == 1 || i2 == 6) {
                throw new IllegalStateException(getStatusString());
            }
            this._state = 0;
            this._initial = true;
            this._resumed = false;
            this._expired = false;
            this._responseWrapped = false;
            cancelTimeout();
            this._timeoutMs = 30000L;
            this._continuationListeners = null;
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void removeAttribute(String str) {
        this._connection.getRequest().removeAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void resume() {
        dispatch();
    }

    public void scheduleDispatch() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (endPoint.isBlocking()) {
            return;
        }
        ((AsyncEndPoint) endPoint).asyncDispatch();
    }

    public void scheduleTimeout() {
        EndPoint endPoint = this._connection.getEndPoint();
        if (this._timeoutMs > 0) {
            if (!endPoint.isBlocking()) {
                ((AsyncEndPoint) endPoint).scheduleTimeout(this._event._timeout, this._timeoutMs);
                return;
            }
            synchronized (this) {
                this._expireAt = System.currentTimeMillis() + this._timeoutMs;
                long jCurrentTimeMillis = this._timeoutMs;
                while (this._expireAt > 0 && jCurrentTimeMillis > 0 && this._connection.getServer().isRunning()) {
                    try {
                        wait(jCurrentTimeMillis);
                    } catch (InterruptedException e2) {
                        LOG.ignore(e2);
                    }
                    jCurrentTimeMillis = this._expireAt - System.currentTimeMillis();
                }
                if (this._expireAt > 0 && jCurrentTimeMillis <= 0 && this._connection.getServer().isRunning()) {
                    expired();
                }
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setAttribute(String str, Object obj) {
        this._connection.getRequest().setAttribute(str, obj);
    }

    public void setConnection(AbstractHttpConnection abstractHttpConnection) {
        synchronized (this) {
            this._connection = abstractHttpConnection;
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setTimeout(long j2) {
        synchronized (this) {
            this._timeoutMs = j2;
        }
    }

    public void start(final Runnable runnable) {
        final AsyncEventState asyncEventState = this._event;
        if (asyncEventState != null) {
            this._connection.getServer().getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.server.AsyncContinuation.1
                @Override // java.lang.Runnable
                public void run() throws Throwable {
                    ((ContextHandler.Context) asyncEventState.getServletContext()).getContextHandler().handle(runnable);
                }
            });
        }
    }

    public void startAsync(ServletContext servletContext, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            this._responseWrapped = !(servletResponse instanceof Response);
            doSuspend(servletContext, servletRequest, servletResponse);
            if (servletRequest instanceof HttpServletRequest) {
                this._event._pathInContext = URIUtil.addPaths(((HttpServletRequest) servletRequest).getServletPath(), ((HttpServletRequest) servletRequest).getPathInfo());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend(ServletResponse servletResponse) {
        this._continuation = true;
        this._responseWrapped = true ^ (servletResponse instanceof Response);
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), servletResponse);
    }

    public String toString() {
        String str;
        synchronized (this) {
            str = super.toString() + "@" + getStatusString();
        }
        return str;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void undispatch() {
        if (!isSuspended()) {
            throw new IllegalStateException("!suspended");
        }
        if (!LOG.isDebugEnabled()) {
            throw __exception;
        }
        throw new ContinuationThrowable();
    }

    public boolean unhandle() {
        synchronized (this) {
            int i2 = this._state;
            if (i2 == 0) {
                throw new IllegalStateException(getStatusString());
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    this._initial = false;
                    this._state = 4;
                    scheduleTimeout();
                    int i3 = this._state;
                    if (i3 == 4) {
                        return true;
                    }
                    if (i3 == 7) {
                        this._state = 8;
                        return true;
                    }
                    this._initial = false;
                    this._state = 6;
                    return false;
                }
                if (i2 == 3) {
                    this._initial = false;
                    this._state = 6;
                    return false;
                }
                if (i2 != 6) {
                    if (i2 != 7) {
                        throw new IllegalStateException(getStatusString());
                    }
                    this._initial = false;
                    this._state = 8;
                    return true;
                }
            }
            this._state = 8;
            return true;
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend() {
        this._responseWrapped = false;
        this._continuation = true;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), this._connection.getResponse());
    }

    public void addListener(AsyncListener asyncListener, ServletRequest servletRequest, ServletResponse servletResponse) {
        synchronized (this) {
            if (this._asyncListeners == null) {
                this._asyncListeners = new ArrayList();
            }
            this._asyncListeners.add(asyncListener);
        }
    }

    public void startAsync() {
        this._responseWrapped = false;
        this._continuation = false;
        doSuspend(this._connection.getRequest().getServletContext(), this._connection.getRequest(), this._connection.getResponse());
    }

    public void dispatch(ServletContext servletContext, String str) {
        this._event._dispatchContext = servletContext;
        this._event._pathInContext = str;
        dispatch();
    }

    public void dispatch(String str) {
        this._event._pathInContext = str;
        dispatch();
    }
}
