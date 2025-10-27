package org.apache.http.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;

/* loaded from: classes9.dex */
public final class BasicHttpProcessor implements HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, Cloneable {
    protected final List requestInterceptors = new ArrayList();
    protected final List responseInterceptors = new ArrayList();

    public final void addInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        addRequestInterceptor(httpRequestInterceptor);
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(httpRequestInterceptor);
    }

    @Override // org.apache.http.protocol.HttpResponseInterceptorList
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i2) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(i2, httpResponseInterceptor);
    }

    public void clearInterceptors() {
        clearRequestInterceptors();
        clearResponseInterceptors();
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList
    public void clearRequestInterceptors() {
        this.requestInterceptors.clear();
    }

    @Override // org.apache.http.protocol.HttpResponseInterceptorList
    public void clearResponseInterceptors() {
        this.responseInterceptors.clear();
    }

    public Object clone() throws CloneNotSupportedException {
        BasicHttpProcessor basicHttpProcessor = (BasicHttpProcessor) super.clone();
        copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    public BasicHttpProcessor copy() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    public void copyInterceptors(BasicHttpProcessor basicHttpProcessor) {
        basicHttpProcessor.requestInterceptors.clear();
        basicHttpProcessor.requestInterceptors.addAll(this.requestInterceptors);
        basicHttpProcessor.responseInterceptors.clear();
        basicHttpProcessor.responseInterceptors.addAll(this.responseInterceptors);
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList
    public HttpRequestInterceptor getRequestInterceptor(int i2) {
        if (i2 < 0 || i2 >= this.requestInterceptors.size()) {
            return null;
        }
        return (HttpRequestInterceptor) this.requestInterceptors.get(i2);
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList
    public int getRequestInterceptorCount() {
        return this.requestInterceptors.size();
    }

    @Override // org.apache.http.protocol.HttpResponseInterceptorList
    public HttpResponseInterceptor getResponseInterceptor(int i2) {
        if (i2 < 0 || i2 >= this.responseInterceptors.size()) {
            return null;
        }
        return (HttpResponseInterceptor) this.responseInterceptors.get(i2);
    }

    @Override // org.apache.http.protocol.HttpResponseInterceptorList
    public int getResponseInterceptorCount() {
        return this.responseInterceptors.size();
    }

    @Override // org.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        for (int i2 = 0; i2 < this.requestInterceptors.size(); i2++) {
            ((HttpRequestInterceptor) this.requestInterceptors.get(i2)).process(httpRequest, httpContext);
        }
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList
    public void removeRequestInterceptorByClass(Class cls) {
        Iterator it = this.requestInterceptors.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                it.remove();
            }
        }
    }

    @Override // org.apache.http.protocol.HttpResponseInterceptorList
    public void removeResponseInterceptorByClass(Class cls) {
        Iterator it = this.responseInterceptors.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                it.remove();
            }
        }
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList, org.apache.http.protocol.HttpResponseInterceptorList
    public void setInterceptors(List list) {
        if (list == null) {
            throw new IllegalArgumentException("List must not be null.");
        }
        this.requestInterceptors.clear();
        this.responseInterceptors.clear();
        for (int i2 = 0; i2 < list.size(); i2++) {
            Object obj = list.get(i2);
            if (obj instanceof HttpRequestInterceptor) {
                addInterceptor((HttpRequestInterceptor) obj);
            }
            if (obj instanceof HttpResponseInterceptor) {
                addInterceptor((HttpResponseInterceptor) obj);
            }
        }
    }

    public final void addInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i2) {
        addRequestInterceptor(httpRequestInterceptor, i2);
    }

    @Override // org.apache.http.protocol.HttpRequestInterceptorList
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i2) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(i2, httpRequestInterceptor);
    }

    @Override // org.apache.http.protocol.HttpResponseInterceptorList
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(httpResponseInterceptor);
    }

    public final void addInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        addResponseInterceptor(httpResponseInterceptor);
    }

    public final void addInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i2) {
        addResponseInterceptor(httpResponseInterceptor, i2);
    }

    @Override // org.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        for (int i2 = 0; i2 < this.responseInterceptors.size(); i2++) {
            ((HttpResponseInterceptor) this.responseInterceptors.get(i2)).process(httpResponse, httpContext);
        }
    }
}
