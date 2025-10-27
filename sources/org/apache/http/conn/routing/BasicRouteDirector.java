package org.apache.http.conn.routing;

import org.apache.http.annotation.Immutable;

@Immutable
/* loaded from: classes9.dex */
public class BasicRouteDirector implements HttpRouteDirector {
    public int directStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        if (routeInfo2.getHopCount() <= 1 && routeInfo.getTargetHost().equals(routeInfo2.getTargetHost()) && routeInfo.isSecure() == routeInfo2.isSecure()) {
            return (routeInfo.getLocalAddress() == null || routeInfo.getLocalAddress().equals(routeInfo2.getLocalAddress())) ? 0 : -1;
        }
        return -1;
    }

    public int firstStep(RouteInfo routeInfo) {
        return routeInfo.getHopCount() > 1 ? 2 : 1;
    }

    @Override // org.apache.http.conn.routing.HttpRouteDirector
    public int nextStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        if (routeInfo != null) {
            return (routeInfo2 == null || routeInfo2.getHopCount() < 1) ? firstStep(routeInfo) : routeInfo.getHopCount() > 1 ? proxiedStep(routeInfo, routeInfo2) : directStep(routeInfo, routeInfo2);
        }
        throw new IllegalArgumentException("Planned route may not be null.");
    }

    public int proxiedStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        int hopCount;
        int hopCount2;
        if (routeInfo2.getHopCount() <= 1 || !routeInfo.getTargetHost().equals(routeInfo2.getTargetHost()) || (hopCount = routeInfo.getHopCount()) < (hopCount2 = routeInfo2.getHopCount())) {
            return -1;
        }
        for (int i2 = 0; i2 < hopCount2 - 1; i2++) {
            if (!routeInfo.getHopTarget(i2).equals(routeInfo2.getHopTarget(i2))) {
                return -1;
            }
        }
        if (hopCount > hopCount2) {
            return 4;
        }
        if ((routeInfo2.isTunnelled() && !routeInfo.isTunnelled()) || (routeInfo2.isLayered() && !routeInfo.isLayered())) {
            return -1;
        }
        if (routeInfo.isTunnelled() && !routeInfo2.isTunnelled()) {
            return 3;
        }
        if (!routeInfo.isLayered() || routeInfo2.isLayered()) {
            return routeInfo.isSecure() != routeInfo2.isSecure() ? -1 : 0;
        }
        return 5;
    }
}
