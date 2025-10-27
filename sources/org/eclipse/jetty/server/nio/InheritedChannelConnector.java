package org.eclipse.jetty.server.nio;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class InheritedChannelConnector extends SelectChannelConnector {
    private static final Logger LOG = Log.getLogger((Class<?>) InheritedChannelConnector.class);

    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[Catch: all -> 0x0048, TryCatch #0 {, blocks: (B:4:0x0002, B:6:0x000a, B:8:0x003f, B:10:0x0043, B:15:0x0053, B:17:0x0057, B:18:0x005a, B:7:0x000f, B:14:0x004a), top: B:22:0x0002, inners: #1 }] */
    @Override // org.eclipse.jetty.server.nio.SelectChannelConnector, org.eclipse.jetty.server.Connector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void open() throws java.io.IOException {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            java.nio.channels.Channel r1 = java.lang.System.inheritedChannel()     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            boolean r2 = r1 instanceof java.nio.channels.ServerSocketChannel     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            if (r2 == 0) goto Lf
            java.nio.channels.ServerSocketChannel r1 = (java.nio.channels.ServerSocketChannel) r1     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            r5._acceptChannel = r1     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            goto L3f
        Lf:
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.nio.InheritedChannelConnector.LOG     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            r3.<init>()     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.String r4 = "Unable to use System.inheritedChannel() ["
            r3.append(r4)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            r3.append(r1)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.String r1 = "]. Trying a new ServerSocketChannel at "
            r3.append(r1)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.String r1 = r5.getHost()     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            r3.append(r1)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.String r1 = ":"
            r3.append(r1)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            int r1 = r5.getPort()     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            r3.append(r1)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            r2.warn(r1, r3)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
        L3f:
            java.nio.channels.ServerSocketChannel r1 = r5._acceptChannel     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            if (r1 == 0) goto L53
            r2 = 1
            r1.configureBlocking(r2)     // Catch: java.lang.Throwable -> L48 java.lang.NoSuchMethodError -> L4a
            goto L53
        L48:
            r0 = move-exception
            goto L5c
        L4a:
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.InheritedChannelConnector.LOG     // Catch: java.lang.Throwable -> L48
            java.lang.String r2 = "Need at least Java 5 to use socket inherited from xinetd/inetd."
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L48
            r1.warn(r2, r0)     // Catch: java.lang.Throwable -> L48
        L53:
            java.nio.channels.ServerSocketChannel r0 = r5._acceptChannel     // Catch: java.lang.Throwable -> L48
            if (r0 != 0) goto L5a
            super.open()     // Catch: java.lang.Throwable -> L48
        L5a:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L48
            return
        L5c:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L48
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.nio.InheritedChannelConnector.open():void");
    }
}
