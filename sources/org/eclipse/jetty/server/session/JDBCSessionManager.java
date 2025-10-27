package org.eclipse.jetty.server.session;

import cn.hutool.core.text.StrPool;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class JDBCSessionManager extends AbstractSessionManager {
    private static final Logger LOG = Log.getLogger((Class<?>) JDBCSessionManager.class);
    protected JDBCSessionIdManager _jdbcSessionIdMgr = null;
    protected long _saveIntervalSec = 60;
    private ConcurrentHashMap<String, AbstractSession> _sessions;

    private String calculateRowId(Session session) {
        return (canonicalize(this._context.getContextPath()) + StrPool.UNDERLINE + getVirtualHost(this._context)) + StrPool.UNDERLINE + session.getId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String canonicalize(String str) {
        return str == null ? "" : str.replace('/', '_').replace('.', '_').replace('\\', '_');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Connection getConnection() throws SQLException {
        return ((JDBCSessionIdManager) getSessionIdManager()).getConnection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getVirtualHost(ContextHandler.Context context) {
        String[] virtualHosts;
        String str;
        return (context == null || (virtualHosts = context.getContextHandler().getVirtualHosts()) == null || virtualHosts.length == 0 || (str = virtualHosts[0]) == null) ? StringUtil.ALL_INTERFACES : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSessionAccessTime(Session session) throws Exception {
        Connection connection = getConnection();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            connection.setAutoCommit(true);
            PreparedStatement preparedStatementPrepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSessionAccessTime);
            preparedStatementPrepareStatement.setString(1, getSessionIdManager().getWorkerName());
            preparedStatementPrepareStatement.setLong(2, session.getAccessed());
            preparedStatementPrepareStatement.setLong(3, session.getLastAccessedTime());
            preparedStatementPrepareStatement.setLong(4, jCurrentTimeMillis);
            preparedStatementPrepareStatement.setLong(5, session.getExpiryTime());
            preparedStatementPrepareStatement.setString(6, session.getRowId());
            preparedStatementPrepareStatement.executeUpdate();
            session.setLastSaved(jCurrentTimeMillis);
            preparedStatementPrepareStatement.close();
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Updated access time session id=" + session.getId(), new Object[0]);
            }
            connection.close();
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public void addSession(AbstractSession abstractSession) {
        if (abstractSession == null) {
            return;
        }
        synchronized (this) {
            this._sessions.put(abstractSession.getClusterId(), abstractSession);
        }
        try {
            synchronized (abstractSession) {
                abstractSession.willPassivate();
                storeSession((Session) abstractSession);
                abstractSession.didActivate();
            }
        } catch (Exception e2) {
            LOG.warn("Unable to store new session id=" + abstractSession.getId(), e2);
        }
    }

    public void cacheInvalidate(Session session) {
    }

    public void deleteSession(Session session) throws Exception {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatementPrepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._deleteSession);
            preparedStatementPrepareStatement.setString(1, session.getRowId());
            preparedStatementPrepareStatement.executeUpdate();
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Deleted Session " + session, new Object[0]);
            }
            connection.close();
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        SessionIdManager sessionIdManager = this._sessionIdManager;
        if (sessionIdManager == null) {
            throw new IllegalStateException("No session id manager defined");
        }
        this._jdbcSessionIdMgr = (JDBCSessionIdManager) sessionIdManager;
        this._sessions = new ConcurrentHashMap<>();
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._sessions.clear();
        this._sessions = null;
        super.doStop();
    }

    public void expire(List<?> list) {
        if (isStopping() || isStopped()) {
            return;
        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        ListIterator<?> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            try {
                String str = (String) listIterator.next();
                Logger logger = LOG;
                if (logger.isDebugEnabled()) {
                    logger.debug("Expiring session id " + str, new Object[0]);
                }
                Session session = (Session) this._sessions.get(str);
                if (session != null) {
                    session.timeout();
                    listIterator.remove();
                } else if (logger.isDebugEnabled()) {
                    logger.debug("Unrecognized session id=" + str, new Object[0]);
                }
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    public long getSaveInterval() {
        return this._saveIntervalSec;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public int getSessions() {
        int size;
        synchronized (this) {
            size = this._sessions.size();
        }
        return size;
    }

    public void invalidateSession(String str) throws IllegalStateException {
        Session session;
        synchronized (this) {
            session = (Session) this._sessions.get(str);
        }
        if (session != null) {
            session.invalidate();
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public void invalidateSessions() {
    }

    public Session loadSession(final String str, final String str2, final String str3) throws Exception {
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        Runnable runnable = new Runnable() { // from class: org.eclipse.jetty.server.session.JDBCSessionManager.1
            /* JADX WARN: Removed duplicated region for block: B:34:0x00f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws java.lang.Throwable {
                /*
                    Method dump skipped, instructions count: 256
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionManager.AnonymousClass1.run():void");
            }
        };
        ContextHandler.Context context = this._context;
        if (context == null) {
            runnable.run();
        } else {
            context.getContextHandler().handle(runnable);
        }
        if (atomicReference2.get() == null) {
            return (Session) atomicReference.get();
        }
        this._jdbcSessionIdMgr.removeSession(str);
        throw ((Exception) atomicReference2.get());
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public AbstractSession newSession(HttpServletRequest httpServletRequest) {
        return new Session(httpServletRequest);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002a  */
    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean removeSession(java.lang.String r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.util.concurrent.ConcurrentHashMap<java.lang.String, org.eclipse.jetty.server.session.AbstractSession> r0 = r5._sessions     // Catch: java.lang.Throwable -> L2d
            java.lang.Object r0 = r0.remove(r6)     // Catch: java.lang.Throwable -> L2d
            org.eclipse.jetty.server.session.JDBCSessionManager$Session r0 = (org.eclipse.jetty.server.session.JDBCSessionManager.Session) r0     // Catch: java.lang.Throwable -> L2d
            if (r0 == 0) goto L26
            r5.deleteSession(r0)     // Catch: java.lang.Exception -> Lf java.lang.Throwable -> L2d
            goto L26
        Lf:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.session.JDBCSessionManager.LOG     // Catch: java.lang.Throwable -> L2d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2d
            r3.<init>()     // Catch: java.lang.Throwable -> L2d
            java.lang.String r4 = "Problem deleting session id="
            r3.append(r4)     // Catch: java.lang.Throwable -> L2d
            r3.append(r6)     // Catch: java.lang.Throwable -> L2d
            java.lang.String r6 = r3.toString()     // Catch: java.lang.Throwable -> L2d
            r2.warn(r6, r1)     // Catch: java.lang.Throwable -> L2d
        L26:
            if (r0 == 0) goto L2a
            r6 = 1
            goto L2b
        L2a:
            r6 = 0
        L2b:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L2d
            return r6
        L2d:
            r6 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L2d
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionManager.removeSession(java.lang.String):boolean");
    }

    public void setSaveInterval(long j2) {
        this._saveIntervalSec = j2;
    }

    public void storeSession(Session session) throws Exception {
        if (session == null) {
            return;
        }
        Connection connection = getConnection();
        try {
            String strCalculateRowId = calculateRowId(session);
            long jCurrentTimeMillis = System.currentTimeMillis();
            connection.setAutoCommit(true);
            PreparedStatement preparedStatementPrepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._insertSession);
            preparedStatementPrepareStatement.setString(1, strCalculateRowId);
            preparedStatementPrepareStatement.setString(2, session.getId());
            preparedStatementPrepareStatement.setString(3, session.getCanonicalContext());
            preparedStatementPrepareStatement.setString(4, session.getVirtualHost());
            preparedStatementPrepareStatement.setString(5, getSessionIdManager().getWorkerName());
            preparedStatementPrepareStatement.setLong(6, session.getAccessed());
            preparedStatementPrepareStatement.setLong(7, session.getLastAccessedTime());
            preparedStatementPrepareStatement.setLong(8, session.getCreationTime());
            preparedStatementPrepareStatement.setLong(9, session.getCookieSet());
            preparedStatementPrepareStatement.setLong(10, jCurrentTimeMillis);
            preparedStatementPrepareStatement.setLong(11, session.getExpiryTime());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(session.getAttributeMap());
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            preparedStatementPrepareStatement.setBinaryStream(12, (InputStream) new ByteArrayInputStream(byteArray), byteArray.length);
            preparedStatementPrepareStatement.executeUpdate();
            session.setRowId(strCalculateRowId);
            session.setLastSaved(jCurrentTimeMillis);
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Stored session " + session, new Object[0]);
            }
            connection.close();
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }

    public void updateSession(Session session) throws Exception {
        if (session == null) {
            return;
        }
        Connection connection = getConnection();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            connection.setAutoCommit(true);
            PreparedStatement preparedStatementPrepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSession);
            preparedStatementPrepareStatement.setString(1, getSessionIdManager().getWorkerName());
            preparedStatementPrepareStatement.setLong(2, session.getAccessed());
            preparedStatementPrepareStatement.setLong(3, session.getLastAccessedTime());
            preparedStatementPrepareStatement.setLong(4, jCurrentTimeMillis);
            preparedStatementPrepareStatement.setLong(5, session.getExpiryTime());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(session.getAttributeMap());
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            preparedStatementPrepareStatement.setBinaryStream(6, (InputStream) new ByteArrayInputStream(byteArray), byteArray.length);
            preparedStatementPrepareStatement.setString(7, session.getRowId());
            preparedStatementPrepareStatement.executeUpdate();
            session.setLastSaved(jCurrentTimeMillis);
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Updated session " + session, new Object[0]);
            }
            connection.close();
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }

    public void updateSessionNode(Session session) throws Exception {
        String workerName = getSessionIdManager().getWorkerName();
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(true);
            PreparedStatement preparedStatementPrepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSessionNode);
            preparedStatementPrepareStatement.setString(1, workerName);
            preparedStatementPrepareStatement.setString(2, session.getRowId());
            preparedStatementPrepareStatement.executeUpdate();
            preparedStatementPrepareStatement.close();
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Updated last node for session id=" + session.getId() + ", lastNode = " + workerName, new Object[0]);
            }
            connection.close();
        } catch (Throwable th) {
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }

    public class ClassLoadingObjectInputStream extends ObjectInputStream {
        public ClassLoadingObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override // java.io.ObjectInputStream
        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
            try {
                return Class.forName(objectStreamClass.getName(), false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException unused) {
                return super.resolveClass(objectStreamClass);
            }
        }

        public ClassLoadingObjectInputStream() throws IOException {
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public Session getSession(String str) {
        Session session;
        Session sessionLoadSession;
        Session session2 = (Session) this._sessions.get(str);
        synchronized (this) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                if (session2 == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("getSession(");
                    sb.append(str);
                    sb.append("): not in session map,");
                    sb.append(" now=");
                    sb.append(jCurrentTimeMillis);
                    sb.append(" lastSaved=");
                    sb.append(session2 == null ? 0L : session2._lastSaved);
                    sb.append(" interval=");
                    sb.append(this._saveIntervalSec * 1000);
                    logger.debug(sb.toString(), new Object[0]);
                } else {
                    logger.debug("getSession(" + str + "): in session map,  now=" + jCurrentTimeMillis + " lastSaved=" + session2._lastSaved + " interval=" + (this._saveIntervalSec * 1000) + " lastNode=" + session2._lastNode + " thisNode=" + getSessionIdManager().getWorkerName() + " difference=" + (jCurrentTimeMillis - session2._lastSaved), new Object[0]);
                }
            }
            session = null;
            try {
                if (session2 == null) {
                    logger.debug("getSession(" + str + "): no session in session map. Reloading session data from db.", new Object[0]);
                    sessionLoadSession = loadSession(str, canonicalize(this._context.getContextPath()), getVirtualHost(this._context));
                } else if (jCurrentTimeMillis - session2._lastSaved >= this._saveIntervalSec * 1000) {
                    logger.debug("getSession(" + str + "): stale session. Reloading session data from db.", new Object[0]);
                    sessionLoadSession = loadSession(str, canonicalize(this._context.getContextPath()), getVirtualHost(this._context));
                } else {
                    logger.debug("getSession(" + str + "): session in session map", new Object[0]);
                    sessionLoadSession = session2;
                }
                if (sessionLoadSession == null) {
                    logger.debug("getSession({}): No session in database matching id={}", str, str);
                } else if (!sessionLoadSession.getLastNode().equals(getSessionIdManager().getWorkerName()) || session2 == null) {
                    try {
                        if (sessionLoadSession._expiryTime > 0 && sessionLoadSession._expiryTime <= jCurrentTimeMillis) {
                            logger.debug("getSession ({}): Session has expired", str);
                        }
                        updateSessionNode(sessionLoadSession);
                        sessionLoadSession.didActivate();
                    } catch (Exception e2) {
                        LOG.warn("Unable to update freshly loaded session " + str, e2);
                        return null;
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("getSession(" + str + "): lastNode=" + sessionLoadSession.getLastNode() + " thisNode=" + getSessionIdManager().getWorkerName(), new Object[0]);
                    }
                    sessionLoadSession.setLastNode(getSessionIdManager().getWorkerName());
                    this._sessions.put(str, sessionLoadSession);
                } else {
                    logger.debug("getSession({}): Session not stale {}", str, sessionLoadSession);
                }
                session = sessionLoadSession;
            } catch (Exception e3) {
                LOG.warn("Unable to load session " + str, e3);
                return null;
            }
        }
        return session;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public void removeSession(AbstractSession abstractSession, boolean z2) {
        boolean z3;
        synchronized (this) {
            if (getSession(abstractSession.getClusterId()) != null) {
                removeSession(abstractSession.getClusterId());
                z3 = true;
            } else {
                z3 = false;
            }
        }
        if (z3) {
            this._sessionIdManager.removeSession(abstractSession);
            if (z2) {
                this._sessionIdManager.invalidateAll(abstractSession.getClusterId());
            }
            if (z2 && !this._sessionListeners.isEmpty()) {
                HttpSessionEvent httpSessionEvent = new HttpSessionEvent(abstractSession);
                Iterator<HttpSessionListener> it = this._sessionListeners.iterator();
                while (it.hasNext()) {
                    it.next().sessionDestroyed(httpSessionEvent);
                }
            }
            if (z2) {
                return;
            }
            abstractSession.willPassivate();
        }
    }

    public class Session extends AbstractSession {
        private static final long serialVersionUID = 5208464051134226143L;
        private String _canonicalContext;
        private long _cookieSet;
        private boolean _dirty;
        private long _expiryTime;
        private String _lastNode;
        private long _lastSaved;
        private String _rowId;
        private String _virtualHost;

        public Session(HttpServletRequest httpServletRequest) throws IllegalStateException {
            super(JDBCSessionManager.this, httpServletRequest);
            this._dirty = false;
            int maxInactiveInterval = getMaxInactiveInterval();
            this._expiryTime = maxInactiveInterval <= 0 ? 0L : System.currentTimeMillis() + (maxInactiveInterval * 1000);
            this._virtualHost = JDBCSessionManager.getVirtualHost(JDBCSessionManager.this._context);
            this._canonicalContext = JDBCSessionManager.canonicalize(JDBCSessionManager.this._context.getContextPath());
            this._lastNode = JDBCSessionManager.this.getSessionIdManager().getWorkerName();
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public boolean access(long j2) {
            synchronized (this) {
                if (!super.access(j2)) {
                    return false;
                }
                int maxInactiveInterval = getMaxInactiveInterval();
                this._expiryTime = maxInactiveInterval <= 0 ? 0L : j2 + (maxInactiveInterval * 1000);
                return true;
            }
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void complete() {
            synchronized (this) {
                super.complete();
                try {
                    try {
                        if (isValid()) {
                            if (this._dirty) {
                                willPassivate();
                                JDBCSessionManager.this.updateSession(this);
                                didActivate();
                            } else if (getAccessed() - this._lastSaved >= JDBCSessionManager.this.getSaveInterval() * 1000) {
                                JDBCSessionManager.this.updateSessionAccessTime(this);
                            }
                        }
                    } catch (Exception e2) {
                        AbstractSession.LOG.warn("Problem persisting changed session data id=" + getId(), e2);
                    }
                } finally {
                    this._dirty = false;
                }
            }
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void cookieSet() {
            this._cookieSet = getAccessed();
        }

        public synchronized String getCanonicalContext() {
            return this._canonicalContext;
        }

        public synchronized long getCookieSet() {
            return this._cookieSet;
        }

        public synchronized long getExpiryTime() {
            return this._expiryTime;
        }

        public synchronized String getLastNode() {
            return this._lastNode;
        }

        public synchronized long getLastSaved() {
            return this._lastSaved;
        }

        public synchronized String getRowId() {
            return this._rowId;
        }

        public synchronized String getVirtualHost() {
            return this._virtualHost;
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void removeAttribute(String str) {
            super.removeAttribute(str);
            this._dirty = true;
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void setAttribute(String str, Object obj) {
            super.setAttribute(str, obj);
            this._dirty = true;
        }

        public synchronized void setCanonicalContext(String str) {
            this._canonicalContext = str;
        }

        public void setCookieSet(long j2) {
            this._cookieSet = j2;
        }

        public synchronized void setExpiryTime(long j2) {
            this._expiryTime = j2;
        }

        public synchronized void setLastNode(String str) {
            this._lastNode = str;
        }

        public synchronized void setLastSaved(long j2) {
            this._lastSaved = j2;
        }

        public synchronized void setRowId(String str) {
            this._rowId = str;
        }

        public synchronized void setVirtualHost(String str) {
            this._virtualHost = str;
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void timeout() throws IllegalStateException {
            Logger logger = AbstractSession.LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Timing out session id=" + getClusterId(), new Object[0]);
            }
            super.timeout();
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession
        public String toString() {
            return "Session rowId=" + this._rowId + ",id=" + getId() + ",lastNode=" + this._lastNode + ",created=" + getCreationTime() + ",accessed=" + getAccessed() + ",lastAccessed=" + getLastAccessedTime() + ",cookieSet=" + this._cookieSet + ",lastSaved=" + this._lastSaved + ",expiry=" + this._expiryTime;
        }

        public Session(String str, String str2, long j2, long j3) {
            super(JDBCSessionManager.this, j2, j3, str);
            this._dirty = false;
            this._rowId = str2;
        }
    }
}
