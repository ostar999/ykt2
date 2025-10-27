package org.apache.http.conn.routing;

import cn.hutool.core.text.StrPool;
import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.LangUtils;

@NotThreadSafe
/* loaded from: classes9.dex */
public final class RouteTracker implements RouteInfo, Cloneable {
    private boolean connected;
    private RouteInfo.LayerType layered;
    private final InetAddress localAddress;
    private HttpHost[] proxyChain;
    private boolean secure;
    private final HttpHost targetHost;
    private RouteInfo.TunnelType tunnelled;

    public RouteTracker(HttpHost httpHost, InetAddress inetAddress) {
        if (httpHost == null) {
            throw new IllegalArgumentException("Target host may not be null.");
        }
        this.targetHost = httpHost;
        this.localAddress = inetAddress;
        this.tunnelled = RouteInfo.TunnelType.PLAIN;
        this.layered = RouteInfo.LayerType.PLAIN;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public final void connectProxy(HttpHost httpHost, boolean z2) {
        if (httpHost == null) {
            throw new IllegalArgumentException("Proxy host may not be null.");
        }
        if (this.connected) {
            throw new IllegalStateException("Already connected.");
        }
        this.connected = true;
        this.proxyChain = new HttpHost[]{httpHost};
        this.secure = z2;
    }

    public final void connectTarget(boolean z2) {
        if (this.connected) {
            throw new IllegalStateException("Already connected.");
        }
        this.connected = true;
        this.secure = z2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RouteTracker)) {
            return false;
        }
        RouteTracker routeTracker = (RouteTracker) obj;
        return this.connected == routeTracker.connected && this.secure == routeTracker.secure && this.tunnelled == routeTracker.tunnelled && this.layered == routeTracker.layered && LangUtils.equals(this.targetHost, routeTracker.targetHost) && LangUtils.equals(this.localAddress, routeTracker.localAddress) && LangUtils.equals((Object[]) this.proxyChain, (Object[]) routeTracker.proxyChain);
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final int getHopCount() {
        if (!this.connected) {
            return 0;
        }
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr == null) {
            return 1;
        }
        return 1 + httpHostArr.length;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final HttpHost getHopTarget(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Hop index must not be negative: " + i2);
        }
        int hopCount = getHopCount();
        if (i2 < hopCount) {
            return i2 < hopCount + (-1) ? this.proxyChain[i2] : this.targetHost;
        }
        throw new IllegalArgumentException("Hop index " + i2 + " exceeds tracked route length " + hopCount + StrPool.DOT);
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final RouteInfo.LayerType getLayerType() {
        return this.layered;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final HttpHost getProxyHost() {
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr == null) {
            return null;
        }
        return httpHostArr[0];
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final RouteInfo.TunnelType getTunnelType() {
        return this.tunnelled;
    }

    public final int hashCode() {
        int iHashCode = LangUtils.hashCode(LangUtils.hashCode(17, this.targetHost), this.localAddress);
        if (this.proxyChain != null) {
            int i2 = 0;
            while (true) {
                HttpHost[] httpHostArr = this.proxyChain;
                if (i2 >= httpHostArr.length) {
                    break;
                }
                iHashCode = LangUtils.hashCode(iHashCode, httpHostArr[i2]);
                i2++;
            }
        }
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(iHashCode, this.connected), this.secure), this.tunnelled), this.layered);
    }

    public final boolean isConnected() {
        return this.connected;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final boolean isLayered() {
        return this.layered == RouteInfo.LayerType.LAYERED;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final boolean isSecure() {
        return this.secure;
    }

    @Override // org.apache.http.conn.routing.RouteInfo
    public final boolean isTunnelled() {
        return this.tunnelled == RouteInfo.TunnelType.TUNNELLED;
    }

    public final void layerProtocol(boolean z2) {
        if (!this.connected) {
            throw new IllegalStateException("No layered protocol unless connected.");
        }
        this.layered = RouteInfo.LayerType.LAYERED;
        this.secure = z2;
    }

    public final HttpRoute toRoute() {
        if (this.connected) {
            return new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder((getHopCount() * 30) + 50);
        sb.append("RouteTracker[");
        InetAddress inetAddress = this.localAddress;
        if (inetAddress != null) {
            sb.append(inetAddress);
            sb.append("->");
        }
        sb.append('{');
        if (this.connected) {
            sb.append('c');
        }
        if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
            sb.append('t');
        }
        if (this.layered == RouteInfo.LayerType.LAYERED) {
            sb.append('l');
        }
        if (this.secure) {
            sb.append('s');
        }
        sb.append("}->");
        if (this.proxyChain != null) {
            int i2 = 0;
            while (true) {
                HttpHost[] httpHostArr = this.proxyChain;
                if (i2 >= httpHostArr.length) {
                    break;
                }
                sb.append(httpHostArr[i2]);
                sb.append("->");
                i2++;
            }
        }
        sb.append(this.targetHost);
        sb.append(']');
        return sb.toString();
    }

    public final void tunnelProxy(HttpHost httpHost, boolean z2) {
        if (httpHost == null) {
            throw new IllegalArgumentException("Proxy host may not be null.");
        }
        if (!this.connected) {
            throw new IllegalStateException("No tunnel unless connected.");
        }
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr == null) {
            throw new IllegalStateException("No proxy tunnel without proxy.");
        }
        int length = httpHostArr.length + 1;
        HttpHost[] httpHostArr2 = new HttpHost[length];
        System.arraycopy(httpHostArr, 0, httpHostArr2, 0, httpHostArr.length);
        httpHostArr2[length - 1] = httpHost;
        this.proxyChain = httpHostArr2;
        this.secure = z2;
    }

    public final void tunnelTarget(boolean z2) {
        if (!this.connected) {
            throw new IllegalStateException("No tunnel unless connected.");
        }
        if (this.proxyChain == null) {
            throw new IllegalStateException("No tunnel without proxy.");
        }
        this.tunnelled = RouteInfo.TunnelType.TUNNELLED;
        this.secure = z2;
    }

    public RouteTracker(HttpRoute httpRoute) {
        this(httpRoute.getTargetHost(), httpRoute.getLocalAddress());
    }
}
