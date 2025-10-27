package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.HttpVersion;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.RequestAuthCache;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.client.protocol.RequestProxyAuthentication;
import org.apache.http.client.protocol.RequestTargetAuthentication;
import org.apache.http.client.protocol.ResponseAuthCache;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.VersionInfo;

@ThreadSafe
/* loaded from: classes9.dex */
public class DefaultHttpClient extends AbstractHttpClient {
    public DefaultHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }

    public static void setDefaultHttpParams(HttpParams httpParams) throws IOException {
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "ISO-8859-1");
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        VersionInfo versionInfoLoadVersionInfo = VersionInfo.loadVersionInfo("org.apache.http.client", DefaultHttpClient.class.getClassLoader());
        HttpProtocolParams.setUserAgent(httpParams, "Apache-HttpClient/" + (versionInfoLoadVersionInfo != null ? versionInfoLoadVersionInfo.getRelease() : VersionInfo.UNAVAILABLE) + " (java 1.5)");
    }

    @Override // org.apache.http.impl.client.AbstractHttpClient
    public HttpParams createHttpParams() throws IOException {
        SyncBasicHttpParams syncBasicHttpParams = new SyncBasicHttpParams();
        setDefaultHttpParams(syncBasicHttpParams);
        return syncBasicHttpParams;
    }

    @Override // org.apache.http.impl.client.AbstractHttpClient
    public BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        basicHttpProcessor.addInterceptor(new RequestDefaultHeaders());
        basicHttpProcessor.addInterceptor(new RequestContent());
        basicHttpProcessor.addInterceptor(new RequestTargetHost());
        basicHttpProcessor.addInterceptor(new RequestClientConnControl());
        basicHttpProcessor.addInterceptor(new RequestUserAgent());
        basicHttpProcessor.addInterceptor(new RequestExpectContinue());
        basicHttpProcessor.addInterceptor(new RequestAddCookies());
        basicHttpProcessor.addInterceptor(new ResponseProcessCookies());
        basicHttpProcessor.addInterceptor(new RequestAuthCache());
        basicHttpProcessor.addInterceptor(new ResponseAuthCache());
        basicHttpProcessor.addInterceptor(new RequestTargetAuthentication());
        basicHttpProcessor.addInterceptor(new RequestProxyAuthentication());
        return basicHttpProcessor;
    }

    public DefaultHttpClient(ClientConnectionManager clientConnectionManager) {
        super(clientConnectionManager, null);
    }

    public DefaultHttpClient(HttpParams httpParams) {
        super(null, httpParams);
    }

    public DefaultHttpClient() {
        super(null, null);
    }
}
