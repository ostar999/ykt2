package net.tsz.afinal.http;

import java.io.IOException;
import java.net.UnknownHostException;
import net.tsz.afinal.http.entityhandler.StringEntityHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes9.dex */
public class SyncRequestHandler {
    private String charset;
    private final AbstractHttpClient client;
    private final HttpContext context;
    private final StringEntityHandler entityHandler = new StringEntityHandler();
    private int executionCount = 0;

    public SyncRequestHandler(AbstractHttpClient abstractHttpClient, HttpContext httpContext, String str) {
        this.client = abstractHttpClient;
        this.context = httpContext;
        this.charset = str;
    }

    private Object makeRequestWithRetries(HttpUriRequest httpUriRequest) throws IOException {
        HttpRequestRetryHandler httpRequestRetryHandler = this.client.getHttpRequestRetryHandler();
        boolean zRetryRequest = true;
        IOException iOException = null;
        while (zRetryRequest) {
            try {
                return this.entityHandler.handleEntity(this.client.execute(httpUriRequest, this.context).getEntity(), null, this.charset);
            } catch (IOException e2) {
                iOException = e2;
                int i2 = this.executionCount + 1;
                this.executionCount = i2;
                zRetryRequest = httpRequestRetryHandler.retryRequest(iOException, i2, this.context);
            } catch (NullPointerException e3) {
                iOException = new IOException("NPE in HttpClient" + e3.getMessage());
                int i3 = this.executionCount + 1;
                this.executionCount = i3;
                zRetryRequest = httpRequestRetryHandler.retryRequest(iOException, i3, this.context);
            } catch (UnknownHostException e4) {
                iOException = e4;
                int i4 = this.executionCount + 1;
                this.executionCount = i4;
                zRetryRequest = httpRequestRetryHandler.retryRequest(iOException, i4, this.context);
            } catch (Exception e5) {
                e5.printStackTrace();
                iOException = new IOException("Exception" + e5.getMessage());
                int i5 = this.executionCount + 1;
                this.executionCount = i5;
                zRetryRequest = httpRequestRetryHandler.retryRequest(iOException, i5, this.context);
            }
        }
        if (iOException != null) {
            throw iOException;
        }
        throw new IOException("未知网络错误");
    }

    public Object sendRequest(HttpUriRequest... httpUriRequestArr) {
        try {
            return makeRequestWithRetries(httpUriRequestArr[0]);
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
