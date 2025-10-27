package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpVersion;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.UnsupportedHttpVersionException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

/* loaded from: classes9.dex */
public class HttpService {
    private volatile ConnectionReuseStrategy connStrategy;
    private volatile HttpExpectationVerifier expectationVerifier;
    private volatile HttpRequestHandlerResolver handlerResolver;
    private volatile HttpParams params;
    private volatile HttpProcessor processor;
    private volatile HttpResponseFactory responseFactory;

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory, HttpRequestHandlerResolver httpRequestHandlerResolver, HttpExpectationVerifier httpExpectationVerifier, HttpParams httpParams) {
        this.params = null;
        this.processor = null;
        this.handlerResolver = null;
        this.connStrategy = null;
        this.responseFactory = null;
        this.expectationVerifier = null;
        if (httpProcessor == null) {
            throw new IllegalArgumentException("HTTP processor may not be null");
        }
        if (connectionReuseStrategy == null) {
            throw new IllegalArgumentException("Connection reuse strategy may not be null");
        }
        if (httpResponseFactory == null) {
            throw new IllegalArgumentException("Response factory may not be null");
        }
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        this.processor = httpProcessor;
        this.connStrategy = connectionReuseStrategy;
        this.responseFactory = httpResponseFactory;
        this.handlerResolver = httpRequestHandlerResolver;
        this.expectationVerifier = httpExpectationVerifier;
        this.params = httpParams;
    }

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IllegalStateException, IOException {
        HttpRequestHandler httpRequestHandlerLookup;
        if (this.handlerResolver != null) {
            httpRequestHandlerLookup = this.handlerResolver.lookup(httpRequest.getRequestLine().getUri());
        } else {
            httpRequestHandlerLookup = null;
        }
        if (httpRequestHandlerLookup != null) {
            httpRequestHandlerLookup.handle(httpRequest, httpResponse, httpContext);
        } else {
            httpResponse.setStatusCode(501);
        }
    }

    public HttpParams getParams() {
        return this.params;
    }

    public void handleException(HttpException httpException, HttpResponse httpResponse) throws IllegalStateException {
        if (httpException instanceof MethodNotSupportedException) {
            httpResponse.setStatusCode(501);
        } else if (httpException instanceof UnsupportedHttpVersionException) {
            httpResponse.setStatusCode(505);
        } else if (httpException instanceof ProtocolException) {
            httpResponse.setStatusCode(400);
        } else {
            httpResponse.setStatusCode(500);
        }
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(EncodingUtils.getAsciiBytes(httpException.getMessage()));
        byteArrayEntity.setContentType("text/plain; charset=US-ASCII");
        httpResponse.setEntity(byteArrayEntity);
    }

    public void handleRequest(HttpServerConnection httpServerConnection, HttpContext httpContext) throws HttpException, IllegalStateException, IOException {
        HttpResponse httpResponseNewHttpResponse;
        httpContext.setAttribute(ExecutionContext.HTTP_CONNECTION, httpServerConnection);
        try {
            HttpRequest httpRequestReceiveRequestHeader = httpServerConnection.receiveRequestHeader();
            httpRequestReceiveRequestHeader.setParams(new DefaultedHttpParams(httpRequestReceiveRequestHeader.getParams(), this.params));
            ProtocolVersion protocolVersion = httpRequestReceiveRequestHeader.getRequestLine().getProtocolVersion();
            HttpVersion httpVersion = HttpVersion.HTTP_1_1;
            if (!protocolVersion.lessEquals(httpVersion)) {
                protocolVersion = httpVersion;
            }
            httpResponseNewHttpResponse = null;
            if (httpRequestReceiveRequestHeader instanceof HttpEntityEnclosingRequest) {
                if (((HttpEntityEnclosingRequest) httpRequestReceiveRequestHeader).expectContinue()) {
                    HttpResponse httpResponseNewHttpResponse2 = this.responseFactory.newHttpResponse(protocolVersion, 100, httpContext);
                    httpResponseNewHttpResponse2.setParams(new DefaultedHttpParams(httpResponseNewHttpResponse2.getParams(), this.params));
                    if (this.expectationVerifier != null) {
                        try {
                            this.expectationVerifier.verify(httpRequestReceiveRequestHeader, httpResponseNewHttpResponse2, httpContext);
                        } catch (HttpException e2) {
                            HttpResponse httpResponseNewHttpResponse3 = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, httpContext);
                            httpResponseNewHttpResponse3.setParams(new DefaultedHttpParams(httpResponseNewHttpResponse3.getParams(), this.params));
                            handleException(e2, httpResponseNewHttpResponse3);
                            httpResponseNewHttpResponse2 = httpResponseNewHttpResponse3;
                        }
                    }
                    if (httpResponseNewHttpResponse2.getStatusLine().getStatusCode() < 200) {
                        httpServerConnection.sendResponseHeader(httpResponseNewHttpResponse2);
                        httpServerConnection.flush();
                        httpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest) httpRequestReceiveRequestHeader);
                    } else {
                        httpResponseNewHttpResponse = httpResponseNewHttpResponse2;
                    }
                } else {
                    httpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest) httpRequestReceiveRequestHeader);
                }
            }
            if (httpResponseNewHttpResponse == null) {
                httpResponseNewHttpResponse = this.responseFactory.newHttpResponse(protocolVersion, 200, httpContext);
                httpResponseNewHttpResponse.setParams(new DefaultedHttpParams(httpResponseNewHttpResponse.getParams(), this.params));
                httpContext.setAttribute(ExecutionContext.HTTP_REQUEST, httpRequestReceiveRequestHeader);
                httpContext.setAttribute(ExecutionContext.HTTP_RESPONSE, httpResponseNewHttpResponse);
                this.processor.process(httpRequestReceiveRequestHeader, httpContext);
                doService(httpRequestReceiveRequestHeader, httpResponseNewHttpResponse, httpContext);
            }
            if (httpRequestReceiveRequestHeader instanceof HttpEntityEnclosingRequest) {
                EntityUtils.consume(((HttpEntityEnclosingRequest) httpRequestReceiveRequestHeader).getEntity());
            }
        } catch (HttpException e3) {
            httpResponseNewHttpResponse = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, httpContext);
            httpResponseNewHttpResponse.setParams(new DefaultedHttpParams(httpResponseNewHttpResponse.getParams(), this.params));
            handleException(e3, httpResponseNewHttpResponse);
        }
        this.processor.process(httpResponseNewHttpResponse, httpContext);
        httpServerConnection.sendResponseHeader(httpResponseNewHttpResponse);
        httpServerConnection.sendResponseEntity(httpResponseNewHttpResponse);
        httpServerConnection.flush();
        if (this.connStrategy.keepAlive(httpResponseNewHttpResponse, httpContext)) {
            return;
        }
        httpServerConnection.close();
    }

    public void setConnReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        if (connectionReuseStrategy == null) {
            throw new IllegalArgumentException("Connection reuse strategy may not be null");
        }
        this.connStrategy = connectionReuseStrategy;
    }

    public void setExpectationVerifier(HttpExpectationVerifier httpExpectationVerifier) {
        this.expectationVerifier = httpExpectationVerifier;
    }

    public void setHandlerResolver(HttpRequestHandlerResolver httpRequestHandlerResolver) {
        this.handlerResolver = httpRequestHandlerResolver;
    }

    public void setHttpProcessor(HttpProcessor httpProcessor) {
        if (httpProcessor == null) {
            throw new IllegalArgumentException("HTTP processor may not be null");
        }
        this.processor = httpProcessor;
    }

    public void setParams(HttpParams httpParams) {
        this.params = httpParams;
    }

    public void setResponseFactory(HttpResponseFactory httpResponseFactory) {
        if (httpResponseFactory == null) {
            throw new IllegalArgumentException("Response factory may not be null");
        }
        this.responseFactory = httpResponseFactory;
    }

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory, HttpRequestHandlerResolver httpRequestHandlerResolver, HttpParams httpParams) {
        this(httpProcessor, connectionReuseStrategy, httpResponseFactory, httpRequestHandlerResolver, null, httpParams);
    }

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory) {
        this.params = null;
        this.processor = null;
        this.handlerResolver = null;
        this.connStrategy = null;
        this.responseFactory = null;
        this.expectationVerifier = null;
        setHttpProcessor(httpProcessor);
        setConnReuseStrategy(connectionReuseStrategy);
        setResponseFactory(httpResponseFactory);
    }
}
