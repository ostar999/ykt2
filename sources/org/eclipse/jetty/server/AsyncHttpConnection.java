package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.AsyncConnection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class AsyncHttpConnection extends AbstractHttpConnection implements AsyncConnection {
    private final AsyncEndPoint _asyncEndp;
    private boolean _readInterested;
    private int _total_no_progress;
    private static final int NO_PROGRESS_INFO = Integer.getInteger("org.mortbay.jetty.NO_PROGRESS_INFO", 100).intValue();
    private static final int NO_PROGRESS_CLOSE = Integer.getInteger("org.mortbay.jetty.NO_PROGRESS_CLOSE", 200).intValue();
    private static final Logger LOG = Log.getLogger((Class<?>) AsyncHttpConnection.class);

    public AsyncHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(connector, endPoint, server);
        this._readInterested = true;
        this._asyncEndp = (AsyncEndPoint) endPoint;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:7|174|8|(5:(2:10|(1:12))(16:13|(0)(1:17)|170|19|(1:29)|30|(1:32)|33|166|34|(1:38)(1:37)|(1:(5:41|(1:45)|46|(1:50)|51)(1:52))|(2:56|57)|58|179|93)|166|34|(5:38|(0)|(3:54|56|57)|58|179)(0)|93)|18|170|19|(5:21|23|25|27|29)|30|(0)|33) */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x01de, code lost:
    
        r11 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01e3, code lost:
    
        if (r10 != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01ed, code lost:
    
        if (r17._response.getStatus() == 101) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01ef, code lost:
    
        r6 = (org.eclipse.jetty.io.Connection) r17._request.getAttribute("org.eclipse.jetty.io.Connection");
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01f7, code lost:
    
        reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0200, code lost:
    
        if (r17._generator.isPersistent() == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x020a, code lost:
    
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.warn("Safety net oshut!!!  IF YOU SEE THIS, PLEASE RAISE BUGZILLA", new java.lang.Object[0]);
        r17._endp.shutdownOutput();
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0218, code lost:
    
        r17._readInterested = false;
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug("Disabled read interest while writing response {}", r17._endp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ff, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0107, code lost:
    
        r9 = org.eclipse.jetty.server.AsyncHttpConnection.LOG;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x010d, code lost:
    
        if (r9.isDebugEnabled() != false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x010f, code lost:
    
        r9.debug("uri=" + r17._uri, new java.lang.Object[r10]);
        r9.debug("fields=" + r17._requestFields, new java.lang.Object[r10]);
        r9.debug(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0142, code lost:
    
        r17._generator.sendError(r0.getStatus(), r0.getReason(), null, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0151, code lost:
    
        r13 = r13 | 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0153, code lost:
    
        r0 = r17._parser.isComplete();
        r10 = r17._generator.isComplete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x015f, code lost:
    
        if (r0 == false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0163, code lost:
    
        r11 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0165, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0166, code lost:
    
        if (r0 != false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0168, code lost:
    
        if (r10 != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0172, code lost:
    
        if (r17._response.getStatus() == 101) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x017e, code lost:
    
        r12 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x017f, code lost:
    
        reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0188, code lost:
    
        if (r17._generator.isPersistent() == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0192, code lost:
    
        r9.warn("Safety net oshut!!!  IF YOU SEE THIS, PLEASE RAISE BUGZILLA", new java.lang.Object[0]);
        r17._endp.shutdownOutput();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x019e, code lost:
    
        r17._readInterested = false;
        r9.debug("Disabled read interest while writing response {}", r17._endp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01ab, code lost:
    
        if (r11 != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01b9, code lost:
    
        r9.debug("suspended {}", r17);
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01c4, code lost:
    
        r0 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01c9, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01ca, code lost:
    
        r15 = 1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a1  */
    /* JADX WARN: Type inference failed for: r0v44, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r0v79, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v13 */
    @Override // org.eclipse.jetty.server.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.io.Connection handle() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 906
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }

    @Override // org.eclipse.jetty.server.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return !this._readInterested || super.isSuspended();
    }

    @Override // org.eclipse.jetty.io.nio.AsyncConnection
    public void onInputShutdown() throws IOException {
        if (this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
            this._endp.close();
        }
        if (this._parser.isIdle()) {
            this._parser.setPersistent(false);
        }
    }

    @Override // org.eclipse.jetty.server.AbstractHttpConnection
    public void reset() throws IOException {
        this._readInterested = true;
        LOG.debug("Enabled read interest {}", this._endp);
        super.reset();
    }
}
