package org.eclipse.jetty.client;

import java.io.IOException;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes9.dex */
public class HttpEventListenerWrapper implements HttpEventListener {
    boolean _delegatingRequests;
    boolean _delegatingResponses;
    boolean _delegationResult;
    HttpEventListener _listener;
    private Buffer _reason;
    private int _status;
    private Buffer _version;

    public HttpEventListenerWrapper() {
        this._delegationResult = true;
        this._listener = null;
        this._delegatingRequests = false;
        this._delegatingResponses = false;
    }

    public HttpEventListener getEventListener() {
        return this._listener;
    }

    public boolean isDelegatingRequests() {
        return this._delegatingRequests;
    }

    public boolean isDelegatingResponses() {
        return this._delegatingResponses;
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onConnectionFailed(Throwable th) {
        if (this._delegatingRequests) {
            this._listener.onConnectionFailed(th);
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onException(Throwable th) {
        if (this._delegatingRequests || this._delegatingResponses) {
            this._listener.onException(th);
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onExpire() {
        if (this._delegatingRequests || this._delegatingResponses) {
            this._listener.onExpire();
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onRequestCommitted() throws IOException {
        if (this._delegatingRequests) {
            this._listener.onRequestCommitted();
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onRequestComplete() throws IOException {
        if (this._delegatingRequests) {
            this._listener.onRequestComplete();
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onResponseComplete() throws IOException {
        if (this._delegatingResponses) {
            if (!this._delegationResult) {
                this._listener.onResponseStatus(this._version, this._status, this._reason);
            }
            this._listener.onResponseComplete();
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onResponseContent(Buffer buffer) throws IOException {
        if (this._delegatingResponses) {
            this._listener.onResponseContent(buffer);
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
        if (this._delegatingResponses) {
            this._listener.onResponseHeader(buffer, buffer2);
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onResponseHeaderComplete() throws IOException {
        if (this._delegatingResponses) {
            this._listener.onResponseHeaderComplete();
        }
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onResponseStatus(Buffer buffer, int i2, Buffer buffer2) throws IOException {
        if (this._delegatingResponses) {
            this._listener.onResponseStatus(buffer, i2, buffer2);
            return;
        }
        this._version = buffer;
        this._status = i2;
        this._reason = buffer2;
    }

    @Override // org.eclipse.jetty.client.HttpEventListener
    public void onRetry() {
        if (this._delegatingRequests) {
            this._listener.onRetry();
        }
    }

    public void setDelegatingRequests(boolean z2) {
        this._delegatingRequests = z2;
    }

    public void setDelegatingResponses(boolean z2) {
        this._delegatingResponses = z2;
    }

    public void setDelegationResult(boolean z2) {
        this._delegationResult = z2;
    }

    public void setEventListener(HttpEventListener httpEventListener) {
        this._listener = httpEventListener;
    }

    public HttpEventListenerWrapper(HttpEventListener httpEventListener, boolean z2) {
        this._delegationResult = true;
        this._listener = httpEventListener;
        this._delegatingRequests = z2;
        this._delegatingResponses = z2;
    }
}
