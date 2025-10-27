package com.nirvana.tools.logger.executor;

/* loaded from: classes4.dex */
public abstract class AbstractSafeRunnable implements Runnable {
    @Override // java.lang.Runnable
    public void run() {
        try {
            safeRun();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public abstract void safeRun();
}
