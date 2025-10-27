package net.tsz.afinal.http;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes9.dex */
public class RetryHandler implements HttpRequestRetryHandler {
    private static final int RETRY_SLEEP_TIME_MILLIS = 1000;
    private final int maxRetries;
    private static HashSet<Class<?>> exceptionWhitelist = new HashSet<>();
    private static HashSet<Class<?>> exceptionBlacklist = new HashSet<>();

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLHandshakeException.class);
    }

    public RetryHandler(int i2) {
        this.maxRetries = i2;
    }

    @Override // org.apache.http.client.HttpRequestRetryHandler
    public boolean retryRequest(IOException iOException, int i2, HttpContext httpContext) {
        boolean z2;
        Boolean bool = (Boolean) httpContext.getAttribute(ExecutionContext.HTTP_REQ_SENT);
        if (bool != null) {
            bool.booleanValue();
        }
        if (i2 <= this.maxRetries && !exceptionBlacklist.contains(iOException.getClass())) {
            exceptionWhitelist.contains(iOException.getClass());
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            HttpUriRequest httpUriRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            z2 = (httpUriRequest == null || "POST".equals(httpUriRequest.getMethod())) ? false : true;
        }
        if (z2) {
            SystemClock.sleep(1000L);
        } else {
            iOException.printStackTrace();
        }
        return z2;
    }
}
