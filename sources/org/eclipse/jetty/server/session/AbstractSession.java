package org.eclipse.jetty.server.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class AbstractSession implements AbstractSessionManager.SessionIf {
    static final Logger LOG = SessionHandler.LOG;
    private long _accessed;
    private final Map<String, Object> _attributes;
    private final String _clusterId;
    private long _cookieSet;
    private final long _created;
    private boolean _doInvalidate;
    private boolean _idChanged;
    private boolean _invalid;
    private long _lastAccessed;
    private final AbstractSessionManager _manager;
    private long _maxIdleMs;
    private boolean _newSession;
    private final String _nodeId;
    private int _requests;

    public AbstractSession(AbstractSessionManager abstractSessionManager, HttpServletRequest httpServletRequest) {
        this._attributes = new HashMap();
        this._manager = abstractSessionManager;
        this._newSession = true;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this._created = jCurrentTimeMillis;
        String strNewSessionId = abstractSessionManager._sessionIdManager.newSessionId(httpServletRequest, jCurrentTimeMillis);
        this._clusterId = strNewSessionId;
        String nodeId = abstractSessionManager._sessionIdManager.getNodeId(strNewSessionId, httpServletRequest);
        this._nodeId = nodeId;
        this._accessed = jCurrentTimeMillis;
        this._lastAccessed = jCurrentTimeMillis;
        this._requests = 1;
        int i2 = abstractSessionManager._dftMaxIdleSecs;
        this._maxIdleMs = i2 > 0 ? i2 * 1000 : -1L;
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("new session & id " + nodeId + " " + strNewSessionId, new Object[0]);
        }
    }

    public boolean access(long j2) {
        synchronized (this) {
            if (this._invalid) {
                return false;
            }
            this._newSession = false;
            long j3 = this._accessed;
            this._lastAccessed = j3;
            this._accessed = j2;
            long j4 = this._maxIdleMs;
            if (j4 <= 0 || j3 <= 0 || j3 + j4 >= j2) {
                this._requests++;
                return true;
            }
            invalidate();
            return false;
        }
    }

    public void addAttributes(Map<String, Object> map) {
        this._attributes.putAll(map);
    }

    public void bindValue(String str, Object obj) {
        if (obj == null || !(obj instanceof HttpSessionBindingListener)) {
            return;
        }
        ((HttpSessionBindingListener) obj).valueBound(new HttpSessionBindingEvent(this, str));
    }

    public void checkValid() throws IllegalStateException {
        if (this._invalid) {
            throw new IllegalStateException();
        }
    }

    public void clearAttributes() {
        ArrayList arrayList;
        Object objDoPutOrRemove;
        while (true) {
            Map<String, Object> map = this._attributes;
            if (map == null || map.size() <= 0) {
                break;
            }
            synchronized (this) {
                arrayList = new ArrayList(this._attributes.keySet());
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                synchronized (this) {
                    objDoPutOrRemove = doPutOrRemove(str, null);
                }
                unbindValue(str, objDoPutOrRemove);
                this._manager.doSessionAttributeListeners(this, str, objDoPutOrRemove, null);
            }
        }
        Map<String, Object> map2 = this._attributes;
        if (map2 != null) {
            map2.clear();
        }
    }

    public void complete() {
        synchronized (this) {
            int i2 = this._requests - 1;
            this._requests = i2;
            if (this._doInvalidate && i2 <= 0) {
                doInvalidate();
            }
        }
    }

    public void cookieSet() {
        synchronized (this) {
            this._cookieSet = this._accessed;
        }
    }

    public void didActivate() {
        synchronized (this) {
            HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);
            for (Object obj : this._attributes.values()) {
                if (obj instanceof HttpSessionActivationListener) {
                    ((HttpSessionActivationListener) obj).sessionDidActivate(httpSessionEvent);
                }
            }
        }
    }

    public Object doGet(String str) {
        return this._attributes.get(str);
    }

    public void doInvalidate() throws IllegalStateException {
        try {
            LOG.debug("invalidate {}", this._clusterId);
            if (isValid()) {
                clearAttributes();
            }
            synchronized (this) {
                this._invalid = true;
            }
        } catch (Throwable th) {
            synchronized (this) {
                this._invalid = true;
                throw th;
            }
        }
    }

    public Object doPutOrRemove(String str, Object obj) {
        return obj == null ? this._attributes.remove(str) : this._attributes.put(str, obj);
    }

    public long getAccessed() {
        long j2;
        synchronized (this) {
            j2 = this._accessed;
        }
        return j2;
    }

    public Object getAttribute(String str) {
        Object obj;
        synchronized (this) {
            checkValid();
            obj = this._attributes.get(str);
        }
        return obj;
    }

    public Map<String, Object> getAttributeMap() {
        return this._attributes;
    }

    public Enumeration<String> getAttributeNames() {
        Enumeration<String> enumeration;
        synchronized (this) {
            checkValid();
            enumeration = Collections.enumeration(this._attributes == null ? Collections.EMPTY_LIST : new ArrayList(this._attributes.keySet()));
        }
        return enumeration;
    }

    public int getAttributes() {
        int size;
        synchronized (this) {
            checkValid();
            size = this._attributes.size();
        }
        return size;
    }

    public String getClusterId() {
        return this._clusterId;
    }

    public long getCookieSetTime() {
        return this._cookieSet;
    }

    public long getCreationTime() throws IllegalStateException {
        return this._created;
    }

    public String getId() throws IllegalStateException {
        return this._manager._nodeIdInSessionId ? this._nodeId : this._clusterId;
    }

    public long getLastAccessedTime() throws IllegalStateException {
        checkValid();
        return this._lastAccessed;
    }

    public int getMaxInactiveInterval() throws IllegalStateException {
        checkValid();
        return (int) (this._maxIdleMs / 1000);
    }

    public Set<String> getNames() {
        HashSet hashSet;
        synchronized (this) {
            hashSet = new HashSet(this._attributes.keySet());
        }
        return hashSet;
    }

    public String getNodeId() {
        return this._nodeId;
    }

    public int getRequests() {
        int i2;
        synchronized (this) {
            i2 = this._requests;
        }
        return i2;
    }

    public ServletContext getServletContext() {
        return this._manager._context;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager.SessionIf
    public AbstractSession getSession() {
        return this;
    }

    @Deprecated
    public HttpSessionContext getSessionContext() throws IllegalStateException {
        checkValid();
        return AbstractSessionManager.__nullSessionContext;
    }

    @Deprecated
    public Object getValue(String str) throws IllegalStateException {
        return getAttribute(str);
    }

    @Deprecated
    public String[] getValueNames() throws IllegalStateException {
        synchronized (this) {
            checkValid();
            Map<String, Object> map = this._attributes;
            if (map == null) {
                return new String[0];
            }
            return (String[]) this._attributes.keySet().toArray(new String[map.size()]);
        }
    }

    public void invalidate() throws IllegalStateException {
        this._manager.removeSession(this, true);
        doInvalidate();
    }

    public boolean isIdChanged() {
        return this._idChanged;
    }

    public boolean isNew() throws IllegalStateException {
        checkValid();
        return this._newSession;
    }

    public boolean isValid() {
        return !this._invalid;
    }

    @Deprecated
    public void putValue(String str, Object obj) throws IllegalStateException {
        setAttribute(str, obj);
    }

    public void removeAttribute(String str) {
        setAttribute(str, null);
    }

    @Deprecated
    public void removeValue(String str) throws IllegalStateException {
        removeAttribute(str);
    }

    public void setAttribute(String str, Object obj) {
        Object objDoPutOrRemove;
        synchronized (this) {
            checkValid();
            objDoPutOrRemove = doPutOrRemove(str, obj);
        }
        if (obj == null || !obj.equals(objDoPutOrRemove)) {
            if (objDoPutOrRemove != null) {
                unbindValue(str, objDoPutOrRemove);
            }
            if (obj != null) {
                bindValue(str, obj);
            }
            this._manager.doSessionAttributeListeners(this, str, objDoPutOrRemove, obj);
        }
    }

    public void setIdChanged(boolean z2) {
        this._idChanged = z2;
    }

    public void setLastAccessedTime(long j2) {
        this._lastAccessed = j2;
    }

    public void setMaxInactiveInterval(int i2) {
        this._maxIdleMs = i2 * 1000;
    }

    public void setRequests(int i2) {
        synchronized (this) {
            this._requests = i2;
        }
    }

    public void timeout() throws IllegalStateException {
        boolean z2 = true;
        this._manager.removeSession(this, true);
        synchronized (this) {
            if (this._invalid) {
                z2 = false;
            } else if (this._requests > 0) {
                this._doInvalidate = true;
                z2 = false;
            }
        }
        if (z2) {
            doInvalidate();
        }
    }

    public String toString() {
        return getClass().getName() + ":" + getId() + "@" + hashCode();
    }

    public void unbindValue(String str, Object obj) {
        if (obj == null || !(obj instanceof HttpSessionBindingListener)) {
            return;
        }
        ((HttpSessionBindingListener) obj).valueUnbound(new HttpSessionBindingEvent(this, str));
    }

    public void willPassivate() {
        synchronized (this) {
            HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);
            for (Object obj : this._attributes.values()) {
                if (obj instanceof HttpSessionActivationListener) {
                    ((HttpSessionActivationListener) obj).sessionWillPassivate(httpSessionEvent);
                }
            }
        }
    }

    public AbstractSession(AbstractSessionManager abstractSessionManager, long j2, long j3, String str) {
        this._attributes = new HashMap();
        this._manager = abstractSessionManager;
        this._created = j2;
        this._clusterId = str;
        String nodeId = abstractSessionManager._sessionIdManager.getNodeId(str, null);
        this._nodeId = nodeId;
        this._accessed = j3;
        this._lastAccessed = j3;
        this._requests = 1;
        int i2 = abstractSessionManager._dftMaxIdleSecs;
        this._maxIdleMs = i2 > 0 ? i2 * 1000 : -1L;
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("new session " + nodeId + " " + str, new Object[0]);
        }
    }
}
