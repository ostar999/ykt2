package com.arialyy.aria.core.queue.pool;

/* loaded from: classes2.dex */
public class DGLoadSharePool {
    private static volatile DGLoadSharePool INSTANCE;
    public DGLoadExecutePool executePool = new DGLoadExecutePool();
    public BaseCachePool cachePool = new BaseCachePool();

    private DGLoadSharePool() {
    }

    public static DGLoadSharePool getInstance() {
        if (INSTANCE == null) {
            synchronized (DGLoadSharePool.class) {
                INSTANCE = new DGLoadSharePool();
            }
        }
        return INSTANCE;
    }
}
