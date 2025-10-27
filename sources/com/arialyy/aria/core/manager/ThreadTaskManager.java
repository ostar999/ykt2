package com.arialyy.aria.core.manager;

import android.text.TextUtils;
import com.arialyy.aria.core.task.IThreadTask;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class ThreadTaskManager {
    private static final int CORE_POOL_NUM = 20;
    private static volatile ThreadTaskManager INSTANCE;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private ThreadPoolExecutor mExePool;
    private final String TAG = CommonUtil.getClassName(this);
    private Map<String, Set<FutureContainer>> mThreadTasks = new ConcurrentHashMap();

    public class FutureContainer {
        Future future;
        IThreadTask threadTask;

        private FutureContainer() {
        }
    }

    private ThreadTaskManager() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue());
        this.mExePool = threadPoolExecutor;
        threadPoolExecutor.allowsCoreThreadTimeOut();
    }

    public static synchronized ThreadTaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThreadTaskManager();
        }
        return INSTANCE;
    }

    private String getKey(String str) {
        return CommonUtil.getStrMd5(str);
    }

    public void removeAllThreadTask() {
        try {
            if (this.mThreadTasks.isEmpty()) {
                return;
            }
            try {
                LOCK.tryLock(2L, TimeUnit.SECONDS);
                for (Set<FutureContainer> set : this.mThreadTasks.values()) {
                    for (FutureContainer futureContainer : set) {
                        if (!futureContainer.future.isDone() && !futureContainer.future.isCancelled()) {
                            futureContainer.threadTask.destroy();
                        }
                    }
                    set.clear();
                }
                this.mThreadTasks.clear();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        } finally {
            LOCK.unlock();
        }
    }

    public boolean removeSingleTaskThread(String str, String str2) {
        ReentrantLock reentrantLock;
        FutureContainer next;
        try {
            try {
                reentrantLock = LOCK;
                reentrantLock.tryLock(2L, TimeUnit.SECONDS);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.mExePool.isShutdown()) {
                ALog.e(this.TAG, "线程池已经关闭");
                reentrantLock.unlock();
                return false;
            }
            if (TextUtils.isEmpty(str2)) {
                ALog.e(this.TAG, "线程名为空");
                reentrantLock.unlock();
                return false;
            }
            Set<FutureContainer> set = this.mThreadTasks.get(getKey(str));
            if (set != null && set.size() > 0) {
                Iterator<FutureContainer> it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (next.threadTask.getThreadName().equals(str2)) {
                        break;
                    }
                }
                if (next != null) {
                    next.threadTask.destroy();
                    set.remove(next);
                    LOCK.unlock();
                    return true;
                }
            }
            return false;
        } finally {
            LOCK.unlock();
        }
    }

    public void removeTaskThread(String str) {
        ReentrantLock reentrantLock;
        try {
            try {
                reentrantLock = LOCK;
                reentrantLock.tryLock(2L, TimeUnit.SECONDS);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.mExePool.isShutdown()) {
                ALog.e(this.TAG, "线程池已经关闭");
                reentrantLock.unlock();
                return;
            }
            String key = getKey(str);
            Set<FutureContainer> set = this.mThreadTasks.get(key);
            if (set != null && set.size() > 0) {
                for (FutureContainer futureContainer : set) {
                    if (!futureContainer.future.isDone() && !futureContainer.future.isCancelled()) {
                        futureContainer.threadTask.destroy();
                    }
                }
                set.clear();
                this.mThreadTasks.remove(key);
            }
        } finally {
            LOCK.unlock();
        }
    }

    public void retryThread(IThreadTask iThreadTask) {
        try {
            ReentrantLock reentrantLock = LOCK;
            reentrantLock.tryLock(2L, TimeUnit.SECONDS);
            if (this.mExePool.isShutdown()) {
                ALog.e(this.TAG, "线程池已经关闭");
                reentrantLock.unlock();
                return;
            }
            if (iThreadTask != null) {
                try {
                    if (!iThreadTask.isDestroy()) {
                        this.mExePool.submit(iThreadTask);
                        reentrantLock.unlock();
                        return;
                    }
                } catch (Exception e2) {
                    ALog.e(this.TAG, "", e2);
                    return;
                }
            }
            ALog.e(this.TAG, "线程为空或线程已经中断");
            reentrantLock.unlock();
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public void startThread(String str, IThreadTask iThreadTask) {
        try {
            try {
                ReentrantLock reentrantLock = LOCK;
                reentrantLock.tryLock(2L, TimeUnit.SECONDS);
                if (this.mExePool.isShutdown()) {
                    ALog.e(this.TAG, "线程池已经关闭");
                    reentrantLock.unlock();
                    return;
                }
                String key = getKey(str);
                Set<FutureContainer> hashSet = this.mThreadTasks.get(key);
                if (hashSet == null) {
                    hashSet = new HashSet<>();
                    this.mThreadTasks.put(key, hashSet);
                }
                FutureContainer futureContainer = new FutureContainer();
                futureContainer.threadTask = iThreadTask;
                futureContainer.future = this.mExePool.submit(iThreadTask);
                hashSet.add(futureContainer);
                reentrantLock.unlock();
            } catch (Exception e2) {
                e2.printStackTrace();
                LOCK.unlock();
            }
        } catch (Throwable th) {
            LOCK.unlock();
            throw th;
        }
    }

    public boolean taskIsRunning(String str) {
        return this.mThreadTasks.get(getKey(str)) != null;
    }

    public boolean removeSingleTaskThread(String str, IThreadTask iThreadTask) {
        ReentrantLock reentrantLock;
        FutureContainer next;
        try {
            try {
                reentrantLock = LOCK;
                reentrantLock.tryLock(2L, TimeUnit.SECONDS);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.mExePool.isShutdown()) {
                ALog.e(this.TAG, "线程池已经关闭");
                reentrantLock.unlock();
                return false;
            }
            if (iThreadTask == null) {
                ALog.e(this.TAG, "线程任务为空");
                reentrantLock.unlock();
                return false;
            }
            Set<FutureContainer> set = this.mThreadTasks.get(getKey(str));
            if (set != null && set.size() > 0) {
                Iterator<FutureContainer> it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (next.threadTask == iThreadTask) {
                        break;
                    }
                }
                if (next != null) {
                    iThreadTask.destroy();
                    set.remove(next);
                    LOCK.unlock();
                    return true;
                }
            }
            return false;
        } finally {
            LOCK.unlock();
        }
    }
}
