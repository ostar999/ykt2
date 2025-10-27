package org.eclipse.jetty.server.handler;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.IPAddressMap;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class IPAccessHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger((Class<?>) IPAccessHandler.class);
    IPAddressMap<PathMap> _white = new IPAddressMap<>();
    IPAddressMap<PathMap> _black = new IPAddressMap<>();

    public IPAccessHandler() {
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void add(java.lang.String r7, org.eclipse.jetty.util.IPAddressMap<org.eclipse.jetty.http.PathMap> r8) throws java.lang.IllegalArgumentException {
        /*
            r6 = this;
            if (r7 == 0) goto L8f
            int r0 = r7.length()
            if (r0 <= 0) goto L8f
            r0 = 124(0x7c, float:1.74E-43)
            int r1 = r7.indexOf(r0)
            r2 = 1
            r3 = 0
            if (r1 <= 0) goto L18
            int r0 = r7.indexOf(r0)
        L16:
            r1 = r3
            goto L21
        L18:
            r0 = 47
            int r0 = r7.indexOf(r0)
            if (r0 < 0) goto L16
            r1 = r2
        L21:
            if (r0 <= 0) goto L28
            java.lang.String r4 = r7.substring(r3, r0)
            goto L29
        L28:
            r4 = r7
        L29:
            if (r0 <= 0) goto L30
            java.lang.String r0 = r7.substring(r0)
            goto L32
        L30:
            java.lang.String r0 = "/*"
        L32:
            java.lang.String r5 = "."
            boolean r5 = r4.endsWith(r5)
            if (r5 == 0) goto L3b
            r1 = r2
        L3b:
            if (r0 == 0) goto L51
            java.lang.String r5 = "|"
            boolean r5 = r0.startsWith(r5)
            if (r5 != 0) goto L4d
            java.lang.String r5 = "/*."
            boolean r5 = r0.startsWith(r5)
            if (r5 == 0) goto L51
        L4d:
            java.lang.String r0 = r0.substring(r2)
        L51:
            java.lang.Object r5 = r8.get(r4)
            org.eclipse.jetty.http.PathMap r5 = (org.eclipse.jetty.http.PathMap) r5
            if (r5 != 0) goto L61
            org.eclipse.jetty.http.PathMap r5 = new org.eclipse.jetty.http.PathMap
            r5.<init>(r2)
            r8.put(r4, r5)
        L61:
            if (r0 == 0) goto L6e
            java.lang.String r8 = ""
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L6e
            r5.put(r0, r0)
        L6e:
            if (r1 == 0) goto L8f
            org.eclipse.jetty.util.log.Logger r8 = org.eclipse.jetty.server.handler.IPAccessHandler.LOG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r1 = " - deprecated specification syntax: "
            r0.append(r1)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            java.lang.Object[] r0 = new java.lang.Object[r3]
            r8.debug(r7, r0)
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.IPAccessHandler.add(java.lang.String, org.eclipse.jetty.util.IPAddressMap):void");
    }

    public void addBlack(String str) throws IllegalArgumentException {
        add(str, this._black);
    }

    public void addWhite(String str) throws IllegalArgumentException {
        add(str, this._white);
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        if (LOG.isDebugEnabled()) {
            System.err.println(dump());
        }
    }

    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        sb.append(" WHITELIST:\n");
        dump(sb, this._white);
        sb.append(toString());
        sb.append(" BLACKLIST:\n");
        dump(sb, this._black);
        return sb.toString();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        EndPoint endPoint;
        String remoteAddr;
        AbstractHttpConnection connection = request.getConnection();
        if (connection == null || (endPoint = connection.getEndPoint()) == null || (remoteAddr = endPoint.getRemoteAddr()) == null || isAddrUriAllowed(remoteAddr, request.getPathInfo())) {
            getHandler().handle(str, request, httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendError(403);
            request.setHandled(true);
        }
    }

    public boolean isAddrUriAllowed(String str, String str2) {
        Object lazyMatches;
        boolean z2;
        if (this._white.size() > 0) {
            Object lazyMatches2 = this._white.getLazyMatches(str);
            if (lazyMatches2 != null) {
                Iterator it = (lazyMatches2 instanceof List ? (List) lazyMatches2 : Collections.singletonList(lazyMatches2)).iterator();
                z2 = false;
                while (it.hasNext()) {
                    PathMap pathMap = (PathMap) ((Map.Entry) it.next()).getValue();
                    z2 = pathMap != null && (pathMap.size() == 0 || pathMap.match(str2) != null);
                    if (z2) {
                        break;
                    }
                }
            } else {
                z2 = false;
            }
            if (!z2) {
                return false;
            }
        }
        if (this._black.size() > 0 && (lazyMatches = this._black.getLazyMatches(str)) != null) {
            Iterator it2 = (lazyMatches instanceof List ? (List) lazyMatches : Collections.singletonList(lazyMatches)).iterator();
            while (it2.hasNext()) {
                PathMap pathMap2 = (PathMap) ((Map.Entry) it2.next()).getValue();
                if (pathMap2 != null && (pathMap2.size() == 0 || pathMap2.match(str2) != null)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void set(String[] strArr, IPAddressMap<PathMap> iPAddressMap) throws IllegalArgumentException {
        iPAddressMap.clear();
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        for (String str : strArr) {
            add(str, iPAddressMap);
        }
    }

    public void setBlack(String[] strArr) throws IllegalArgumentException {
        set(strArr, this._black);
    }

    public void setWhite(String[] strArr) throws IllegalArgumentException {
        set(strArr, this._white);
    }

    public IPAccessHandler(String[] strArr, String[] strArr2) throws IllegalArgumentException {
        if (strArr != null && strArr.length > 0) {
            setWhite(strArr);
        }
        if (strArr2 == null || strArr2.length <= 0) {
            return;
        }
        setBlack(strArr2);
    }

    public void dump(StringBuilder sb, IPAddressMap<PathMap> iPAddressMap) {
        for (String str : iPAddressMap.keySet()) {
            for (Object obj : iPAddressMap.get(str).values()) {
                sb.append("# ");
                sb.append(str);
                sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
                sb.append(obj);
                sb.append("\n");
            }
        }
    }
}
