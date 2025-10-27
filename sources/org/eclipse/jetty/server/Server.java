package org.eclipse.jetty.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Container;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ShutdownThread;
import org.eclipse.jetty.util.thread.ThreadPool;

/* loaded from: classes9.dex */
public class Server extends HandlerWrapper implements Attributes {
    private static final Logger LOG = Log.getLogger((Class<?>) Server.class);
    private static final String __version;
    private Connector[] _connectors;
    private SessionIdManager _sessionIdManager;
    private boolean _stopAtShutdown;
    private ThreadPool _threadPool;
    private final Container _container = new Container();
    private final AttributesMap _attributes = new AttributesMap();
    private boolean _sendServerVersion = true;
    private boolean _sendDateHeader = false;
    private int _graceful = 0;
    private boolean _dumpAfterStart = false;
    private boolean _dumpBeforeStop = false;
    private boolean _uncheckedPrintWriter = false;

    public interface Graceful extends Handler {
        void setShutdown(boolean z2);
    }

    static {
        if (Server.class.getPackage() == null || !"Eclipse.org - Jetty".equals(Server.class.getPackage().getImplementationVendor()) || Server.class.getPackage().getImplementationVersion() == null) {
            __version = System.getProperty("jetty.version", "8.y.z-SNAPSHOT");
        } else {
            __version = Server.class.getPackage().getImplementationVersion();
        }
    }

    public Server() {
        setServer(this);
    }

    public static String getVersion() {
        return __version;
    }

    public static void main(String... strArr) throws Exception {
        System.err.println(getVersion());
    }

    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle
    public boolean addBean(Object obj) {
        if (!super.addBean(obj)) {
            return false;
        }
        this._container.addBean(obj);
        return true;
    }

    public void addConnector(Connector connector) {
        setConnectors((Connector[]) LazyList.addToArray(getConnectors(), connector, Connector.class));
    }

