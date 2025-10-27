package org.eclipse.jetty.server.session;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/* loaded from: classes9.dex */
public class HashSessionIdManager extends AbstractSessionIdManager {
    private final Map<String, Set<WeakReference<HttpSession>>> _sessions;

    public HashSessionIdManager() {
        this._sessions = new HashMap();
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public void addSession(HttpSession httpSession) {
        String clusterId = getClusterId(httpSession.getId());
        WeakReference<HttpSession> weakReference = new WeakReference<>(httpSession);
        synchronized (this) {
            Set<WeakReference<HttpSession>> hashSet = this._sessions.get(clusterId);
            if (hashSet == null) {
                hashSet = new HashSet<>();
                this._sessions.put(clusterId, hashSet);
            }
            hashSet.add(weakReference);
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionIdManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionIdManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._sessions.clear();
        super.doStop();
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String getClusterId(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf > 0 ? str.substring(0, iLastIndexOf) : str;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String getNodeId(String str, HttpServletRequest httpServletRequest) {
        String str2 = httpServletRequest == null ? null : (String) httpServletRequest.getAttribute("org.eclipse.jetty.ajp.JVMRoute");
        if (str2 != null) {
            return str + '.' + str2;
        }
        if (this._workerName == null) {
            return str;
        }
        return str + '.' + this._workerName;
    }

    public Collection<HttpSession> getSession(String str) {
        ArrayList arrayList = new ArrayList();
        Set<WeakReference<HttpSession>> set = this._sessions.get(str);
        if (set != null) {
            Iterator<WeakReference<HttpSession>> it = set.iterator();
            while (it.hasNext()) {
                HttpSession httpSession = it.next().get();
                if (httpSession != null) {
                    arrayList.add(httpSession);
                }
            }
        }
        return arrayList;
    }

    public Collection<String> getSessions() {
        return Collections.unmodifiableCollection(this._sessions.keySet());
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public boolean idInUse(String str) {
        boolean zContainsKey;
        synchronized (this) {
            zContainsKey = this._sessions.containsKey(str);
        }
        return zContainsKey;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public void invalidateAll(String str) throws IllegalStateException {
        Set<WeakReference<HttpSession>> setRemove;
        synchronized (this) {
            setRemove = this._sessions.remove(str);
        }
        if (setRemove != null) {
            Iterator<WeakReference<HttpSession>> it = setRemove.iterator();
            while (it.hasNext()) {
                AbstractSession abstractSession = (AbstractSession) it.next().get();
                if (abstractSession != null && abstractSession.isValid()) {
                    abstractSession.invalidate();
                }
            }
            setRemove.clear();
        }
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public void removeSession(HttpSession httpSession) {
        String clusterId = getClusterId(httpSession.getId());
        synchronized (this) {
            Set<WeakReference<HttpSession>> set = this._sessions.get(clusterId);
            if (set != null) {
                Iterator<WeakReference<HttpSession>> it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    HttpSession httpSession2 = it.next().get();
                    if (httpSession2 == null) {
                        it.remove();
                    } else if (httpSession2 == httpSession) {
                        it.remove();
                        break;
                    }
                }
                if (set.isEmpty()) {
                    this._sessions.remove(clusterId);
                }
            }
        }
    }

    public HashSessionIdManager(Random random) {
        super(random);
        this._sessions = new HashMap();
    }
}
