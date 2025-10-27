package okhttp3.internal.http;

import cn.hutool.core.lang.RegexPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J(\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\u0018\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lokhttp3/internal/http/RetryAndFollowUpInterceptor;", "Lokhttp3/Interceptor;", "client", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "buildRedirectRequest", "Lokhttp3/Request;", "userResponse", "Lokhttp3/Response;", "method", "", "followUpRequest", "exchange", "Lokhttp3/internal/connection/Exchange;", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "isRecoverable", "", AliyunLogKey.KEY_EVENT, "Ljava/io/IOException;", "requestSendStarted", "recover", "call", "Lokhttp3/internal/connection/RealCall;", "userRequest", "requestIsOneShot", "retryAfter", "", "defaultDelay", "Companion", "okhttp"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class RetryAndFollowUpInterceptor implements Interceptor {
    private static final int MAX_FOLLOW_UPS = 20;

    @NotNull
    private final OkHttpClient client;

    public RetryAndFollowUpInterceptor(@NotNull OkHttpClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        this.client = client;
    }

    private final Request buildRedirectRequest(Response userResponse, String method) {
        String strHeader$default;
        HttpUrl httpUrlResolve;
        if (!this.client.followRedirects() || (strHeader$default = Response.header$default(userResponse, "Location", null, 2, null)) == null || (httpUrlResolve = userResponse.request().url().resolve(strHeader$default)) == null) {
            return null;
        }
        if (!Intrinsics.areEqual(httpUrlResolve.scheme(), userResponse.request().url().scheme()) && !this.client.followSslRedirects()) {
            return null;
        }
        Request.Builder builderNewBuilder = userResponse.request().newBuilder();
        if (HttpMethod.permitsRequestBody(method)) {
            int iCode = userResponse.code();
            HttpMethod httpMethod = HttpMethod.INSTANCE;
            boolean z2 = httpMethod.redirectsWithBody(method) || iCode == 308 || iCode == 307;
            if (!httpMethod.redirectsToGet(method) || iCode == 308 || iCode == 307) {
                builderNewBuilder.method(method, z2 ? userResponse.request().body() : null);
            } else {
                builderNewBuilder.method("GET", null);
            }
            if (!z2) {
                builderNewBuilder.removeHeader("Transfer-Encoding");
                builderNewBuilder.removeHeader("Content-Length");
                builderNewBuilder.removeHeader("Content-Type");
            }
        }
        if (!Util.canReuseConnectionFor(userResponse.request().url(), httpUrlResolve)) {
            builderNewBuilder.removeHeader("Authorization");
        }
        return builderNewBuilder.url(httpUrlResolve).build();
    }

    private final Request followUpRequest(Response userResponse, Exchange exchange) throws IOException {
        RealConnection connection;
        Route route = (exchange == null || (connection = exchange.getConnection()) == null) ? null : connection.getRoute();
        int iCode = userResponse.code();
        String strMethod = userResponse.request().method();
        if (iCode != 307 && iCode != 308) {
            if (iCode == 401) {
                return this.client.authenticator().authenticate(route, userResponse);
            }
            if (iCode == 421) {
                RequestBody requestBodyBody = userResponse.request().body();
                if ((requestBodyBody != null && requestBodyBody.isOneShot()) || exchange == null || !exchange.isCoalescedConnection$okhttp()) {
                    return null;
                }
                exchange.getConnection().noCoalescedConnections$okhttp();
                return userResponse.request();
            }
            if (iCode == 503) {
                Response responsePriorResponse = userResponse.priorResponse();
                if ((responsePriorResponse == null || responsePriorResponse.code() != 503) && retryAfter(userResponse, Integer.MAX_VALUE) == 0) {
                    return userResponse.request();
                }
                return null;
            }
            if (iCode == 407) {
                Intrinsics.checkNotNull(route);
                if (route.proxy().type() == Proxy.Type.HTTP) {
                    return this.client.proxyAuthenticator().authenticate(route, userResponse);
                }
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            }
            if (iCode == 408) {
                if (!this.client.retryOnConnectionFailure()) {
                    return null;
                }
                RequestBody requestBodyBody2 = userResponse.request().body();
                if (requestBodyBody2 != null && requestBodyBody2.isOneShot()) {
                    return null;
                }
                Response responsePriorResponse2 = userResponse.priorResponse();
                if ((responsePriorResponse2 == null || responsePriorResponse2.code() != 408) && retryAfter(userResponse, 0) <= 0) {
                    return userResponse.request();
                }
                return null;
            }
            switch (iCode) {
                case 300:
                case 301:
                case 302:
                case 303:
                    break;
                default:
                    return null;
            }
        }
        return buildRedirectRequest(userResponse, strMethod);
    }

    private final boolean isRecoverable(IOException e2, boolean requestSendStarted) {
        if (e2 instanceof ProtocolException) {
            return false;
        }
        return e2 instanceof InterruptedIOException ? (e2 instanceof SocketTimeoutException) && !requestSendStarted : (((e2 instanceof SSLHandshakeException) && (e2.getCause() instanceof CertificateException)) || (e2 instanceof SSLPeerUnverifiedException)) ? false : true;
    }

    private final boolean recover(IOException e2, RealCall call, Request userRequest, boolean requestSendStarted) {
        if (this.client.retryOnConnectionFailure()) {
            return !(requestSendStarted && requestIsOneShot(e2, userRequest)) && isRecoverable(e2, requestSendStarted) && call.retryAfterFailure();
        }
        return false;
    }

    private final boolean requestIsOneShot(IOException e2, Request userRequest) {
        RequestBody requestBodyBody = userRequest.body();
        return (requestBodyBody != null && requestBodyBody.isOneShot()) || (e2 instanceof FileNotFoundException);
    }

    private final int retryAfter(Response userResponse, int defaultDelay) throws NumberFormatException {
        String strHeader$default = Response.header$default(userResponse, "Retry-After", null, 2, null);
        if (strHeader$default == null) {
            return defaultDelay;
        }
        if (!new Regex(RegexPool.NUMBERS).matches(strHeader$default)) {
            return Integer.MAX_VALUE;
        }
        Integer numValueOf = Integer.valueOf(strHeader$default);
        Intrinsics.checkNotNullExpressionValue(numValueOf, "valueOf(header)");
        return numValueOf.intValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0040, code lost:
    
        r7 = r0;
        r0 = r1.getInterceptorScopedExchange();
        r6 = followUpRequest(r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0049, code lost:
    
        if (r6 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004b, code lost:
    
        if (r0 == null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0051, code lost:
    
        if (r0.getIsDuplex() == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0053, code lost:
    
        r1.timeoutEarlyExit();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0056, code lost:
    
        r1.exitNetworkInterceptorExchange$okhttp(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0059, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005a, code lost:
    
        r0 = r6.body();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005e, code lost:
    
        if (r0 == null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
    
        if (r0.isOneShot() == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0066, code lost:
    
        r1.exitNetworkInterceptorExchange$okhttp(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0069, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006a, code lost:
    
        r0 = r7.body();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:
    
        if (r0 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
    
        okhttp3.internal.Util.closeQuietly(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0074, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0078, code lost:
    
        if (r8 > 20) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008e, code lost:
    
        throw new java.net.ProtocolException(kotlin.jvm.internal.Intrinsics.stringPlus("Too many follow-up requests: ", java.lang.Integer.valueOf(r8)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
    
        if (r7 == null) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0028, code lost:
    
        r0 = r0.newBuilder().priorResponse(r7.newBuilder().body(null).build()).build();
     */
    @Override // okhttp3.Interceptor
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull okhttp3.Interceptor.Chain r11) throws java.io.IOException {
        /*
            r10 = this;
            java.lang.String r0 = "chain"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            okhttp3.internal.http.RealInterceptorChain r11 = (okhttp3.internal.http.RealInterceptorChain) r11
            okhttp3.Request r0 = r11.getRequest()
            okhttp3.internal.connection.RealCall r1 = r11.getCall()
            java.util.List r2 = kotlin.collections.CollectionsKt.emptyList()
            r3 = 0
            r4 = 0
            r5 = 1
            r8 = r3
            r7 = r4
        L18:
            r6 = r5
        L19:
            r1.enterNetworkInterceptorExchange(r0, r6)
            boolean r6 = r1.getCanceled()     // Catch: java.lang.Throwable -> Ld5
            if (r6 != 0) goto Lcd
            okhttp3.Response r0 = r11.proceed(r0)     // Catch: java.io.IOException -> L8f okhttp3.internal.connection.RouteException -> La9 java.lang.Throwable -> Ld5
            if (r7 == 0) goto L40
            okhttp3.Response$Builder r0 = r0.newBuilder()     // Catch: java.lang.Throwable -> Ld5
            okhttp3.Response$Builder r6 = r7.newBuilder()     // Catch: java.lang.Throwable -> Ld5
            okhttp3.Response$Builder r6 = r6.body(r4)     // Catch: java.lang.Throwable -> Ld5
            okhttp3.Response r6 = r6.build()     // Catch: java.lang.Throwable -> Ld5
            okhttp3.Response$Builder r0 = r0.priorResponse(r6)     // Catch: java.lang.Throwable -> Ld5
            okhttp3.Response r0 = r0.build()     // Catch: java.lang.Throwable -> Ld5
        L40:
            r7 = r0
            okhttp3.internal.connection.Exchange r0 = r1.getInterceptorScopedExchange()     // Catch: java.lang.Throwable -> Ld5
            okhttp3.Request r6 = r10.followUpRequest(r7, r0)     // Catch: java.lang.Throwable -> Ld5
            if (r6 != 0) goto L5a
            if (r0 == 0) goto L56
            boolean r11 = r0.getIsDuplex()     // Catch: java.lang.Throwable -> Ld5
            if (r11 == 0) goto L56
            r1.timeoutEarlyExit()     // Catch: java.lang.Throwable -> Ld5
        L56:
            r1.exitNetworkInterceptorExchange$okhttp(r3)
            return r7
        L5a:
            okhttp3.RequestBody r0 = r6.body()     // Catch: java.lang.Throwable -> Ld5
            if (r0 == 0) goto L6a
            boolean r0 = r0.isOneShot()     // Catch: java.lang.Throwable -> Ld5
            if (r0 == 0) goto L6a
            r1.exitNetworkInterceptorExchange$okhttp(r3)
            return r7
        L6a:
            okhttp3.ResponseBody r0 = r7.body()     // Catch: java.lang.Throwable -> Ld5
            if (r0 != 0) goto L71
            goto L74
        L71:
            okhttp3.internal.Util.closeQuietly(r0)     // Catch: java.lang.Throwable -> Ld5
        L74:
            int r8 = r8 + 1
            r0 = 20
            if (r8 > r0) goto L7f
            r1.exitNetworkInterceptorExchange$okhttp(r5)
            r0 = r6
            goto L18
        L7f:
            java.net.ProtocolException r11 = new java.net.ProtocolException     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r0 = "Too many follow-up requests: "
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r0 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r2)     // Catch: java.lang.Throwable -> Ld5
            r11.<init>(r0)     // Catch: java.lang.Throwable -> Ld5
            throw r11     // Catch: java.lang.Throwable -> Ld5
        L8f:
            r6 = move-exception
            boolean r9 = r6 instanceof okhttp3.internal.http2.ConnectionShutdownException     // Catch: java.lang.Throwable -> Ld5
            if (r9 != 0) goto L96
            r9 = r5
            goto L97
        L96:
            r9 = r3
        L97:
            boolean r9 = r10.recover(r6, r1, r0, r9)     // Catch: java.lang.Throwable -> Ld5
            if (r9 == 0) goto La4
            java.util.Collection r2 = (java.util.Collection) r2     // Catch: java.lang.Throwable -> Ld5
            java.util.List r2 = kotlin.collections.CollectionsKt.plus(r2, r6)     // Catch: java.lang.Throwable -> Ld5
            goto Lbe
        La4:
            java.lang.Throwable r11 = okhttp3.internal.Util.withSuppressed(r6, r2)     // Catch: java.lang.Throwable -> Ld5
            throw r11     // Catch: java.lang.Throwable -> Ld5
        La9:
            r6 = move-exception
            java.io.IOException r9 = r6.getLastConnectException()     // Catch: java.lang.Throwable -> Ld5
            boolean r9 = r10.recover(r9, r1, r0, r3)     // Catch: java.lang.Throwable -> Ld5
            if (r9 == 0) goto Lc4
            java.util.Collection r2 = (java.util.Collection) r2     // Catch: java.lang.Throwable -> Ld5
            java.io.IOException r6 = r6.getFirstConnectException()     // Catch: java.lang.Throwable -> Ld5
            java.util.List r2 = kotlin.collections.CollectionsKt.plus(r2, r6)     // Catch: java.lang.Throwable -> Ld5
        Lbe:
            r1.exitNetworkInterceptorExchange$okhttp(r5)
            r6 = r3
            goto L19
        Lc4:
            java.io.IOException r11 = r6.getFirstConnectException()     // Catch: java.lang.Throwable -> Ld5
            java.lang.Throwable r11 = okhttp3.internal.Util.withSuppressed(r11, r2)     // Catch: java.lang.Throwable -> Ld5
            throw r11     // Catch: java.lang.Throwable -> Ld5
        Lcd:
            java.io.IOException r11 = new java.io.IOException     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r0 = "Canceled"
            r11.<init>(r0)     // Catch: java.lang.Throwable -> Ld5
            throw r11     // Catch: java.lang.Throwable -> Ld5
        Ld5:
            r11 = move-exception
            r1.exitNetworkInterceptorExchange$okhttp(r5)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }
}
