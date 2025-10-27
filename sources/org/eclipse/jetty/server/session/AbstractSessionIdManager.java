package org.eclipse.jetty.server.session;

import cn.hutool.core.text.StrPool;
import java.security.SecureRandom;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public abstract class AbstractSessionIdManager extends AbstractLifeCycle implements SessionIdManager {
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractSessionIdManager.class);
    private static final String __NEW_SESSION_ID = "org.eclipse.jetty.server.newSessionId";
    protected Random _random;
    protected long _reseed = 100000;
    protected boolean _weakRandom;
    protected String _workerName;

    public AbstractSessionIdManager() {
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        initRandom();
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
    }

    public Random getRandom() {
        return this._random;
    }

    public long getReseed() {
        return this._reseed;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String getWorkerName() {
        return this._workerName;
    }

    public void initRandom() {
        Random random = this._random;
        if (random != null) {
            random.setSeed(((random.nextLong() ^ System.currentTimeMillis()) ^ hashCode()) ^ Runtime.getRuntime().freeMemory());
            return;
        }
        try {
            this._random = new SecureRandom();
        } catch (Exception e2) {
            LOG.warn("Could not generate SecureRandom for session-id randomness", e2);
            this._random = new Random();
            this._weakRandom = true;
        }
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String newSessionId(HttpServletRequest httpServletRequest, long j2) {
        synchronized (this) {
            if (httpServletRequest != null) {
                try {
                    String requestedSessionId = httpServletRequest.getRequestedSessionId();
                    if (requestedSessionId != null) {
                        String clusterId = getClusterId(requestedSessionId);
                        if (idInUse(clusterId)) {
                            return clusterId;
                        }
                    }
                    String str = (String) httpServletRequest.getAttribute(__NEW_SESSION_ID);
                    if (str != null && idInUse(str)) {
                        return str;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            String str2 = null;
            while (true) {
                if (str2 != null && str2.length() != 0 && !idInUse(str2)) {
                    httpServletRequest.setAttribute(__NEW_SESSION_ID, str2);
                    return str2;
                }
                long jHashCode = this._weakRandom ? ((hashCode() ^ Runtime.getRuntime().freeMemory()) ^ this._random.nextInt()) ^ (httpServletRequest.hashCode() << 32) : this._random.nextLong();
                if (jHashCode < 0) {
                    jHashCode = -jHashCode;
                }
                long j3 = this._reseed;
                if (j3 > 0 && jHashCode % j3 == 1) {
                    LOG.debug("Reseeding {}", this);
                    Random random = this._random;
                    if (random instanceof SecureRandom) {
                        SecureRandom secureRandom = (SecureRandom) random;
                        secureRandom.setSeed(secureRandom.generateSeed(8));
                    } else {
                        random.setSeed(((random.nextLong() ^ System.currentTimeMillis()) ^ httpServletRequest.hashCode()) ^ Runtime.getRuntime().freeMemory());
                    }
                }
                long jHashCode2 = this._weakRandom ? (httpServletRequest.hashCode() << 32) ^ ((hashCode() ^ Runtime.getRuntime().freeMemory()) ^ this._random.nextInt()) : this._random.nextLong();
                if (jHashCode2 < 0) {
                    jHashCode2 = -jHashCode2;
                }
                str2 = Long.toString(jHashCode, 36) + Long.toString(jHashCode2, 36);
                if (this._workerName != null) {
                    str2 = this._workerName + str2;
                }
            }
        }
    }

    public synchronized void setRandom(Random random) {
        this._random = random;
        this._weakRandom = false;
    }

    public void setReseed(long j2) {
        this._reseed = j2;
    }

    public void setWorkerName(String str) {
        if (str.contains(StrPool.DOT)) {
            throw new IllegalArgumentException("Name cannot contain '.'");
        }
        this._workerName = str;
    }

    public AbstractSessionIdManager(Random random) {
        this._random = random;
    }
}
