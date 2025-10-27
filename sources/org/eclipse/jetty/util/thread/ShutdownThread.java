package org.eclipse.jetty.util.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.component.Destroyable;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class ShutdownThread extends Thread {
    private static final Logger LOG = Log.getLogger((Class<?>) ShutdownThread.class);
    private static final ShutdownThread _thread = new ShutdownThread();
    private boolean _hooked;
    private final List<LifeCycle> _lifeCycles = new CopyOnWriteArrayList();

    private ShutdownThread() {
    }

    public static synchronized void deregister(LifeCycle lifeCycle) {
        ShutdownThread shutdownThread = _thread;
        shutdownThread._lifeCycles.remove(lifeCycle);
        if (shutdownThread._lifeCycles.size() == 0) {
            shutdownThread.unhook();
        }
    }

    public static ShutdownThread getInstance() {
        return _thread;
    }

    private synchronized void hook() {
        try {
            if (!this._hooked) {
                Runtime.getRuntime().addShutdownHook(this);
            }
            this._hooked = true;
        } catch (Exception e2) {
            Logger logger = LOG;
            logger.ignore(e2);
            logger.info("shutdown already commenced", new Object[0]);
        }
    }

    public static synchronized void register(LifeCycle... lifeCycleArr) {
        ShutdownThread shutdownThread = _thread;
        shutdownThread._lifeCycles.addAll(Arrays.asList(lifeCycleArr));
        if (shutdownThread._lifeCycles.size() > 0) {
            shutdownThread.hook();
        }
    }

    private synchronized void unhook() {
        try {
            this._hooked = false;
            Runtime.getRuntime().removeShutdownHook(this);
        } catch (Exception e2) {
            Logger logger = LOG;
            logger.ignore(e2);
            logger.debug("shutdown already commenced", new Object[0]);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        for (LifeCycle lifeCycle : _thread._lifeCycles) {
            try {
                if (lifeCycle.isStarted()) {
                    lifeCycle.stop();
                    LOG.debug("Stopped {}", lifeCycle);
                }
                if (lifeCycle instanceof Destroyable) {
                    ((Destroyable) lifeCycle).destroy();
                    LOG.debug("Destroyed {}", lifeCycle);
                }
            } catch (Exception e2) {
                LOG.debug(e2);
            }
        }
    }

    public static synchronized void register(int i2, LifeCycle... lifeCycleArr) {
        ShutdownThread shutdownThread = _thread;
        shutdownThread._lifeCycles.addAll(i2, Arrays.asList(lifeCycleArr));
        if (shutdownThread._lifeCycles.size() > 0) {
            shutdownThread.hook();
        }
    }
}
