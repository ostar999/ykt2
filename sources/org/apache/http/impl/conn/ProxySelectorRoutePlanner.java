package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;

@NotThreadSafe
/* loaded from: classes9.dex */
public class ProxySelectorRoutePlanner implements HttpRoutePlanner {
    protected ProxySelector proxySelector;
    protected final SchemeRegistry schemeRegistry;

    /* renamed from: org.apache.http.impl.conn.ProxySelectorRoutePlanner$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$net$Proxy$Type;

        static {
            int[] iArr = new int[Proxy.Type.values().length];
            $SwitchMap$java$net$Proxy$Type = iArr;
            try {
                iArr[Proxy.Type.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$net$Proxy$Type[Proxy.Type.HTTP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$net$Proxy$Type[Proxy.Type.SOCKS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public ProxySelectorRoutePlanner(SchemeRegistry schemeRegistry, ProxySelector proxySelector) {
        if (schemeRegistry == null) {
            throw new IllegalArgumentException("SchemeRegistry must not be null.");
        }
        this.schemeRegistry = schemeRegistry;
        this.proxySelector = proxySelector;
    }

    public Proxy chooseProxy(List<Proxy> list, HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Proxy list must not be empty.");
        }
        Proxy proxy = null;
        for (int i2 = 0; proxy == null && i2 < list.size(); i2++) {
            Proxy proxy2 = list.get(i2);
            int i3 = AnonymousClass1.$SwitchMap$java$net$Proxy$Type[proxy2.type().ordinal()];
            if (i3 == 1 || i3 == 2) {
                proxy = proxy2;
            }
        }
        return proxy == null ? Proxy.NO_PROXY : proxy;
    }

    public HttpHost determineProxy(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        ProxySelector proxySelector = this.proxySelector;
        if (proxySelector == null) {
            proxySelector = ProxySelector.getDefault();
        }
        if (proxySelector == null) {
            return null;
        }
        try {
            Proxy proxyChooseProxy = chooseProxy(proxySelector.select(new URI(httpHost.toURI())), httpHost, httpRequest, httpContext);
            if (proxyChooseProxy.type() != Proxy.Type.HTTP) {
                return null;
            }
            if (proxyChooseProxy.address() instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) proxyChooseProxy.address();
                return new HttpHost(getHost(inetSocketAddress), inetSocketAddress.getPort());
            }
            throw new HttpException("Unable to handle non-Inet proxy address: " + proxyChooseProxy.address());
        } catch (URISyntaxException e2) {
            throw new HttpException("Cannot convert host to URI: " + httpHost, e2);
        }
    }

    @Override // org.apache.http.conn.routing.HttpRoutePlanner
    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        if (httpRequest == null) {
            throw new IllegalStateException("Request must not be null.");
        }
        HttpRoute forcedRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
        if (forcedRoute != null) {
            return forcedRoute;
        }
        if (httpHost == null) {
            throw new IllegalStateException("Target host must not be null.");
        }
        InetAddress localAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
        HttpHost httpHostDetermineProxy = determineProxy(httpHost, httpRequest, httpContext);
        boolean zIsLayered = this.schemeRegistry.getScheme(httpHost.getSchemeName()).isLayered();
        return httpHostDetermineProxy == null ? new HttpRoute(httpHost, localAddress, zIsLayered) : new HttpRoute(httpHost, localAddress, httpHostDetermineProxy, zIsLayered);
    }

    public String getHost(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public void setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }
}
