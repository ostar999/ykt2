package org.eclipse.jetty.util.thread;

/* loaded from: classes9.dex */
public interface ThreadPool {

    public interface SizedThreadPool extends ThreadPool {
        int getMaxThreads();

        int getMinThreads();

        void setMaxThreads(int i2);

        void setMinThreads(int i2);
    }

    boolean dispatch(Runnable runnable);

    int getIdleThreads();

    int getThreads();

    boolean isLowOnThreads();

    void join() throws InterruptedException;
}