    @Deprecated
    public void addLifeCycle(LifeCycle lifeCycle) {
        addBean(lifeCycle);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void clearAttributes() {
        this._attributes.clearAttributes();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        int i2 = 0;
        if (getStopAtShutdown()) {
            ShutdownThread.register(this);
        }
        ShutdownMonitor.getInstance().start();
        Logger logger = LOG;
        StringBuilder sb = new StringBuilder();
        sb.append("jetty-");
        String str = __version;
        sb.append(str);
        logger.info(sb.toString(), new Object[0]);
        HttpGenerator.setServerVersion(str);
        MultiException multiException = new MultiException();
        if (this._threadPool == null) {
            setThreadPool(new QueuedThreadPool());
        }
        try {
            super.doStart();
        } catch (Throwable th) {
            multiException.add(th);
        }
        if (this._connectors != null && multiException.size() == 0) {
            while (true) {
                Connector[] connectorArr = this._connectors;
                if (i2 >= connectorArr.length) {
                    break;
                }
                try {
                    connectorArr[i2].start();
                } catch (Throwable th2) {
                    multiException.add(th2);
                }
                i2++;
            }
        }
        if (isDumpAfterStart()) {
            dumpStdErr();
        }
        multiException.ifExceptionThrow();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|2|(1:4)|5|(6:7|(2:9|(2:10|(5:12|41|13|49|17)(1:47)))(0)|18|(2:21|19)|50|22)|23|(5:25|(2:26|(4:45|28|52|32)(0))|33|37|(2:39|40)(1:53))(0)|43|33|37|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0078, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0079, code lost:
    
        r0.add(r1);
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStop() throws java.lang.Exception {
        /*
            r9 = this;
            boolean r0 = r9.isDumpBeforeStop()
            if (r0 == 0) goto L9
            r9.dumpStdErr()
        L9:
            org.eclipse.jetty.util.MultiException r0 = new org.eclipse.jetty.util.MultiException
            r0.<init>()
            int r1 = r9._graceful
            if (r1 <= 0) goto L5d
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors
            java.lang.String r2 = "Graceful shutdown {}"
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L3a
            int r1 = r1.length
        L1b:
            int r5 = r1 + (-1)
            if (r1 <= 0) goto L3a
            org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.Server.LOG
            java.lang.Object[] r6 = new java.lang.Object[r4]
            org.eclipse.jetty.server.Connector[] r7 = r9._connectors
            r7 = r7[r5]
            r6[r3] = r7
            r1.info(r2, r6)
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors     // Catch: java.lang.Throwable -> L34
            r1 = r1[r5]     // Catch: java.lang.Throwable -> L34
            r1.close()     // Catch: java.lang.Throwable -> L34
            goto L38
        L34:
            r1 = move-exception
            r0.add(r1)
        L38:
            r1 = r5
            goto L1b
        L3a:
            java.lang.Class<org.eclipse.jetty.server.Server$Graceful> r1 = org.eclipse.jetty.server.Server.Graceful.class
            org.eclipse.jetty.server.Handler[] r1 = r9.getChildHandlersByClass(r1)
            r5 = r3
        L41:
            int r6 = r1.length
            if (r5 >= r6) goto L57
            r6 = r1[r5]
            org.eclipse.jetty.server.Server$Graceful r6 = (org.eclipse.jetty.server.Server.Graceful) r6
            org.eclipse.jetty.util.log.Logger r7 = org.eclipse.jetty.server.Server.LOG
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r3] = r6
            r7.info(r2, r8)
            r6.setShutdown(r4)
            int r5 = r5 + 1
            goto L41
        L57:
            int r1 = r9._graceful
            long r1 = (long) r1
            java.lang.Thread.sleep(r1)
        L5d:
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors
            if (r1 == 0) goto L74
            int r1 = r1.length
        L62:
            int r2 = r1 + (-1)
            if (r1 <= 0) goto L74
            org.eclipse.jetty.server.Connector[] r1 = r9._connectors     // Catch: java.lang.Throwable -> L6e
            r1 = r1[r2]     // Catch: java.lang.Throwable -> L6e
            r1.stop()     // Catch: java.lang.Throwable -> L6e
            goto L72
        L6e:
            r1 = move-exception
            r0.add(r1)
        L72:
            r1 = r2
            goto L62
        L74:
            super.doStop()     // Catch: java.lang.Throwable -> L78
            goto L7c
        L78:
            r1 = move-exception
            r0.add(r1)
        L7c:
            r0.ifExceptionThrow()
            boolean r0 = r9.getStopAtShutdown()
            if (r0 == 0) goto L88
            org.eclipse.jetty.util.thread.ShutdownThread.deregister(r9)
        L88:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.Server.doStop():void");
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(getHandlers()), getBeans(), TypeUtil.asList(this._connectors));
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Object getAttribute(String str) {
        return this._attributes.getAttribute(str);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Enumeration getAttributeNames() {
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Connector[] getConnectors() {
        return this._connectors;
    }

    public Container getContainer() {
        return this._container;
    }

    public int getGracefulShutdown() {
        return this._graceful;
    }

    @Deprecated
    public int getMaxCookieVersion() {
        return 1;
    }

    public boolean getSendDateHeader() {
        return this._sendDateHeader;
    }

    public boolean getSendServerVersion() {
        return this._sendServerVersion;
    }

    public SessionIdManager getSessionIdManager() {
        return this._sessionIdManager;
    }

    public boolean getStopAtShutdown() {
        return this._stopAtShutdown;
    }

    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    public void handle(AbstractHttpConnection abstractHttpConnection) throws ServletException, IOException {
        String pathInfo = abstractHttpConnection.getRequest().getPathInfo();
        Request request = abstractHttpConnection.getRequest();
        Response response = abstractHttpConnection.getResponse();
        Logger logger = LOG;
        if (!logger.isDebugEnabled()) {
            handle(pathInfo, request, request, response);
            return;
        }
        logger.debug("REQUEST " + pathInfo + " on " + abstractHttpConnection, new Object[0]);
        handle(pathInfo, request, request, response);
        logger.debug("RESPONSE " + pathInfo + "  " + abstractHttpConnection.getResponse().getStatus() + " handled=" + request.isHandled(), new Object[0]);
    }

    public void handleAsync(AbstractHttpConnection abstractHttpConnection) throws ServletException, IOException {
        AsyncContinuation asyncContinuation = abstractHttpConnection.getRequest().getAsyncContinuation();
        AsyncContinuation.AsyncEventState asyncEventState = asyncContinuation.getAsyncEventState();
        Request request = abstractHttpConnection.getRequest();
        String path = asyncEventState.getPath();
        if (path != null) {
            HttpURI httpURI = new HttpURI(URIUtil.addPaths(asyncEventState.getServletContext().getContextPath(), path));
            request.setUri(httpURI);
            request.setRequestURI(null);
            request.setPathInfo(request.getRequestURI());
            if (httpURI.getQuery() != null) {
                request.mergeQueryString(httpURI.getQuery());
            }
        }
        String pathInfo = request.getPathInfo();
        HttpServletRequest httpServletRequest = (HttpServletRequest) asyncContinuation.getRequest();
        HttpServletResponse httpServletResponse = (HttpServletResponse) asyncContinuation.getResponse();
        Logger logger = LOG;
        if (!logger.isDebugEnabled()) {
            handle(pathInfo, request, httpServletRequest, httpServletResponse);
            return;
        }
        logger.debug("REQUEST " + pathInfo + " on " + abstractHttpConnection, new Object[0]);
        handle(pathInfo, request, httpServletRequest, httpServletResponse);
        logger.debug("RESPONSE " + pathInfo + "  " + abstractHttpConnection.getResponse().getStatus(), new Object[0]);
    }

    public boolean isDumpAfterStart() {
        return this._dumpAfterStart;
    }

    public boolean isDumpBeforeStop() {
        return this._dumpBeforeStop;
    }

    public boolean isUncheckedPrintWriter() {
        return this._uncheckedPrintWriter;
    }

    public void join() throws InterruptedException {
        getThreadPool().join();
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void removeAttribute(String str) {
        this._attributes.removeAttribute(str);
    }

    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle
    public boolean removeBean(Object obj) {
        if (!super.removeBean(obj)) {
            return false;
        }
        this._container.removeBean(obj);
        return true;
    }

    public void removeConnector(Connector connector) {
        setConnectors((Connector[]) LazyList.removeFromArray(getConnectors(), connector));
    }

    @Deprecated
    public void removeLifeCycle(LifeCycle lifeCycle) {
        removeBean(lifeCycle);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void setAttribute(String str, Object obj) {
        this._attributes.setAttribute(str, obj);
    }

    public void setConnectors(Connector[] connectorArr) {
        if (connectorArr != null) {
            for (Connector connector : connectorArr) {
                connector.setServer(this);
            }
        }
        this._container.update((Object) this, (Object[]) this._connectors, (Object[]) connectorArr, "connector");
        this._connectors = connectorArr;
    }

    public void setDumpAfterStart(boolean z2) {
        this._dumpAfterStart = z2;
    }

    public void setDumpBeforeStop(boolean z2) {
        this._dumpBeforeStop = z2;
    }

    public void setGracefulShutdown(int i2) {
        this._graceful = i2;
    }

    @Deprecated
    public void setMaxCookieVersion(int i2) {
    }

    public void setSendDateHeader(boolean z2) {
        this._sendDateHeader = z2;
    }

    public void setSendServerVersion(boolean z2) {
        this._sendServerVersion = z2;
    }

    public void setSessionIdManager(SessionIdManager sessionIdManager) {
        SessionIdManager sessionIdManager2 = this._sessionIdManager;
        if (sessionIdManager2 != null) {
            removeBean(sessionIdManager2);
        }
        this._container.update((Object) this, (Object) this._sessionIdManager, (Object) sessionIdManager, "sessionIdManager", false);
        this._sessionIdManager = sessionIdManager;
        if (sessionIdManager != null) {
            addBean(sessionIdManager);
        }
    }

    public void setStopAtShutdown(boolean z2) {
        if (!z2) {
            ShutdownThread.deregister(this);
        } else if (!this._stopAtShutdown && isStarted()) {
            ShutdownThread.register(this);
        }
        this._stopAtShutdown = z2;
    }

    public void setThreadPool(ThreadPool threadPool) {
        ThreadPool threadPool2 = this._threadPool;
        if (threadPool2 != null) {
            removeBean(threadPool2);
        }
        this._container.update((Object) this, (Object) this._threadPool, (Object) threadPool, "threadpool", false);
        this._threadPool = threadPool;
        if (threadPool != null) {
            addBean(threadPool);
        }
    }

    public void setUncheckedPrintWriter(boolean z2) {
        this._uncheckedPrintWriter = z2;
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    public Server(int i2) {
        setServer(this);
        SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
        selectChannelConnector.setPort(i2);
        setConnectors(new Connector[]{selectChannelConnector});
    }

    public Server(InetSocketAddress inetSocketAddress) {
        setServer(this);
        SelectChannelConnector selectChannelConnector = new SelectChannelConnector();
        selectChannelConnector.setHost(inetSocketAddress.getHostName());
        selectChannelConnector.setPort(inetSocketAddress.getPort());
        setConnectors(new Connector[]{selectChannelConnector});
    }
}
