package com.arialyy.aria.core.queue.pool;

/* loaded from: classes2.dex */
public class DLoadSharePool {
    private static volatile DLoadSharePool INSTANCE;
    public DLoadExecutePool executePool = new DLoadExecutePool();
    public BaseCachePool cachePool = new BaseCachePool();

    private DLoadSharePool() {
    }

    public static DLoadSharePool getInstance() {
        if (INSTANCE == null) {
            synchronized (DLoadSharePool.class) {
                INSTANCE = new DLoadSharePool();
            }
        }
        return INSTANCE;
    }
}
