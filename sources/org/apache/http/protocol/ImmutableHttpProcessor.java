package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;

/* loaded from: classes9.dex */
public final class ImmutableHttpProcessor implements HttpProcessor {
    private final HttpRequestInterceptor[] requestInterceptors;
    private final HttpResponseInterceptor[] responseInterceptors;

    public ImmutableHttpProcessor(HttpRequestInterceptor[] httpRequestInterceptorArr, HttpResponseInterceptor[] httpResponseInterceptorArr) {
        if (httpRequestInterceptorArr != null) {
            int length = httpRequestInterceptorArr.length;
            this.requestInterceptors = new HttpRequestInterceptor[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.requestInterceptors[i2] = httpRequestInterceptorArr[i2];
            }
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorArr == null) {
            this.responseInterceptors = new HttpResponseInterceptor[0];
            return;
        }
        int length2 = httpResponseInterceptorArr.length;
        this.responseInterceptors = new HttpResponseInterceptor[length2];
        for (int i3 = 0; i3 < length2; i3++) {
            this.responseInterceptors[i3] = httpResponseInterceptorArr[i3];
        }
    }

    @Override // org.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        int i2 = 0;
        while (true) {
            HttpRequestInterceptor[] httpRequestInterceptorArr = this.requestInterceptors;
            if (i2 >= httpRequestInterceptorArr.length) {
                return;
            }
            httpRequestInterceptorArr[i2].process(httpRequest, httpContext);
            i2++;
        }
    }

    @Override // org.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        int i2 = 0;
        while (true) {
            HttpResponseInterceptor[] httpResponseInterceptorArr = this.responseInterceptors;
            if (i2 >= httpResponseInterceptorArr.length) {
                return;
            }
            httpResponseInterceptorArr[i2].process(httpResponse, httpContext);
            i2++;
        }
    }

    public ImmutableHttpProcessor(HttpRequestInterceptorList httpRequestInterceptorList, HttpResponseInterceptorList httpResponseInterceptorList) {
        if (httpRequestInterceptorList != null) {
            int requestInterceptorCount = httpRequestInterceptorList.getRequestInterceptorCount();
            this.requestInterceptors = new HttpRequestInterceptor[requestInterceptorCount];
            for (int i2 = 0; i2 < requestInterceptorCount; i2++) {
                this.requestInterceptors[i2] = httpRequestInterceptorList.getRequestInterceptor(i2);
            }
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorList != null) {
            int responseInterceptorCount = httpResponseInterceptorList.getResponseInterceptorCount();
            this.responseInterceptors = new HttpResponseInterceptor[responseInterceptorCount];
            for (int i3 = 0; i3 < responseInterceptorCount; i3++) {
                this.responseInterceptors[i3] = httpResponseInterceptorList.getResponseInterceptor(i3);
            }
            return;
        }
        this.responseInterceptors = new HttpResponseInterceptor[0];
    }

    public ImmutableHttpProcessor(HttpRequestInterceptor[] httpRequestInterceptorArr) {
        this(httpRequestInterceptorArr, (HttpResponseInterceptor[]) null);
    }

    public ImmutableHttpProcessor(HttpResponseInterceptor[] httpResponseInterceptorArr) {
        this((HttpRequestInterceptor[]) null, httpResponseInterceptorArr);
    }
}
