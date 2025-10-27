package org.eclipse.jetty.server.session;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class HashSessionManager extends AbstractSessionManager {
    private static int __id;
    static final Logger __log = SessionHandler.LOG;
    private TimerTask _saveTask;
    File _storeDir;
    private TimerTask _task;
    private Timer _timer;
    protected final ConcurrentMap<String, HashedSession> _sessions = new ConcurrentHashMap();
    private boolean _timerStop = false;
    long _scavengePeriodMs = 30000;
    long _savePeriodMs = 0;
    long _idleSavePeriodMs = 0;
    private boolean _lazyLoad = false;
    private volatile boolean _sessionsLoaded = false;
    private boolean _deleteUnrestorableSessions = false;

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public void addSession(AbstractSession abstractSession) {
        if (isRunning()) {
            this._sessions.put(abstractSession.getClusterId(), (HashedSession) abstractSession);
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        this._timerStop = false;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext != null) {
            this._timer = (Timer) currentContext.getAttribute("org.eclipse.jetty.server.session.timer");
        }
        if (this._timer == null) {
            this._timerStop = true;
            StringBuilder sb = new StringBuilder();
            sb.append("HashSessionScavenger-");
            int i2 = __id;
            __id = i2 + 1;
            sb.append(i2);
            this._timer = new Timer(sb.toString(), true);
        }
        setScavengePeriod(getScavengePeriod());
        File file = this._storeDir;
        if (file != null) {
            if (!file.exists()) {
                this._storeDir.mkdirs();
            }
            if (!this._lazyLoad) {
                restoreSessions();
            }
        }
        setSavePeriod(getSavePeriod());
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        synchronized (this) {
            TimerTask timerTask = this._saveTask;
            if (timerTask != null) {
                timerTask.cancel();
            }
            this._saveTask = null;
            TimerTask timerTask2 = this._task;
            if (timerTask2 != null) {
                timerTask2.cancel();
            }
            this._task = null;
            Timer timer = this._timer;
            if (timer != null && this._timerStop) {
                timer.cancel();
            }
            this._timer = null;
        }
        super.doStop();
        this._sessions.clear();
    }

    public int getIdleSavePeriod() {
        long j2 = this._idleSavePeriodMs;
        if (j2 <= 0) {
            return 0;
        }
        return (int) (j2 / 1000);
    }

    public int getSavePeriod() {
        long j2 = this._savePeriodMs;
        if (j2 <= 0) {
            return 0;
        }
        return (int) (j2 / 1000);
    }

    public int getScavengePeriod() {
        return (int) (this._scavengePeriodMs / 1000);
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public AbstractSession getSession(String str) {
        if (this._lazyLoad && !this._sessionsLoaded) {
            try {
                restoreSessions();
            } catch (Exception e2) {
                __log.warn(e2);
            }
        }
        ConcurrentMap<String, HashedSession> concurrentMap = this._sessions;
        if (concurrentMap == null) {
            return null;
        }
        HashedSession hashedSessionRestoreSession = concurrentMap.get(str);
        if (hashedSessionRestoreSession == null && this._lazyLoad) {
            hashedSessionRestoreSession = restoreSession(str);
        }
        if (hashedSessionRestoreSession == null) {
            return null;
        }
        if (this._idleSavePeriodMs != 0) {
            hashedSessionRestoreSession.deIdle();
        }
        return hashedSessionRestoreSession;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public int getSessions() {
        int sessions = super.getSessions();
        Logger logger = __log;
        if (logger.isDebugEnabled() && this._sessions.size() != sessions) {
            logger.warn("sessions: " + this._sessions.size() + "!=" + sessions, new Object[0]);
        }
        return sessions;
    }

    public File getStoreDirectory() {
        return this._storeDir;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public void invalidateSessions() throws Exception {
        File file;
        ArrayList arrayList = new ArrayList(this._sessions.values());
        int i2 = 100;
        while (arrayList.size() > 0) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return;
            }
            if (isStopping() && (file = this._storeDir) != null && file.exists() && this._storeDir.canWrite()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    HashedSession hashedSession = (HashedSession) it.next();
                    hashedSession.save(false);
                    removeSession((AbstractSession) hashedSession, false);
                }
            } else {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ((HashedSession) it2.next()).invalidate();
                }
            }
            arrayList = new ArrayList(this._sessions.values());
            i2 = i3;
        }
    }

    public boolean isDeleteUnrestorableSessions() {
        return this._deleteUnrestorableSessions;
    }

    public boolean isLazyLoad() {
        return this._lazyLoad;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public AbstractSession newSession(HttpServletRequest httpServletRequest) {
        return new HashedSession(this, httpServletRequest);
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public boolean removeSession(String str) {
        return this._sessions.remove(str) != null;
    }

    public synchronized HashedSession restoreSession(String str) {
        FileInputStream fileInputStream;
        File file = new File(this._storeDir, str);
        FileInputStream fileInputStream2 = null;
        try {
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th) {
            th = th;
        }
        if (!file.exists()) {
            file.delete();
            return null;
        }
        fileInputStream = new FileInputStream(file);
        try {
            HashedSession hashedSessionRestoreSession = restoreSession(fileInputStream, null);
            addSession(hashedSessionRestoreSession, false);
            hashedSessionRestoreSession.didActivate();
            IO.close(fileInputStream);
            file.delete();
            return hashedSessionRestoreSession;
        } catch (Exception e3) {
            e = e3;
            if (fileInputStream != null) {
                IO.close(fileInputStream);
            }
            if (isDeleteUnrestorableSessions() && file.exists() && file.getParentFile().equals(this._storeDir)) {
                file.delete();
                __log.warn("Deleting file for unrestorable session " + str, e);
            } else {
                __log.warn("Problem restoring session " + str, e);
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                IO.close(fileInputStream2);
            }
            file.delete();
            throw th;
        }
    }

    public void restoreSessions() throws Exception {
        this._sessionsLoaded = true;
        File file = this._storeDir;
        if (file == null || !file.exists()) {
            return;
        }
        if (this._storeDir.canRead()) {
            String[] list = this._storeDir.list();
            for (int i2 = 0; list != null && i2 < list.length; i2++) {
                restoreSession(list[i2]);
            }
            return;
        }
        __log.warn("Unable to restore Sessions: Cannot read from Session storage directory " + this._storeDir.getAbsolutePath(), new Object[0]);
    }

    public void saveSessions(boolean z2) throws Exception {
        File file = this._storeDir;
        if (file == null || !file.exists()) {
            return;
        }
        if (this._storeDir.canWrite()) {
            Iterator<HashedSession> it = this._sessions.values().iterator();
            while (it.hasNext()) {
                it.next().save(true);
            }
        } else {
            __log.warn("Unable to save Sessions: Session persistence storage directory " + this._storeDir.getAbsolutePath() + " is not writeable", new Object[0]);
        }
    }

    public void scavenge() {
        if (isStopping() || isStopped()) {
            return;
        }
        Thread threadCurrentThread = Thread.currentThread();
        ClassLoader contextClassLoader = threadCurrentThread.getContextClassLoader();
        try {
            ClassLoader classLoader = this._loader;
            if (classLoader != null) {
                threadCurrentThread.setContextClassLoader(classLoader);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            for (HashedSession hashedSession : this._sessions.values()) {
                long maxInactiveInterval = hashedSession.getMaxInactiveInterval() * 1000;
                if (maxInactiveInterval > 0 && hashedSession.getAccessed() + maxInactiveInterval < jCurrentTimeMillis) {
                    try {
                        hashedSession.timeout();
                    } catch (Exception e2) {
                        __log.warn("Problem scavenging sessions", e2);
                    }
                } else if (this._idleSavePeriodMs > 0 && hashedSession.getAccessed() + this._idleSavePeriodMs < jCurrentTimeMillis) {
                    try {
                        hashedSession.idle();
                    } catch (Exception e3) {
                        __log.warn("Problem idling session " + hashedSession.getId(), e3);
                    }
                }
            }
        } finally {
            threadCurrentThread.setContextClassLoader(contextClassLoader);
        }
    }

    public void setDeleteUnrestorableSessions(boolean z2) {
        this._deleteUnrestorableSessions = z2;
    }

    public void setIdleSavePeriod(int i2) {
        this._idleSavePeriodMs = i2 * 1000;
    }

    public void setLazyLoad(boolean z2) {
        this._lazyLoad = z2;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.server.SessionManager
    public void setMaxInactiveInterval(int i2) {
        super.setMaxInactiveInterval(i2);
        int i3 = this._dftMaxIdleSecs;
        if (i3 <= 0 || this._scavengePeriodMs <= i3 * 1000) {
            return;
        }
        setScavengePeriod((i3 + 9) / 10);
    }

    public void setSavePeriod(int i2) {
        long j2 = i2 * 1000;
        if (j2 < 0) {
            j2 = 0;
        }
        this._savePeriodMs = j2;
        if (this._timer != null) {
            synchronized (this) {
                TimerTask timerTask = this._saveTask;
                if (timerTask != null) {
                    timerTask.cancel();
                }
                if (this._savePeriodMs > 0 && this._storeDir != null) {
                    TimerTask timerTask2 = new TimerTask() { // from class: org.eclipse.jetty.server.session.HashSessionManager.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            try {
                                HashSessionManager.this.saveSessions(true);
                            } catch (Exception e2) {
                                HashSessionManager.__log.warn(e2);
                            }
                        }
                    };
                    this._saveTask = timerTask2;
                    Timer timer = this._timer;
                    long j3 = this._savePeriodMs;
                    timer.schedule(timerTask2, j3, j3);
                }
            }
        }
    }

    public void setScavengePeriod(int i2) {
        if (i2 == 0) {
            i2 = 60;
        }
        long j2 = this._scavengePeriodMs;
        long j3 = i2 * 1000;
        if (j3 > 60000) {
            j3 = 60000;
        }
        long j4 = j3 >= 1000 ? j3 : 1000L;
        this._scavengePeriodMs = j4;
        if (this._timer != null) {
            if (j4 != j2 || this._task == null) {
                synchronized (this) {
                    TimerTask timerTask = this._task;
                    if (timerTask != null) {
                        timerTask.cancel();
                    }
                    TimerTask timerTask2 = new TimerTask() { // from class: org.eclipse.jetty.server.session.HashSessionManager.2
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            HashSessionManager.this.scavenge();
                        }
                    };
                    this._task = timerTask2;
                    Timer timer = this._timer;
                    long j5 = this._scavengePeriodMs;
                    timer.schedule(timerTask2, j5, j5);
                }
            }
        }
    }

    public void setStoreDirectory(File file) throws IOException {
        this._storeDir = file.getCanonicalFile();
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

    public AbstractSession newSession(long j2, long j3, String str) {
        return new HashedSession(this, j2, j3, str);
    }

    public HashedSession restoreSession(InputStream inputStream, HashedSession hashedSession) throws Exception {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            String utf = dataInputStream.readUTF();
            dataInputStream.readUTF();
            long j2 = dataInputStream.readLong();
            long j3 = dataInputStream.readLong();
            int i2 = dataInputStream.readInt();
            if (hashedSession == null) {
                hashedSession = (HashedSession) newSession(j2, j3, utf);
            }
            hashedSession.setRequests(i2);
            int i3 = dataInputStream.readInt();
            if (i3 > 0) {
                ClassLoadingObjectInputStream classLoadingObjectInputStream = new ClassLoadingObjectInputStream(dataInputStream);
                for (int i4 = 0; i4 < i3; i4++) {
                    try {
                        hashedSession.setAttribute(classLoadingObjectInputStream.readUTF(), classLoadingObjectInputStream.readObject());
                    } finally {
                        IO.close(classLoadingObjectInputStream);
                    }
                }
            }
            return hashedSession;
        } finally {
            IO.close(dataInputStream);
        }
    }
}
