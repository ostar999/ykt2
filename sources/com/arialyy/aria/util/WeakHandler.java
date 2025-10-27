package com.arialyy.aria.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class WeakHandler {
    private final Handler.Callback mCallback;
    private final ExecHandler mExec;
    private Lock mLock;
    final ChainedRef mRunnables;

    public static class WeakRunnable implements Runnable {
        private final WeakReference<Runnable> mDelegate;
        private final WeakReference<ChainedRef> mReference;

        public WeakRunnable(WeakReference<Runnable> weakReference, WeakReference<ChainedRef> weakReference2) {
            this.mDelegate = weakReference;
            this.mReference = weakReference2;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = this.mDelegate.get();
            ChainedRef chainedRef = this.mReference.get();
            if (chainedRef != null) {
                chainedRef.remove();
            }
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public WeakHandler() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new ChainedRef(reentrantLock, null);
        this.mCallback = null;
        this.mExec = new ExecHandler();
    }

    private WeakRunnable wrapRunnable(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("Runnable can't be null");
        }
        ChainedRef chainedRef = new ChainedRef(this.mLock, runnable);
        this.mRunnables.insertAfter(chainedRef);
        return chainedRef.wrapper;
    }

    public final Looper getLooper() {
        return this.mExec.getLooper();
    }

    public final boolean hasMessages(int i2) {
        return this.mExec.hasMessages(i2);
    }

    public final boolean post(Runnable runnable) {
        return this.mExec.post(wrapRunnable(runnable));
    }

    public final boolean postAtFrontOfQueue(Runnable runnable) {
        return this.mExec.postAtFrontOfQueue(wrapRunnable(runnable));
    }

    public final boolean postAtTime(Runnable runnable, long j2) {
        return this.mExec.postAtTime(wrapRunnable(runnable), j2);
    }

    public final boolean postDelayed(Runnable runnable, long j2) {
        return this.mExec.postDelayed(wrapRunnable(runnable), j2);
    }

    public final void removeCallbacks(Runnable runnable) {
        WeakRunnable weakRunnableRemove = this.mRunnables.remove(runnable);
        if (weakRunnableRemove != null) {
            this.mExec.removeCallbacks(weakRunnableRemove);
        }
    }

    public final void removeCallbacksAndMessages(Object obj) {
        this.mExec.removeCallbacksAndMessages(obj);
    }

    public final void removeMessages(int i2) {
        this.mExec.removeMessages(i2);
    }

    public final boolean sendEmptyMessage(int i2) {
        return this.mExec.sendEmptyMessage(i2);
    }

    public final boolean sendEmptyMessageAtTime(int i2, long j2) {
        return this.mExec.sendEmptyMessageAtTime(i2, j2);
    }

    public final boolean sendEmptyMessageDelayed(int i2, long j2) {
        return this.mExec.sendEmptyMessageDelayed(i2, j2);
    }

    public final boolean sendMessage(Message message) {
        return this.mExec.sendMessage(message);
    }

    public final boolean sendMessageAtFrontOfQueue(Message message) {
        return this.mExec.sendMessageAtFrontOfQueue(message);
    }

    public boolean sendMessageAtTime(Message message, long j2) {
        return this.mExec.sendMessageAtTime(message, j2);
    }

    public final boolean sendMessageDelayed(Message message, long j2) {
        return this.mExec.sendMessageDelayed(message, j2);
    }

    public static class ExecHandler extends Handler {
        private final WeakReference<Handler.Callback> mCallback;

        public ExecHandler() {
            this.mCallback = null;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Handler.Callback callback;
            WeakReference<Handler.Callback> weakReference = this.mCallback;
            if (weakReference == null || (callback = weakReference.get()) == null) {
                return;
            }
            callback.handleMessage(message);
        }

        public ExecHandler(WeakReference<Handler.Callback> weakReference) {
            this.mCallback = weakReference;
        }

        public ExecHandler(Looper looper) {
            super(looper);
            this.mCallback = null;
        }

        public ExecHandler(Looper looper, WeakReference<Handler.Callback> weakReference) {
            super(looper);
            this.mCallback = weakReference;
        }
    }

    public final boolean hasMessages(int i2, Object obj) {
        return this.mExec.hasMessages(i2, obj);
    }

    public final boolean postAtTime(Runnable runnable, Object obj, long j2) {
        return this.mExec.postAtTime(wrapRunnable(runnable), obj, j2);
    }

    public final void removeMessages(int i2, Object obj) {
        this.mExec.removeMessages(i2, obj);
    }

    public final void removeCallbacks(Runnable runnable, Object obj) {
        WeakRunnable weakRunnableRemove = this.mRunnables.remove(runnable);
        if (weakRunnableRemove != null) {
            this.mExec.removeCallbacks(weakRunnableRemove, obj);
        }
    }

    public WeakHandler(Handler.Callback callback) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new ChainedRef(reentrantLock, null);
        this.mCallback = callback;
        this.mExec = new ExecHandler((WeakReference<Handler.Callback>) new WeakReference(callback));
    }

    public static class ChainedRef {
        Lock lock;
        ChainedRef next;
        ChainedRef prev;
        final Runnable runnable;
        final WeakRunnable wrapper;

        public ChainedRef(Lock lock, Runnable runnable) {
            this.runnable = runnable;
            this.lock = lock;
            this.wrapper = new WeakRunnable(new WeakReference(runnable), new WeakReference(this));
        }

        public void insertAfter(ChainedRef chainedRef) {
            this.lock.lock();
            try {
                ChainedRef chainedRef2 = this.next;
                if (chainedRef2 != null) {
                    chainedRef2.prev = chainedRef;
                }
                chainedRef.next = chainedRef2;
                this.next = chainedRef;
                chainedRef.prev = this;
            } finally {
                this.lock.unlock();
            }
        }

        public WeakRunnable remove() {
            this.lock.lock();
            try {
                ChainedRef chainedRef = this.prev;
                if (chainedRef != null) {
                    chainedRef.next = this.next;
                }
                ChainedRef chainedRef2 = this.next;
                if (chainedRef2 != null) {
                    chainedRef2.prev = chainedRef;
                }
                this.prev = null;
                this.next = null;
                this.lock.unlock();
                return this.wrapper;
            } catch (Throwable th) {
                this.lock.unlock();
                throw th;
            }
        }

        public WeakRunnable remove(Runnable runnable) {
            this.lock.lock();
            try {
                for (ChainedRef chainedRef = this.next; chainedRef != null; chainedRef = chainedRef.next) {
                    if (chainedRef.runnable == runnable) {
                        return chainedRef.remove();
                    }
                }
                this.lock.unlock();
                return null;
            } finally {
                this.lock.unlock();
            }
        }
    }

    public WeakHandler(Looper looper) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new ChainedRef(reentrantLock, null);
        this.mCallback = null;
        this.mExec = new ExecHandler(looper);
    }

    public WeakHandler(Looper looper, Handler.Callback callback) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLock = reentrantLock;
        this.mRunnables = new ChainedRef(reentrantLock, null);
        this.mCallback = callback;
        this.mExec = new ExecHandler(looper, new WeakReference(callback));
    }
}
