package org.eclipse.jetty.continuation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;
import org.eclipse.jetty.continuation.ContinuationFilter;

/* loaded from: classes9.dex */
class FauxContinuation implements ContinuationFilter.FilteredContinuation {
    private static final int __COMPLETE = 7;
    private static final int __COMPLETING = 4;
    private static final int __HANDLING = 1;
    private static final int __RESUMING = 3;
    private static final int __SUSPENDED = 5;
    private static final int __SUSPENDING = 2;
    private static final int __UNSUSPENDING = 6;
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private ArrayList<ContinuationListener> _listeners;
    private final ServletRequest _request;
    private ServletResponse _response;
    private int _state = 1;
    private boolean _initial = true;
    private boolean _resumed = false;
    private boolean _timeout = false;
    private boolean _responseWrapped = false;
    private long _timeoutMs = 30000;

    public FauxContinuation(ServletRequest servletRequest) {
        this._request = servletRequest;
    }

    private void fauxResume() {
        this._timeoutMs = 0L;
        notifyAll();
    }

    private void fauxSuspend() throws InterruptedException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jCurrentTimeMillis2 = this._timeoutMs;
        long j2 = jCurrentTimeMillis + jCurrentTimeMillis2;
        while (this._timeoutMs > 0 && jCurrentTimeMillis2 > 0) {
            try {
                wait(jCurrentTimeMillis2);
                jCurrentTimeMillis2 = j2 - System.currentTimeMillis();
            } catch (InterruptedException unused) {
            }
        }
        if (this._timeoutMs <= 0 || jCurrentTimeMillis2 > 0) {
            return;
        }
        expire();
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void addContinuationListener(ContinuationListener continuationListener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList<>();
        }
        this._listeners.add(continuationListener);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void complete() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    throw new IllegalStateException(getStatusString());
                case 2:
                    this._state = 4;
                    break;
                case 3:
                    break;
                case 4:
                    return;
                case 5:
                    this._state = 4;
                    fauxResume();
                    break;
                case 6:
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation
    public boolean enter(ServletResponse servletResponse) {
        this._response = servletResponse;
        return true;
    }

    @Override // org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation
    public boolean exit() {
        synchronized (this) {
            int i2 = this._state;
            if (i2 == 1) {
                this._state = 7;
                onComplete();
                return true;
            }
            if (i2 != 2) {
                if (i2 == 3) {
                    this._initial = false;
                    this._state = 1;
                    return false;
                }
                if (i2 != 4) {
                    throw new IllegalStateException(getStatusString());
                }
                this._initial = false;
                this._state = 7;
                onComplete();
                return true;
            }
            this._initial = false;
            this._state = 5;
            fauxSuspend();
            int i3 = this._state;
            if (i3 != 5 && i3 != 4) {
                this._initial = false;
                this._state = 1;
                return false;
            }
            onComplete();
            return true;
        }
    }

    public void expire() {
        synchronized (this) {
            this._timeout = true;
        }
        onTimeout();
        synchronized (this) {
            switch (this._state) {
                case 1:
                    return;
                case 2:
                    this._timeout = true;
                    this._state = 3;
                    fauxResume();
                    return;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    this._timeout = true;
                    this._state = 6;
                    return;
                case 6:
                    this._timeout = true;
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public Object getAttribute(String str) {
        return this._request.getAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public ServletResponse getServletResponse() {
        return this._response;
    }

    public String getStatusString() {
        String str;
        String string;
        synchronized (this) {
            StringBuilder sb = new StringBuilder();
            int i2 = this._state;
            if (i2 == 1) {
                str = "HANDLING";
            } else if (i2 == 2) {
                str = "SUSPENDING";
            } else if (i2 == 5) {
                str = DebugCoroutineInfoImplKt.SUSPENDED;
            } else if (i2 == 3) {
                str = "RESUMING";
            } else if (i2 == 6) {
                str = "UNSUSPENDING";
            } else if (i2 == 4) {
                str = "COMPLETING";
            } else {
                str = "???" + this._state;
            }
            sb.append(str);
            sb.append(this._initial ? ",initial" : "");
            sb.append(this._resumed ? ",resumed" : "");
            sb.append(this._timeout ? ",timeout" : "");
            string = sb.toString();
        }
        return string;
    }

    public void handling() {
        synchronized (this) {
            this._responseWrapped = false;
            switch (this._state) {
                case 1:
                    throw new IllegalStateException(getStatusString());
                case 2:
                case 3:
                    throw new IllegalStateException(getStatusString());
                case 4:
                    return;
                case 5:
                    fauxResume();
                    break;
                case 6:
                    break;
                default:
                    throw new IllegalStateException("" + this._state);
            }
            this._state = 1;
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public boolean isExpired() {
        boolean z2;
        synchronized (this) {
            z2 = this._timeout;
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
            if (i2 != 1) {
                return i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5;
            }
            return false;
        }
    }

    public void onComplete() {
        ArrayList<ContinuationListener> arrayList = this._listeners;
        if (arrayList != null) {
            Iterator<ContinuationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onComplete(this);
            }
        }
    }

    public void onTimeout() {
        ArrayList<ContinuationListener> arrayList = this._listeners;
        if (arrayList != null) {
            Iterator<ContinuationListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onTimeout(this);
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void removeAttribute(String str) {
        this._request.removeAttribute(str);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void resume() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._resumed = true;
                    return;
                case 2:
                    this._resumed = true;
                    this._state = 3;
                    return;
                case 3:
                case 4:
                    return;
                case 5:
                    fauxResume();
                    this._resumed = true;
                    this._state = 6;
                    return;
                case 6:
                    this._resumed = true;
                    return;
                default:
                    throw new IllegalStateException(getStatusString());
            }
        }
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setAttribute(String str, Object obj) {
        this._request.setAttribute(str, obj);
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void setTimeout(long j2) {
        this._timeoutMs = j2;
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend(ServletResponse servletResponse) {
        this._response = servletResponse;
        this._responseWrapped = servletResponse instanceof ServletResponseWrapper;
        suspend();
    }

    public String toString() {
        return getStatusString();
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void undispatch() {
        if (!isSuspended()) {
            throw new IllegalStateException("!suspended");
        }
        if (!ContinuationFilter.__debug) {
            throw __exception;
        }
        throw new ContinuationThrowable();
    }

    @Override // org.eclipse.jetty.continuation.Continuation
    public void suspend() {
        synchronized (this) {
            switch (this._state) {
                case 1:
                    this._timeout = false;
                    this._resumed = false;
                    this._state = 2;
                    return;
                case 2:
                case 3:
                    return;
                case 4:
                case 5:
                case 6:
                    throw new IllegalStateException(getStatusString());
                default:
                    throw new IllegalStateException("" + this._state);
            }
        }
    }
}
