package com.arialyy.aria.core.manager;

import android.util.LruCache;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.upload.UTaskWrapper;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class TaskWrapperManager {
    private static volatile TaskWrapperManager INSTANCE = null;
    private static final String TAG = "TaskWrapperManager";
    private LruCache<String, AbsTaskWrapper> cache = new LruCache<>(1024);
    private Lock lock = new ReentrantLock();

    private TaskWrapperManager() {
    }

    private IGroupWrapperFactory chooseGroupFactory(Class cls) {
        if (cls == DGTaskWrapper.class) {
            return DGTaskWrapperFactory.getInstance();
        }
        return null;
    }

    private INormalTEFactory chooseNormalFactory(Class cls) {
        if (cls == DTaskWrapper.class) {
            return DTaskWrapperFactory.getInstance();
        }
        if (cls == UTaskWrapper.class) {
            return UTaskWrapperFactory.getInstance();
        }
        return null;
    }

    private String convertKey(Class cls, long j2) {
        return CommonUtil.keyToHashKey(cls.getName() + j2);
    }

    public static TaskWrapperManager getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskWrapperManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskWrapperManager();
                }
            }
        }
        return INSTANCE;
    }

    public <TW extends AbsTaskWrapper> TW getGroupWrapper(Class<TW> cls, long j2) {
        Lock lock = this.lock;
        lock.lock();
        try {
            TW tw = (TW) this.cache.get(convertKey(cls, j2));
            if (tw == null || tw.getClass() != cls) {
                IGroupWrapperFactory iGroupWrapperFactoryChooseGroupFactory = chooseGroupFactory(cls);
                if (iGroupWrapperFactoryChooseGroupFactory == null) {
                    ALog.e(TAG, "任务实体创建失败");
                    lock.unlock();
                    return null;
                }
                tw = (TW) iGroupWrapperFactoryChooseGroupFactory.getGroupWrapper(j2);
                putTaskWrapper(tw);
            }
            return tw;
        } finally {
            lock.unlock();
        }
    }

    public <TW extends AbsTaskWrapper> TW getNormalTaskWrapper(Class<TW> cls, long j2) {
        Lock lock = this.lock;
        lock.lock();
        try {
            TW tw = (TW) this.cache.get(convertKey(cls, j2));
            if (tw == null || tw.getClass() != cls) {
                INormalTEFactory iNormalTEFactoryChooseNormalFactory = chooseNormalFactory(cls);
                if (iNormalTEFactoryChooseNormalFactory == null) {
                    ALog.e(TAG, "任务实体创建失败");
                    lock.unlock();
                    return null;
                }
                tw = (TW) iNormalTEFactoryChooseNormalFactory.create(j2);
                putTaskWrapper(tw);
            }
            return tw;
        } finally {
            lock.unlock();
        }
    }

    public void putTaskWrapper(AbsTaskWrapper absTaskWrapper) {
        if (absTaskWrapper == null) {
            ALog.e(TAG, "任务实体添加失败");
            return;
        }
        if (absTaskWrapper.getEntity() == null || absTaskWrapper.getEntity().getId() == -1) {
            return;
        }
        Lock lock = this.lock;
        lock.lock();
        try {
            this.cache.put(convertKey(absTaskWrapper.getClass(), absTaskWrapper.getEntity().getId()), absTaskWrapper);
        } finally {
            lock.unlock();
        }
    }

    public void removeTaskWrapper(AbsTaskWrapper absTaskWrapper) {
        Lock lock = this.lock;
        lock.lock();
        try {
            this.cache.remove(convertKey(absTaskWrapper.getClass(), absTaskWrapper.getEntity().getId()));
        } finally {
            lock.unlock();
        }
    }
}
