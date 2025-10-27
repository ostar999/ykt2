package org.eclipse.jetty.client;

import java.io.IOException;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.AsyncConnection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class AsyncHttpConnection extends AbstractHttpConnection implements AsyncConnection {
    private static final Logger LOG = Log.getLogger((Class<?>) AsyncHttpConnection.class);
    private final AsyncEndPoint _asyncEndp;
    private boolean _requestComplete;
    private Buffer _requestContentChunk;

    public AsyncHttpConnection(Buffers buffers, Buffers buffers2, EndPoint endPoint) {
        super(buffers, buffers2, endPoint);
        this._asyncEndp = (AsyncEndPoint) endPoint;
    }

    /* JADX WARN: Code restructure failed: missing block: B:136:0x0226, code lost:
    
        r7.debug("finally {} on {} progress={} {}", r8, r16, java.lang.Boolean.valueOf(r13), r16._endp);
        r16._generator.setPersistent(false);
        reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0243, code lost:
    
        monitor-enter(r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0244, code lost:
    
        r0 = r16._exchange;
        r16._exchange = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0248, code lost:
    
        if (r0 == null) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x024a, code lost:
    
        r0.cancelTimeout(r16._destination.getHttpClient());
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0255, code lost:
    
        if (r16._status != 101) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0257, code lost:
    
        r0 = r0.onSwitchProtocol(r16._endp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x025d, code lost:
    
        if (r0 == 0) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x025f, code lost:
    
        r4 = r16._pipeline;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0261, code lost:
    
        if (r4 == null) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0263, code lost:
    
        r16._destination.send(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0268, code lost:
    
        r16._pipeline = null;
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x026b, code lost:
    
        r0 = r16._pipeline;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x026d, code lost:
    
        if (r0 == null) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x026f, code lost:
    
        r16._destination.send(r0);
        r16._pipeline = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0278, code lost:
    
        if (r16._exchange != null) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x027e, code lost:
    
        if (isReserved() != false) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0280, code lost:
    
        r16._destination.returnConnection(r16, true);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01fe  */
    /* JADX WARN: Type inference failed for: r0v26, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r7v23, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r7v9, types: [org.eclipse.jetty.io.Connection] */
    @Override // org.eclipse.jetty.client.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.io.Connection handle() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 923
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.AsyncHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }

    @Override // org.eclipse.jetty.io.nio.AsyncConnection
    public void onInputShutdown() throws IOException {
        if (this._generator.isIdle()) {
            this._endp.shutdownOutput();
        }
    }

    @Override // org.eclipse.jetty.client.AbstractHttpConnection
    public void reset() throws IOException {
        this._requestComplete = false;
        super.reset();
    }

    @Override // org.eclipse.jetty.client.AbstractHttpConnection
    public boolean send(HttpExchange httpExchange) throws IOException {
        boolean zSend = super.send(httpExchange);
        if (zSend) {
            this._asyncEndp.asyncDispatch();
        }
        return zSend;
    }
}
